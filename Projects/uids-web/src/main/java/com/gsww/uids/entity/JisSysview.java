package com.gsww.uids.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "JIS_SYSVIEW")
public class JisSysview implements java.io.Serializable {

	private static final long serialVersionUID = 2492351453264347034L;

	
	private Integer iid;
	private String objectId;
	private String objectName;
	private String state;
	private String result;
	private Integer optResult;
	private Date syncTime;
	private Integer appId;
	private String codeId;
	private String operateType;
	private Integer times;
	private String errorspec;
	
	public JisSysview() {
	}

	
	public JisSysview(String objectName, String state,String result,Integer optResult,Integer appid
		,Date syncTime,Integer appId,String codeId,	String operateType,Integer times,String errorspec) {
		this.objectName = objectName;
		this.state = state;
		this.result=result;
		this.optResult=optResult;
		this.appId=appId;
		this.codeId=codeId;
		this.operateType=operateType;
		this.times=times;
		this.errorspec=errorspec;
	}
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "iid")
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}
	@Column(name = "objectid")
	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}
	@Column(name = "objectname")
	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	@Column(name = "state")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	@Column(name = "result")
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	@Column(name = "optresult")
	public Integer getOptResult() {
		return optResult;
	}

	public void setOptResult(Integer optResult) {
		this.optResult = optResult;
	}
	@Column(name = "synctime")
	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}
	@Column(name = "appid")
	public Integer getAppid() {
		return appId;
	}

	public void setAppid(Integer appId) {
		this.appId = appId;
	}
	@Column(name = "codeid")
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}
	@Column(name = "operatetype")
	public String getOperateType() {
		return operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	@Column(name = "times")
	public Integer getTimes() {
		return times;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
	@Column(name = "errorspec")
	public String getErrorspec() {
		return errorspec;
	}

	public void setErrorspec(String errorspec) {
		this.errorspec = errorspec;
	}
	
}
