package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisDatacall;

/**
 * 数据调用模块DAO层
 * @author Seven
 *
 */
public interface JisDatacallDao extends PagingAndSortingRepository<JisDatacall, String>,
JpaSpecificationExecutor<JisDatacall>{
	
	/**
	 * 根据主键查询信息
	 * @param iid
	 * @return
	 */
	JisDatacall findByIid(String iid);
	
	/**
	 * 根据标识获得数据调用对象
	 * @param remark
	 * @return
	 */
	List<JisDatacall> findByRemark(String remark);
}
