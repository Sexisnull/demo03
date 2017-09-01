package com.gsww.uids.manager.role.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 角色
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_ROLE_INFO")
public class Role extends PO {
	
	private static final long serialVersionUID = -1219873877466676476L;
	
	/**
	 * 如果MARK为ADMIN_ROLE角色，则认为是系统管理员角色，拥有此角色的账号或用户，拥有后台管理权限
	 */
	public static final String ADMIN_ROLE = "admin";
	
	/**
	 * 角色名
	 */
	@Column(name = "NAME", unique = true, nullable = false, length = 128)
	private String name;
	
	/**
	 * 角色标识
	 */
	@Column(name = "MARK_", length = 32)
	private String mark;
	
	/**
	 * 角色描述
	 */
	@Column(name = "DESC_", length = 256)
	private String desc;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", length = 23)
	private String createTime;
	
	/**
	 * 是否逻辑删除
	 */
	@Column(name = "IS_DELETE",length=1)
	private int isDelete = 0; //逻辑删除（0-未删除，1-删除）
	
	/**
	 * 有效状态
	 * 默认为有效状态
	 */
	@Column(name = "VALID_STATUS",length=1)
	private int validStatus = 0; //0表示有效，1表示无效
	
	public int getValidStatus() {
		return validStatus;
	}

	public void setValidStatus(int validStatus) {
		this.validStatus = validStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}
	
	public boolean isAdminRole() {
		if ( ADMIN_ROLE.equalsIgnoreCase(this.mark) ) {
			return true;
		} else {
			return false;
		}
	}
}
