package com.gsww.uids.gateway.entity;

import java.io.Serializable;
/**
 * 实体类-Application-jis_application
 * @author zcc
 *
 */
public class Application implements Serializable {
	private static final long serialVersionUID = -9198437426281533523L;

	private Integer iid;
	private String name;
	private String mark;
	private Integer groupid = Integer.valueOf(0);
	private String spec;
	private Integer transtype = Integer.valueOf(0);
	private String encryptkey;
	private String icon = "/resources/jis/front/app/default.jpg";
	private Integer logintype = Integer.valueOf(0);
	private String appurl;
	private String ssourl;
	private Integer userdefined = Integer.valueOf(0);
	private String allloginiid;
	private String allpwd;
	private Integer encrypttype = Integer.valueOf(1);
	private Integer issyncgroup = Integer.valueOf(1);
	private Integer orderid;
	private Integer isshow = Integer.valueOf(1);
	private Integer islogoff = Integer.valueOf(0);
	private Integer isunifyregister = Integer.valueOf(0);
	private Integer nettype = Integer.valueOf(0);
	private String logoffurl;

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Integer getTranstype() {
		return transtype;
	}

	public void setTranstype(Integer transtype) {
		this.transtype = transtype;
	}

	public String getEncryptkey() {
		return encryptkey;
	}

	public void setEncryptkey(String encryptkey) {
		this.encryptkey = encryptkey;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getLogintype() {
		return logintype;
	}

	public void setLogintype(Integer logintype) {
		this.logintype = logintype;
	}

	public String getAppurl() {
		return appurl;
	}

	public void setAppurl(String appurl) {
		this.appurl = appurl;
	}

	public String getSsourl() {
		return ssourl;
	}

	public void setSsourl(String ssourl) {
		this.ssourl = ssourl;
	}

	public Integer getUserdefined() {
		return userdefined;
	}

	public void setUserdefined(Integer userdefined) {
		this.userdefined = userdefined;
	}

	public String getAllloginiid() {
		return allloginiid;
	}

	public void setAllloginiid(String allloginiid) {
		this.allloginiid = allloginiid;
	}

	public String getAllpwd() {
		return allpwd;
	}

	public void setAllpwd(String allpwd) {
		this.allpwd = allpwd;
	}

	public Integer getEncrypttype() {
		return encrypttype;
	}

	public void setEncrypttype(Integer encrypttype) {
		this.encrypttype = encrypttype;
	}

	public Integer getIssyncgroup() {
		return issyncgroup;
	}

	public void setIssyncgroup(Integer issyncgroup) {
		this.issyncgroup = issyncgroup;
	}

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	public Integer getIsshow() {
		return isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}

	public Integer getIslogoff() {
		return islogoff;
	}

	public void setIslogoff(Integer islogoff) {
		this.islogoff = islogoff;
	}

	public Integer getIsunifyregister() {
		return isunifyregister;
	}

	public void setIsunifyregister(Integer isunifyregister) {
		this.isunifyregister = isunifyregister;
	}

	public Integer getNettype() {
		return nettype;
	}

	public void setNettype(Integer nettype) {
		this.nettype = nettype;
	}

	public String getLogoffurl() {
		return logoffurl;
	}

	public void setLogoffurl(String logoffurl) {
		this.logoffurl = logoffurl;
	}

}
