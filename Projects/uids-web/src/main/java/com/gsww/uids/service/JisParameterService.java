package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.JisParameter;

/**
 * 系统参数模块Service层
 * @author Seven
 *
 */
public interface JisParameterService {
	
	/**
	 * 保存或修改对象
	 * @param jisParameter
	 * @throws Exception
	 */
	public JisParameter save(JisParameter entity) throws Exception;
	
	/**
	 * 根据主键查找对象
	 * @param iid
	 * @throws Exception
	 */
	public JisParameter findByKey(Integer iid) throws Exception;
	
	/**
	 * 
	 * 方法描述 : 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<JisParameter> getParameterPage(Specification<JisParameter> spec, PageRequest pageRequest);
}
