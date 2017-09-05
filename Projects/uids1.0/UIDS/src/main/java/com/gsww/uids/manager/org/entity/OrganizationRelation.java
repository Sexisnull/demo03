package com.gsww.uids.manager.org.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 按自定义标准划分的上级机构
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_ORGANIZATION_RELATION") 
public class OrganizationRelation extends PO {

	private static final long serialVersionUID = 6506302608571214544L;

	/**
	 * 机构：organization + classify 是唯一的
	 */
	@ManyToOne
	@JoinColumn(name = "ORG_ID", nullable = false)
	private Organization organization;
	
	/**
	 * 机构划分方式
	 */
	@ManyToOne
	@JoinColumn(name = "CLASSIFY_ID", nullable = false)
	private OrganizationRelationType classify;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", length = 23)
	private String createTime;
	
	/**
	 * 逻辑删除
	 */
	@Column(name="IS_DELETE")
	private int del = 0;
	
	/**
	 * 上级机构
	 */
	@ManyToOne
	@JoinColumn(name = "PARENT_ORG_ID", nullable = false)
	private Organization parentOrganization;

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public OrganizationRelationType getClassify() {
		return classify;
	}

	public void setClassify(OrganizationRelationType classify) {
		this.classify = classify;
	}

	public Organization getParentOrganization() {
		return parentOrganization;
	}

	public void setParentOrganization(Organization parentOrganization) {
		this.parentOrganization = parentOrganization;
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
