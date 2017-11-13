package com.gsww.uids.entity;


/**
 * 
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-entity</p>
 * <p>创建时间 : 2017-09-03 上午15:46:34</p>
 * <p>类描述 : 在线用户        </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">yaoxi</a>
 */
public class CountUser {
	
	private String userName;//用户名称
	private String groupName;//机构名称
	private String IP;
	private String operateTime;//访问时间
	
	
	
	public CountUser() {
		super();
	}



	public CountUser(String userName, String groupName, String IP,
			String operateTime) {
		super();
		this.userName = userName;
		this.groupName = groupName;
		this.IP = IP;
		this.operateTime = operateTime;
	}


	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public String getIP() {
		return IP;
	}


	public void setIP(String iP) {
		IP = iP;
	}


	public String getOperateTime() {
		return operateTime;
	}


	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}
	
	
	
	
	
}
