package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisApplication;

public interface JisApplicationDao extends PagingAndSortingRepository<JisApplication, Integer>,JpaSpecificationExecutor<JisApplication>{
     
	
}
