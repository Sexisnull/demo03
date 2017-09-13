package com.gsww.uids.gateway.dao.sysview;

import java.util.Map;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;

public class SysViewDetailDao extends JdbcTemplateDao{
	protected Logger logger = Logger.getLogger(getClass());	
	
	//查询同步明细
	private static final String FIND_DETAIL_SQL = "SELECT * FROM jis_sysview_detail where iid = ?";
	
	//更新同步明细
	private static final String UPDATE_DETAIL_SQL = "update jis_sysview_detail t SET t.respmsg = ? where t.iid=?";
		
	public Map<String,Object> findDetailById(int iid){
		Map<String,Object> map = jdbcTemplate.queryForMap(FIND_DETAIL_SQL, new Object[]{iid});
		return map;
	}
	
	public void updateSysViewCurr(int iid,String respmsg){
		jdbcTemplate.update(UPDATE_DETAIL_SQL, new Object[]{respmsg,iid});
	}
}
