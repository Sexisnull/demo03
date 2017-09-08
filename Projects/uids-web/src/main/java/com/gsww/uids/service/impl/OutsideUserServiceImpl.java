package com.gsww.uids.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.OutsideUserDao;
import com.gsww.uids.entity.OutsideUser;
import com.gsww.uids.service.OutsideUserService;



@Transactional
@Service("outsideUserService")
public class OutsideUserServiceImpl implements OutsideUserService{
	@Autowired
	private OutsideUserDao outsideUserDao;
	
	@Override
	public Page<OutsideUser> getOutsideUserPage(Specification<OutsideUser> spec, 
			PageRequest pageRequest) {
		return outsideUserDao.findAll(spec, pageRequest);
	}
}
