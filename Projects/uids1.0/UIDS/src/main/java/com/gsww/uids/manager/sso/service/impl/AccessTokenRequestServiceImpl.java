package com.gsww.uids.manager.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.common.dao.HibernateBaseDao;
import com.gsww.common.util.StringUtil;
import com.gsww.uids.manager.sso.entity.AccessTokenRequest;
import com.gsww.uids.manager.sso.service.AccessTokenRequestService;

/**
 * accessToken请求相关的业务层实现
 * 
 * @author taolm
 *
 */
@Service
public class AccessTokenRequestServiceImpl implements AccessTokenRequestService {

	@Autowired
	private HibernateBaseDao hibernateBaseDao;

	@Override
	@Transactional
	public AccessTokenRequest persist(AccessTokenRequest obj) {
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
			hibernateBaseDao.deleteById(AccessTokenRequest.class, id);
		}		
	}

	@Override
	public void remove(AccessTokenRequest obj) {
		if ( obj != null ) {
			hibernateBaseDao.delete(obj);	
		}
	}
}
