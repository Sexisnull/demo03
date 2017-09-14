package com.gsww.uids.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-entity</p>
 * <p>创建时间 : 2017-09-07 上午09:46:34</p>
 * <p>类描述 : 法人账号管理实体类        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">yaoxi</a>
 */
@Entity
@Table(name = "COMPLAT_CORPORATION")
public class ComplatCorporation implements java.io.Serializable{
	
	private static final long serialVersionUID = -107463658789987756L;
	
	
	private Integer iid;//法人ID
	private String uuid; 
	private Integer type;//法人类型   1-企业法人；2-非企业法人
	private Integer enable;//是否启用   0-禁用；1-启用
	private String loginName;//登录名
	private String pwd;//密码
	private String mobile;//移动电话
	private String phone;//固定电话
	private String name;//法人名称（企业名称、机构名称）
	private String regNumber;//企业工商注册号码
	private String realName;//负责人姓名
	private String sex;//企业负责人或机构负责人性别  1-男;0-女
	private String post;//邮政编码
	private String address;//联系地址（注册地址）
	private String scope;//经营范围
	private String regorgName;//登记机构
	private String serialNumber;//工商内部序号
	private String headShip;//用户职务
	private String fax;//传真
	private String email;//email
	private String cardNumber;//身份证号码
	private String orgNumber;//组织机构代码号
	private Date createTime;//注册时间
	private String nation;//民族
	private String pinyin;//法人名称（企业名称、机构名称）全拼
	private Integer isAuth;//实名认证标志  0-否；1-是
	private Integer authState;//实名认证标志  0-未审核；1-审核通过  ；2-审核未通过
	private String cardPic;//身份证上传文件名称
	private String orgPic;//组织机构代码证上传文件名称
	private String licencePic;//企业法人营业执照上传名称
	private Integer isUpload;//是否上传实名认证资料  0-否;1-是
	private String rejectReason;//审核未通过原因
	private String cardreNamePic;//身份证文件新名称
	private String licencereNamePic;//企业法人营业执照上传名称
	private String orgreNamePic;//组织机构代码证新文件名称
	private Integer operSign;//1-新增；2-修改；3-假删
	private Integer synState;//0-未备份;2-备份失败；3-备份成功
	private Integer declarationIid;
	private String residenceId;//户籍所在地区
	private String presidenceId;//户籍所在地市
	private String gpresidenceId;//户籍所在地省
	private String residenceDetail;//户籍所在地详情
	private String livingAreaId;//常住地区
	private String plivingAreaId;//常住地市
	private String gplivingAreaId;//常住地省
	private String livingAreaDetail;//常住地详址
	private Date loginTime;//最后一次登陆时间
	private String loginIp;//最后一次登录IP
	
	
	public ComplatCorporation() {
		super();
	}
	
	
	public ComplatCorporation(Integer iid, String uuid, Integer type,
			Integer enable, String loginName, String pwd, String mobile,
			String phone, String name, String regNumber, String realName,
			String sex, String post, String address, String scope,
			String regorgName, String serialNumber, String headShip,
			String fax, String email, String cardNumber, String orgNumber,
			Date createTime, String nation, String pinyin, Integer isAuth,
			Integer authState, String cardPic, String orgPic,
			String licencePic, Integer isUpload, String rejectReason,
			String cardreNamePic, String licencereNamePic, String orgreNamePic) {
		super();
		this.iid = iid;
		this.uuid = uuid;
		this.type = type;
		this.enable = enable;
		this.loginName = loginName;
		this.pwd = pwd;
		this.mobile = mobile;
		this.phone = phone;
		this.name = name;
		this.regNumber = regNumber;
		this.realName = realName;
		this.sex = sex;
		this.post = post;
		this.address = address;
		this.scope = scope;
		this.regorgName = regorgName;
		this.serialNumber = serialNumber;
		this.headShip = headShip;
		this.fax = fax;
		this.email = email;
		this.cardNumber = cardNumber;
		this.orgNumber = orgNumber;
		this.createTime = createTime;
		this.nation = nation;
		this.pinyin = pinyin;
		this.isAuth = isAuth;
		this.authState = authState;
		this.cardPic = cardPic;
		this.orgPic = orgPic;
		this.licencePic = licencePic;
		this.isUpload = isUpload;
		this.rejectReason = rejectReason;
		this.cardreNamePic = cardreNamePic;
		this.licencereNamePic = licencereNamePic;
		this.orgreNamePic = orgreNamePic;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "IID")
	public Integer getIid() {
		return iid;
	}
	public void setIid(Integer iid) {
		this.iid = iid;
	}
	
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "UUID")
	public String getUuid() {
		return uuid;
	}


	public void setUuid(String uuid) {
		this.uuid = uuid;
	}


	@Column(name = "TYPE")
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	@Column(name = "ENABLE")
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
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
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "REGNUMBER")
	public String getRegNumber() {
		return regNumber;
	}
	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}
	
	@Column(name = "REALNAME")
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	@Column(name = "SEX")
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Column(name = "POST")
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
	@Column(name = "ADDRESS")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	@Column(name = "SCOPE")
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Column(name = "REGORGNAME")
	public String getRegorgName() {
		return regorgName;
	}
	public void setRegorgName(String regorgName) {
		this.regorgName = regorgName;
	}
	
	@Column(name = "SERIALNUMBER")
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	
	@Column(name = "HEADSHIP")
	public String getHeadShip() {
		return headShip;
	}
	public void setHeadShip(String headShip) {
		this.headShip = headShip;
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
	
	@Column(name = "CARDNUMBER")
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
	@Column(name = "ORGNUMBER")
	public String getOrgNumber() {
		return orgNumber;
	}
	public void setOrgNumber(String orgNumber) {
		this.orgNumber = orgNumber;
	}
	
	@Column(name = "CREATETIME")
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Column(name = "NATION")
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	
	@Column(name = "PINYIN")
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
	@Column(name = "ISAUTH")
	public Integer getisAuth() {
		return isAuth;
	}
	public void setisAuth(Integer isAuth) {
		this.isAuth = isAuth;
	}
	
	@Column(name = "AUTHSTATE")
	public Integer getauthState() {
		return authState;
	}
	public void setauthState(Integer authState) {
		this.authState = authState;
	}
	
	@Column(name = "CARDPIC")
	public String getcardPic() {
		return cardPic;
	}
	public void setcardPic(String cardPic) {
		this.cardPic = cardPic;
	}
	
	@Column(name = "ORGPIC")
	public String getorgPic() {
		return orgPic;
	}
	public void setorgPic(String orgPic) {
		this.orgPic = orgPic;
	}
	
	@Column(name = "LICENCEPIC")
	public String getlicencePic() {
		return licencePic;
	}
	public void setlicencePic(String licencePic) {
		this.licencePic = licencePic;
	}
	
	@Column(name = "ISUPLOAD")
	public Integer getisUpload() {
		return isUpload;
	}
	public void setisUpload(Integer isUpload) {
		this.isUpload = isUpload;
	}
	
	@Column(name = "REJECTREASON")
	public String getrejectReason() {
		return rejectReason;
	}
	public void setrejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}
	
	@Column(name = "CARDRENAMEPIC")
	public String getcardreNamePic() {
		return cardreNamePic;
	}
	public void setcardreNamePic(String cardreNamePic) {
		this.cardreNamePic = cardreNamePic;
	}
	
	@Column(name = "LICENCERENAMEPIC")
	public String getlicencereNamePic() {
		return licencereNamePic;
	}
	public void setlicencereNamePic(String licencereNamePic) {
		this.licencereNamePic = licencereNamePic;
	}
	
	@Column(name = "ORGRENAMEPIC")
	public String getorgreNamePic() {
		return orgreNamePic;
	}
	public void setorgreNamePic(String orgreNamePic) {
		this.orgreNamePic = orgreNamePic;
	}

	@Column(name = "OPERSIGN")
	public Integer getOperSign() {
		return operSign;
	}


	public void setOperSign(Integer operSign) {
		this.operSign = operSign;
	}

	@Column(name = "SYNSTATE")
	public Integer getSynState() {
		return synState;
	}


	public void setSynState(Integer synState) {
		this.synState = synState;
	}

	@Column(name = "DECLARATIONIID")
	public Integer getDeclarationIid() {
		return declarationIid;
	}


	public void setDeclarationIid(Integer declarationIid) {
		this.declarationIid = declarationIid;
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
	

}
