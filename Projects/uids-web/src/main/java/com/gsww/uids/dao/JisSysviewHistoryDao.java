package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisSysviewHistory;


	public interface JisSysviewHistoryDao extends PagingAndSortingRepository<JisSysviewHistory, String>,JpaSpecificationExecutor<JisSysviewHistory> {

		JisSysviewHistory findByIid(Integer iid);

		List<JisSysviewHistory> findAll();
	}


