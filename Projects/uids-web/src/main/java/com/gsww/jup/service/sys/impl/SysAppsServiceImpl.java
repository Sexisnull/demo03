package com.gsww.jup.service.sys.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.dao.sys.SysAppsDao;
import com.gsww.jup.entity.sys.SysApps;
import com.gsww.jup.service.sys.SysAppsService;

@Transactional
@Service("sysAppsService")
public class SysAppsServiceImpl implements SysAppsService{

	@Autowired
	private SysAppsDao sysAppsDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Override
	public void save(SysApps entity) throws Exception {
		sysAppsDao.save(entity);
	}

	@Override
	public void delete(SysApps entity) throws Exception {
		sysAppsDao.delete(entity);
	}

	@Override
	public SysApps findBykey(String key) throws Exception {
		return sysAppsDao.findByKey(key);
	}

	@Override
	public Page<SysApps> getSysAppsPage(Specification<SysApps> spec,
			PageRequest pageRequest) {
		return sysAppsDao.findAll(spec, pageRequest);
	}

	@Override
	public void stop(String key) throws Exception {
		sysAppsDao.updateState("00B", key);
		
	}

	@Override
	public void start(String key) throws Exception {
		sysAppsDao.updateState("00A", key);
		
	}

	@Override
	public void stopAppGreenChanal(String key) throws Exception {
		sysAppsDao.updateAppGreenChanal("00A", key);
		
	}

	@Override
	public void startAppGreenChanal(String key) throws Exception {
		sysAppsDao.updateAppGreenChanal("00B", key);
		
	}

}
