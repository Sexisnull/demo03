package com.gsww.uids.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.ComplatBanlist;

public interface ComplatBanListService {
	public Page<ComplatBanlist> getComplatBanPage(Specification<ComplatBanlist> spec,PageRequest pageRequest);
	
		
	public List<ComplatBanlist> findComplatbanList() throws Exception;
	
	public void delete(ComplatBanlist entity) throws Exception;
	
	public ComplatBanlist findByIid(Integer iid)throws Exception;

}
