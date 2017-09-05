package com.gsww.uids.manager.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 注册、密码找回、动态密码发送短信相关参数
 * 
 * @author simplife
 *
 */
@Entity
@Table(name = "SYS_SMS_PARAM")
public class SystemSMSParam extends PO {

	private static final long serialVersionUID = 3283334200134035405L;
	
	// *****************************************************************
	// 第三方发送短信接口参数
	// *****************************************************************
	
	/**
	 * APP_ID
	 */
	@Column(name = "APP_ID", length = 32)
	private String appId;
	
	
	/**
	 * APP_NAME
	 */
	@Column(name = "APP_NAME", length = 128)
	private String appName;
	
	/**
	 * APP_ACC
	 */
	@Column(name = "APP_ACC", length = 32)
	private String appAcc;
	
	/**
	 * APP_PWD
	 */
	@Column(name = "APP_PWD", length = 32)
	private String appPwd;
	
	/**
	 * IMPORT_ANT_LEVEL
	 */
	@Column(name = "IMPORT_ANT_LEVEL", length = 2)
	private String importantLevel;
	
	/**
	 * IS_LOSE
	 */
	@Column(name = "IS_LOSE", length = 1)
	private String isLose;
	
	
	/**
	 * IS_SEND_AGAIN
	 */
	@Column(name = "IS_SEND_AGAIN", length = 1)
	private String isSendAgain;
	
	/**
	 * IS_UP_STREAM
	 */
	@Column(name = "IS_UP_STREAM", length = 1)
	private String isUpstream;
	
	/**
	 * URL_ROOT
	 */
	@Column(name = "URL_ROOT", length = 128)
	private String urlRoot;
	
	// *****************************************************************
	// 注册、密码找回、动态验证码
	// *****************************************************************
	
	/**
	 * 账号注册短信业务id
	 */
	@Column(name = "ACCOUNT_REGISTER_SMS_BUSINESS_ID", length = 32)
	private String accountRegisterBussinessId;
	
	/**
	 * 账号注册短信业务名称
	 */
	@Column(name = "ACCOUNT_REGISTER_SMS_BUSINESS_NAME", length = 128)
	private String accountRegisterBussinessName;
	
	/**
	 * 账号注册时短信内容
	 */
	@Column(name = "ACCOUNT_REGISTER_SMS_BUSINESS_MESSAGE", length = 256)
	private String accountRegisterBussinessMessage;
	
	/**
	 * 个人注册短信业务id
	 */
	@Column(name = "NATURAL_REGISTER_SMS_BUSINESS_ID", length = 32)
	private String naturalRegisterBussinessId;
	
	/**
	 * 个人注册短信业务名称
	 */
	@Column(name = "NATURAL_REGISTER_SMS_BUSINESS_NAME", length = 128)
	private String naturalRegisterBussinessName;
	
	/**
	 * 个人注册时短信内容
	 */
	@Column(name = "NATURAL_REGISTER_SMS_BUSINESS_MESSAGE", length = 256)
	private String naturalRegisterBussinessMessage;
	
	/**
	 * 法人注册短信业务id
	 */
	@Column(name = "CORPORATE_REGISTER_SMS_BUSINESS_ID", length = 32)
	private String corporateRegisterBussinessId;
	
	/**
	 * 法人注册短信业务名称
	 */
	@Column(name = "CORPORATE_REGISTER_SMS_BUSINESS_NAME", length = 128)
	private String corporateRegisterBussinessName;
	
	/**
	 * 法人注册时短信内容
	 */
	@Column(name = "CORPORATE_REGISTER_SMS_BUSINESS_MESSAGE", length = 256)
	private String corporateRegisterBussinessMessage;
	
	/**
	 * 绑定用户短信业务id
	 */
	@Column(name = "BIND_USER_SMS_BUSINESS_ID", length = 32)
	private String bindUserBussinessId;
	
	/**
	 * 绑定用户短信业务名称
	 */
	@Column(name = "BIND_USER_SMS_BUSINESS_NAME", length = 128)
	private String bindUserBussinessName;
	
	/**
	 * 绑定用户时短信内容
	 */
	@Column(name = "BIND_USER_SMS_BUSINESS_MESSAGE", length = 256)
	private String bindUserBussinessMessage;
	
	/**
	 * 解绑用户短信业务id
	 */
	@Column(name = "UNBIND_USER_SMS_BUSINESS_ID", length = 32)
	private String unbindUserBussinessId;
	
	/**
	 * 解绑用户短信业务名称
	 */
	@Column(name = "UNBIND_USER_SMS_BUSINESS_NAME", length = 128)
	private String unbindUserBussinessName;
	
