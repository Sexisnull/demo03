package com.gsww.uids.service;

import java.util.List;
import java.util.Map;




import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import com.gsww.uids.entity.JisLog;



/**
 * Copyright: Copyright (c) 2011
 * </p>
 * <p>
 * 公司名称 : 中国电信甘肃万维公司
 * </p>
 * <p>
 * 项目名称 : uids
 * </p>
 * <p>
 * 创建时间 : 2017年9月13日10:18:07
 * </p>
 * <p>
 * 类描述 :系统日志service层
 * </p>
 * 
 * @author <a href=" ">zcc</a>
 * */
public interface JisLogService {

	
	public Page<Map<String,String>> getJisLogPage(int pageNumber,int pageSize,List<List<String>> searchCodition) throws Exception;


	public void logInsert(JisLog jisLog);

	/**
	 * 查询所有日志列表
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<JisLog> findLogList() throws Exception;

	/**
	 * 
	 * 方法描述 : 日志分页查询
	 * 
	 * @param spec
	 * @param pageRequest
	 * @return
	 */
	public Page<JisLog> getJisLogPage(Specification<JisLog> spec,
			PageRequest pageRequest);

	/**
	 * 方法描述 : 根据操作类型查询
	 * 
	 * @param roleNameInput
	 * @param string
	 * @return
	 * @throws Exception
	 */
	public List<JisLog> findBySpec(String spec) throws Exception;


}
