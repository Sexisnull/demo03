package com.gsww.uids.manager.user.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.gsww.common.entity.AuthStatusEnum;
import com.gsww.common.entity.NationalityEnum;
import com.gsww.common.entity.PO;
import com.gsww.common.enums.UserTypeEnum;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.role.entity.RoleRelation;
import com.gsww.uids.manager.sys.entity.Area;

/**
 * 用户基本信息
 * 用户分为两类：自然人和企业法人
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_USER_INFO")
public class User extends PO {

	private static final long serialVersionUID = -1714434647371248308L;
	
	/**
	 * 法人类型：企业法人
	 */
	public static final String CORPORATE_TYPE = "corporate";
	
	/**
	 * 法人类型：非企业法人
	 */
	public static final String ORGANIZATION_TYPE = "organization";
	
	/**
	 * 用户类型
	 */
	@Column(name = "USER_TYPE", columnDefinition = "INT default 1")
	private int type = UserTypeEnum.NATURAL.getNumber();
	
	/**
	 * 法人类型：type = UserTypeEnum.CORPORATE.getNumber()时有效
	 */
	@Column(name = "CORPORATE_TYPE", length = 16)
	private String corporateType;
	
	/**
	 * （自然人或法人）姓名
	 */
	@Column(name = "NAME", nullable = false, length = 128)
	private String name;
	
	/**
	 * （自然人）性别：男-1，女-2
	 */
	@Column(name = "SEX", columnDefinition = "INT default 1")
	private int sex = 1;
	
	/**
	 * （自然人或法人）民族：从NationalityEnum中选择
	 */
	@Column(name = "NATIONALITY", columnDefinition = "INT default 0")
	private int nationality = NationalityEnum.HAN.getNumber();
	
	/**
	 * （自然人）出生地省市区（固定3级）
	 */
	@ManyToOne
	@JoinColumn(name = "BIRTH_AREA_ID")
	private Area birthArea;
	
	/**
	 * （自然人）出生地详细地址
	 */
	@Column(name = "BIRTH_ADDRESS", length = 64)
	private String birthAddress;
	
	/**
	 * （自然人）居住地省市区（固定3级）
	 */
	@ManyToOne
	@JoinColumn(name = "LIVE_AREA_ID")
	private Area liveArea;
	
	/**
	 * （自然人）居住地详细地址
	 */
	@Column(name = "LIVE_ADDRESS", length = 64)
	private String liveAddress;
	
	/**
	 * （自然人或法人）身份证号
	 */
	@Column(name = "IDENTITY", nullable = false, length = 18)
	private String identity;
	
	/**
	 * （自然人或法人）手机号
	 */
	@Column(name = "MOBILE", nullable = false, length = 14)
	private String mobile;
	
	/**
	 * （自然人或法人）邮箱
	 */
	@Column(name = "EMAIL", length = 128)
	private String email;

	/**
	 * （自然人或法人）用户状态：从RealNameAuthStatusEnum中选择
	 */
	@Column(name = "STATUS", columnDefinition = "INT default 1")
	private int status = AuthStatusEnum.UNAUDIT.getNumber();
	
	/**
	 * 用户详情
	 */
	@ManyToOne
	@JoinColumn(name = "USER_DETAIL_ID")
	private UserDetail detail;
	
	/**
	 * 企业/机构名称：法人用户有效
	 */
	@Column(name = "CORPORATE_NAME", length = 64)
	private String corporateName;
	
	/**
	 * 企业工商注册号/统一社会信用代码：企业法人用户有效
	 */
	@Column(name = "CREDIT_CODE", length = 18)
	private String creditCode;
	
	/**
	 * 组织机构代码：法人用户有效
	 */
	@Column(name = "ORG_CODE", length = 10)
	private String orgCode;
	
	/**
	 * 所属机构：政府用户有效
	 */
	@ManyToOne
	@JoinColumn(name = "ORG_ID")
	private Organization org;
	
	/**
	 * 职务：政府用户有效
	 */
	@Column(name = "POST", length = 64)
	private String post;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", nullable = false, length = 23)
	private String createTime;
	
	/**
	 * 是否删除：是-1，否-0
	 */
	@Column(name = "IS_DELETE", columnDefinition = "INT default 0")
	private int del = 0;
	
	/**
	 * 拥有的角色关系
	 */
	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER) 
	@Where(clause = "IS_DELETE = 0")
	@OrderBy(value = "createTime desc")
	private Set<RoleRelation> roleRelationSet = new HashSet<RoleRelation>();

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getNationality() {
		return nationality;
	}

	public void setNationality(int nationality) {
		this.nationality = nationality;
	}

	public Area getBirthArea() {
		return birthArea;
	}

	public void setBirthArea(Area birthArea) {
		this.birthArea = birthArea;
	}

	public String getBirthAddress() {
		return birthAddress;
	}

	public void setBirthAddress(String birthAddress) {
		this.birthAddress = birthAddress;
	}

	public Area getLiveArea() {
		return liveArea;
	}

	public void setLiveArea(Area liveArea) {
		this.liveArea = liveArea;
	}

	public String getLiveAddress() {
		return liveAddress;
	}

	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public UserDetail getDetail() {
		return detail;
	}

	public void setDetail(UserDetail detail) {
		this.detail = detail;
	}

	public String getCorporateType() {
		return corporateType;
	}

	public void setCorporateType(String corporateType) {
		this.corporateType = corporateType;
	}
	
	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getCreditCode() {
		return creditCode;
	}

	public void setCreditCode(String creditCode) {
		this.creditCode = creditCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public Set<RoleRelation> getRoleRelationSet() {
		return roleRelationSet;
	}

	public void setRoleRelationSet(Set<RoleRelation> roleRelationSet) {
		this.roleRelationSet = roleRelationSet;
	}
	
	/**
	 * 政府用户所属机构名
	 * 
	 * @return
	 */
	public String getOrgShortName() {
		if ( org != null ) {
			return org.getShortName();
		} else {
			return null;
		}
	}
}