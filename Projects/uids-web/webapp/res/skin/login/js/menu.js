$.fn.menu = function(options) {
	var inputJq = $(this);
	var inputJqId = inputJq.attr('id');
	var menu;
	var menuId = inputJqId + '_menu';
	var exist = $('#' + menuId).size() > 0;

	var settings = {
		width : inputJq.outerWidth() - 2,
		height : null,
		maxHeight : null,
		tree : null,
		content : null,
		init : null,
		event : 'click',
		menuId : menuId
	};

	settings = $.extend(settings, options);

	var heightStyle = settings.height ? 'height:' + settings.height + 'px' : '';

	var menu;
	if (exist) {
		menu = $('#' + settings.menuId);
		menu.css({
			width : settings.width,
			height : settings.height,
			maxHeight : settings.maxHeight
		});
	} else {
		$('body').append('<div id="' + settings.menuId + '" class="menu" ' + 'style="width:' + settings.width + 'px;' + heightStyle + '"><div class="menu-content"></div></div>');
		menu = $('#' + settings.menuId);
	}
	var menuContent = menu.children('.menu-content');

	if (settings.tree) {
		menuContent.append('<ul id="' + settings.tree + '" class="ztree"></ul>');
	} else if (settings.content) {
		menuContent.html(settings.content);
	}

	if (settings.init) {
		var treeJq = $('#' + settings.tree);
		settings.init(menu, inputJq, treeJq);
	}
	//
	// if (inputJq.is('[readonly]')) {
	// return;
	// }

	if (!exist) {
		$("body").bind("mousedown", onMouseDownBody);
		inputJq.css('cursor', 'pointer').focus(function() {
			$(this).blur();
		});

		inputJq.on(settings.event, toggleMenu);
		
		$(window).resize(hideMenu);
	}

	function toggleMenu() {
		if (menu.is(':hidden')) {
			showMenu();
		} else {
			hideMenu();
		}
	}

	function showMenu() {
		if (settings.height && menu.outerHeight() > settings.height) {
			menu.height(settings.height);
		} else 	if (settings.maxHeight && menu.outerHeight() > settings.maxHeight) {
			menu.height(settings.maxHeight);
		} else {
			menu.height('auto');
		}
		
		var inputJqOffset = inputJq.offset();
		var documentScrollTop = $(document).scrollTop();

		var left = inputJqOffset.left;
		menu.css('left', left + 'px');
		var top = inputJqOffset.top + inputJq.outerHeight() + 1;
		var bottom = $(window).height() - inputJqOffset.top + 1;
		
		var winHeight = $(window).height();
		var upHeight = documentScrollTop + inputJqOffset.top - 1; 
		var downHeight = documentScrollTop + winHeight - top; 
		var menuHeight = menu.outerHeight();
		
		if (menuHeight < downHeight) { 
			menu.css('bottom', 'auto');
			menu.css('top', top + 'px');
		} else if (menuHeight < upHeight ) { 
			menu.css('top', 'auto');
			menu.css('bottom', bottom + 'px');
		} else {
			if (downHeight > upHeight) {
				menu.outerHeight(downHeight - 5);
				menu.css('bottom', 'auto');
				menu.css('top', top + 'px');
			} else { 
				menu.outerHeight(upHeight - 5);
				menu.css('top', 'auto');
				menu.css('bottom', bottom + 'px');
			}
		}

		if (menu.is(':hidden')) {
			menu.slideDown(50, function() {
				menu.children('.menu-content').css('overflow', 'auto');
			});
		}

	}

	function hideMenu() {
		menu.children('.menu-content').css('overflow', '');
		menu.fadeOut(50);
	}

	
	function onMouseDownBody(event) {
		if (event.target.id != inputJqId && $(event.target).closest('#' + inputJqId).size() == 0 && event.target.id != settings.menuId && $(event.target).closest('#' + settings.menuId).length == 0) {
			hideMenu();
		}
	}
};