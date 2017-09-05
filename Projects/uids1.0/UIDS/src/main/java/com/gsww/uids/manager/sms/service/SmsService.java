package com.gsww.uids.manager.sms.service;

import com.gsww.uids.manager.sms.entity.SmsRecord;

/**
 * 短信验证
 * @author simplife
 *
 */
public interface SmsService {

	/**
	 * 保存
	 */
	void saveOrUpdate(SmsRecord info);
	
	/**
	 * 查询最新一条记录
	 */
	SmsRecord getObj(String mobile, String code);
	
	/**
	 * 根据uuid查找记录
	 * 
	 * @param uuid
	 * @return
	 */
	SmsRecord findById(String uuid);
	
	/**
	 * 批量删除记录
	 * 
	 * @param ids
	 */
	void delete(String ids);
	
	/**
	 * 每天删除当天过期的记录
	 */
	void timeClear();
}
