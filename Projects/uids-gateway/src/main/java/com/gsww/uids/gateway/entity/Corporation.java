package com.gsww.uids.gateway.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Corporation {
	private Integer iid;

	private String uuid;

	private Integer type;

	private Integer enable = Integer.valueOf(1);

	private String loginName;

	private String pwd;
	private String mobile;

	private String phone;

	private String name;

	private String regNumber;

	private String realName;

	private String sex;

	private String post;

	private String address;

	private String scope;

	private String regorgName;

	private String serialNumber;

	private String headship;

	private String fax;

	private String email;

	private String cardNumber;

	private String orgNumber;

	private Date createTime;

	private String nation;

	private String pinYin;

	private Integer isauth = Integer.valueOf(0);

	private Integer authState = Integer.valueOf(0);

	private String cardPic;

	private String cardRenamePic;

	private String orgPic;

	private String orgRenamePic;

	private String licencePic;

	private String licenceRenamePic;

	private Integer isupload = Integer.valueOf(0);

	private String rejectReason;

	private Date modifytime;
	private Map<String, String> userFieldsMap = new HashMap();

	private Integer opersign;

	private Integer synState = Integer.valueOf(0);

	private Integer declarationIid;

	private String residenceId = "";

	private String presidenceId = "";

	private String gpresidenceId = "";

	private String residenceDetail = "";

	private String livingAreaId = "";

	private String plivingAreaId = "";

	private String gplivingAreaId = "";

	private String livingAreaDetail = "";

	private Date loginTime;

	private String loginip = "";

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginip() {
		return this.loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

	public Integer getIid() {
		return this.iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getUuid() {
		return this.uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegNumber() {
		return this.regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getScope() {
		return this.scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRegorgName() {
		return this.regorgName;
	}

	public void setRegorgName(String regorgName) {
		this.regorgName = regorgName;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getHeadship() {
		return this.headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getOrgNumber() {
		return this.orgNumber;
	}

	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getPinYin() {
		return this.pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public Integer getIsauth() {
		return this.isauth;
	}

	public void setIsauth(Integer isauth) {
		this.isauth = isauth;
	}

	public Map<String, String> getUserFieldsMap() {
		return this.userFieldsMap;
	}

	public void setUserFieldsMap(Map<String, String> userFieldsMap) {
		this.userFieldsMap = userFieldsMap;
	}

	public Integer getAuthState() {
		return this.authState;
	}

	public void setAuthState(Integer authState) {
		this.authState = authState;
	}

	public String getCardPic() {
		return this.cardPic;
	}

	public void setCardPic(String cardPic) {
		this.cardPic = cardPic;
	}

	public String getOrgPic() {
		return this.orgPic;
	}

	public void setOrgPic(String orgPic) {
		this.orgPic = orgPic;
	}

	public String getLicencePic() {
		return this.licencePic;
	}

	public void setLicencePic(String licencePic) {
		this.licencePic = licencePic;
	}

	public String getNation() {
		return this.nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public Integer getIsupload() {
		return this.isupload;
	}

	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	public String getCardRenamePic() {
		return this.cardRenamePic;
	}

	public void setCardRenamePic(String cardRenamePic) {
		this.cardRenamePic = cardRenamePic;
	}

	public String getOrgRenamePic() {
		return this.orgRenamePic;
	}

	public void setOrgRenamePic(String orgRenamePic) {
		this.orgRenamePic = orgRenamePic;
	}

	public String getLicenceRenamePic() {
		return this.licenceRenamePic;
	}

	public void setLicenceRenamePic(String licenceRenamePic) {
		this.licenceRenamePic = licenceRenamePic;
	}

	public String getRejectReason() {
		return this.rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public Date getModifytime() {
		return this.modifytime;
	}

	public void setModifytime(Date modifytime) {
		this.modifytime = modifytime;
	}

	public Integer getOpersign() {
		return this.opersign;
	}

	public void setOpersign(Integer opersign) {
		this.opersign = opersign;
	}

	public Integer getSynState() {
		return this.synState;
	}

	public void setSynState(Integer synState) {
		this.synState = synState;
	}

	public static long getSerialversionuid() {
		return -4109747289770321719L;
	}

	public String getResidenceId() {
		return this.residenceId;
	}

	public void setResidenceId(String residenceId) {
		this.residenceId = residenceId;
	}

	public String getResidenceDetail() {
		return this.residenceDetail;
	}

	public void setResidenceDetail(String residenceDetail) {
		this.residenceDetail = residenceDetail;
	}

	public String getLivingAreaId() {
		return this.livingAreaId;
	}

	public void setLivingAreaId(String livingAreaId) {
		this.livingAreaId = livingAreaId;
	}

	public String getLivingAreaDetail() {
		return this.livingAreaDetail;
	}

	public void setLivingAreaDetail(String livingAreaDetail) {
		this.livingAreaDetail = livingAreaDetail;
	}

	public String getPresidenceId() {
		return this.presidenceId;
	}

	public void setPresidenceId(String presidenceId) {
		this.presidenceId = presidenceId;
	}

	public String getGpresidenceId() {
		return this.gpresidenceId;
	}

	public void setGpresidenceId(String gpresidenceId) {
		this.gpresidenceId = gpresidenceId;
	}

	public String getPlivingAreaId() {
		return this.plivingAreaId;
	}

	public void setPlivingAreaId(String plivingAreaId) {
		this.plivingAreaId = plivingAreaId;
	}

	public String getGplivingAreaId() {
		return this.gplivingAreaId;
	}

	public void setGplivingAreaId(String gplivingAreaId) {
		this.gplivingAreaId = gplivingAreaId;
	}

	public Integer getDeclarationIid() {
		return this.declarationIid;
	}

	public void setDeclarationIid(Integer declarationIid) {
		this.declarationIid = declarationIid;
	}
}
