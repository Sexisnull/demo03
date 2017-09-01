package com.gsww.uids.manager.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 个人、法人实名认证参数
 * 
 * @author sunbw
 *
 */
@Entity
@Table(name = "SYS_AUTH_PARAM")
public class SystemAuthParam extends PO {

	private static final long serialVersionUID = 3283334200134035405L;
	
	/**
	 * 法人实名认证开关（0-开启；1-关闭）
	 */
	@Column(name = "CORP_AUTH_SWITCH", columnDefinition = "INT default 1")
	private int corpAuthSwitch = 1;
	
	/**
	 * 认证模式
	 */
	@Column(name = "CORP_AUTH_MODEL", columnDefinition = "INT default 1")
	private int authModel = 1;
	
	/**
	 * 法人认证接口参数requestcod
	 */
	@Column(name = "CORP_AUTH_CODE",length = 32)
	private String corpAuthCode;
	
	/**
	 * 法人认证接口参数username
	 */
	@Column(name = "CORP_AUTH_USER_NAME",length = 128)
	private String corpAuthUserName;
	
	/**
	 * 法人认证接口参数password
	 */
	@Column(name = "CORP_AUTH_PWD",length = 128)
	private String corpAuthPwd;
	
	/**
	 * 法人认证换取token地址
	 */
	@Column(name = "CORP_AUTH_TOKEN_URL",length = 128)
	private String corpAuthTokenUrl;
	
	/**
	 * 法人认证实名比对地址
	 */
	@Column(name = "CORP_AUTH_COMPARE_URL",length = 128)
	private String corpAuthCompareUrl;
	
	/**
	 * 个人认证实名比对地址
	 */
	@Column(name = "PUBLIC_AUTH_COMPARE_URL",length = 128)
	private String publicAuthCompareUrl;
	
	/**
	 * 获取法人详细信息地址
	 */
	@Column(name = "CORP_DETAIL_URL",length = 128)
	private String corpDetailUrl;
	
	/*------------------------------*/
	
	/**
	 * 个人实名认证开关（0-开启；1-关闭）
	 */
	@Column(name = "PERS_AUTH_SWITCH", columnDefinition = "INT default 1")
	private int personalAuthSwitch = 0;
	
	/**
	 * 认证模式
	 */
	@Column(name = "PERS_AUTH_MODE",length = 32)
	private String personalAuthMode;
	
	/**
	 * 个人认证接口参数requestcod
	 */
	@Column(name = "PERS_AUTH_CODE",length = 32)
	private String personalAuthCode;
	
	/**
	 * 个人认证接口参数username
	 */
	@Column(name = "PERS_AUTH_USER_NAME",length = 128)
	private String personalAuthUserName;
	
	/**
	 * 个人认证接口参数password
	 */
	@Column(name = "PERS_AUTH_PWD",length = 128)
	private String personalAuthPwd;
	
	/**
	 * 个人认证换取token地址
	 */
	@Column(name = "PERS_AUTH_TOKEN_URL",length = 128)
	private String personalAuthTokenUrl;
	
	/**
	 * 个人认证实名比对地址
	 */
	@Column(name = "PERS_AUTH_COMPARE_URL",length = 128)
	private String personalAuthCompareUrl;

	public int getCorpAuthSwitch() {
		return corpAuthSwitch;
	}

	public void setCorpAuthSwitch(int corpAuthSwitch) {
		this.corpAuthSwitch = corpAuthSwitch;
	}

	public String getCorpAuthCode() {
		return corpAuthCode;
	}

	public void setCorpAuthCode(String corpAuthCode) {
		this.corpAuthCode = corpAuthCode;
	}

	public String getCorpAuthUserName() {
		return corpAuthUserName;
	}

	public void setCorpAuthUserName(String corpAuthUserName) {
		this.corpAuthUserName = corpAuthUserName;
	}

	public String getCorpAuthPwd() {
		return corpAuthPwd;
	}

	public void setCorpAuthPwd(String corpAuthPwd) {
		this.corpAuthPwd = corpAuthPwd;
	}

	public String getCorpAuthTokenUrl() {
		return corpAuthTokenUrl;
	}

	public void setCorpAuthTokenUrl(String corpAuthTokenUrl) {
		this.corpAuthTokenUrl = corpAuthTokenUrl;
	}

	public String getCorpAuthCompareUrl() {
		return corpAuthCompareUrl;
	}

	public void setCorpAuthCompareUrl(String corpAuthCompareUrl) {
		this.corpAuthCompareUrl = corpAuthCompareUrl;
	}

	public String getPublicAuthCompareUrl() {
		return publicAuthCompareUrl;
	}

	public void setPublicAuthCompareUrl(String publicAuthCompareUrl) {
		this.publicAuthCompareUrl = publicAuthCompareUrl;
	}

	public String getCorpDetailUrl() {
		return corpDetailUrl;
	}

	public void setCorpDetailUrl(String corpDetailUrl) {
		this.corpDetailUrl = corpDetailUrl;
	}

	public int getPersonalAuthSwitch() {
		return personalAuthSwitch;
	}

	public void setPersonalAuthSwitch(int personalAuthSwitch) {
		this.personalAuthSwitch = personalAuthSwitch;
	}

	public String getPersonalAuthMode() {
		return personalAuthMode;
	}

	public void setPersonalAuthMode(String personalAuthMode) {
		this.personalAuthMode = personalAuthMode;
	}

	public String getPersonalAuthCode() {
		return personalAuthCode;
	}

	public void setPersonalAuthCode(String personalAuthCode) {
		this.personalAuthCode = personalAuthCode;
	}

	public String getPersonalAuthUserName() {
		return personalAuthUserName;
	}

	public void setPersonalAuthUserName(String personalAuthUserName) {
		this.personalAuthUserName = personalAuthUserName;
	}

	public String getPersonalAuthPwd() {
		return personalAuthPwd;
	}

	public void setPersonalAuthPwd(String personalAuthPwd) {
		this.personalAuthPwd = personalAuthPwd;
	}

	public String getPersonalAuthTokenUrl() {
		return personalAuthTokenUrl;
	}

	public void setPersonalAuthTokenUrl(String personalAuthTokenUrl) {
		this.personalAuthTokenUrl = personalAuthTokenUrl;
	}

	public String getPersonalAuthCompareUrl() {
		return personalAuthCompareUrl;
	}

	public void setPersonalAuthCompareUrl(String personalAuthCompareUrl) {
		this.personalAuthCompareUrl = personalAuthCompareUrl;
	}

	public int getAuthModel() {
		return authModel;
	}

	public void setAuthModel(int authModel) {
		this.authModel = authModel;
	}
	
	
}
