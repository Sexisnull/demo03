package com.gsww.ischool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ComplatRolerelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "complat_rolerelation", catalog = "uidsdx")
public class ComplatRolerelation implements java.io.Serializable {

	// Fields

	private Integer iid;
	private Integer roleid;
	private Integer userid;
	private Integer groupid;

	// Constructors

	/** default constructor */
	public ComplatRolerelation() {
	}

	/** full constructor */
	public ComplatRolerelation(Integer roleid, Integer userid, Integer groupid) {
		this.roleid = roleid;
		this.userid = userid;
		this.groupid = groupid;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "iid", unique = true, nullable = false)
	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	@Column(name = "roleid")
	public Integer getRoleid() {
		return this.roleid;
	}

	public void setRoleid(Integer roleid) {
		this.roleid = roleid;
	}

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "groupid")
	public Integer getGroupid() {
		return this.groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

}