package com.sr178.common.jdbc;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据库参数
 * 
 * @author dogdog
 * 
 */

public class SqlParameter {
	
	public static SqlParameter Instance(){
		return new SqlParameter();
	}
	
	public SqlParameter withLong(Long param){
		return this.withObject(param);
	}
	
	public SqlParameter withInt(Integer param){
		return this.withObject(param);
	}

	public SqlParameter withString(String param){
		return this.withObject(param);
	}
	
	public SqlParameter withDouble(Double param){
		return this.withObject(param);
	}
	
	public SqlParameter withObject(Object param) {
		this.params.put(this.parameterInd++, param);
		return this;
	}
	/**
	 * 设置long型参数
	 * 
	 * @param param
	 */
	public void setLong(Long param) {

		this.setObject(param);
	}

	/**
	 * 设置double参数
	 * 
	 * @param param
	 */
	public void setDouble(Double param) {

		this.setObject(param);
	}

	public void setObject(Object param) {

		this.params.put(this.parameterInd++, param);
	}

	public Map<Integer, Object> getParams() {
		return params;
	}
	int parameterInd = 1;

	private Map<Integer, Object> params = new HashMap<Integer, Object>();

	/***
	 * 设置整形参数
	 * 
	 * @param param
	 */
	public void setInt(Integer param) {

		this.setObject(param);
	}

	/**
	 * 设置字符串参数
	 * 
	 * @param param
	 */
	public void setString(String param) {

		this.setObject(param);
	}
}
