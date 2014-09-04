package lab.core.orm.hibernate.base;

import java.io.Serializable;

import lab.core.dao.impl.vo.Page;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

public interface HibernateDAO {

	/**
	 * 使用criteria进行分页查询
	 * 可在Page参数中指定分页，排序相关参数
	 * 此处不负责填充查询条件，需在子类中填充
	 * @param c
	 * @param p
	 */
	public abstract void pageQuery(Criteria c, Page p);

	public abstract SessionFactory getSf();

	public abstract void setSf(SessionFactory sf);

	/**
	 * 保存实体
	 * @param obj
	 */
	public abstract void save(Object obj);

	/**
	 * 删除实体
	 * @param obj
	 */
	public abstract void remove(Object obj);

	/**
	 * 根据ID删除实体
	 * @param obj
	 */
	public abstract void removeById(Class clazz, Serializable id);

	/**
	 * 更新实体
	 * @param obj
	 */
	public abstract void update(Object obj);
	public void saveOrUpdate(Object obj);
	public Object get(Class clz, Serializable id) ;
	public Object load(Class clz, Serializable id);
	public Long seqNextValue(String seqName) ;
}