package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.gsww.jup.entity.sys.SysApps;
import com.gsww.jup.entity.sys.SysOperator;
import com.gsww.jup.entity.sys.SysRole;
import com.gsww.jup.entity.sys.SysRoleAcctRel;
import com.gsww.uids.dao.JisCurrentDao;
import com.gsww.uids.entity.JisCurrent;
import com.gsww.uids.service.JisCurrentService;

@Transactional
@Service("jisCurrentService")
public class JisCurrentServiceImpl implements JisCurrentService{
	@Autowired
	private JisCurrentDao jisCurrentDao;
	
	@Override
	public Page<JisCurrent> getJisPage(Specification<JisCurrent> spec, PageRequest pageRequest) {
		return jisCurrentDao.findAll(spec, pageRequest);
	}

	@Override
	public JisCurrent findByKey(String objectId) throws Exception {
		return jisCurrentDao.findByObjectId(objectId);
	}

	@Override
	public List<JisCurrent> findJisCurList() throws Exception {
		return jisCurrentDao.findAll();
	}

	@Override
	public JisCurrent findByObjectId(String objectId) throws Exception {
		return jisCurrentDao.findByObjectId(objectId);
	}
	

	@Override
	public void delete(JisCurrent entity) throws Exception {
		jisCurrentDao.delete(entity);
	}

	@Override
	public JisCurrent findByIid(Integer iid) throws Exception {
		// TODO Auto-generated method stub
		return jisCurrentDao.findByIid(iid);
	}

}
