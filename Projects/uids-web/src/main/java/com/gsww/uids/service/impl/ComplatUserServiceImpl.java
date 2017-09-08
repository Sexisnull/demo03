package com.gsww.uids.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.uids.dao.ComplatUserDao;
import com.gsww.uids.entity.ComplatUser;
import com.gsww.uids.service.ComplatUserService;

@Transactional
@Service("complatUserService")
public class ComplatUserServiceImpl implements ComplatUserService{

	@Autowired
	private ComplatUserDao complatUserDao;
	
	@Override
	public Page<ComplatUser> getUserPage(Specification<ComplatUser> spec,PageRequest pageRequest) {
		return complatUserDao.findAll(spec, pageRequest);
	}
	
	
	
	/**
	 * 查询用户列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<ComplatUser> findComplatUserList(int iid)throws Exception{
		List<ComplatUser> list=new ArrayList<ComplatUser>();
		//List<ComplatUser> userList=new ArrayList<ComplatUser>();
		
		list=complatUserDao.findByIid(iid);
		/*if(list.size()>0){
			for (ComplatUser complatUser : list) {
				complatUser=complatUserDao.findByRoleId(complatUser.getUuid());
				list.add(complatUser);
			}
		}*/
		return list;
	}
	
	
	
	@Override
	public ComplatUser findByKey(String pk) throws Exception {
		ComplatUser complatUser=complatUserDao.findById(pk);
		return complatUser;
	}
	
	
	/**
	 * 删除用户角色中间表数据
	 * @param userInfo
	 * @throws Exception
	 */
	public void deleteAccountRole(ComplatUser entity) throws Exception{
		if(entity!=null){
			List<ComplatUser> list=complatUserDao.findByIid(entity.getIid());
			if(list.size()>0){
				for (ComplatUser complatUser : list) {
					complatUserDao.delete(complatUser);
				}			
			}
		}
	}
	
	
	/**
	 * 保存用户角色关系表
	 * @param userId
	 * @param roleId
	 * @throws Exception
	 */
	@Override
	public void saveUserRole(int userId,String uuid) throws Exception{
		ComplatUser complatUser=new ComplatUser();
		complatUser.setIid(userId);
		complatUser.setUuid(uuid);
		complatUserDao.save(complatUser);
	}

}
