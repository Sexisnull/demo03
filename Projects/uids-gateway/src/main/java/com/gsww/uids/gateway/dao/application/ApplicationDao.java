package com.gsww.uids.gateway.dao.application;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.entity.Application;
import com.gsww.uids.gateway.entity.ApplicationMule;
import com.gsww.uids.gateway.util.ConvertSqlToDtoList;
/**
 * ApplicationDao
 * @author zcc
 *
 */
public class ApplicationDao extends JdbcTemplateDao {
	protected Logger logger = Logger.getLogger(getClass());

	// 注册应用SQL
	private static final String APP_REGISTER_SQL = "INSERT INTO jis_application(name,encryptkey,appurl,mark,encrypttype) VALUES(?,?,?,?,?)";
	// 查询应用
	private static final String SELECT_APP_SQL = "select * from jis_application t WHERE t.iid = ?";
	// 查询实时同步列表sql
	private static final String FIND_APP_SQL_BY_MARK = "SELECT * FROM JIS_APPLICATION WHERE MARK =?";

	/**
	 * 应用注册
	 * 
	 * @param map
	 */
	public void appRegister(ApplicationMule app) {
		int type = 0;
		if (app.getEncrypttype() != null && !"".equals(app.getEncrypttype())) {
			type = Integer.valueOf((String) app.getEncrypttype());
		}
		jdbcTemplate.update(APP_REGISTER_SQL,
				new Object[] { app.getAppname(), app.getEnckey(), app.getSysurl(), app.getLdapurl(), type });
	}

	public Map<String, Object> selectAppById(int iid) {
		Map<String, Object> map = jdbcTemplate.queryForMap(SELECT_APP_SQL, new Object[] { iid });
		return map;
	}

	@SuppressWarnings("unchecked")
	public Application getApplicatioonByMark(String mark) {
		List<Application> list = (List<Application>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, FIND_APP_SQL_BY_MARK,
				new Object[] { mark }, "com.gsww.uids.gateway.entity.Application");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

}
