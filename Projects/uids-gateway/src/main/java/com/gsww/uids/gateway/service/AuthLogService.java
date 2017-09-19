package com.gsww.uids.gateway.service;

import com.gsww.uids.gateway.dao.authlog.AuthLogDao;
import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.gsww.uids.gateway.util.StringHelper;

public class AuthLogService {
	private static AuthLogDao authLogDao;
	static {
		authLogDao = SpringContextHolder.getBean("authLogDao");
	}

	public JisAuthLog findByTicket(String ticket, int userType) {
		if ((!StringHelper.isNotBlack(ticket)) || (userType == 0)) {
			return null;
		} else {
			JisAuthLog jisAuthLog = authLogDao.getJisAuthLogByTicket(ticket, userType);
			return jisAuthLog;
		}
	}

	public JisAuthLog findByToken(String token, int userType) {

		if ((!StringHelper.isNotBlack(token)) || (userType == 0)) {
			return null;
		} else {
			JisAuthLog jisAuthLog = authLogDao.getJisAuthLogByTicket(token, userType);
			return jisAuthLog;
		}

	}

}
