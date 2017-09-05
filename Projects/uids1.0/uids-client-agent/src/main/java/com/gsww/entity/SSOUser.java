package com.gsww.entity;

import java.io.Serializable;

/**
 * 从uids服务器获取的用户信息
 * 
 * @author taolm
 *
 */
public class SSOUser implements Serializable {
	
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 3101409827687697350L;

	/**
	 * 用户的uuid
	 */
	private String uuid;
	
	/**
	 * 用户类型：1-个人；2-法人
	 */
	private int userType;

	/**
	 * 如果userType = 2：corporate-企业法人；organization-非企业法人
	 */
	private String corporateType;
	
	/**
	 * 身份证号
	 */
	private String identity;
	
	/**
	 * 组织机构代码：userType = 2时有效
	 */
	private String orgCode;
	
	/**
	 * 企业/机构名称：userType = 2时有效
	 */
	private String corpName;
	
	/**
	 * 企业工商注册号/统一社会信用代码：仅当userType = 2且corporateType = "corporate"时有效
	 */
	private String creditID;
	
	/**
	 * 登录账号类型
	 */
	private int loginAccountType;
	
	/**
	 * 所属机构uuid
	 */
	private String orgId;
	
	/**
	 * 所属机构的简称
	 */
	private String orgShortName;
	
	/**
	 * 所属机构全称
	 */
	private String orgFullName;
	
	/**
	 * 所属机构编码
	 */
	private String orgInnerCode;
	
	/**
	 * 主账号类型
	 */
	private int accountType;
	
	/**
	 * 用户名：主账号的账号名
	 */
	private String userName;
	
	/**
	 * 主账号昵称
	 */
	private String nickName;
	
	/**
	 * 名字
	 */
	private String firstName;
	
	/**
	 * 姓
	 */
	private String lastName;
	
	/**
	 * 描述
	 */
	private String desc;
	
	/**
	 * 传真
	 */
	private String fax;
	
	/**
	 * 电话
	 */
	private String tel;
	
	/**
	 * 真实姓名
	 */
	private String trueName;
	
	/**
	 * 电子邮件
	 */
	private String email;
	
	/**
	 * 手机
	 */
	private String mobile;
	
	/**
	 * 所属机构名（为了兼容大汉模式，与orgShortName等效）
	 */
	private String groupName;
	
	/**
	 * 联系人
	 */
	private String relPerson;
	
	/**
	 * 籍贯
	 */
	private String regFrom;
	
	/**
	 * 创建时间
	 */
	private String createdDate;
	
	/**
	 * 最后一次退出日期
	 */
	private String lastLogoutDate;
	
	/**
	 * 最后一次登录时间
	 */
	private String lastLoginDate;
	
	/**
	 * ip地址
	 */
	private String regIP;
	
	/**
	 * 登录次数
	 */
	private int loginTimes;
	
	/**
	 * 是否可用
	 */
	private boolean actived;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getCorporateType() {
		return corporateType;
	}

	public void setCorporateType(String corporateType) {
		this.corporateType = corporateType;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCorpName() {
		return corpName;
	}

	public void setCorpName(String corpName) {
		this.corpName = corpName;
	}

	public String getCreditID() {
		return creditID;
	}

	public void setCreditID(String creditID) {
		this.creditID = creditID;
	}

	public int getLoginAccountType() {
		return loginAccountType;
	}

	public void setLoginAccountType(int loginAccountType) {
		this.loginAccountType = loginAccountType;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgShortName() {
		return orgShortName;
	}

	public void setOrgShortName(String orgShortName) {
		this.orgShortName = orgShortName;
	}

	public String getOrgFullName() {
		return orgFullName;
	}

	public void setOrgFullName(String orgFullName) {
		this.orgFullName = orgFullName;
	}

	public String getOrgInnerCode() {
		return orgInnerCode;
	}

	public void setOrgInnerCode(String orgInnerCode) {
		this.orgInnerCode = orgInnerCode;
	}

	public int getAccountType() {
		return accountType;
	}

	public void setAccountType(int accountType) {
		this.accountType = accountType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getRelPerson() {
		return relPerson;
	}

	public void setRelPerson(String relPerson) {
		this.relPerson = relPerson;
	}

	public String getRegFrom() {
		return regFrom;
	}

	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String getLastLogoutDate() {
		return lastLogoutDate;
	}

	public void setLastLogoutDate(String lastLogoutDate) {
		this.lastLogoutDate = lastLogoutDate;
	}

	public String getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(String lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getRegIP() {
		return regIP;
	}

	public void setRegIP(String regIP) {
		this.regIP = regIP;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public boolean isActived() {
		return actived;
	}

	public void setActived(boolean actived) {
		this.actived = actived;
	}
}
