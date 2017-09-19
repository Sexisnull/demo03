<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<title>组织选择</title>
<link type="text/css" rel="stylesheet" href="${ctx}/res/ui/css/roleGlobal.css"/>
<script type="text/javascript" src="${ctx}/res/plugin/jquery/jquery-1.8.3.min.js"></script>
<script type="text/javascript" src="${ctx }/res/skin/default/js/jquery-ui.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/login/js/jquery.draggable.js"></script>

<link rel="stylesheet" href="${ctx}/res/skin/default/css/tabs.css"/>
<script type="text/javascript" src="${ctx}/res/skin/login/js/jquery.parse.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/panel.css"/>
<script type="text/javascript" src="${ctx}/res/skin/login/js/jquery.panel.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/login/js/jquery.tabs.js"></script>
<link rel="stylesheet" href="${ctx}/res/ui/css/grouptree.css"/>
<script type="text/javascript" src="${ctx}/res/skin/login/js/jquery.tree.js"></script>
<script type="text/javascript" src="${ctx}/res/skin/default/js/jquery.hotkeys-0.7.9.min.js"></script>

<script>
	var orgType = '${orgType}';
	var userEnable = orgType.indexOf('u') == -1 ? false : true;
	var groupEnable = orgType.indexOf('g') == -1 ? false : true;
	var roleEnable = orgType.indexOf('r') == -1 ? false : true;

	var selectedUsers = {}; //已选用户
	var selectedGroups = {}; //已选机构
	var selectedRoles = {}; //已选角色

	/**
	 * 加载用户数据
	 * 
	 * @param currentId
	 * @returns
	 */
	function loadUserData(currentId) {
		$('#usersselect .selectsource').empty();
		console.log("currentid"+currentId);
		$.ajax({
			type : "POST",
			url : "${ctx}/complat/user_load",
			dataType : 'json',
			data : 'id=' + currentId + "&roleid=${roleid}",
			success : function(jsonData) {
				var pid = jsonData.pid;
				console.log(jsonData);
				console.log(pid);
				if (pid != null) {
					$('#usersselect .selectsource').append('<li class="reply" pid="'+pid+'">... 返回上一层</li>');
				}

				var groups = jsonData.groups;
				var users = jsonData.users;

				var id;
				var text;
				var ic;

				$.each(groups, function(index, group) {
					id = group.id;
					text = group.text;
					ic = group.ic;

					var disableTitle = '';
					var disableBtn = '';
					if (selectedGroups[id]) {
						disableTitle = ' style="color:#CCC;"';
						disableBtn = ' style="background-color:#CCC;"';
					}

					var groupLi = '<li class="option group" id="'+id+'"><i class="icon-folder-close"></i><span class="optionname"'+disableTitle+'>' + text + '</span>';

					if (ic) {
						groupLi += ' <span class="ic-wrap">&lt;<span class="ic">' + ic + '</span>&gt;</span>';
					}

					groupLi += '<div class="checkbtn"' + disableBtn + '><i class="icon-check"></i>添加</div></li>';
					$('#usersselect .selectsource').append(groupLi);
				});

				$.each(users, function(index, users) {
					id = users.id;
					text = users.text;
					ic = users.ic;
					
					var disableTitle = '';
					var disableBtn = '';
					if (selectedUsers[id]) {
						disableTitle = ' style="color:#CCC;"';
						disableBtn = ' style="background-color:#CCC;"';
					}

					var userLi = '<li class="option user" id="'+id+'"><i class="icon-user-3"></i><span class="optionname"'+disableTitle+'>' + text + '</span>';

					if (ic) {
						userLi += ' <span class="ic-wrap">&lt;<span class="ic">' + ic + '</span>&gt;</span>';
					}

					userLi += '<div class="checkbtn"' + disableBtn + '><i class="icon-check"></i>添加</div></li>';

					$('#usersselect .selectsource').append(userLi);
				});
			}

		});
	}

	/**
	 * 加载角色数据
	 * 
	 * @returns
	 */
	function loadRoleData() {
		$.ajax({
			type : "POST",
			url : "${ctx}/complat/role_load",
			dataType : 'json',
			success : function(jsonData) {
				var roles = jsonData.roles;
				var id;
				var text;
				
				var disableTitle = '';
				var disableBtn = '';
				if (selectedRoles[id]) {
					disableTitle = ' style="color:#CCC;"';
					disableBtn = ' style="background-color:#CCC;"';
				}

				$.each(roles, function(index, role) {
					id = role.id;
					text = role.text;
					$('#roleselect .selectsource').append('<li class="option role" id="' + id + '"><i class="icon-vcard-2"></i><span class="optionname"'+disableTitle+'>' + text + '</span><div class="checkbtn"' + disableBtn + '><i class="icon-check"></i>添加</div></li>');
				});
			}
		});
	}

	$(function() {
		// 选项卡
		$('#orgselect-source').tabs({
			plain : true,
			border : false
		});
		
		
		var button = $(".ui-button-text");
		
		if (userEnable) {
			loadUserData(0); // 加载用户数据

			// 双击“返回上一层”，加载上一层数据
			$('#usersselect .selectsource').on('dblclick', '.reply', function() {
				loadUserData($('.reply').attr('pid'));
			});
			
			// 检索机构和用户
			$('#user-search-btn').click(searchUser);
			$('#user-search').bind('keydown', 'return', searchUser);
		}
		
		if (groupEnable) {
			// 检索机构
			$('#group-search-btn').click(searchGroup);
			console.log($('#group-search-btn'));
			$('#group-search').bind('keydown', 'return', searchGroup);
		}
		
		if (roleEnable) {
			loadRoleData(); // 加载角色数据
			
			// 检索角色
			$('#role-search-btn').click(searchRole);
			$('#role-search').bind('keydown', 'return', searchRole);
		}
		// 加载结束

		// 双击机构，加载机构下数据
		$('#usersselect').on('dblclick', '.group', function() {
			loadUserData($(this).attr('id'));
		});

		// 点击“返回”，取消所有选中状态，取消批量操作按钮
		$('#orgselect-source').on('click', '.reply', function() {
			$('.selected').removeClass('selected');
			$('.batchadd, .batchremove').fadeOut('fast');
		});
		
		// 检索中点击“返回”，清空输入框，切换回原列表
		$('.searchresult').on('dblclick', '.reply', function() {
			var container = $(this).parent().parent().parent();
			container.find('.searchresult').hide();
			container.find('.selectsource').show();
			container.find('.searchtext').val('');
		});

		// 鼠标移到左侧列表时的效果
		$('.batchselect').on('mouseenter', '.option:not(.selected)', function() {
			$(this).addClass('hover');
		});

		$('.batchselect').on('mouseleave', '.option:not(.selected)', function() {
			$(this).removeClass('hover');
		});
		
		$('.batchselect').on('mouseenter', '.option', function(e) {
			if (e.shiftKey || e.ctrlKey || $('.selected').size() > 1) {
				return false;
			}
			$(this).find('.checkbtn').show();
		});

		$('.batchselect').on('mouseleave', '.option', function() {
			$(this).find('.checkbtn').hide();
		});

		$('.batchselect').on('selectstart', function() {
			return false;
		});

		// 点击左侧列表
		$('.batchselect').on('click', '.option', function(e) {
			var currentBatchselect = $(this).closest('.batchselect');
			$('.batchselect').not(currentBatchselect).find('.selected').removeClass('selected');
			
			// 单选
			if ($('.selected').size() <= 1) {
				$(this).find('.checkbtn').show();
			}
			
			// 多选
			if (e.shiftKey) {
				var lastSelectedIndex = currentBatchselect.find('.option').index($('.lastselect'));
				var currSelectedIndex = currentBatchselect.find('.option').index($(this));
				currentBatchselect.find('.selected:not(.lastselect)').removeClass('selected');
				var begin = lastSelectedIndex;
				var end = currSelectedIndex;
				if (lastSelectedIndex > currSelectedIndex) {
					begin = currSelectedIndex;
					end = lastSelectedIndex;
				}
				currentBatchselect.find('.option').slice(begin, end).filter(':visible').addClass('selected');
				$(this).addClass('selected');
			} else if (!e.ctrlKey) {
				currentBatchselect.find('.selected').removeClass('selected');
				$(this).addClass('selected');
			} else {
				$(this).toggleClass('selected');
			}
			$('.lastselect').removeClass('lastselect');
			$(this).removeClass('hover').addClass('lastselect');

			if (currentBatchselect.find('.selected').size() > 1) {
				if (currentBatchselect.is('.selecttarget')) {
					$('.batchremove').fadeIn('fast');
				} else {
					$('.batchadd').fadeIn('fast');
				}
			} else {
				$('.batchremove').fadeOut('fast');
				$('.batchadd').fadeOut('fast');
			}
		});

		$('.selectsource, .searchresult').on('click', '.checkbtn', function(e) {
			e.stopPropagation();

			var selectedLi = $(this).parent();
			var id = selectedLi.attr('id');

			var name = selectedLi.find('.optionname').text();
			var ic = selectedLi.find('.ic').text();

			if (selectedLi.is('.group')) {
				if (selectedGroups[id]) {
					return;
				}
				var targetLi = '<li id="'+id+'" class="option group"><i class="icon-users"></i><span class="optionname">' + name + '</span>';

				if (ic) {
					targetLi += ' <span class="ic-wrap">&lt;<span class="ic">' + ic + '</span>&gt;</span>';
				}

				targetLi += '<div class="checkbtn"><i class="icon-remove-sign"></i>移除</div></li>';

				if ($('.selecttarget .group').size() > 0) {
					$('.selecttarget .group:last').after(targetLi);
				} else {
					$('.selecttarget').prepend(targetLi);
				}
				$('.selectsource, .searchresult').find('.group#' + id).find('.optionname').css('color', '#CCC');
				selectedGroups[id] = {
					name : name,
					ic : ic
				};
			} else if (selectedLi.is('.user')) {
				if (selectedUsers[id]) {
					return;
				}
				var targetLi = '<li id="'+id+'" class="option user"><i class="icon-user-3"></i><span class="optionname">' + name + '</span>';

				if (ic) {
					targetLi += ' <span class="ic-wrap">&lt;<span class="ic">' + ic + '</span>&gt;</span>';
				}

				targetLi += '<div class="checkbtn"><i class="icon-remove-sign"></i>移除</div></li>';

				$('.selecttarget').append(targetLi);
				$('.selectsource, .searchresult').find('.user#' + id).find('.optionname').css('color', '#CCC');
				selectedUsers[id] = {
					name : name,
					ic : ic
				};
			} else {
				if (selectedRoles[id]) {
					return;
				}
				var targetLi = '<li id="'+id+'" class="option role"><i class="icon-vcard-2"></i><span class="optionname">' + name + '</span><div class="checkbtn"><i class="icon-remove-sign"></i>移除</div></li>';
				if ($('.selecttarget .role').size() > 0) {
					$('.selecttarget .role:last').after(targetLi);
				} else {
					$('.selecttarget').prepend(targetLi);
				}
				$('.selectsource, .searchresult').find('.role#' + id).find('.optionname').css('color', '#CCC');
				selectedRoles[id] = {
					name : name
				};
			}

			updateSize();

			$('.selectsource .selected').removeClass('selected');

			$(this).animate({
				width : 0
			}, 'fast', function() {
				$(this).hide().width(70);
				if (selectedLi.is('.group')) {
					$('.selectsource, .searchresult').find('.group#' + id).children('.checkbtn').css('background-color', '#CCC');
				} else if (selectedLi.is('.user')) {
					$('.selectsource, .searchresult').find('.user#' + id).children('.checkbtn').css('background-color', '#CCC');
				} else {
					$('.selectsource, .searchresult').find('.role#' + id).children('.checkbtn').css('background-color', '#CCC');
				}
			});
		});

		$('.selecttarget').on('click', '.checkbtn', function(e) {
			var selectedLi = $(this).parent();
			var id = selectedLi.attr('id');

			if (selectedLi.is('.group')) {
				delete selectedGroups[id];
				$('.selectsource, .searchresult').find('.group#' + id).find('.optionname').css('color', '');
				$('.selectsource, .searchresult').find('.group#' + id).children('.checkbtn').css('background-color', '');
			} else if (selectedLi.is('.user')) {
				delete selectedUsers[id];
				$('.selectsource, .searchresult').find('.user#' + id).find('.optionname').css('color', '');
				$('.selectsource, .searchresult').find('.user#' + id).children('.checkbtn').css('background-color', '');
			} else {
				delete selectedRoles[id];
				$('.selectsource, .searchresult').find('.role#' + id).find('.optionname').css('color', '');
				$('.selectsource, .searchresult').find('.role#' + id).children('.checkbtn').css('background-color', '');
			}
			updateSize();

			selectedLi.animate({
				'margin-left' : '-=100',
				opacity : '0.1'
			}, 'fast', function() {
				$(this).css('visibility', 'hidden').slideUp('fast', function() {
					$(this).remove();
				});
			});

			e.stopPropagation();
		});

		$(document).bind('keydown', 'ctrl+a', function(e) {
			var batchselect = $('.lastselect').closest('.batchselect');
			if (batchselect.size() > 0) {
				batchselect.find('.option:visible').removeClass('hover').addClass('selected');
				if (batchselect.is('.selecttarget')) {
					$('.batchremove').fadeIn('fast');
				} else {
					$('.batchadd').fadeIn('fast');
				}
			}
			return false;
		});

		$(document).bind('keydown', function(e) {
			if (e.shiftKey || e.ctrlKey) {
				$('.checkbtn').hide();
			}
		});

		$('.batchadd').click(function() {
			var userLis = new Array();
			var groupLis = new Array();
			var roleLis = new Array();
			$('.selectsource, .searchresult').find('.selected').each(function() {
				var selectedLi = $(this);
				var id = selectedLi.attr('id');

				var name = selectedLi.find('.optionname').text();
				var ic = selectedLi.find('.ic').text();
				var targetLi;

				if (selectedLi.is('.group')) {
					if (selectedGroups[id]) {
						return;
					}

					targetLi = '<li id="'+id+'" class="option group"><i class="icon-users"></i><span class="optionname">' + name + '</span>';

					if (ic) {
						targetLi += ' <span class="ic-wrap">&lt;<span class="ic">' + ic + '</span>&gt;</span>';
					}

					targetLi += '<div class="checkbtn"><i class="icon-remove-sign"></i>移除</div></li>';

					groupLis.push(targetLi);

					$('.selectsource, .searchresult').find('.group#' + id).find('.optionname').css('color', '#CCC');
					selectedGroups[id] = {
						name : name,
						ic : ic
					};
				} else if (selectedLi.is('.user')) {
					if (selectedUsers[id]) {
						return;
					}

					targetLi = '<li id="' + id + '" class="option user"><i class="icon-user-3"></i><span class="optionname">' + name + '</span>';

					if (ic) {
						targetLi += ' <span class="ic-wrap">&lt;<span class="ic">' + ic + '</span>&gt;</span>';
					}

					targetLi += '<div class="checkbtn"><i class="icon-remove-sign"></i>移除</div></li>';

					userLis.push(targetLi);

					$('.selectsource, .searchresult').find('.user#' + id).find('.optionname').css('color', '#CCC');
					selectedUsers[id] = {
						name : name,
						ic : ic
					};
				} else {
					if (selectedRoles[id]) {
						return;
					}
					roleLis.push('<li id="'+id+'" class="option role"><i class="icon-vcard-2"></i><span class="optionname">' + name + '</span><div class="checkbtn"><i class="icon-remove-sign"></i>移除</div></li>');
					$('.selectsource, .searchresult').find('.role#' + id).find('.optionname').css('color', '#CCC');
					selectedRoles[id] = {
						name : name
					};
				}

				if (selectedLi.is('.group')) {
					$('.selectsource, .searchresult').find('.group#' + id).children('.checkbtn').css('background-color', '#CCC');
				} else if (selectedLi.is('.user')) {
					$('.selectsource, .searchresult').find('.user#' + id).children('.checkbtn').css('background-color', '#CCC');
				} else {
					$('.selectsource, .searchresult').find('.role#' + id).children('.checkbtn').css('background-color', '#CCC');
				}

			}).removeClass('selected');

			if ($('.selecttarget .group').size() > 0) {
				$('.selecttarget .group:last').after(groupLis.join(''));
			} else {
				$('.selecttarget').prepend(groupLis.join(''));
			}

			if ($('.selecttarget .role').size() > 0) {
				$('.selecttarget .role:last').after(roleLis.join(''));
			} else {
				$('.selecttarget').prepend(roleLis.join(''));
			}

			$('.selecttarget').append(userLis.join(''));

			updateSize();

			$(this).fadeOut('fast');
		});

		$('.batchremove').click(function() {
			$('.selecttarget .selected').each(function() {
				var selectedLi = $(this);
				var id = selectedLi.attr('id');

				if (selectedLi.is('.group')) {
					delete selectedGroups[id];
					$('.selectsource, .searchresult').find('.group#' + id).find('.optionname').css('color', '');
					$('.selectsource, .searchresult').find('.group#' + id).children('.checkbtn').css('background-color', '');
				} else if (selectedLi.is('.user')) {
					delete selectedUsers[id];
					$('.selectsource, .searchresult').find('.user#' + id).find('.optionname').css('color', '');
					$('.selectsource, .searchresult').find('.user#' + id).children('.checkbtn').css('background-color', '');
				} else {
					delete selectedRoles[id];
					$('.selectsource, .searchresult').find('.role#' + id).find('.optionname').css('color', '');
					$('.selectsource, .searchresult').find('.role#' + id).children('.checkbtn').css('background-color', '');
				}
			});
			updateSize();

			$('.selecttarget .selected').animate({
				'margin-left' : '-=100',
				opacity : '0.1'
			}, 'fast', function() {
				$(this).css('visibility', 'hidden').slideUp('fast', function() {
					$(this).remove();
				});
			});

			$(this).fadeOut('fast');
		});
		
		/**
		 * 检索用户
		 */
		function searchUser(e) {
			e.stopPropagation();
			
			var searchText = $('#user-search').val();
			if (searchText == '') {
				var container = $('#usersselect');
				container.find('.searchresult').hide();
				container.find('.selectsource').show();
				return;
			}

			$('#usersselect .selectsource').hide();
			$('#usersselect .searchresult').empty().show();
			$.ajax({
				url : '${ctx}/complat/user_search',
				data : {keyword:$('#user-search').val(),roleid:"${roleid}"},
				dataType: 'json',
				type : 'post',
				success : function(jsonData) {
					$('#usersselect .searchresult').append('<li class="reply">... 返回</li>');
					
					var groups = jsonData.groups;
					var users = jsonData.users;

					var id;
					var text;
					var ic;

					$.each(groups, function(index, group) {
						id = group.id;
						text = group.text;
						ic = group.ic;

						var disableTitle = '';
						var disableBtn = '';
						if (selectedGroups[id]) {
							disableTitle = ' style="color:#CCC;"';
							disableBtn = ' style="background-color:#CCC;"';
						}

						var groupLi = '<li class="option group" id="'+id+'"><i class="icon-folder-close"></i><span class="optionname"'+disableTitle+'>' + text + '</span>';

						if (ic) {
							groupLi += ' <span class="ic-wrap">&lt;<span class="ic">' + ic + '</span>&gt;</span>';
						}

						groupLi += '<div class="checkbtn"' + disableBtn + '><i class="icon-check"></i>添加</div></li>';
						$('#usersselect .searchresult').append(groupLi);
					});

					$.each(users, function(index, users) {
						id = users.id;
						text = users.text;
						ic = users.ic;

						var disableTitle = '';
						var disableBtn = '';
						if (selectedUsers[id]) {
							disableTitle = ' style="color:#CCC;"';
							disableBtn = ' style="background-color:#CCC;"';
						}
						
						var userLi = '<li class="option user" id="'+id+'"><i class="icon-user-3"></i><span class="optionname"'+disableTitle+'>' + text + '</span>';

						if (ic) {
							userLi += ' <span class="ic-wrap">&lt;<span class="ic">' + ic + '</span>&gt;</span>';
						}

						userLi += '<div class="checkbtn"' + disableBtn + '><i class="icon-check"></i>添加</div></li>';

						$('#usersselect .searchresult').append(userLi);
					});
				}
			});
		}
		
		/**
		 * 检索机构
		 */
		function searchGroup(e) {
			e.stopPropagation();

			var searchText = $('#group-search').val();
			if (searchText == '') {
				var container = $('#groupselect');
				container.find('.searchresult').hide();
				container.find('.selectsource').show();
				return;
			}

			$('#groupselect .selectsource').hide();
			$('#groupselect .searchresult').empty().show();
			$.ajax({
				url : '${ctx}/complat/group_search',
				data : {keyword:$('#group-search').val(),roleid:"${roleid}"},
				dataType: 'json',
				type : 'post',
				success : function(jsonData) {
					$('#groupselect .searchresult').append('<li class="reply">... 返回</li>');
					
					var groups = jsonData.groups;

					var id;
					var text;
					var ic;

					$.each(groups, function(index, group) {
						id = group.id;
						text = group.text;
						ic = group.ic;

						var disableTitle = '';
						var disableBtn = '';
						if (selectedGroups[id]) {
							disableTitle = ' style="color:#CCC;"';
							disableBtn = ' style="background-color:#CCC;"';
						}

						var groupLi = '<li class="option group" id="'+id+'"><i class="icon-folder-close"></i><span class="optionname"'+disableTitle+'>' + text + '</span>';

						if (ic) {
							groupLi += ' <span class="ic-wrap">&lt;<span class="ic">' + ic + '</span>&gt;</span>';
						}

						groupLi += '<div class="checkbtn"' + disableBtn + '><i class="icon-check"></i>添加</div></li>';
						$('#groupselect .searchresult').append(groupLi);
					});
				}
			});
		}
		
		function searchRole(e) {
			e.stopPropagation();

			var searchText = $('#role-search').val();
			if (searchText == '') {
				var container = $('#rolesselect');
				container.find('.searchresult').hide();
				container.find('.selectsource').show();
				return;
			}

			$('#roleselect .selectsource').hide();
			$('#roleselect .searchresult').empty().show();
			$.ajax({
				type : "POST",
				url : '${ctx}/complat/role_search',
				data : {keyword:$('#role-search').val()},
				dataType : 'json',
				success : function(jsonData) {
					$('#roleselect .searchresult').append('<li class="reply">... 返回</li>');
					
					var roles = jsonData.roles;
					var id;
					var text;

					$.each(roles, function(index, role) {
						id = role.id;
						text = role.text;

						var disableTitle = '';
						var disableBtn = '';
						if (selectedRoles[id]) {
							disableTitle = ' style="color:#CCC;"';
							disableBtn = ' style="background-color:#CCC;"';
						}
						
						$('#roleselect .searchresult').append('<li class="option role" id="' + id + '"><i class="icon-vcard-2"></i><span class="optionname"'+disableTitle+'>' + text + '</span><div class="checkbtn"' + disableBtn + '><i class="icon-check"></i>添加</div></li>');
					});
				}
			});
		}
		
		/**
		 * 更新已选数量
		 */
		function updateSize() {
			var userSize = 0;
			var groupSize = 0;
			var roleSize = 0;

			$.each(selectedUsers, function(id, value) {
				if (value) {
					userSize++;
				}
			});
			$.each(selectedGroups, function(id, value) {
				if (value) {
					groupSize++;
				}
			});
			$.each(selectedRoles, function(id, value) {
				if (value) {
					roleSize++;
				}
			});

			$('#usersize').text(userSize);
			$('#groupsize').text(groupSize);
			$('#rolesize').text(roleSize);

			$('.member-size').each(function() {
				if ($(this).text() == 0) {
					$(this).hide();
				} else {
					$(this).show();
				}
			});
		}

		$('.easyui-tree').on('selectstart', function() {
			return false;
		});

		$('.tree-node').on('mouseleave', function() {
			$(this).children('.checkbtn').hide();
		});

	});

	function expandNode(node) {
		$('#grouptree').tree('toggle', node.target);
	}

	function loadNodeSuccess(node, data) {
		$.each(data, function(index, nodeData) {
			var id = nodeData.id;
			if (selectedGroups[id]) {
				var selectedDiv = $('#grouptree #' + id);
				selectedDiv.find('.optionname').css('color', '#CCC');
				selectedDiv.children('.checkbtn').css('background-color', '#CCC');
			}
		});
	}

	function ok() {
		var noSelected = true;

		if (userEnable && !$.isEmptyObject(selectedUsers)) {
			noSelected = false;
		}

		if (groupEnable && !$.isEmptyObject(selectedGroups)) {
			noSelected = false;
		}

		if (roleEnable && !$.isEmptyObject(selectedRoles)) {
			noSelected = false;
		}

		if (noSelected) {
			alert('没有添加任何对象!');
			return;
		}
		
		//$("#dialogframe").callback(selectedUsers, selectedGroups, selectedRoles);
		var groupsArray = new Array();
		var usersArray = new Array();

		if(typeof(selectedGroups)!="undefined"){ 
			$.each(selectedGroups, function(id) {
				groupsArray.push(id);
			});
		} 
		if(typeof(selectedUsers)!="undefined"){ 
			$.each(selectedUsers, function(id) {
				usersArray.push(id);
			});
		} 
		
		var users = usersArray.join();
		var groups = groupsArray.join();
		console.log(users);
		console.log(groups);
		$.ajax({
			type: 'POST',
			url : "${ctx}/complat/modify_submit",
			data:{"roleId":${roleid},"users":users,"groups":groups},
			dataType : "JSON",
			async : false,
			success : function(data) {
				if(data.result){
					alert("新增成功");
				}else{
					alert("新增失败");
				}
			}
		});
		closeDialog();
	}
	
	function closeDialog(){
		window.parent.document.location.reload();
	}
	
