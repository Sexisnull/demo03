package com.gsww.jup.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 */
@Entity
@Table(name = "complat_outsideuser")
public class OutsideuserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private int iid;
	private String uuid;
	private String loginName;
	private String pwd;
	private String name;
	private int age;
	private String sex;
	private int enable;
	private String degree;
	private String pinyin;
	private int papersType;
	private String papersNumber;
	private String description;
	private String mobile;
	private String phone;
	private String fax;
	private String email;
	private String qq;
	private String msn;
	private String address;
	private String post;
	private String workUnit;
	private String headShip;
	private String birthDate;
	private String loginTime;
	private String loginIp;
	private String regIp;
	private String createTime;
	private String regSite;
	private int isAuth;
	private int authState;
	private String headPic;
	private String bodyPic;
	private int isUpload;
	private String compTel;
	private String rejectReason;
	private String headRenamePic;
	private String bodyRenamePic;

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "IID", unique = true, nullable = false)
	public int getIid() {
		return iid;
	}

	public void setIid(int iid) {
		this.iid = iid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getEnable() {
		return enable;
	}

	public void setEnable(int enable) {
		this.enable = enable;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public int getPapersType() {
		return papersType;
	}

	public void setPapersType(int papersType) {
		this.papersType = papersType;
	}

	public String getPapersNumber() {
		return papersNumber;
	}

	public void setPapersNumber(String papersNumber) {
		this.papersNumber = papersNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	public String getHeadShip() {
		return headShip;
	}

	public void setHeadShip(String headShip) {
		this.headShip = headShip;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRegSite() {
		return regSite;
	}

	public void setRegSite(String regSite) {
		this.regSite = regSite;
	}

	public int getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(int isAuth) {
		this.isAuth = isAuth;
	}

	public int getAuthState() {
		return authState;
	}

	public void setAuthState(int authState) {
		this.authState = authState;
	}

	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	public String getBodyPic() {
		return bodyPic;
	}

	public void setBodyPic(String bodyPic) {
		this.bodyPic = bodyPic;
	}

	public int getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(int isUpload) {
		this.isUpload = isUpload;
	}

	public String getCompTel() {
		return compTel;
	}

	public void setCompTel(String compTel) {
		this.compTel = compTel;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getHeadRenamePic() {
		return headRenamePic;
	}

	public void setHeadRenamePic(String headRenamePic) {
		this.headRenamePic = headRenamePic;
	}

	public String getBodyRenamePic() {
		return bodyRenamePic;
	}

	public void setBodyRenamePic(String bodyRenamePic) {
		this.bodyRenamePic = bodyRenamePic;
	}

	public OutsideuserEntity(int iid, String uuid, String loginName, String pwd, String name, int age, String sex,
			int enable, String degree, String pinyin, int papersType, String papersNumber, String description,
			String mobile, String phone, String fax, String email, String qq, String msn, String address, String post,
			String workUnit, String headShip, String birthDate, String loginTime, String loginIp, String regIp,
			String createTime, String regSite, int isAuth, int authState, String headPic, String bodyPic, int isUpload,
			String compTel, String rejectReason, String headRenamePic, String bodyRenamePic) {
		super();
		this.iid = iid;
		this.uuid = uuid;
		this.loginName = loginName;
		this.pwd = pwd;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.enable = enable;
		this.degree = degree;
		this.pinyin = pinyin;
		this.papersType = papersType;
		this.papersNumber = papersNumber;
		this.description = description;
		this.mobile = mobile;
		this.phone = phone;
		this.fax = fax;
		this.email = email;
		this.qq = qq;
		this.msn = msn;
		this.address = address;
		this.post = post;
		this.workUnit = workUnit;
		this.headShip = headShip;
		this.birthDate = birthDate;
		this.loginTime = loginTime;
		this.loginIp = loginIp;
		this.regIp = regIp;
		this.createTime = createTime;
		this.regSite = regSite;
		this.isAuth = isAuth;
		this.authState = authState;
		this.headPic = headPic;
		this.bodyPic = bodyPic;
		this.isUpload = isUpload;
		this.compTel = compTel;
		this.rejectReason = rejectReason;
		this.headRenamePic = headRenamePic;
		this.bodyRenamePic = bodyRenamePic;
	}

	public OutsideuserEntity() {
	}

}
