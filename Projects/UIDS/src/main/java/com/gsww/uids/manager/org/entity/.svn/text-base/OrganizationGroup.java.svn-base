package com.gsww.uids.manager.org.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 机构分组（分类）
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_ORGANIZATION_GROUP")
public class OrganizationGroup extends PO {
	
	private static final long serialVersionUID = -7674251806156541334L;

	/**
	 * 名称
	 */
	@Column(name = "NAME", unique = true, length = 128)
	private String name;
	
	/**
	 * 分组标识
	 */
	@Column(name = "MARK", unique = true, length = 9)
	private String mark;
	
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
	 * 逻辑删除
	 */
	@Column(name="IS_DELETE")
	private int del= 0;
	
	/**
	 * 机构
	 */
	@ManyToMany(mappedBy = "groupSet", fetch = FetchType.LAZY)
	private Set<Organization> orgSet = new HashSet<Organization>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Set<Organization> getOrgSet() {
		return orgSet;
	}

	public void setOrgSet(Set<Organization> orgSet) {
		this.orgSet = orgSet;
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
}
