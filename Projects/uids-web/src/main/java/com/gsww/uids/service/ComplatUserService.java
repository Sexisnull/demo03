package com.gsww.uids.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysAccount;
import com.gsww.uids.entity.ComplatUser;


public interface ComplatUserService {

	
	
	public void save(ComplatUser entity);
	/**
	 * 
	 * 查询政府用户列表
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<ComplatUser> getUserPage(Specification<ComplatUser> spec, PageRequest pageRequest);
	
	
	/**
	 * 查询用户角色列表
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<ComplatUser> findComplatUserList(int iid)throws Exception;
	
	
	/**
	 * 根据主键查找对象
	 * @param pk
	 * @throws Exception
	 */
	public ComplatUser findByKey(String pk) throws Exception;
	
	
	/**
	 * 删除用户角色中间表数据
	 * @param userInfo
	 * @throws Exception
	 */
	public void deleteAccountRole(ComplatUser entity) throws Exception;
	
	
	
	/**
	 * 保存用户角色关系表
	 * @param userId
	 * @param roleId
	 * @throws Exception
	 */
	public void saveUserRole(int userId,String uuid) throws Exception;

}
