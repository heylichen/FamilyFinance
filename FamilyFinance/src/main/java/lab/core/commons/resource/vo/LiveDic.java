package lab.core.commons.resource.vo;

import java.io.Serializable;
import java.util.Date;

public class LiveDic implements Serializable {
	private String key;
	private Object data;
	private Date createdOn;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	
}
