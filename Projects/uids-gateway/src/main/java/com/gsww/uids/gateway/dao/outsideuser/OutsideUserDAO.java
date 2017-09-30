package com.gsww.uids.gateway.dao.outsideuser;

import java.util.List;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.util.ConvertSqlToDtoList;

/**
 * OutsideUserDAO
 * 
 * @author zcc
 *
 */
public class OutsideUserDao extends JdbcTemplateDao {
	// 通过AliAccessToken查找个人用户信息
	private static final String findByAliUserId = "select * FROM complat_outsideuser where aliUserId = ?  AND opersign!=3 ";
	private static final String saveAliUserId = "update complat_outsideuser set aliUserId=? where iid=? ";
	// 通过openId查找个人用户信息
	private static final String findByWeChatOpenId = "select * FROM complat_outsideuser where weChatOpenId = ?  AND opersign!=3 ";
	private static final String saveWeChatOpenId = "update complat_outsideuser set weChatOpenId=? where iid=? ";
	private static final String findByQQOpenId = "select * FROM complat_outsideuser where QQOpenId = ?  AND opersign!=3 ";
	private static final String saveQQOpenId = "update complat_outsideuser set QQOpenId=? where iid=? ";
	private static final String findByLoginName = "select *FROM complat_outsideuser where loginname = ?  AND opersign<>3 ";
	private static final String findByMobile = "select *FROM complat_outsideuser where mobile = ?  AND opersign<>3 ";
	private static final String findByIdCard = "select *FROM complat_outsideuser where papersnumber = ?  AND opersign<>3 ";

	@SuppressWarnings("unchecked")
	public OutsideUser findByQQOpenId(String QQOpenId) {
		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByQQOpenId,
				new Object[] { QQOpenId }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() >= 1) {
			return list.get(0);
		}
		return null;
	}

	public int saveQQOpenId(String QQOpenId, String iid) {
		int a = 0;
		a = jdbcTemplate.update(saveQQOpenId, new Object[] { QQOpenId, iid });
		return a;
	}

	@SuppressWarnings("unchecked")
	public OutsideUser findByWeChatOpenId(String weChatOpenId) {
		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByWeChatOpenId,
				new Object[] { weChatOpenId }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() >= 1) {
			return list.get(0);
		}
		return null;
	}

	public int saveWeChatOpenId(String weChatOpenId, String iid) {
		int a = 0;
		a = jdbcTemplate.update(saveWeChatOpenId, new Object[] { weChatOpenId, iid });
		return a;
	}

	@SuppressWarnings("unchecked")
	public OutsideUser findByUserId(String aliUserId) {
		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByAliUserId,
				new Object[] { aliUserId }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() >= 1) {
			return list.get(0);
		}
		return null;
	}

	public int saveAliUserId(String aliUserId, String iid) {
		int a = 0;
		a = jdbcTemplate.update(saveAliUserId, new Object[] { aliUserId, iid });
		return a;
	}

	@SuppressWarnings("unchecked")
	public OutsideUser findByLoginName(String loginName) {

		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByLoginName,
				new Object[] { loginName }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public OutsideUser findByMobile(String cellPhoneNum) {

		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByMobile,
				new Object[] { cellPhoneNum }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public OutsideUser findByIdCard(String IdCard) {
		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByIdCard,
				new Object[] { IdCard }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}
}
