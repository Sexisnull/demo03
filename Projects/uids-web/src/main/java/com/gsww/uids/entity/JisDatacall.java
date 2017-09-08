package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 数据调用实体类
 * @author Seven
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JIS_DATACALL")
public class JisDatacall implements java.io.Serializable{
	private int iid;         	 //主键id
	private String resName;   	 //名称
	private String resUrl;   	 //url地址
	private int callingType; 	 //调用方式,0：iframe调用,1：rss调用
	private int isVerification;  //是否验证
	private int orderType;   	 //排序方式
	private String content;  	 //rss模板样式
	private String timeFormat;   //时间格式 
	private int appId;  		 //应用id
	private String remark; 		 //标识
	private int infoNum;   		 //信息数量
	
	public JisDatacall() {
		super();
		// TODO Auto-generated constructor stub
	}

	public JisDatacall(int iid, String resName, String resUrl, int callingType,
			int isVerification, int orderType, String content,
			String timeFormat, int appId, String remark, int infoNum) {
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
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "IID", unique = true, nullable = false)
	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
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
	public int getCallingType() {
		return callingType;
	}

	public void setCallingType(int callingType) {
		this.callingType = callingType;
	}

	@Column(name = "ISVERIFICATION")
	public int getIsVerification() {
		return isVerification;
	}

	public void setIsVerification(int isVerification) {
		this.isVerification = isVerification;
	}

	@Column(name = "ORDERTYPE")
	public int getOrderType() {
		return orderType;
	}

	public void setOrderType(int orderType) {
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
	public int getAppId() {
		return appId;
	}

	public void setAppId(int appId) {
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
	public int getInfoNum() {
		return infoNum;
	}

	public void setInfoNum(int infoNum) {
		this.infoNum = infoNum;
	}
}
