package com.sr178.common.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.mysql.jdbc.Statement;
import com.sr178.common.jdbc.bean.Page;
import com.sr178.common.jdbc.util.SqlUtil;

public class JdbcImpl implements Jdbc {


	public <T> T get(final String sql, Class<T> cls, final SqlParameter parameter) {

		List<T> list = this.getList(sql, cls, parameter);
		if (list != null) {
			if (list.size() == 1) {
				return list.get(0);
			} else if (list.size() > 0) {
				throw new RuntimeException("查询单个对象时有存在多个相同对象");
			}
		}
		return null;
	}

	public <T> List<T> getList(String sql, Class<T> cls) {
		return this.getList(sql, cls, null);
	}

	public <T> List<T> getList(String sql, final Class<T> cls, final SqlParameter parameter) {

		DataColumnBeanToRowMapper<T> mapper = new DataColumnBeanToRowMapper<T>();
		mapper.setMappedClass(cls);

		List<T> list = this.jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				if (parameter != null) {
					for (Entry<Integer, Object> entry : parameter.getParams().entrySet()) {
						ps.setObject(entry.getKey(), entry.getValue());
					}
				}
			}
		}, mapper);

		return list;
	}

	public int getInt(String sql, final SqlParameter parameter) {

		return this.jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				if (parameter != null) {
					for (Entry<Integer, Object> entry : parameter.getParams().entrySet()) {
						ps.setObject(entry.getKey(), entry.getValue());
					}
				}
			}
		}, new ResultSetExtractor<Integer>() {

			public Integer extractData(ResultSet rs) throws SQLException {

				if (rs.next()) {
					return rs.getInt(1);
				}
				return 0;
			}
		});

	}

	public long getLong(String sql, final SqlParameter parameter) {

		return this.jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				if (parameter != null) {
					for (Entry<Integer, Object> entry : parameter.getParams().entrySet()) {
						ps.setObject(entry.getKey(), entry.getValue());
					}
				}
			}
		}, new ResultSetExtractor<Long>() {

			public Long extractData(ResultSet rs) throws SQLException {

				if (rs.next()) {
					return rs.getLong(1);
				}
				return 0l;
			}
		});

	}
	
	public <T> int insert(T t) {
		BeanToSQL beanSql = SqlUtil.createBeanSql(t);
		return this.update(beanSql.getSql(), beanSql.getParams());
	}

	public <T> int[] insert(final List<T> list) {

		BeansToSQL beansSql = SqlUtil.crateBeanSql(list);
		final List<SqlParameter> parameters = beansSql.getParams();

		return this.jdbcTemplate.batchUpdate(beansSql.getSql(), new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SqlParameter parameter = parameters.get(i);
				for (Entry<Integer, Object> entry : parameter.getParams().entrySet()) {
					ps.setObject(entry.getKey(), entry.getValue());
				}
			}

			public int getBatchSize() {
				return list.size();
			}
		});
	}

	public <T> int insert(String tableName, T t) {
		BeanToSQL beanSql = SqlUtil.createBeanSql(tableName,t);
		return this.update(beanSql.getSql(), beanSql.getParams());
	}

	public <T> int[] insert(String tableName,final List<T> list) {
		BeansToSQL beansSql = SqlUtil.crateBeanSql(tableName,list);
		final List<SqlParameter> parameters = beansSql.getParams();

		return this.jdbcTemplate.batchUpdate(beansSql.getSql(), new BatchPreparedStatementSetter() {

			public void setValues(PreparedStatement ps, int i) throws SQLException {
				SqlParameter parameter = parameters.get(i);
				for (Entry<Integer, Object> entry : parameter.getParams().entrySet()) {
					ps.setObject(entry.getKey(), entry.getValue());
				}
			}

			public int getBatchSize() {
				return list.size();
			}
		});
	}
	public <T> int insertBackKeys(T t) {
		final BeanToSQL beanSql = SqlUtil.createBeanSql(t);
		final SqlParameter parameter = beanSql.getParams();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(java.sql.Connection conn) throws SQLException {
				PreparedStatement ps = conn.prepareStatement(beanSql.getSql(),Statement.RETURN_GENERATED_KEYS);
				for (Entry<Integer, Object> entry : parameter.getParams().entrySet()) {
					ps.setObject(entry.getKey(), entry.getValue());
				} 
				return ps;
			}
		}, keyHolder);
		return keyHolder.getKey().intValue();
	}	
	public <T> int[] batchSql(String[] sqls) {
		return jdbcTemplate.batchUpdate(sqls);
	}
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public int update(final String sql, final SqlParameter parameter) {

		int effectRow = this.jdbcTemplate.update(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				if (parameter != null) {
					for (Entry<Integer, Object> entry : parameter.getParams().entrySet()) {
						ps.setObject(entry.getKey(), entry.getValue());
					}
				}
			}
		});

		return effectRow;
	}

	@Override
	public double getDouble(String sql, final SqlParameter parameter) {
		return this.jdbcTemplate.query(sql, new PreparedStatementSetter() {

			public void setValues(PreparedStatement ps) throws SQLException {
				if (parameter != null) {
					for (Entry<Integer, Object> entry : parameter.getParams().entrySet()) {
						ps.setObject(entry.getKey(), entry.getValue());
					}
				}
			}
		}, new ResultSetExtractor<Double>() {

			public Double extractData(ResultSet rs) throws SQLException {

				if (rs.next()) {
					return rs.getDouble(1);
				}
				return 0d;
			}
		});
	}

	@Override
	public <T> Page<T> getListPage(String sql, Class<T> cls, SqlParameter parameter,final int pageSize, final int pageIndex) {
		int startNum = pageIndex*pageSize;
		startNum = startNum<0?0:startNum;
		int endNum = pageSize;
		String resultSql = sql+" limit "+startNum+","+endNum;
 
		List<T> result = this.getList(resultSql, cls, parameter);

		return new Page<T>(result, count(sql, parameter), pageSize, 
				pageIndex);
	}
	
	
	/**
	 * 由select x 变成统计结果集的记录条数
	 * @param queryString
	 * @param parameter
	 * @return
	 */
	private long count(final String queryString,SqlParameter parameter){
		String sql = getCountString(queryString);
		return this.getLong(sql, parameter);
	}
	/**
	 * 
	 * @param queryString
	 * @return
	 */
	private String getCountString(final String queryString) {
		String tmp = queryString.trim();
		if (tmp.startsWith("from ")) {
			return " select count(*) " + queryString;
		}
		if (!tmp.toLowerCase().startsWith("select"))
			throw new RuntimeException(" the query not valid [" + queryString
					+ "]");
		int pos = queryString.indexOf(" from ");
		if (pos == -1)
			throw new RuntimeException("the query not valid [" + queryString
					+ "]");
		final String where = tmp.substring(pos);
		final String suffix = tmp.substring(7, pos);
		String token[] = suffix.split(",");
		String cntField = token[0];
		String [] str = cntField.split("[(]");
		
		if (str.length > 1) {
			cntField = str[1];
		}

		return " select count(*) " + where;
	}
}
