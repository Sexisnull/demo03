package com.gsww.uids.service.impl;

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
 * <p>Copyright: Copyright (c) 2014</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2017-09-12 下午14:30:23</p>
 * <p>类描述 :   在线用户模块serviceImpl层    </p>
 *
 *
 * @version 3.0.0
 * @author <a href=" ">yaoxi</a>
 */
@Transactional
@Service("JisLogService")
public class JisLogServiceImpl implements JisLogService {
	
	@Autowired
	private JisLogDao jisLogDao;
	
	@Override
	public Page<JisLog> getJisLogPage(Specification<JisLog> spec,
			PageRequest pageRequest) {
		
		return jisLogDao.findAll(spec,pageRequest);
	}
	
	
}
