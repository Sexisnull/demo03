package com.gsww.jup.entity.sys;

import java.util.List;

public class SysResourceTree {
	private String id; // 节点id
	private String name; // 节点名字
	private String customType; // 节点类型标识值
	private String parId; // 父节点id
	private boolean checked; // 是否选中
	private boolean checkbox; // 节点是否显示checkbox，这样就可以实现某些节点没有复选框，要求树type为checkbox
	private boolean horiz; // 节点是否横向排布
	private String icon;
	private boolean checkInteractParent; // 节点选中时是否影响其父节点，没有默认为true
	private boolean checkInteractSub; // 节点选中时是否影响其子节点，没有默认为true
	private boolean uncheckInteractParent; // 节点取消选中时是否影响其父节点，没有默认为true
	private boolean uncheckInteractSub; // 节点取消选中时是否影响其子节点，没有默认为true
	private List<SysResourceTree> nodes; // 子节点

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomType() {
		return customType;
	}

	public void setCustomType(String customType) {
		this.customType = customType;
	}

	public String getParId() {
		return parId;
	}

	public void setParId(String parId) {
		this.parId = parId;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isCheckbox() {
		return checkbox;
	}

	public void setCheckbox(boolean checkbox) {
		this.checkbox = checkbox;
	}

	public boolean isHoriz() {
		return horiz;
	}

	public void setHoriz(boolean horiz) {
		this.horiz = horiz;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public boolean isCheckInteractParent() {
		return checkInteractParent;
	}

	public void setCheckInteractParent(boolean checkInteractParent) {
		this.checkInteractParent = checkInteractParent;
	}

	public boolean isCheckInteractSub() {
		return checkInteractSub;
	}

	public void setCheckInteractSub(boolean checkInteractSub) {
		this.checkInteractSub = checkInteractSub;
	}

	public boolean isUncheckInteractParent() {
		return uncheckInteractParent;
	}

	public void setUncheckInteractParent(boolean uncheckInteractParent) {
		this.uncheckInteractParent = uncheckInteractParent;
	}

	public boolean isUncheckInteractSub() {
		return uncheckInteractSub;
	}

	public void setUncheckInteractSub(boolean uncheckInteractSub) {
		this.uncheckInteractSub = uncheckInteractSub;
	}

	public List<SysResourceTree> getNodes() {
		return nodes;
	}

	public void setNodes(List<SysResourceTree> nodes) {
		this.nodes = nodes;
	}
}
