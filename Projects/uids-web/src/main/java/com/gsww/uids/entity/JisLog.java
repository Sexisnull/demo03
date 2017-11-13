package com.gsww.uids.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-entity</p>
 * <p>创建时间 : 2017-09-07 上午09:46:34</p>
 * <p>类描述 : 在线用户管理实体类        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">yaoxi</a>
 */
@Entity
@Table(name = "jis_log")
public class JisLog implements java.io.Serializable {

	private static final long serialVersionUID = 2145799659997031679L;

	/**
	 * 
	 */
	
	private Integer iid;  //主键ID
	private String userId;//操作人ID
	private String ip;//操作IP
	private Date operateTime;//操作时间
	private Integer operateType;//操作类型
	private Integer moduleName;//模块名称
	private String spec;//操作描述
	private String os;//操作系统
	private String browser;//浏览器

	// Constructors

	/** default constructor */
	public JisLog() {
	}

	/** full constructor */
	public JisLog(String userId, String ip, Date operateTime,
			Integer operateType, Integer moduleName, String spec, String os,
			String browser) {
		this.userId = userId;
		this.ip = ip;
		this.operateTime = operateTime;
		this.operateType = operateType;
		this.moduleName = moduleName;
		this.spec = spec;
		this.os = os;
		this.browser = browser;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "iid")
	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	@Column(name = "userid")
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "ip")
	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Column(name = "operatetime", length = 0)
	public Date getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}

	@Column(name = "operatetype")
	public Integer getOperateType() {
		return this.operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	@Column(name = "modulename")
	public Integer getModuleName() {
		return this.moduleName;
	}

	public void setModuleName(Integer moduleName) {
		this.moduleName = moduleName;
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