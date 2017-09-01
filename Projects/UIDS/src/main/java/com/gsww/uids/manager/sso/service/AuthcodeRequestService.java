package com.gsww.uids.manager.sso.service;

import org.springframework.stereotype.Service;

import com.gsww.uids.manager.sso.entity.AuthcodeRequest;

/**
 * autocode请求相关的业务层接口
 * 
 * @author taolm
 *
 */
@Service
public interface AuthcodeRequestService {

	/**
	 * 持久化对象，即保存
	 * 
	 * @param obj
	 * @return
	 */
	AuthcodeRequest persist(AuthcodeRequest obj);
	
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
	void remove(AuthcodeRequest obj);
}
