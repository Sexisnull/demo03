/**
 * RadioNode
 */
function RadioNode(options) {
	IconNode.call(this, options);
	
	this.radioClick = null;                                             // 单选框单击事件处理函数
	this.checked = false;                                               // 单选框是否选中
	this.radio = true;                                                  // 节点是否具有单选框，默认值true
	this.class_radio = 'jqtree-radio';                                  // 单选框div样式
	this.div_radio = $('<div></div>');                                  // 单选框div
	this.input_radio = $('<input type="radio" name="jqtreeradio" />');  // 单选框input
	
	$.extend(this, options);
}
/**
 * extend from IconTreeNode
 */
RadioNode.prototype = new IconNode();
/**
 * view()
 */
RadioNode.prototype.view = function() {
	this.input_radio.attr('value', this.id);
	this.div_radio.addClass(this.class_icon).addClass(this.class_radio);
	this.div_radio.append(this.input_radio);
	
	if(this.checked) {
		this.input_radio.attr('checked', true);
		this.tree.checkedNode = this;
	}
	
	IconNode.prototype.view.call(this);
	
	if(this.radio) {
		this.div_arrow.after(this.div_radio);
	}
}
/**
 * checkClickBind()
 */
RadioNode.prototype.checkClickBind = function() {
	var _this = this;
	
	this.input_radio.click(function(event) {
		_this.tree.checkedNode = _this;
		
		if(_this.radioClick) {
			_this.radioClick(_this, event);
		}
	});
}
/**
 * control()
 */
RadioNode.prototype.control = function() {
	IconNode.prototype.control.call(this);
	
	this.checkClickBind();
}
/**
 * createSubNode(o)
 */
RadioNode.prototype.createSubNode = function(o) {
	return new RadioNode(o);
}