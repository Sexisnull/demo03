package com.gsww.uids.gateway.dao.corAuth;

import java.util.Map;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;

public class CorAuthDao extends JdbcTemplateDao {
	protected Logger logger = Logger.getLogger(getClass());
	StringBuffer sql = new StringBuffer();

	/* 根据返回的票据信息获取令牌信息 */
	public String findTokenByTicket(String ticket) {

		sql.append("select token FROM complat_corporation where mark = ? ");

		Map<String, Object> map = jdbcTemplate.queryForMap(sql.toString(), ticket);
		if (map.get("token") != null) {
			return String.valueOf(map.get("token"));
		} else {
			return "";
		}
	}

	/* 使用令牌获取用户详细信息 */
	public String findUserByToken(String token) {

		sql.append("select *FROM complat_corporation where token = ? ");

		Map<String, Object> map = jdbcTemplate.queryForMap(sql.toString(), token);
		if (map.size() > 0) {
			return String.valueOf(map);
		} else {
			return "";
		}
	}
	/* 接入资源使用刷新令牌，更新将要过期的接入资源令牌，获取最新的接入资源令牌 */

	/* 该接口是实现接入资源使用用户名和密码方式认证用户信息 */
	public String findUserByLoginNameAndPassWord(String loginName, String passWord) {

		sql.append("select *FROM complat_corporation where loginname = ? and pwd=? ");

		Map<String, Object> map = jdbcTemplate.queryForMap(sql.toString(), loginName, passWord);
		if (map.size() > 0) {
			return String.valueOf(map);
		} else {
			return "";
		}
	}

}
