package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatUser;

public interface ComplatUserDao extends
PagingAndSortingRepository<ComplatUser, Integer>,
JpaSpecificationExecutor<ComplatUser>{

	

	
	
	/**
	 * 新增政府用户信息保存
	 */
	public List<ComplatUser> saveComplatUser();
	
	
	/**
	 * 根据账号主键查询账号信息
	 * @param accountId
	 * @return
	 */
	ComplatUser findById(String pk);
	
	
	List<ComplatUser> findByIid(int iid);

	List<ComplatUser> findByRoleId(String uuid);
	
	
}
