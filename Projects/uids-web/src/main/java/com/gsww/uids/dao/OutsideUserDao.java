package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.OutsideUser;

/**
 * Title: OutsideUserDao.java    
 * Description: 个人用户dao层
 * @author yangxia       
 * @created 2017年9月8日 上午10:37:24
*/
public interface OutsideUserDao extends 
	PagingAndSortingRepository<OutsideUser, Integer>,
	JpaSpecificationExecutor<OutsideUser> {
	/**
	 * 根据iid查找个人用户
	 * @param iid
	 * @return
	 */
	OutsideUser findByIid(Integer iid);
}
