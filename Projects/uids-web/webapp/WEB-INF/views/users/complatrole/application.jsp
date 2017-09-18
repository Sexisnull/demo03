<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%response.setHeader("Pragma","no-cache");response.setHeader("Cache-Control","no-store");response.setDateHeader("Expires",-1);%>
<!doctype html>
<html>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>统一身份认证系统</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/res/skin/default/css/window.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/res/skin/default/css/panel.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/res/skin/default/css/grid.css">
<!--[if IE 8]>
<link type="text/css" rel="stylesheet" href="/gsjis/ui/lib/icomoon/style-ie8.css"/>
<![endif]-->
<!--[if lte IE 8]>
<script type="text/javascript" src="/gsjis/ui/lib/icomoon/lte-ie7.js"></script>
<![endif]-->

<script type="text/javascript" src="/gsjis/ui/script/ui.js"></script>

<style>
html,body {
	height: 100%;
	overflow: hidden;
}

body {
	margin: 0;
	padding: 0;
}

#page-wrap {
	position: absolute;
	z-index: 10;
	right: 0;
}

#page {
	width: 100%;
	height: 100%;
}

#menu-wrap {
	display: none;
	position: absolute;
	z-index: 10;
	border-right: 3px solid #EFEFEF;
}

#menu-wrap.compact {
	background-color: #FFF;
	border: 1px solid #CCC;
	border-radius: 5px;
	height: 400px;
	box-shadow: 2px 2px 1px #EFEFEF;
}

#menu {
	width: 240px;
	height: 100%;
}

.dialog-content {
	overflow: hidden;
}

#sso {
	width: 100%;
	height: 3px;
	background-color: #44C7F4;
	/* 	background-image: linear-gradient(to right, rgb(85, 164, 242), */
	/* 		rgb(182, 212, 56) 50%, rgb(255, 194, 14)); */
	/* 	background-repeat: no-repeat; */
	/* 	background-color: rgb(197, 208, 48); */
	overflow: hidden;
	*overflow: hidden;
}

#sso.running {
	height: 30px;
	line-height: 30px;
}

#sso i {
	float: left;
	height: 30px;
	line-height: 30px;
}

#sso a {
	height: 30px;
	color: #FFF;
	text-decoration: none;
	display: inline-block;
	*display:inline;
	*zoom : 1;
	padding: 0 10px;
	float: left;
}

#sso a:hover,#sso a.active {
	background-color: #38A4C9;
}

#header {
	width: 100%;
	/* 	background-color: #F9F9F9; */
	/* 	background: url(/gsjis/ui/images/nav-tail.png) bottom repeat-x; */
	background: url(/gsjis/ui/images/nav-bg.png) bottom repeat-x;
	box-shadow: 0 1px 5px #888;
	position: absolute;
	z-index: 20;
	_border-bottom: 1px solid #CCC;
}

#header-container {
	height: 60px;
	min-width: 960px;
	margin: 0 20px;
}

#logo {
	float: left;
	width: 280px;
	height: 60px;
	line-height: 60px;
	font-size: 36px;
	color: #FFF;
}

.nav-menu {
	float: right;
	height: 60px;
}

.nav-menu li {
	float: left;
	font-size: 16px;
	width: 110px;
	text-align: center;
	line-height: 60px;
	position: relative;
	z-index: 100;
}

.nav-menu div {
	width: 100%;
	height: 100%;
	cursor: default;
}

.nav-title {
	color: #333;
}

.nav-menu .hover {
	background-color: #666;
}

.nav-menu .active {
	background-color: #44C7F4;
}

.nav-menu .active .nav-title,.nav-menu .hover .nav-title {
	color: #FFF;
}

.nav-menu .separator {
	border-left: 1px solid #CCC;
	border-right: 1px solid #FFF;
	height: 60px;
	width: 0;
}

.nav-account-btn {
	line-height: 60px;
	font-size: 20px;
}

.nav-submenu {
	display: none;
	position: absolute;
	top: 60px;
	left: 0;
	background-color: #F9F9F9;
	border: 1px solid #CCC;
	border-bottom-left-radius: 5px;
	border-bottom-right-radius: 5px;
	box-shadow: 1px 2px 1px #EFEFEF;
}

.nav-submenu li {
	width: auto;
	min-width: 108px;
	_width: 108px;
	height: 40px;
	line-height: 40px;
	float: none;
	text-align: left;
}

