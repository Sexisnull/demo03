package com.gsww.jup.entity.sys;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * SysResource entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "SYS_MENU")
public class SysMenu implements java.io.Serializable{

	// Fields

	private String menuId;
	private String parentMenuId;
	private String menuName;
	private String menuUrl;
	private String menuImg;
	private String menuState;
	private String isTreenode;
	private int menuSeq;
	private Set<SysOperator> sysOperator = new HashSet<SysOperator>();

	// Constructors

	/** default constructor */
	public SysMenu() {
	}

	/** minimal constructor */
	public SysMenu(String menuName, String menuState, int menuSeq) {
		this.menuName = menuName;
		this.menuState = menuState;
		this.menuSeq = menuSeq;
	}

	/** full constructor */
	public SysMenu(String parentMenuId, String menuName, String menuUrl,
			String menuImg, String menuState, String isTreenode, int menuSeq,
			Set SysOperator) {
		this.parentMenuId = parentMenuId;
		this.menuName = menuName;
		this.menuUrl = menuUrl;
		this.menuImg = menuImg;
		this.menuState = menuState;
		this.isTreenode = isTreenode;
		this.menuSeq = menuSeq;
		this.sysOperator = SysOperator;
	}

	// Property accessors

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid.hex")
	@Column(name = "MENU_ID", unique = true, nullable = false, length = 32)
	public String getMenuId() {
		return this.menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "PARENT_MENU_ID")
	public String getParentMenuId() {
		return this.parentMenuId;
	}

	public void setParentMenuId(String parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	@Column(name = "MENU_NAME")
	public String getMenuName() {
		return this.menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "MENU_URL")
	public String getMenuUrl() {
		return this.menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	@Column(name = "MENU_IMG")
	public String getMenuImg() {
		return this.menuImg;
	}

	public void setMenuImg(String menuImg) {
		this.menuImg = menuImg;
	}

	@Column(name = "MENU_STATE")
	public String getMenuState() {
		return this.menuState;
	}

	public void setMenuState(String menuState) {
		this.menuState = menuState;
	}

	@Column(name = "IS_TREENODE")
	public String getIsTreenode() {
		return this.isTreenode;
	}

	public void setIsTreenode(String isTreenode) {
		this.isTreenode = isTreenode;
	}

	@Column(name = "MENU_SEQ")
	public int getMenuSeq() {
		return this.menuSeq;
	}

	public void setMenuSeq(int menuSeq) {
		this.menuSeq = menuSeq;
	}

	@OneToMany(cascade = {CascadeType.REFRESH, CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REMOVE },mappedBy ="sysMenu") 
	public Set<SysOperator> getSysOperator() {
		return sysOperator;
	}

	public void setSysOperator(Set<SysOperator> sysOperator) {
		this.sysOperator = sysOperator;
	}
	
}