</script>
<style>
#orgselect-source {
	border-right: 1px solid #CCC;
}

.tabs {
	font-size: 14px;
}

.tabs-wrap {
	background-color: #E8E8E8;
}

.option {
	height: 26px;
	line-height: 26px;
	padding: 0 10px;
	cursor: default;
	display: block;
	position: relative;
	overflow: hidden;
	white-space: nowrap;
	text-overflow: ellipsis;
}

.batchselect .hover {
	background-color: #E6F2FF;
}

.selected {
	background-color: #4488BB;
	color: #FFF;
	color: #FFF !important;
}

.selected .optionname {
	color: #FFF;
	color: #FFF !important;
}

.ic-wrap {
	color: #CCC;
}

.batchselect li.reply {
	background-color: #4488BB;
	color: #FFF;
	font-weight: bold;
	height: 26px;
	line-height: 26px;
	padding: 0 10px;
	cursor: default;
	display: block;
}

.batchselect li.user {
	color: #226699;
}

.batchselect li.group {
	color: #F88E32;
}

.batchselect li.role {
	color: #B858A6;
}

.batchselect i {
	margin-right: 5px;
}

.panel {
	font-size: 14px;
}

.member-icon {
	font-size: 18px;
	margin-left: 30px;
	position: relative;
	display: inline-block;
}

