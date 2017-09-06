package com.hanweb.inner.service;

import com.hanweb.inner.entity.InnerGroup;

public class InnerGroupService {
	/**
	 *  操作信息
	 */
	private String msg = "";
	/**
	 * 无参构造函数（一定要有不然JIS调用不到）
	 */
	public InnerGroupService() {
	}
	/**
	 * 机构的新增方法
	 * @param groupEn  机构实体
	 * @return
	 */
	public boolean addGroup(InnerGroup groupEn){
		boolean bl =false;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return bl;
	}
	
	/**
	 * 机构的更新方法
	 * @param groupEn  机构实体
	 * @return
	 */
	public boolean modifyGroup(InnerGroup groupEn){
		boolean bl =false;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return bl;
	}
	/**
	 * 机构的删除方法
	 * @param groupCode  机构编码
	 * @return           
	 */
	public boolean removeGroup(String groupCode){
		boolean bl =false;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return bl;
	}
	
	/**
	 * 机构的查询方法（要返回机构实体的所有属性）
	 * @param groupCode  机构编码
	 * @return           机构实体
	 */
	public InnerGroup findGroup(String groupCode){
		InnerGroup groupEn = null;
		//相关的数据库操作和业务操作
		msg = "XXXX";//要返回给JIS的信息
		return groupEn;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	} 
}
