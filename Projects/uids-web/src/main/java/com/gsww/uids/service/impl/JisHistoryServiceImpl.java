package com.gsww.uids.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisCurrentDao;
import com.gsww.uids.dao.JisHistoryDao;
import com.gsww.uids.entity.JisCurrent;
import com.gsww.uids.entity.JisHistory;
import com.gsww.uids.service.JisCurrentService;
import com.gsww.uids.service.JisHistoryService;

@Transactional
@Service("jisHistoryService")
public class JisHistoryServiceImpl implements JisHistoryService{
	@Autowired
	private JisHistoryDao jisHistoryDao;
	
	@Override
	public Page<JisHistory> getJisPage(Specification<JisHistory> spec, PageRequest pageRequest) {
		return jisHistoryDao.findAll(spec, pageRequest);
	}

	@Override
	public JisHistory findByKey(String objectId) throws Exception {
		return jisHistoryDao.findByObjectId(objectId);
	}
}
