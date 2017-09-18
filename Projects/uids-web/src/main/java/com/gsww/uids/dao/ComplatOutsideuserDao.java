package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatOutsideuser;

/**
 * Title: OutsideUserDao.java    
 * Description: 个人用户dao层
 * @author yangxia       
 * @created 2017年9月8日 上午10:37:24
*/
public interface ComplatOutsideuserDao extends 
	PagingAndSortingRepository<ComplatOutsideuser, Integer>,
	JpaSpecificationExecutor<ComplatOutsideuser> {
	/**
	 * 根据iid查找个人用户
	 * @param iid
	 * @return
	 */
	ComplatOutsideuser findByIid(Integer iid);
	
	/**
	 * 查找所用个人用户
	 */
	List<ComplatOutsideuser> findAll();
}
