package com.gsww.uids.manager.org.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gsww.common.entity.PO;
import com.gsww.uids.manager.sys.entity.Area;

/**
 * 机构信息表
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_ORGANIZATION_INFO")
public class Organization extends PO {
	
	private static final long serialVersionUID = 7224866441110133758L;
	
	/** 
	 * 节点类型值：区域
	 */
	public static final String AREA_TYPE = "area";
	
	/** 
	 * 节点类型值：单位
	 */
	public static final String INSTITUTION_TYPE = "institution";
	
	/** 
	 * 节点类型值：部门或处室
	 */
	public static final String DEPARTMENT_TYPE = "department";
	
	/** 
	 * 节点类型值：部门或处室
	 */
	public static final String OTHER_TYPE = "other";
	
	/**
	 * 机构简称
	 */
	@Column(name = "SHORT_NAME", unique = true, length = 128)
	private String shortName;
	
	/**
	 * 机构全称
	 */
	@Column(name = "FULL_NAME", unique = true, length = 256)
	private String fullName;
	
	/**
	 * 节点类型：区域-AREA_TYPE；单位-INSTITUTION_TYPE；部门或处室-DEPARTMENT_TYPE
	 */
	@Column(name = "TYPE", length = 32, nullable = false)
	private String type = "0";
	
	/**
	 * 机构排序字段
	 */
	@Column(name = "SQUENCE", nullable = false)
	private int sequence = 0;

	/**
	 * 区域
	 */
	@ManyToOne
	@JoinColumn(name = "AREA_ID", nullable = false)
	private Area area;

	/**
	 * 机构后缀：即拼音缩写
	 */
	@Column(name = "SUFFIX", unique = true, length = 64)
	private String suffix;
	
	/**
	 * 树状机构编码
	 */
	@Column(name = "CODE", unique = true)
	private String code;
	
	/**
	 * 组织机构代码
	 */
	@Column(name = "ORG_CODE", length = 9)
	private String orgCode;
	
	/**
	 * 父机构：这是按固定的树状划分
	 */
	@ManyToOne
	@JoinColumn(name = "PARENT_ID", nullable = true)
	private Organization parent;
	
	/**
	 * 机构描述
	 */
	@Column(name = "DESC_", length = 256)
	private String desc;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", length = 23)
	private String createTime;
	
	/**
	 * 是否为父机构
	 */
	@Column(name="IS_PARENT")
	private int parentIs = 0;
	
	/**
	 * 逻辑删除
	 */
	@Column(name="IS_DELETE")
	private int del = 0;

	/**
	 * 所属分组
	 */
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "UIDS_ORGANIZATION_GROUP_LINK", joinColumns = { @JoinColumn(name = "ORG_ID") }, inverseJoinColumns = { @JoinColumn(name = "GROUP_ID") })
	private Set<OrganizationGroup> groupSet = new HashSet<OrganizationGroup>();

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
		
	public int getParentIs() {
		return parentIs;
	}

	public void setParentIs(int parentIs) {
		this.parentIs = parentIs;
	}
	
	public Set<OrganizationGroup> getGroupSet() {
		return groupSet;
	}

	public void setGroupSet(Set<OrganizationGroup> groupSet) {
		this.groupSet = groupSet;
	}
	
	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
	
	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}
		
	/**
	 * 父级机构uuid
	 * @return
	 */
	public String getParentUuid() {
		return parent == null ? "" : parent.getUuid();
	}
	
	/**
	 * 父级机构名称
	 * @return
	 */
	public String getParentName() {
		return parent == null ? "" : parent.getShortName();
	}
	
	/**
	 * 父机构的机构编码
	 * @return
	 */
	public String getParentCode() {
		return parent == null ? "" : parent.getCode();
	}
}
