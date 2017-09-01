var pageTitleHtml;
var pageTitleText;

/**
 * 设置事件
 */
function initEvent() {
	if (top.menu && !top.compactModal) {
		$('.switchmenu').off('click').removeClass('btn').find('i').hide();
	} else {
		$(function() {
			$('#page-title').html(pageTitleHtml);
			$('.switchmenu').addClass('btn').find('i').show();
			$('.switchmenu').click(function(e) {
				var topPos = top.$('#header').outerHeight() + $(this).offset().top + $(this).height() - document.documentElement.scrollTop + 2;
				var leftPos = $(this).offset().left;
				top.$('#menu-wrap').slideToggle(50).css({
					top : topPos,
					left : leftPos
				});
				e.stopPropagation();
			}).on('selectstart', function(e) {
				return false;
				e.stopPropagation();
			});

			$(document).click(function() {
				top.$('#menu-wrap.compact').hide();
			});
		});
	}
}

$(function() {
	top.$.cookie('pageUrl', location.href, {path: top.cookiePath});

	// 返回顶端
	$.scrollUp({
		topDistance : 70,
		scrollText : '',
		scrollImg : true
	});

	// IE6的position:fixed修复
	$('#scrollUp').ie6fixed();

	pageTitleHtml = $('#page-title').html();
	pageTitleText = $('#page-title').text();

	initEvent();

	$(window).resize(initEvent);
});