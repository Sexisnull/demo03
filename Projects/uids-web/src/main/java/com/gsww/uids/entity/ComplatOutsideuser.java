package com.gsww.uids.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * Title: OutsideUser.java Description: 个人用户实体类
 * 
 * @author yangxia
 * @created 2017年9月8日 下午7:51:33
 */
@Entity
@Table(name = "COMPLAT_OUTSIDEUSER")
public class ComplatOutsideuser implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer iid; // 用户id
	private String uuid; // UUID
	private String loginName; // 登录名，如张三zhangs
	private String pwd; // 密码(加密)
	private String name; // 姓名
	private Integer age; // 年龄
	private String sex; // 性别:1-男0女
	private Integer enable; // 是否启用：0：禁用1：启用
	private String degree; // 学历
	private String pinyin; // 全拼首字母
	private Integer papersType; // 证件类型1身份证2教师证3军官证4其他默认身份证
	private String papersNumber;// 证件编号
	private String description; // 备注
	private String mobile; // 移动电话
	private String phone; // 固定电话
	private String fax; // 传真
	private String email; // Email
	private String qq; // QQ
	private String msn; // Msn
	private String address; // 家庭地址
	private String post; // 邮政编码
	private String workUnit; // 单位名称
	private String headShip; // 职务
	private Date birthDate; // 生日
	private Date loginTime; // 上次登录时间
	private String loginIp; // 上次登录ip
	private String regIp; // 注册ip
	private Date createTime; // 创建时间
	private String regSite; // 注册地点
	private Integer isAuth; // 实名认证标志 0-否，1-是
	private Integer authState; // 实名认证标志 0-未认证，1-审核通过 2-审核未通过
	private String headPic; // 身份证头部照片 上传文件名称
	private String bodyPic; // 身份证照片上传文件名称
	private Integer isUpload; // 是否上传实名认证资料 0-否，1-是
	private String compTel; // 公司电话
	private String rejectReason;// 审核未通过原因
	private String headRenamePic;// 身份证头部照片 新名称
	private String bodyRenamePic;// 身份证照片新名称
	private String residenceId; // 户籍所在地区
	private String presidenceId; // 户籍所在地市
	private String gpresidenceId; // 户籍所在地省
	private String residenceDetail; // 户籍所在地详址
	private String livingAreaId; // 常住地区
	private String plivingAreaId; // 常住地市
	private String gplivingAreaId; // 常住地省
	private String livingAreaDetail;// 常住详细地
	private Date modifyTime;// 修改时间
	private Integer synState;
	private Integer operSign;
	private Integer isCellphoneVerified;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IID")
	public Integer getIid() {
		return iid;
	}

	public void setIid(Integer iid) {
		this.iid = iid;
	}

	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
	@Column(name = "UUID")
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	@Column(name = "LOGINNAME")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@Column(name = "PWD")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Column(name = "NAME")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "AGE")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "SEX")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "ENABLE")
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	@Column(name = "DEGREE")
	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "PINYIN")
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column(name = "PAPERSTYPE")
	public Integer getPapersType() {
		return papersType;
	}

	public void setPapersType(Integer papersType) {
		this.papersType = papersType;
	}

	@Column(name = "PAPERSNUMBER")
	public String getPapersNumber() {
		return papersNumber;
	}

	public void setPapersNumber(String papersNumber) {
		this.papersNumber = papersNumber;
	}

	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "MOBILE")
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	@Column(name = "PHONE")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "FAX")
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "QQ")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "MSN")
	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "POST")
	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	@Column(name = "WORKUNIT")
	public String getWorkUnit() {
		return workUnit;
	}

	public void setWorkUnit(String workUnit) {
		this.workUnit = workUnit;
	}

	@Column(name = "HEADSHIP")
	public String getHeadShip() {
		return headShip;
	}

	public void setHeadShip(String headShip) {
		this.headShip = headShip;
	}

	@Column(name = "BIRTHDATE")
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	@Column(name = "LOGINTIME")
	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Column(name = "LOGINIP")
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@Column(name = "REGIP")
	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	@Column(name = "CREATETIME")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "REGSITE")
	public String getRegSite() {
		return regSite;
	}

	public void setRegSite(String regSite) {
		this.regSite = regSite;
	}

	@Column(name = "ISAUTH")
	public Integer getIsAuth() {
		return isAuth;
	}

	public void setIsAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}

	@Column(name = "AUTHSTATE")
	public Integer getAuthState() {
		return authState;
	}

	public void setAuthState(Integer authState) {
		this.authState = authState;
	}

	@Column(name = "HEADPIC")
	public String getHeadPic() {
		return headPic;
	}

	public void setHeadPic(String headPic) {
		this.headPic = headPic;
	}

	@Column(name = "BODYPIC")
	public String getBodyPic() {
		return bodyPic;
	}

	public void setBodyPic(String bodyPic) {
		this.bodyPic = bodyPic;
	}

	@Column(name = "ISUPLOAD")
	public Integer getIsUpload() {
		return isUpload;
	}

	public void setIsUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}

	@Column(name = "COMPTEL")
	public String getCompTel() {
		return compTel;
	}

	public void setCompTel(String compTel) {
		this.compTel = compTel;
	}

	@Column(name = "REJECTREASON")
	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	@Column(name = "HEADRENAMEPIC")
	public String getHeadRenamePic() {
		return headRenamePic;
	}

	public void setHeadRenamePic(String headRenamePic) {
		this.headRenamePic = headRenamePic;
	}

	@Column(name = "BODYRENAMEPIC")
	public String getBodyRenamePic() {
		return bodyRenamePic;
	}

	public void setBodyRenamePic(String bodyRenamePic) {
		this.bodyRenamePic = bodyRenamePic;
	}

	@Column(name = "RESIDENCEID")
	public String getResidenceId() {
		return residenceId;
	}

	public void setResidenceId(String residenceId) {
		this.residenceId = residenceId;
	}

	@Column(name = "PRESIDENCEID")
	public String getPresidenceId() {
		return presidenceId;
	}

	public void setPresidenceId(String presidenceId) {
		this.presidenceId = presidenceId;
	}

	@Column(name = "GPRESIDENCEID")
	public String getGpresidenceId() {
		return gpresidenceId;
	}

	public void setGpresidenceId(String gpresidenceId) {
		this.gpresidenceId = gpresidenceId;
	}

	@Column(name = "RESIDENCEDETAIL")
	public String getResidenceDetail() {
		return residenceDetail;
	}

	public void setResidenceDetail(String residenceDetail) {
		this.residenceDetail = residenceDetail;
	}

	@Column(name = "LIVINGAREAID")
	public String getLivingAreaId() {
		return livingAreaId;
	}

	public void setLivingAreaId(String livingAreaId) {
		this.livingAreaId = livingAreaId;
	}

	@Column(name = "PLIVINGAREAID")
	public String getPlivingAreaId() {
		return plivingAreaId;
	}

	public void setPlivingAreaId(String plivingAreaId) {
		this.plivingAreaId = plivingAreaId;
	}

	@Column(name = "GPLIVINGAREAID")
	public String getGplivingAreaId() {
		return gplivingAreaId;
	}

	public void setGplivingAreaId(String gplivingAreaId) {
		this.gplivingAreaId = gplivingAreaId;
	}

	@Column(name = "LIVINGAREADETAIL")
	public String getLivingAreaDetail() {
		return livingAreaDetail;
	}

	public void setLivingAreaDetail(String livingAreaDetail) {
		this.livingAreaDetail = livingAreaDetail;
	}

	@Column(name = "MODIFYTIME")
	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	@Column(name = "SYNSTATE")
	public Integer getSynState() {
		return synState;
	}

	public void setSynState(Integer synState) {
		this.synState = synState;
	}

	@Column(name = "OPERSIGN")
	public Integer getOperSign() {
		return operSign;
	}

	public void setOperSign(Integer operSign) {
		this.operSign = operSign;
	}

	@Column(name = "ISCELLPHONEVERIFIED")
	public Integer getIsCellphoneVerified() {
		return isCellphoneVerified;
	}

	public void setIsCellphoneVerified(Integer isCellphoneVerified) {
		this.isCellphoneVerified = isCellphoneVerified;
	}

	public ComplatOutsideuser(Integer iid, String uuid, String loginName, String pwd, String name, Integer age,
			String sex, Integer enable, String degree, String pinyin, Integer papersType, String papersNumber,
			String description, String mobile, String phone, String fax, String email, String qq, String msn,
			String address, String post, String workUnit, String headShip, Date birthDate, Date loginTime,
			String loginIp, String regIp, Date createTime, String regSite, Integer isAuth, Integer authState,
			String headPic, String bodyPic, Integer isUpload, String compTel, String rejectReason, String headRenamePic,
			String bodyRenamePic, String residenceId, String presidenceId, String gpresidenceId, String residenceDetail,
			String livingAreaId, String plivingAreaId, String gplivingAreaId, String livingAreaDetail, Date modifyTime,
			Integer synState, Integer operSign, Integer isCellphoneVerified) {
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
		this.residenceId = residenceId;
		this.presidenceId = presidenceId;
		this.gpresidenceId = gpresidenceId;
		this.residenceDetail = residenceDetail;
		this.livingAreaId = livingAreaId;
		this.plivingAreaId = plivingAreaId;
		this.gplivingAreaId = gplivingAreaId;
		this.livingAreaDetail = livingAreaDetail;
		this.modifyTime = modifyTime;
		this.synState = synState;
		this.operSign = operSign;
		this.isCellphoneVerified = isCellphoneVerified;
	}

	public ComplatOutsideuser() {
	}
}
