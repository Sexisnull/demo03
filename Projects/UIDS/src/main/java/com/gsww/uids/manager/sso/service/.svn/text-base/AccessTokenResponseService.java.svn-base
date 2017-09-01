package com.gsww.uids.manager.sso.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gsww.uids.manager.sso.entity.AccessTokenResponse;

/**
 * accessToken应答相关的业务层接口
 * 
 * @author taolm
 *
 */
@Service
public interface AccessTokenResponseService {
	
	/**
	 * 持久化对象，即保存
	 * 
	 * @param obj
	 * @return
	 */
	AccessTokenResponse persist(AccessTokenResponse obj);
	
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
	void remove(AccessTokenResponse obj);
	
	/**
	 * 根据accessToken获取记录
	 * 
	 * @param accessToken
	 * @return
	 */
	AccessTokenResponse getByAccessToken(String accessToken);
	
	/**
	 * accessToken是否有效（存在，且没有过期）
	 * 
	 * @param accessToken
	 * @param err
	 * @return
	 */
	boolean isAccessTokenValid(String accessToken, StringBuilder err);
	
	/**
	 * 重载 boolean isAccessTokenValid(String accessToken, StringBuilder err)
	 * 
	 * @param obj
	 * @param err
	 * @return
	 */
	boolean isAccessTokenValid(AccessTokenResponse obj, StringBuilder err);
	
	/**
	 * 获取不会再使用的记录：accessToken和refreshToken都过期
	 * 
	 * @return
	 */
	List<AccessTokenResponse> getInvalid();
	
	/**
	 * 刷新accessToken
	 * 
	 * @param obj
	 * @param newAccessToken
	 * @param newRefreshToken
	 */
	void refreshAccessToken(AccessTokenResponse obj, String newAccessToken, String newRefreshToken);
	
	/**
	 * 根据refreshToken查找记录
	 * 
	 * @param refreshToken
	 * @return
	 */
	AccessTokenResponse getByRefreshToken(String refreshToken);
	
	/**
	 * 退出：即设置accessToken的有效期为0
	 * 
	 * @param obj
	 */
	void logout(AccessTokenResponse obj);
}
