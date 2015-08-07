package com.sr178.common.jdbc;

/**
 * 一个bean的sql转换
 * 
 * @author dogdog
 * 
 */

public class BeanToSQL {
	private String sql;
	private SqlParameter params = new SqlParameter();
	public void AddParam(Object param) {
		if (param instanceof String) {
			this.params.setString(param.toString());
		} else if (param instanceof Integer) {
			this.params.setInt(Integer.parseInt(param.toString()));
		} else if (param instanceof Long) {
			this.params.setLong(Long.parseLong(param.toString()));
		} else if (param instanceof Double) {
			this.params.setDouble(Double.parseDouble(param.toString()));
		} else {
			this.params.setObject(param);
		}

	}
	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public SqlParameter getParams() {
		return params;
	}
}
