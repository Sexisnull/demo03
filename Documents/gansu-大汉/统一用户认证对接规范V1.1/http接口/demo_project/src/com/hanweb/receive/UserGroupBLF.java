package com.hanweb.receive;

import java.util.Map;

public class UserGroupBLF {

	/**
	 * 1 新增机构 
	 * 可用的key:result,domainname,appname ,state,Domainid
	 * @param map
	 * @return true = 操作成功 false=操作失败
	 */
	public boolean doGroupInsertExcute(Map map) {
		return false;
	}

	/**
	 * 2修改机构 
	 * 可用的key:result,domainname,appname ,state,Domainid
	 * @param map
	 * @return true = 操作成功 false=操作失败
	 */
	public boolean doGroupUpdateExcute(Map map) {
		return false;
	}

	/**
	 * 3 删除机构 
	 * 可用的key:result,state,Domainid
	 * @param map
	 * @return true = 操作成功 false=操作失败
	 */
	public boolean doGroupDeleteExcute(Map map) {
		return false;
	}
	
	/**
	 * 4 判断机构是否存在 
	 * 可用的key:result,domainname,appname ,state,Domainid
	 * @param map
	 * @return true = 操作成功 false=操作失败
	 */
	public boolean isGroupExist(Map map) {
		return false;
	}

	/**
	 * 5  新增用户 
	 * 可用的key: result,pardomainname,state,loginpass,Domainid
	 * domainname,email,appname,loginuser,t_name,alldomainname,mobile
	 * @param map
	 * @return true = 操作成功 false=操作失败
	 */
	public boolean doUserInsertExcute(Map map) {
		return false;
	}
	
	/**
	 * 6 修改用户 
	 * 可用的key: result,pardomainname,state,loginpass,Domainid
	 * domainname,email,appname,loginuser,t_name,alldomainname,mobile
	 * @param map
	 * @return true = 操作成功 false=操作失败
	 */
	public boolean doUserUpdateExcute(Map map) {
		return false;
	}
	
	/**
	 * 7  删除用户 
	 * 可用的key：result,state,loginuser
	 * @param map
	 * @return true = 操作成功 false=操作失败
	 */
	public boolean doUserDeleteExcute(Map map) {
		return false;
	}
	
	/**
	 * 8 判断用户是否存在 
	 * 可用的key: result,pardomainname,state,loginpass,Domainid
	 * domainname,email,appname,loginuser,t_name,alldomainname,mobile
	 * @param map
	 * @return true = 操作成功 false=操作失败
	 */
	public boolean isUserExist(Map map) {
		return false;
	}

	/**
	 * 9 判断用户是否能能够登录 
	 *  可用的key: loginpass,loginuser
	 * @param map
	 * @return true = 操作成功 false=操作失败
	 */
	public boolean isCanPass(Map map) {
		return true;
	}
}