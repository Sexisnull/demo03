package com.gsww.ischool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisDatacall entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_datacall", catalog = "uidsdx")
public class JisDatacall implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String resname;
	private String resurl;
	private Integer callingtype;
	private Integer isverification;
	private Integer appid;
	private Integer ordertype;
	private String timeformat;
	private String content;
	private String remark;
	private Integer infonum;

	// Constructors

	/** default constructor */
	public JisDatacall() {
	}

	/** full constructor */
	public JisDatacall(String resname, String resurl, Integer callingtype,
			Integer isverification, Integer appid, Integer ordertype,
			String timeformat, String content, String remark, Integer infonum) {
		this.resname = resname;
		this.resurl = resurl;
		this.callingtype = callingtype;
		this.isverification = isverification;
		this.appid = appid;
		this.ordertype = ordertype;
		this.timeformat = timeformat;
		this.content = content;
		this.remark = remark;
		this.infonum = infonum;
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

	@Column(name = "resname", length = 100)
	public String getResname() {
		return this.resname;
	}

	public void setResname(String resname) {
		this.resname = resname;
	}

	@Column(name = "resurl", length = 900)
	public String getResurl() {
		return this.resurl;
	}

	public void setResurl(String resurl) {
		this.resurl = resurl;
	}

	@Column(name = "callingtype")
	public Integer getCallingtype() {
		return this.callingtype;
	}

	public void setCallingtype(Integer callingtype) {
		this.callingtype = callingtype;
	}

	@Column(name = "isverification")
	public Integer getIsverification() {
		return this.isverification;
	}

	public void setIsverification(Integer isverification) {
		this.isverification = isverification;
	}

	@Column(name = "appid")
	public Integer getAppid() {
		return this.appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	@Column(name = "ordertype")
	public Integer getOrdertype() {
		return this.ordertype;
	}

	public void setOrdertype(Integer ordertype) {
		this.ordertype = ordertype;
	}

	@Column(name = "timeformat", length = 60)
	public String getTimeformat() {
		return this.timeformat;
	}

	public void setTimeformat(String timeformat) {
		this.timeformat = timeformat;
	}

	@Column(name = "content", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "remark", length = 100)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "infonum")
	public Integer getInfonum() {
		return this.infonum;
	}

	public void setInfonum(Integer infonum) {
		this.infonum = infonum;
	}

}