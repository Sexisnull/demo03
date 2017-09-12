package com.gsww.uids.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.entity.sys.SysRole;
import com.gsww.uids.dao.ComplatRoleDao;
import com.gsww.uids.dao.ComplatRoleRelationDao;
import com.gsww.uids.entity.ComplatRole;
import com.gsww.uids.entity.ComplatRolerelation;
import com.gsww.uids.service.ComplatRoleService;

@Transactional
@Service("complatRoleService")
public class ComplatRoleServiceImpl implements ComplatRoleService{
	@Autowired
	private ComplatRoleDao dao;
	@Autowired
	private ComplatRoleRelationDao comrelationDao;

	@Override
	public Page<ComplatRole> getRolePage(Specification<ComplatRole> spec,
			PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return dao.findAll(spec, pageRequest);
	}

	@Override
	public void save(ComplatRole entity) throws Exception {
		// TODO Auto-generated method stub
		dao.save(entity);
	}

	@Override
	public void delete(int id) throws Exception {
		// TODO Auto-generated method stub
		
		dao.delete(id);
	}

	@Override
	public List<ComplatRole> findRoleList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ComplatRole findByKey(int id) throws Exception {
		// TODO Auto-generated method stub
		return dao.findByIid(id);
	}

	@Override
	public List<ComplatRolerelation> findAcctByroleId(Integer roleId)
			{
		// TODO Auto-generated method stub
		return comrelationDao.findByRoleId(roleId);
	}

}
