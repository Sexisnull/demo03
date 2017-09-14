package com.gsww.jup.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SysRoleOperRel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_ROLE_OPER_REL")
public class SysRoleOperRel implements java.io.Serializable {

	// Fields

	private String roleOperId;
	private SysOperator sysOperator;
	private String roleId;

	// Constructors

	/** default constructor */
	public SysRoleOperRel() {
	}

	/** full constructor */
	public SysRoleOperRel(SysOperator sysOperator, String roleId) {
		this.sysOperator = sysOperator;
		this.roleId = roleId;
	}

	// Property accessors

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "ROLE_OPER_ID", unique = true, nullable = false, length = 32)
	public String getRoleOperId() {
		return this.roleOperId;
	}

	public void setRoleOperId(String roleOperId) {
		this.roleOperId = roleOperId;
	}

	@ManyToOne
    @JoinColumn(name = "OPERATOR_ID")
	public SysOperator getSysOperator() {
		return this.sysOperator;
	}

	public void setSysOperator(SysOperator sysOperator) {
		this.sysOperator = sysOperator;
	}

	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
}