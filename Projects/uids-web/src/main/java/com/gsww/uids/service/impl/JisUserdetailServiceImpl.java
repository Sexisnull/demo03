package com.gsww.uids.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.JisUserdetailDao;
import com.gsww.uids.entity.JisUserdetail;
import com.gsww.uids.service.JisUserdetailService;


@Transactional
@Service("JisUserdetailService")
public class JisUserdetailServiceImpl implements JisUserdetailService {
	
	@Autowired
	private JisUserdetailDao jisUserdetailDao;
	
	@Override
	public JisUserdetail findByUserid(Integer userId) {
		
		return jisUserdetailDao.findByUserid(userId);
	}

	@Override
	public void save(JisUserdetail jisUserdetail) {
		
		jisUserdetailDao.save(jisUserdetail);
	}

	@Override
	public void update(Integer iid, String cardId) {
		jisUserdetailDao.update(iid,cardId);
	}
	
	
	
	
}
