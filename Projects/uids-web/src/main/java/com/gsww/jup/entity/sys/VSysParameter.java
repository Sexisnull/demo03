package com.gsww.jup.entity.sys;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "V_SYS_PARAMETER")
public class VSysParameter {

	private String paraId;
	private String paraCode;
	private String paraName;
	private int paraSeq;
	private String paraState;
	private String paraTypeName;
	private String paraTypeDesc;
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	public String getParaId() {
		return paraId;
	}
	public void setParaId(String paraId) {
		this.paraId = paraId;
	}
	public String getParaCode() {
		return paraCode;
	}
	public void setParaCode(String paraCode) {
		this.paraCode = paraCode;
	}
	public String getParaName() {
		return paraName;
	}
	public void setParaName(String paraName) {
		this.paraName = paraName;
	}
	public int getParaSeq() {
		return paraSeq;
	}
	public void setParaSeq(int paraSeq) {
		this.paraSeq = paraSeq;
	}
	public String getParaState() {
		return paraState;
	}
	public void setParaState(String paraState) {
		this.paraState = paraState;
	}
	public String getParaTypeName() {
		return paraTypeName;
	}
	public void setParaTypeName(String paraTypeName) {
		this.paraTypeName = paraTypeName;
	}
	public String getParaTypeDesc() {
		return paraTypeDesc;
	}
	public void setParaTypeDesc(String paraTypeDesc) {
		this.paraTypeDesc = paraTypeDesc;
	}
	
	
	
}
