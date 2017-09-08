package com.gsww.uids.gateway.dao.sysview;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.entity.ApplicationMule;
import com.gsww.uids.gateway.util.SpringContextHolder;

public class SysViewDao extends JdbcTemplateDao{
	protected Logger logger = Logger.getLogger(getClass());	
	
	//查询同步实时列表sql
	String sysViewSQL = "select t.objectid,t.state,t.result,t.optresult,t.synctime,t.appid,t.codeid,t.objectname,t.operatetype,t.times,t.errorspec from jis_sysview t";
	
	//查询同步当前列表sql
	String currSysViewSQL = "select t.objectid,t.state,t.result,t.optresult,t.synctime,t.appid,t.codeid,t.objectname,t.operatetype,t.times,t.errorspec from jis_sysview_current t";
	
	//删除同步实时列表sql
	String deleteSysViewSQL = "delete from jis_sysview t where t.iid=?";
	
	/**
	 *实时列表
	 * @param map
	 */
	public List<Map<String, Object>> selectSysView(){
		List<Map<String, Object>> map = jdbcTemplate.queryForList(sysViewSQL);
		return map;
	}
	
	/**
	 *当前列表
	 * @param map
	 */
	public List<Map<String, Object>> selectCurrSysView(){
		List<Map<String, Object>> map = jdbcTemplate.queryForList(currSysViewSQL);
		return map;
	}
	
	/**
	 * 删除实时同步数据
	 * @param iid
	 */
	public void deleteSysView(String iid){
		jdbcTemplate.update(deleteSysViewSQL, new Object[]{iid});
	}
}
