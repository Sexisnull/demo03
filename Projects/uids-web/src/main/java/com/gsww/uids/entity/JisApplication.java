package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 应用实体类
 * @author Seven
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "JIS_APPLICATION")
public class JisApplication implements java.io.Serializable {

	private Integer iid;                   //应用ID
	private String name;                   //应用名称
	private String mark;                   //应用标识
	private String spec;				   //描述
	private String encryptKey;             //密钥
	private String icon;                   //应用图标地址
	private Integer synUser;               //同步用户，1：同步后台、前台用户，2：只同步后台，3：不同步
	private Integer loginType;             //登录方式，0：统一用户且单点登录，1：仅单点登录
	private Integer userDefined;           //是否支持自定义登录帐号/密码，0：自定义帐号，1：固定帐号
	private String allLoginIid;            //统一帐号用户名
	private String allPwd;                 //统一帐号密码(加密)
	private Integer encryptType;           //加密方式，0：不加密，1：MD5加密，2：MD5+base64加密
	private Integer isSyncGroup;           //是否同步外网用户，0：不支持，1：支持
	private Integer orderId;               //应用排序号
	private String appUrl;                 //应用地址
	private String ssoUrl;                 //登录地址
	private Integer isShow;                //是否在前台显示，0：否，1：是
	private Integer groupId;               //所属机构
	private Integer transType;             //数据传送方式，0：HTTP，1：WebService
	private Integer isLogOff;              //是否统一注销，0：否，1：是
	private Integer isUnifyRegister;       //是否统一注册，0：否，1：是
	private String logOffUrl;              //注销地址
	private Integer netType;               //网络类型，0：外网，1：专网
	
	public JisApplication() {
		super();
	}

	public JisApplication(Integer iid, String name, String mark, String spec,
			String encryptKey, String icon,Integer synUser,Integer loginType,
			Integer userDefined, String allLoginIid, String allPwd,
			Integer encryptType, Integer isSyncGroup, Integer orderId,
			String appUrl, String ssoUrl, Integer isShow, Integer groupId,
			Integer transType,Integer isLogOff, Integer isUnifyRegister, 
			String logOffUrl,Integer netType) {
		super();
		this.iid = iid;
		this.name = name;
		this.mark = mark;
		this.spec = spec;
		this.encryptKey = encryptKey;
		this.icon = icon;
		this.synUser = synUser;
		this.loginType = loginType;
		this.userDefined = userDefined;
		this.allLoginIid = allLoginIid;
		this.allPwd = allPwd;
		this.encryptType = encryptType;
		this.isSyncGroup = isSyncGroup;
		this.orderId = orderId;
		this.appUrl = appUrl;
		this.ssoUrl = ssoUrl;
		this.isShow = isShow;
		this.groupId = groupId;
		this.transType = transType;
		this.isLogOff = isLogOff;
		this.isUnifyRegister = isUnifyRegister;
		this.logOffUrl = logOffUrl;
		this.netType = netType;
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

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "MARK")
	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	@Column(name = "SPEC")
	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Column(name = "ENCRYPTKEY")
	public String getEncryptKey() {
		return encryptKey;
	}

	public void setEncryptKey(String encryptKey) {
		this.encryptKey = encryptKey;
	}

	@Column(name = "ICON")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "SYNUSER")
	public Integer getSynUser() {
		return synUser;
	}

	public void setSynUser(Integer synUser) {
		this.synUser = synUser;
	}

	@Column(name = "LOGINTYPE")
	public Integer getLoginType() {
		return loginType;
	}

	public void setLoginType(Integer loginType) {
		this.loginType = loginType;
	}

	@Column(name = "USERDEFINED")
	public Integer getUserDefined() {
		return userDefined;
	}

	public void setUserDefined(Integer userDefined) {
		this.userDefined = userDefined;
	}

	@Column(name = "ALLLOGINIID")
	public String getAllLoginIid() {
		return allLoginIid;
	}

	public void setAllLoginIid(String allLoginIid) {
		this.allLoginIid = allLoginIid;
	}

	@Column(name = "ALLPWD")
	public String getAllPwd() {
		return allPwd;
	}

	public void setAllPwd(String allPwd) {
		this.allPwd = allPwd;
	}

	@Column(name = "ENCRYPTTYPE")
	public Integer getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(Integer encryptType) {
		this.encryptType = encryptType;
	}

	@Column(name = "ISSYNCGROUP")
	public Integer getIsSyncGroup() {
		return isSyncGroup;
	}

	public void setIsSyncGroup(Integer isSyncGroup) {
		this.isSyncGroup = isSyncGroup;
	}

	@Column(name = "ORDERID")
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "APPURL")
	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	@Column(name = "SSOURL")
	public String getSsoUrl() {
		return ssoUrl;
	}

	public void setSsoUrl(String ssoUrl) {
		this.ssoUrl = ssoUrl;
	}

	@Column(name = "ISSHOW")
	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	@Column(name = "GROUPID")
	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Column(name = "TRANSTYPE")
	public Integer getTransType() {
		return transType;
	}

	public void setTransType(Integer transType) {
		this.transType = transType;
	}

	@Column(name = "ISLOGOFF")
	public Integer getIsLogOff() {
		return isLogOff;
	}

	public void setIsLogOff(Integer isLogOff) {
		this.isLogOff = isLogOff;
	}

	@Column(name = "ISUNIFYREGISTER")
	public Integer getIsUnifyRegister() {
		return isUnifyRegister;
	}

	public void setIsUnifyRegister(Integer isUnifyRegister) {
		this.isUnifyRegister = isUnifyRegister;
	}

	@Column(name = "LOGOFFURL")
	public String getLogOffUrl() {
		return logOffUrl;
	}

	public void setLogOffUrl(String logOffUrl) {
		this.logOffUrl = logOffUrl;
	}

	@Column(name = "NETTYPE")
	public Integer getNetType() {
		return netType;
	}

	public void setNetType(Integer netType) {
		this.netType = netType;
	}
}