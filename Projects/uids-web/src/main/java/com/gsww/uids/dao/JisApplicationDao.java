package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.JisApplication;
/**
 * 应用表dao接口
 * @author zhanglei
 *
 */
public interface JisApplicationDao extends PagingAndSortingRepository<JisApplication, String>,JpaSpecificationExecutor<JisApplication>{
	
	
}
