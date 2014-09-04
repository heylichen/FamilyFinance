package lab.core.dao.proxy;

import java.lang.reflect.Method;

import org.apache.commons.beanutils.MethodUtils;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
/**
 * 实现了spring 的MethodInterceptor
 * 用于CGLIB的代理
 * @author heylichen
 *
 */
public class SimpleMethodInterceptor implements MethodInterceptor {
	private Object actualBean;
	@Override
	public Object intercept(Object obj, Method method, Object[] args,
			MethodProxy proxy) throws Throwable { 
		return MethodUtils.invokeMethod(actualBean, method.getName(), args);
	}
	public Object getActualBean() {
		return actualBean;
	}
	public void setActualBean(Object actualBean) {
		this.actualBean = actualBean;
	}
	public SimpleMethodInterceptor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SimpleMethodInterceptor(Object actualBean) {
		super();
		this.actualBean = actualBean;
	}

}
