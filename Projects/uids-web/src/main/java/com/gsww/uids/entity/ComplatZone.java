package com.gsww.uids.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Title: ComplatZone.java 
 * entity Description: 区域管理实体
 * 
 * @author yangxia
 * @created 2017年9月15日 上午11:04:17
 */
@Entity
@Table(name = "COMPLAT_ZONE")
public class ComplatZone implements Serializable {
	private static final long serialVersionUID = -123448301699001298L;

	private Integer iid;
	private String name;
	private String pinyin;
	private Integer pid;
	private String orderId;
	private String codeId;
	private Integer type;

	public ComplatZone() {
	}

	public ComplatZone(Integer iid, String name, String pinyin, Integer pid, String orderId, String codeId,
			Integer type) {
		super();
		this.iid = iid;
		this.name = name;
		this.pinyin = pinyin;
		this.pid = pid;
		this.orderId = orderId;
		this.codeId = codeId;
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IID")
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

	@Column(name = "PINYIN")
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column(name = "PID")
	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	@Column(name = "ORDERID")
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "CODEID")
	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	@Column(name = "TYPE")
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
