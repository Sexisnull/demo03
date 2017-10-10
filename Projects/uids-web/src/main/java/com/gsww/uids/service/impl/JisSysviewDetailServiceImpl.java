package com.gsww.uids.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisSysviewDetailDao;
import com.gsww.uids.entity.JisSysviewDetail;
import com.gsww.uids.service.JisSysviewDetailService;


@Transactional
@Service("jisSysviewDetailService")
public class JisSysviewDetailServiceImpl implements JisSysviewDetailService{

	@Autowired
	private JisSysviewDetailDao sysviewDetailDao;

	@Override
	public JisSysviewDetail findByIid(int iid) { 
		return sysviewDetailDao.findByIid(iid);
	}

	@Override
	public void delete(JisSysviewDetail jisSysviewDetail) {
		sysviewDetailDao.delete(jisSysviewDetail);
	}
	
	@Override
	public JisSysviewDetail findByTranscationId(String transcationId) { 
		return sysviewDetailDao.findByTranscationId(transcationId);
	}
}
