package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.OutsideUser;


public interface OutsideUserService {

	/**
	 * 
	 * 方法描述 : 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<OutsideUser> getOutsideUserPage(Specification<OutsideUser> spec, PageRequest pageRequest);
}
