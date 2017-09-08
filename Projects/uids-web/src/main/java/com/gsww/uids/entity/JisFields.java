package com.gsww.uids.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JisFields entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "jis_fields", catalog = "uidsdx")
public class JisFields implements java.io.Serializable {

	// Fields

	private Integer iid;
	private String showname;
	private String fieldname;
	private Integer type;
	private Integer issys;
	private Integer iswrite;
	private String defvalue;
	private String fieldkeys;
	private String fieldvalues;

	// Constructors

	/** default constructor */
	public JisFields() {
	}

	/** full constructor */
	public JisFields(String showname, String fieldname, Integer type,
			Integer issys, Integer iswrite, String defvalue, String fieldkeys,
			String fieldvalues) {
		this.showname = showname;
		this.fieldname = fieldname;
		this.type = type;
		this.issys = issys;
		this.iswrite = iswrite;
		this.defvalue = defvalue;
		this.fieldkeys = fieldkeys;
		this.fieldvalues = fieldvalues;
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

	@Column(name = "showname", length = 100)
	public String getShowname() {
		return this.showname;
	}

	public void setShowname(String showname) {
		this.showname = showname;
	}

	@Column(name = "fieldname", length = 50)
	public String getFieldname() {
		return this.fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	@Column(name = "type")
	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	@Column(name = "issys")
	public Integer getIssys() {
		return this.issys;
	}

	public void setIssys(Integer issys) {
		this.issys = issys;
	}

	@Column(name = "iswrite")
	public Integer getIswrite() {
		return this.iswrite;
	}

	public void setIswrite(Integer iswrite) {
		this.iswrite = iswrite;
	}

	@Column(name = "defvalue", length = 50)
	public String getDefvalue() {
		return this.defvalue;
	}

	public void setDefvalue(String defvalue) {
		this.defvalue = defvalue;
	}

	@Column(name = "fieldkeys", length = 1000)
	public String getFieldkeys() {
		return this.fieldkeys;
	}

	public void setFieldkeys(String fieldkeys) {
		this.fieldkeys = fieldkeys;
	}

	@Column(name = "fieldvalues", length = 2000)
	public String getFieldvalues() {
		return this.fieldvalues;
	}

	public void setFieldvalues(String fieldvalues) {
		this.fieldvalues = fieldvalues;
	}

}