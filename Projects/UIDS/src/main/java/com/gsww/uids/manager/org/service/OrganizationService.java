package com.gsww.uids.manager.org.service;

import java.util.List;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationErrorTemp;
import com.gsww.uids.manager.org.entity.OrganizationMergeTemp;

public interface OrganizationService {
	
	/**
	 * 机构保存
	 */
	Organization saveOrUpdate(Organization info);
	
	/**
	 * 根据类型、所属任务分页查询
	 * @param currentPage  	当前页
	 * @param pageSize		页码
	 * @param parentId		父节点id
	 * @param orgName		机构名称
	 * @param nodeType		节点类型
	 * @param areaType		区域类型
	 * @param areaCode		区域编码
	 * @param code		        机构编码
	 * @return
	 */
	PageObject<Organization> findPage(Integer currentPage, Integer pageSize, String parentId, String orgName, String nodeType, String areaCode, String areaType, String code);
	
	/**
	 * 机构删除
	 */
	void delete(String ids);
	
	/**
	 * 获取机构对象
	 */
	Organization findById(String id);
	
	/**
	 * 获取机构树List
	 */
	List<Organization> findChild(String pid);
	
	/**
	 * 找出所有机构
	 * 
	 * @return
	 */
	List<Organization> findAll();	
	
	/**
	 * 根据名称获取对象
	 * @return
	 */
	Organization getByName(String name);
	
	/**
	 * 获取机构的根节点
	 * 
	 * @return
	 */
	Organization getRootOrganization();
	
	/**
	 * 删除时，调用此方法用来判断，区域是否被占用
	 * @param area 区域
	 * @return
	 */
	List<Organization> findListByParamer(String area);
	
	/**
	 * 检查记录的唯一性
	 * 
	 * @param info
	 * @param errInfo
	 * @return
	 */
	boolean checkUnique(Organization info, StringBuilder errInfo);
	
	/**
	 * 根据机构全名获取机构列表
	 * @param fullName
	 * @return
	 */
	List<Organization> findListByFullName(String fullName);
	
	/**
	 * 能否被删除
	 * 
	 * @param ids
	 * @param errInfo
	 * @return
	 */
	boolean checkDelete(String ids, StringBuilder errInfo);
	
	/**
	 * 根据机构名称查找机构
	 * @param shortName
	 * @return
	 */
	Organization findByShortName(String shortName);
	
	/**
	 * 根据机构全名查找机构
	 * @param fullName
	 * @return
	 */
	Organization findByFullName(String fullName);
	
	/**
	 * 根据机构后缀查找机构
	 * @param suffix
	 * @return
	 */
	public Organization findBySuffix(String suffix);
	
	/**
	 * 根据机构编码查找机构
	 * @param code
	 * @return
	 */
	Organization findByCode(String code);
	
	/**
	 * 是否是根节点
	 * @param orgId
	 * @return
	 */
	boolean isRoot(String orgId);
	
	/**
	 * 根据外键查找记录，主要检查实体间的引用关系，能否删除某个实体
	 * 
	 * @param areaId
	 * @param pId
	 * @return
	 */
	List<Organization> findListByParams(String areaId, String pId);
	
	/**
	 * 保存错误信息临时表
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(OrganizationErrorTemp info);
	
	/**
	 * 保存合并账号临时表
	 * @param info
	 * @return
	 */
	boolean saveOrUpdate(OrganizationMergeTemp info);
	
	/**
	 * 清空错误信息临时表，逻辑删除
	 * @return
	 */
	void deleteErrorTemp();
	
	/**
	 * 清空错误信息临时表，逻辑删除
	 * @return
	 */
	void deleteMergeTemp();
	
	/**
	 * 合并之后更新
	 * 
	 * @param tableinfo
	 */
	void mergeAfterUpdate(String tableinfo1);
	
	/**
	 * 查找所有的错误账号信息
	 * @return
	 */
	List<OrganizationErrorTemp>  findErrAll();
	
	/**
	 * 查找所有的合并账号信息
	 * @return
	 */
	List<OrganizationMergeTemp> findMergeAll();
	
	/**
	 * 关闭窗口或者取消窗口时，清空临时表
	 * @return 
	 */
	boolean closeWindows();
	
	/**
	 * 保存排序结果
	 * @param ids
	 * @return
	 */
	String saveOrderResult(String ids);
	
	/**
	 * 当前登录的用户在后台能否访问某个模块（机构管理、用户管理、应用和资源管理）中的某个机构
	 * 
	 * @param org
	 * @param roleMark  角色标识，对应了要访问的模块
	 * @return
	 */
	boolean canCurrentLoginUserAccessOrganization(Organization org, String roleMark);
}
