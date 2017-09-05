/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysPara;
import com.gsww.jup.entity.sys.SysParaType;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-9-9 上午10:21:11</p>
 * <p>类描述 :参数管理DAO        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangjy</a>
 */
public interface SysParaDao extends PagingAndSortingRepository<SysPara, String>,JpaSpecificationExecutor<SysPara> {
	
	/**
	 * 方法描述 : 根据主键查询实体
	 * @param paraId
	 * @return
	 */
	SysPara findByParaId(String paraId);
	/**
	 * 查询所有参数
	 */
	List<SysPara> findAll();
	/**
	 * 
	 * 方法描述 : 通过paraId更新该参数状态
	 * @param paraState 状态
	 * @param paraId
	 */
	@Modifying
	@Query("update SysPara t set t.paraState =?1 where t.paraId =?2")
	void updateState(String paraState, Integer paraId);
	
	/**
	 * 
	 * 方法描述 : 通过paraCode更新该参数的Name
	 * @param paraState 状态
	 * @param paraId
	 */
	@Modifying
	@Query("update SysPara t set t.paraName =?1 where t.paraCode =?2")
	void updateParamNameBycode(String paraState, String paraCode);
	/**
	 * 方法描述 : 根据参数编码和状态查询
	 * @param paraNameInput
	 * @param state
	 * @return
	 */
	@Query(" from SysPara t where  t.paraCode =?1  and t.paraState =?2 and t.sysParaType =?3")
	List<SysPara> findByParaCodeAndParaStateAndSysParaType(
			String paraCodeInput, String state,SysParaType sysParaType);
	/**
	 * 方法描述 : 根据参数名称和状态查询
	 * @param paraNameInput
	 * @param state
	 * @return
	 */
	@Query(" from SysPara t where  t.paraName =?1  and t.paraState =?2 and t.sysParaType =?3")
	List<SysPara> findByParaNameAndParaStateAndSysParaType(
			String paraNameInput, String state,SysParaType sysParaType);
	/**
	 * 
	 * 方法描述 :逻辑删除参数
	 * @param paraState 状态
	 * @param paraId
	 */
	@Modifying
	@Query("update SysPara t set t.paraState =?1 where t.paraId =?2")
	void tombstoneDelete(String paraState, String paraId);

	/**
	 * 根据参数类型查询参数
	 * @param sysParaType
	 * @return
	 */
	@Query(" from SysPara t where  t.sysParaType =?1 ")
	List<SysPara> findBySysParaType(SysParaType sysParaType);
	/**
	 * 
	 * 方法描述 :根据参数类型逻辑删除参数
	 * @param paraState 状态
	 * @param paraId
	 */
	@Modifying
	@Query("update SysPara t set t.paraState =?1 where t.sysParaType =?2")
	void updateStateBySysParaType(String paraState, SysParaType sysParaType);
	
}