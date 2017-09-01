package com.gsww.uids.manager.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 机构信息表
 * 
 * @author sunbw
 *
 */
@Entity
@Table(name = "UIDS_ORGANIZATION_ERROR_TEMP")
public class OrganizationErrorTemp extends PO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5634609528187186090L;

	/**
	 * 错误信息
	 */
	@Column(name = "ERROR_MESSAGE", length = 2048)
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
}
