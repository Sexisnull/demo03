package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.JisCurrent;
import com.gsww.uids.entity.JisHistory;
import com.gsww.uids.entity.JisSysview;

public interface JisSysviewService {
	

		public Page<JisSysview> getJisPage(Specification<JisSysview> spec,PageRequest pageRequest);
		/**
		 * 方法描述 : 根据主键查询实体
		 * @param parseInt
		 * @return
		 * @throws Exception
		 */
		public JisSysview findByKey(String objectId) throws Exception;
		
}
