package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisRelation entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_relation", catalog = "uidsdx")
public class JisRelation implements java.io.Serializable {

	// Fields

	private Integer iid;
	private Integer temid;
	private Integer userid;
	private Integer type;

	// Constructors

	/** default constructor */
	public JisRelation() {
	}

	/** full constructor */
	public JisRelation(Integer temid, Integer userid, Integer type) {
		this.temid = temid;
		this.userid = userid;
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

	@Column(name = "temid")
	public Integer getTemid() {
		return this.temid;
	}

	public void setTemid(Integer temid) {
		this.temid = temid;
	}

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}