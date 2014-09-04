package lab.core.dao.impl.transformer;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lab.core.commons.vo.BusinessException;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.property.ChainedPropertyAccessor;
import org.hibernate.property.PropertyAccessor;
import org.hibernate.property.PropertyAccessorFactory;
import org.hibernate.property.Setter;
import org.hibernate.transform.ResultTransformer;

public class CustomAliasToBeanTransformer implements ResultTransformer {

	private final Class resultClass;
	private Setter[] setters;
	private PropertyAccessor propertyAccessor;

	public CustomAliasToBeanTransformer(Class resultClass) {
		if (resultClass == null)
			throw new IllegalArgumentException("resultClass cannot be null");
		this.resultClass = resultClass;
		propertyAccessor = new ChainedPropertyAccessor(new PropertyAccessor[] {
				PropertyAccessorFactory.getPropertyAccessor(resultClass, null),
				PropertyAccessorFactory.getPropertyAccessor("field") });
	}

	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result;
		Map<String, Object> cacheMap = new HashMap<String, Object>();
		String key = "";
		Object value;
		try {
			result = resultClass.newInstance();
			for (int i = 0; i < aliases.length; i++) {
				String alias = aliases[i];
				String firstCh = alias.substring(0, 1);
				key = alias;
				// get property name
				if (firstCh.equals(firstCh.toUpperCase())) {
					alias = ColumnNameToPropertyName(alias);
					key = alias;
				}
				// get value
				value = tuple[i];
				Class expectedClass = null;
				try {
					
					Field field = this.resultClass.getDeclaredField(alias); 
					expectedClass = field.getType();
					 
				} catch (NoSuchFieldException ex) {
					 continue;
				}
				// 类型转换
				if (tuple[i] != null && tuple[i] instanceof BigDecimal) {
					if (expectedClass.equals(Long.class)) {
						value = new Long(((BigDecimal) tuple[i]).longValue());

					} else if (expectedClass.equals(Integer.class)) {
						value = new Integer(((BigDecimal) tuple[i]).intValue());
					} else if (expectedClass.equals(Boolean.class)) {
						int v = ((BigDecimal) tuple[i]).intValue();
						if (v == 0) {
							value = false;
						} else {
							value = true;
						}
					}
				}
				// 赋值
				PropertyUtils.setProperty(result, key, value);
			}// end of for
		} catch (InstantiationException e) {
			throw new HibernateException("Could not instantiate resultclass: "
					+ resultClass.getName());
		} catch (Exception e) {
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
