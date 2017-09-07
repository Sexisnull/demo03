/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.service.sys;

import java.util.List;

import net.sf.json.JSONArray;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysRole;
import com.gsww.jup.entity.sys.SysRoleAcctRel;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-7-28 下午07:13:16</p>
 * <p>类描述 : 角色管理业务类       </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
public interface SysRoleService {
	
	/**
	 * 
	 * 方法描述 : 角色分页查询
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<SysRole> getRolePage(Specification<SysRole> spec, PageRequest pageRequest);
	
	/**
	 * 方法描述 : 根据主键查询实体
	 * @param parseInt
	 * @return
	 * @throws Exception
	 */
	public SysRole findByKey(String roleId) throws Exception;
	
	/**
	 * 保存角色
	 * @param entity
	 * @throws Exception
	 */
	public void save(SysRole entity) throws Exception;
	
	/**
	 * 根据主键删除对象
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception;


	/**获取授权树
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public String getAuthorizeTree(String roleId) throws Exception;

	/**保存授权树
	 * @param roleId
	 * @param jsonArray
	 * @return
	 */
	public void saveAuthorize(String roleId,String keys,String types);

	/**
	 * 查询所有角色列表
	 * @return
	 * @throws Exception
	 */
	public List<SysRole> findRoleList() throws Exception;

	/**
	 * 方法描述 : 根据角色查询用户关系表
	 * @param trim
	 * @return
	 */
	public List<SysRoleAcctRel> findAcctByroleId(String roleId)throws Exception;

	/**
	 * 方法描述 : 根据角色名和状态查询
	 * @param roleNameInput
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<SysRole> findByRoleNameAndRoleState(String roleNameInput, String string)throws Exception;



}
