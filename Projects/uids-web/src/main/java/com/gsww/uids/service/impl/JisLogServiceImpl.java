package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisLogDao;
import com.gsww.uids.entity.JisLog;
import com.gsww.uids.service.JisLogService;

/**
 * <p>
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * 公司名称 : 中国电信甘肃万维公司
 * </p>
 * <p>
 * 项目名称 : uids
 * </p>
 * <p>
 * 创建时间 : 2017年9月13日10:25:59
 * </p>
 * <p>
 * 类描述 : 系统日志service层接口
 * </p>
 * 
 * 
 * @version 1.0.0
 * @author <a href=" ">zcc</a>
 */
@Transactional
@Service("jisLogService")
public class JisLogServiceImpl implements JisLogService {

	@Autowired
	private JisLogDao jisLogDao;

	@Override
	public void logInsert(JisLog jisLog) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<JisLog> findLogList() throws Exception {
		List<JisLog> list = new ArrayList<JisLog>();
		list = jisLogDao.findAll();
		return list;
	}

	@Override
	public Page<JisLog> getJisLogPage(Specification<JisLog> spec,
			PageRequest pageRequest) {
		return jisLogDao.findAll(spec, pageRequest);
	}

	@Override
	public List<JisLog> findBySpec(String spec) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
