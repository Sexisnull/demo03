var setting = {
	async : {
		enable : true,
		url : rootCtx+"/sys/departmenttreeuser" //获取节点数据的URL地址
	},
	view : {
		dblClickExpand : false
	},
	check: {
		enable: true,
		nocheckInherit: false
	},
	data: {
		simpleData: {
			enable: true
		}
	},
	callback : {
		onCheck: onCheck
	}
};

var zNodes = [];
function zTreeBeforeClick(e,treeId,treeNode) {
	return treeNode.isParent;//当是父节点 返回false 不让选取
}
function onCheck(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    nodes=zTree.getCheckedNodes(true);
    var names="";
    var ids="";
	for(i=0;i<nodes.length;i++){
		if(i==nodes.length-1){
			names+=nodes[i].name;
			ids+=nodes[i].id;
		}else{
			names+=nodes[i].name+",";
			ids+=nodes[i].id+",";
		}
	}
	$("#userName").val(names);
	$("#loginAccount").val(ids);
}
$(function() {
	//选择机构
	$(".select-radio").on("click", function(event) {
		$('.error-info-checkedbox').remove();
		$(this).removeClass('erro');
	});
	//初始化组织机构树
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
	//
	$(".icon-date-r").click(function() {
		$(this).prev("input").click();
	});
	
	$("#userName").Popup($("#treeDemo"), { width: "auto" });
});
