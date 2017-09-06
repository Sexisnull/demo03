/**
 * CheckNode
 */
function CheckNode(options) {
	IconNode.call(this, options);
	
	this.checkClick = null;                            // 复选框单击事件处理函数
	this.checked = false;                              // 复选框是否选中
	this.halfChecked = false;                          // 复选框是否半选中
	this.checkbox = true;                              // 节点是否具有复选框，默认值true
	this.checkInteractParent = true;                   // 选中是否影响父节点，默认值true
	this.checkInteractSub = true;                      // 选中是否影响子节点，默认值true
	this.uncheckInteractParent = true;                 // 取消选中是否影响父节点，默认值true
	this.uncheckInteractSub = true;                    // 取消选中是否影响子节点，默认值true
	this.class_checked_true = 'jqtree-checked-true';   // 复选框选中样式
	this.class_checked_false = 'jqtree-checked-false'; // 复选框未选中样式
	this.class_checked_half = 'jqtree-checked-half';   // 复选框半选中样式
	this.div_checkbox = $('<div></div>');              // 复选框div
	
	$.extend(this, options);
}
/**
 * extend from IconNode
 */
CheckNode.prototype = new IconNode();
/**
 * view()
 */
CheckNode.prototype.view = function() {
	this.div_checkbox.addClass(this.class_icon);
	if(this.checked) {
		this.div_checkbox.addClass(this.class_checked_true);
	}
	else {
		this.div_checkbox.addClass(this.class_checked_false);
	}
	
	IconNode.prototype.view.call(this);
	
	if(this.checkbox) {
		this.div_arrow.after(this.div_checkbox);
	}
}
/**
 * checkClickBind()
 */
CheckNode.prototype.checkClickBind = function() {
	var _this = this;
	
	if(this.checkClick) {
		this.div_checkbox.click(function(event) {
			_this.checkClick(_this, event);
		});
	}
}
/**
 * control()
 */
CheckNode.prototype.control = function() {
	IconNode.prototype.control.call(this);
	
	this.checkClickBind();
}
/**
 * checkToggle()
 */
CheckNode.prototype.checkToggle = function() {
	if(this.checked) {
		this.uncheck();

		if(this.uncheckInteractSub) {
			this.interactSubNodes();
		}
		
		if(this.parentNode && this.uncheckInteractParent) {
			this.parentNode.interactedBySubNodes();
		}
	}
	else {
		this.check();

		if(this.checkInteractSub) {
			this.interactSubNodes();
		}
		
		if(this.parentNode && this.checkInteractParent) {
			this.parentNode.interactedBySubNodes();
		}
	}
}
/**
 * interactSubNodes()
 */
CheckNode.prototype.interactSubNodes = function() {
	var _this = this;
	
	$.each(this.subNodes, function(i, o) {
		if(_this.checked) {
			o.check();
			
			if(o.checkInteractSub) {
				o.interactSubNodes();
			}
		}
		else {
			o.uncheck();
			
			if(o.uncheckInteractSub) {
				o.interactSubNodes();
			}
		}
	});
}
/**
 * interactedBySubNodes()
 */
CheckNode.prototype.interactedBySubNodes = function() {
	var checkedCount = 0;
	var halfCheckedCount = 0;
	
	$.each(this.subNodes, function(i, o) {
		if(o.checked) {
			checkedCount++;
		}
		if(o.halfChecked) {
			halfCheckedCount++;
		}
	});
	
	if(checkedCount == this.subNodes.length) {
		this.check();
		
		if(this.parentNode && this.checkInteractParent) {
			this.parentNode.interactedBySubNodes();
		}
	}
	else if(checkedCount == 0) {
		this.uncheck();
		
		if(this.parentNode && this.uncheckInteractParent) {
			this.parentNode.interactedBySubNodes();
		}
	}
	else {
		this.halfCheck();
		
		if(this.parentNode && this.checkInteractParent) {
			this.parentNode.interactedBySubNodes();
		}
	}
	
	if(halfCheckedCount > 0) {
		this.halfCheck();
		
		if(this.parentNode && this.checkInteractParent) {
			this.parentNode.interactedBySubNodes();
		}
	}
}
/**
 * check()
 */
CheckNode.prototype.check = function() {
	this.checked = true;
	this.halfChecked = false;
	this.div_checkbox.removeClass(this.class_checked_false).removeClass(this.class_checked_half).addClass(this.class_checked_true);
}
/**
 * uncheck()
 */
CheckNode.prototype.uncheck = function() {
	this.checked = false;
	this.halfChecked = false;
	this.div_checkbox.removeClass(this.class_checked_true).removeClass(this.class_checked_half).addClass(this.class_checked_false);
}
/**
 * halfCheck()
 */
CheckNode.prototype.halfCheck = function() {
	this.checked = false;
	this.halfChecked = true;
	this.div_checkbox.removeClass(this.class_checked_true).removeClass(this.class_checked_false).addClass(this.class_checked_half);
}
/**
 * createSubNode(o)
 */
CheckNode.prototype.createSubNode = function(o) {
	return new CheckNode(o);
}
/**
 * getCheckedNodes()
 */
CheckNode.prototype.getCheckedNodes = function() {
	if(this.checked && this.checkbox || this.halfChecked && this.checkbox) {
		this.tree.checkedNodes[this.tree.checkedNodes.length] = this;
	}
	
	$.each(this.subNodes, function(i, o) {
		o.getCheckedNodes();
	});
}