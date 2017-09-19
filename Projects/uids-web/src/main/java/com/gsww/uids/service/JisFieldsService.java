package com.gsww.uids.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.JisFields;

/**
 * Title: JisFieldsService.java Description: 用户扩展属性Service
 * 
 * @author yangxia
 * @created 2017年9月12日 下午5:18:58
 */
public interface JisFieldsService {

	/**
	 * @discription 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<JisFields> getJisFieldsPage(Specification<JisFields> spec, PageRequest pageRequest);

	/**
	 * @discription 根据iid查询用户扩展属性
	 * @param iid
	 * @return
	 */
	JisFields findByKey(Integer iid);

	/**
	 * @discription 保存用户扩展属性
	 * @param jisFields
	 */
	void save(JisFields jisFields);

	/**
	 * @discription 删除用户扩展属性
	 * @param jisFields
	 */
	void delete(JisFields jisFields);
	/**
	 * @discription 查询所有用户扩展属性实体   
	 * @return
	 */
	List<JisFields> findAllJisFields();
}