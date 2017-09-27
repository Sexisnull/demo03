package com.gsww.uids.gateway.service;

import com.gsww.uids.gateway.dao.authlog.AuthLogDao;
import com.gsww.uids.gateway.entity.JisAuthLog;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.gsww.uids.gateway.util.StringHelper;

/**
 * AuthLogService
 * 
 * @author zcc
 *
 */
public class AuthLogService {
	private static AuthLogDao authLogDao;
	static {
		authLogDao = SpringContextHolder.getBean("authLogDao");
	}

	public JisAuthLog findByTicket(String ticket, int userType) {
		if ((StringHelper.isNotBlack(ticket)) || (userType != 0)) {
			JisAuthLog jisAuthLog = authLogDao.getJisAuthLogByTicket(ticket, userType);
			if (jisAuthLog == null) {
				// System.out.println("jisAuthLog==null!!!");
			} else {
			}
			return jisAuthLog;
		} else {
			return null;
		}
	}

	public JisAuthLog findByToken(String token, int userType) {

		if ((!StringHelper.isNotBlack(token)) || (userType == 0)) {
			return null;
		} else {
			JisAuthLog jisAuthLog = authLogDao.getJisAuthLogByToken(token, userType);
			return jisAuthLog;
		}

	}

}
