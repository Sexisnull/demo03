package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysRoleAcctRel;
import com.gsww.uids.entity.ComplatRolerelation;

public interface ComplatRoleRelationDao extends PagingAndSortingRepository<ComplatRolerelation, Integer>,
JpaSpecificationExecutor<ComplatRolerelation>{
	List<ComplatRolerelation> findByUserId(String userId);

	List<ComplatRolerelation> findByRoleId(Integer roleId);

}
