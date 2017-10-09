package com.gsww.uids.gateway.dao.sysview;

import java.util.Map;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;

public class SysViewDetailDao extends JdbcTemplateDao{
	protected Logger logger = Logger.getLogger(getClass());	
	
	//查询同步明细
	private static final String FIND_DETAIL_SQL = "SELECT * FROM jis_sysview_detail where transcation_id = ?";
	
	//更新同步明细
	private static final String UPDATE_DETAIL_SQL = "update jis_sysview_detail t SET t.respmsg = ? where t.transcation_id=?";
		
	public Map<String,Object> findDetailById(String transcationId){
		Map<String,Object> map = jdbcTemplate.queryForMap(FIND_DETAIL_SQL, new Object[]{transcationId});
		return map;
	}
	
	public void updateSysViewCurr(String transcationId,String respmsg){
		jdbcTemplate.update(UPDATE_DETAIL_SQL, new Object[]{respmsg,transcationId});
	}
}