	/**
	 * 解绑用户时短信内容
	 */
	@Column(name = "UNBIND_USER_SMS_BUSINESS_MESSAGE", length = 256)
	private String unbindUserBussinessMessage;
	
	/**
	 * 找回密码业务id
	 */
	@Column(name = "FIND_PWD_SMS_BUSINESS_ID", length = 32)
	private String findPwdBussinessId;
	
	/**
	 * 找回密码业务名称
	 */
	@Column(name = "FIND_PWD_SMS_BUSINESS_NAME", length = 128)
	private String findPwdBussinessName;
	
	/**
	 * 找回密码短信内容
	 */
	@Column(name = "FIND_PWD_BUSINESS_MESSAGE", length = 512)
	private String findPwdBussinessMessage;
	
	/**
	 * 认证手机业务id
	 */
	@Column(name = "AUTH_MOBILE_SMS_BUSINESS_ID", length = 32)
	private String authMobileBussinessId;
	
	/**
	 * 认证手机业务名称
	 */
	@Column(name = "AUTH_MOBILE_SMS_BUSINESS_NAME", length = 128)
	private String authMobileBussinessName;
	
	/**
	 * 认证手机短信内容
	 */
	@Column(name = "AUTH_MOBILE_BUSINESS_MESSAGE", length = 512)
	private String authMobileBussinessMessage;
	
	/**
	 * 注销账号业务id
	 */
	@Column(name = "DELETE_ACCOUNT_SMS_BUSINESS_ID", length = 32)
	private String deleteAccountBussinessId;
	
	/**
	 * 注销账号业务名称
	 */
	@Column(name = "DELETE_ACCOUNT_SMS_BUSINESS_NAME", length = 128)
	private String deleteAccountBussinessName;
	
	/**
	 * 注销账号短信内容
	 */
	@Column(name = "DELETE_ACCOUNT_BUSINESS_MESSAGE", length = 512)
	private String deleteAccountBussinessMessage;
	
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppAcc() {
		return appAcc;
	}

	public void setAppAcc(String appAcc) {
		this.appAcc = appAcc;
	}

	public String getAppPwd() {
		return appPwd;
	}

	public void setAppPwd(String appPwd) {
		this.appPwd = appPwd;
	}

	public String getImportantLevel() {
		return importantLevel;
	}

	public void setImportantLevel(String importantLevel) {
		this.importantLevel = importantLevel;
	}

	public String getIsLose() {
		return isLose;
	}

	public void setIsLose(String isLose) {
		this.isLose = isLose;
	}

	public String getIsSendAgain() {
		return isSendAgain;
	}

	public void setIsSendAgain(String isSendAgain) {
		this.isSendAgain = isSendAgain;
	}

	public String getIsUpstream() {
		return isUpstream;
	}

	public void setIsUpstream(String isUpstream) {
		this.isUpstream = isUpstream;
	}

	public String getUrlRoot() {
		return urlRoot;
	}

	public void setUrlRoot(String urlRoot) {
		this.urlRoot = urlRoot;
	}

	public String getAccountRegisterBussinessId() {
		return accountRegisterBussinessId;
	}

	public void setAccountRegisterBussinessId(String accountRegisterBussinessId) {
		this.accountRegisterBussinessId = accountRegisterBussinessId;
	}

	public String getAccountRegisterBussinessName() {
		return accountRegisterBussinessName;
	}

	public void setAccountRegisterBussinessName(String accountRegisterBussinessName) {
		this.accountRegisterBussinessName = accountRegisterBussinessName;
	}

	public String getAccountRegisterBussinessMessage() {
		return accountRegisterBussinessMessage;
	}

	public void setAccountRegisterBussinessMessage(String accountRegisterBussinessMessage) {
		this.accountRegisterBussinessMessage = accountRegisterBussinessMessage;
	}

	public String getNaturalRegisterBussinessId() {
		return naturalRegisterBussinessId;
	}

	public void setNaturalRegisterBussinessId(String naturalRegisterBussinessId) {
		this.naturalRegisterBussinessId = naturalRegisterBussinessId;
	}

	public String getNaturalRegisterBussinessName() {
		return naturalRegisterBussinessName;
	}

	public void setNaturalRegisterBussinessName(String naturalRegisterBussinessName) {
		this.naturalRegisterBussinessName = naturalRegisterBussinessName;
	}

	public String getNaturalRegisterBussinessMessage() {
		return naturalRegisterBussinessMessage;
	}

