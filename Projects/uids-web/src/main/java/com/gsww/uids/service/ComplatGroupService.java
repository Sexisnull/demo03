package com.gsww.uids.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatGroup;

public interface ComplatGroupService {
	
	public List<ComplatGroup> findByPid(Integer pid);
	
	public ComplatGroup findByIid(Integer iid);
	
	
}
