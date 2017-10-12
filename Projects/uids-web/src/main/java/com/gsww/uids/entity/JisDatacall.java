package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 数据调用实体类
 * @author Seven
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JIS_DATACALL")
public class JisDatacall implements java.io.Serializable{
	private Integer iid;         //主键id
	private String resName;   	 //名称
	private String resUrl;   	 //url地址
	private Integer callingType; //调用方式,1：iframe调用,2：rss调用
	private Integer isVerification;  //是否验证
	private Integer orderType;   	 //排序方式,1:正序,2:倒叙
	private String content;  	 //rss模板样式
	private String timeFormat;   //时间格式 
	private Integer appId;  		 //应用id
	private String remark; 		 //标识
	private Integer infoNum;   		 //信息数量
	
	public JisDatacall() {
		super();
	}

	public JisDatacall(Integer iid, String resName, String resUrl, Integer callingType,
			Integer isVerification, Integer orderType, String content,
			String timeFormat, Integer appId, String remark, Integer infoNum) {
		super();
		this.iid = iid;
		this.resName = resName;
		this.resUrl = resUrl;
		this.callingType = callingType;
		this.isVerification = isVerification;
		this.orderType = orderType;
		this.content = content;
		this.timeFormat = timeFormat;
		this.appId = appId;
		this.remark = remark;
		this.infoNum = infoNum;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IID", unique = true, nullable = false)
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	@Column(name = "RESNAME")
	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	@Column(name = "RESURL")
	public String getResUrl() {
		return resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	@Column(name = "CALLINGTYPE")
	public Integer getCallingType() {
		return callingType;
	}

	public void setCallingType(Integer callingType) {
		this.callingType = callingType;
	}

	@Column(name = "ISVERIFICATION")
	public Integer getIsVerification() {
		return isVerification;
	}

	public void setIsVerification(Integer isVerification) {
		this.isVerification = isVerification;
	}

	@Column(name = "ORDERTYPE")
	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	@Column(name = "CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "TIMEFORMAT")
	public String getTimeFormat() {
		return timeFormat;
	}

	public void setTimeFormat(String timeFormat) {
		this.timeFormat = timeFormat;
	}

	@Column(name = "APPID")
	public Integer getAppId() {
		return appId;
	}

	public void setAppId(Integer appId) {
		this.appId = appId;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "INFONUM")
	public Integer getInfoNum() {
		return infoNum;
	}

	public void setInfoNum(Integer infoNum) {
		this.infoNum = infoNum;
	}
}
