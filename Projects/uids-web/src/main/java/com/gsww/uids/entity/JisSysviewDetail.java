package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisSysviewDetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_sysview_detail")
public class JisSysviewDetail implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String sendmsg;
	private String respmsg;

	// Constructors

	/** default constructor */
	public JisSysviewDetail() {
	}

	/** minimal constructor */
	public JisSysviewDetail(Integer iid) {
		this.iid = iid;
	}

	/** full constructor */
	public JisSysviewDetail(Integer iid, String sendmsg, String respmsg) {
		this.iid = iid;
		this.sendmsg = sendmsg;
		this.respmsg = respmsg;
	}

	// Property accessors
	@Id
	@Column(name = "iid", unique = true, nullable = false)
	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	@Column(name = "sendmsg", length = 65535)
	public String getSendmsg() {
		return this.sendmsg;
	}

	public void setSendmsg(String sendmsg) {
		this.sendmsg = sendmsg;
	}

	@Column(name = "respmsg", length = 65535)
	public String getRespmsg() {
		return this.respmsg;
	}

	public void setRespmsg(String respmsg) {
		this.respmsg = respmsg;
	}

}