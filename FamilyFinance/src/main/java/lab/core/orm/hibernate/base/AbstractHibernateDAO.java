package lab.core.orm.hibernate.base;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import lab.core.commons.vo.LocalBusinessException;
import lab.core.dao.impl.util.HqlUtils;
import lab.core.dao.impl.vo.HqlParameter;
import lab.core.dao.impl.vo.Page;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

 
public class AbstractHibernateDAO   {
	@Autowired
	private SessionFactory sf;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * platform.core.base.IAbstractHibernateDAO#pageQuery(org.hibernate.Criteria
	 * , platform.core.base.Page)
	 */
	
	public void pageQuery(Criteria c, Page p) {
		// 查询总记录数
		p.setTotalCount(getCount(c));
		// 填充排序参数
		populateOrders(c, p);
		// 设置分页参数
		c.setFirstResult(p.getStart());
		c.setMaxResults(p.getLimit());
		p.setData(c.list());
	}

	// 填充排序参数
	private void populateOrders(Criteria c, Page p) {
		if (p.getOrders() != null && p.getOrders().size() > 0) {
			for (Order order : (List<Order>) p.getOrders()) {
				c.addOrder(order);
			}
		}
	}

	// 查询总记录数
	public Integer getCount(Criteria c) {
		c.setProjection(Projections.rowCount());
		Integer count = (Integer) c.uniqueResult();
		c.setProjection(null);
		return count;
	}

	protected Session getCurrentSession() {
		return sf.getCurrentSession();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see platform.core.base.IAbstractHibernateDAO#getSf()
	 */
	
	public SessionFactory getSf() {
		return sf;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * platform.core.base.IAbstractHibernateDAO#setSf(org.hibernate.SessionFactory
	 * )
	 */
	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	 
	/**
	 * 使用sql的分页查询
	 * 
	 * @param sql
	 * @param paramList
	 * @param entityClass
	 * @param page
	 * @return
	 */
	public void sqlPageQuery(String sql, List<HqlParameter> paramList,
			Class entityClass, Page page) {
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
		sq.addEntity(entityClass);

		page.setData(sq.list());
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
			throw new LocalBusinessException("无效的SQL!");
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

	public List sqlQuery(String sql, List<HqlParameter> paramList,
			Class entityClass) {
		SQLQuery sq = this.getCurrentSession().createSQLQuery(sql);
		HqlUtils.setQueryParameters(sq, paramList);
		sq.addEntity(entityClass);
		return sq.list();
	}

	public void saveOrUpdate(List list) {
		for (Object obj : list) {
			this.getCurrentSession().saveOrUpdate(obj);
		}
	}

	public Long seqNextValue(String seqName) {
		String sql = StringUtils.join("select ", seqName, ".Nextval from dual");
		SQLQuery sq = this.getCurrentSession().createSQLQuery(sql);
		return ((BigDecimal) sq.uniqueResult()).longValue();
	}
}
