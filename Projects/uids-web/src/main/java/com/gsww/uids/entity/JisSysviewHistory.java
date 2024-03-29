package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisSysviewHistory entity. @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "jis_sysview_history")
public class JisSysviewHistory implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String objectid;
	private String objectname;
	private String state;
	private String result;
	private Integer optresult;
	private String synctime;
	private Integer appid;
	private String codeid;
	private String operatetype;
	private Integer times;
	private String errorspec;
	private String transcationId;

	// Constructors

	/** default constructor */
	public JisSysviewHistory() {
	}

	/** full constructor */
	public JisSysviewHistory(String objectid, String objectname, String state,
			String result, Integer optresult, String synctime,
			Integer appid, String codeid, String operatetype, Integer times,
			String errorspec,String transcationId) {
		this.objectid = objectid;
		this.objectname = objectname;
		this.state = state;
		this.result = result;
		this.optresult = optresult;
		this.synctime = synctime;
		this.appid = appid;
		this.codeid = codeid;
		this.operatetype = operatetype;
		this.times = times;
		this.errorspec = errorspec;
		this.transcationId = transcationId;
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

	@Column(name = "objectid")
	public String getObjectid() {
		return this.objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	@Column(name = "objectname")
	public String getObjectname() {
		return this.objectname;
	}

	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}

	@Column(name = "state", length = 1)
	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Column(name = "result", length = 2)
	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	@Column(name = "optresult")
	public Integer getOptresult() {
		return this.optresult;
	}

	public void setOptresult(Integer optresult) {
		this.optresult = optresult;
	}

	@Column(name = "synctime", length = 0)
	public String getSynctime() {
		return this.synctime;
	}

	public void setSynctime(String synctime) {
		this.synctime = synctime;
	}

	@Column(name = "appid")
	public Integer getAppid() {
		return this.appid;
	}

	public void setAppid(Integer appid) {
		this.appid = appid;
	}

	@Column(name = "codeid", length = 150)
	public String getCodeid() {
		return this.codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	@Column(name = "operatetype", length = 20)
	public String getOperatetype() {
		return this.operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	@Column(name = "times")
	public Integer getTimes() {
		return this.times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	@Column(name = "errorspec", length = 1000)
	public String getErrorspec() {
		return this.errorspec;
	}

	public void setErrorspec(String errorspec) {
		this.errorspec = errorspec;
	}

	@Column(name = "transcation_id")
	public String getTranscationId() {
		return transcationId;
	}

	public void setTranscationId(String transcationId) {
		this.transcationId = transcationId;
	}
	
}