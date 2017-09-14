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
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.dao.sys.SysParaTypeDao;
import com.gsww.jup.entity.sys.SysParaType;
import com.gsww.jup.service.sys.SysParaTypeService;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014-9-9 下午03:20:47</p>
 * <p>类描述 : 参数类型管理业务实现类        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangjy</a>
 */
@Transactional
@Service("sysParaTypeService")
public class SysParaTypeServiceImpl implements SysParaTypeService {
	@Autowired
	private SysParaTypeDao sysParaTypeDao;
/*	@Autowired
	private SysParaTypemeterTypeDao sysRParaTypemeterTypeDao;
*/
	@Override
	public void delete(String paraTypeState,String paraTypeId) throws Exception {
		sysParaTypeDao.updateState(paraTypeState, paraTypeId);		
	}

	@Override
	public SysParaType findByKey(String paraTypeId) throws Exception {
		return sysParaTypeDao.findByParaTypeId(paraTypeId);
	}

	@Override
	public List<SysParaType> findParaTypeList() throws Exception {
		return sysParaTypeDao.findAll();
	}

	@Override
	public Page<SysParaType> getParaTypePage(
			Specification<SysParaType> spec, PageRequest pageRequest) {
		return sysParaTypeDao.findAll(spec,pageRequest);
	}

	@Override
	public void save(SysParaType entity) throws Exception {
		sysParaTypeDao.save(entity);
	}

	@Override
	public void startParaType(String paraTypeId) throws Exception {
		sysParaTypeDao.updateState("1", paraTypeId);
	}

	@Override
	public void stopParaType(String paraTypeId) throws Exception {
		sysParaTypeDao.updateState("0", paraTypeId);
	}




}
