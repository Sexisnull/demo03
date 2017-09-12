package com.gsww.jup.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.ComplatRoleRelation;
import com.gsww.jup.entity.sys.SysRoleAcctRel;

public interface ComplatRoleRelationDao extends PagingAndSortingRepository<ComplatRoleRelation, Integer>,
JpaSpecificationExecutor<ComplatRoleRelation>{
	List<ComplatRoleRelation> findByUserId(String userId);

	List<ComplatRoleRelation> findByRoleId(Integer roleId);

}