.member-size {
	color: #FFF;
	background-color: red;
	border-radius: 10px;
	font-size: 9px;
	height: 12px;
	line-height: 12px;
	text-align: center;
	position: absolute;
	top: 6px;
	left: 8px;
	font-weight: bold;
	padding: 0 3px;
	display: none;
}

.checkbtn {
	font-size: 14px;
	width: 70px;
	height: 26px;
	text-align: center;
	line-height: 26px;
	position: absolute;
	top: 0;
	right: 0;
	z-index: 10;
	color: #FFF;
	display: none;
	cursor: pointer;
	white-space: nowrap;
}

.selectsource .checkbtn, .searchresult .checkbtn {
	background-color: #43BBEF;
}

.selecttarget .checkbtn {
	background-color: #FF9300;
}

.checkbtn i {
	vertical-align: middle;
}

.batchadd {
	display: none;
	cursor: pointer;
	position: absolute;
	top: 140px;
	right: 80px;
	z-index: 20;
	width: 100px;
	height: 80px;
	border-radius: 10px;
	background-color: #43BBEF;
	color: #FFF;
	text-align: center;
	padding: 10px;
	box-shadow: 2px 2px 1px #EFEFEF;
	cursor: pointer;
}

.batchremove {
	display: none;
	cursor: pointer;
	position: absolute;
	top: 140px;
	left: 250px;
	z-index: 20;
	width: 100px;
	height: 80px;
	border-radius: 10px;
	background-color: #FF9300;
	color: #FFF;
	text-align: center;
	padding: 10px;
	box-shadow: 2px 2px 1px #EFEFEF;
	cursor: pointer;
}

