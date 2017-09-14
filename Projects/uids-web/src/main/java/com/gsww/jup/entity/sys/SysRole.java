/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-entity</p>
 * <p>创建时间 : 2014-7-28 下午07:12:42</p>
 * <p>类描述 : 角色实体        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
@Entity
@Table(name = "SYS_ROLE")
public class SysRole implements java.io.Serializable{

	private static final long serialVersionUID = -1375109781906435423L;
	// Fields

	private String roleId;
	private String roleName;
	private String roleDesc;
	private String roleState;
	private String createTime;

	// Constructors

	/** default constructor */
	public SysRole() {
	}

	/** minimal constructor */
	public SysRole(String roleName, String roleState) {
		this.roleName = roleName;
		this.roleState = roleState;
	}

	/** full constructor */
	public SysRole(String roleName, String roleDesc, String roleState,
			String createTime) {
		this.roleName = roleName;
		this.roleDesc = roleDesc;
		this.roleState = roleState;
		this.createTime = createTime;
	}

	// Property accessors

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "ROLE_NAME")
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Column(name = "ROLE_DESC")
	public String getRoleDesc() {
		return this.roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	@Column(name = "ROLE_STATE")
	public String getRoleState() {
		return this.roleState;
	}

	public void setRoleState(String roleState) {
		this.roleState = roleState;
	}

	@Column(name = "CREATE_TIME")
	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}