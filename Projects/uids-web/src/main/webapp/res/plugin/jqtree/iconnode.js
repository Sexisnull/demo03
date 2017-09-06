/**
 * IconNode
 */
function IconNode(options) {
	BaseNode.call(this, options);
	
	this.arrowClick = null;                        // 箭头单击事件处理函数
	this.class_node_horiz = 'jqtree-node-horiz';   // 节点横向排列样式
	this.class_sub_horiz = 'jqtree-sub-horiz';     // 下级节点横向排列sub div样式
	this.class_icon = 'jqtree-icon';               // 图片div基本样式
	this.class_arrow = 'jqtree-arrow';             // 箭头样式
	this.class_arrow_close = 'jqtree-arrow-close'; // 箭头关闭样式
	this.class_icon_close = 'jqtree-icon-close';   // 图标关闭样式
	this.class_arrow_open = 'jqtree-arrow-open';   // 箭头打开样式
	this.class_icon_open = 'jqtree-icon-open';     // 图标打开样式
	this.class_loading = 'jqtree-loading';         // 加载中样式
	this.div_arrow = $('<div></div>');             // 箭头div
	this.div_icon = $('<div></div>');              // 图标div
	this.div_loading = $('<div></div>');           // 加载中div
	this.horiz = false;                            // 是否横向显示
	this.icon = null;                              // 节点自定义icon
	
	$.extend(this, options);
}
/**
 * extend from BaseTreeNode
 */
IconNode.prototype = new BaseNode();
/**
 * view()
 */
IconNode.prototype.view = function() {
	var _this = this;
	
	this.div_arrow.addClass(this.class_icon).addClass(this.class_arrow).addClass(this.class_arrow_close);
	this.div_icon.addClass(this.class_icon).addClass(this.class_icon_close);
	this.div_loading.addClass(this.class_loading);
	
	this.div_node.append(this.div_arrow);
	this.div_node.append(this.div_icon);
	
	if(this.icon) {
		this.div_icon.css('background', 'url('+this.icon+') no-repeat');
	}

	BaseNode.prototype.view.call(this);
	
	if(this.horiz) {
		this.div_node.addClass(this.class_node_horiz);
		
		this.parentNode.div_sub.addClass(this.class_sub_horiz);
		this.parentNode.div_sub.css('padding-left', this.indent * this.level);
		this.div_node.css('padding-left', '0');
		
		this.parentNode.div_sub.hover(function() {
			$(this).addClass(_this.class_hover);
		}, function() {
			$(this).removeClass(_this.class_hover);
		});
	}
}
/**
 * arrowClickBind()
 */
IconNode.prototype.arrowClickBind = function() {
	var _this = this;
	
	if(this.arrowClick) {
		this.div_arrow.click(function(event) {
			_this.arrowClick(_this, event);
		});
	}
}
/**
 * control()
 */
IconNode.prototype.control = function() {
	BaseNode.prototype.control.call(this);
	
	this.arrowClickBind();
}
/**
 * collapse()
 */
IconNode.prototype.collapse = function() {
	BaseNode.prototype.collapse.call(this);
	
	if(this.subNodes != 0) {
		this.div_arrow.removeClass(this.class_arrow_open).addClass(this.class_arrow_close);
		this.div_icon.removeClass(this.class_icon_open).addClass(this.class_icon_close);
	}
}
/**
 * expand()
 */
IconNode.prototype.expand = function() {
	BaseNode.prototype.expand.call(this);
	
	if(this.subNodes != 0) {
		this.div_arrow.removeClass(this.class_arrow_close).addClass(this.class_arrow_open);
		this.div_icon.removeClass(this.class_icon_close).addClass(this.class_icon_open);
	}
}
/**
 * success(data)
 */
IconNode.prototype.success = function(data) {
	var _this = this;
	
	this.div_loading.remove();
	
	if(data.length == 0) {
		this.div_arrow.removeClass(this.class_arrow_close);
		this.div_icon.removeClass(this.class_icon_close).addClass(this.class_icon_open);
		return;
	}
	
	BaseNode.prototype.success.call(this, data);
}
/**
 * createSubNode(o)
 */
IconNode.prototype.createSubNode = function(o) {
	return new IconNode(o);
}
/**
 * beforeSend()
 */
IconNode.prototype.beforeSend = function() {
	this.div_loading.css('background-position', this.indent * (this.level + 1) + 'px 0');
	this.div_node.after(this.div_loading);
}