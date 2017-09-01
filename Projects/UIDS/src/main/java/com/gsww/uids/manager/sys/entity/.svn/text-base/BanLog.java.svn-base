package com.gsww.uids.manager.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 封停管理
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_LOG_BAN")
public class BanLog extends PO {

	private static final long serialVersionUID = -8193596164178087867L;

	/**
	 * 应用账号来源的应用名称
	 */
	@Column(name = "APP_NAME", nullable = false, length = 128)
	private String appName;
	
	/**
	 * 账号名称
	 */
	@Column(name = "ACCOUNT_NAME", nullable = false, length = 128)
	private String accountName;
	
	/**
	 * 最后登录时间
	 */
	@Column(name = "LAST_LOGIN_TIME", nullable = false, length = 23)
	private String lastLoginTime;
	
	/**
	 * 最近登录错误次数
	 */
	@Column(name = "RECENT_ERROR_TIMES", columnDefinition = "INT default 0")
	private int recentErrorTimes = 0;
	
	/**
	 * 最后登录的ip地址
	 */
	@Column(name = "LAST_LOGIN_IP", nullable = false, length = 15)
	private String lastLoginIp;
	
	/**
	 * 是否逻辑删除
	 */
	@Column(name = "IS_DELETE",length=1)
	private int isDelete = 0; //逻辑删除（0-未删除，1-删除）
	
	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getAppName() {
		return appName;
	} 

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(String lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public int getRecentErrorTimes() {
		return recentErrorTimes;
	}

	public void setRecentErrorTimes(int recentErrorTimes) {
		this.recentErrorTimes = recentErrorTimes;
	}

	public String getLastLoginIp() {
		return lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
}
