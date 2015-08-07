package com.sr178.common.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

public class DataColumnBeanToRowMapper<T> extends BeanPropertyRowMapper<T> implements ParameterizedRowMapper<T> {

}