	public void setNaturalRegisterBussinessMessage(String naturalRegisterBussinessMessage) {
		this.naturalRegisterBussinessMessage = naturalRegisterBussinessMessage;
	}

	public String getCorporateRegisterBussinessId() {
		return corporateRegisterBussinessId;
	}

	public void setCorporateRegisterBussinessId(String corporateRegisterBussinessId) {
		this.corporateRegisterBussinessId = corporateRegisterBussinessId;
	}

	public String getCorporateRegisterBussinessName() {
		return corporateRegisterBussinessName;
	}

	public void setCorporateRegisterBussinessName(String corporateRegisterBussinessName) {
		this.corporateRegisterBussinessName = corporateRegisterBussinessName;
	}

	public String getCorporateRegisterBussinessMessage() {
		return corporateRegisterBussinessMessage;
	}

	public void setCorporateRegisterBussinessMessage(String corporateRegisterBussinessMessage) {
		this.corporateRegisterBussinessMessage = corporateRegisterBussinessMessage;
	}

	public String getBindUserBussinessId() {
		return bindUserBussinessId;
	}

	public void setBindUserBussinessId(String bindUserBussinessId) {
		this.bindUserBussinessId = bindUserBussinessId;
	}

	public String getBindUserBussinessName() {
		return bindUserBussinessName;
	}

	public void setBindUserBussinessName(String bindUserBussinessName) {
		this.bindUserBussinessName = bindUserBussinessName;
	}

	public String getBindUserBussinessMessage() {
		return bindUserBussinessMessage;
	}

	public void setBindUserBussinessMessage(String bindUserBussinessMessage) {
		this.bindUserBussinessMessage = bindUserBussinessMessage;
	}

	public String getUnbindUserBussinessId() {
		return unbindUserBussinessId;
	}

	public void setUnbindUserBussinessId(String unbindUserBussinessId) {
		this.unbindUserBussinessId = unbindUserBussinessId;
	}

	public String getUnbindUserBussinessName() {
		return unbindUserBussinessName;
	}

	public void setUnbindUserBussinessName(String unbindUserBussinessName) {
		this.unbindUserBussinessName = unbindUserBussinessName;
	}

	public String getUnbindUserBussinessMessage() {
		return unbindUserBussinessMessage;
	}

	public void setUnbindUserBussinessMessage(String unbindUserBussinessMessage) {
		this.unbindUserBussinessMessage = unbindUserBussinessMessage;
	}

	public String getFindPwdBussinessId() {
		return findPwdBussinessId;
	}

	public void setFindPwdBussinessId(String findPwdBussinessId) {
		this.findPwdBussinessId = findPwdBussinessId;
	}

	public String getFindPwdBussinessName() {
		return findPwdBussinessName;
	}

	public void setFindPwdBussinessName(String findPwdBussinessName) {
		this.findPwdBussinessName = findPwdBussinessName;
	}

	public String getFindPwdBussinessMessage() {
		return findPwdBussinessMessage;
	}

	public void setFindPwdBussinessMessage(String findPwdBussinessMessage) {
		this.findPwdBussinessMessage = findPwdBussinessMessage;
	}

	public String getAuthMobileBussinessId() {
		return authMobileBussinessId;
	}

	public void setAuthMobileBussinessId(String authMobileBussinessId) {
		this.authMobileBussinessId = authMobileBussinessId;
	}

	public String getAuthMobileBussinessName() {
		return authMobileBussinessName;
	}

	public void setAuthMobileBussinessName(String authMobileBussinessName) {
		this.authMobileBussinessName = authMobileBussinessName;
	}

	public String getAuthMobileBussinessMessage() {
		return authMobileBussinessMessage;
	}

	public void setAuthMobileBussinessMessage(String authMobileBussinessMessage) {
		this.authMobileBussinessMessage = authMobileBussinessMessage;
	}

	public String getDeleteAccountBussinessId() {
		return deleteAccountBussinessId;
	}

	public void setDeleteAccountBussinessId(String deleteAccountBussinessId) {
		this.deleteAccountBussinessId = deleteAccountBussinessId;
	}

	public String getDeleteAccountBussinessName() {
		return deleteAccountBussinessName;
	}

	public void setDeleteAccountBussinessName(String deleteAccountBussinessName) {
		this.deleteAccountBussinessName = deleteAccountBussinessName;
	}

	public String getDeleteAccountBussinessMessage() {
		return deleteAccountBussinessMessage;
	}

	public void setDeleteAccountBussinessMessage(String deleteAccountBussinessMessage) {
		this.deleteAccountBussinessMessage = deleteAccountBussinessMessage;
	}
}
