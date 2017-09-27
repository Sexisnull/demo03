(function($) {
	$.fn.tree = function(setting, nodes) {
		var treeId = $(this).attr("id");
		var tree = getTreeObj(treeId);
		if (tree == null) {
			var getFont = function getFont(treeId, node) {
				return node.font ? node.font : {};
			};
			var options = {
				view : {
					fontCss : getFont,
					showLine : false,
					nameIsHTML : true,
					selectedMulti : false
				},
				data : {
					simpleData : {
						enable : true
					}
				}
			};

			options = $.extend(options, setting);
			options.view.showLine = false;
			tree = $.fn.zTree.init(this, options, nodes);
		}
		var treeObj = bindMethod(tree);
		return treeObj;
	};
	function bindMethod(tree) {
		var treeObj = {
			getTree : function() {
				return tree;
			},
			expandAll : function(expand) {
				tree.expandAll(expand);
				return this;
			},
			addNodes : function(nodes) {
				if (typeof (nodes) == 'string') {
					nodes = $.parseJSON(nodes);
				}
				if (nodes) {
					var pNode, pid;
					if ($.isArray(nodes)) {
						$(nodes).each(function(element) {
							pid = this.pId;
							pNode = getNodeById(pid, tree);
							tree.addNodes(pNode, this);
						});
					} else {
						pid = nodes.pId;
						pNode = getNodeById(pid, tree);
						tree.addNodes(pNode, nodes);
					}
				}
				return this;
			},
			removeNode : function(ids) {
				if (ids) {
					var nodeIds = ids.split(',');
					$.each(nodeIds, function(index, nodeId) {
						var node = getNodeById(nodeId, tree);
						tree.removeNode(node);
					});
				}
				return this;
			},
			updateNode : function(nodes) {
				if (typeof (nodes) == 'string') {
					nodes = $.parseJSON(nodes);
				}
				if (nodes) {
					var currNode;
					if ($.isArray(nodes)) {
						$.each(nodes, function(index, node) {
							currNode = getNodeById(node.id, tree);
							currNode = $.extend(currNode, node);
							tree.updateNode(currNode);
						});
					} else {
						currNode = getNodeById(nodes.id, tree);
						currNode = $.extend(currNode, nodes);
						tree.updateNode(currNode);
					}
				}
				return this;
			},
			refresh : function(){
				tree.refresh();
			},
			refreshNode : function(id, refreshType, isSilent) {
				if (id) {
					var node = getNodeById(id, tree);
					if (node) {
						node.isParent = true;
						tree.reAsyncChildNodes(node, 'refresh', isSilent);
					}
				}
				return this;
			},
			getAttr : function(id, key){
				if (id) {
					var node = getNodeById(id, tree);
					if (node) {
						return node.attr[key];
					}
				}
			}
		};
		return treeObj;
	}
	function getTreeObj(obj) {
		return $.fn.zTree.getZTreeObj(obj);
	}
	function getNodeById(id, tree) {
		if (id == null) {
			return null;
		}
		return tree.getNodeByParam("id", id);
	}
})(jQuery);