/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.service.sys;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysParaType;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-9-9 下午02:56:18</p>
 * <p>类描述 : 参数类型管理业务类       </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangjy</a>
 */
public interface SysParaTypeService {
	
	/**
	 * 
	 * 方法描述 : 参数分页查询
	 * @ParaTypem spec
	 * @ParaTypem pageRequest
	 * @return
	 */
	public Page<SysParaType> getParaTypePage(Specification<SysParaType> spec, PageRequest pageRequest);
	
	/**
	 * 方法描述 : 根据主键查询实体
	 * @ParaTypem parseInt
	 * @return
	 * @throws Exception
	 */
	public SysParaType findByKey(String paraTypeId) throws Exception;
	
	/**
	 * 保存角色
	 * @ParaTypem entity
	 * @throws Exception
	 */
	public void save(SysParaType entity) throws Exception;
	
	/**
	 * 逻辑删除
	 * @ParaTypem id
	 * @throws Exception
	 */
	public void delete(String paraTypeState,String paraTypeId) throws Exception;




	/**
	 * 查询所有参数类型列表
	 * @return
	 * @throws Exception
	 */
	public List<SysParaType> findParaTypeList() throws Exception;

	/**
	 * 根据主键启用参数类型
	 * @param paraTypeId
	 * @throws Exception
	 */
	public void startParaType(String paraTypeId) throws Exception;
	/**
	 * 根据主键停用参数类型
	 * @param paraTypeId
	 * @throws Exception
	 */
	public void stopParaType(String paraTypeId) throws Exception;

}
