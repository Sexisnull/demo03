package com.gsww.uids.manager.org.service;

import java.util.List;

import com.gsww.uids.manager.org.entity.OrganizationGroup;

public interface OrganizationGroupService {
	
	/**
	 * 获取所有机构分组
	 * @return
	 */
	public List<OrganizationGroup> findAll();
	
	/**
	 * 根据id获取机构分组
	 * @return
	 */
	public OrganizationGroup findById(String id);
	
	/**
	 * 机构分组保存
	 */
	public OrganizationGroup saveOrUpdate(OrganizationGroup info);
	
	/**
	 * 机构分组删除
	 * @param ids
	 */
	public void delete(String ids);
	
	/**
	 * 分页查询机构分组
	 * @param currentPage
	 * @param pageSize
	 * @param groupName
	 * @return
	 */
	public String findPage(Integer currentPage, Integer pageSize,String groupName);
	
	/**
	 * 根据分组id，机构名称 查询机构分组成员
	 * @param id			分组id
	 * @param orgName		机构名称
	 * @return
	 */
	public String findGroupOrg(String groupId, String orgName);
	
	/**
	 * 机构分组成员保存
	 * @param orgIds	机构ids
	 * @param groupId	分组id
	 */
	public String saveGroupOrg(String orgIds, String groupId);
	
	/**
	 * 能否被删除
	 * 
	 * @param ids
	 * @param errInfo
	 * @return
	 */
	boolean checkDelete(String ids, StringBuilder errInfo);
	
	/**
	 * 检查记录的唯一性
	 * 
	 * @param info
	 * @param errInfo
	 * @return
	 */
	boolean checkUnique(OrganizationGroup info, StringBuilder errInfo);
	
	/**
	 * 根据机构分组名称获取分组
	 * @param orgGroupName
	 * @return
	 */
	public OrganizationGroup findByOrgGtoupName(String orgGroupName);
	
	/**
	 * 根据机构分组标识获取分组
	 * @param ogrGroupMark
	 * @return
	 */
	public OrganizationGroup findByOrgGroupMark(String ogrGroupMark); 
}
