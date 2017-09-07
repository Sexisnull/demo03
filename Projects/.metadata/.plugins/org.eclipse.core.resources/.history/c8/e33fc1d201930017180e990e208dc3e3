package com.gsww.jup.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name = "SYS_USER_APPS")
public class SysUserApps {
	
	private String sysUserAppsKey;//主键
	private String sysAccountId;//外键
	private String sysAppsKey;//所属系统标识
		
	public SysUserApps() {
		super();
	}

	public SysUserApps(String sysUserAppsKey, String sysAccountId,
			String sysAppsKey) {
		super();
		this.sysUserAppsKey = sysUserAppsKey;
		this.sysAccountId = sysAccountId;
		this.sysAppsKey = sysAppsKey;
	}
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "SYS_USER_APPS_KEY", unique = true, nullable = false)
	public String getSysUserAppsKey() {
		return sysUserAppsKey;
	}
	public void setSysUserAppsKey(String sysUserAppsKey) {
		this.sysUserAppsKey = sysUserAppsKey;
	}
	@Column(name="SYS_ACCOUNT_ID")
	public String getSysAccountId() {
		return sysAccountId;
	}
	public void setSysAccountId(String sysAccountId) {
		this.sysAccountId = sysAccountId;
	}
	@Column(name="SYS_APPS_KEY")
	public String getSysAppsKey() {
		return sysAppsKey;
	}
	public void setSysAppsKey(String sysAppsKey) {
		this.sysAppsKey = sysAppsKey;
	}
	


}
