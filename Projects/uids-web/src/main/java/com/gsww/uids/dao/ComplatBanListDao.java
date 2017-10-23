package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatBanlist;

public interface ComplatBanListDao extends PagingAndSortingRepository<ComplatBanlist, Integer>,JpaSpecificationExecutor<ComplatBanlist> {
	
	ComplatBanlist findByIid(Integer iid);

	List<ComplatBanlist> findAll();

	ComplatBanlist findByLoginnameAndIpaddrAndUsertype(String loginallname,
			String ipAddr, Integer userType);

}
