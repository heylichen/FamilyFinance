package lab.core.dao;

import java.io.Serializable;
import java.util.List;

import lab.core.dao.impl.vo.HqlParameter;
import lab.core.dao.impl.vo.Page;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public interface GenericDAO<T, K extends Serializable> {
	// -------------------------分页查询
	/**
	 * Criteria的分页查询
	 */
	public void pageQuery(Criteria c, Page p);

	/**
	 * sql分页查询，结果作为List<VO>返回
	 */
	public void sqlPageQueryAsVO(String sql, List<HqlParameter> paramList,
			Class voClass, Page page);

	/**
	 * 使用sql的分页查询-返回data为 List<Map<String,Object>>
	 */
	public void sqlPageQueryAsMap(String sql, List<HqlParameter> paramList,
			Page page);

	/**
	 * 使用sql的分页查询-返回data为 List<Entity>
	 */
	public void sqlPageQuery(String sql, List<HqlParameter> paramList,
			Class entityClass, Page page);

	// ------------CRUD
	/**
	 * 根据主键加载
	 * 
	 * @param id
	 * @return
	 */
	public T getEntity(K id);

	// 保存
	public T save(T entity);

	/**
	 * 删除对象
	 * 
	 * @param entity
	 */
	public void deleteById(K id);

	// 删除对象
	public void delete(T entity);

	/**
	 * 保存或更新
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity);

	// -------辅助方法
	public SessionFactory getSessionFactory();

	public void setSessionFactory(SessionFactory sessionFactory);

	public Session getCurrentSession();

}
