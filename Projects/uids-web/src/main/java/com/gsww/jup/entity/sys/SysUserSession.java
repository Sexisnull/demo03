/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.entity.sys;


/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-entity</p>
 * <p>创建时间 : 2014年7月23日 下午11:18:02</p>
 * <p>类描述 :         </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */

public class SysUserSession {

	/** 
	 * serialVersionUID :  
	 */
	@SuppressWarnings("unused")
	private static final long serialVersionUID = -5325484377920023911L;

	private String accountId; 			// 用户登录帐号ID
	private String loginAccount; 		// 用户登录帐号
	private String userName;			// 用户名称
	private String userSex;
	private String setName;				// 帐套名称
	private String roleIds; 			// 用户所属角色ID
	private String roleNames;			// 角色名称
	private String roleTypes;
	private String deptId; 				// 部门id
	private String deptCode; 			// 用户所属部门ID
	private String deptName;			// 部门名称
	private String userIp; 				// 用户登录IP地址
	private String loginTime; 			// 用户登录时间
	private String defaultCss; 			// 用户所选样式
	private String userState;			// 用户状态
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSetName() {
		return setName;
	}
	public void setSetName(String setName) {
		this.setName = setName;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getLoginTime() {
		return loginTime;
	}
	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}
	public String getDefaultCss() {
		return defaultCss;
	}
	public void setDefaultCss(String defaultCss) {
		this.defaultCss = defaultCss;
	}
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	public String getRoleTypes() {
		return roleTypes;
	}
	public void setRoleTypes(String roleTypes) {
		this.roleTypes = roleTypes;
	}
	
	
}
