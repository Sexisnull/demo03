package com.gsww.uids.entity;

<<<<<<< HEAD
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
=======
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisSysview entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_sysview", catalog = "uidsdx")
public class JisSysview implements java.io.Serializable {

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
	public JisSysview() {
	}

	/** full constructor */
	public JisSysview(String objectid, String objectname, String state,
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
>>>>>>> 0e9ad56ac2c05f81c4c1aad0ed1e9c2903c8a34c
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}
<<<<<<< HEAD
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
=======

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
>>>>>>> 0e9ad56ac2c05f81c4c1aad0ed1e9c2903c8a34c
	}

	public void setState(String state) {
		this.state = state;
	}
<<<<<<< HEAD
	@Column(name = "result")
	public String getResult() {
		return result;
=======

	@Column(name = "result", length = 2)
	public String getResult() {
		return this.result;
>>>>>>> 0e9ad56ac2c05f81c4c1aad0ed1e9c2903c8a34c
	}

	public void setResult(String result) {
		this.result = result;
	}
<<<<<<< HEAD
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
=======

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
>>>>>>> 0e9ad56ac2c05f81c4c1aad0ed1e9c2903c8a34c
	}

	public void setTimes(Integer times) {
		this.times = times;
	}
<<<<<<< HEAD
	@Column(name = "errorspec")
	public String getErrorspec() {
		return errorspec;
=======

	@Column(name = "errorspec", length = 1000)
	public String getErrorspec() {
		return this.errorspec;
>>>>>>> 0e9ad56ac2c05f81c4c1aad0ed1e9c2903c8a34c
	}

	public void setErrorspec(String errorspec) {
		this.errorspec = errorspec;
	}
<<<<<<< HEAD
	
}
=======

}
>>>>>>> 0e9ad56ac2c05f81c4c1aad0ed1e9c2903c8a34c
