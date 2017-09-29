package com.gsww.uids.gateway.dao.corporation;

import java.util.List;

import org.apache.log4j.Logger;

import com.gsww.uids.gateway.dao.JdbcTemplateDao;
import com.gsww.uids.gateway.entity.Corporation;
import com.gsww.uids.gateway.util.ConvertSqlToDtoList;
/**
 * CorporationDAO
 * @author zcc
 *
 */
public class CorporationDao extends JdbcTemplateDao {
	protected Logger logger = Logger.getLogger(getClass());
	// findByLoginName
	private static final String findByLoginName = "select *FROM complat_corporation where loginname = ?  AND opersign<>3";
	// findByRegNumber
	private static final String findByRegNumber = "select *FROM complat_corporation where RegNumber = ?  AND opersign<>3";
	// findByOrgNumber
	private static final String findByOrgNumber = "select *FROM complat_corporation where orgnumber = ?  AND opersign<>3";

	@SuppressWarnings("unchecked")
	public Corporation findByLoginName(String loginName) {
		List<Corporation> list = (List<Corporation>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByLoginName,
				new Object[] { loginName }, "com.gsww.uids.gateway.entity.Corporation");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;

	}

	@SuppressWarnings("unchecked")
	public Corporation findByRegNumber(String regnumber) {
		List<Corporation> list = (List<Corporation>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByRegNumber,
				new Object[] { regnumber }, "com.gsww.uids.gateway.entity.Corporation");
		if (list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public List<Corporation> findByOrgNumber(String orgnumber) {
		if (("".equals(orgnumber)) || (orgnumber.length() == 0)) {
			return null;
		}
		List<Corporation> list = (List<Corporation>) ConvertSqlToDtoList.ExeSQL2List(jdbcTemplate, findByOrgNumber,
				new Object[] { orgnumber }, "com.gsww.uids.gateway.entity.Corporation");
		if (list != null) {
			return list;
		} else {
			return null;
		}
	}
}
