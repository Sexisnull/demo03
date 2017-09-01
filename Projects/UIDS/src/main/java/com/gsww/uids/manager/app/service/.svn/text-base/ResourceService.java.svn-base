package com.gsww.uids.manager.app.service;

import java.util.List;

import com.gsww.uids.manager.app.entity.AppResource;

public interface ResourceService {
	
    /**
     * 资源保存
     */
	boolean saveOrUpdate(AppResource appResource);

	/**
	 * 资源查询
	 */
	AppResource findById(String id);

	/**
	 * 获取全部资源
	 * @return
	 */
	List<AppResource> findAll();

	/**
	 * 删除资源
	 * @param ids
	 */
	void deleteSource(String ids);

	/**
	 *资源列表
	 */
	String findPage(int page, int size, String name,String groupId);
	
	/**
	 * 根据应用的ID查询资源
	 */
	List<AppResource> findAppResourceByApp(String appId);
	
	/**
	 * 根据账号的ID查询资源
	 */
	List<AppResource> findByAccount(String accountId);
	
	/**
	 * 查找某个账号的前台显示的资源
	 * 
	 * @param accountId
	 * @return
	 */
	List<AppResource> findShowResourcesOfAccount(String accountId);
	
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
	 * @param appId
	 * @param iconId
	 * @param pId
	 * @return
	 */
	List<AppResource> findListByParams(String appId, String iconId, String pId);
	
	/**
	 * 检查记录的唯一性
	 * 
	 * @param info
	 * @param errInfo
	 * @return
	 */
	boolean checkUnique(AppResource appResoure,StringBuilder errInfo);
	
	/**
	 * 根据资源编码、资源名称、资源路径查询资源
	 * @param code
	 * @return
	 */
	AppResource findByParames(String code,String name,String url);
	
	/**
	 * 查找某个应用下url对应的资源
	 * 
	 * @param appId
	 * @param url
	 * @return
	 */
	AppResource findByAppAndUrl(String appId, String url);
}
