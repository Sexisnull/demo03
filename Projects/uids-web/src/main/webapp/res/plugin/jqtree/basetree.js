/**
 * BaseTree
 */
function BaseTree(options) {
	AjaxModel.call(this, options);
	
	this.container = null;        // 容器
	this.options = options;       // 树参数对象，也是树节点参数对象，参数对象的大部分属性为树节点所用
	this.rootNodes = new Array(); // 根节点对象数组，树可以有一个根，也可以是多个根
	
	$.extend(this, options);
}
/**
 * extend from AjaxModel
 */
BaseTree.prototype = new AjaxModel();
/**
 * init()
 */
BaseTree.prototype.init = function() {
	this.container.get(0).onselectstart = function() { // 禁止树被鼠标选择
		return false;
	};

	this.options.tree = this; // 树对象作为树节点参数对象的属性
	
	this.load();
}
/**
 * success(data)
 */
BaseTree.prototype.success = function(data) {
	var _this = this;
	
	$.each(data, function(i, o) {
		$.extend(o, _this.options, {
			parentNode: null,
			level: 0
		});

		if(o.nodes) {
			o.isParent = false;
		}
		else {
			o.nodes = [];
		}
		if(o.isParent) {
			o.nodes = null;
		}
		
		var node = _this.createRootNode(o);
		
		node.view();
		node.control();
		
		_this.container.append(node.div_node);
		node.div_node.after(node.div_sub);
		
		if(o.nodes) {
			node.loaded = true;
			
			if(o.nodes.length == 0) {
				node.isParent = false;
			}
			else {
				node.isParent = true;
			}
			
			node.success(o.nodes);
		}
		_this.rootNodes[i] = node;
	});
}
/**
 * xmlParse()
 */
BaseTree.prototype.xmlParse = function(data) {
	if((window.ActiveXObject && data instanceof ActiveXObject) || (!window.ActiveXObject && data instanceof XMLDocument)) {
		var parser = new XMLParser();
		data = parser.parse($(data).children().children());
	}
	return data;
}
/**
 * createRootNode(o)
 */
BaseTree.prototype.createRootNode = function(o) {
	return new BaseNode(o);
}