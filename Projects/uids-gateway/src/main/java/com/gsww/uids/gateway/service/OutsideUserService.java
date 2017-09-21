package com.gsww.uids.gateway.service;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import com.gsww.uids.gateway.dao.application.ApplicationDao;
import com.gsww.uids.gateway.dao.outsideuser.OutsideUserDao;
import com.gsww.uids.gateway.entity.OutsideUser;
import com.gsww.uids.gateway.exception.LoginException;
import com.gsww.uids.gateway.util.CacheUtil;
import com.gsww.uids.gateway.util.Md5Util;
import com.gsww.uids.gateway.util.SpringContextHolder;

public class OutsideUserService {
	private static OutsideUserDao outsideUserDAO;
	static {
		outsideUserDAO = SpringContextHolder.getBean("outsideUserDAO");
	}

	public OutsideUser findByLoginName(String loginName) {
		OutsideUser outsideUser = null;
		outsideUser = this.outsideUserDAO.findByLoginName(loginName);
		return outsideUser;
	}

	public OutsideUser findByMobile(String cellPhoneNum) {
		OutsideUser outsideUser = null;
		outsideUser = this.outsideUserDAO.findByMobile(cellPhoneNum);
		return outsideUser;
	}

	public OutsideUser findByIdCard(String IdCard) {
		OutsideUser outsideUser = null;
		outsideUser = this.outsideUserDAO.findByIdCard(IdCard);
		return outsideUser;
	}

	public synchronized OutsideUser checkUserLogin(String loginName, String pwd, String ip) throws LoginException {
		OutsideUser outsideUser = null;

		if (outsideUser == null) {
			outsideUser = this.outsideUserDAO.findByLoginName(loginName);
		}
		if (outsideUser != null) {
			if (outsideUser.getEnable().intValue() == 0) {
				throw new LoginException("login.isnotallowed");
			}
			String password = outsideUser.getPwd();
			if (password.equals(pwd)) {
				outsideUser.setLoginip(ip);
				outsideUser.setLogintime(new Date());
			} else {
				outsideUser = null;
			}
		}
		return outsideUser;
	}

}
