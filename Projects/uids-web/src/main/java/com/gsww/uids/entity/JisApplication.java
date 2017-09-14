package com.gsww.uids.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisApplication entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_application", catalog = "uidsdx")
public class JisApplication implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String name;
	private String mark;
	private String spec;
	private Integer transtype;
	private String encryptkey;
	private String icon;
	private Integer logintype;
	private String appurl;
	private String ssourl;
	private Integer userdefined;
	private String allloginiid;
	private String allpwd;
	private Integer encrypttype;
	private Integer issyncgroup;
	private Integer orderid;
	private Integer isshow;
	private Integer islogoff;
	private Integer isunifyregister;
	private String logoffurl;
	private Integer nettype;

	// Constructors

	/** default constructor */
	public JisApplication() {
	}

	/** full constructor */
	public JisApplication(String name, String mark, String spec,
			Integer transtype, String encryptkey, String icon,
			Integer logintype, String appurl, String ssourl,
			Integer userdefined, String allloginiid, String allpwd,
			Integer encrypttype, Integer issyncgroup, Integer orderid,
			Integer isshow, Integer islogoff, Integer isunifyregister,
			String logoffurl, Integer nettype) {
		this.name = name;
		this.mark = mark;
		this.spec = spec;
		this.transtype = transtype;
		this.encryptkey = encryptkey;
		this.icon = icon;
		this.logintype = logintype;
		this.appurl = appurl;
		this.ssourl = ssourl;
		this.userdefined = userdefined;
		this.allloginiid = allloginiid;
		this.allpwd = allpwd;
		this.encrypttype = encrypttype;
		this.issyncgroup = issyncgroup;
		this.orderid = orderid;
		this.isshow = isshow;
		this.islogoff = islogoff;
		this.isunifyregister = isunifyregister;
		this.logoffurl = logoffurl;
		this.nettype = nettype;
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

	@Column(name = "name", length = 60)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "mark", length = 50)
	public String getMark() {
		return this.mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Column(name = "spec")
	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Column(name = "transtype")
	public Integer getTranstype() {
		return this.transtype;
	}

	public void setTranstype(Integer transtype) {
		this.transtype = transtype;
	}

	@Column(name = "encryptkey", length = 20)
	public String getEncryptkey() {
		return this.encryptkey;
	}

	public void setEncryptkey(String encryptkey) {
		this.encryptkey = encryptkey;
	}

	@Column(name = "icon")
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "logintype")
	public Integer getLogintype() {
		return this.logintype;
	}

	public void setLogintype(Integer logintype) {
		this.logintype = logintype;
	}

	@Column(name = "appurl")
	public String getAppurl() {
		return this.appurl;
	}

	public void setAppurl(String appurl) {
		this.appurl = appurl;
	}

	@Column(name = "ssourl")
	public String getSsourl() {
		return this.ssourl;
	}

	public void setSsourl(String ssourl) {
		this.ssourl = ssourl;
	}

	@Column(name = "userdefined")
	public Integer getUserdefined() {
		return this.userdefined;
	}

	public void setUserdefined(Integer userdefined) {
		this.userdefined = userdefined;
	}

	@Column(name = "allloginiid", length = 50)
	public String getAllloginiid() {
		return this.allloginiid;
	}

	public void setAllloginiid(String allloginiid) {
		this.allloginiid = allloginiid;
	}

	@Column(name = "allpwd", length = 50)
	public String getAllpwd() {
		return this.allpwd;
	}

	public void setAllpwd(String allpwd) {
		this.allpwd = allpwd;
	}

	@Column(name = "encrypttype")
	public Integer getEncrypttype() {
		return this.encrypttype;
	}

	public void setEncrypttype(Integer encrypttype) {
		this.encrypttype = encrypttype;
	}

	@Column(name = "issyncgroup")
	public Integer getIssyncgroup() {
		return this.issyncgroup;
	}

	public void setIssyncgroup(Integer issyncgroup) {
		this.issyncgroup = issyncgroup;
	}

	@Column(name = "orderid")
	public Integer getOrderid() {
		return this.orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}

	@Column(name = "isshow")
	public Integer getIsshow() {
		return this.isshow;
	}

	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}

	@Column(name = "islogoff")
	public Integer getIslogoff() {
		return this.islogoff;
	}

	public void setIslogoff(Integer islogoff) {
		this.islogoff = islogoff;
	}

	@Column(name = "isunifyregister")
	public Integer getIsunifyregister() {
		return this.isunifyregister;
	}

	public void setIsunifyregister(Integer isunifyregister) {
		this.isunifyregister = isunifyregister;
	}

	@Column(name = "logoffurl")
	public String getLogoffurl() {
		return this.logoffurl;
	}

	public void setLogoffurl(String logoffurl) {
		this.logoffurl = logoffurl;
	}

	@Column(name = "nettype")
	public Integer getNettype() {
		return this.nettype;
	}

	public void setNettype(Integer nettype) {
		this.nettype = nettype;
	}

}