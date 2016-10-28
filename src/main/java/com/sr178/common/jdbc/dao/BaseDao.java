package com.sr178.common.jdbc.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;


import com.sr178.common.jdbc.Jdbc;
import com.sr178.common.jdbc.SqlParameter;
import com.sr178.common.jdbc.bean.IPage;
import com.sr178.common.jdbc.bean.SqlParamBean;

public abstract class BaseDao<T> {
	
    private Class<T> cls;
    
    private String table;
    
    @SuppressWarnings("unchecked")
	public BaseDao(){
    	Type genType = getClass().getGenericSuperclass();   
    	Type[] params = ((ParameterizedType) genType).getActualTypeArguments();   
    	cls =  (Class<T>)params[0];
    	table = className2TableName(cls.getSimpleName());
    }
    
	private String className2TableName(String className) {
		char[] chs = className.toCharArray();

		StringBuffer tableName = new StringBuffer();
		tableName.append(chs[0]);
		for (int i = 1; i < chs.length; i++) {
			byte bt = (byte) chs[i];
			if (bt >= 65 && bt <= 90) {
				tableName.append("_");
				tableName.append(chs[i]);
			} else {
				tableName.append(chs[i]);
			}
		}
		return tableName.toString().toLowerCase();
	}
	
	private String generatorWhere(SqlParameter parameter,SqlParamBean... beans){
		StringBuffer where = new StringBuffer();
		if(beans!=null&&beans.length>0){
    		where.append(" where ");
    		boolean isFirst = true;
    		for(SqlParamBean param:beans){
    			if(isFirst){
    				where.append(param.getKey()+"=?");
    			}else{
    				where.append(" "+param.getType()+" "+param.getKey()+"=?");
    			}
    			isFirst = false;
    			parameter.setObject(param.getValue());
    		}
    	}
		return where.toString();
	}
    
	
	
	
    public List<T> getAll(){
    	String sql = "select * from "+table;
    	return getJdbc().getList(sql, cls, null);
    }
    
    public List<T> getAllOrder(String orderStr){
    	String sql = "select * from "+table+ " "+orderStr;
    	return getJdbc().getList(sql, cls, null);
    }
	
    public List<T> getList(SqlParamBean... beans){
    	SqlParameter parameter = SqlParameter.Instance();
    	String sql = "select * from "+table;
    	sql = sql + generatorWhere(parameter, beans);
    	return getJdbc().getList(sql, cls, parameter);
    }
    
    public List<T> getList(String orderBy,SqlParamBean... beans){
    	SqlParameter parameter = SqlParameter.Instance();
    	String sql = "select * from "+table;
    	sql = sql + generatorWhere(parameter, beans);
    	sql = sql +" "+orderBy;
    	return getJdbc().getList(sql, cls, parameter);
    }
    
    public T get(SqlParamBean... beans){
    	SqlParameter parameter = SqlParameter.Instance();
    	String sql = "select * from "+table;
    	sql = sql + generatorWhere(parameter, beans);
    	sql = sql + " limit 1";
    	return getJdbc().get(sql, cls, parameter);
    }
    
    public T getFirstOne(String orderBy){
    	String sql = "select * from "+table+" "+orderBy;
    	sql = sql + " limit 1";
    	return getJdbc().get(sql, cls, null);
    }
    
    public IPage<T> getPageList(int pageIndex,int pageSize,SqlParamBean... beans){
    	SqlParameter parameter = SqlParameter.Instance();
    	String sql = "select * from "+table;
    	sql = sql + generatorWhere(parameter, beans);
    	return getJdbc().getListPage(sql, cls, parameter, pageSize, pageIndex);
    }
    
    public IPage<T> getPageList(int pageIndex,int pageSize,String orderBy){
    	SqlParameter parameter = SqlParameter.Instance();
    	String sql = "select * from "+table;
    	sql = sql +" "+orderBy;
    	return getJdbc().getListPage(sql, cls, parameter, pageSize, pageIndex);
    }
    
    public IPage<T> getPageList(String backField,int pageIndex,int pageSize,String orderBy){
    	SqlParameter parameter = SqlParameter.Instance();
    	String sql = "select "+backField+" from "+table;
    	sql = sql +" "+orderBy;
    	return getJdbc().getListPage(sql, cls, parameter, pageSize, pageIndex);
    }
    
    public IPage<T> getPageList(int pageIndex,int pageSize,String orderBy,SqlParamBean... beans){
    	SqlParameter parameter = SqlParameter.Instance();
    	String sql = "select * from "+table;
    	sql = sql + generatorWhere(parameter, beans);
    	sql = sql +" "+orderBy;
    	return getJdbc().getListPage(sql, cls, parameter, pageSize, pageIndex);
    }
    public boolean add(T t){
    	return getJdbc().insert(t)>0;
    }
    public int addBackKey(T t){
    	return getJdbc().insertBackKeys(t);
    }
    public long addBackKeyLong(T t){
    	return getJdbc().insertBackKeysLong(t);
    }
    public boolean delete(SqlParamBean... beans){
    	SqlParameter parameter =SqlParameter.Instance();
    	String sql = "delete from "+table;
    	sql = sql + generatorWhere(parameter, beans);
    	return getJdbc().update(sql,parameter)>0;
    }
	public String getTable() {
		return table;
	}
	
	public abstract Jdbc getJdbc();
}