.tree-node {
	height: 26px;
	line-height: 26px;
	position: relative;
}

.tree-title {
	color: #F88E32;
	font-size: 14px;
}

.tree-expanded,.tree-collapsed,.tree-folder,.tree-file,.tree-checkbox,.tree-indent,.tree-title
	{
	vertical-align: middle;
}
.ui_buttons {
    white-space: nowrap;
    padding: 4px 0 4px 8px;
    height: 52px;
    line-height: 52px;
    text-align: right;
    background-color: #ffffff;
    border-top: solid 1px #e5e5e5;
}
.ui_buttons input::-moz-focus-inner {
    border: 0;
    padding: 0;
    margin: 0;
}

.ui_buttons input {
    padding: 4px 12px 4px 14px;
    padding: 6px 12px 6px 14px\0;
    *padding: 5px 12px 5px 12px;
    margin-left: 6px;
    cursor: pointer;
    display: inline-block;
    text-align: center;
    line-height: 1;
    height: 28px;
    letter-spacing: 3px;
    overflow: visible;
    color: #4cadf1;
    font-size: 14px;
    border: solid 1px #4cadf1;
    border-radius: 3px;
    border-radius: 0\9;
    background: #ffffff;
}

.ui_buttons input:hover {
    color: #ffffff;
	background-color: #81c5f5;
    box-shadow: none;
}
.ui_buttons input:active {
    color: #ffffff;
	background-color: #81c5f5;
    box-shadow: none;
}
</style>
</head>
<body>
	<div class="batchadd">
		<i class="icon-check" style="font-size: 60px;"></i><br /> 点击添加已选中
	</div>
	<div class="batchremove">
		<i class="icon-remove-sign" style="font-size: 60px;"></i><br /> 点击移除已选中
	</div>
	<div id="dialog-content" style="padding: 0; overflow: hidden;">
		<div id="orgselect-source" data-options="tabPosition:'left',headerWidth:100" class="easyui-tabs" style="width: 500px; height: 368px;">
			
			<c:if test="${fn:contains(orgType,'u')}">
				<div id="usersselect" title="用户和机构">
					<div style="height: 37px; padding-top: 5px; border-bottom: 1px dotted #CCC; background-color: #F9F9F9;">
						<div class="input-text" style="margin: auto; width: 370px;">
							<input id="user-search" class="searchtext" type="text" placeholder="用户名、机构名或拼音缩写，回车检索" style="background-color: #FCFCFC; width: 345px; height: 23px; border: none; margin-top: 3px; vertical-align: middle;" />
							<img id="user-search-btn" src="${ctx}/res/ui/images/enter.png" title="点击此处检索" style="vertical-align: middle; cursor: pointer;">
						</div>
					</div>
					<div style="height: 356px; overflow: auto; position: relative;">
						<ul class="selectsource batchselect"></ul>
						<ul class="searchresult batchselect"></ul>
					</div>
				</div>
			</c:if>
			<c:if test="${fn:contains(orgType,'g')}">
				<div id="groupselect" title="机构">
					<div style="height: 37px; padding-top: 5px; border-bottom: 1px dotted #CCC; background-color: #F9F9F9;">
						<div class="input-text" style="margin: auto; width: 370px;">
							<input id="group-search" class="searchtext" type="text" placeholder="机构名或拼音缩写，回车检索" style="background-color: #FCFCFC; width: 345px; height: 23px; border: none; margin-top: 3px; vertical-align: middle;" />
							<img id="group-search-btn" src="${ctx}/res/ui/images/enter.png" title="点击此处检索" style="vertical-align: middle; cursor: pointer;">
						</div>
					</div>
					<div style="height: 356px; overflow: auto; position: relative;">
						<ul id="grouptree" class="easyui-tree selectsource batchselect" data-options="onDblClick:expandNode,onLoadSuccess:loadNodeSuccess" url="group_load?roleid=${roleid }">
						</ul>
						<ul class="searchresult batchselect"></ul>
					</div>
				</div>
			</c:if>
			<c:if test="${fn:contains(orgType,'r')}">
				<div id="roleselect" title="角色">
					<div style="height: 37px; padding-top: 5px; border-bottom: 1px dotted #CCC; background-color: #F9F9F9;">
						<div class="input-text" style="margin: auto; width: 370px;">
							<input id="role-search" class="searchtext" type="text" placeholder="角色名或拼音缩写，回车检索" style="background-color: #FCFCFC; width: 345px; height: 23px; border: none; margin-top: 3px; vertical-align: middle;" />
							<img id="role-search-btn" src="${ctx}/res/ui/images/enter.png" title="点击此处检索" style="vertical-align: middle; cursor: pointer;">
						</div>
					</div>
					<div style="height: 356px; overflow: auto; position: relative;">
						<ul class="selectsource batchselect" style="width: 100%;"></ul>
						<ul class="searchresult batchselect"></ul>
					</div>
				</div>
			</c:if>
		</div>
		<div id="orgselect-target" style="width: 247px; height: 399px; position: absolute; right: 0; top: 8px">
			<div style="height: 42px; line-height: 42px; padding: 0 10px; border-bottom: 1px dotted #CCC; background-color: #F9F9F9;">
				已选择： 
					<c:if test="${fn:contains(orgType,'u')}">
					<span class="member-icon"><i class="icon-user-3"></i><div id="usersize" class="member-size"></div></span>
					</c:if>
					<c:if test="${fn:contains(orgType,'g')}">
					<span class="member-icon"><i class="icon-users"></i><div id="groupsize" class="member-size"></div></span>
					</c:if>
					<c:if test="${fn:contains(orgType,'r')}">
					<span class="member-icon"><i class="icon-vcard-2"></i><div id="rolesize" class="member-size"></div></span>
					</c:if>
			</div>
			<div class="batchselect-wrap" style="height: 325px; overflow: auto; position: relative;">
				<ul class="selecttarget batchselect"></ul>
			</div>
		</div>
	</div>
	<div class="ui_buttons">
			<input type="button" class="btn btn-primary" value="确定" onclick="ok();" />
			<input type="button" class="btn ui-dialog-titlebar-close" value="关闭" onclick="closeDialog();" />
		</div>
</body>
</html>