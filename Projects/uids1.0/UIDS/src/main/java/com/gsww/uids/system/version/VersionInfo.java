package com.gsww.uids.system.version;

/**
 * 版本信息
 * 
 * @author chenyc
 *
 */
public class VersionInfo {

	/**
	 * 版本序号
	 */
	private int order = 0;
	
	/**
	 * 版本号码
	 */
	private String version = "V 1.0";
	
	/**
	 * 版本说明
	 */
	private String desc = "";
	
	/**
	 * 发布日期
	 */
	private String releaseTime = "";

	public VersionInfo() {
		
	}
	
	public VersionInfo(int order, String version, String desc, String releaseTime) {
		this.order = order;
		this.version = version;
		this.desc = desc;
		this.releaseTime = releaseTime;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
}
