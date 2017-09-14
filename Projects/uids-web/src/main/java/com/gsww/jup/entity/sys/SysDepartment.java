package com.gsww.jup.entity.sys;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "SYS_DEPARTMENT")
public class SysDepartment{
	private static final long serialVersionUID = -5695376774730539314L;
	// Fields

	private String deptId;
	private String deptName;
	private String deptCode;
	private String parentDeptCode;
	private String parentDeptName;
	private String deptState;
	private Integer deptSeq;
	private String createTime;
	private String xzqh;

	// Constructors

	/** default constructor */
	public SysDepartment() {
	}

	/** minimal constructor */
	public SysDepartment(String deptName, String deptState, String createTime, String xzqh) {
		this.deptName = deptName;
		this.deptState = deptState;
		this.createTime = createTime;
		this.xzqh = xzqh;
	}

	/** full constructor */
	public SysDepartment(String deptName, String deptCode,
			String parentDeptCode, String deptState, Integer deptSeq,
			String createTime, String userAcctId, String xzqh) {
		this.deptName = deptName;
		this.deptCode = deptCode;
		this.parentDeptCode = parentDeptCode;
		this.deptState = deptState;
		this.deptSeq = deptSeq;
		this.createTime = createTime;
		this.xzqh = xzqh;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "DEPT_ID", unique = true, nullable = false)
	public String getDeptId() {
		return this.deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getDeptCode() {
		return this.deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getParentDeptCode() {
		return this.parentDeptCode;
	}

	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}

	public String getDeptState() {
		return this.deptState;
	}

	public void setDeptState(String deptState) {
		this.deptState = deptState;
	}

	public Integer getDeptSeq() {
		return this.deptSeq;
	}

	public void setDeptSeq(Integer deptSeq) {
		this.deptSeq = deptSeq;
	}

	public String getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getXzqh() {
		return xzqh;
	}

	public void setXzqh(String xzqh) {
		this.xzqh = xzqh;
	}

	private SysDepartment parentDepartment;
	private SysAccount sysAccount;
	@Transient
	public SysDepartment getParentDepartment() {
		return parentDepartment;
	}
	public void setParentDepartment(SysDepartment parentDepartment) {
		this.parentDepartment = parentDepartment;
	}
	
	@Transient
	public SysAccount getSysAccount() {
		return sysAccount;
	}
	public void setSysAccount(SysAccount sysAccount) {
		this.sysAccount = sysAccount;
	}
	@Transient
	public String getParentDeptName() {
		return parentDeptName;
	}

	public void setParentDeptName(String parentDeptName) {
		this.parentDeptName = parentDeptName;
	}
	
}
