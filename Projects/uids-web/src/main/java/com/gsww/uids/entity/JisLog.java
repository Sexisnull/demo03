package com.gsww.ischool.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisLog entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_log", catalog = "uidsdx")
public class JisLog implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String userid;
	private String ip;
	private Timestamp operatetime;
	private Integer operatetype;
	private Integer modulename;
	private String spec;
	private String os;
	private String browser;

	// Constructors

	/** default constructor */
	public JisLog() {
	}

	/** full constructor */
	public JisLog(String userid, String ip, Timestamp operatetime,
			Integer operatetype, Integer modulename, String spec, String os,
			String browser) {
		this.userid = userid;
		this.ip = ip;
		this.operatetime = operatetime;
		this.operatetype = operatetype;
		this.modulename = modulename;
		this.spec = spec;
		this.os = os;
		this.browser = browser;
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

	@Column(name = "userid")
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "ip")
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "operatetime", length = 0)
	public Timestamp getOperatetime() {
		return this.operatetime;
	}

	public void setOperatetime(Timestamp operatetime) {
		this.operatetime = operatetime;
	}

	@Column(name = "operatetype")
	public Integer getOperatetype() {
		return this.operatetype;
	}

	public void setOperatetype(Integer operatetype) {
		this.operatetype = operatetype;
	}

	@Column(name = "modulename")
	public Integer getModulename() {
		return this.modulename;
	}

	public void setModulename(Integer modulename) {
		this.modulename = modulename;
	}

	@Column(name = "spec")
	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Column(name = "os")
	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	@Column(name = "browser")
	public String getBrowser() {
		return this.browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

}