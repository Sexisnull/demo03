/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.service.sys.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.dao.sys.SysParaDao;
import com.gsww.jup.dao.sys.SysParaTypeDao;
import com.gsww.jup.dao.sys.VSysParameterDao;
import com.gsww.jup.entity.sys.SysPara;
import com.gsww.jup.entity.sys.SysParaType;
import com.gsww.jup.entity.sys.VSysParameter;
import com.gsww.jup.service.sys.SysParaService;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-9-9 下午03:05:28</p>
 * <p>类描述 : 参数管理业务实现类        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangjy</a>
 */
@Transactional
@Service("sysParaService")
public class SysParaServiceImpl implements SysParaService {
	@Autowired
	private SysParaDao sysParaDao;
	@Autowired
	private SysParaTypeDao sysParaTypeDao;
/*	@Autowired
	private SysParameterTypeDao sysRParameterTypeDao;
*/
	@Autowired
	private JdbcTemplate jdbcTemplate ;
	@Autowired
	private VSysParameterDao vSysParameterDao;
	@Override
	public void delete(String id) throws Exception {
		sysParaDao.delete(	sysParaDao.findByParaId(id));
		
	}

	@Override
	public SysPara findByKey(String paraId) throws Exception {
		return sysParaDao.findByParaId(paraId);
	}

	@Override
	public List<SysPara> findParaList() throws Exception {
		return sysParaDao.findAll();
	}

	@Override
	public Page<SysPara> getParaPage(
			Specification<SysPara> spec, PageRequest pageRequest) {
		return sysParaDao.findAll(spec,pageRequest);
	}

	@Override
	public void save(SysPara entity) throws Exception {
		sysParaDao.save(entity);
	}
	@Override
	public List<SysPara> findByParaCodeAndParaStateAndSysParaType(
			String paraCodeInput, String paraState, SysParaType sysParaType)
			throws Exception {
		
		return sysParaDao.findByParaCodeAndParaStateAndSysParaType(paraCodeInput, "1", sysParaType);
	}
	@Override
	public List<SysPara> findByParaNameAndParaStateAndSysParaType(
			String paraNameInput, String paraState, SysParaType sysParaType)
			throws Exception {
		
		return sysParaDao.findByParaNameAndParaStateAndSysParaType(paraNameInput, "1", sysParaType);
	}

	@Override
	public List<VSysParameter> findByParaTypeDesc(String paraTypeDesc) {
		return this.vSysParameterDao.findByParaTypeDesc(paraTypeDesc, new Sort(new Sort.Order(Direction.ASC,"paraSeq")));
	}

	@Override
	public VSysParameter findByKeyVSysParameter(Integer id) throws Exception {
		return this.vSysParameterDao.findOne(id);
	}

	@Override
	public VSysParameter findByParaCodeAndParaTypeDesc(String paraCode,
			String paraTypeDesc) {
		return this.vSysParameterDao.findByParaCodeAndParaTypeDesc(paraCode,paraTypeDesc);
	}

	@Override
	public void updateParaNameByCode(String paraName, String paraCode) {
		sysParaDao.updateParamNameBycode(paraName, paraCode);
	}

	@Override
	public void delete(String paraState, String paraId) {
		System.out.println("tombstoneDelete!");
		sysParaDao.tombstoneDelete(paraState, paraId);
	}
	/**
	 * 根据参数类型逻辑删除参数
	 * **/
	public void updateStateBySysParaType(SysParaType sysParaType) throws Exception{
		sysParaDao.updateStateBySysParaType("0",sysParaType);
	}

	@Override
	public List<SysPara> findByParaType(String typeName) {
		return sysParaDao.findBySysParaType(sysParaTypeDao.findByParaTypeNameAndParaTypeState(typeName,"1"));
	}

	@Override
	public List<Map<String, Object>> getParaList() throws Exception {
		String sql ="SELECT p.PARA_CODE,p.PARA_NAME from sys_parameter p where p.PARA_TYPE_ID = '8a929c355e6fa05e015e705fe9910002'";
		List<Map<String, Object>> mapList = jdbcTemplate.queryForList(sql, new Object[]{});
		return mapList;
	}



}
