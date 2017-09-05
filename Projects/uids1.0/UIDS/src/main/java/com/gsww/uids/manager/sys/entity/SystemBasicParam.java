package com.gsww.uids.manager.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 统一身份认证系统的基本参数设置
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "SYS_BASIC_PARAM")
public class SystemBasicParam extends PO {

	private static final long serialVersionUID = -2250895392958749158L;
	
	/**
	 * 密码强度等级：强
	 */
	public static final int STRONG_PASSWORD_LEVEL = 1;
	
	/**
	 * 密码强度等级：中
	 */
	public static final int MIDDLE_PASSWORD_LEVEL = 2;
	
	/**
	 * 密码强度等级：弱
	 */
	public static final int WEAK_PASSWORD_LEVEL = 3;
	
	/**
	 * 网络类型：外网
	 */
	public static final String PUBLIC_NET = "public";
	
	/**
	 * 网络类型：专网
	 */
	public static final String PRIVATE_NET = "private";

	/**
	 * 系统名称
	 */
	@Column(name = "SYS_NAME", nullable = false, length = 128)
	private String sysName;
	
	/**
	 * 系统地址
	 */
	@Column(name = "SYS_URL", nullable = false, length = 128)
	private String sysUrl;
	
	/**
	 * 版权信息
	 */
	@Column(name = "COPYRIGHT", nullable = false, length = 128)
	private String copyright;
	
	/**
	 * 是否限制登录次数：是-1，否-0
	 */
	@Column(name = "IS_LIMIT_LOGIN_TIMES", columnDefinition = "INT default 1")
	private int limitLoginTimes = 1;
	
	/**
	 * 允许连续错误次数
	 */
	@Column(name = "LOGIN_ERROR", columnDefinition = "INT default 3")
	private int loginError = 3;
	
	/**
	 * 锁定错误账号时长（单位：分钟）
	 */
	@Column(name = "BAN_TIMES", columnDefinition = "INT default 15")
	private int banTimes = 15;
	
	/**
	 * 密码强度等级
	 */
	@Column(name = "PWD_LEVEL", columnDefinition = "INT default 1")
	private int pwdLevel = MIDDLE_PASSWORD_LEVEL;
	
	/**
	 * 网络类型：外网-PUBLIC_NET；专网-PRIVATE_NET
	 */
	@Column(name = "NET_TYPE", nullable = false, length = 16)
	private String netType = PUBLIC_NET;
	
	/**
	 * 前台参数：用户授权的有效时间（单位：秒）
	 */
	@Column(name = "TICKET_EFFECTIVE_TIME", columnDefinition = "INT default 10")
	private int ticketEffectiveTime = 10;
	
	/**
	 * 前台参数：令牌有效时间（单位：秒）
	 */
	@Column(name = "TOKEN_EFFECTIVE_TIME", columnDefinition = "INT default 14400")
	private int tokenEffectiveTime = 14400;
	
	/**
	 * 前台参数：个人账号登录超时时间（单位：分钟）
	 */
	@Column(name = "PUBLIC_SESSION_TIMEOUT", columnDefinition = "INT default 30")
	private int publicSessionTimeout = 30;
	
	/**
	 * 前台参数：法人账号登录超时时间（单位：分钟）
	 */
	@Column(name = "CORPORATE_SESSION_TIMEOUT", columnDefinition = "INT default 30")
	private int corporateSessionTimeout = 30;
	
	/**
	 * 前台参数：公务账号登录超时时间（单位：分钟）
	 */
	@Column(name = "GOVERNMENT_SESSION_TIMEOUT", columnDefinition = "INT default 30")
	private int governmentSessionTimeout = 30;

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysUrl() {
		return sysUrl;
	}

	public void setSysUrl(String sysUrl) {
		this.sysUrl = sysUrl;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public int getLimitLoginTimes() {
		return limitLoginTimes;
	}

	public void setLimitLoginTimes(int limitLoginTimes) {
		this.limitLoginTimes = limitLoginTimes;
	}

	public int getLoginError() {
		return loginError;
	}

	public void setLoginError(int loginError) {
		this.loginError = loginError;
	}

	public int getBanTimes() {
		return banTimes;
	}

	public void setBanTimes(int banTimes) {
		this.banTimes = banTimes;
	}

	public int getPwdLevel() {
		return pwdLevel;
	}

	public void setPwdLevel(int pwdLevel) {
		this.pwdLevel = pwdLevel;
	}

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public int getTicketEffectiveTime() {
		return ticketEffectiveTime;
	}

	public void setTicketEffectiveTime(int ticketEffectiveTime) {
		this.ticketEffectiveTime = ticketEffectiveTime;
	}

	public int getTokenEffectiveTime() {
		return tokenEffectiveTime;
	}

	public void setTokenEffectiveTime(int tokenEffectiveTime) {
		this.tokenEffectiveTime = tokenEffectiveTime;
	}

	public int getPublicSessionTimeout() {
		return publicSessionTimeout;
	}

	public void setPublicSessionTimeout(int publicSessionTimeout) {
		this.publicSessionTimeout = publicSessionTimeout;
	}

	public int getCorporateSessionTimeout() {
		return corporateSessionTimeout;
	}

	public void setCorporateSessionTimeout(int corporateSessionTimeout) {
		this.corporateSessionTimeout = corporateSessionTimeout;
	}

	public int getGovernmentSessionTimeout() {
		return governmentSessionTimeout;
	}

	public void setGovernmentSessionTimeout(int governmentSessionTimeout) {
		this.governmentSessionTimeout = governmentSessionTimeout;
	}
}
