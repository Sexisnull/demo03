package com.gsww.uids.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Transient;

import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ComplatBanlist entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "complat_banlist")
public class ComplatBanlist implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private Integer iid;
	private String ipaddr;
	private Timestamp logindate;
	private Integer logintimes;
	private String loginname;
	private Integer usertype;
	
	@Transient
	private boolean canLogin;
	// Constructors
	@Transient
	public boolean isCanLogin() {
		return canLogin;
	}
	@Transient
	public void setCanLogin(boolean canLogin) {
		this.canLogin = canLogin;
	}

	/** default constructor */
	public ComplatBanlist() {
	}

	/** full constructor */
	public ComplatBanlist(String ipaddr, Timestamp logindate,
			Integer logintimes, String loginname, Integer usertype) {
		this.ipaddr = ipaddr;
		this.logindate = logindate;
		this.logintimes = logintimes;
		this.loginname = loginname;
		this.usertype = usertype;
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

	@Column(name = "ipaddr")
	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	@Column(name = "logindate", length = 0)
	public Timestamp getLogindate() {
		return this.logindate;
	}

	public void setLogindate(Timestamp logindate) {
		this.logindate = logindate;
	}

	@Column(name = "logintimes")
	public Integer getLogintimes() {
		return this.logintimes;
	}

	public void setLogintimes(Integer logintimes) {
		this.logintimes = logintimes;
	}

	@Column(name = "loginname")
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "usertype")
	public Integer getUsertype() {
		return this.usertype;
	}

	public void setUsertype(Integer usertype) {
		this.usertype = usertype;
	}

}