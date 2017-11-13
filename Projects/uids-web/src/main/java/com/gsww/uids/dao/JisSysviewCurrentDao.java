package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisSysviewCurrent;


public interface JisSysviewCurrentDao extends PagingAndSortingRepository<JisSysviewCurrent, Integer>,JpaSpecificationExecutor<JisSysviewCurrent> {

	
	
	JisSysviewCurrent findByIid(Integer iid);

	List<JisSysviewCurrent> findAll();

}
