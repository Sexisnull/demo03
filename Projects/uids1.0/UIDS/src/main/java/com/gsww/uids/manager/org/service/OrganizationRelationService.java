package com.gsww.uids.manager.org.service;

import java.util.List;

import com.gsww.uids.manager.org.entity.OrganizationRelation;
import com.gsww.uids.manager.org.entity.OrganizationRelationType;

public interface OrganizationRelationService {
	
	/**
	 * 获取上级机构关系对象列表
	 * @param orgId
	 * @param classifyCode
	 * @return
	 */
	List<OrganizationRelation> finParent(String orgId, String classifyCode);
	
	/**
	 * 分页查询机构关系对象列表
	 * @param currentPage
	 * @param pageSize
	 * @param parentId
	 * @param relationCode
	 * @return
	 */
	String findPage(Integer currentPage, Integer pageSize, String parentId, String relationCode);
		
	/**
	 * 机构关系保存
	 */
	OrganizationRelation saveOrUpdate(OrganizationRelation info);
	
	/**
	 * 机构关系删除
	 */
	void delete(String ids);
	
	/**
	 * 根据机构id删除架构关系
	 */
	void deleteByOrgId(String ids);
	
	/**
	 * 获取机构关系对象
	 */
	OrganizationRelation findById(String id);
	
	/**
	 * 获取机构关系对象
	 */
	OrganizationRelation findByOrgId(String orgId);
	
	/**
	 * 获取机构关系对象
	 */
	OrganizationRelation findByParentOrgId(String Pid);
	
	/**
	 * 获取所有机构分类类型
	 * @return
	 */
	List<OrganizationRelationType> findAll();
	
	/**
	 * 获取机构关系类型对象
	 */
	OrganizationRelationType findTypeById(String id);
	
	/**
	 * 根据机构编码，机构类型编码 获取机构关系子节点列表
	 * @param id			父机构id
	 * @param code			机构分类编码
	 * @return
	 */
	List<OrganizationRelation> findChildByPIdAndCode(String id, String code);
	
	/**
	 * 获取按类型划分的机构的根节点
	 * 
	 * @return
	 */
	OrganizationRelation getRootOrganization();
	
	/**
	 * 能否被删除
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
	 * @param orgTypeId
	 * @param pOrgId
	 * @return
	 */
	List<OrganizationRelation> findListByParams(String orgId, String orgTypeId, String pOrgId);
}
