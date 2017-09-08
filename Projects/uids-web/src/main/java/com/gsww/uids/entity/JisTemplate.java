package com.gsww.ischool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisTemplate entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_template", catalog = "uidsdx")
public class JisTemplate implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String name;
	private Integer type;
	private Integer isdefault;
	private Integer isfront;
	private String url;

	// Constructors

	/** default constructor */
	public JisTemplate() {
	}

	/** full constructor */
	public JisTemplate(String name, Integer type, Integer isdefault,
			Integer isfront, String url) {
		this.name = name;
		this.type = type;
		this.isdefault = isdefault;
		this.isfront = isfront;
		this.url = url;
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

	@Column(name = "name", length = 50)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "isdefault")
	public Integer getIsdefault() {
		return this.isdefault;
	}

	public void setIsdefault(Integer isdefault) {
		this.isdefault = isdefault;
	}

	@Column(name = "isfront")
	public Integer getIsfront() {
		return this.isfront;
	}

	public void setIsfront(Integer isfront) {
		this.isfront = isfront;
	}

	@Column(name = "url")
	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}