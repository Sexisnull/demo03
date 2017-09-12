package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

<<<<<<< HEAD
import com.gsww.uids.entity.JisHistory;
=======
>>>>>>> 0f1e907c2c1b5c7934a70a80948604ffa7ebc6cb
import com.gsww.uids.entity.JisSysview;

public interface JisSysviewService {
	

		public Page<JisSysview> getJisPage(Specification<JisSysview> spec,PageRequest pageRequest);
		/**
		 * 方法描述 : 根据主键查询实体
		 * @param parseInt
		 * @return
		 * @throws Exception
		 */
		public JisSysview findByIid(Integer iid) throws Exception;
		
}
