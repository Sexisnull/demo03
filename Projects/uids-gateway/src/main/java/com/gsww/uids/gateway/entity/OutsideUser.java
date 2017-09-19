package com.gsww.uids.gateway.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class OutsideUser implements Serializable {
	private static final long serialVersionUID = 2506351625961775795L;

	private Integer iid;

	private String uuid;

	private String loginName;

	private String pwd;

	private String name;

	private Integer age;

	private String sex;

	private Integer enable = Integer.valueOf(1);

	private String degree;

	private String pinYin;

	private Integer papersType = Integer.valueOf(1);

	private String papersNumber;

	private String description;

	private String mobile;

	private String phone;

	private String comptel;

	private String fax;

	private String email;

	private String qq;

	private String msn;

	private String address;

	private String post;

	private String workUnit;

	private String headship;

	private Date birthDate;

	private Date loginTime;

	private String loginIp;

	private String regip;

	private Date createTime;

	private String regsite;

	private String rejectReason;

	private Integer isauth = Integer.valueOf(0);

	private Integer authState = Integer.valueOf(0);

	private String headpic;

	private String headRenamePic;

	private String bodypic;

	private String bodyRenamePic;

	private Integer isupload = Integer.valueOf(0);

	private Date modifytime;
	private List<Role> roleList;

	private Integer opersign;

	private Integer synState = Integer.valueOf(0);

	private String residenceId = "";

	private String presidenceId = "";

	private String gpresidenceId = "";

	private String residenceDetail = "";

	private String livingAreaId = "";

	private String plivingAreaId = "";

	private String gplivingAreaId = "";

	private String livingAreaDetail = "";

	private Integer isCellphoneVerified = Integer.valueOf(0);

	public Integer getIid() {
		return this.iid;
	}

	public Integer getIsCellphoneVerified() {
		return this.isCellphoneVerified;
	}

	public void setIsCellphoneVerified(Integer isCellphoneVerified) {
		this.isCellphoneVerified = isCellphoneVerified;
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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Integer getEnable() {
		return this.enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPinYin() {
		return this.pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public Integer getPapersType() {
		return this.papersType;
	}

	public void setPapersType(Integer papersType) {
		this.papersType = papersType;
	}

	public String getPapersNumber() {
		return this.papersNumber;
	}

	public void setPapersNumber(String papersNumber) {
		this.papersNumber = papersNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return this.post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getWorkUnit() {
		return this.workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getHeadship() {
		return this.headship;
	}

	public void setHeadship(String headship) {
		this.headship = headship;
	}

	public Date getBirthDate() {
		return this.birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return this.loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getRegip() {
		return this.regip;
	}

	public void setRegip(String regip) {
		this.regip = regip;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRegsite() {
		return this.regsite;
	}

	public void setRegsite(String regsite) {
		this.regsite = regsite;
	}

	public List<Role> getRoleList() {
		return this.roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public Integer getIsauth() {
		return this.isauth;
	}

	public void setIsauth(Integer isauth) {
		this.isauth = isauth;
	}

	public String getHeadpic() {
		return this.headpic;
	}

	public void setHeadpic(String headpic) {
		this.headpic = headpic;
	}

	public String getBodypic() {
		return this.bodypic;
	}

	public void setBodypic(String bodypic) {
		this.bodypic = bodypic;
	}

	public Integer getAuthState() {
		return this.authState;
	}

	public void setAuthState(Integer authState) {
		this.authState = authState;
	}

	public Integer getIsupload() {
		return this.isupload;
	}

	public void setIsupload(Integer isupload) {
		this.isupload = isupload;
	}

	public String getComptel() {
		return this.comptel;
	}

	public void setComptel(String comptel) {
		this.comptel = comptel;
	}

	public String getHeadRenamePic() {
		return this.headRenamePic;
	}

	public void setHeadRenamePic(String headRenamePic) {
		this.headRenamePic = headRenamePic;
	}

	public String getBodyRenamePic() {
		return this.bodyRenamePic;
	}

	public void setBodyRenamePic(String bodyRenamePic) {
		this.bodyRenamePic = bodyRenamePic;
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
		return 2506351625961775795L;
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
}