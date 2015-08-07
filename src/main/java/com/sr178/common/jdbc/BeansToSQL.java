package com.sr178.common.jdbc;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个bean的sql转换
 * 
 * @author dogdog
 * 
 */

public class BeansToSQL {

	private String sql;
	public List<SqlParameter> getParams() {
		return parameters;
	}

	public void AddParam(SqlParameter parameter) {
		this.parameters.add(parameter);
	}
	private List<SqlParameter> parameters = new ArrayList<SqlParameter>();

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
