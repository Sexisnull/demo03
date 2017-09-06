/**
 * IconTree
 */
function IconTree(options) {
	BaseTree.call(this, options);
	
	this.arrowClick = null; // 箭头单击事件处理函数
	
	$.extend(this, options);
}
/**
 * extend from BaseTree
 */
IconTree.prototype = new BaseTree();
/**
 * createRootNode(o)
 */
IconTree.prototype.createRootNode = function(o) {
	return new IconNode(o);
}