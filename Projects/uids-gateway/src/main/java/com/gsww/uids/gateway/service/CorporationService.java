package com.gsww.uids.gateway.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.gsww.uids.gateway.dao.corporation.CorporationDao;
import com.gsww.uids.gateway.entity.Corporation;
import com.gsww.uids.gateway.exception.LoginException;
import com.gsww.uids.gateway.util.Md5Util;
import com.gsww.uids.gateway.util.SpringContextHolder;

/**
 * CorporationService
 * 
 * @author zcc
 * 
 */
public class CorporationService {
	private static CorporationDao corporationDao;
	static {
		corporationDao = SpringContextHolder.getBean("corporationDao");
	}

	public Corporation findByLoginName(String loginName) {
		Corporation corporation = null;
		corporation = corporationDao.findByLoginName(loginName);
		return corporation;
	}

	public Corporation findByRegNumber(String regnumber) {
		if (("".equals(regnumber)) || (regnumber.length() == 0)) {
			return null;
		}
		return corporationDao.findByRegNumber(regnumber);
	}

	public List<Corporation> findByOrgNumber(String orgnumber) {
		return corporationDao.findByOrgNumber(orgnumber);
	}

	public synchronized Corporation checkUserLogin(String loginName,
			String pwd, String ip) throws LoginException {
		Corporation corporation = null;
		corporation = corporationDao.findByLoginName(loginName);
		if (corporation == null) {
			corporation = corporationDao.findByLoginName(loginName);
			if (corporation == null) {
				corporation = findByRegNumber(loginName);
				if (corporation == null) {
					List<Corporation> corporationList = findByOrgNumber(loginName);
					if (CollectionUtils.isNotEmpty(corporationList)) {
						corporation = (Corporation) corporationList.get(0);
					}
				}
			}
		}
		if (corporation != null) {
			if (corporation.getEnable().intValue() == 0) {
				throw new LoginException("login.isnotallowed");
			}
			String password = Md5Util.md5decode(corporation.getPwd());
			if (!password.equals(pwd)) {
				corporation = null;
			} else {
				corporation.setLoginip(ip);
				corporation.setLoginTime(new Date());
			}
		}
		return corporation;
	}
}