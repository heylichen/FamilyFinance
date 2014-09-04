package lab.core.commons.resource.service.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.transaction.Transactional;

import lab.core.commons.resource.dao.CodeDicDAO;
import lab.core.commons.resource.entity.CodeDic;
import lab.core.commons.resource.service.ResourceService;
import lab.core.commons.resource.vo.ResourceConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {
 
	@Autowired
	private ResourceConfig resourceConfig;
	@Autowired
	private CodeDicDAO codeDictDAO;
	
	/* (non-Javadoc)
	 * @see lab.core.commons.resource.service.ResourceService#loadAllCodeDict()
	 */
	@Transactional
	public void loadAllCodeDict() { 
		List<CodeDic> list = codeDictDAO.getCodeList(null,null);
		Map<String,Map<String,String>> totalMap = new TreeMap<String,Map<String,String>>();
		for(CodeDic c:list){
			joinTotal(c,totalMap);
		} 
		resourceConfig.setDictMap(totalMap);
	}
    
	private void joinTotal(CodeDic c, Map<String,Map<String,String>> totalMap){
		Map<String,String> dicMap = totalMap.get(c.getCategory());
		if(dicMap==null){
			dicMap = new TreeMap<String,String>();
			totalMap.put(c.getCategory(), dicMap);
		}
		dicMap.put(c.getCode(), c.getLabel());
	}
	/* (non-Javadoc)
	 * @see lab.core.commons.resource.service.ResourceService#loadAllCodeDictFull()
	 */
	@Transactional
	public void loadAllCodeDictFull() { 
		List<CodeDic> list = codeDictDAO.getCodeList(null,null);
		resourceConfig.setDicFullList(list);
	}
	
 
}
