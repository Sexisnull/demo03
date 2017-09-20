package com.gsww.uids.gateway.entity;

import java.io.Serializable;
import java.util.Date;

public class JisAuthLog implements Serializable {
	private static final long serialVersionUID = 6960861914952175611L;
	private Integer iid;

	private Integer userType;

	private Integer authType;

	private Integer userId;

	private String loginName;

	private String userName;

	private Integer appId;

	private String appmark;

	private String ticket;

	private String token;

	private Integer state;

	private Date createTime;

	private Date outTicketTime;

	private Date outTokenTime;

	private String spec;

	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getAuthType() {
		return this.authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAppId() {
		return this.appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	public String getAppmark() {
		return this.appmark;
	}

	public void setAppmark(String appmark) {
		this.appmark = appmark;
	}

	public String getTicket() {
		return this.ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getOutTicketTime() {
		return this.outTicketTime;
	}

	public void setOutTicketTime(Date outTicketTime) {
		this.outTicketTime = outTicketTime;
	}

	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getOutTokenTime() {
		return this.outTokenTime;
	}

	public void setOutTokenTime(Date outTokenTime) {
		this.outTokenTime = outTokenTime;
	}
}