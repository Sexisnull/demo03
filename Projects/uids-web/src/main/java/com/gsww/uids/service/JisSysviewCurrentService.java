package com.gsww.uids.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.jup.entity.sys.SysParaType;
import com.gsww.uids.entity.JisSysviewCurrent;
import com.gsww.uids.entity.JisSysviewDetail;

public interface JisSysviewCurrentService {

	public Page<JisSysviewCurrent> getJisPage(Specification<JisSysviewCurrent> spec,PageRequest pageRequest);
	/**
	 * 方法描述 : 根据主键查询实体
	 * @param parseInt
	 * @return
	 * @throws Exception
	 */
	public List<JisSysviewCurrent> findJisCurList() throws Exception;
	
	public void delete(JisSysviewCurrent entity) throws Exception;
	
	public JisSysviewCurrent findByIid(Integer iid)throws Exception;
	
	
}
