package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisCurrent;
import com.gsww.uids.entity.JisHistory;


	public interface JisHistoryDao extends PagingAndSortingRepository<JisHistory, String>,JpaSpecificationExecutor<JisHistory> {

		JisHistory findByObjectId(String objectId);

		List<JisHistory> findAll();
	}


