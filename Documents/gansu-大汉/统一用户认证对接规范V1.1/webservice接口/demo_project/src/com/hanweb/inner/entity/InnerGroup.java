package com.hanweb.inner.entity;
/**
 * 后台机构
 * @author chen
 *
 */
public class InnerGroup {
	/**
	 * ID
	 */
	private String id = "";
	/**
	 * 机构编码
	 */
	private String groupCode = "";
	/**
	 * 机构名称
	 */
	private String groupName = "";
	/**
	 * 父机构ID
	 */
	private String parGroupId = "";
	
	/**
	 * 无参构造函数（一定要有不然JIS调用不到）
	 */
	public InnerGroup() {
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public String getParGroupId() {
		return parGroupId;
	}
	public void setParGroupId(String parGroupId) {
		this.parGroupId = parGroupId;
	}
	
	
}
