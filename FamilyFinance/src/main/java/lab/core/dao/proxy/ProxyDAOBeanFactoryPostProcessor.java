package lab.core.dao.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * 实现spring BeanFactoryPostProcessor接口 在spring 的BeanFactory初始化后
 * 扫描所有bean，查询继承了GenericDAO但没有实例的接口
 * 使用GenericDAOHibernateImpl代理实例化，作为这些接口的bean
 * 
 * @author heylichen
 *
 */

public class ProxyDAOBeanFactoryPostProcessor implements
		BeanFactoryPostProcessor {
	Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		log.info("--> scanning for candicate DAO interfaces(extends GenericDAO and has no implementation): ");
		SpecificDAOUtils.addGenericDAO(beanFactory);
		log.info("--> scan done. ");
	}

}
