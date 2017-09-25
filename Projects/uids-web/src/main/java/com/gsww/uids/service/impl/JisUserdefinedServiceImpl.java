package com.gsww.uids.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisUserdefinedDao;
import com.gsww.uids.entity.JisUserdefined;
import com.gsww.uids.service.JisUserdefinedService;

@Transactional
@Service("jisUserdefinedService")
public class JisUserdefinedServiceImpl implements JisUserdefinedService{
	
	@Autowired
	private JisUserdefinedDao jisUserdefinedDao;
	
	@Override
	public JisUserdefined findByAppidAndLoginAllName(Integer appid,
			String loginAllName) {
		// TODO Auto-generated method stub
		if(appid!=null && loginAllName!=null){
			return jisUserdefinedDao.findByAppidAndLoginallname(appid, loginAllName);
		}
		return null;
	}
	
	@Override
	public JisUserdefined findByKey(int key) {
		// TODO Auto-generated method stub
		return jisUserdefinedDao.findByIid(key);
	}

	@Override
	public void save(JisUserdefined defined) throws Exception {
		
		jisUserdefinedDao.save(defined);
		
	}
}
