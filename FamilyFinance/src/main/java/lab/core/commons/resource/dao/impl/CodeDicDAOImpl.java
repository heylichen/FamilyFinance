package lab.core.commons.resource.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lab.core.commons.resource.dao.CodeDicDAO;
import lab.core.commons.resource.entity.CodeDic;
import lab.core.orm.hibernate.base.AbstractHibernateDAO;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class CodeDicDAOImpl  extends AbstractHibernateDAO  implements CodeDicDAO   { 
	
	  private static final String Order = null;
	  private Order order2;
 
	/* (non-Javadoc)
	 * @see lab.core.commons.resource.dao.CodeDicDAO#getCodeMapByType(java.lang.String)
	 */
	public Map<String,CodeDic> getCodeMapByType(String type){ 
		  List<CodeDic> list = (List<CodeDic>)getCodeList(null,type); 
		  Map<String,CodeDic> map = new TreeMap<String,CodeDic>();
		  for(CodeDic cd: list) {
			  map.put(cd.getCategory(), cd);
		  }
		  return map; 
	  }
	 
	/* (non-Javadoc)
	 * @see lab.core.commons.resource.dao.CodeDicDAO#getCodeList(java.lang.String, java.lang.String)
	 */
	public List<CodeDic> getCodeList(String bizSystem, String type){
		  Criteria c = this.getCurrentSession().createCriteria(CodeDic.class);
		  if(bizSystem!=null){
			  c.add(Restrictions.eq("bizSystem", bizSystem));
		  }
		  if(type!=null){
			  c.add(Restrictions.eq("type", type));
		  }
		  c.addOrder(order2.asc("code"));
		  
		  return c.list();
	  }
	  
	  /* (non-Javadoc)
	 * @see lab.core.commons.resource.dao.CodeDicDAO#queryCodeDic(lab.core.commons.resource.entity.CodeDic)
	 */
	public List<CodeDic> queryCodeDic(CodeDic codeD) {
		  Criteria c = this.getCurrentSession().createCriteria(CodeDic.class);
		  
		  if(codeD.getCategory()!=null){
			  c.add(Restrictions.eq("type", codeD.getCategory()));
		  }
		  if(codeD.getCode()!=null){
			  c.add(Restrictions.eq("code", codeD.getCode()));
		  }
		  return c.list();
	  }
}
