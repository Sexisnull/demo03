var setting = {
    async: {
        enable: true,
        // url:"sys-region!regiontree.do",
        url:rootCtx+"/sys/departmenttree",
        autoParam: ["id"],
        otherParam: { "type": "1" },
        dataFilter: filter
    },
    view: {
       // addHoverDom: addHoverDom,
       // removeHoverDom: removeHoverDom,
       // dblClickExpand: dblClickExpand,
        selectedMulti: false
    },
    edit: {
        enable: false,
        editNameSelectAll: true,
        drag: {
            isCopy: false,
            isMove: false
        }//,
       // showRemoveBtn: showRemoveBtn,
       // renameTitle: '重命名',
       // removeTitle: "删除部门",
        //showRenameBtn: showRenameBtn
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        // beforeDrag: beforeDrag,
        // beforeDrop: beforeDrop,
        // beforeEditName: beforeEditName,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        onClick: onClick,
        onRemove: zTreeOnRemove
    }
};

var zNodes = [];
var log, className = "dark";
function dblClickExpand(treeId, treeNode) {
    return treeNode.level > 0;
}

function beforeEditName(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    return confirm("确定 -- " + treeNode.name + " 的编辑状态吗？");
}
function beforeRemove(treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    var flag = false;
    $.ajax({
        type: "POST",
        url: rootCtx+"/sys/department/checkUser",
        data: "key=" + treeNode.tld,
        async: false,
        success: function (data) {
            if (data == "exist") {
                alert("该部门下有用户，请先删除用户");
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
function zTreeOnRemove(event, treeId, treeNode) {
    className = (className === "dark" ? "" : "dark");
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    // ajax调用后台进行数据库删除
    if (treeNode.id != undefined) {
        $.ajax({
            type: "POST",
            url: rootCtx+"/sys/department/delete",
            data: "key=" + treeNode.tld,
            dataType: "text",
            success: function (data) {
                if (data == "success") {
                    // zTree.removeNode(treeNode);
                    // zTree.reAsyncChildNodes(treeNode.getParentNode().getParentNode(),
                    // "refresh");
                    $("#regionName").val("");
                    $("#regionName1").val("");
                	$("#groupallname").val("");
                	$("#regionCode").val("");
                    $("#regionCode1").attr("value","");
                	$("#deptId").val("");
                	$("#parentDeptName").val("");
                	$("#parentDeptCode").val("");
                	$("#areacode").val("");
                	$("#areaname").val("");
                	$("#deptState").val("");
                	$("#orgcode").val("");
                	$("#createTime").val("");
                	$("#pinyin").val("");
                	$("#setId").val("");
                	$("#userAcctId").val("");
                	$("#spec").val("");
                	$("#iscombine").val("");
                	$("#suffix").val("");
                	$("#nodetype").val("");
                	$("#orgtype").val("");
                	$("#areatype").val("");
                	$("#deptSeq").val("");
                    return true;
                } else if (data == "exist") {
                    alert("该部门下有用户，请先删除用户");
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

function beforeRename(treeId, treeNode, newName) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    var oldName = treeNode.name;
    className = (className === "dark" ? "" : "dark");
    if (newName.length == 0) {
        alert("节点名称不能为空.");
        setTimeout(function () { zTree.editName(treeNode) }, 10);
        return false;
    }
    if (newName.length > 32) {
        alert("节点名称过长，请检查！");
        return false;
    }
    if (newName != treeNode.name) {
        if (1 == 1) {
            $.ajax({
                type: "POST",
                url: rootCtx+"/sys/department/updateName",
                data: "key=" + treeNode.tld + "&newName=" + newName,
                dataType: "text",
                success: function (data) {
                    if (data == "repeat") {
                        treeNode.name = oldName;
                        alert("部门名称重复，请重新输入！");

                        treeNode.viewtype = '2';
                        zTree.updateNode(treeNode);

                        $.ajax({
                            type: "POST",
                            url: rootCtx+"/sys/department/getRegion",
                            data: "key=" + treeNode.tld,
                            dataType: "json",
                            success: function (data) {
                                var json = data;
                                if (json.deptName == undefined) {
                                    $("#regionName").val("");
                                } else {
                                    $("#regionName").val(json.deptName);
                                }
                                $("#regionCode").val(json.deptCode);
                                $("#regionCode1").val(json.deptCode);
                                $("#deptId").val(json.deptId);
                                $("#parentDeptName").val(json.parentDeptName);
                                $("#parentDeptCode").val(json.parentDeptCode);
                                $("#deptSeq").val(json.deptSeq);
                                $("#deptState").val(json.deptState);
                                $("#createTime").val(json.createTime);
                                $("#setId").val(json.setId);
                                $("#userAcctId").val(json.userAcctId);
                            }
                        });

                        return true;

                    } else {
                        confirm("确认要将节点重命名为 " + newName + " 吗？");
                        treeNode.viewtype = '2';
                        zTree.updateNode(treeNode);

                        $.ajax({
                            type: "POST",
                            url: rootCtx+"/sys/department/getRegion",
                            data: "key=" + treeNode.tld,
                            dataType: "json",
                            success: function (data) {
                                var json = data;
                                if (json.deptName == undefined) {
                                    $("#regionName").val("");
                                } else {
                                    $("#regionName").val(json.deptName);
                                }
                                $("#regionCode").val(json.deptCode);
                                $("#regionCode1").val(json.deptCode);
                                $("#deptId").val(json.deptId);
                                $("#parentDeptName").val(json.parentDeptName);
                                $("#parentDeptCode").val(json.parentDeptCode);
                                $("#deptSeq").val(json.deptSeq);
                                $("#deptState").val(json.deptState);
                                $("#createTime").val(json.createTime);
                                $("#setId").val(json.setId);
                                $("#userAcctId").val(json.userAcctId);
                            }
                        });

                        return true;
                    }
                }
            });
        } else {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.cancelEditName();
            return false;
        }
    }

}

function showRemoveBtn(treeId, treeNode) {
    if (treeNode.id == '1' || treeNode.isParent) {
        return false;
    } else {
        return true;
    }
}
function showRenameBtn(treeId, treeNode) {
    if (treeNode.id == '1') {
        return false;
    } else {
        return true;
    }
}
function getTime() {
    var now = new Date(),
    h = now.getHours(),
    m = now.getMinutes(),
    s = now.getSeconds(),
    ms = now.getMilliseconds();
    return (h + ":" + m + ":" + s + " " + ms);
}

var newCount = 1;
function addHoverDom(treeId, treeNode) {
    // alert("addHoverDom");
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_" + treeNode.id).length > 0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.id
                       + "' title='添加部门' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_" + treeNode.id);
    if (btn) btn.bind("click", function () {
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
        } else if (seq.length == 2) {
            seqNum = "0" + seqNum;
        }
        var dcode = treeNode.id + "" + seqNum;
        $.ajax({
            type: "POST",
            url: rootCtx+"/sys/department/save",
            data: "seq=" + seq + "&name=" + name + "&parId=" + treeNode.id + "&dcode=" + dcode,
            dataType: "json",
            success: function (data) {
                if (data.ret == 1) {
                    zTree.addNodes(treeNode, { id: dcode, pId: treeNode.id, tld: data.id, name: name, title: name, seq: seq });
                    // zTree.reAsyncChildNodes(treeNode, "refresh");
                    $("#" + treeNode.tId + "_remove").remove();
                    alert("部门添加成功！");
                    return true;
                } else if (data.ret == 2) {
                    alert("部门名称或编码重复！");
                    return false;
                } else {
                    alert("部门添加失败！");
                    return false;
                }
            }
        });
        return false;
    });
};
function removeHoverDom(treeId, treeNode) {
    $("#addBtn_" + treeNode.id).unbind().remove();
};
function selectAll() {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.setting.edit.editNameSelectAll = $("#selectAll").attr("checked");
}
function showMenu() {
    var cityObj = $("#parentDept");
    var cityOffset = $("#parentDept").offset();
    $("#menuContent").css({ left: cityOffset.left + "px", top: cityOffset.top + cityObj.outerHeight() + "px" }).slideDown("fast");

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

function onClick(e, treeId, treeNode, clickFlag) {
    if (treeNode.id != '1') {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
        nodes = zTree.getSelectedNodes();
        var name = nodes[0].name;
        var id = nodes[0].id;
        var key = nodes[0].tld;
        var seq = nodes[0].seq;
            window.location.href = rootCtx+"/overAll/qlsxList?qlName=${qlName }&qlKind=${qlKind}&deptCode="+id;
    }
}
// treeId是treeDemo
function filter(treeId, parentNode, childNodes) {
    if (!childNodes) return null;
    for (var i = 0, l = childNodes.length; i < l; i++) {
        childNodes[i].name = childNodes[i].name.replace('', '');
    }
    return childNodes;
}
$(document).ready(function () {
    var validateUp = $("#errorUpForm").validate({
        errorPlacement: function (error, element) {
            // return error;
        },
        showErrors: function (errorMap, errorList) {
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
            // this.defaultShowErrors();
        },
        success: function () {
            // $(".formError").remove();
        },
        submitHandler: function (form) {
            form.submit();
        }
    });

    $("#treeDemo").height(305);// -20
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
});