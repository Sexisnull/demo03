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
	private static final String SYSVIEW_SQL = "select t.iid,t.objectid,t.state,t.result,t.optresult,t.synctime,t.appid,t.codeid,t.objectname,t.operatetype,t.times,t.errorspec from jis_sysview t";
	
	//查询同步当前列表sql
	private static final String CURR_SYSVIEW_SQL = "select t.iid,t.objectid,t.state,t.result,t.optresult,t.synctime,t.appid,t.codeid,t.objectname,t.operatetype,t.times,t.errorspec from jis_sysview_current t";
	
	//删除同步实时列表sql
	private static final String DELETE_SYSVIEW_SQL = "delete from jis_sysview t where t.iid=?";
	
	//插入当前列表数据
	private static final String INSERT_CURRSYSVIEW_SQL = "INSERT INTO jis_sysview_current (objectid,state,result,optresult,synctime,appid,codeid,objectname,operatetype,times,errorspec ) SELECT t.objectid,t.state,t.result,t.optresult,t.synctime,t.appid,t.codeid,t.objectname,t.operatetype,t.times,t.errorspec from jis_sysview t where t.iid=?";
	
	//更新当前列表数据
	private static final String UPDATE_CURRSYSVIEW_SQL = "update jis_sysview_current t SET t.optresult = ? where t.iid=?";
	
	/**
	 *实时列表
	 * @param map
	 */
	public List<Map<String, Object>> selectSysView(){
		List<Map<String, Object>> map = jdbcTemplate.queryForList(SYSVIEW_SQL);
		return map;
	}
	
	/**
	 *当前列表
	 * @param map
	 */
	public List<Map<String, Object>> selectCurrSysView(){
		List<Map<String, Object>> map = jdbcTemplate.queryForList(CURR_SYSVIEW_SQL);
		return map;
	}
	
	/**
	 * 删除实时列表数据
	 * @param iid
	 */
	public void deleteSysView(int iid){
		jdbcTemplate.update(DELETE_SYSVIEW_SQL, new Object[]{iid});
	}
	
	/**
	 * 插入实时列表数据到当前列表数据
	 * @param iid
	 */
	public void insertSysViewCurr(int iid){
		jdbcTemplate.update(INSERT_CURRSYSVIEW_SQL, new Object[]{iid});
	}
	
	/**
	 * 更新当前列表数据
	 * @param iid
	 */
	public void updateSysViewCurr(int iid,int optresult){
		jdbcTemplate.update(UPDATE_CURRSYSVIEW_SQL, new Object[]{optresult,iid});
	}
}
