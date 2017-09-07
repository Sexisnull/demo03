package com.gsww.jup.dao.sys;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.gsww.jup.entity.sys.VSysParameter;

public interface VSysParameterDao extends PagingAndSortingRepository<VSysParameter, Integer>,JpaSpecificationExecutor<VSysParameter>{

	public List<VSysParameter> findByParaTypeDesc(String paraTypeDesc,Sort sort);
	
	VSysParameter findByParaCodeAndParaTypeDesc(String paraCode,String paraTypeDesc);
	
	/**
	 * 方法描述 : 根据主键查询实体
	 * @param paraId
	 * @return
	 */
	VSysParameter findByParaId(Integer paraId);
	
}
