package com.gsww.uids.gateway.dao.application;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.entity.ApplicationMule;
import com.gsww.uids.gateway.util.SpringContextHolder;

public class ApplicationDao extends JdbcTemplateDao{
	protected Logger logger = Logger.getLogger(getClass());	
	
	//注册应用SQL
	String appRegisterSql = "INSERT INTO jis_application(name,encryptkey,appurl,mark,encrypttype) VALUES(?,?,?,?,?)";
	
	/**
	 *应用注册
	 * @param map
	 */
	public void appRegister(ApplicationMule app){
		int type = 0;
		if(app.getEncrypttype() != null && !"".equals(app.getEncrypttype())){
			type = Integer.valueOf((String) app.getEncrypttype());
		}
		jdbcTemplate.update(appRegisterSql, new Object[]{app.getAppname(),app.getEnckey(),app.getSysurl(),app.getLdapurl(),type});
	}
}
