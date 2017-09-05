package com.gsww.uids.manager.app.service;

import java.util.List;

import com.gsww.uids.manager.account.entity.Account;
import com.gsww.uids.manager.app.entity.AppResourceAuth;
import com.gsww.uids.manager.app.entity.Application;

public interface SourceAuthService {

	/**
	 * 资源的授权列表
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public String findPageBySource(Integer currentPage, Integer pageSize,String sourceId);
	
	/**
	 * 角色的授权列表
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	public String findPageByRole(Integer currentPage, Integer pageSize, String roleId);
	
	/**
	 * 获取授权信息对象
	 */
	public AppResourceAuth findSourceAuthById(String authId);
	
	/**
	 * 删除授权信息
	 */
	public boolean authDelete(String ids);
	
	/**
	 * 资源授权信息保存
	 */
	public boolean saveOrUpdateSourceAuth(AppResourceAuth sourceAuth); 
	
	/**
	 * 删除划分方式、机构、机构组、角色、资源前调用此方法来验证是否可以删除
	 *
	 * @param orgId 机构id
	 * @param orgGroupId 机构组id
	 * @param roleId 角色id
	 * @param sourceId 资源id
	 * @return
	 */
	public List<AppResourceAuth> findListByParames(String orgId,String orgGroupId,String roleId,String sourceId);
	
	/**
	 * 获取用户的所有统一身份认证系统中的资源
	 * 
	 * @param user
	 * @return
	 */
	List<AppResourceAuth> findByAccount(String accountId);
	
	/**
	 * 根据资源ID查询授权信息
	 */
	List<AppResourceAuth> findAuthByAppRes(String sourceId);
	
	/**
	 * 检查能否批量删除：如果有一个不能删除，则返回false
	 * 
	 * @param ids
	 * @param errInfo
	 * @return
	 */
	boolean checkDelete(String ids, StringBuilder errInfo);
	
	/**
	 * 根据外键查找记录，主要检查实体间的引用关系，能否删除某个实体
	 * 
	 * @param orgId
	 * @param orgGroupId
	 * @param roleId
	 * @param resourceId
	 * @return
	 */
	List<AppResourceAuth> findListByParams(String orgId, String orgGroupId, String roleId, String resourceId);
	
	/**
	 * 授权唯一性检查
	 * @param orgId
	 * @param orgGroupId
	 * @param roleId
	 * @param resourceId
	 * @param grant
	 * @return
	 */
	boolean checkUnique(AppResourceAuth reAuth,String orgId, String orgGroupId, String roleId, String resourceId,StringBuilder errInfo);
	
	/**
	 * 用户能否访问某个应用下的url
	 * 
	 * @param account
	 * @param app
	 * @param url
	 * @return
	 */
	boolean canAccessResource(Account account, Application app, String url);
}