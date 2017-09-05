package com.gsww.uids.manager.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;
import com.gsww.common.enums.ModuleEnum;
import com.gsww.common.enums.OperateEnum;

/**
 * 操作日志
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_LOG_OPERATION")
public class OperationLog extends PO {

	private static final long serialVersionUID = 8839584512060723217L;
	
	/**
	 * 操作类型：登录
	 */
	public static final String LOGIN_TYPE = "login";
	
	/**
	 * 操作类型：注销
	 */
	public static final String LOGOUT_TYPE = "logout";
	
	/**
	 * 操作类型：账号注册
	 */
	public static final String ACCOUNT_REGISTER = "register";
	
	/**
	 * 操作类型：增加或修改
	 */
	public static final String INSERT_UPDATE = "insertOrUpdate";
	
	/**
	 * 操作类型：增加
	 */
	public static final String INSERT_TYPE = "insert";
	
	/**
	 * 操作类型：修改
	 */
	public static final String UPDATE_TYPE = "update";
	
	/**
	 * 操作类型：删除
	 */
	public static final String DELETE_TYPE = "delete";
	
	/**
	 * 操作模块：登录模块
	 */
	public static final String LOGIN_MODULE = "login";
	
	/**
	 * 操作模块：机构
	 */
	public static final String ORGANIZATION_MODULE = "organization";
	
	/**
	 * 操作模块：机构分组
	 */
	public static final String ORGANIZATION_GROUP_MODULE = "organizationGroup";
	
	/**
	 * 操作模块：用户
	 */
	public static final String USER_MODULE = "user";
	
	/**
	 * 操作模块：账号
	 */
	public static final String ACCOUNT_MODULE = "account";
	
	/**
	 * 操作模块：角色
	 */
	public static final String ROLE_MODULE = "role";
	
	/**
	 * 操作模块：应用
	 */
	public static final String APP_MODULE = "app";
	
	/**
	 * 操作模块：资源
	 */
	public static final String SOURCE_MODULE = "source";
	
	/**
	 * 操作模块：系统参数
	 */
	public static final String SYS_PARAM_MODULE = "sysParam";
	
	/**
	 * 操作模块：区域
	 */
	public static final String SYS_AREA_MODULE = "area";
	
	/**
	 * 操作账号名
	 */
	@Column(name = "ACCOUNT_NAME", nullable = false, length = 128)
	public String accountName;

	/**
	 * 账号来源应用名
	 */
	@Column(name = "APP_NAME", nullable = false, length = 128)
	public String appName;
	
	/**
	 * 操作人姓名
	 */
	@Column(name = "USER_NAME", nullable = true, length = 128)
	private String userName;
	
	/**
	 * 操作人身份证号
	 */
	@Column(name = "USER_IDENTITY", length = 18)
	private String userIdentity;
	
	/**
	 * 操作类型
	 */
	@Column(name = "TYPE", nullable = false, length = 32)
	private String type = LOGIN_TYPE;
	
	/**
	 * 操作模块
	 */
	@Column(name = "MODULE", nullable = false, length = 32)
	private String module = LOGIN_MODULE;
	
	/**
	 * 操作时间
	 */
	@Column(name = "TIME", nullable = false, length = 23)
	private String time;
	
	/**
	 * 操作ip
	 */
	@Column(name = "IP", nullable = false, length = 15)
	private String ip;
	
	/**
	 * 操作描述
	 */
	@Column(name = "LOG_DESC", length = 256)
	private String logDesc;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIdentity() {
		return userIdentity;
	}

	public void setUserIdentity(String userIdentity) {
		this.userIdentity = userIdentity;
	}

	public String getType() {
		return OperateEnum.valueOfOpt(type);
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getModule() {
		return ModuleEnum.valueOfLog(module);
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getLogDesc() {
		return logDesc;
	}

	public void setLogDesc(String logDesc) {
		this.logDesc = logDesc;
	}
	
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}
