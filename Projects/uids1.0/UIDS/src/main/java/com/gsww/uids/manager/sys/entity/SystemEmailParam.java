package com.gsww.uids.manager.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 邮件参数
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "SYS_EMAIL_PARAM")
public class SystemEmailParam extends PO {

	private static final long serialVersionUID = 9132006170087404630L;
	
	/**
	 * 服务器
	 */
	@Column(name = "SERVER_URL", nullable = false, length = 128)
	private String emailServer;
	
	/**
	 * 端口
	 */
	@Column(name = "PORT", columnDefinition = "INT default 25")
	private int port = 25;
	
	/**
	 * 邮箱
	 */
	@Column(name = "EMAIL", nullable = false, length = 128)
	private String emailBox;
	
	/**
	 * 邮箱密码
	 */
	@Column(name = "EMAIL_PASSWORD", nullable = false, length = 128)
	private String emailPassword;
	
	/**
	 * 寄件方名称
	 */
	@Column(name = "SENDER_NAME", nullable = false, length = 128)
	private String senderName;
	
	/**
	 * 账号注册邮件标题
	 */
	@Column(name = "ACCOUNT_REGISTER_EMAIL_TITLE", length = 128)
	private String accountRegisterEmailTitle;
	
	/**
	 * 账号注册邮件内容
	 */
	@Column(name = "ACCOUNT_REGISTER_EMAIL_CONTENT", length = 256)
	private String accountRegisterEmailContent;
	
	/**
	 * 个人注册邮件标题
	 */
	@Column(name = "NATURAL_REGISTER_EMAIL_TITLE", length = 128)
	private String naturalRegisterEmailTitle;
	
	/**
	 * 个人注册邮件内容
	 */
	@Column(name = "NATURAL_REGISTER_EMAIL_CONTENT", length = 256)
	private String naturalRegisterEmailContent;
	
	/**
	 * 法人注册邮件标题
	 */
	@Column(name = "CORPORATE_REGISTER_EMAIL_TITLE", length = 128)
	private String corporateRegisterEmailTitle;
	
	/**
	 * 法人注册邮件内容
	 */
	@Column(name = "CORPORATE_REGISTER_EMAIL_CONTENT", length = 256)
	private String corporateRegisterEmailContent;
	
	/**
	 * 绑定用户邮件标题
	 */
	@Column(name = "BIND_USER_EMAIL_TITLE", length = 128)
	private String bindUserEmailTitle;
	
	/**
	 * 绑定用户邮件内容
	 */
	@Column(name = "BIND_USER_EMAIL_CONTENT", length = 256)
	private String bindUserEmailContent;
	
	/**
	 * 解绑用户邮件标题
	 */
	@Column(name = "UNBIND_USER_EMAIL_TITLE", length = 128)
	private String unbindUserEmailTitle;
	
	/**
	 * 解绑用户邮件内容
	 */
	@Column(name = "UNBIND_USER_EMAIL_CONTENT", length = 256)
	private String unbindUserEmailContent;
	
	/**
	 * 找回密码业务名称
	 */
	@Column(name = "FIND_PWD_EMAIL_TITLE", length = 128)
	private String findPwdEmailTitle;
	
	/**
	 * 找回密码短信内容
	 */
	@Column(name = "FIND_PWD_EMAIL_CONTENT", length = 512)
	private String findPwdEmailContent;
	
	/**
	 * 认证手机业务名称
	 */
	@Column(name = "AUTH_MOBILE_EMAIL_TITLE", length = 128)
	private String authMobileEmailTitle;
	
	/**
	 * 认证手机短信内容
	 */
	@Column(name = "AUTH_MOBILE_EMAIL_CONTENT", length = 512)
	private String authMobileEmailContent;
	
	/**
	 * 注销账号业务名称
	 */
	@Column(name = "DELETE_ACCOUNT_EMAIL_TITLE", length = 128)
	private String deleteAccountEmailTitle;
	
	/**
	 * 注销账号短信内容
	 */
	@Column(name = "DELETE_ACCOUNT_EMAIL_CONTENT", length = 512)
	private String deleteAccountEmailContent;


	public String getEmailServer() {
		return emailServer;
	}

