package com.sr178.common.jdbc;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.sr178.common.jdbc.bean.Page;

public interface Jdbc {
	/**
	 * 获取整数返回值
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 */
	public int getInt(String sql, SqlParameter parameter);
	
	/**
	 * 获取double返回值
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 */
	public double getDouble(String sql, SqlParameter parameter);

	/**
	 * 获取单个对象
	 * 
	 * @param sql
	 * 
	 * @param cls
	 * @param parameter
	 * @return
	 */
	public <T> T get(String sql, Class<T> cls, SqlParameter parameter);

	/**
	 * 获取对象列表
	 * 
	 * @param sql
	 * @param cls
	 * @param parameter
	 * @return
	 */
	public <T> List<T> getList(String sql, Class<T> cls, SqlParameter parameter);

	/**
	 * 获取对象列表
	 * 
	 * @param sql
	 * @param cls
	 * @param parameter
	 * @return
	 */
	public <T> List<T> getList(String sql, Class<T> cls);

	/**
	 * 插入单个对象
	 * 
	 * @param t
	 * @return
	 */
	public <T> int insert(T t);

	/**
	 * 指量插入对象
	 * 
	 * @param list
	 * @return
	 */
	public <T> int[] insert(List<T> list);
	
	
	/**
	 * 插入对象后返回自增主键值
	 * @param t
	 * @return
	 */
	public <T> int insertBackKeys(T t);
	
	/**
	 * 插入单个对象 指定表名的
	 * 
	 * @param t
	 * @return
	 */
	public <T> int insert(String tableName,T t);

	/**
	 * 指量插入对象 指定表名的
	 * 
	 * @param list
	 * @return
	 */
	public <T> int[] insert(String tableName,List<T> list);
	/**
	 * 设置JdbcTemplate
	 * @param jdbcTemplate
	 * @return
	 */
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate);
	
	/**
	 * 获取JdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate();
	/**
	 * 执行更新sql
	 * 
	 * @param sql
	 * @param parameter
	 * @return
	 */
	public int update(String sql, SqlParameter parameter);
	/**
	 * 批量执行sql
	 * @param sql
	 * @return
	 */
	public <T> int[] batchSql(String[] sqls);
	
	/**
	 * 分页查询 
	 * @param sql
	 * @param cls
	 * @param parameter
	 * @return
	 */
	public <T> Page<T> getListPage(String sql, Class<T> cls, SqlParameter parameter,final int pageSize, final int pageIndex);

}
