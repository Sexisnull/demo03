package com.gsww.uids.gateway.entity;

import java.io.Serializable;

public class Application implements Serializable {
	private static final long serialVersionUID = -9198437426281533523L;

	private Integer iid;

	private String name;

	private String mark;

	private Integer groupId = Integer.valueOf(0);

	private String spec;

	private Integer transtype = Integer.valueOf(0);

	private String encryptKey;

	private String icon = "/resources/jis/front/app/default.jpg";

	private Integer loginType = Integer.valueOf(0);

	private String appUrl;

	private String ssoUrl;

	private Integer userDefined = Integer.valueOf(0);

	private String allLoginiId;

	private String allPwd;

	private Integer encryptType = Integer.valueOf(1);

	private Integer isSyncGroup = Integer.valueOf(1);

	private Integer orderId;

	private Integer isShow = Integer.valueOf(1);

	private Integer isLogoff = Integer.valueOf(0);

	private Integer isUnifyRegister = Integer.valueOf(0);

	private Integer netType = Integer.valueOf(0);

	private String logoffUrl;
	private Integer approleid;

	public Integer getApproleid() {
		return this.approleid;
	}

	public void setApproleid(Integer approleid) {
		this.approleid = approleid;
	}

	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getEncryptKey() {
		return this.encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Integer getLoginType() {
		return this.loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	public String getAppUrl() {
		return this.appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getSsoUrl() {
		return this.ssoUrl;
	}

	public void setSsoUrl(String ssoUrl) {
		this.ssoUrl = ssoUrl;
	}

	public Integer getUserDefined() {
		return this.userDefined;
	}

	public void setUserDefined(Integer userDefined) {
		this.userDefined = userDefined;
	}

	public String getAllLoginiId() {
		return this.allLoginiId;
	}

	public void setAllLoginiId(String allLoginiId) {
		this.allLoginiId = allLoginiId;
	}

	public String getAllPwd() {
		return this.allPwd;
	}

	public void setAllPwd(String allPwd) {
		this.allPwd = allPwd;
	}

	public Integer getEncryptType() {
		return this.encryptType;
	}

	public void setEncryptType(Integer encryptType) {
		this.encryptType = encryptType;
	}

	public Integer getIsSyncGroup() {
		return this.isSyncGroup;
	}

	public void setIsSyncGroup(Integer isSyncGroup) {
		this.isSyncGroup = isSyncGroup;
	}

	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getIsShow() {
		return this.isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public Integer getTranstype() {
		return this.transtype;
	}

	public void setTranstype(Integer transtype) {
		this.transtype = transtype;
	}

	public Integer getIsLogoff() {
		return this.isLogoff;
	}

	public void setIsLogoff(Integer isLogoff) {
		this.isLogoff = isLogoff;
	}

	public String getLogoffUrl() {
		return this.logoffUrl;
	}

	public void setLogoffUrl(String logoffUrl) {
		this.logoffUrl = logoffUrl;
	}

	public Integer getIsUnifyRegister() {
		return this.isUnifyRegister;
	}

	public void setIsUnifyRegister(Integer isUnifyRegister) {
		this.isUnifyRegister = isUnifyRegister;
	}

	public Integer getNetType() {
		return this.netType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}
}
