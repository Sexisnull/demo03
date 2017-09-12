package com.gsww.uids.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

<<<<<<< HEAD
import com.gsww.uids.entity.JisHistory;
=======
>>>>>>> 0f1e907c2c1b5c7934a70a80948604ffa7ebc6cb
import com.gsww.uids.entity.JisSysview;


	public interface JisSysviewDao extends PagingAndSortingRepository<JisSysview, String>,JpaSpecificationExecutor<JisSysview> {

		JisSysview findByIid(Integer iid);

		List<JisSysview> findAll();
	}


