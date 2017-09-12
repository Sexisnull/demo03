package com.gsww.uids.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisSysviewHistoryDao;
import com.gsww.uids.entity.JisSysviewHistory;
import com.gsww.uids.service.JisSysviewHistoryService;

@Transactional
@Service("jisSysviewHistoryService")
public class JisSysviewHistoryServiceImpl implements JisSysviewHistoryService{
	@Autowired
	private JisSysviewHistoryDao jisSysviewHistoryDao;
	
	@Override
	public Page<JisSysviewHistory> getJisPage(Specification<JisSysviewHistory> spec, PageRequest pageRequest) {
		return jisSysviewHistoryDao.findAll(spec, pageRequest);
	}

	@Override
	public JisSysviewHistory findByIid(Integer iid) throws Exception {
		return jisSysviewHistoryDao.findByIid(iid);
	}
}
