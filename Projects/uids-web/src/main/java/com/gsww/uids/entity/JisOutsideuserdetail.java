package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisOutsideuserdetail entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_outsideuserdetail", catalog = "uidsdx")
public class JisOutsideuserdetail implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -403173206924352981L;
	private Integer iid;
	private Integer userid;
	private String comptel;
	private String cardid;

	// Constructors

	/** default constructor */
	public JisOutsideuserdetail() {
	}

	/** full constructor */
	public JisOutsideuserdetail(Integer userid, String comptel, String cardid) {
		this.userid = userid;
		this.comptel = comptel;
		this.cardid = cardid;
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

	@Column(name = "userid")
	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	@Column(name = "comptel")
	public String getComptel() {
		return this.comptel;
	}

	public void setComptel(String comptel) {
		this.comptel = comptel;
	}

	@Column(name = "cardid")
	public String getCardid() {
		return this.cardid;
	}

	public void setCardid(String cardid) {
		this.cardid = cardid;
	}

}