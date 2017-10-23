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
	
	//查询实时同步列表sql
	private static final String FIND_SYSVIEW_SQL = "select t.iid,t.objectid,t.state,t.result,t.optresult,t.synctime,t.appid,t.codeid,t.objectname,t.operatetype,t.times,t.errorspec,transcation_id from jis_sysview t";
	
	//查询当前同步列表sql
	private static final String FIND_SYSVIEW_CURR_SQL = "select t.iid,t.objectid,t.state,t.result,t.optresult,t.synctime,t.appid,t.codeid,t.objectname,t.operatetype,t.times,t.errorspec,transcation_id from jis_sysview_current t where t.optresult = ?";
	
	//删除实时同步列表sql
	private static final String DELETE_SYSVIEW_SQL = "delete from jis_sysview where iid=?";
	
	//插入当前同步列表sql
	private static final String INSERT_SYSVIEW_CURR_SQL = "INSERT INTO jis_sysview_current (objectid,state,result,optresult,synctime,appid,codeid,objectname,operatetype,times,errorspec,transcation_id ) SELECT t.objectid,t.state,t.result,t.optresult,t.synctime,t.appid,t.codeid,t.objectname,t.operatetype,t.times,t.errorspec,t.transcation_id from jis_sysview t where t.iid=?";
	
	//更新当前同步列表sql
	private static final String UPDATE_SYSVIEW_CURR_SQL = "update jis_sysview_current t SET t.optresult = ? where t.transcation_id=?";
	
	//删除当前同步列表sql
	private static final String DELETE_SYSVIEW_CURR_SQL = "delete from jis_sysview_current where iid=?";
		
	//插入历史同步列表sql
	private static final String INSERT_SYSVIEW_HIS_SQL = "INSERT INTO jis_sysview_history (objectid,state,result,optresult,synctime,appid,codeid,objectname,operatetype,times,errorspec,transcation_id ) SELECT t.objectid,t.state,t.result,t.optresult,t.synctime,t.appid,t.codeid,t.objectname,t.operatetype,t.times,t.errorspec,t.transcation_id from jis_sysview_current t where t.iid=? ";
	
	/**
	 *查询实时列表
	 */
	public List<Map<String, Object>> findSysView(){
		List<Map<String, Object>> map = jdbcTemplate.queryForList(FIND_SYSVIEW_SQL);
		return map;
	}
	
	/**
	 * 查询所有同步成功的数据
	 */
	public List<Map<String, Object>> findCurrSysViewByOptResult(int optResult){
		List<Map<String, Object>> map = jdbcTemplate.queryForList(FIND_SYSVIEW_CURR_SQL,new Object[]{optResult});
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
		jdbcTemplate.update(INSERT_SYSVIEW_CURR_SQL, new Object[]{iid});
	}
	
	/**
	 * 更新当前列表数据
	 * @param iid
	 */
	public void updateSysViewCurr(String transcation_id,int optresult){
		jdbcTemplate.update(UPDATE_SYSVIEW_CURR_SQL, new Object[]{optresult,transcation_id});
	}
	
	/**
	 * 删除当前列表数据
	 * @param iid
	 */
	public void deleteSysViewCurr(int iid){
		jdbcTemplate.update(DELETE_SYSVIEW_CURR_SQL, new Object[]{iid});
	}
	
	/**
	 * 插入当前列表数据到历史列表数据
	 * @param iid
	 */
	public void insertSysViewHis(int iid){
		jdbcTemplate.update(INSERT_SYSVIEW_HIS_SQL, new Object[]{iid});
	}
}
