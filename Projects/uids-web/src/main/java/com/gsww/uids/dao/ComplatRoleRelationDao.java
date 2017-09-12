package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.SysRoleAcctRel;
<<<<<<< HEAD
import com.gsww.uids.entity.ComplatRoleRelation;
=======
import com.gsww.uids.entity.ComplatRolerelation;
>>>>>>> 9b9ebf74bfed4c28637b43429fa8aca33d294949

public interface ComplatRoleRelationDao extends PagingAndSortingRepository<ComplatRolerelation, Integer>,
JpaSpecificationExecutor<ComplatRolerelation>{
	List<ComplatRolerelation> findByUserId(String userId);

	List<ComplatRolerelation> findByRoleId(Integer roleId);

}
