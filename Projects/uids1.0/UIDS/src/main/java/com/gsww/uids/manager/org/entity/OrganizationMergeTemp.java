package com.gsww.uids.manager.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 机构信息表
 * 
 * @author sunbw
 *
 */
@Entity
@Table(name = "UIDS_ORGANIZATION_MERGE_TEMP")
public class OrganizationMergeTemp extends PO {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1683188050540026680L;

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
	 * 相同id是需要合并的数据
	 */
	@Column(name = "ID",length = 32)
	private String id;
	
	
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
	@Column(name = "SQUENCE",length = 32, nullable = false)
	private int sequence = 0;

	/**
	 * 区域Id
	 */
	@Column(name = "AREA_ID",length = 32, nullable = false)
	private String areaId;
	
	/**
	 * 区域民称
	 */
	@Column(name = "AREA_NAME",length = 128, nullable = false)
	private String areaName;

	/**
	 * 机构后缀：即拼音缩写
	 */
	@Column(name = "SUFFIX", unique = true,length = 64)
	private String suffix;
	
	/**
	 * 标准9位机构编码
	 */
	@Column(name = "CODE", unique = true, length = 9)
	private String code;
	
	/**
	 * 父机构：这是按固定的树状划分
	 */
	@Column(name = "PARENT_ID", nullable = true,length = 32)
	private String parentId;
	
	/**
	 * 父机构名称
	 */
	@Column(name = "PARENT_Name", nullable = true,length = 128)
	private String parentName;
	
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
	@Column(name="IS_PARENT",length = 11)
	private int parentIs = 0;
	
	/**
	 * 逻辑删除
	 */
	@Column(name="IS_DELETE",length = 11)
	private int del = 0;

	/**
	 * 所属分组
	 */
	@Column(name = "UIDS_ORGANIZATION_GROUP_ID",length = 32 )
	private String groupSetId;
	
	/**
	 * 所属分组
	 */
	@Column(name = "UIDS_ORGANIZATION_GROUP_Name",length = 128 )
	private String groupSetName;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


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

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
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

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
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

	public int getDel() {
		return del;
	}

	public void setDel(int del) {
		this.del = del;
	}

	public String getGroupSetId() {
		return groupSetId;
	}

	public void setGroupSetId(String groupSetId) {
		this.groupSetId = groupSetId;
	}

	public String getGroupSetName() {
		return groupSetName;
	}

	public void setGroupSetName(String groupSetName) {
		this.groupSetName = groupSetName;
	}

}
