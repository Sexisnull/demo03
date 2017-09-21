package com.gsww.uids.gateway.dao.outsideuser;

import java.util.List;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.util.ConvertSqlToDtoList;

public class OutsideUserDAO extends JdbcTemplateDao {
	// 通过AliAccessToken查找个人用户信息
	private static final String findByAccessToken = "select * FROM complat_outsideuser where aliAccessToken = ?  AND opersign!=3 ";
	private static final String findByLoginName = "select *FROM complat_outsideuser where loginname = ?  AND opersign<>3 ";
	private static final String findByMobile = "select *FROM complat_outsideuser where cellPhoneNum = ?  AND opersign<>3 ";
	private static final String findByIdCard = "select *FROM complat_outsideuser where IdCard = ?  AND opersign<>3 ";
	
	@SuppressWarnings("unchecked")
	public OutsideUser findByAccessToken(String accessToken) {
		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByAccessToken,
				new Object[] { accessToken }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() >= 1) {
			return list.get(0);
		}
		return null;
	}
	
	public OutsideUser findByLoginName(String loginName) {
		
		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByLoginName,
				new Object[] { loginName }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}
	
	public OutsideUser findByMobile(String cellPhoneNum) {

		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByMobile,
				new Object[] { cellPhoneNum }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}

	public OutsideUser findByIdCard(String IdCard) {
		List<OutsideUser> list = (List<OutsideUser>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByIdCard,
				new Object[] { IdCard }, "com.gsww.uids.gateway.entity.OutsideUser");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}
}
