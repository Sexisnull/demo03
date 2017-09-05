package com.gsww.uids.manager.user.service;

import java.util.List;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.user.entity.User;
import com.gsww.uids.manager.user.entity.UserDetail;

/**
 * 用户的业务层接口
 * 
 * @author taolm
 *
 */
public interface UserService {
	
	/**
	 * 保存或更新用户信息
	 * 
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(User info);
	
	/**
	 * 批量删除用户信息
	 * 
	 * @param ids
	 * @return
	 */
	void delete(String ids);
	
	/**
	 * 查找用户信息
	 * 
	 * @param id
	 * @return
	 */
	User findById(String id);

	/**
	 * 获得分页列表
	 * 
	 * @param type
	 * @param name
	 * @param identity
	 * @param mobile
	 * @param corporateName
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageObject<User> findPage(int type, String name, String identity, String mobile, String corporateName, Integer currentPage, Integer pageSize);
	
	/**
     * 检查能否批量删除：如果有一个不能删除，则返回false
     * 
     * @param ids
     * @param errInfo
     * @return
     */
	boolean checkDelete(String ids, StringBuilder errInfo);
	
	/**
	 * 根据身份证号精确查找自然人用户
	 * 
	 * @param identity
	 * @return
	 */
	User findNaturalUserByIdentity(String identity);
	
	/**
	 * 根据手机号精确查找自然人用户
	 * 
	 * @param mobile
	 * @return
	 */
	User findNaturalUserByMobile(String mobile);
	
	/**
	 * 根据法人信息中统一社会信用代码查询企业法人
	 * 
	 * @param creDitCode
	 * @return
	 */
	User findCorporateUserByCreditCode(String creDitCode);
	
	/**
	 * 根据组织机构代码查询法人用户
	 * 
	 * @param orgCode
	 * @return
	 */
	User findCorporateUserByOrgCode(String orgCode);
	
	/**
	 * 根据企业/机构名称查找法人用户
	 * 
	 * @param corporteName
	 * @return
	 */
	User findCorporateUserByCorporteName(String corporateName);
	
	/**
	 * 检查记录的唯一性
	 * 
	 * @param info
	 * @param errInfo
	 * @return
	 */
	boolean checkUnique(User info, StringBuilder errInfo);
	
	/**
	 * 查找所有用户
	 * 
	 * @return
	 */
	List<User> findAll();
	
	/**
	 * 主要用于删除的检查
	 * 
	 * @param areaId
	 * @param orgId
	 * @return
	 */
	List<User> findListByParamer(String areaId, String orgId);
	
	/**
	 * 保存或更新用户的完整信息
	 * 
	 * @param user
	 * @param detail
	 * @return
	 */
	boolean saveOrUpdateCompleteUser(User user, UserDetail detail);
	
	/**
	 * 获取当前登录的用户
	 * 
	 * @return
	 */
	User getCurrentLoginUser();
	
	/**
	 * 给用户中的敏感信息（身份证号、手机号）增加掩码
	 * 
	 * @param pages
	 * @return
	 */
	PageObject<User> addMaskToSeceretInfo(PageObject<User> pages);
}
