package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.gsww.uids.entity.JisSysview;


	public interface JisSysviewDao extends PagingAndSortingRepository<JisSysview, String>,JpaSpecificationExecutor<JisSysview> {

		JisSysview findByIid(Integer iid);

		List<JisSysview> findAll();
	}


