package com.gsww.uids.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


<<<<<<< HEAD
/**
 * JisSysview entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name="jis_sysview")
public class JisSysview implements java.io.Serializable {

=======
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
>>>>>>> e72a5d3d713759b3753afb26456d3d6caf3862f3

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
    public JisSysview(String objectid, String objectname, String state, String result, Integer optresult, Timestamp synctime, Integer appid, String codeid, String operatetype, Integer times, String errorspec) {
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
    @GenericGenerator(name="generator", strategy="uuid.hex")
    @Id 
    @GeneratedValue(generator="generator")
    @Column(name="iid", unique=true, nullable=false)

    public Integer getIid() {
        return this.iid;
    }
    
    public void setIid(Integer iid) {
        this.iid = iid;
    }
    
    @Column(name="objectid")

    public String getObjectid() {
        return this.objectid;
    }
    
    public void setObjectid(String objectid) {
        this.objectid = objectid;
    }
    
    @Column(name="objectname")

    public String getObjectname() {
        return this.objectname;
    }
    
    public void setObjectname(String objectname) {
        this.objectname = objectname;
    }
    
    @Column(name="state", length=1)

    public String getState() {
        return this.state;
    }
    
    public void setState(String state) {
        this.state = state;
    }
    
    @Column(name="result", length=2)

    public String getResult() {
        return this.result;
    }
    
    public void setResult(String result) {
        this.result = result;
    }
    
    @Column(name="optresult")

    public Integer getOptresult() {
        return this.optresult;
    }
    
    public void setOptresult(Integer optresult) {
        this.optresult = optresult;
    }
    
    @Column(name="synctime", length=0)

    public Timestamp getSynctime() {
        return this.synctime;
    }
    
    public void setSynctime(Timestamp synctime) {
        this.synctime = synctime;
    }
    
    @Column(name="appid")

    public Integer getAppid() {
        return this.appid;
    }
    
    public void setAppid(Integer appid) {
        this.appid = appid;
    }
    
    @Column(name="codeid", length=150)

    public String getCodeid() {
        return this.codeid;
    }
    
    public void setCodeid(String codeid) {
        this.codeid = codeid;
    }
    
    @Column(name="operatetype", length=20)

    public String getOperatetype() {
        return this.operatetype;
    }
    
    public void setOperatetype(String operatetype) {
        this.operatetype = operatetype;
    }
    
    @Column(name="times")

    public Integer getTimes() {
        return this.times;
    }
    
    public void setTimes(Integer times) {
        this.times = times;
    }
    
    @Column(name="errorspec", length=1000)

    public String getErrorspec() {
        return this.errorspec;
    }
    
    public void setErrorspec(String errorspec) {
        this.errorspec = errorspec;
    }
   
}
