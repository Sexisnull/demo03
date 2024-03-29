
var zNodes = [];
var log,
	className = "dark";
function dblClickExpand(treeId, treeNode) {
	return treeNode.level > 0;
}

function onClick(e, treeId, treeNode, clickFlag) {			//点击区域将数据填充到表单
    url = pageUrl + "?orgId="+treeNode.id
    $("#main1").attr("src", url);
    $('#groupid').val(treeNode.id);
    $('#orgId').val(treeNode.id);
}

//treeId是treeDemo
function filter(treeId, parentNode, childNodes) {
	if (!childNodes) return null;
	for (var i = 0, l = childNodes.length; i < l; i++) {
		childNodes[i].name = childNodes[i].name.replace('', '');
	}
	return childNodes;
}

function zTreeOnExpand(event, treeId, treeNode) {
    alert(treeNode.tId + ", " + treeNode.name);
};


$(document).ready(function() {
	$("#treeDemo").height(500); //-20
	$("#treeDemo").width(200); //-20
    var orgId = $("#orgId").val();
    var setting1 = {
        async : {
            enable : true,
            url : "../uids/orgTree",
            autoParam : [ "id=pId" ],
            // autoParam : [ "id" ],
            otherParam : {
                "orgId" : orgId
            }
            // dataFilter : filter
        },
        // check: {
        //     enable: true,
        //     chkStyle: "radio",
        //     radioType: "all"
        // },
        view : {
            dblClickExpand : dblClickExpand,
            selectedMulti : false
        },
        data : {
            simpleData : {
                enable : true
            }
        },
        callback : {
            onClick : onClick
            // onAsyncSuccess : ztreeOnAsyncSuccess
            // onExpand: zTreeOnExpand
        }
    };
    $.fn.zTree.init($("#treeDemo"), setting1, zNodes);
});