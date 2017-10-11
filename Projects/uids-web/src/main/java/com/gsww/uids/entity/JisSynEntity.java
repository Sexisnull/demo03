package com.gsww.uids.entity;

public class JisSynEntity {
	/**
	 * ID
	 */
	private int id = 0;
	/**
	 * 应用的id
	 */
	private int appid = 0;
	/**
	 * 应用名称
	 */
	private String appName = "";
	/**
	 * 操作状态(N：用户启用    DN：用户停用    T：用户新增修改 D：用户删除 TG:机构新增修改 DG：机构删除)
	 */
	private String state = "";
	/**
	 * 机构编码
	 */
	private String groupCode = "";
	/**
	 * 机构名称
	 */
	private String groupName = "";
	/**
	 * 父机构ID
	 */
	private String parCode = "";
	/**
	 * 父机构名称
	 */
	private String parName = "";
	/**
	 * 机构码全路径
	 */
	private String allParCode = "";
	/**
	 * 机构名称全路径
	 */
	private String allParName = "";
	
	/**
	 * 登陆名称
	 */
	private String loginName = "";
	/**
	 * 密码
	 */
	private String loginPass = "";
	/**
	 * 用户名称
	 */
	private String userName = "";
	/**
	 * 身份证号码
	 */
	private String cardId = "";
	/**
	 * 办公电话
	 */
	private String comptel = "";
	/**
	 * 传真
	 */
	private String compfax = "";
	/**
	 * 移动电话
	 */
	private String mobile = "";
	/**
	 * Email
	 */
	private String email = "";
	/**
	 * QQ
	 */
	private String qq = "";
	/**
	 * MSN
	 */
	private String msn = "";
	/**
	 * 家庭电话
	 */
	private String hometel = "";
	/**
	 * 职务
	 */
	private String headShip = "";
	/**
	 * 
	 */
	private String ndlogin = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getAppid() {
		return appid;
	}
	public void setAppid(int appid) {
		this.appid = appid;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getParCode() {
		return parCode;
	}
	public void setParCode(String parCode) {
		this.parCode = parCode;
	}
	public String getParName() {
		return parName;
	}
	public void setParName(String parName) {
		this.parName = parName;
	}
	public String getAllParCode() {
		return allParCode;
	}
	public void setAllParCode(String allParCode) {
		this.allParCode = allParCode;
	}
	public String getAllParName() {
		return allParName;
	}
	public void setAllParName(String allParName) {
		this.allParName = allParName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPass() {
		return loginPass;
	}
	public void setLoginPass(String loginPass) {
		this.loginPass = loginPass;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getComptel() {
		return comptel;
	}
	public void setComptel(String comptel) {
		this.comptel = comptel;
	}
	public String getCompfax() {
		return compfax;
	}
	public void setCompfax(String compfax) {
		this.compfax = compfax;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getMsn() {
		return msn;
	}
	public void setMsn(String msn) {
		this.msn = msn;
	}
	public String getHometel() {
		return hometel;
	}
	public void setHometel(String hometel) {
		this.hometel = hometel;
	}
	public String getHeadShip() {
		return headShip;
	}
	public void setHeadShip(String headShip) {
		this.headShip = headShip;
	}
	public String getNdlogin() {
		return ndlogin;
	}
	public void setNdlogin(String ndlogin) {
		this.ndlogin = ndlogin;
	}
}