package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysApps;
import com.gsww.jup.entity.sys.SysOperator;
import com.gsww.uids.entity.JisCurrent;


public interface JisCurrentDao extends PagingAndSortingRepository<JisCurrent, Integer>,JpaSpecificationExecutor<JisCurrent> {

	JisCurrent findByObjectId(String objectId);
	
	JisCurrent findByIid(Integer iid);

	List<JisCurrent> findAll();

}
