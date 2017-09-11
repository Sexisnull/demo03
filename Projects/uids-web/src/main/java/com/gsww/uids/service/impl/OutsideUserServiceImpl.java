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

/**
 * 
 * Title: OutsideUserServiceImpl.java Description: 个人用户Service实现层
 * 
 * @author yangxia
 * @created 2017年9月8日 上午10:40:50
 */
@Transactional
@Service("outsideUserService")
public class OutsideUserServiceImpl implements OutsideUserService {
	@Autowired
	private OutsideUserDao outsideUserDao;

	@Override
	public Page<OutsideUser> getOutsideUserPage(Specification<OutsideUser> spec, PageRequest pageRequest) {
		return outsideUserDao.findAll(spec, pageRequest);
	}

	@Override
	public OutsideUser findByKey(Integer iid) {
		return outsideUserDao.findByIid(iid);
	}

	@Override
	public void save(OutsideUser outsideUser) {
		outsideUserDao.save(outsideUser);
	}

	@Override
	public void delete(OutsideUser outsideUser) {
		outsideUserDao.delete(outsideUser);
	}
}