.nav-submenu span {
	padding: 0 10px;
}
</style>
<script>
	var cookiePath = '/uids-web/';

	var sso = false;
	var compactModal = $(window).width() <= 1024 ? true : false;

	var height;
	var channel = $.cookie('channel');
	var menuUrl = $.cookie('menuUrl');
	var pageUrl = $.cookie('pageUrl');
	if(!pageUrl && !menuUrl){
		pageUrl = 'groupdetail/list.do';
		menuUrl = 'menudetail/groupmenu_show.do';
	}
	
	$(function() {
		channel = channel ? channel : $('li[channel]').attr('channel');
		$('body').on('mousedown', '*', function() {
			if (typeof($dp) != 'undefined') {
				try {
					$dp.hide();//修复弹出框内的日历控件有时候关闭窗口不消失
				} catch(e) {
					
				}
			}
		});
		
		if (sso) {
			$('#sso').addClass('running');
			$('#sso').append('<i class="icon-reorder" style="color: #FFF; margin: 0 10px;"></i>');
			ssoCheck();
		}

		$('.nav-menu > li').hover(function() {
			$(this).not('.active').addClass('hover');
			$(this).children('.nav-submenu').slideDown(50);
		}, function() {
			$(this).not('.active').removeClass('hover');
			$(this).children('.nav-submenu').hide();
		});

		$('.nav-submenu > li').hover(function() {
			$(this).css('background-color', '#EFEFEF');
		}, function() {
			$(this).css('background-color', '');
		});

		$('li[channel=' + channel + ']').addClass('active');
		$('#page').attr('src', pageUrl);
		$('#menu').attr('src', menuUrl);

		height = $(window).height() - $('#header').outerHeight();

		$('#page-wrap').css('top', $('#header').outerHeight());

		initLayout();

		$(window).resize(function() {
			height = $(window).height() - $('#header').outerHeight();
			compactModal = $(window).width() <= 1024 ? true : false;
			initLayout();
		});

		$('.nav-link[page]').click(function() {
			$('.active').removeClass('active');
			$('.hover').removeClass('hover');
			var currentNav = $(this).closest('li[channel]');
			currentNav.addClass('active');

			channel = currentNav.attr('channel');
			pageUrl = $(this).attr('page');
			menuUrl = $(this).attr('menu');
			menuUrl = menuUrl ? menuUrl : null;

			initLayout();

			$('#page').attr('src', pageUrl);
			$('#menu').attr('src', menuUrl);

			$.cookie('channel', channel, {path: cookiePath});
			$.cookie('pageUrl', pageUrl, {path: cookiePath});
			$.cookie('menuUrl', menuUrl, {path: cookiePath});
		});
	});

	/**
	 * 设置布局
	 */
	function initLayout() {
		if (menuUrl && !compactModal) {
			$('#page-wrap').width($(top.window).width() - 240).height(height);
			$('#menu-wrap').css({
				top : $('#header').outerHeight(),
				left : 'auto'
			}).removeClass('compact').show().height(height);
		} else {
			$('#menu-wrap').addClass('compact').height(400).hide();
			$('#page-wrap').width($(top.window).width()).height(height);
		}
	}
	/**
	 * 获取sso列表
	 */
	function ssoCheck() {
		var url = "/gsjis/interface/ldap/sso.do";
		$.ajax({
			type : "POST",
			url : url,
			success : function(msg) {
				var appList = eval(msg);
				if (appList) {
					$.each(appList, function(index, app) {
						$('#sso').append(app);
					});
				}
			}
		});
	}
	
	/**
	 * 保持用户在线，20分钟一次
	 */
	setInterval(function(){
		var url = '/gsjis/manager/user/keep_online.do';
		ajaxSubmit(url, {
			type:'html',
			error:function(a,b,msg){
			}
		});
	}, 1200000);
	function toolbarAction(action) {
		switch (action) {
		case 'remove':
			var ids = getCheckedIds();
			if (ids == "") {
				alert("未选中任何记录");
				return;
			}
			removeMembers(ids);
			break;
		case 'add':
			openDialog(contextPath + '/manager/orgselect.do?orgType=u,g&roleId=1', 800, 500, {
				title: '成员新增',
				callback : addMembers
			});
			break;
		case 'clean':
			confirm('您将清空当前角色下的所有成员\n是否继续？', cleanMembers);
			break;
		}
	}
	
	/**
	 * 新增成员
	 */
	function addMembers(users, groups) {
		var groupsArray = new Array();
		var usersArray = new Array();
		
		$.each(groups, function(id) {
			groupsArray.push(id);
		});

		$.each(users, function(id) {
			usersArray.push(id);
		});
		
		ajaxSubmit("modify_submit.do?roleId=1&users=" + usersArray.join() + "&groups=" + groupsArray.join(), {
			success:function(result){
				if(result.success){
					location.reload();
				}else{
					alert(result.message);
				}
			}
		});
	}

	/**
	 * 删除成员
	 */
	function removeMembers(ids) {
		var groupsArray = new Array();
		var usersArray = new Array();
		
		var idsArray = ids.split(',');
		$.each(idsArray, function(index, id) {
			if (id.indexOf('g_') != -1) {
				groupsArray.push(id.replace('g_', ''));
			} else {
				usersArray.push(id.replace('u_', ''));
			}
		});
		
		confirm("您确定要删除这" + ids.split(",").length + "条记录吗", function() {
			ajaxSubmit("remove.do?roleId=1&userIds="+usersArray.join()+"&groupIds="+groupsArray.join(), {
				success:function(result){
					if(result.success){
						location.reload();
					}else{
						alert(result.message);
					}
				}
			});
		});
	}
	
	/**
	 * 清空成员
	 */
	function cleanMembers() {
		ajaxSubmit('clean.do?roleId=1', {
			success:function(result){
				if(result.success){
					location.reload();
				}else{
					alert(result.message);
				}
			}
		});
	}

