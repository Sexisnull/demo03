package com.gsww.uids.manager.sso.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.sso.entity.AuthcodeResponse;

/**
 * authcode应答相关的业务层接口
 * 
 * @author taolm
 *
 */
@Service
public interface AuthcodeResponseService {

	/**
	 * 持久化对象，即保存
	 * 
	 * @param obj
	 * @return
	 */
	AuthcodeResponse persist(AuthcodeResponse obj);
	
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
	void remove(AuthcodeResponse obj);
	
	/**
	 * 根据authcode获取记录
	 * 
	 * @param authcode
	 * @return
	 */
	AuthcodeResponse getByAuthcode(String authcode);
	
	/**
	 * 根据authcode查找账号
	 * 
	 * @param authcode
	 * @return
	 */
	Account getAccountByAuthcode(String authcode);
	
	/**
	 * authcode能否使用（因为只能使用一次，所以如果使用过，就不能再使用了）
	 * 
	 * @param authcode
	 * @param err
	 * @return
	 */
	boolean canUseAuthcode(String authcode, StringBuilder err);
	
	/**
	 * 重载 boolean canUseAuthcode(String authcode, StringBuilder err)
	 * 
	 * @param obj
	 * @param err
	 * @return
	 */
	boolean canUseAuthcode(AuthcodeResponse obj, StringBuilder err);
	
	/**
	 * 使用authcode（在使用之前，一定要先判断能否使用）
	 * 
	 * @param authcode
	 */
	void useAuthcode(String authcode);
	
	/**
	 * 重载void useAuthcode(String authcode)
	 * 
	 * @param authcode
	 */
	void useAuthcode(AuthcodeResponse obj);
	
	/**
	 * 获取过期的或者使用过的授权码记录
	 * 
	 * @return
	 */
	List<AuthcodeResponse> getInvalid();
}
