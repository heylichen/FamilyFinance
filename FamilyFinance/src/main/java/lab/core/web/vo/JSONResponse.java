package lab.core.web.vo;

import java.io.Serializable;

public class JSONResponse implements Serializable { 
	private String msg;
	private Object data;
	private String type;//备用
	 
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
}
