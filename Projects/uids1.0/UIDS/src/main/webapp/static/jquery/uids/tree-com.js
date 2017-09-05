/**
 * 初始化树js 
 * 
 * 参数列表
 * @param url				请求url
 * @param idName 			传入后台的父节点id
 * @param beforeClick 		点击前事件
 * @param onClick			单机事件
 * @param onDblClick		双击事件
 * @param treeId			html中需要显示树的id
 *	注意：后台传入前台的数据必须传入 id:childId,pId:parentId, 用于设置idKey，pIdKey
*/
function initTree(url, idName, beforeClick, onClick, onDblClick, treeId){
// 1、结构树设置信息
var setting = {
		// 禁止多选
		view: { 
			selectedMulti: false,
			showLine:false
		},
		// 树节点数据源
		async: { 
			enable: true,
			url: url,
			dataType: 'json',
			autoParam: ["id="+idName, "isDisabled"] // 默认参数为id，可重命名为pid
		},
		// 树节点中数据的字段设置
		data: {
			simpleData: {
				enable: true,
		  		idKey: "id",  
		   		pIdKey: "pId",
		   	}
		},  
		// 有关树操作的一些回调函数
		callback: {
			beforeClick : beforeClick,
			onClick : onClick,
			onDblClick : onDblClick
		}
	};

// 2、用设置去初始化结构树
$.fn.zTree.init($("#"+treeId), setting);	
}