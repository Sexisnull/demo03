package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisParameter;

/**
 * 系统参数模块DAO层
 * @author Seven
 *
 */
public interface JisParameterDao extends PagingAndSortingRepository<JisParameter, String>,
JpaSpecificationExecutor<JisParameter>{
	
	/**
	 * 根据主键查询信息
	 * @param iid
	 * @return
	 */
	JisParameter findByIid(Integer iid);
}
