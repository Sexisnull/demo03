/**
 * jQuery jqTree plugin
 * Version: 1.5
 * Company: 甘肃万维
 * Author: 王波
 * Date: 2011.03.16
 */
(function($) {
$.extend($.fn, {
	jqTree: function(options) {
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
		
		$.extend(options, {
			container: this,
			dblclick: function(node) {
				node.toggle();
			},
			arrowClick: function(node, event) {
				event.stopPropagation();
				node.toggle();
			}
		});
		var tree = null;
		switch(options.treeType) {
			case 'normal':
				tree = new IconTree(options);
				break;
			case 'checkbox':
				$.extend(options, {
					checkClick: function(node, event) {
						event.stopPropagation();
						node.checkToggle();
					}
				});
				tree = new CheckTree(options);
				break;
			case 'radio':
				$.extend(options, {
					radioClick: function(node, event) {
						event.stopPropagation();
					}
				});
				tree = new RadioTree(options);
				break;
			default:
				tree = new IconTree(options);
				break;
		}
		tree.init();
		return tree;
	}
});
})(jQuery);