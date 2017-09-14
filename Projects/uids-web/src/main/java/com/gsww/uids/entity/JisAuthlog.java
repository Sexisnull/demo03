package com.gsww.uids.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisAuthlog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_authlog", catalog = "uidsdx")
public class JisAuthlog implements java.io.Serializable {

	// Fields

	private Integer iid;
	private Integer usertype;
	private Integer authtype;
	private Integer userid;
	private String loginname;
	private String username;
	private Integer appid;
	private String appmark;
	private String ticket;
	private String token;
	private Integer state;
	private Timestamp createtime;
	private Timestamp outtime;
	private String spec;

	// Constructors

	/** default constructor */
	public JisAuthlog() {
	}

	/** full constructor */
	public JisAuthlog(Integer usertype, Integer authtype, Integer userid,
			String loginname, String username, Integer appid, String appmark,
			String ticket, String token, Integer state, Timestamp createtime,
			Timestamp outtime, String spec) {
		this.usertype = usertype;
		this.authtype = authtype;
		this.userid = userid;
		this.loginname = loginname;
		this.username = username;
		this.appid = appid;
		this.appmark = appmark;
		this.ticket = ticket;
		this.token = token;
		this.state = state;
		this.createtime = createtime;
		this.outtime = outtime;
		this.spec = spec;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "iid", unique = true, nullable = false)
	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	@Column(name = "usertype")
	public Integer getUsertype() {
		return this.usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

	@Column(name = "authtype")
	public Integer getAuthtype() {
		return this.authtype;
	}

	public void setAuthtype(Integer authtype) {
		this.authtype = authtype;
	}

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "loginname")
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "username")
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "appid")
	public Integer getAppid() {
		return this.appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	@Column(name = "appmark")
	public String getAppmark() {
		return this.appmark;
	}

	public void setAppmark(String appmark) {
		this.appmark = appmark;
	}

	@Column(name = "ticket")
	public String getTicket() {
		return this.ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Column(name = "token")
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name = "state")
	public Integer getState() {
		return this.state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Column(name = "createtime", length = 0)
	public Timestamp getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	@Column(name = "outtime", length = 0)
	public Timestamp getOuttime() {
		return this.outtime;
	}

	public void setOuttime(Timestamp outtime) {
		this.outtime = outtime;
	}

	@Column(name = "spec")
	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

}