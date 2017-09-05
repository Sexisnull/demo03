package com.gsww.uids.manager.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.util.StringUtil;
import com.gsww.uids.manager.sso.entity.AuthcodeRequest;
import com.gsww.uids.manager.sso.service.AuthcodeRequestService;

/**
 * authcode请求相关的业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class AuthcodeRequestServiceImpl implements AuthcodeRequestService {

	@Autowired
	private HibernateBaseDao hibernateBaseDao;

	@Override
	@Transactional
	public AuthcodeRequest persist(AuthcodeRequest obj) {
		hibernateBaseDao.save(obj);
		return obj;
	}

	@Override
	@Transactional
	public void batchRemove(String ids) {
		if ( StringUtil.isBlank(ids) ) {
			return ;
		}
		
		for (String id : ids.split(",")) {
			hibernateBaseDao.deleteById(AuthcodeRequest.class, id);
		}
	}

	@Override
	public void remove(AuthcodeRequest obj) {
		if ( obj != null ) {
			hibernateBaseDao.delete(obj);	
		}
	}
}
