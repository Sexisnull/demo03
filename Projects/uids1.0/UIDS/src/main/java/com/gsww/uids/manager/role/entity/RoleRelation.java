package com.gsww.uids.manager.role.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gsww.common.entity.PO;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.user.entity.User;

/**
 * 用户角色：**用户在**机构拥有**角色
 * 或者（法人）账号角色：账号在**机构下拥有**角色
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_ROLE_RELATION")
public class RoleRelation extends PO {

	private static final long serialVersionUID = 2378026240275170063L;
	
	/**
	 * 用户
	 */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	/**
	 * 机构
	 */
	@ManyToOne
	@JoinColumn(name = "ORG_ID", nullable = false)
	private Organization org;
	
	/**
	 * 角色
	 */
	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = false)
	private Role role;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", nullable = false, length = 23)
	private String createTime;
	
	/**
	 * 是否删除：是-1，否-0
	 */
	@Column(name = "IS_DELETE", columnDefinition = "INT default 0")
	private int del = 0;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}
	
	public String getUserId() {
		if ( user != null ) {
			return user.getUuid();
		} else {
			return null;
		}
	}
	
	public String getOrgId() {
		return org.getUuid();
	}
	
	public String getOrgShortName() {
		return org.getShortName();
	}
	
	public String getRoleId() {
		return role.getUuid();
	}
	
	public String getRoleName() {
		return role.getName();
	}
}
