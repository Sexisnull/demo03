package com.gsww.jup.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * 
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-entity</p>
 * <p>创建时间 : 2014-7-27 上午11:59:34</p>
 * <p>类描述 : 用户账号管理实体类        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangxj</a>
 */
@Entity
@Table(name = "SYS_ACCOUNT")
public class SysAccount implements java.io.Serializable{
	private static final long serialVersionUID = -5695376774730539314L;
	private String userAcctId;         //账号id
	private String userName;	 	  //人员姓名
	private String userSex;           //性别 1男 2女
	private String userBirthday;      //出生日期 1985-05-12
	private String userSchool;        //毕业学校
	private String userEducation;     //学历
	private String userPostion;       //职位
	private String userIdentityCode;  //身份证号
	private String userAddress;       //家庭住址
	private String userPostcode;      //邮编
	private String userTele;		  //联系电话
	private String userHomeTele;	  //住宅电话
	private String userEmail;		  //邮箱
	private String loginAccount;	  //登录账号
	private String loginPassword;     //登录密码
	private String userState;             //状态 0停用 1启用
	private String createTime;        //创建时间 2014-06-15 12:23:55
	private String opeAcctId;		  //操作主键
	private SysDepartment sysDepartment;
	
	public SysAccount() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SysAccount(String userAcctId, String userName, String userSex,
			String userBirthday, String userSchool, String userEducation,
			String userPostion, String userIdentityCode, String userAddress,
			String userPostcode, String userTele, String userHomeTele,
			String userEmail, String loginAccount,
			String loginPassword, String userState, String createTime,
			String opeAcctId, SysDepartment sysDepartment) {
		super();
		this.userAcctId = userAcctId;
		this.userName = userName;
		this.userSex = userSex;
		this.userBirthday = userBirthday;
		this.userSchool = userSchool;
		this.userEducation = userEducation;
		this.userPostion = userPostion;
		this.userIdentityCode = userIdentityCode;
		this.userAddress = userAddress;
		this.userPostcode = userPostcode;
		this.userTele = userTele;
		this.userHomeTele = userHomeTele;
		this.userEmail = userEmail;
		this.loginAccount = loginAccount;
		this.loginPassword = loginPassword;
		this.userState = userState;
		this.createTime = createTime;
		this.opeAcctId = opeAcctId;
		this.sysDepartment=sysDepartment;
	}

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "USER_ACCT_ID", unique = true, nullable = false)
	public String getUserAcctId() {
		return userAcctId;
	}

	public void setUserAcctId(String userAcctId) {
		this.userAcctId = userAcctId;
	}
	
	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	public String getOpeAcctId() {
		return opeAcctId;
	}

	public void setOpeAcctId(String opeAcctId) {
		this.opeAcctId = opeAcctId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	@Column(name = "USER_SEX")
	public String getUserSex() {
		return userSex;
	}
	public void setUserSex(String userSex) {
		this.userSex = userSex;
	}
	@Column(name = "USER_BIRTHDAY")
	public String getUserBirthday() {
		return userBirthday;
	}
	public void setUserBirthday(String userBirthday) {
		this.userBirthday = userBirthday;
	}
	@Column(name = "USER_SCHOOL")
	public String getUserSchool() {
		return userSchool;
	}
	public void setUserSchool(String userSchool) {
		this.userSchool = userSchool;
	}
	@Column(name = "USER_EDUCATION")
	public String getUserEducation() {
		return userEducation;
	}
	public void setUserEducation(String userEducation) {
		this.userEducation = userEducation;
	}
	@Column(name = "USER_POSITION")
	public String getUserPostion() {
		return userPostion;
	}
	public void setUserPostion(String userPostion) {
		this.userPostion = userPostion;
	}
	@Column(name = "USER_IDENTITY_CODE")
	public String getUserIdentityCode() {
		return userIdentityCode;
	}
	public void setUserIdentityCode(String userIdentityCode) {
		this.userIdentityCode = userIdentityCode;
	}
	@Column(name = "USER_ADDRESS")
	public String getUserAddress() {
		return userAddress;
	}
	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}
	@Column(name = "USER_POSTCODE")
	public String getUserPostcode() {
		return userPostcode;
	}
	public void setUserPostcode(String userPostcode) {
		this.userPostcode = userPostcode;
	}
	@Column(name = "USER_TELE")
	public String getUserTele() {
		return userTele;
	}
	public void setUserTele(String userTele) {
		this.userTele = userTele;
	}
	@Column(name = "USER_HOME_TELE")
	public String getUserHomeTele() {
		return userHomeTele;
	}
	public void setUserHomeTele(String userHomeTele) {
		this.userHomeTele = userHomeTele;
	}
	@Column(name = "USER_EMAIL")
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	
	@Column(name = "LOGIN_ACCOUNT")
	public String getLoginAccount() {
		return loginAccount;
	}
	public void setLoginAccount(String loginAccount) {
		this.loginAccount = loginAccount;
	}
	@Column(name = "LOGIN_PASSWORD")
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	@Column(name = "USER_STATE")
	public String getUserState() {
		return userState;
	}
	public void setUserState(String userState) {
		this.userState = userState;
	}
	@Column(name = "CREATE_TIME")
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
	@ManyToOne
	@JoinColumn(name = "deptId")  
	public SysDepartment getSysDepartment() {
		return sysDepartment;
	}

	public void setSysDepartment(SysDepartment sysDepartment) {
		this.sysDepartment = sysDepartment;
	}
	
	

}
