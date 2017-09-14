package com.gsww.jup.entity.sys;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 参数类型
 * @author zhangjy
 *
 */
@Entity
@Table(name = "SYS_PARAMETER_TYPE")
public class SysParaType{
	private static final long serialVersionUID = -3719656829396052454L;

	// Fields

	private String paraTypeId;
	private String paraTypeName;
	private String paraTypeDesc;
	private String paraTypeState;
	//private Set<SysPara> sysPara = new HashSet<SysPara>();
	private List<SysPara> sysPara = new ArrayList<SysPara>();

	// Constructors

	/** default constructor */
	public SysParaType() {
	}

	/** minimal constructor */
	public SysParaType(String paraTypeName, String paraTypeState) {
		this.paraTypeName = paraTypeName;
		this.paraTypeState = paraTypeState;
	}

	/** full constructor */
/*	public SysParaType(String paraTypeName, String paraTypeDesc,
			String paraTypeState, Set<SysPara> sysPara) {
		this.paraTypeName = paraTypeName;
		this.paraTypeDesc = paraTypeDesc;
		this.paraTypeState = paraTypeState;
		this. sysPara= sysPara;
	}
*/
	// Property accessors

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "PARA_TYPE_ID")
	public String getParaTypeId() {
		return this.paraTypeId;
	}

	public void setParaTypeId(String paraTypeId) {
		this.paraTypeId = paraTypeId;
	}

	@Column(name = "PARA_TYPE_NAME")
	public String getParaTypeName() {
		return this.paraTypeName;
	}

	public void setParaTypeName(String paraTypeName) {
		this.paraTypeName = paraTypeName;
	}

	@Column(name = "PARA_TYPE_DESC")
	public String getParaTypeDesc() {
		return this.paraTypeDesc;
	}

	public void setParaTypeDesc(String paraTypeDesc) {
		this.paraTypeDesc = paraTypeDesc;
	}

	@Column(name = "PARA_TYPE_STATE")
	public String getParaTypeState() {
		return this.paraTypeState;
	}

	public void setParaTypeState(String paraTypeState) {
		this.paraTypeState = paraTypeState;
	}

/*@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="sysParaType") 
	public Set<SysPara> getSysPara() {
		return sysPara;
	}

	public void setSysPara(Set<SysPara> sysPara) {
		this.sysPara = sysPara;
	}*/
	
	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="sysParaType") 
	public List<SysPara> getSysPara() {
		return sysPara;
	}

	public void setSysPara(List<SysPara> sysParameter) {
		this.sysPara = sysParameter;
	}
	
}
