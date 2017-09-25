package com.gsww.uids.gateway.dao.authlog;

import java.util.List;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.util.ConvertSqlToDtoList;
/**
 * AuthLogDao
 * @author zcc
 *
 */
public class AuthLogDao extends JdbcTemplateDao {
	// 通过ticket查询政府用户
	private static final String FIND_BY_TICKET = "SELECT * FROM jis_authlog WHERE ticket =? and userType=? ";
	// 通过token查询政府用户
	private static final String FIND_BY_Token = "SELECT * FROM jis_authlog WHERE token =? and userType=? ";

	public JisAuthLog getJisAuthLogByTicket(String ticket, int userType) {

		@SuppressWarnings("unchecked")
		List<JisAuthLog> list = (List<JisAuthLog>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, FIND_BY_TICKET,
				new Object[] { ticket, userType }, "com.gsww.uids.gateway.entity.JisAuthLog");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}

	public JisAuthLog getJisAuthLogByToken(String token, int userType) {

		@SuppressWarnings("unchecked")
		List<JisAuthLog> list = (List<JisAuthLog>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, FIND_BY_Token,
				new Object[] { token, userType }, "com.gsww.uids.gateway.entity.JisAuthLog");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}
}
