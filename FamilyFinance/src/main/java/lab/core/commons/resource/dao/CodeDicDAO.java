package lab.core.commons.resource.dao;

import java.util.List;
import java.util.Map;

import lab.core.commons.resource.entity.CodeDic;

public interface CodeDicDAO {

	public Map<String, CodeDic> getCodeMapByType(String type);

	public List<CodeDic> getCodeList(String bizSystem, String type);

	public List<CodeDic> queryCodeDic(CodeDic codeD);

}