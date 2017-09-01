package com.gsww.uids.manager.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gsww.common.entity.PageObject;
import com.gsww.uids.manager.sys.entity.OperationLog;

/**
 * 日志业务层接口
 * 
 * @author sunbw
 *
 */
@Service
public interface LogService {
	
	/**
	 * 保存、修改
	 * @param info
	 * @return
	 */
	public boolean saveOrUpdate(OperationLog info);
	
	/**
	 * 查询全部列表
	 */
	public List<OperationLog> findAll();
	
	/**
	 * 检查可不可以删除
	 * @param ids uuid组成的字符串
	 * @return
	 */
	
	public OperationLog findById(String id);
	
	/**
	 * 
	 * @param page 页数
	 * @param size  大小
	 * @param module 模块名称
	 * @param logDesc 描述
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @return
	 */
	public PageObject<OperationLog> findPage(Integer page, Integer size,String module,
			String logDesc,String startTime,String endTime);
	
	/**
	 * 根据账号名查询上次登录时间
	 * @param account
	 * @return
	 */
	public String findLastTimeLoginTimeByAccountName(String accountName);
	
}
