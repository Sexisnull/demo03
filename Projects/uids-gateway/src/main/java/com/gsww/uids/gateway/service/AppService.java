package com.gsww.uids.gateway.service;

import com.gsww.uids.gateway.dao.application.ApplicationDao;
import com.gsww.uids.gateway.entity.Application;
import com.gsww.uids.gateway.util.SpringContextHolder;
import com.gsww.uids.gateway.util.StringHelper;

/**
 * AppService
 * 
 * @author zcc
 *
 */
public class AppService {
	private static ApplicationDao applicationDAO;
	static {
		applicationDAO = SpringContextHolder.getBean("applicationDao");
	}

	// 通过应用的mark查询子系统应用信息
	public Application findByMark(String mark) {
		if (!(StringHelper.isNotBlack(mark))) {
			return null;
		} else {
			Application app = applicationDAO.getApplicatioonByMark(mark);
			if (app != null) {
				return app;
			} else {
				return null;
			}
		}
	}
}
