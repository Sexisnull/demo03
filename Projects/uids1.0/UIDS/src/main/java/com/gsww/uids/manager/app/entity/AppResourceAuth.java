package com.gsww.uids.manager.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gsww.common.entity.PO;
import com.gsww.uids.manager.org.entity.Organization;
import com.gsww.uids.manager.org.entity.OrganizationGroup;
import com.gsww.uids.manager.role.entity.Role;

/**
 * 资源授权
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_RES_AUTH")
public class AppResourceAuth extends PO {

	private static final long serialVersionUID = -6046696437303469733L;
	
	/**
	 * 机构授权类型类型：只对指定的某个机构的角色授权
	 */
	public static final String ROLE_GRANT = "roleGrant";
	
	/**
	 * 机构授权类型类型：分组授权
	 */
	public static final String GROUP_GRANT = "groupGrant";

	/**
	 * 机构授权类型：当type = ROLE_GRANT时，表示对org机构的role角色授权；
	 * 当type = groupGrant时，按照机构的分组情况授权
	 */
	@Column(name = "TYPE_", nullable = false, length = 16)
	private String type;
	
	/**
	 * 机构
	 */
	@ManyToOne
	@JoinColumn(name = "ORG_ID", nullable = true)
	private Organization org;
	
	/**
	 * 机构分组
	 */
	@ManyToOne
	@JoinColumn(name = "ORG_GROUP_ID", nullable = true)
	private OrganizationGroup orgGroupId;
	
	/**
	 * 角色：如果为null，表示所有角色
	 */
	@ManyToOne
	@JoinColumn(name = "ROLE_ID", nullable = true)
	private Role role;
	
	/**
	 * 资源
	 */
	@ManyToOne
	@JoinColumn(name = "RESOURCE_ID", nullable = false)
	private AppResource resource;
	
	/**
	 * 删除标识
	 * @return
	 */
	@Column(name = "IS_DELETE", nullable = false, length = 1)
	private int isDelete = 0;
	
	/**
	 * 授权时间
	 * @return
	 */
	@Column(name = "CREATE_TIME", nullable = true, length = 23)
	private String createTime;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Organization getOrg() {
		return org;
	}

	public void setOrg(Organization org) {
		this.org = org;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public AppResource getResource() {
		return resource;
	}

	public void setResource(AppResource resource) {
		this.resource = resource;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public OrganizationGroup getOrgGroup() {
		return orgGroupId;
	}

	public void setOrgGroup(OrganizationGroup orgGroupId) {
		this.orgGroupId = orgGroupId;
	}
	
	public String getSourceName() {
		return resource == null ? "" : resource.getName();
	}
	
	public String getSourceId() {
		return resource == null ? "" : resource.getUuid();
	}
	
	public String getOrgName() {
		return org == null ? "" : org.getShortName();
	}
	
	public String getOrgId() {
		return org == null ? "" : org.getUuid();
	}
	
	public String getOrgGroupName() {
		return orgGroupId == null ? "" : orgGroupId.getName();
	}	
}
