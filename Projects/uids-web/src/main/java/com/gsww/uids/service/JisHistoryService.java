package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.JisCurrent;
import com.gsww.uids.entity.JisHistory;

public interface JisHistoryService {
	

		public Page<JisHistory> getJisPage(Specification<JisHistory> spec,PageRequest pageRequest);
		/**
		 * 方法描述 : 根据主键查询实体
		 * @param parseInt
		 * @return
		 * @throws Exception
		 */
		public JisHistory findByKey(String objectId) throws Exception;
		
}
