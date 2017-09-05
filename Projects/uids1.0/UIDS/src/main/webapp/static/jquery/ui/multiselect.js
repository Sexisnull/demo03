$.fn.multiselect = function(op) {
	var selecter = $(this);

	var settings = {
		width : selecter.outerWidth() - 2,
		options : {
			values : null,
			texts : null
		},
		selected : {
			values : null,
			texts : null
		},
		nondeletable : {
			values : null
		},
		target : '',
		callback : function() {
		}
	};

	settings = $.extend(settings, op);

	var optionValues = settings.options.values ? settings.options.values.split(',') : new Array();
	var optionTexts = settings.options.texts ? settings.options.texts.split(',') : new Array();
	var selectedValues = settings.selected.values ? settings.selected.values.split(',') : new Array();
	var selectedTexts = settings.selected.texts ? settings.selected.texts.split(',') : new Array();
	var nonDeletableValues = settings.nondeletable.values ? settings.nondeletable.values.split(',') : new Array();

	var map = {};
	$.each(optionValues, function(i, value) {
		map[value] = optionTexts[i];
	});

	$.each(selectedValues, function(i, value) {
		map[value] = selectedTexts[i];
		if (contains(nonDeletableValues, value)) {
			selecter.append('<li><span class="multiselect-selected" val="' + value + '">' + map[value] + '</span></li>');
		} else {
			selecter.append('<li><i class="multiselect-remove icon-remove"></i><span class="multiselect-selected" val="' + value + '">' + map[value] + '</span></li>');
		}
	});

	// selecter
	// .append('<li class="multiselect-input"><input type="text"
	// readonly="readonly" autocomplete="off" /></li>');
	selecter.append('<div class="multiselect-clear"></div>');

	updateValue();

	var menuId = 'menu_' + parseInt(Math.random() * 1000);
	var menuContent = '<ul id="' + menuId + '" class="multiselect-options">';
	$.each(map, function(value, text) {
		menuContent += '<li class="multiselect-option" val="' + value + '">' + text + '</li>';
	});
	menuContent += '</ul>';

	selecter.menu({
		width : settings.width,
		height : settings.height,
		maxHeight : settings.maxHeight,
		content : menuContent,
		init : function() {
			$.each(selectedValues, function(i, value) {
				$('#' + menuId).find('li[val=' + value + ']').hide();
			});

			$('#' + menuId + ' li').click(function() {
				var text = $(this).text();
				var value = $(this).attr('val');
				var ie8FixChar = $.browser.msie && $.browser.version < 9 ? '<span style="font-family: \'hanweb\'">&#xe027;</span>' : '';
				selecter.children('.multiselect-clear').before('<li><i class="multiselect-remove icon-remove">' + ie8FixChar + '</i><span class="multiselect-selected" val="' + value + '">' + text + '</span></li>');
				$(this).hide();

				updateMenuPosition();
				updateValue();
			});
		}
	});

	/*
	 * 事件绑定
	 * 
	 */

	selecter.add('.menu').on('selectstart', function() {
		return false;
	});

	// selecter.focus(function() {
	// selecter.find(':text').focus();
	// }).click(function() {
	// selecter.find(':text').focus();
	// });
	selecter.delegate('.multiselect-remove', 'click', function(event) {
		var menu = $(this).parent().parent();
		$('.menu:not(#' + menu.attr('id') + '_menu)').hide();
		var value = $(this).next().attr('val');
		$('#' + menuId).find('li[val=' + value + ']').show();
		$(this).parent().hide(50, function() {
			$(this).remove();
			updateMenuPosition();
			updateValue();
		});
		event.stopPropagation();
	}).delegate('.multiselect-remove', 'mousedown', function(event) {
		event.stopPropagation();
	});

	function updateMenuPosition() {
		var top = selecter.offset().top + selecter.outerHeight() + 1;
		var bottom = $('#' + selecter.attr('id') + '_menu').css('bottom');

		if (bottom == 'auto') {
			$('#' + selecter.attr('id') + '_menu').css('top', top + 'px');
		} else {
			$('#' + selecter.attr('id') + '_menu').css('bottom', bottom);
		}
	}

	function updateValue() {
		var returnArray = new Array();
		selecter.find('.multiselect-selected').each(function() {
			returnArray.push($(this).attr('val'));
		});
		$('#' + settings.target).val(returnArray.join(','));
		settings.callback();
	}

	function contains(arr, str) {
		var i = arr.length;
		while (i--) {
			if (arr[i] === str) {
				return true;
			}
		}
		return false;
	}

};