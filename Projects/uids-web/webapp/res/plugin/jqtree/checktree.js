/**
 * CheckTree
 */
function CheckTree(options) {
	IconTree.call(this, options);
	
	this.checkClick = null;   // 复选框单击事件处理函数
	this.checkedNodes = null; // 选中节点的数组
	
	$.extend(this, options);
}
/**
 * extend from IconTree
 */
CheckTree.prototype = new IconTree();
/**
 * createRootNode(o)
 */
CheckTree.prototype.createRootNode = function(o) {
	return new CheckNode(o);
}
/**
 * getCheckedNodes()
 */
CheckTree.prototype.getCheckedNodes = function() {
	this.checkedNodes = new Array();
	
	$.each(this.rootNodes, function(i, o) {
		o.getCheckedNodes();
	});
	
	return this.checkedNodes;
}
/**
 * val(attrName)
 */
CheckTree.prototype.val = function(attrName) {
	this.getCheckedNodes();
	var str = '';
	
	$.each(this.checkedNodes, function(i, o) {
		str += ',' + o[attrName];
	});
	str = str.substring(1, str.length);
	
	return str;
}