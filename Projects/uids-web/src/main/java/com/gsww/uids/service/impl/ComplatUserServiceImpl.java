package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.uids.dao.ComplatUserDao;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.service.ComplatUserService;


@Transactional
@Service("complatUserService")
public class ComplatUserServiceImpl implements ComplatUserService{

	@Autowired
	private ComplatUserDao complatUserDao;
	
	
	/**
	 * 查询用户列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public Page<ComplatUser> getComplatUserPage(Specification<ComplatUser> spec,PageRequest pageRequest) {
		return complatUserDao.findAll(spec, pageRequest);
	}
	
	
	
	
	@Override
	public ComplatUser findByKey(Integer iid) throws Exception {		
		return complatUserDao.findByIid(iid);
	}
	

	@Override
	public void save(ComplatUser complatUser) {
		
		complatUserDao.save(complatUser);
	}

	@Override
	public void delete(ComplatUser complatUser) throws Exception {
		
		complatUserDao.delete(complatUser);
	}




	@Override
	public List<ComplatUser> findByUserName(String name) {
		List<ComplatUser> list=new ArrayList<ComplatUser>();
		list=complatUserDao.findByName(name);
		return list;
	}
	
	
	/**
	 * 用户设置保存  
	 * @author yaoxi 
	 */
	@Override
	public void updateUser(Integer iid,String name, String headShip, String phone,
			String mobile, String fax, String email, String qq,
			Date modifyTime,String pwd) throws Exception {
	
		complatUserDao.updateUser(iid,name,headShip,phone,mobile,fax,email,qq,modifyTime,pwd);
	}



	


}
