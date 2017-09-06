/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysRole;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-7-28 下午07:58:15</p>
 * <p>类描述 : 角色管理DAO        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
public interface SysRoleDao extends PagingAndSortingRepository<SysRole, String>,JpaSpecificationExecutor<SysRole> {
	
	/**
	 * 方法描述 : 根据主键查询实体
	 * @param roleId
	 * @return
	 */
	SysRole findByRoleId(String roleId);
	/**
	 * 查询所有角色
	 */
	List<SysRole> findAll();
	
	/**
	 * 方法描述 : 根据角色名和状态查询
	 * @param roleNameInput
	 * @param state
	 * @return
	 */
	List<SysRole> findByRoleNameAndRoleState(String roleNameInput, String state);

	
}