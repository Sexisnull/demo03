package com.gsww.uids.service;

import com.gsww.uids.entity.JisUserdefined;

public interface JisUserdefinedService {
	
	/**
	 * 根据应用id和登陆全名称获取自定义账号
	 * @param appid
	 * @param loginAllName
	 * @return
	 */
	public JisUserdefined findByAppidAndLoginAllName(Integer appid,String loginAllName);
	
	/**
	 * 根据主键获取对象
	 * @param parseInt
	 * @return
	 */
	public JisUserdefined findByKey(int parseInt) throws Exception;

	public void save(JisUserdefined defined) throws Exception;
}
