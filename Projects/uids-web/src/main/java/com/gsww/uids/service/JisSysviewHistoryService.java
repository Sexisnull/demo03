package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

<<<<<<< HEAD:Projects/uids-web/src/main/java/com/gsww/uids/service/JisHistoryService.java
import com.gsww.uids.entity.JisHistory;
=======
import com.gsww.uids.entity.JisSysviewHistory;
>>>>>>> 0f1e907c2c1b5c7934a70a80948604ffa7ebc6cb:Projects/uids-web/src/main/java/com/gsww/uids/service/JisSysviewHistoryService.java

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
