package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import com.gsww.uids.entity.JisSysviewHistory;

public interface JisSysviewHistoryService {
	

		public Page<JisSysviewHistory> getJisPage(Specification<JisSysviewHistory> spec,PageRequest pageRequest);
		/**
		 * 方法描述 : 根据主键查询实体
		 * @param parseInt
		 * @return
		 * @throws Exception
		 */
		public JisSysviewHistory findByIid(Integer iid) throws Exception;
		
}