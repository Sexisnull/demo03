package com.gsww.uids.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.JisDatacall;

/**
 * 数据调用模块Service层
 * @author Seven
 *
 */
public interface JisDatacallService {
	
	/**
	 * 保存或修改对象
	 * @param jisDatacall
	 * @throws Exception
	 */
	public JisDatacall save(JisDatacall entity) throws Exception;
	
	/**
	 * 删除单个对象
	 * @param jisDatacall
	 * @throws Exception
	 */
	public String delete(JisDatacall entity) throws Exception;
	
	/**
	 * 根据主键查找对象
	 * @param iid
	 * @throws Exception
	 */
	public JisDatacall findByKey(String iid) throws Exception;
	
	/**
	 * 根据标识获得数据调用对象
	 * @param remark
	 * @throws Exception
	 */
	public JisDatacall findByRemark(String remark) throws Exception;
	
	/**
	 * 
	 * 方法描述 : 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<JisDatacall> getDatacallPage(Specification<JisDatacall> spec, PageRequest pageRequest);
	
	/**
	 * 判断标识是否存在
	 * @param remark
	 * @return
	 * @throws Exception
	 */
	public JisDatacall queryRemarkIsUsed(String remark)throws Exception;
}
