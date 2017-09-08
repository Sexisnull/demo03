package com.gsww.ischool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ComplatRoleright entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "complat_roleright", catalog = "uidsdx")
public class ComplatRoleright implements java.io.Serializable {

	// Fields

	private Integer iid;
	private Integer roleid;
	private Integer rightid;

	// Constructors

	/** default constructor */
	public ComplatRoleright() {
	}

	/** full constructor */
	public ComplatRoleright(Integer roleid, Integer rightid) {
		this.roleid = roleid;
		this.rightid = rightid;
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

	@Column(name = "rightid")
	public Integer getRightid() {
		return this.rightid;
	}

	public void setRightid(Integer rightid) {
		this.rightid = rightid;
	}

}