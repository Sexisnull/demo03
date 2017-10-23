package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisSysviewDetail;

public interface JisSysviewDetailDao extends PagingAndSortingRepository<JisSysviewDetail, String>,JpaSpecificationExecutor<JisSysviewDetail>{

	public JisSysviewDetail findByIid(Integer iid);

	public JisSysviewDetail findByTranscationId(String transcationId);
}
