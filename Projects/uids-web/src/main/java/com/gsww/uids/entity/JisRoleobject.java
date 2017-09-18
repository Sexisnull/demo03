package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisRoleobject entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_roleobject")
public class JisRoleobject implements java.io.Serializable {

	// Fields

	private Integer iid;
	private Integer roleid;
	private Integer objectid;
	private Integer type;

	// Constructors

	/** default constructor */
	public JisRoleobject() {
	}

	/** full constructor */
	public JisRoleobject(Integer roleid, Integer objectid, Integer type) {
		this.roleid = roleid;
		this.objectid = objectid;
		this.type = type;
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

	@Column(name = "objectid")
	public Integer getObjectid() {
		return this.objectid;
	}

	public void setObjectid(Integer objectid) {
		this.objectid = objectid;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}