/**
 * RadioTree
 */
function RadioTree(options) {
	IconTree.call(this, options);
	
	this.checkedNode = null; // 单选中的节点
	
	$.extend(this, options);
}
/**
 * extend from IconTree
 */
RadioTree.prototype = new IconTree();
/**
 * createRootNode(o)
 */
RadioTree.prototype.createRootNode = function(o) {
	return new RadioNode(o);
}
/**
 * getCheckedNode()
 */
RadioTree.prototype.getCheckedNode = function() {
	return this.checkedNode;
}
/**
 * val(attrName)
 */
RadioTree.prototype.val = function(attrName) {
	if(this.checkedNode) {
		return this.checkedNode[attrName];
	}
	else {
		return '';
	}
}