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

import com.gsww.jup.entity.sys.SysParaType;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-9-9 上午09:58:19</p>
 * <p>类描述 :参数管理DAO        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangjy</a>
 */
public interface SysParaTypeDao extends PagingAndSortingRepository<SysParaType, String>,JpaSpecificationExecutor<SysParaType> {
	
	/**
	 * 方法描述 : 根据主键查询实体
	 * @param paraTypeId
	 * @return
	 */
	SysParaType findByParaTypeId(String paraTypeId);
	/**
	 * 查询所有参数类型
	 */
	List<SysParaType> findAll();
	/**
	 * 修改参数类型
	 */

	/**
	 * 
	 * 方法描述 : 通过paraTypeId更新该参数类型状态
	 * @param paraTypeState
	 * @param paraTypeId
	 */
	@Modifying
	@Query("update SysParaType t set t.paraTypeState =?1 where t.paraTypeId =?2")
	void updateState(String paraTypeState, String paraTypeId);
	SysParaType findByParaTypeNameAndParaTypeState(String typeName,
			String string);
	
}