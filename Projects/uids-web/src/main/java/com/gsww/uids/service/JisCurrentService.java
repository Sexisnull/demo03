package com.gsww.uids.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysApps;
import com.gsww.jup.entity.sys.SysParaType;
import com.gsww.uids.entity.JisCurrent;

public interface JisCurrentService {

	public Page<JisCurrent> getJisPage(Specification<JisCurrent> spec,PageRequest pageRequest);
	/**
	 * 方法描述 : 根据主键查询实体
	 * @param parseInt
	 * @return
	 * @throws Exception
	 */
	public JisCurrent findByKey(String objectId) throws Exception;
	/**
	 * 查询所有参数类型列表
	 * @return
	 * @throws Exception
	 */
	public List<JisCurrent> findJisCurList() throws Exception;
	
	
	public JisCurrent findByObjectId(String objectId) throws Exception;
	
	
	public void delete(JisCurrent entity) throws Exception;
	
	public JisCurrent findByIid(Integer iid)throws Exception;
	
	
}
