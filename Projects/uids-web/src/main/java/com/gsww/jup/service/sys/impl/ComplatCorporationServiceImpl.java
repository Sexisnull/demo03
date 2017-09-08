package com.gsww.jup.service.sys.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.dao.ComplatCorporationDao;
import com.gsww.jup.entity.ComplatCorporation;
import com.gsww.jup.service.sys.ComplatCorporationService;
@Transactional
@Service("ComplatCorporationService")
public class ComplatCorporationServiceImpl implements ComplatCorporationService{
	
	@Autowired
	private ComplatCorporationDao complatCorporationDao;

	@Override
	public Page<ComplatCorporation> getCorporationPage(Specification<ComplatCorporation> spec, PageRequest pageRequest) {
		return complatCorporationDao.findAll(spec, pageRequest);
	}

	@Override
	public ComplatCorporation findByKey(Integer iid) throws Exception {
		
		return complatCorporationDao.findByIid(iid);
	}

	@Override
	public void save(ComplatCorporation corporation) {
		
		complatCorporationDao.save(corporation);
	}

	@Override
	public void delete(ComplatCorporation corporation) throws Exception {
		
		complatCorporationDao.delete(corporation);
	}
	
	

	
}
