package lab.core.dao.proxy;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import lab.business.finance.user.dao.UserDAO;
import lab.core.commons.vo.BusinessException;
import lab.core.dao.GenericDAO;
import lab.core.dao.impl.GenericDAOHibernateImpl;

import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
/**
 * 自动代理DAO实现的主要工具类
 * @author heylichen
 *
 */
public class SpecificDAOUtils {
	private static Logger log = LoggerFactory.getLogger(SpecificDAOUtils.class);
	/**
	 * 增加泛型DAO接口的代理实现
	 */
	public static void addGenericDAO(ConfigurableListableBeanFactory beanFactory) {
		String[] beanNames = beanFactory.getBeanDefinitionNames();
		Map<Class, SpecificDAOMeta> map = new HashMap<Class, SpecificDAOMeta>();

		for (String beanName : beanNames) {
			BeanDefinition bd = beanFactory.getBeanDefinition(beanName);
			try {
				if(bd!=null&&bd.getBeanClassName()!=null){ 
					Class beanClass = Class.forName(bd.getBeanClassName());
					/*if(beanName.equals("userService")){
						System.out.println("->user service");
					}*/
					map = joinSpecificMeta(beanClass, map, beanFactory);  
					
				}
				
			} catch (Exception ex) {
				ex.printStackTrace();
			} 
		}
		 
		mergeDAOToContext(beanFactory,map);
	}

	/**
	 * 将需要使用代理的DAO接口在spring context中注册
	 */
	public static void mergeDAOToContext(
			ConfigurableListableBeanFactory beanFactory,
			Map<Class, SpecificDAOMeta> map) {
		SessionFactory sf = beanFactory.getBean(SessionFactory.class);

		for (SpecificDAOMeta meta : map.values()) {
			StringBuilder sb = new StringBuilder();
			sb.append(meta.getSpecificDAOClass().getSimpleName()
					.substring(0, 1).toLowerCase());
			sb.append(meta.getSpecificDAOClass().getSimpleName().substring(1));
			String beanName = sb.toString();
			// 实例化代理类
			GenericDAOHibernateImpl impl = new GenericDAOHibernateImpl();
			if (meta.getActualTypeArguments() != null
					&& meta.getActualTypeArguments().length >= 1) {
				Type firstType = meta.getActualTypeArguments()[0];
				if (firstType instanceof Class) {
					impl.setEntityClass((Class) firstType);
				}
			}
			// cglib 创建代理
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(meta.getSpecificDAOClass());
			enhancer.setCallback(new SimpleMethodInterceptor(impl));
			// 注册
			Object proxyDAO = enhancer.create(); 
			if(proxyDAO instanceof GenericDAO){
				try{
					PropertyUtils.setProperty(proxyDAO, "sessionFactory", sf); 
				}catch(Exception ex){
					throw new BusinessException("Fail to set sessionFactory to "+meta.getSpecificDAOClass().getName()+" implementation!" ,ex);
				} 
			}
 			beanFactory.registerSingleton(beanName, enhancer.create());
 			log.info("registered bean --> : {}",beanName); 
		}
	}

	/**
	 * 收集需要使用代理的DAO接口信息
	 * 
	 * @param targetClass
	 * @param map
	 * @param beanFactory
	 * @return
	 */
	public static Map<Class, SpecificDAOMeta> joinSpecificMeta(
			Class targetClass, Map<Class, SpecificDAOMeta> map,
			ConfigurableListableBeanFactory beanFactory) {

		for (Field f : targetClass.getDeclaredFields()) {
			Type[] gys = f.getType().getGenericInterfaces();
			boolean extendsGenericDAO = false;
			// 先判断是否是继承自GenericDAO的接口
			for (Type type : gys) {
				if (type instanceof ParameterizedType) {
					ParameterizedType t = (ParameterizedType) type;
					if (t.getRawType().equals(lab.core.dao.GenericDAO.class)) {
						// 是关注的依赖
						extendsGenericDAO = true;
					}
				}

			}
			// 再判断在spring context中是否已存在
			boolean existsInContext = true;
			if (extendsGenericDAO == true) {
				try {
					UserDAO ud = beanFactory.getBean(UserDAO.class);
					existsInContext = true;
				} catch (NoSuchBeanDefinitionException ex) {
					existsInContext = false;
				}
			}

			if (extendsGenericDAO && !existsInContext) {
				Type[] types = getParameterTypes(f.getType());
				SpecificDAOMeta meta = new SpecificDAOMeta(f.getType(), types);
				if (map == null) {
					map = new HashMap<Class, SpecificDAOMeta>(); 
				}
				map.put(f.getType(), meta);
			}

		}
		return map;
	}

	/**
	 * 获取某接口的泛型参数
	 * 
	 * @param cl
	 * @return
	 */
	public static Type[] getParameterTypes(Class<?> cl) {
		Type[] gis = cl.getGenericInterfaces();
		if (gis != null && gis.length == 1) {
			Type type = gis[0];
			if (type instanceof ParameterizedType) {
				ParameterizedType t = (ParameterizedType) type;

				Type[] types = t.getActualTypeArguments();
				/*
				 * for(Type pType: types){
				 * System.out.println(pType.getTypeName()); }
				 */
				return types;
			}
			return new Type[] {};
		} else {
			return new Type[] {};
		}
	}

}
