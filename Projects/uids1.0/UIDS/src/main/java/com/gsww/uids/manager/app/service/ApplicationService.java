package com.gsww.uids.manager.app.service;

import java.util.List;

import com.gsww.uids.manager.app.entity.Application;

public interface ApplicationService {
	
	 /**
	 * 查询应用
	 * @param id
	 * @return
	 */
	 Application findById(String id);
	  
	  /**
	   * 查询所有应用
	   * @return
	   */
	  List<Application> findAll();
	  
	  /**
	   * 查询所有接入应用
	   * 
	   * @return
	   */
	  List<Application> findCommonApplications();
	  
	  /**
	   * 查询统一身份认证应用
	   * 
	   * @return
	   */
	  Application findSystemApplication();
	  
	  /**
	   * 查询所有可显示应用
	   * @return
	   */
	  List<Application> findShowAll();
	  
	  /**
	   * 应用列表
	   */
	  String findPage(int page, int size, String name, String groupId);
	  
	  /**
	   * 应用保存
	   * @param application
	   * @return
	   */
	  boolean saveOrUpdate(Application application);
	  
	  /**
	   * 应用删除
	   * @param ids
	   */
	  void deleteApp(String ids);
	  
	  
	  /**
	   * 根据客户端ID查询
	   * @param id
	   * @return
	   */
	  Application findByClientId(String id);
	  
	  /**
	   * 验证clientId和ClientKey的有效性
	   * 
	   * @param clientId
	   * @param clientKey
	   * @return
	   */
	  boolean checkClientAndKey(String clientId, String clientKey);
	  
	  /**
	   * 根据应用名称查询应用
	   * @param appName 应用名称
	   * @return
	   */
	  List<Application> findByName(String appName);
	  
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
		* @param iconId
		* @return
		*/
	   List<Application> findListByParams(String orgId, String iconId);
	   
	   /**
		 * 检查记录的唯一性
		 * 
		 * @param info
		 * @param errInfo
		 * @return
		 */
	   boolean checkUnique (Application app,StringBuilder errInfo);
	   
	  /**
	   * 根据应用标示查询应用
	   * @param marks
	   * @return
	   */
	   Application findByMark(String marks);
	   
	   /**
	    * 验证统一身份系统的唯一性
	    * @param type
	    * @return
	    */
	  List<Application> findByAppType(String type);
}