</script>
</head>
<body>
<div class="panel window" style="display: block; width: 588px; left: 50px; height:350px; z-index: 9052; position: relative">
		<div class="panel-header panel-header-noborder window-header" style="width: 588px;">
			<div class="panel-title">角色成员设置</div>
			<div class="panel-tool"><a class="panel-tool-close" href="javascript:void(0)"></a></div>
		</div>
		<div id="dialog_id_1505375689854" class="dialog-wrap panel-body panel-body-noborder window-body" title="" style="overflow: hidden; width: 586px; height: 526px;">
			<div class="panel" style="display: block; width: 586px;">
				<div style="width: 586px; height: 526px;" title="" class="panel-body panel-body-noheader panel-body-noborder dialog-content">
						<div class="grid-advsearch">
		<form>
			成员类型
			<select name="memberType" data-value="0" style="width: 80px; margin: 0 30px 0 10px;">
				<option value="0">全部</option>
				<option value="1">机构</option>
				<option value="2">用户</option>
			</select>
			成员名称<input name="memberName" type="text" class="input-text" value="" style="width: 120px; margin: 0 20px 0 10px;" />
			<input type="button" class="btn btn-small btn-primary" value="检索" style="margin-right:5px;"/>
			<input type="button" class="btn btn-small advsearch-cancel" value="返回" />
			<div class="line-dotted"></div>
		</form>
	</div>
	<div id="grid_toolbar">
	<div class="datagrid-toolbar-btn-wrap">
		<a class="datagrid-toolbar-btn" onclick="toolbarAction('add')"><i class="icon-plus-sign"></i>新增</a>
		<a class="datagrid-toolbar-btn" onclick="toolbarAction('remove')"><i class="icon-minus-sign"></i>删除</a>
		<a class="datagrid-toolbar-btn" onclick="toolbarAction('clean')"><i class="icon-trash"></i>清空</a>
	</div>
	<div class="datagrid-toolbar-search-wrap">
		<div class="datagrid-toolbar-simple-search">
		<form>
		<input type="text" id="grid_simple_search" class="input-text" style="width:150px;" name="searchText" value="" placeholder="请输入成员名称"/>
		</form>
		<input type="button" class="btn btn-small btn-primary" style="margin-left:5px;" value="检索"/>
		</div>
		<input type="button" class="btn btn-small btn-advsearch" style="margin-left:5px;" value="高级检索"/>
	</div>
</div>
<script type="text/javascript">
	gridOptions['grid'] = {
			pageSize: 10,
			idFieldName: 'iid',
			recordTotal: 18,
			pageSize: 10,
			pageList: [10,15,20],
			showPageList : false,
			pageNumber: 1,
			searchType:0,
			sortName:'',
			sortOrder:'ASC'
	}
</script>
<table id="grid" class="easyui-datagrid" scrollbarSize="1" sortName="" loadMsg="" sortOrder="ASC" data-options="toolbar:'#grid_toolbar',fitColumns:true,striped:true,nowrap:true,pagination:true,pagePosition:'bottom',rownumbers:false,singleSelect:false,checkOnSelect:false,selectOnCheck:true,border:false,queryParams:{roleId:'1'},onClickRow:gridOnClickRow,onSortColumn:gridOnSortColumn" style="display:none">
    <thead>
        <tr>
        	<th data-options="
        					field:'iid',
        					checkbox:true,
        					width:200,
        					align:'center',
        					sortable:false,
        					hidden:false,
        					resizable:false
        	">
        		
        	</th>
        	<th data-options="
        					field:'name',
        					checkbox:false,
        					width:200,
        					align:'left',
        					sortable:false,
        					hidden:false,
        					resizable:true
        	">
        		成员名称
        	</th>
        	<th data-options="
        					field:'type',
        					checkbox:false,
        					width:200,
        					align:'center',
        					sortable:false,
        					hidden:false,
        					resizable:true
        	">
        		成员类型
        	</th>
        </tr>
    </thead>
    <tbody>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_19866_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>测试教育局</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_18751_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>admin01</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_20518_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>史兴辉</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_2394_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>万维2</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_20319_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>高露瑜</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_19867_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>王杰1</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_19893_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>办公厅</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_19856_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>演示账号</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_18755_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>admin05</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    	<tr>
			<td>
			        	<input type="checkbox"  value="u_17594_0"/>
		    </td>
			<td>
			        	<div >
		        			<span class="user"><i class="icon-user-3"></i>魏怡瑾</span>
		        		</div>
		    </td>
			<td>
			        	<div >
		        			<span>用户</span>
		        		</div>
		    </td>
        </tr>
    </tbody>
</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>