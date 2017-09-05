package com.gsww.uids.manager.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 账号信息
 * 
 * @author sunbw
 *
 */
@Entity
@Table(name = "UIDS_ACCOUNT_ERROR_TEMP")
public class AccountErrorTemp extends PO {

	private static final long serialVersionUID = 88509615711334023L;

	/**
	 * 错误信息临时保存
	 */
	@Column(name = "ERROR_MESSAGE",length = 2048)
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
