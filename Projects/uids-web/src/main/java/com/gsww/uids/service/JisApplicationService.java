package com.gsww.uids.service;

import java.util.List;
import java.util.Map;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.JisApplication;

/**
 * 应用表service接口
 * @author zhanglei
 *
 */
public interface JisApplicationService {

	/**
	 * 保存或修改对象
	 * @param jisApplication
	 * @throws Exception
	 */
	public JisApplication save(JisApplication entity) throws Exception;
	
	/**
	 * 删除单个对象
	 * @param jisApplication
	 * @throws Exception
	 */
	public String delete(JisApplication entity) throws Exception;
	
	/**
	 * 根据主键查找对象
	 * @param iid
	 * @throws Exception
	 */
	public JisApplication findByKey(Integer iid) throws Exception;
	
	/**
	 * 根据标识获得应用对象
	 * @param mark
	 * @throws Exception
	 */
	public JisApplication findByMark(String mark) throws Exception;
	
	/**
	 * 
	 * 方法描述 : 分页描述
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<JisApplication> getApplicationPage(Specification<JisApplication> spec, PageRequest pageRequest);
	
	/**
	 * 判断标识是否存在
	 * @param mark
	 * @return
	 * @throws Exception
	 */
	public JisApplication queryMarkIsUsed(String mark)throws Exception;
	
	/**
	 * 根据组织获得机构信息
	 * @param sysUser
	 * @return
	 * @throws Exception
	 */
	/*public List<JisApplication> findApplicationListByUser(SysUser sysUser)throws Exception;*/
	
	/**
	 * 查询所有应用
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> getJisApplicationList() throws Exception;
		
	/**
	 * 查询所有应用
	 * @return
	 * @throws Exception
	 */
	public List<JisApplication> findAll();
}
