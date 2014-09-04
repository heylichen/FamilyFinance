package lab.core.dao.proxy;

import java.io.Serializable;
import java.lang.reflect.Type;
/**
 * 记录某bean的类型信息
 * 用于实例化代理bean
 * @author heylichen
 *
 */
public class SpecificDAOMeta implements Serializable {
	private Class<?> specificDAOClass;
	private Type[] actualTypeArguments;

	public Class<?> getSpecificDAOClass() {
		return specificDAOClass;
	}

	public void setSpecificDAOClass(Class<?> specificDAOClass) {
		this.specificDAOClass = specificDAOClass;
	}

	public Type[] getActualTypeArguments() {
		return actualTypeArguments;
	}

	public void setActualTypeArguments(Type[] actualTypeArguments) {
		this.actualTypeArguments = actualTypeArguments;
	}

	public SpecificDAOMeta() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SpecificDAOMeta(Class<?> specificDAOClass, Type[] actualTypeArguments) {
		super();
		this.specificDAOClass = specificDAOClass;
		this.actualTypeArguments = actualTypeArguments;
	}

}
