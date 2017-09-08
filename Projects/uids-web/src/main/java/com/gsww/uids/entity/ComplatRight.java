package com.gsww.ischool.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * ComplatRight entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "complat_right", catalog = "uidsdx")
public class ComplatRight implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String codeid;
	private String parentcodeid;
	private String moduleid;
	private String modulename;
	private String functionid;
	private String functionname;
	private String dynamicid;
	private String custom;

	// Constructors

	/** default constructor */
	public ComplatRight() {
	}

	/** full constructor */
	public ComplatRight(String codeid, String parentcodeid, String moduleid,
			String modulename, String functionid, String functionname,
			String dynamicid, String custom) {
		this.codeid = codeid;
		this.parentcodeid = parentcodeid;
		this.moduleid = moduleid;
		this.modulename = modulename;
		this.functionid = functionid;
		this.functionname = functionname;
		this.dynamicid = dynamicid;
		this.custom = custom;
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

	@Column(name = "codeid")
	public String getCodeid() {
		return this.codeid;
	}

	public void setCodeid(String codeid) {
		this.codeid = codeid;
	}

	@Column(name = "parentcodeid")
	public String getParentcodeid() {
		return this.parentcodeid;
	}

	public void setParentcodeid(String parentcodeid) {
		this.parentcodeid = parentcodeid;
	}

	@Column(name = "moduleid")
	public String getModuleid() {
		return this.moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	@Column(name = "modulename")
	public String getModulename() {
		return this.modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	@Column(name = "functionid")
	public String getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	@Column(name = "functionname")
	public String getFunctionname() {
		return this.functionname;
	}

	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}

	@Column(name = "dynamicid")
	public String getDynamicid() {
		return this.dynamicid;
	}

	public void setDynamicid(String dynamicid) {
		this.dynamicid = dynamicid;
	}

	@Column(name = "custom")
	public String getCustom() {
		return this.custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

}