package com.gsww.jup.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * SysLog entity. @author MyEclipse Persistence Tools
 */

@Entity
@Table(name = "SYS_LOG")
public class SysLog implements java.io.Serializable {

	// Fields

	private String logId;
	private String operatorTime;
	private String logModel;
	private String logModelOperator;
	private String userAcctId;
	private String logIp;
	private String logType;
	private String logContent;

	// Constructors

	/** default constructor */
	public SysLog() {
	}

	/** minimal constructor */
	public SysLog(String operatorTime, String logModel, String userAcctId) {
		this.operatorTime = operatorTime;
		this.logModel = logModel;
		this.userAcctId = userAcctId;
	}

	/** full constructor */
	public SysLog(String operatorTime, String logModel,
			String logModelOperator, String userAcctId, String logIp,
			String logType, String logContent) {
		this.operatorTime = operatorTime;
		this.logModel = logModel;
		this.logModelOperator = logModelOperator;
		this.userAcctId = userAcctId;
		this.logIp = logIp;
		this.logType = logType;
		this.logContent = logContent;
	}

	// Property accessors

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "LOG_ID", unique = true, nullable = false)
	public String getLogId() {
		return this.logId;
	}

	public void setLogId(String logId) {
		this.logId = logId;
	}

	@Column(name = "OPERATOR_TIME")
	public String getOperatorTime() {
		return this.operatorTime;
	}

	public void setOperatorTime(String operatorTime) {
		this.operatorTime = operatorTime;
	}

	@Column(name = "LOG_MODEL")
	public String getLogModel() {
		return this.logModel;
	}

	public void setLogModel(String logModel) {
		this.logModel = logModel;
	}

	@Column(name = "LOG_MODEL_OPERATOR")
	public String getLogModelOperator() {
		return this.logModelOperator;
	}

	public void setLogModelOperator(String logModelOperator) {
		this.logModelOperator = logModelOperator;
	}

	@Column(name = "USER_ACCT_ID")
	public String getUserAcctId() {
		return this.userAcctId;
	}

	public void setUserAcctId(String userAcctId) {
		this.userAcctId = userAcctId;
	}

	@Column(name = "LOG_IP")
	public String getLogIp() {
		return this.logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	@Column(name = "LOG_TYPE")
	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	@Column(name = "LOG_CONTENT")
	public String getLogContent() {
		return this.logContent;
	}

	public void setLogContent(String logContent) {
		this.logContent = logContent;
	}

}