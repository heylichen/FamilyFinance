package lab.core.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lab.core.commons.vo.BusinessException;
import lab.core.dao.GenericDAO;
import lab.core.dao.impl.transformer.CustomAliasToBeanTransformer;
import lab.core.dao.impl.transformer.CustomAliasToMapTransformer;
import lab.core.dao.impl.util.HqlUtils;
import lab.core.dao.impl.vo.HqlParameter;
import lab.core.dao.impl.vo.Page;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Component;

@Component
public class GenericDAOHibernateImpl<T, K extends Serializable> implements
		GenericDAO<T, K> {
	private Class entityClass;
	private SessionFactory sessionFactory;

	/**
	 * Criteria的分页查询
	 * 
	 * @param c
	 * @param p
	 */
	public void pageQuery(Criteria c, Page p) {
		// 查询总记录数
		p.setTotalCount(getCount(c));
		// 设置分页参数
		c.setFirstResult(p.getStart() - 1);
		c.setMaxResults(p.getLimit());
		p.setData(c.list());
	}

	// 查询总记录数
	public Integer getCount(Criteria c) {
		c.setProjection(Projections.rowCount());
		Integer count = (Integer) c.uniqueResult();
		c.setProjection(null);
		return count;
	}

	/**
	 * sql分页查询，结果作为List<VO>返回
	 * 
	 * @param sql
	 * @param paramList
	 * @param voClass
	 * @param page
	 */
	public void sqlPageQueryAsVO(String sql, List<HqlParameter> paramList,
			Class voClass, Page page) {
		SQLQuery sq = toPageSqlQuery(sql, paramList, page);
		// set transformer
		sq.setResultTransformer(new CustomAliasToBeanTransformer(voClass));
		page.setData(sq.list());
	}

	/**
	 * 使用sql的分页查询-返回data为 List<Map<String,Object>>
	 * 
	 * @param sql
	 * @param paramList
	 * @param entityClass
	 * @param page
	 * @return
	 */
	public void sqlPageQueryAsMap(String sql, List<HqlParameter> paramList,
			Page page) {
		SQLQuery sq = toPageSqlQuery(sql, paramList, page);
		// set transformer
		sq.setResultTransformer(new CustomAliasToMapTransformer());
		page.setData(sq.list());
	}

	/**
	 * 使用sql的分页查询-返回data为 List<Entity>
	 * 
	 * @param sql
	 * @param paramList
	 * @param entityClass
	 * @param page
	 * @return
	 */
	public void sqlPageQuery(String sql, List<HqlParameter> paramList,
			Class entityClass, Page page) {
		SQLQuery sq = toPageSqlQuery(sql, paramList, page);

		sq.addEntity(entityClass);

		page.setData(sq.list());
	}

	/**
	 * 转化为分页查询
	 * 
	 * @param sql
	 * @param paramList
	 * @param page
	 * @return
	 */
	private SQLQuery toPageSqlQuery(String sql, List<HqlParameter> paramList,
			Page page) {
		// 获取count
		Integer count = getCount(sql, paramList);
		page.setTotalCount(count);
		// 生成分页sql
		StringBuilder sb = new StringBuilder(
				"SELECT * FROM   (SELECT a.*,rownum rn  FROM   ( ");
		sb.append(sql)
				.append(") a   WHERE  rownum <= :page_last_row) WHERE  rn >= :page_first_row ");
		List<HqlParameter> params = new ArrayList<HqlParameter>();
		params.add(new HqlParameter("page_first_row", page.getStart()));
		params.add(new HqlParameter("page_last_row", page.getStart()
				+ page.getLimit() - 1));

		// sql查询
		SQLQuery sq = this.getCurrentSession().createSQLQuery(sb.toString());
		HqlUtils.setQueryParameters(sq, paramList);// 查询参数
		HqlUtils.setQueryParameters(sq, params);// 分页参数

		return sq;
	}

	/**
	 * sql 查询记录数
	 * 
	 * @param sql
	 * @param paramList
	 * @return
	 */
	public Integer getCount(String sql, List<HqlParameter> paramList) {
		int fIdx = sql.indexOf("FROM");
		if (fIdx == -1) {
			fIdx = sql.indexOf("from");
		}
		if (fIdx == -1) {
			throw new BusinessException("无效的SQL!");
		}

		StringBuilder sb = new StringBuilder("select count(1)  ");
		sb.append(sql.substring(fIdx));

		SQLQuery sq = this.getCurrentSession().createSQLQuery(sb.toString());
		HqlUtils.setQueryParameters(sq, paramList);

		Integer count = null;
		BigDecimal countD = ((BigDecimal) sq.uniqueResult());
		if (countD != null) {
			count = countD.intValue();
		}

		return count;

	}

	/**
	 * 批量保存或更新
	 * 
	 * @param list
	 */
	public void saveOrUpdate(List<T> list) {
		for (Object obj : list) {
			this.getCurrentSession().saveOrUpdate(obj);
		}
	}

	/**
	 * 根据主键加载
	 * 
	 * @param id
	 * @return
	 */
	public T getEntity(K id) {
		return (T) this.getCurrentSession().get(entityClass, id);
	}

	/**
	 * 删除对象
	 * 
	 * @param entity
	 */
	public void deleteById(K id) {
		Object entity = this.getEntity(id);
		this.getCurrentSession().delete(entity);
	}

	/**
	 * 删除对象
	 * 
	 * @param entity
	 */
	public void delete(T entity) {
		this.getCurrentSession().delete(entity);
	}

	/**
	 * 保存或更新
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(T entity) {
		this.getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * 保存
	 */
	public T save(T entity) {
		this.getCurrentSession().save(entity);
		return entity;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public Class getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class entityClass) {
		this.entityClass = entityClass;
	}

}
