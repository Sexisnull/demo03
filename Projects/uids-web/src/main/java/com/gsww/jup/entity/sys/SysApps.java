package com.gsww.jup.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
/**
 * 应用系统管理实体类
 * @author taoweixin
 *
 */
@Entity
@Table(name = "SYS_APPS")
public class SysApps implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 19971922092282908L;
	
	private String key;             //主键
	private String appName;         //应用名称
	private String appUrl;          //应用URL
	private String appState;        //应用状态  00A 启用  00B 禁用 ( 默认启用00A)
	private String appGreenChanal;  //启用绿色通道 00A 未启用，00B启用  (默认未启用00A)
	private String appLogo;         //应用图标
	private String appDesc;         //应用描述
	private String createTime;      //创建时间
	private String appCode; 		//应用编码
	
	
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "SYS_APPS_KEY", unique = true, nullable = false)
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	@Column(name = "APP_NAME")
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	@Column(name = "APP_URL")
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	@Column(name = "APP_STATE")
	public String getAppState() {
		return appState;
	}
	public void setAppState(String appState) {
		this.appState = appState;
	}
	@Column(name = "APP_GREEN_CHANAL")
	public String getAppGreenChanal() {
		return appGreenChanal;
	}
	public void setAppGreenChanal(String appGreenChanal) {
		this.appGreenChanal = appGreenChanal;
	}
	@Column(name = "APP_LOGO")
	public String getAppLogo() {
		return appLogo;
	}
	public void setAppLogo(String appLogo) {
		this.appLogo = appLogo;
	}
	@Column(name = "APP_DESC")
	public String getAppDesc() {
		return appDesc;
	}
	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}
	@Column(name = "CREATE_TIME")
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	@Column(name = "APP_CODE")
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	
	
	
}
