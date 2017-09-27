
var setting = {
	async : {
		enable : true,
		url : "zoneTree",
		autoParam : [ "id" ],
		otherParam : {
			"type" : "1"
		},
		dataFilter : filter
	},
	view : {
		addHoverDom : addHoverDom,
		removeHoverDom : removeHoverDom,
		dblClickExpand : dblClickExpand,
		selectedMulti : false
	},
	edit : {
		enable : true,
		editNameSelectAll : true,
		drag : {
			isCopy : false,
			isMove : false
		},
		showRemoveBtn : showRemoveBtn,
		renameTitle : '重命名',
		removeTitle : "删除区域",
		showRenameBtn : showRenameBtn
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		//beforeDrag: beforeDrag,
		//beforeDrop: beforeDrop,
		//beforeEditName: beforeEditName,				
		beforeRemove : beforeRemove,
		beforeRename : beforeRename,
		onClick : onClick,
		onRemove : zTreeOnRemove
	}
};

var zNodes = [];
var log,
	className = "dark";
function dblClickExpand(treeId, treeNode) {
	return treeNode.level > 0;
}

function beforeEditName(treeId, treeNode) {
	className = (className === "dark" ? "" : "dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	return confirm("确定 -- " + treeNode.name + " 的编辑状态吗？");
}
function beforeRemove(treeId, treeNode) {                   //删除前
	className = (className === "dark" ? "" : "dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	var flag = false;
	$.ajax({
		type : "POST",
		url : "checkZone",
		data : "key=" + treeNode.tld,
		async : false,
		success : function(data) {
			if (data == "exist") {
				alert("该区域下有子区域，请先删除子区域");
				flag = false;
			} else {
				flag = true;
			}
		}
	});
	if (flag == true) {
		return confirm("确定要删除 " + treeNode.name + " 吗？");
	} else {
		return false;
	}

}
function zTreeOnRemove(event, treeId, treeNode) {                //删除
	className = (className === "dark" ? "" : "dark");
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.selectNode(treeNode);
	//ajax调用后台进行数据库删除
	if (treeNode.id != undefined) {
		$.ajax({
			type : "POST",
			url : "zonedelete",
			data : "key=" + treeNode.tld,
			dataType : "text",
			success : function(data) {
				if (data == "success") {
					$("#regionName").attr("value", "");
					$("#regionName1").attr("value", "");
					$("#regionCode").attr("value", "");
					$("#regionCode1").attr("value", "");
					$("#iid").attr("value", "");
					$("#pid").attr("value", "");
					$("#type").attr("value", "");
					return true;
				} else if (data == "exist") {
					alert("该区域下有子区域，请先删除子区域");
					zTree.cancelSelectedNode();
					return false;
				} else {
					alert(data);
					zTree.cancelSelectedNode();
					return false;
				}
			}
		});
	} else {
		return false;
	}
}

function beforeRename(treeId, treeNode, newName) {       //重命名前---获取区域详细信息
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	var oldName = treeNode.name;
	className = (className === "dark" ? "" : "dark");
	if (newName.length == 0) {
		alert("节点名称不能为空.");
		setTimeout(function() {
			zTree.editName(treeNode)
		}, 10);
		return false;
	}
	if (newName.length > 32) {
		alert("节点名称过长，请检查！");
		return false;
	}
	if (newName != treeNode.name) {
		if (1 == 1) {
			$.ajax({
				type : "POST",
				url : "updateName",
				data : "key=" + treeNode.tld + "&newName=" + newName,
				dataType : "text",
				success : function(data) {
					if (data == "repeat") {
						treeNode.name = oldName;
						alert("区域名称重复，请重新输入！");

						treeNode.viewtype = '2';
						zTree.updateNode(treeNode);

						$.ajax({
							type : "POST",
							url : "getZoneInfo", //异步获取区域的完整信息并以json的格式返回
							data : "key=" + treeNode.tld,
							dataType : "json",
							success : function(data) {
								var json = data;
								if (json.name == undefined) {
									$("#regionName").attr("value", "");
									$("#regionName1").attr("value", "");
								} else {
									$("#regionName").attr("value", json.name);
									$("#regionName1").attr("value", json.name);
								}
								$("#regionCode").attr("value", json.codeId);
								$("#regionCode1").attr("value", json.codeId);
								$("#iid").attr("value", json.iid);
								$("#pid").attr("value", json.pid);
								$("#type").attr("value", json.type);
							}
						});

						return true;

					} else {
						confirm("确认要将节点重命名为 " + newName + " 吗？");
						treeNode.viewtype = '2';
						zTree.updateNode(treeNode);

						$.ajax({
							type : "POST",
							url : "getZoneInfo",
							data : "key=" + treeNode.tld,
							dataType : "json",
							success : function(data) {
								var json = data;
								if (json.name == undefined) {
									$("#regionName").attr("value", "");
									$("#regionName1").attr("value", "");
								} else {
									$("#regionName").attr("value", json.name);
									$("#regionName1").attr("value", json.name);
								}
								$("#regionCode").attr("value", json.codeId);
								$("#regionCode1").attr("value", json.codeId);
								$("#iid").attr("value", json.iid);
								$("#pid").attr("value", json.pid);
								$("#type").attr("value", json.type);
							}
						});
						return true;
					}
				}
			}); //Ajax请求结束
		} else {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			zTree.cancelEditName();
			return false;
		}
	}

}

function showRemoveBtn(treeId, treeNode) {						//展示删除按钮
	if (treeNode.id == '1' || treeNode.isParent) {
		return false;
	} else {
		return true;
	}
}
function showRenameBtn(treeId, treeNode) {						//展示重命名按钮
	if (treeNode.id == '1') {
		return false;
	} else {
		return true;
	}
}
function getTime() {											//获取时间
	var now = new Date(),
		h = now.getHours(),
		m = now.getMinutes(),
		s = now.getSeconds(),
		ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {    				//用于当鼠标移动到节点上时，显示用户自定义控件，显示隐藏状态同 zTree 内部的编辑、删除按钮
	var sObj = $("#" + treeNode.tId + "_span");
	if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0) return;
	var addStr = "<span class='button add' id='addBtn_" + treeNode.id
		+ "' title='添加区域' onfocus='this.blur();'></span>";
	sObj.after(addStr);
	var btn = $("#addBtn_" + treeNode.id);
	if (btn) btn.bind("click", function() {
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var seq = 1;
			if (treeNode.children && treeNode.children != "") {
				var nodes = treeNode.children;
				seq = parseInt(nodes[nodes.length - 1].seq) + 1;
			}
			var name = treeNode.name + seq;
			var level = parseInt(treeNode.level) + 1;

			var seqNum = "" + seq;
			if (seqNum.length == 1) {
				seqNum = "00" + seqNum;
			} else if (seqNum.length == 2) {
				seqNum = "0" + seqNum;
			}
			var dcode = treeNode.id + "" + seqNum;
			$.ajax({
				type : "POST",
				url : "saveZone",
				data : "seq=" + seq + "&name=" + name + "&parId=" + treeNode.tld + "&dcode=" + dcode + "&type=" + treeNode.level,
				dataType : "json",
				success : function(data) {
					if (data.ret == 1) {
						zTree.addNodes(treeNode, {
							id : dcode,
							pId : treeNode.id,
							tld : data.id,
							name : name,
							title : name,
							seq : seq
						});
						$("#" + treeNode.tId + "_remove").remove();
						alert("区域添加成功！");
						return true;
					} else if (data.ret == 2) {
						alert("区域名称或编码重复！");
						return false;
					} else if(data.ret == 4) {
						alert("不能再添加子区域了！");
						return false;
					} else {
						alert("区域添加失败！");
						return false;
					}
				}
			});
			return false;
		});
}
;
function removeHoverDom(treeId, treeNode) {
	$("#addBtn_" + treeNode.id).unbind().remove();
}
;
function selectAll() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo");
	zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}
function showMenu() {
	var cityObj = $("#parentDept");
	var cityOffset = $("#parentDept").offset();
	$("#menuContent").css({
		left : cityOffset.left + "px",
		top : cityOffset.top + cityObj.outerHeight() + "px"
	}).slideDown("fast");

	$("html").bind("mousedown", onBodyDown);

}
function hideMenu() {
	$("#menuContent").fadeOut("fast");
	$("html").unbind("mousedown", onBodyDown);
}
function onBodyDown(event) {
	if (!(event.target.id == "parentDept" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length > 0)) {
		hideMenu();
	}
}

function onClick(e, treeId, treeNode, clickFlag) {			//点击区域将数据填充到表单
	if (treeNode.id != '1') {
		var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
			nodes = zTree.getSelectedNodes();
		var name = nodes[0].name;
		var id = nodes[0].id;
		var key = nodes[0].tld;
		var seq = nodes[0].seq;
		//后期需要用ajax获取数据后填充到表单		
		if (key != undefined) {
			$.ajax({
				type : "POST",
				url : "getZoneInfo",
				data : "key=" + key,
				dataType : "json",
				success : function(data) {
					var jsonStr = data;
					if (jsonStr.name == undefined) {
						$("#regionName").attr("value", "");
						$("#regionName1").attr("value", "");
					} else {
						$("#regionName").attr("value", jsonStr.name);
						$("#regionName1").attr("value", jsonStr.name);
					}
					$.ajax({
						type : "POST",
						url : "getZonePname",
						data : "pid=" + jsonStr.pid,
						dataType : "json",
						success : function(data) {
							$("#pName").attr("value", data.name);
						}
					});
					$("#regionCode").attr("value", jsonStr.codeId);
					$("#regionCode1").attr("value", jsonStr.codeId);
					$("#iid").attr("value", jsonStr.iid);
					$("#pid").attr("value", jsonStr.pid);
					/*$("#type").attr("value", jsonStr.type);*/
					if(jsonStr.type == 1) {
						$("#typeName").html("省");
					} else if(jsonStr.type == 2) {
						$("#typeName").html("市");
					} else if(jsonStr.type == 3) {
						$("#typeName").html("区县");
					} else if(jsonStr.type == 4) {
						$("#typeName").html("乡镇");
					} else {
						$("#typeName").html("");
					}
				}
			});
		} else {
			$("#deptId").attr("value", "");
			$("#state").attr("value", "");
			$("#regionName").attr("value", name);
			$("#regionName1").attr("value", name);
			$("#regionRemark").attr("value", "");
			var seqNum = "" + seq;
			if (seqNum.length == 1) {
				seqNum = "00" + seqNum;
			} else if (seq.length == 2) {
				seqNum = "0" + seqNum;
			}
			$("#regionCode").attr("value", nodes[0].getParentNode().tld + seqNum);
		}
		//$("#regionName").attr("value",name);
		//$("#regionCode1").attr("value", id);
		//$("#regionCode").attr("value", id);
		$("#key").attr("value", key);
		if (nodes[0].getParentNode()) {
			$("#pid").attr("value", nodes[0].getParentNode().id);
		}
	}
}
//treeId是treeDemo 
function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace('', '');
	}
	return childNodes;
}
$(document).ready(function() {
	var validateUp = $("#errorUpForm").validate({
		errorPlacement : function(error, element) {
			//return error;
		},
		showErrors : function(errorMap, errorList) {
			$(".formError").remove();
			for (var s in errorMap) {
			}
			for (var i = 0; errorList[i]; i++) {
				var ee = $(errorList[i].element);
				var emsg = errorMap[ee.attr("name")];
				var position = ee.position();
				var style = "top:" + position.top + "px;left:" + position.left + "px;";
				var div = $('<div class="formError" style="' + style + '"><div class="formErrorContent">' + emsg + '</div><div class="formErrorArrow"><div class="line10"><!-- --></div><div class="line9"><!-- --></div><div class="line8"><!-- --></div><div class="line7"><!-- --></div><div class="line6"><!-- --></div><div class="line5"><!-- --></div><div class="line4"><!-- --></div><div class="line3"><!-- --></div><div class="line2"><!-- --></div><div class="line1"><!-- --></div></div></div>');
				ee.after(div);
				var top = position.top - div.outerHeight();
				div.attr("style", "top:" + top + "px;left:" + position.left + "px;");
			}
		//this.defaultShowErrors();
		},
		success : function() {
			//$(".formError").remove();
		},
		submitHandler : function(form) {
			form.submit();
		}
	});

	$("#treeDemo").height(400); //-20
	$.fn.zTree.init($("#treeDemo"), setting, zNodes);
});