/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.service.sys;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysPara;
import com.gsww.jup.entity.sys.SysParaType;
import com.gsww.jup.entity.sys.VSysParameter;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-9-9 下午02:14:05</p>
 * <p>类描述 : 参数管理业务类       </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangjy</a>
 */
public interface SysParaService {
	
	/**
	 * 
	 * 方法描述 : 参数分页查询
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<SysPara> getParaPage(Specification<SysPara> spec, PageRequest pageRequest);
	
	/**
	 * 方法描述 : 根据主键查询实体
	 * @param parseInt
	 * @return
	 * @throws Exception
	 */
	public SysPara findByKey(String paraId) throws Exception;
	
	/**
	 * 保存角色
	 * @param entity
	 * @throws Exception
	 */
	public void save(SysPara entity) throws Exception;
	
	/**
	 * 根据主键删除对象
	 * @param id
	 * @throws Exception
	 */
	public void delete(String id) throws Exception;




	/**
	 * 查询所有角色列表
	 * @return
	 * @throws Exception
	 */
	public List<SysPara> findParaList() throws Exception;

	/**
	 * 方法描述 : 根据参数名称和状态查询
	 * @param paraCodeInput
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<SysPara> findByParaCodeAndParaStateAndSysParaType(
			String paraCodeInput, String paraState,SysParaType sysParaType)throws Exception;
	/**
	 * 方法描述 : 根据参数名称和状态查询
	 * @param paraNameInput
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<SysPara> findByParaNameAndParaStateAndSysParaType(
			String paraNameInput, String paraState,SysParaType sysParaType)throws Exception;

	/**
	 * 根据类型编码获取所有的参数列表
	 * @param paraCode
	 * @param paraTypeDesc
	 * @return
	 */
	public List<VSysParameter> findByParaTypeDesc(String paraTypeDesc);
	public VSysParameter findByParaCodeAndParaTypeDesc(String paraCode,String paraTypeDesc);
	
	/**
	 * 方法描述 : 根据主键查询实体
	 * @param parseInt
	 * @return
	 * @throws Exception
	 */
	public VSysParameter findByKeyVSysParameter(Integer Id) throws Exception;
	
	
	/**
	 * 通过paraCode更新该参数的Name
	 * */
	public  void updateParaNameByCode(String paraName,String paraCode);
	/**
	 * 逻辑删除参数
	 * **/
	public void delete(String paraState,String paraId);

	/**
	 * 根据参数类型逻辑删除参数
	 * **/
	public void updateStateBySysParaType(SysParaType sysParaType) throws Exception;
	
	public List<SysPara> findByParaType(String type);
	
	

	public List<Map<String, Object>> getParaList()throws Exception;
}
