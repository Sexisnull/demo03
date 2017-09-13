package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisLog;

public interface JisLogDao extends PagingAndSortingRepository<JisLog, String>,
		JpaSpecificationExecutor<JisLog> {

	/**
	 * 查询所有日志
	 */
	List<JisLog> findAll();

	/**
	 * 方法描述 : 根据操作类型查询
	 * 
	 * @param spec
	 * @return
	 */
	List<JisLog> findBySpec(String spec);

}
