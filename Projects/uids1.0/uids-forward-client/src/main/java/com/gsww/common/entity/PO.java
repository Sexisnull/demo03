package com.gsww.common.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

/**
 * 实体类公共
 * 
 * @author simplife
 *
 */
@MappedSuperclass
public class PO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name="UUID", length=32, nullable=false, unique=true)
    @GenericGenerator(name="generator", strategy="uuid.hex")
    @GeneratedValue(generator="generator")
	private String uuid;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
}
