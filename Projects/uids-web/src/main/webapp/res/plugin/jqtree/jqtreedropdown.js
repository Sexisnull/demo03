/**
 * jQuery jqTreeDropdown plugin
 * Version: 1.5
 * Company: 甘肃万维
 * Author: 王波
 * Date: 2011.01.16
 */
(function($) {
$.extend($.fn, {
	jqTreeDropdown: function(options) {
		$('head').append('<link rel="stylesheet" type="text/css" href="'+options.path+'skin/default/jqtree.css" />');

		$('head').append('<script type="text/javascript" src="'+options.path+'ajaxmodel.js"></script>');
		$('head').append('<script type="text/javascript" src="'+options.path+'xmlparser.js"></script>');
		$('head').append('<script type="text/javascript" src="'+options.path+'basetree.js"></script>');
		$('head').append('<script type="text/javascript" src="'+options.path+'icontree.js"></script>');
		$('head').append('<script type="text/javascript" src="'+options.path+'checktree.js"></script>');
		$('head').append('<script type="text/javascript" src="'+options.path+'radiotree.js"></script>');
		$('head').append('<script type="text/javascript" src="'+options.path+'basenode.js"></script>');
		$('head').append('<script type="text/javascript" src="'+options.path+'iconnode.js"></script>');
		$('head').append('<script type="text/javascript" src="'+options.path+'checknode.js"></script>');
		$('head').append('<script type="text/javascript" src="'+options.path+'radionode.js"></script>');
		
		var div_jqtree = $('<div></div>');
		div_jqtree.addClass('jqtree-dropdown');
		$(document.body).append(div_jqtree);
		if($.fn.bgiframe) {
			div_jqtree.bgiframe();
		}
		div_jqtree.css('top', options.top);
		div_jqtree.css('left', options.left);
		div_jqtree.width(options.width);
		div_jqtree.height(options.height);
		
		$.extend(options, {
			container: div_jqtree,
			// 禁用单击事件，并阻止事件传播
			click: function(treeNode, event) {
				event.stopPropagation();
			},
			dblclick: function(treeNode) {
				treeNode.toggle();
			},
			arrowClick: function(treeNode, event) {
				event.stopPropagation();
				treeNode.toggle();
			}
		});
		
		// 阻止事件传播
		this.click(function(event) {
			event.stopPropagation();
			div_jqtree.show();
		});
		
		div_jqtree.click(function(event) {
			event.stopPropagation();
		});
		
		var jqtree = null;
		switch(options.treeType) {
			case 'checkbox':
				$.extend(options, {
					checkClick: function(treeNode) {
						treeNode.checkToggle();
						alert(treeNode.name);
					}
				});
				jqtree = new CheckTree(options);
				break;
			case 'radio':
				/*$.extend(options, {
					radioClick: function(treeNode, event) {
						event.stopPropagation();
					}
				});*/
				jqtree = new RadioTree(options);
				break;
			default:
				jqtree = new RadioTree(options);
				break;
		}
		jqtree.init();
		
		// 点击页面其他地方时层消失并取值
		$(document).click(function() {
			if(options.onDisappear) {
				options.onDisappear(jqtree);
			}
			
			div_jqtree.hide();
		});
		
		return jqtree;
	}
});
})(jQuery);