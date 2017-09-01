package com.gsww.uids.manager.sso.service;

import org.springframework.stereotype.Service;

import com.gsww.uids.manager.sso.entity.AccessTokenRequest;

/**
 * accessToken请求相关的业务层接口
 * 
 * @author taolm
 *
 */
@Service
public interface AccessTokenRequestService {

	/**
	 * 持久化对象，即保存
	 * 
	 * @param obj
	 * @return
	 */
	AccessTokenRequest persist(AccessTokenRequest obj);
	
	/**
	 * 批量删除记录
	 * 
	 * @param ids
	 */
	void batchRemove(String ids);
	
	/**
	 * 删除记录
	 * 
	 * @param obj
	 */
	void remove(AccessTokenRequest obj);
}
