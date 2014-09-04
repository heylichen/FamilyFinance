package lab.core.dao.impl.transformer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.core.commons.vo.BusinessException;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.transform.ResultTransformer;

public class CustomAliasToMapTransformer implements ResultTransformer { 
	public CustomAliasToMapTransformer( ) {
		 
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Map<String,Object> result = new HashMap<String,Object>(); 
		Object value;
		try {
			 
			for (int i = 0; i < aliases.length; i++) {
				String alias = aliases[i];
				String firstCh = alias.substring(0, 1); 
				// get property name
				if (firstCh.equals(firstCh.toUpperCase())) {
					alias = ColumnNameToPropertyName(alias); 
				}
				// get value
				value = tuple[i];
				result.put(alias, value); 
			}// end of for
		}   catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(e);
		}

		return result;
	}

	public String ColumnNameToPropertyName(String colName) {
		if (StringUtils.isBlank(colName)) {
			return StringUtils.EMPTY;
		}
		String colName2 = colName.toLowerCase();
		String[] cols = colName2.split("_");
		StringBuilder sb = new StringBuilder();
		sb.append(cols[0]);
		if (cols.length == 1) {
			return sb.toString();
		}

		for (int i = 1; i < cols.length; i++) {
			String col = cols[i];
			sb.append(col.substring(0, 1).toUpperCase());
			sb.append(col.substring(1));
		}
		return sb.toString();
	}

	public List transformList(List collection) {
		return collection;
	}

}
