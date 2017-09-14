/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.service.sys;

import com.gsww.jup.entity.sys.SysUserSession;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-core</p>
 * <p>创建时间 : 2014年7月23日 下午11:34:50</p>
 * <p>类描述 : 用户登录service        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */

public interface SysLoginService {

	/**
	 * 方法描述 : 用户登录
	 * @param userName
	 * @param password
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public SysUserSession login(String userName, String password,String groupid, String ip) throws Exception;
	
	/**
	 * 方法描述 : CAS用户登录
	 * @param userName
	 * @param password
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	public SysUserSession login(String userName) throws Exception;

}