	public void setEmailServer(String emailServer) {
		this.emailServer = emailServer;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


	public String getEmailBox() {
		return emailBox;
	}

	public void setEmailBox(String emailBox) {
		this.emailBox = emailBox;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getSenderName() {
		return senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public String getAccountRegisterEmailTitle() {
		return accountRegisterEmailTitle;
	}

	public void setAccountRegisterEmailTitle(String accountRegisterEmailTitle) {
		this.accountRegisterEmailTitle = accountRegisterEmailTitle;
	}

	public String getAccountRegisterEmailContent() {
		return accountRegisterEmailContent;
	}

	public void setAccountRegisterEmailContent(String accountRegisterEmailContent) {
		this.accountRegisterEmailContent = accountRegisterEmailContent;
	}

	public String getNaturalRegisterEmailTitle() {
		return naturalRegisterEmailTitle;
	}

	public void setNaturalRegisterEmailTitle(String naturalRegisterEmailTitle) {
		this.naturalRegisterEmailTitle = naturalRegisterEmailTitle;
	}

	public String getNaturalRegisterEmailContent() {
		return naturalRegisterEmailContent;
	}

	public void setNaturalRegisterEmailContent(String naturalRegisterEmailContent) {
		this.naturalRegisterEmailContent = naturalRegisterEmailContent;
	}

	public String getCorporateRegisterEmailTitle() {
		return corporateRegisterEmailTitle;
	}

	public void setCorporateRegisterEmailTitle(String corporateRegisterEmailTitle) {
		this.corporateRegisterEmailTitle = corporateRegisterEmailTitle;
	}

	public String getCorporateRegisterEmailContent() {
		return corporateRegisterEmailContent;
	}

	public void setCorporateRegisterEmailContent(String corporateRegisterEmailContent) {
		this.corporateRegisterEmailContent = corporateRegisterEmailContent;
	}

	public String getBindUserEmailTitle() {
		return bindUserEmailTitle;
	}

	public void setBindUserEmailTitle(String bindUserEmailTitle) {
		this.bindUserEmailTitle = bindUserEmailTitle;
	}

	public String getBindUserEmailContent() {
		return bindUserEmailContent;
	}

	public void setBindUserEmailContent(String bindUserEmailContent) {
		this.bindUserEmailContent = bindUserEmailContent;
	}

	public String getUnbindUserEmailTitle() {
		return unbindUserEmailTitle;
	}

	public void setUnbindUserEmailTitle(String unbindUserEmailTitle) {
		this.unbindUserEmailTitle = unbindUserEmailTitle;
	}

	public String getUnbindUserEmailContent() {
		return unbindUserEmailContent;
	}

	public void setUnbindUserEmailContent(String unbindUserEmailContent) {
		this.unbindUserEmailContent = unbindUserEmailContent;
	}

	public String getFindPwdEmailTitle() {
		return findPwdEmailTitle;
	}

	public void setFindPwdEmailTitle(String findPwdEmailTitle) {
		this.findPwdEmailTitle = findPwdEmailTitle;
	}

	public String getFindPwdEmailContent() {
		return findPwdEmailContent;
	}

	public void setFindPwdEmailContent(String findPwdEmailContent) {
		this.findPwdEmailContent = findPwdEmailContent;
	}

	public String getAuthMobileEmailTitle() {
		return authMobileEmailTitle;
	}

	public void setAuthMobileEmailTitle(String authMobileEmailTitle) {
		this.authMobileEmailTitle = authMobileEmailTitle;
	}

	public String getAuthMobileEmailContent() {
		return authMobileEmailContent;
	}

	public void setAuthMobileEmailContent(String authMobileEmailContent) {
		this.authMobileEmailContent = authMobileEmailContent;
	}

	public String getDeleteAccountEmailTitle() {
		return deleteAccountEmailTitle;
	}

	public void setDeleteAccountEmailTitle(String deleteAccountEmailTitle) {
		this.deleteAccountEmailTitle = deleteAccountEmailTitle;
	}

	public String getDeleteAccountEmailContent() {
		return deleteAccountEmailContent;
	}

	public void setDeleteAccountEmailContent(String deleteAccountEmailContent) {
		this.deleteAccountEmailContent = deleteAccountEmailContent;
	}
}