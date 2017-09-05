package com.gsww.uids.manager.role.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.role.entity.RoleRelation;

/**
 * 用户角色业务层接口
 * 
 * @author taolm
 *
 */
@Service
public interface RoleRelationService {
	
	/**
	 * 根据主键id查找
	 * 
	 * @param id
	 * @return
	 */
	RoleRelation findById(String id);
	
	/**
	 * 保存或更新记录
	 * 
	 * @param info
	 * @return
	 */
	RoleRelation saveOrUpdate(RoleRelation info);
	
	/**
	 * 批量删除
	 * 
	 * @param ids
	 */
	void delete(String ids);
	
	/**
	 * 查找某个用户的所有角色分页
	 * 
	 * @param userId
	 * @param currentPage
	 * @param pageSize
	 * @return
	 */
	PageObject<RoleRelation> findPage(String userId, Integer currentPage, Integer pageSize);
	
	/**
	 * 检查唯一性
	 * 
	 * @param roleRelation
	 * @param errInfo
	 * @return
	 */
	boolean checkUnique(RoleRelation roleRelation, StringBuilder errInfo);
	
	/**
     * 检查能否批量删除：如果有一个不能删除，则返回false
     * 
     * @param ids
     * @param errInfo
     * @return
     */
	boolean checkDelete(String ids, StringBuilder errInfo);
	
	/**
	 * 条件查找
	 * 
	 * @param userId
	 * @param orgId
	 * @param roleId
	 * @return
	 */
	List<RoleRelation> findListByParams(String userId, String orgId, String roleId);
	
	/**
	 * 当前登录用户拥有哪些机构的某个角色
	 * 
	 * @param roleMark
	 * @return
	 */
	List<Organization> getOrganizationsOfCurrentLoginUserAs(String roleMark);
}
