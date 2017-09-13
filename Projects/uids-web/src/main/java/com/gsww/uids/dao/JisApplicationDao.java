package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisApplication;
<<<<<<< HEAD

public interface JisApplicationDao extends PagingAndSortingRepository<JisApplication, Integer>,JpaSpecificationExecutor<JisApplication>{
     
	
=======
/**
 * 应用表dao接口
 * @author zhanglei
 *
 */
public interface JisApplicationDao extends PagingAndSortingRepository<JisApplication, String>,
JpaSpecificationExecutor<JisApplication>{

>>>>>>> 0a7a30be8820fcb0c223016a83220645f3e1c9c1
}
