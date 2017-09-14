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
 * SysRoleMenuRel entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_ROLE_MENU_REL")
public class SysRoleMenuRel implements java.io.Serializable {

	// Fields

	private String roleMenuId;
	private SysMenu sysMenu;
	private String roleId;

	// Constructors

	/** default constructor */
	public SysRoleMenuRel() {
	}

	/** full constructor */
	public SysRoleMenuRel(SysMenu sysMenu, String roleId) {
		this.sysMenu = sysMenu;
		this.roleId = roleId;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "ROLE_MENU_ID")
	public String getRoleMenuId() {
		return this.roleMenuId;
	}

	public void setRoleMenuId(String roleMenuId) {
		this.roleMenuId = roleMenuId;
	}
	
	@ManyToOne
    @JoinColumn(name = "MENU_ID")
	public SysMenu getSysMenu() {
		return this.sysMenu;
	}

	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}

	@Column(name = "ROLE_ID")
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

}