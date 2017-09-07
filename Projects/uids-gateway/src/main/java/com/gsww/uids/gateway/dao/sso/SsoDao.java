package com.gsww.uids.gateway.dao.sso;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.util.SpringContextHolder;

public class SsoDao extends JdbcTemplateDao{
	protected Logger logger = Logger.getLogger(getClass());
	
	public String findUrlByAppmark(String appmark){
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ssourl FROM jis_application where mark = ? ");
		
		Map<String,Object> map = jdbcTemplate.queryForMap(sql.toString(), appmark);
		if(map.get("ssourl") != null){
			return String.valueOf(map.get("ssourl"));
		}else{
			return "";
		}
	}
	
}
