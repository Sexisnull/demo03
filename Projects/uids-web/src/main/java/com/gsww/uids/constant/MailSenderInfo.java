package com.gsww.uids.constant;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class MailSenderInfo {
	private String mailServerHost;
	private String mailServerPort = "25";
	private String fromAddress;
	private String toAddress;
	private String userName;
	private String password;
	private boolean validate = false;

	private String subject = "";

	private String content = "";

	private List<String> fileList = new ArrayList<String>();

	private List<String> picList = new ArrayList<String>();

	private boolean debug = false;

	private boolean isTxt = true;

	private String code = "UTF-8";

	public String getMailServerHost() {
		return this.mailServerHost;
	}

	public void setMailServerHost(String mailServerHost) {
		this.mailServerHost = mailServerHost;
	}

	public String getMailServerPort() {
		return this.mailServerPort;
	}

	public void setMailServerPort(String mailServerPort) {
		this.mailServerPort = mailServerPort;
	}

	public boolean isValidate() {
		return this.validate;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public List<String> getFileList() {
		return this.fileList;
	}

	public void setFileList(List<String> fileList) {
		this.fileList = fileList;
	}

	public List<String> getPicList() {
		return this.picList;
	}

	public void setPicList(List<String> picList) {
		this.picList = picList;
	}

	public String getFromAddress() {
		return this.fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String textContent) {
		this.content = textContent;
	}

	public boolean getDebug() {
		return this.debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public boolean isTxt() {
		return this.isTxt;
	}

	public void setIsTxt(boolean isTxt) {
		this.isTxt = isTxt;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Properties getProperties() {
		Properties p = new Properties();
		p.put("mail.smtp.host", this.mailServerHost);
		p.put("mail.smtp.port", this.mailServerPort);
		p.put("mail.smtp.auth", this.validate ? "true" : "false");
		return p;
	}
}
