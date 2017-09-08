package com.gsww.uids.gateway.dao.group;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.util.SpringContextHolder;

public class GroupDao extends JdbcTemplateDao{
	protected Logger logger = Logger.getLogger(getClass());	
	
	//机构新增SQL
	String appRegisterSql = "INSERT INTO complat_group(codeid,name,) VALUES(?,?,?,?,?)";
	
	/**
	 *应用注册
	 * @param map
	 */
	public void appRegister(Map map){
		jdbcTemplate.update(appRegisterSql, new Object[]{});
	}
}
