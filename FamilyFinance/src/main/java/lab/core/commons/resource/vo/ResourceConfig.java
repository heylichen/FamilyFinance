package lab.core.commons.resource.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lab.core.commons.resource.entity.CodeDic;

import org.springframework.stereotype.Component;

@Component
public class ResourceConfig {
	 
	private Map<String,Map<String,String>> dictMap = new TreeMap<String,Map<String,String>>(); 
	private List<CodeDic> dicFullList = new ArrayList<CodeDic>();
	private Map<String ,LiveDic> liveDicMap = new HashMap<String ,LiveDic>();
	
	
	
	public Map<String,Map<String,String>> getSubDcitMap(String...typeList){
		Map<String,Map<String,String>> subDictMap = new TreeMap<String,Map<String,String>>(); 
		for(String type:typeList){
			subDictMap.put(type,dictMap.get(type)); 
		}
		return subDictMap;
	}

	public Map<String, List<CodeDic>> getSubDcitMapFull(
			String... typeList) {
		Map<String, List<CodeDic>> subDictMap = new TreeMap<String, List<CodeDic>>();
		for (String type : typeList) {
			List<CodeDic> clist = new ArrayList<CodeDic>();
			for (CodeDic c : dicFullList) {
				if (c.getCategory().equals(type)) {
					clist.add(c);
				}
			}
			subDictMap.put(type, clist);
		}
		return subDictMap;
	}

 
	public Map<String, Map<String, String>> getDictMap() {
		return dictMap;
	}
	public void setDictMap(Map<String, Map<String, String>> dictMap) {
		this.dictMap = dictMap;
	}

//	public Map<String, Map<String, CodeDict>> getDicFullMap() {
//		return dicFullMap;
//	}
//
//	public void setDicFullMap(Map<String, Map<String, CodeDict>> dicFullMap) {
//		this.dicFullMap = dicFullMap;
//	}

	public Map<String, LiveDic> getLiveDicMap() {
		return liveDicMap;
	}

	public void setLiveDicMap(Map<String, LiveDic> liveDicMap) {
		this.liveDicMap = liveDicMap;
	}

	public List<CodeDic> getDicFullList() {
		return dicFullList;
	}

	public void setDicFullList(List<CodeDic> dicFullList) {
		this.dicFullList = dicFullList;
	}

	 
	
}
