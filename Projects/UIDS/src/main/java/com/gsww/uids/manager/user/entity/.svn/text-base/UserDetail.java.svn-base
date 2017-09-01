package com.gsww.uids.manager.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;


/**
 * 用户详细信息
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_USER_DETAIL")
public class UserDetail extends PO {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 6804083139924618757L;
	
	/**
	 * 年龄
	 */
	@Column(name = "AGE", length = 3)
	private String age;
	
	/**
	 * 学历
	 */
	@Column(name = "DEGREE", columnDefinition = "INT default 0")
	private int degree = 0;
	
	/**
	 * QQ
	 */
	@Column(name = "QQ", length = 12)
	private String qq;
	
	/**
	 * MSN
	 */
	@Column(name = "MSN", length = 64)
	private String msn;
	
	/**
	 * 固定电话
	 */
	@Column(name = "HOME_TEL", length = 14)
	private String homeTel;
	
	/**
	 * （自然人或法人）办公电话
	 */
	@Column(name = "COMPANY_TEL", length = 14)
	private String companyTel;
	
	/**
	 * 传真
	 */
	@Column(name = "FAX", length = 14)
	private String fax;
	
	/**
	 * 联系地址
	 */
	@Column(name = "CONTACT_ADDRESS", length = 64)
	private String contactAddress;
	
	/**
	 * 邮编
	 */
	@Column(name = "POST_CODE", length = 6)
	private String postCode;
	
	/**
	 * 经营范围：法人类型账号有效
	 */
	@Column(name = "BUSINESS_SCOPE", length = 256)
	private String businessScope;

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public int getDegree() {
		return degree;
	}

	public void setDegree(int degree) {
		this.degree = degree;
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

	public String getHomeTel() {
		return homeTel;
	}

	public void setHomeTel(String homeTel) {
		this.homeTel = homeTel;
	}

	public String getCompanyTel() {
		return companyTel;
	}

	public void setCompanyTel(String companyTel) {
		this.companyTel = companyTel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
}