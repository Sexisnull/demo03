package com.gsww.uids.manager.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.NationalityEnum;
import com.gsww.common.entity.PO;
import com.gsww.common.enums.AccountTypeEnum;

/**
 * 账号信息
 * 
 * @author sunbw
 *
 */
@Entity
@Table(name = "UIDS_ACCOUNT_MERGE_TEMP")
public class AccountMergeTemp extends PO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6603961599635910974L;
	
	/**
	 * 账号状态：正常
	 */
	public static final int NORMAL_STATUS = 1;
	
	/**
	 * 账号状态：冻结
	 */
	public static final int FROZEN_STATUS = 2;
	
	@Column(name = "ID", length = 128)
	private String id;
	
	/**
	 * 账号类型
	 */
	@Column(name = "ACCOUNT_TYPE", columnDefinition = "INT default 1")
	private int type = AccountTypeEnum.GOVERNMENT.getNumber();
	
	/**
	 * 绑定的用户：type!=CORPORATE_TYPE之外有效，即法人账号不绑定用户，其它类型的账号都可以绑定用户
	 */
	@Column(name = "USER_ID",length = 128)
	private String userId;
	
	/**
	 * 来源于哪个应用
	 */
	@Column(name = "APP_ID",length = 128)
	private String appId;
	
	/**
	 * 应用名称
	 */
	@Column(name = "APP_NAME",length = 128)
	private String appName;
	
	/**
	 * 账号名
	 */
	@Column(name = "NAME", nullable = false, length = 128)
	private String name;
	
	/**
	 * 昵称
	 */
	@Column(name = "NICKNAME", nullable = false, length = 128)
	private String nickname;
	
	/**
	 * 密码
	 */
	@Column(name = "PASSWORD", nullable = false, length = 128)
	private String password;
	
	/**
	 * 账号状态
	 */
	@Column(name = "STATUS", columnDefinition = "INT default 1")
	private int status = NORMAL_STATUS;
	
	/**
	 * 账号的详细信息
	 */
	@Column(name = "ACCOUNT_DETAIL_ID", length = 128)
	private String accountDetailId;
	
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
	 * role关系id
	 */
	@Column(name = "ROLE_RELATION_ID", columnDefinition = "INT default 0")
	private String RoleRelationId;
	
	/**
	 * 机构id
	 */
	@Column(name = "ORG_ID", length=32)
	private String orgId;
	
	/**
	 * 机构名称
	 */
	@Column(name = "ORG_NAME", length=64)
	private String orgName;

	/**
	 * 职务：政府类型账号有效
	 */
	@Column(name = "POST", length = 64)
	private String post;

	/**
	 * 法人类型：法人类型账号有效
	 */
	@Column(name = "CORPORATE_TYPE", length = 64)
	private String corporateType;
	
	/**
	 * 企业/机构名称：法人类型账号有效
	 */
	@Column(name = "CORPORATE_NAME", length = 64)
	private String corporateName;
	
	/**
	 * 企业工商注册号/统一社会信用代码：企业法人的法人类型账号有效
	 */
	@Column(name = "CREDIT_CODE", length = 64)
	private String creditCode;
	
	/**
	 * 组织机构代码：法人类型账号有效
	 */
	@Column(name = "ORG_CODE", length = 64)
	private String orgCode;
	
	/**
	 * 	法人代表/机构负责人姓名：法人类型账号有效
	 */
	@Column(name = "PERSON_NAME", length = 64)
	private String personName;
	
	/**
	 * 法人代表/机构负责人民族：法人类型账号有效
	 */
	@Column(name = "PERSON_NATIONALITY", columnDefinition = "INT default 0")
	private int nationality = NationalityEnum.HAN.getNumber();
	
	/**
	 * 法人代表/机构负责人身份证号：法人类型账号有效
	 */
	@Column(name = "PERSON_IDENTITY", length = 64)
	private String identity;
	
	/**
	 * 手机号：法人类型账号有效
	 */
	@Column(name = "PERSON_MOBILE", length = 64)
	private String mobile;
	
	/**
	 * 邮箱：法人类型账号有效
	 */
	@Column(name = "EMAIL", length = 128)
	private String email;
	
	/**
	 * 办公电话：法人类型账号有效
	 */
	@Column(name = "WORK_PHONE", length = 32)
	private String workPhone;
	
	/**
	 * 经营范围：法人类型账号有效
	 */
	@Column(name = "BUSINESS_SCOPE", length = 128)
	private String businessScope;
	
	@Column(name = "TEMP_DELETE", length = 11, columnDefinition = "INT default 0")
	private int tempDelete = 0;
	
	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
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

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public int getNationality() {
		return nationality;
	}

	public void setNationality(int nationality) {
		this.nationality = nationality;
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

	public String getWorkPhone() {
		return workPhone;
	}

	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	
	
	public int getTempDelete() {
		return tempDelete;
	}

	public void setTempDelete(int tempDelete) {
		this.tempDelete = tempDelete;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getAccountDetailId() {
		return accountDetailId;
	}

	public void setAccountDetailId(String accountDetailId) {
		this.accountDetailId = accountDetailId;
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

	public String getRoleRelationId() {
		return RoleRelationId;
	}

	public void setRoleRelationId(String roleRelationId) {
		RoleRelationId = roleRelationId;
	}
	
}
