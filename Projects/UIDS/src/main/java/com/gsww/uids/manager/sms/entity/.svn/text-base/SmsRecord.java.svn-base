package com.gsww.uids.manager.sms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;
import com.gsww.common.util.TimeHelper;

/**
 * 短信记录实体
 * 
 * @author simplife
 *
 */
@Entity
@Table(name = "UIDS_SMS_RECORD")
public class SmsRecord extends PO {
	
	private static final long serialVersionUID = 212350429938967708L;
	
	/**
	 * 验证码的有效时长
	 */
	public static final int VALID_TIME_IN_SECONDS = 120;

	/**
	 * 手机号
	 */
	@Column(name = "MOBILE", nullable = false, length = 14)
	private String mobile;
	
	/**
	 * 验证码
	 */
	@Column(name = "VER_CODE", nullable = false, length = 6)
	private String code;
	
	/**
	 * 发送时间
	 */
	@Column(name = "SEND_TIME", nullable = false, length = 23)
	private String sendTime;
	
	/**
	 * 过期时间
	 */
	@Column(name = "FAILURE_TIME", nullable = false, length = 23)
	private String failureTime;
	
	/**
	 * 使用次数
	 */
	@Column(name = "USE_NUM")
	private int use = 0;
	
	/**
	 * 接口返回信息
	 */
	@Column(name = "BACK_MSG")
	private String backMsg;

	public SmsRecord(String mobile, String code, String backMsg) {
		super();
		this.mobile = mobile;
		this.code = code;
		this.sendTime = TimeHelper.getCurrentTime();
		this.failureTime = TimeHelper.addSecond(this.sendTime, VALID_TIME_IN_SECONDS);
		this.backMsg = backMsg;
	}
	
	public SmsRecord() {
		super();
		this.sendTime = TimeHelper.getCurrentTime();
		this.failureTime = TimeHelper.addSecond(this.sendTime, VALID_TIME_IN_SECONDS);
	}
	
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getFailureTime() {
		return failureTime;
	}

	public void setFailureTime(String failureTime) {
		this.failureTime = failureTime;
	}

	public int getUse() {
		return use;
	}

	public void setUse(int use) {
		this.use = use;
	}

	public String getBackMsg() {
		return backMsg;
	}

	public void setBackMsg(String backMsg) {
		this.backMsg = backMsg;
	}
}
