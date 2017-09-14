package com.gsww.uids.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="complat_role")
public class ComplatRole implements Serializable{

	private static final long serialVersionUID = -1064119275354985881L;
	
	
	private Integer iid;
	private String name;
	private String spec;
	private Integer isdeFault;
	private Integer type;
	private String pinYin;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)  
	@Column(name = "IID", unique = true, nullable = false)
	public Integer getIid() {
		return iid;
	}
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "SPEC")
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	
	@Column(name = "ISDEFAULT")
	public Integer getIsdeFault() {
		return isdeFault;
	}
	public void setIsdeFault(Integer isdeFault) {
		this.isdeFault = isdeFault;
	}
	
	@Column(name = "TYPE")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "PINYIN")
	public String getPinYin() {
		return pinYin;
	}
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public ComplatRole(Integer iid,String name, String spec,
			Integer isdeFault, Integer type, String pinYin) {
		super();
		this.iid = iid;
		this.name = name;
		this.spec = spec;
		this.isdeFault = isdeFault;
		this.type = type;
		this.pinYin = pinYin;
	}
	public ComplatRole() {
		super();
	}
	
	

}
