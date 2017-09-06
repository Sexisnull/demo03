package com.hanweb.jis.expansion.entity;

public class JisMsgEntity {
	/**
	 * ID
	 */
	private int id = 0;
	/**
	 * 成功？
	 */
	private boolean isSuccess = false;
	/**
	 * 错误信息
	 */
	private String  message = "";
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
