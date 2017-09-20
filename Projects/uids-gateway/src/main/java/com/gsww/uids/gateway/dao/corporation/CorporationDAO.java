package com.gsww.uids.gateway.dao.corporation;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.entity.Corporation;
import com.gsww.uids.gateway.util.ConvertSqlToDtoList;

public class CorporationDAO extends JdbcTemplateDao {
	protected Logger logger = Logger.getLogger(getClass());
	// findByLoginName
	private static final String findByLoginName = "select *FROM complat_corporation where loginName = ?  AND opersign<>3";
	// findByRegNumber
	private static final String findByRegNumber = "select *FROM complat_corporation where loginName = ?  AND opersign<>3";

	public Corporation findByLoginName(String loginName) {
		List<Corporation> list = (List<Corporation>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByLoginName,
				new Object[] { loginName }, "com.gsww.uids.gateway.entity.Corporation");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}

	public Corporation findByRegNumber(String regnumber) {
		List<Corporation> list = (List<Corporation>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByRegNumber,
				new Object[] { regnumber }, "com.gsww.uids.gateway.entity.Corporation");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	public List<Corporation> findByOrgNumber(String orgnumber) {
		if (("".equals(orgnumber)) || (orgnumber.length() == 0)) {
			return null;
		}
		StringBuffer sql = new StringBuffer();
		sql.append("select *FROM complat_corporation where regnumber = ?  AND opersign<>3 ");

		Map<String, Object> map = jdbcTemplate.queryForMap(sql.toString(), orgnumber);
		if (map != null) {
			return (List<Corporation>) map;
		} else {
			return null;
		}
	}
}
