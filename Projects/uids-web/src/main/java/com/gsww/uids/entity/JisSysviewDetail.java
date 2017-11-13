package com.gsww.uids.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisSysviewDetail entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "jis_sysview_detail")
public class JisSysviewDetail implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String sendmsg;
	private String respmsg;
	private String transcationId;

	// Constructors

	/** default constructor */
	public JisSysviewDetail() {
	}

	/** minimal constructor */
	public JisSysviewDetail(Integer iid) {
		this.iid = iid;
	}

	/** full constructor */
	public JisSysviewDetail(Integer iid, String sendmsg, String respmsg,String transcationId) {
		this.iid = iid;
		this.sendmsg = sendmsg;
		this.respmsg = respmsg;
		this.transcationId=transcationId;
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
	
	@Column(name = "transcation_id")
	public String getTranscationId() {
		return transcationId;
	}

	public void setTranscationId(String transcationId) {
		this.transcationId = transcationId;
	}

}