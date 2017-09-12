package com.gsww.uids.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
//用户角色实体
@Entity
@Table(name="complat_rolerelation")
public class ComplatRolerelation implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2796558422748048428L;
	
	private int iid;
	private int roleId;
	private int userId;
	private int groupId;
	public ComplatRolerelation() {
		super();
	}
	public ComplatRolerelation(int iid, int roleId, int userId, int groupId) {
		super();
		this.iid = iid;
		this.roleId = roleId;
		this.userId = userId;
		this.groupId = groupId;
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "IID", unique = true, nullable = false)
	public int getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	@Column(name="ROLEID")
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	@Column(name="USERID")
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	@Column(name="GROUPID")
	public int getGroupId() {
		return groupId;
	}
	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

}
