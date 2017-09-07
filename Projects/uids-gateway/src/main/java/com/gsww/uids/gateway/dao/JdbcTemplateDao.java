package com.gsww.uids.gateway.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class JdbcTemplateDao {

	
	protected JdbcTemplate jdbcTemplate;
	
	
	/** 
	* 通过Spring容器注入Datasource 
	* 实例化JdbcTemplate,该类为主要操作数据库的类 
	* @param ds 
	*/  

	public void setDataSource(DataSource ds) {  
		this.jdbcTemplate = new JdbcTemplate(ds);  
	}  
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
}
