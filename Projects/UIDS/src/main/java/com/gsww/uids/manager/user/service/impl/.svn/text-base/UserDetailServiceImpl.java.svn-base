package com.gsww.uids.manager.user.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.util.StringUtil;
import com.gsww.uids.manager.user.entity.UserDetail;
import com.gsww.uids.manager.user.service.UserDetailService;

/**
 * 用户详细信息的业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	private HibernateBaseDao hibernateBaseDao;
	
	@Override
	@Transactional
	public boolean saveOrUpdate(UserDetail info) {
		if (StringUtil.isBlank(info.getUuid())) {
			hibernateBaseDao.save(info);			
		} else {
			hibernateBaseDao.update(info);
		}
		return true;
	}
	
	@Override
	public UserDetail findById(String id) {
		if (StringUtil.isBlank(id)) {
			return new UserDetail();
		}
		return hibernateBaseDao.getById(UserDetail.class, id);
	}
}
