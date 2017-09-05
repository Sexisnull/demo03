package com.gsww.uids.manager.setting.service;

import org.springframework.stereotype.Service;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.setting.entity.ActiveAccount;

/**
 * 在线用户业务层接口
 * 
 * @author sunbw
 *
 */
@Service
public interface ActiveAccountService {
	
	/**
	 * 分页获取在线用户
	 * 	
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public PageObject<ActiveAccount> findPage(Integer currentPage, Integer pageSize);
}
