package com.hanweb.inner.service;

import com.hanweb.inner.entity.InnerGroup;
import com.hanweb.inner.entity.InnerUser;

public class InnerUserService {
	/**
	 *  操作信息
	 */
	private String msg = "";
	/**
	 * 无参构造函数（一定要有不然JIS调用不到）
	 */
	public InnerUserService() {
	}
	/**
	 * 用户的新增方法
	 * @param userEn  用户实体
	 * @return
	 */
	public boolean addUser(InnerUser userEn){
		boolean bl =false;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return bl;
	}
	
	/**
	 * 用户的更新方法
	 * @param userEn  用户实体
	 * @return
	 */
	public boolean modifyUser(InnerUser userEn){
		boolean bl =false;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return bl;
	}
	/**
	 * 用户的删除方法
	 * @param loginId  登陆名称
	 * @return           
	 */
	public boolean removeUser(String loginId){
		boolean bl =false;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return bl;
	}
	
	/**
	 * 用户的查询方法（要返回用户实体的所有属性）
	 * @param loginId  登陆名称
	 * @return         用户实体
	 */
	public InnerGroup findUser(String loginId){
		InnerGroup groupEn = null;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return groupEn;
	}
	
	/**
	 * 用户的启用方法
	 * @param loginId  登陆名称
	 * @return           
	 */
	public boolean enableUser(String loginId){
		boolean bl =false;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return bl;
	}
	
	/**
	 * 用户的停用方法
	 * @param loginId  登陆名称
	 * @return           
	 */
	public boolean disableUser(String loginId){
		boolean bl =false;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return bl;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	} 
	
}
