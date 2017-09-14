package com.gsww.uids.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisSysviewCurrentDao;
import com.gsww.uids.entity.JisSysviewCurrent;
import com.gsww.uids.service.JisSysviewCurrentService;

@Transactional
@Service("jisSysviewCurrentService")
public class JisSysviewCurrentServiceImpl implements JisSysviewCurrentService{
	
	@Autowired
	private JisSysviewCurrentDao jisSysviewCurrentDao;
	
	@Override
	public Page<JisSysviewCurrent> getJisPage(Specification<JisSysviewCurrent> spec, PageRequest pageRequest) {
		return jisSysviewCurrentDao.findAll(spec, pageRequest);
	}

	@Override
	public List<JisSysviewCurrent> findJisCurList() throws Exception {
		return jisSysviewCurrentDao.findAll();
	}

	@Override
	public void delete(JisSysviewCurrent entity) throws Exception {
		jisSysviewCurrentDao.delete(entity);
	}

	@Override
	public JisSysviewCurrent findByIid(Integer iid) throws Exception {
		return jisSysviewCurrentDao.findByIid(iid);
	}

}
