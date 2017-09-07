package com.gsww.jup.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="complat_role")
public class ComplatRole implements Serializable{

	private static final long serialVersionUID = -1064119275354985881L;
	
	
	private int iid;
	private String uuid;
	private String name;
	private String spec;
	private int isdeFault;
	private int type;
	private String pinYin;
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "IID", unique = true, nullable = false)
	public int getIid() {
		return iid;
	}
	public void setIid(int iid) {
		this.iid = iid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSpec() {
		return spec;
	}
	public void setSpec(String spec) {
		this.spec = spec;
	}
	public int getIsdeFault() {
		return isdeFault;
	}
	public void setIsdeFault(int isdeFault) {
		this.isdeFault = isdeFault;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getPinYin() {
		return pinYin;
	}
	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public ComplatRole(int iid, String uuid, String name, String spec,
			int isdeFault, int type, String pinYin) {
		super();
		this.iid = iid;
		this.uuid = uuid;
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
