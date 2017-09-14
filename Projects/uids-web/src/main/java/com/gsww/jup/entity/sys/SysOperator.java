package com.gsww.jup.entity.sys;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * 系统操作表
 * @author wxl
 *
 */
@Entity
@Table(name = "SYS_OPERATOR")
public class SysOperator implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// Fields    

	private String operatorId;
	private SysMenu sysMenu;
	private String operatorName;
	private String operatorUrl;
	private String operatorImage;
	private int operatorLevel;
	private String operatorState;
	private String operatorType;
	private String tabIndex;
	private Set<SysRoleOperRel> sysRoleOperRels = new HashSet<SysRoleOperRel>();

	// Constructors

	/** default constructor */
	public SysOperator() {
	}

	/** full constructor */
	public SysOperator(SysMenu sysMenu, String operatorName, String operatorUrl, String operatorImage, int operatorLevel, String operatorState, String operatorType, String tabIndex, Set sysRoleOperRels) {
		this.sysMenu = sysMenu;
		this.operatorName = operatorName;
		this.operatorUrl = operatorUrl;
		this.operatorImage = operatorImage;
		this.operatorLevel = operatorLevel;
		this.operatorState = operatorState;
		this.operatorType = operatorType;
		this.tabIndex = tabIndex;
		this.sysRoleOperRels = sysRoleOperRels;
	}

	// Property accessors
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "OPERATOR_ID")
	public String getOperatorId() {
		return this.operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	@ManyToOne
	@JoinColumn(name = "MENU_ID")
	public SysMenu getSysMenu() {
		return sysMenu;
	}

	public void setSysMenu(SysMenu sysMenu) {
		this.sysMenu = sysMenu;
	}

	@Column(name = "OPERATOR_NAME")
	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	@Column(name = "OPERATOR_URL")
	public String getOperatorUrl() {
		return this.operatorUrl;
	}

	public void setOperatorUrl(String operatorUrl) {
		this.operatorUrl = operatorUrl;
	}

	@Column(name = "OPERATOR_IMAGE")
	public String getOperatorImage() {
		return this.operatorImage;
	}

	public void setOperatorImage(String operatorImage) {
		this.operatorImage = operatorImage;
	}

	@Column(name = "OPERATOR_LEVEL")
	public int getOperatorLevel() {
		return this.operatorLevel;
	}

	public void setOperatorLevel(int operatorLevel) {
		this.operatorLevel = operatorLevel;
	}

	@Column(name = "OPERATOR_STATE")
	public String getOperatorState() {
		return this.operatorState;
	}

	public void setOperatorState(String operatorState) {
		this.operatorState = operatorState;
	}

	@Column(name = "OPERATOR_TYPE")
	public String getOperatorType() {
		return this.operatorType;
	}

	public void setOperatorType(String operatorType) {
		this.operatorType = operatorType;
	}

	@Column(name = "TAB_INDEX")
	public String getTabIndex() {
		return this.tabIndex;
	}

	public void setTabIndex(String tabIndex) {
		this.tabIndex = tabIndex;
	}

	@OneToMany(cascade = { CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE }, mappedBy = "sysOperator")
	public Set<SysRoleOperRel> getSysRoleOperRels() {
		return sysRoleOperRels;
	}

	public void setSysRoleOperRels(Set<SysRoleOperRel> sysRoleOperRels) {
		this.sysRoleOperRels = sysRoleOperRels;
	}

}
