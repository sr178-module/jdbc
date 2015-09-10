package com.sr178.common.jdbc.bean;

public class SqlParamBean {
	private String key;
	private Object value;
	private String type;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public SqlParamBean(String type,String key, Object value) {
		super();
		this.key = key;
		this.value = value;
		this.type = type;
	}
	public SqlParamBean(String key, Object value) {
		super();
		this.key = key;
		this.value = value;
	}
}
