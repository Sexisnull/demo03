package com.gsww.uids.dao;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.uids.entity.ComplatGroup;

public interface ComplatGroupDao extends  PagingAndSortingRepository<ComplatGroup, String>,JpaSpecificationExecutor<ComplatGroup>{

}
