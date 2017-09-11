package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ComplatRole entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "complat_role", catalog = "uidsdx")
public class ComplatRole implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String name;
	private String spec;
	private Integer isdefault;
	private Integer type;
	private String pinyin;

	// Constructors

	/** default constructor */
	public ComplatRole() {
	}

	/** full constructor */
	public ComplatRole(String name, String spec, Integer isdefault,
			Integer type, String pinyin) {
		this.name = name;
		this.spec = spec;
		this.isdefault = isdefault;
		this.type = type;
		this.pinyin = pinyin;
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

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "spec")
	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Column(name = "isdefault")
	public Integer getIsdefault() {
		return this.isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "pinyin")
	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

}