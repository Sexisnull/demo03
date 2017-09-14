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
 * <p>创建时间 : 2014-7-30 上午09:17:19</p>
 * <p>类描述 : 用户角色实体        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">lzxij</a>
 */
@Entity
@Table(name = "SYS_ROLE_ACCT_REL")
public class SysRoleAcctRel implements java.io.Serializable {

	// Fields

	/** 
	 * serialVersionUID :  
	 */
	private static final long serialVersionUID = 4904314067890594752L;
	private String roleAcctId;
	private String roleId;
	private String userAcctId;

	// Constructors

	/** default constructor */
	public SysRoleAcctRel() {
	}

	/** full constructor */
	public SysRoleAcctRel(String roleId, String userAcctId) {
		this.roleId = roleId;
		this.userAcctId = userAcctId;
	}

	// Property accessors

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "ROLE_ACCT_ID")
	public String getRoleAcctId() {
		return this.roleAcctId;
	}

	public void setRoleAcctId(String roleAcctId) {
		this.roleAcctId = roleAcctId;
	}

	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "USER_ACCT_ID")
	public String getUserAcctId() {
		return userAcctId;
	}

	public void setUserAcctId(String userAcctId) {
		this.userAcctId = userAcctId;
	}
}