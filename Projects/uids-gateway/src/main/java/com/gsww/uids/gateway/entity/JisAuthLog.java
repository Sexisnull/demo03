package com.gsww.uids.gateway.entity;

import java.io.Serializable;
import java.util.Date;
/**
 * 实体类-JisAuthLog-jis_authlog
 * @author zcc
 *
 */
public class JisAuthLog implements Serializable {
	private static final long serialVersionUID = 6960861914952175611L;
	private Integer iid;

	private Integer usertype;

	private Integer authtype;

	private Integer userId;

	private String loginname;

	private String username;

	private Integer appid;

	private String appmark;

	private String ticket;

	private String token;

	private Integer state;

	private Date createtime;

	private Date outtickettime;

	private Date outtokentime;

	private String spec;

	public JisAuthLog() {
	}

	public JisAuthLog(Integer iid, Integer usertype, Integer authtype, Integer userId, String loginname,
			String username, Integer appid, String appmark, String ticket, String token, Integer state, Date createtime,
			Date outtickettime, Date outtokentime, String spec) {
		super();
		this.iid = iid;
		this.usertype = usertype;
		this.authtype = authtype;
		this.userId = userId;
		this.loginname = loginname;
		this.username = username;
		this.appid = appid;
		this.appmark = appmark;
		this.ticket = ticket;
		this.token = token;
		this.state = state;
		this.createtime = createtime;
		this.outtickettime = outtickettime;
		this.outtokentime = outtokentime;
		this.spec = spec;
	}

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public Integer getUsertype() {
		return usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	public Integer getAuthtype() {
		return authtype;
	}

	public void setAuthtype(Integer authtype) {
		this.authtype = authtype;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAppid() {
		return appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	public String getAppmark() {
		return appmark;
	}

	public void setAppmark(String appmark) {
		this.appmark = appmark;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public Date getOuttickettime() {
		return outtickettime;
	}

	public void setOuttickettime(Date outtickettime) {
		this.outtickettime = outtickettime;
	}

	public Date getOuttokentime() {
		return outtokentime;
	}

	public void setOuttokentime(Date outtokentime) {
		this.outtokentime = outtokentime;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

}