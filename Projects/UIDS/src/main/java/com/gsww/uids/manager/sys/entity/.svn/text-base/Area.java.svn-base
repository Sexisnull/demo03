package com.gsww.uids.manager.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 行政区域信息
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "SYS_AREA_INFO")
public class Area extends PO {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 7045870575636945121L;
	
	
	/** 
	 * 区域名称
	 */
	@Column(name = "NAME", unique = true, length = 128)
	private String name;

	/**
	 * 区域编码
	 */
	@Column(name = "CODE", unique = true, length = 32)
	private String code;
	
	/**
	 * 区域类型：level从1-5级
	 */
	@Column(name = "LEVEL", nullable = true, length = 32)
	private String level ;
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	/**
	 * 是否逻辑删除
	 */
	@Column(name = "IS_DELETE",length=1)
	private int isDelete = 0; //逻辑删除（0-未删除，1-删除）
	
	/**
	 * 是否为父机构
	 */
	@Column(name="IS_PARENT")
	private int parentOrChild = 0;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", nullable = false, length = 32)
	private String createTime;
	

	/**
	 * 上级区域
	 */
	@ManyToOne
	@JoinColumn(name = "PARENT_ID", nullable = true)
	private Area parent;
	
	/**
	 * 有效状态
	 * 默认为有效状态
	 */
	@Column(name = "VALID_STATUS", columnDefinition = "INT default 1", length=1)
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Area getParent() {
		return parent;
	}

	public void setParent(Area parent) {
		this.parent = parent;
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
	
	public int getParentOrChild() {
		return parentOrChild;
	}

	public void setParentOrChild(int parentOrChild) {
		this.parentOrChild = parentOrChild;
	}
	
	/**
	 * 父级区域uuid
	 * @return
	 */
	public String getParentUuid() {
		return parent == null ? "" : parent.getUuid();
	}
	/**
	 * 父级区域name
	 * @return
	 */
	public String getParentName() {
		return parent == null ? "" : parent.getName();
	}
	
	public boolean isRoot() {
		return "0".equalsIgnoreCase(this.getUuid());
	}
	
	/**
	 * 将区域名称组合长一个全称
	 * 
	 * @return
	 */
	public String generateFullName() {
		
		String fullName = this.name;
		
		Area tempParent = this.parent;
		while ( tempParent != null && !tempParent.isRoot() ) {
			fullName = tempParent.getName() + fullName;
			tempParent = tempParent.getParent();
		}
		
		return fullName;
	}
}
