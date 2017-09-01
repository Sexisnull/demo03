package com.gsww.uids.manager.app.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gsww.common.entity.PO;

/**
 * 资源
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_APP_RESOURCE")
public class AppResource extends PO {

	private static final long serialVersionUID = 2299676452482059413L;

	/**
	 * 应用
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "APP_ID", nullable = false)
	private Application app;
	
	/**
	 * 资源名称
	 */
	@Column(name = "NAME", nullable = false, length = 128)
	private String name;

	/**
	 * 资源编码
	 */
	@Column(name = "CODE", unique = true, nullable = false, length = 256)
	private String code;

	/**
	 * URL
	 */
	@Column(name = "URL", nullable = false, length = 256)
	private String url;

	/**
	 * 资源图标：选择的系统图标
	 */
	@ManyToOne
	@JoinColumn(name = "SYS_ICON_ID",nullable = true)
	private AppIcon sysIcon;

	/**
	 * 资源图标：用户选择的图标文件
	 */
	@Column(name = "ICON_PATH", length = 256)
	private String iconPath;

	/**
	 * 资源描述
	 */
	@Column(name = "DESC_", length = 256)
	private String desc;

	/**
	 * 逻辑删除标记
	 */
	@Column(name = "IS_DELETE", length = 1)
	private int isDelete = 0;

	/**
	 * 资源新建时间
	 * 
	 * @return
	 */
	@Column(name = "CREATE_TIME", length = 23)
	private String createTime;

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

	public Application getApp() {
		return app;
	}

	public void setApp(Application app) {
		this.app = app;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public AppIcon getSysIcon() {
		return sysIcon;
	}

	public void setSysIcon(AppIcon sysIcon) {
		this.sysIcon = sysIcon;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
