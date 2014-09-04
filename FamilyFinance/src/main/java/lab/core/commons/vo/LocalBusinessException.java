package lab.core.commons.vo;

import java.util.Map;
import java.util.TreeMap;

public class LocalBusinessException extends BusinessException {
	public static final String EX_TYPE_VALIDATION = "EX_VALIDATION";// 验证错误，能提供验证错误信息
	public static final String EX_TYPE_COMMON_TIP = "EX_COMMON_TIP";// 普通业务异常
	public static final String EX_TYPE_FILTER_LOGON = "FILTER_LOGON";

	private String type = EX_TYPE_COMMON_TIP;// 错误类型
	private Map<String, String> validationErrorMap = new TreeMap<String, String>();
 
	private Object data;

	public String getType() {
		 
		return type;
	}

	public LocalBusinessException(String type,String msg) {
		super(msg);
		this.type = type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, String> getValidationErrorMap() {
		return validationErrorMap;
	}

	 

	public void setValidationErrorMap(Map<String, String> validationErrorMap) {
		this.validationErrorMap = validationErrorMap;
	}

	public LocalBusinessException() {
		super();

	}
 

	public LocalBusinessException(String message, Throwable cause) {
		super(message, cause);

	}

	 

	public LocalBusinessException(String message) {
		super(message);

	}

	public LocalBusinessException(Throwable cause) {
		super(cause);

	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
