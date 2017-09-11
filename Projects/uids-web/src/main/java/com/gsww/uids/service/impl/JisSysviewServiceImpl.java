package com.gsww.uids.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisSysviewDao;
import com.gsww.uids.entity.JisSysview;
import com.gsww.uids.service.JisSysviewService;

@Transactional
@Service("jisSysviewService")
public class JisSysviewServiceImpl implements JisSysviewService{
	@Autowired
	private JisSysviewDao jisSysviewDao;
	
	@Override
	public Page<JisSysview> getJisPage(Specification<JisSysview> spec, PageRequest pageRequest) {
		return jisSysviewDao.findAll(spec, pageRequest);
	}

	@Override
	public JisSysview findByKey(String objectId) throws Exception {
		return jisSysviewDao.findByObjectId(objectId);
	}
}
