package com.gsww.uids.gateway.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * 实体类-Corporation-complat_corporation
 * @author zcc
 *
 */
public class Corporation {
	private Integer iid;
	private String uuid;
	private Integer type;
	private Integer enable = Integer.valueOf(1);
	private String loginname;
	private String pwd;
	private String mobile;
	private String phone;
	private String name;
	private String regnumber;
	private String realname;
	private String sex;
	private String post;
	private String address;
	private String scope;
	private String regorgname;
	private String serialnumber;
	private String headship;
	private String fax;
	private String email;
	private String cardnumber;
	private String orgnumber;
	private Date createtime;
	private String nation;
	private String pinyin;
	private Integer isauth = Integer.valueOf(0);
	private Integer authstate = Integer.valueOf(0);
	private String cardpic;
	private String cardRenamepic;
	private String orgpic;
	private String orgrenamepic;
	private String licencepic;
	private String licencerenamepic;
	private Integer isupload = Integer.valueOf(0);
	private String rejectreason;
	private Integer synState = Integer.valueOf(0);
	private String residenceId = "";
	private String residenceDetail = "";
	private String livingAreaId = "";
	private String livingAreaDetail = "";
	private String presidenceId = "";
	private String gpresidenceId = "";
	private String plivingAreaId = "";
	private String gplivingAreaId = "";
	private Integer declarationIid;
	private Date loginTime;
	private String loginip = "";

	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegnumber() {
		return regnumber;
	}

	public void setRegnumber(String regnumber) {
		this.regnumber = regnumber;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getRegorgname() {
		return regorgname;
	}

	public void setRegorgname(String regorgname) {
		this.regorgname = regorgname;
	}

	public String getSerialnumber() {
		return serialnumber;
	}

	public void setSerialnumber(String serialnumber) {
		this.serialnumber = serialnumber;
	}

	public String getHeadship() {
		return headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCardnumber() {
		return cardnumber;
	}

	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
	}

	public String getOrgnumber() {
		return orgnumber;
	}

	public void setOrgnumber(String orgnumber) {
		this.orgnumber = orgnumber;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public Integer getIsauth() {
		return isauth;
	}

	public void setIsauth(Integer isauth) {
		this.isauth = isauth;
	}

	public Integer getAuthstate() {
		return authstate;
	}

	public void setAuthstate(Integer authstate) {
		this.authstate = authstate;
	}

	public String getCardpic() {
		return cardpic;
	}

	public void setCardpic(String cardpic) {
		this.cardpic = cardpic;
	}

	public String getCardRenamepic() {
		return cardRenamepic;
	}

	public void setCardRenamepic(String cardRenamepic) {
		this.cardRenamepic = cardRenamepic;
	}

	public String getOrgpic() {
		return orgpic;
	}

	public void setOrgpic(String orgpic) {
		this.orgpic = orgpic;
	}

	public String getOrgrenamepic() {
		return orgrenamepic;
	}

	public void setOrgrenamepic(String orgrenamepic) {
		this.orgrenamepic = orgrenamepic;
	}

	public String getLicencepic() {
		return licencepic;
	}

	public void setLicencepic(String licencepic) {
		this.licencepic = licencepic;
	}

	public String getLicencerenamepic() {
		return licencerenamepic;
	}

	public void setLicencerenamepic(String licencerenamepic) {
		this.licencerenamepic = licencerenamepic;
	}

	public Integer getIsupload() {
		return isupload;
	}

	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	public String getRejectreason() {
		return rejectreason;
	}

	public void setRejectreason(String rejectreason) {
		this.rejectreason = rejectreason;
	}

	public Integer getSynState() {
		return synState;
	}

	public void setSynState(Integer synState) {
		this.synState = synState;
	}

	public String getResidenceId() {
		return residenceId;
	}

	public void setResidenceId(String residenceId) {
		this.residenceId = residenceId;
	}

	public String getResidenceDetail() {
		return residenceDetail;
	}

	public void setResidenceDetail(String residenceDetail) {
		this.residenceDetail = residenceDetail;
	}

	public String getLivingAreaId() {
		return livingAreaId;
	}

	public void setLivingAreaId(String livingAreaId) {
		this.livingAreaId = livingAreaId;
	}

	public String getLivingAreaDetail() {
		return livingAreaDetail;
	}

	public void setLivingAreaDetail(String livingAreaDetail) {
		this.livingAreaDetail = livingAreaDetail;
	}

	public String getPresidenceId() {
		return presidenceId;
	}

	public void setPresidenceId(String presidenceId) {
		this.presidenceId = presidenceId;
	}

	public String getGpresidenceId() {
		return gpresidenceId;
	}

	public void setGpresidenceId(String gpresidenceId) {
		this.gpresidenceId = gpresidenceId;
	}

	public String getPlivingAreaId() {
		return plivingAreaId;
	}

	public void setPlivingAreaId(String plivingAreaId) {
		this.plivingAreaId = plivingAreaId;
	}

	public String getGplivingAreaId() {
		return gplivingAreaId;
	}

	public void setGplivingAreaId(String gplivingAreaId) {
		this.gplivingAreaId = gplivingAreaId;
	}

	public Integer getDeclarationIid() {
		return declarationIid;
	}

	public void setDeclarationIid(Integer declarationIid) {
		this.declarationIid = declarationIid;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginip() {
		return loginip;
	}

	public void setLoginip(String loginip) {
		this.loginip = loginip;
	}

}
