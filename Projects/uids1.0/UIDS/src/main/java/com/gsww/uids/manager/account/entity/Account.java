package com.gsww.uids.manager.account.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.gsww.common.entity.PO;
import com.gsww.common.enums.AccountTypeEnum;
import com.gsww.uids.manager.app.entity.Application;
import com.gsww.uids.manager.user.entity.User;

/**
 * 账号信息
 * 个人账号、公务账号绑定自然人用户，法人账号绑定法人用户
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_ACCOUNT_INFO")
public class Account extends PO {

	private static final long serialVersionUID = 88509615711334023L;

	/**
	 * 账号状态：正常
	 */
	public static final int NORMAL_STATUS = 1;
	
	/**
	 * 账号状态：冻结
	 */
	public static final int FROZEN_STATUS = 2;
	
	/**
	 * 账号类型
	 */
	@Column(name = "ACCOUNT_TYPE", columnDefinition = "INT default 1")
	private int type = AccountTypeEnum.GOVERNMENT.getNumber();
	
	/**
	 * 绑定的用户
	 */
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	/**
	 * 来源于哪个应用
	 */
	@ManyToOne
	@JoinColumn(name = "APP_ID", nullable = false)
	private Application app;
	
	/**
	 * 账号名
	 */
	@Column(name = "NAME", nullable = false, length = 128)
	private String name;
	
	/**
	 * 昵称
	 */
	@Column(name = "NICKNAME", nullable = false, length = 128)
	private String nickname;
	
	/**
	 * 密码
	 */
	@Column(name = "PASSWORD", nullable = false, length = 128)
	private String password;
	
	/**
	 * 账号状态
	 */
	@Column(name = "STATUS", columnDefinition = "INT default 1")
	private int status = NORMAL_STATUS;
	
	/**
	 * 手机号
	 */
	@Column(name = "PHONE_NUMBER", nullable = false, length = 11)
	private String phone;
	
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME", nullable = false, length = 23)
	private String createTime;
	
	/**
	 * 是否删除：是-1，否-0
	 */
	@Column(name = "IS_DELETE", columnDefinition = "INT default 0")
	private int del = 0;
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
	
	////////////////////////////////////////////////////////////////////////////////////
	// VO

	public String getUserName() {
		if ( user != null ) {
			return user.getName();
		} else {
			return null;
		}
	}
	
	public String getUserIdentity() {
		if ( user != null ) {
			return user.getIdentity();
		} else {
			return null;
		}
	}
	
	public String getCorporateType() {
		if ( user != null ) {
			return user.getCorporateType();
		} else {
			return null;
		}
	}
	
	public String getCorporateName() {
		if ( user != null ) {
			return user.getCorporateName();
		} else {
			return null;
		}
	}
	
	/**
	 * 应用名称
	 * @return
	 */
	public String getAppName() {
		return app.getName();
	}
	
	/**
	 * 是否绑定用户
	 * @return
	 */
	public boolean hasBindUser() {
		return (user != null);
	}
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
