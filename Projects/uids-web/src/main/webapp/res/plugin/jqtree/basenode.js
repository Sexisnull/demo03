/**
 * BaseNode
 */
function BaseNode(options) {
	AjaxModel.call(this, options);
	
	this.tree = null;                    // 节点所属树对象
	this.id = null;                      // 节点id
	this.name = null;                    // 节点name
	this.parentNode = null;              // 父节点对象
	this.level = null;                   // 节点级数
	this.indent = 0;                     // 节点缩进距离，默认0
	this.createRequestParam = null;      // 构造请求参数的函数
	this.click = null;                   // 节点单击事件处理函数
	this.dblclick = null;                // 节点双击事件处理函数
	this.subNodes = new Array();         // 子节点对象数组
	this.loaded = false;                 // 是否已加载子节点
	this.isParent = false;               // 是否还有子节点，默认没有
	this.open = true,                    // 下级面板是否展开，默认展开
	this.class_node = 'jqtree-node';     // 节点node div样式
	this.class_name = 'jqtree-name';     // 节点name span样式
	this.class_substr = 'substr';		 // 结点name 截串样式
	this.class_sub = 'jqtree-sub';       // 下级节点sub div样式
	this.div_node = $('<div></div>');    // 节点node div
	this.span_name = $('<span></span>'); // 节点name span
	this.div_sub = $('<div></div>');     // 下级节点 sub div
	this.class_hover = 'ui-state-hover'; // hover高亮效果样式
	
	$.extend(this, options);
}
/**
 * extend from AjaxModel
 */
BaseNode.prototype = new AjaxModel();
/**
 * load()
 */
BaseNode.prototype.load = function() {
	if(this.loaded) {
		return;
	}
	
	this.loaded = true;
	
	if(this.createRequestParam) {
		this.createRequestParam(this);
	}
	
	AjaxModel.prototype.load.call(this);
}
/**
 * view()
 */
BaseNode.prototype.view = function() {
	var _this = this;
	
	this.div_node.addClass(this.class_node);
	this.span_name.addClass(this.class_name);
	this.span_name.addClass(this.class_substr);
	this.div_sub.addClass(this.class_sub);
	
	this.span_name.append(this.name);
	this.div_node.append(this.span_name);
	
	this.div_node.css('padding-left', this.indent * this.level);
	
	if(this.parentNode) {
		this.parentNode.div_sub.append(this.div_node);
		this.div_node.after(this.div_sub);
	}
	
	this.div_node.hover(function() {
		$(this).addClass(_this.class_hover);
	}, function() {
		$(this).removeClass(_this.class_hover);
	});
}
/**
 * clickBind()
 */
BaseNode.prototype.clickBind = function() {
	var _this = this;
	
	if(this.click) {
		this.div_node.click(function(event) {
			_this.click(_this, event);
			
			// 单击后高亮保持
			_this.tree.container.find('.ui-state-default').removeClass('ui-state-default');
			_this.span_name.addClass('ui-state-default');
		});
	}
}
/**
 * dblclickBind()
 */
BaseNode.prototype.dblclickBind = function() {
	var _this = this;
	
	if(this.dblclick) {
		this.div_node.dblclick(function(event) {
			_this.dblclick(_this, event);
		});
	}
}
/**
 * control()
 */
BaseNode.prototype.control = function() {
	var _this = this;
	
	this.clickBind();
	this.dblclickBind();
	
	// 整合右键菜单
	if(this.tree.contextmenu) {
		this.span_name.bind('contextmenu', function(e) {
			_this.tree.contextmenu.show(_this, e);
			
			// 右键菜单选中高亮保持
			_this.tree.container.find('.context-menu-selected').removeClass('context-menu-selected');
			$(this).addClass('context-menu-selected');
			return false;
		});
	}
}
/**
 * toggle()
 */
BaseNode.prototype.toggle = function() {
	if(this.loaded) {
		if(this.open) {
			this.collapse();
		}
		else {
			this.expand();
		}
	}
	else {
		this.load();
	}
}
/**
 * collapse()
 */
BaseNode.prototype.collapse = function() {
	this.div_sub.hide();
	this.open = false;
}
/**
 * expand()
 */
BaseNode.prototype.expand = function() {
	this.div_sub.show();
	this.open = true;
}
/**
 * success(data)
 */
BaseNode.prototype.success = function(data) {
	var _this = this;
	
	$.each(data, function(i, o) {
		$.extend(o, _this.tree.options, {
			parentNode: _this,
			level: _this.level + 1
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
		
		var node = _this.createSubNode(o);
		
		node.view();
		node.control();
		
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
		_this.subNodes[i] = node;
	});
	
	if(this.open) {
		this.expand();
	}
	else {
		this.collapse();
	}
}
/**
 * xmlParse()
 */
BaseNode.prototype.xmlParse = function(data) {
	if((window.ActiveXObject && data instanceof ActiveXObject) || (!window.ActiveXObject && data instanceof XMLDocument)) {
		var parser = new XMLParser();
		data = parser.parse($(data).children().children());
	}
	return data;
}
/**
 * createSubNode(o)
 */
BaseNode.prototype.createSubNode = function(o) {
	return new BaseNode(o);
}