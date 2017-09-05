package com.gsww.uids.manager.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 系统提供的应用图标
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_APP_ICON")
public class AppIcon extends PO {

	private static final long serialVersionUID = 3126683265221445330L;
	
	/**
	 * 图标类型：通用图标
	 */
	public static final String COMMON_ICON = "common";
	
	/**
	 * 图标类型：应用图标
	 */
	public static final String APP_ICON = "application";
	
	/**
	 * 图标类型：资源图标
	 */
	public static final String RESOURCE_ICON = "resource";
	
	/**
	 * 图标类型：通用图标-COMMON_ICON，应用图标-APP_ICON，资源图标-RESOURCE_ICON
	 */
	@Column(name = "TYPE", length = 32, nullable = false)
	private String type;
	
	/**
	 * 图标名称
	 */
	@Column(name = "NAME", unique = true, length = 128)
	private String name;
	
	/**
	 * 图标文件路径
	 */
	@Column(name = "PATH", length = 256)
	private String path;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
