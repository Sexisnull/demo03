package com.gsww.uids.service;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.JisFields;
import com.gsww.uids.entity.JisUserdetail;

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
	
	
	/**
	 * 获取用户扩展属性
	 * @param fieldsList
	 * @param userId
	 * @return
	 * @throws Exception
	 * @author yaoxi
	 */
	List<Map<String, Object>> findExtendAttr(List<JisFields> fieldsList,Integer userId,Integer type) throws Exception;
	
	/**
	 * 获取扩展属性类型
	 * @return
	 */
	List<Integer> findFieldsType() throws Exception;
	
	/**
	 * 获取字符和枚举类型的属性
	 */
	
	List<JisFields> findByType(Integer type) throws Exception;
	
	/**
	 * 根据用户ID获取下拉列表的值
	 */
	Map<String,Object> findByUserIdAndType(List<JisFields> fieldsList,Integer userId) throws Exception;
	
	/**
     * @discription   根据fieldname查找用户扩展属性实体集合 
     * @param fieldname
     * @return
	 */
	List<JisFields> findByFieldname(String fieldname);
	
	
	/**
	 * 查询依据Iid查询fieldname字段的所有值
	 */
	List<Map<String,Object>> findFieldName();
	
}
