package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisUserdefined entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_userdefined")
public class JisUserdefined implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String loginname;
	private Integer appid;
	private String apploginname;
	private String apppwd;

	// Constructors

	/** default constructor */
	public JisUserdefined() {
	}

	/** full constructor */
	public JisUserdefined(String loginname, Integer appid, String apploginname,
			String apppwd) {
		this.loginname = loginname;
		this.appid = appid;
		this.apploginname = apploginname;
		this.apppwd = apppwd;
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

	@Column(name = "loginname")
	public String getLoginname() {
		return this.loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	@Column(name = "appid")
	public Integer getAppid() {
		return this.appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	@Column(name = "apploginname")
	public String getApploginname() {
		return this.apploginname;
	}

	public void setApploginname(String apploginname) {
		this.apploginname = apploginname;
	}

	@Column(name = "apppwd")
	public String getApppwd() {
		return this.apppwd;
	}

	public void setApppwd(String apppwd) {
		this.apppwd = apppwd;
	}

}