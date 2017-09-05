/**
 * WWUIMenu
 */
function WWUIMenu(options) {
	// options
	this.url = null;
	this.dataType = 'JSON';
	this.timeout = 10000;
	this.ids = null;
	this.templates = null;
	this.change = null;
	this.finish = null;
	this.selected = null;
	

	this.rootItems = new Array();

	$.extend(this, options);

	//this.init();

	this.load();
}

/**
 * init()
 */
WWUIMenu.prototype.init = function() {
	
}

/**
 * load()
 */
WWUIMenu.prototype.load = function() {
	var _this = this;
	
	$.ajax({
		url: this.url,
		cache: false,
		async: true,
		type: 'POST',
		dataType: this.dataType,
		timeout: this.timeout,
		beforeSend: function() {
			
		},
		error: function() {
			console.log('WWUIMenu AJAX load error');
		},
		success: function(data) {
			
			if(_this.dataType == 'XML') {
				data = _this.xmlParse($(data).children().children());
			}

			_this.createMenu(data);
		}
	});
}

/**
 * createMenu()
 */
WWUIMenu.prototype.createMenu = function(data) {
	var _this = this;

	$.each(data, function(i, o) {
		
		$.extend(o, {
			menu: _this,
			parent: null,
			level: 0,
			template: _this.templates[0]
		});

		var item = new WWUIMenuItem(o);

		_this.rootItems[i] = item;
	});

	$.each(this.rootItems, function(i, o) {
		o.hideSub();
	});

	this.rootItems[0].showSub();

	if(this.finish) {
		this.finish();
	}
}

/**
 * xmlParse()
 */
WWUIMenu.prototype.xmlParse = function($XMLDocument) {
	var _this = this;
	
	var json = null;
	
	if($XMLDocument.length != 0) {
		json = new Array();
	}
	
	$XMLDocument.each(function(i, o) {
		json[i] = new Object();
		
		var attrs = o.attributes;
		
		for(var j = 0; j < attrs.length; j++) {
			var name = attrs[j].name;
			json[i][name] = attrs[j].value;
			
			// 对布尔值的处理，从xml获得为string类型
			if(json[i][name] == 'true') {
				json[i][name] = true;
			}
			else if(json[i][name] == 'false') {
				json[i][name] = false;
			}
		}
		
		var nodes = _this.xmlParse($(o).children()); // 递归转换下级节点xml对象
		
		json[i].nodes = nodes; // nodes属性赋值
	});
	return json;
}










/**
 * WWUIMenuItem
 */
function WWUIMenuItem(options) {
	this.id = null;
	this.name = null;
	this.url = null;
	this.nodes = null;
	this.template = null;
	this.parent = null;
	this.subItems = new Array();
	this.menu = null;
	this.level = 0;

	$.extend(this, options);
	
	this.isParent = !!this.nodes;

	this.init();

	if(this.isParent) {
		this.loadSub();
	}

	this.bind();
}

/**
 * init()
 */
WWUIMenuItem.prototype.init = function() {
	$.template('menuItem', this.template);
	this.item = $.tmpl('menuItem', this);	
	if(this.level==2){
		$(this.parent.item).find("ul").append(this.item);
	}else{
		$(this.menu.ids[this.level]).append(this.item);
	}
	
}

/**
 * bind()
 */
WWUIMenuItem.prototype.bind = function() {
	var _this = this;

	this.item.click(function(e) {
		if(_this.menu.click) {
			_this.menu.click(_this, e);
		}
	});
}

/**
 * hideSub()
 */
WWUIMenuItem.prototype.hideSub = function() {
	$.each(this.subItems, function(i, o) {
		o.item.hide();
		o.hideSub();
	});
}

/**
 * showSub()
 */
WWUIMenuItem.prototype.showSub = function() {
	$.each(this.subItems, function(i, o) {
		o.item.show();
	});

	if(this.subItems[0]) {
		this.subItems[0].showSub();

		if(this.menu.selected) {
			this.menu.selected(this);
		}
	}
	else {
		
		if(this.menu.change) {
			this.menu.change(this);
		}

		if(this.menu.selected) {
			this.menu.selected(this);
		}
	}
}

/**
 * loadSub()
 */
WWUIMenuItem.prototype.loadSub = function() {
	var _this = this;

	$.each(this.nodes, function(i, o) {
		$.extend(o, {
			menu: _this.menu,
			parent: _this,
			level: _this.level + 1,
			template: _this.menu.templates[_this.level + 1]
		});

		var subItem = new WWUIMenuItem(o);

		_this.subItems[i] = subItem;
	});
}
