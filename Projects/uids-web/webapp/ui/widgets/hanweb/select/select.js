$.fn.select = function(arg1, arg2) {

	// API
	if (arg2) {
		switch (arg1) {
		case 'setValue':
			var select = $(this);
			var selectId = select.attr('id');
			var value = arg2;
			$('#' + selectId).val(arg2);// 设值
			var selectWidgetId = $('#' + selectId).parent().attr('id');
			var menu = $('#' + selectWidgetId + '_menu');
			menu.find('.selected').removeClass('selected');
			var option = menu.find('.select-option[val=' + arg2 + ']');
			option.addClass('selected');// 选中
			$('#' + selectWidgetId).find('.select-text').text(option.text());// 设字
			break;
		}
		return;
	}

	$(this).each(function() {
		var content = '';

		var select = $(this);
		var selectId = select.attr('id');
		selectId = selectId ? selectId : Math.round(Math.random() * 10000);
		var selectWidgetId = 'select_' + selectId;
		var selectName = select.attr('name');
		selectName = selectName ? selectName : selectId;
		var selectStyle = select.attr('style');
		var selectedText = '';
		var selectedValue = '';
		var readOnly = select.is('[readonly]');
		var disabled = select.is('[disabled]');
		var disabledStyle = disabled || readOnly ? ' disabled' : '';
		var onChange = select.attr('onchange');
		var selectedInitVal = select.attr('data-value');
		selectedInitVal = selectedInitVal ? selectedInitVal : select.val();
		var height = select.attr('height');

		select.children('option, li').each(function() {
			var selected = '';
			if (selectedInitVal == $(this).val()) {
				selected = ' selected';
				selectedText = $(this).text();
				selectedValue = $(this).val();
			}
			content += '<li class="select-option' + selected + '" val="' + $(this).val() + '">' + $(this).html() + '</li>';
		});
		content = '<ul class="select-options">' + content + '</ul>';

		select.replaceWith('<div id="' + selectWidgetId + '" class="select input-text' + disabledStyle + '" style="' + selectStyle + '"><span class="select-text">' + selectedText + '</span><i class="icon-chevron-down"></i><input id="' + selectId + '" name="' + selectName + '" type="hidden" class="select-val" value="' + selectedValue + '"/></div>');

		if (!readOnly && !disabled) {
			var $select = $('#' + selectWidgetId);
			$select.menu({
				height : height,
				content : content,
				init : function(menu, selectObj) {
					menu.find('li').click(function() {
						menu.find('.selected').removeClass('selected');
						$(this).addClass('selected');
						selectObj.children('.select-text').text($(this).text());
						selectObj.children('.select-val').val($(this).attr('val'));
						menu.children('.menu-content').css('overflow', '');
						menu.fadeOut(50);
						if (onChange) {
							onChange = onChange.replace(/this/g, "$('#" + selectWidgetId + " > .select-val')[0]");
							try {
								eval(onChange);
							} catch (e) {
								alert(e.message);
							}
						}
					});
				}
			});
		}
	});
};

$(function() {
	// .lazy和表格翻页，不自动使用select控件
	$('select:not(.lazy,.pagination-page-list), .select').select();
});