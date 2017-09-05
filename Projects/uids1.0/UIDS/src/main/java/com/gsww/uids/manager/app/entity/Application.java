package com.gsww.uids.manager.app.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

import com.gsww.common.entity.PO;
import com.gsww.uids.manager.org.entity.Organization;

/**
 * 应用的信息
 * 
 * @author taolm
 *
 */
@Entity
@Table(name = "UIDS_APP_INFO")
public class Application extends PO {

	private static final long serialVersionUID = -6924305304113884240L;

	/**
	 * 网络类型：外网
	 */
	public static final String PUBLIC_NET = "public";

	/**
	 * 网络类型：专网
	 */
	public static final String PRIVATE_NET = "private";

	/**
	 * 应用类型：系统应用
	 */
	public static final String SYSTEM_APP_TYPE = "system";

	/**
	 * 应用类型：非应用类型
	 */
	public static final String COMMON_APP_TYPE = "common";

	/**
	 * 应用名称
	 */
	@Column(name = "NAME", unique = true, length = 128)
	private String name;

	/**
	 * 应用类型
	 */
	@Column(name = "TYPE", nullable = false, length = 128)
	private String type = COMMON_APP_TYPE;

	/**
	 * 应用标识
	 */
	@Column(name = "MARK", unique = true, length = 128)
	private String mark;

	/**
	 * 所属机构
	 */
	@ManyToOne
	@JoinColumn(name = "ORGANIZATION_ID", nullable = false)
	private Organization organization;

	/**
	 * 应用图标：选择的系统图标
	 */
	@ManyToOne
	@JoinColumn(name = "SYS_ICON_ID")
	private AppIcon sysIcon;

	/**
	 * 应用图标：用户选择的图标文件
	 */
	@Column(name = "ICON_PATH", length = 256)
	private String iconPath;

	/**
	 * 网络类型：外网-PUBLIC_NET；专网-PRIVATE_NET
	 */
	@Column(name = "NET_TYPE", nullable = false, length = 16)
	private String netType = PUBLIC_NET;

	/**
	 * 应用描述
	 */
	@Column(name = "APP_DESC", length = 512)
	private String appDesc;

	/**
	 * Oauth中分配的clientId
	 */
	@Column(name = "CLIENT_ID", nullable = false, unique = true, length = 128)
	private String clientId;

	/**
	 * Oauth中的秘钥
	 */
	@Column(name = "CLIENT_KEY", nullable = false, length = 128)
	private String clientKey;

	/**
	 * 数据传送的加密方式
	 */
	@Column(name = "ENCRYPTION_TYPE", length = 16)
	private String enctyptionType;
	
	/**
	 * 数据传送的加密秘钥
	 */
	@Column(name = "ENCRYPTION_KEY", length = 16)
	private String encryptionKey;

	/**
	 * 数据传送的加密salt
	 */
	@Column(name = "ENCRYPTION_SALT", length = 16)
	private String encryptionSalt;

	/**
	 * 应用下账号的加密方式
	 */
	@Column(name = "ACCOUNT_ENCRYPT_TYPE", nullable = false, length = 16)
	private String accountEncryptType = "md5";
	
	/**
	 * 应用下账号的加密salt
	 */
	@Column(name = "ACCOUNT_ENCRYPT_SALT", length = 256)
	private String accountEncryptSalt;

	/**
	 * 应用下账号的加密轮数
	 */
	@Column(name = "ACCOUNT_ENCRYPT_ITERATIONS", columnDefinition = "INT default 1")
	private int accountEncryptIterations = 1;

	/**
	 * 单点登录地址（兼容大汉单点登录）
	 */
	@Column(name = "SINGLE_LOGIN_URL", length = 128)
	private String singleLoginUrl;

	/**
	 * 逻辑删除标识
	 */
	@Column(name = "IS_DELETE", length = 128)
	private int isDelete = 0;

	/**
	 * 是否统一注册
	 * 
	 * @return
	 */
	@Column(name = "IS_UNITE_REG", length = 1)
	private String isUniteReg = "1";

	/**
	 * 是否统一注册
	 * 
	 * @return
	 */
	@Column(name = "IS_UNITE_OFF", length = 1)
	private String isUniteOff = "1";

	/**
	 * 前台是否展示
	 * 
	 * @return
	 */
	@Column(name = "IS_SHOW", length = 1)
	private String isShow = "0";

	/**
	 * 添加时间
	 */
	@Column(name = "CREATE_TIME", length = 23)
	private String createTime;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "app")
	@Where(clause="IS_DELETE = 0 ")
	private Set<AppResource> setAppResource = new HashSet<AppResource>();

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getIsUniteReg() {
		return isUniteReg;
	}

	public void setIsUniteReg(String isUniteReg) {
		this.isUniteReg = isUniteReg;
	}

	public String getIsUniteOff() {
		return isUniteOff;
	}

	public void setIsUniteOff(String isUniteOff) {
		this.isUniteOff = isUniteOff;
	}

	public int getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(int isDelete) {
		this.isDelete = isDelete;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public String getEnctyptionType() {
		return enctyptionType;
	}

	public void setEnctyptionType(String enctyptionType) {
		this.enctyptionType = enctyptionType;
	}

	public String getEncryptionSalt() {
		return encryptionSalt;
	}

	public void setEncryptionSalt(String encryptionSalt) {
		this.encryptionSalt = encryptionSalt;
	}

	public String getAccountEncryptType() {
		return accountEncryptType;
	}

	public void setAccountEncryptType(String accountEncryptType) {
		this.accountEncryptType = accountEncryptType;
	}

	public int getAccountEncryptIterations() {
		return accountEncryptIterations;
	}

	public void setAccountEncryptIterations(int accountEncryptIterations) {
		this.accountEncryptIterations = accountEncryptIterations;
	}
	
	public String getSingleLoginUrl() {
		return singleLoginUrl;
	}

	public void setSingleLoginUrl(String singleLoginUrl) {
		this.singleLoginUrl = singleLoginUrl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
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

	public String getNetType() {
		return netType;
	}

	public void setNetType(String netType) {
		this.netType = netType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getAppDesc() {
		return appDesc;
	}

	public void setAppDesc(String appDesc) {
		this.appDesc = appDesc;
	}
	
	public Set<AppResource> getSetAppResource() {
		return setAppResource;
	}

	public void setSetAppResource(Set<AppResource> setAppResource) {
		this.setAppResource = setAppResource;
	}
	
	public int getAppResourceNum (){
		Set<AppResource> sets = getSetAppResource();
		return sets.size();
	}

	public String getAccountEncryptSalt() {
		return accountEncryptSalt;
	}

	public void setAccountEncryptSalt(String accountEncryptSalt) {
		this.accountEncryptSalt = accountEncryptSalt;
	}
}