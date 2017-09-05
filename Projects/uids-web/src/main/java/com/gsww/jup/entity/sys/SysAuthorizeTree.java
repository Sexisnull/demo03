package com.gsww.jup.entity.sys;


public class SysAuthorizeTree {
	private String id; // 节点id
	private String pId; // 父节点id
	private String name; // 节点名字
	private boolean checked; // 是否选中
	private String customType; // 节点类型标识值
//	private boolean checkbox; // 节点是否显示checkbox，这样就可以实现某些节点没有复选框，要求树type为checkbox
//	private boolean horiz; // 节点是否横向排布
	private String icon;
//	private boolean checkInteractParent=true; // 节点选中时是否影响其父节点，没有默认为true
//	private boolean checkInteractSub=true; // 节点选中时是否影响其子节点，没有默认为true
//	private boolean uncheckInteractParent=true; // 节点取消选中时是否影响其父节点，没有默认为true
//	private boolean uncheckInteractSub=true; // 节点取消选中时是否影响其子节点，没有默认为true
//	private List<SysAuthorizeTree> nodes=new ArrayList<SysAuthorizeTree>(); // 子节点
	
	/*public SysAuthorizeTree getSubNode(String id){
		for(SysAuthorizeTree tree:nodes){
			if(tree.getId().equals(id)){
				return tree;
			}else{
				tree.getSubNode(id);
			}
		}
		return null;
	}
	
	public boolean addSubTree(String parentId,SysAuthorizeTree tree){
		if(this.id.equals(parentId)){
			nodes.add(tree);
			return true;
		}else{
			for(SysAuthorizeTree tree1:nodes){
				tree1.addSubTree(parentId, tree);
			}
		}
		return false;
	}*/
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
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

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
}
