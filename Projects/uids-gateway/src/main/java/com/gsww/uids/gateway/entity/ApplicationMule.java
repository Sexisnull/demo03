package com.gsww.uids.gateway.entity;

/**
 * 应用实体类
 * @author zhl
 *
 */
public class ApplicationMule {
	
	private String state; //同步类型
	private String appname; //应用标识
	private String enckey; //密钥
	private String sysurl; //系统路径
	private String ldapurl; //应用标识
	private String encrypttype; //加密方式
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getAppname() {
		return appname;
	}
	public void setAppname(String appname) {
		this.appname = appname;
	}
	public String getEnckey() {
		return enckey;
	}
	public void setEnckey(String enckey) {
		this.enckey = enckey;
	}
	public String getSysurl() {
		return sysurl;
	}
	public void setSysurl(String sysurl) {
		this.sysurl = sysurl;
	}
	public String getLdapurl() {
		return ldapurl;
	}
	public void setLdapurl(String ldapurl) {
		this.ldapurl = ldapurl;
	}
	public String getEncrypttype() {
		return encrypttype;
	}
	public void setEncrypttype(String encrypttype) {
		this.encrypttype = encrypttype;
	}
	
}
