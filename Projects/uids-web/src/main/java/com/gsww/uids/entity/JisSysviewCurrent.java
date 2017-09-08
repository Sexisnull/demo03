package com.gsww.ischool.entity;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisSysviewCurrent entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_sysview_current", catalog = "uidsdx")
public class JisSysviewCurrent implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String objectid;
	private String objectname;
	private String state;
	private String result;
	private Integer optresult;
	private Timestamp synctime;
	private Integer appid;
	private String codeid;
	private String operatetype;
	private Integer times;
	private String errorspec;

	// Constructors

	/** default constructor */
	public JisSysviewCurrent() {
	}

	/** full constructor */
	public JisSysviewCurrent(String objectid, String objectname, String state,
			String result, Integer optresult, Timestamp synctime,
			Integer appid, String codeid, String operatetype, Integer times,
			String errorspec) {
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
	public Timestamp getSynctime() {
		return this.synctime;
	}

	public void setSynctime(Timestamp synctime) {
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

}