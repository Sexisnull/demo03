<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 

<head>
<title>天翼高清管理平台</title>
<script type="text/javascript">
$().ready(function() {
//表单校验
var deptNameInput=$("#name").val();
var deptCode=$("#regionCode").val();
 $("#editForm").validate({
    rules: {
	   name: {
	    required: true,
	    maxlength: 32,
	    /* uniqueName : true, */
	    stringCheck:deptNameInput
	   }
	  },submitHandler:function(form){  
	  		var iid=$("#iid").val();
            if(iid==''){
            	$.validator.errorShow($("#name"),'添加区域请从左侧树添加，此处只用于编辑');
            	return false;   
            }else{
				 form.submit();
			}
        } 
    });
    // Ajax重命名校验
	$.uniqueValidate('uniqueName', '${ctx}/complat/checkZoneName', ['regionName','oldRoleName','regionCode'], '对不起，这个区域名称重复了');
});
</script>

<script type="text/javascript">
	var setting1 = {
		async: {
			enable: true,
			url: "${ctx}/complat/zoneTree" //获取节点数据的URL地址
		},
		view: {
			dblClickExpand: false
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			//beforeClick: beforeClick,
			onClick: onClick
		}
	};

	var zNodes1 =[			
	 ];

	function beforeClick(treeId, treeNode) {
		var check = (treeNode && !treeNode.isParent);
		if (!check) alert("只能选择城市...");
		return check;
	}
	
	function onClick(e, treeId, treeNode) {
		$('.error-info-checkedbox').remove();
		$('.erro').removeClass('erro');
		var zTree = $.fn.zTree.getZTreeObj("treeRegion"),
		nodes = zTree.getSelectedNodes(),
		v = "";
		nodes.sort(function compare(a,b){return a.id-b.id;});
		//alert(nodes[0].id);
		v=nodes[0].name;
		
		//获取多个时
		//for (var i=0, l=nodes.length; i<l; i++) {			
		//	v += nodes[i].name + ",";
		//}
		//if (v.length > 0 ) v = v.substring(0, v.length-1);
		var cityObj = $("#deptName");
		cityObj.html(v);
		$("#regionId").val(nodes[0].tld);
		//收起下拉框
		$('#treeRegion').slideUp('fast');
	}
</script>
<link rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgdialog.js"></script>
<script type="text/javascript" src="${ctx}/res/js/region/zone.js"></script>
<style>
#tablelist table { text-align: left; width: 100%; margin-bottom: 0; background: #FFF; border: 1px solid #DDD; /*margin-top: 60px;*/ margin-right: auto; margin-left: auto; /*_width: 98%;*/ }
#tablelist table a { text-decoration: none; font-weight: normal; font-size: 12px; }
#tablelist table tr.even td { background: #fbfbfb; }
#tablelist table tr td, #tablelist table tr, .table_ninghua td { font-size: 12px !important; }
#tablelist table tr td, #tablelist table tr th { padding: 8px 10px; line-height: normal; text-align: left; }
.zTreeDemoBackground {border: 1px solid #ddd; */}
#tablelist form #main tbody tr td {padding: 0px 0px;}
#tablelist table tr td b, #tablelist table tr th b { padding: 0 5px; vertical-align: middle;}
#tablelist table tr th.headerSortUp { color: #333; background: url(../images/sortd.gif) 95% center no-repeat; }
#tablelist table tr th.headerSortDown { color: #333; background: url(../images/sorta.gif) 95% center no-repeat; }
#tablelist table tr td { color: #000; font-size: 13px; }
#tablelist table tr td.delete { width: 29px; text-align: center; }
#tablelist table tr td.delete a { opacity: .50; -moz-opacity: .50; }
#tablelist table tr td.delete a:hover { opacity: 1; -moz-opacity: 1; }
#tablelist table thead { background: #F7F7F7; border: 1px ssolid #9EDAFA; }
#tablelist table thead th { font-size: 14px; color: #636363; background: #D8D8D8 url(images/bar_bottom.png) bottom repeat-x; /*text-align:center;*/ }
#tablelist table td.td_tiele { text-align: right; color: #333; }
#tablelist .tableactions { overflow: hidden; padding: 0 1em 0px 0; float: right; }
#tablelist .tableactions select { width: 100px; margin-right: 5px; vertical-align: middle; outline: none; padding: 3px; background: #F3F3F3; color: #636363; border: 1px solid #D7D7D7; }
#tablelist .tableactions input.submit { border: 1px solid #D7D7D7; background: #F1F1F1 url(../images/mbg.jpg) center repeat-x; padding: 1px 6px; line-height: 20px; font-size: 12px; /*font-weight: bold;*/ color: #333; vertical-align: middle; /*text-shadow: 0 1px 0 #000;*/ cursor: pointer; border-radius: 1px; -moz-border-radius: 1px; -webkit-border-radius: 1px; }
#tablelist .tableactions input.submit:hover { background: #3D82BD url(../images/mbgh.jpg) center repeat-x; color: #FFF; border: 1px solid #243C62; /*text-shadow: 0 1px 0 #6e3704; -moz-box-shadow: inset 0 1px 0 #f3cf8c; -webkit-box-shadow: inset 0 1px 0 #f3cf8c; box-shadow: inset 0 1px 0 #f3cf8c;*/ }
#tablelist .table_pagination { font-size: 10px; font-weight: bold; padding: 5px 0 5px 1em; font-family: Verdana, Geneva, sans-serif; }
#tablelist .table_pagination.right { float: right; text-align: right; border: 0; width: 500px; overflow: hidden; padding-top: 1px; }
#tablelist .table_pagination a { text-decoration: none; border: 1px solid #ccc; padding: 3px 7px; margin: 0 1px; -webkit-border-radius: 3px; -moz-border-radius: 3px; border-radius: 3px; }
#tablelist .table_pagination a.active { color: #FFF; background: #5278F3; }
#tablelist .table_pagination a:hover { color: #fff; background: #5278F3; border: 1px solid #0069ac; text-shadow: 0 1px 0 #333; }

#tablelist table.data_form { margin: 5px auto; width: 98%; text-align: left;  color: #666; }
#tablelist table.data_form input { padding: 5px; }
/*#tablelist table.data_form tr { border-bottom: 1px solid #CCC; }*/
#tablelist table.data_form td { border: none; border-bottom: 1px solid #CCC; padding: 5px; line-height: 38px; vertical-align: middle; }
#tablelist table.data_form td img { vertical-align: middle; margin: 0 4px; }
#tablelist table.data_form span { padding: 0 5px; font-size: 20px; height: 30px; overflow: hidden; color: #CCC; }
#tablelist table.data_form select { width: 210px; padding: 5px; font: 14px/24px "microsoft yahei"; }
.blue_sub { background: #2A75AF; border: none; color: #FFF; padding: 0 20px; }
.blue_sub:hover { border: none; }
#tablelist table.data_form .pass_reg_submit { background: url(../../Images/New/WebPic/regbtn-split.gif) no-repeat; width: 109px; height: 34px; color: white; border: none; font-size: 14px; clear: both; display: inline-block; cursor: pointer; line-height: 10px; padding: 0; margin: 0 10px 0 0; }
#tablelist table.data_form .pass_reg_submit_hover, input.pass_reg_submit:hover { background-position: -120px 0; }
#tablelist table.data_form .pass_reg_submit_mousedown { background-position: -240px 0; }


#tablelist table.mes_form { margin: 5px auto; width: 95%; text-align: left;  color: #666; }
#tablelist table.mes_form input { padding: 2px 6px; }
/*#tablelist table.data_form tr { border-bottom: 1px solid #CCC; }*/
#tablelist table.mes_form td { border: none; border-bottom: 1px dashed #999; padding: 2px 5px; line-height: 34px; vertical-align: middle; }
#tablelist table.mes_form td img { vertical-align: middle; margin: 0 4px; }
#tablelist table.mes_form span { padding: 0 5px; font-size: 20px; height: 30px; overflow: hidden; color: #CCC; }
#tablelist table.mes_form select { width: 210px; padding: 5px; font: 14px/24px "microsoft yahei"; }
#tablelist table.mes_com td, #tablelist table.mes_com tr { border: none; padding: 0 5px; }

#tablelist table.reg_#tablelist table { width: 600px; /*background:#FF6;*/ margin: 2em 0 0 0; float: left; border: none; }
#tablelist table.reg_#tablelist table td { border: none; line-height: normal; height: 30px; overflow: hidden; vertical-align: top; }

.ztree li span.button.add {margin-left:2px; margin-right: -1px; background-position:-144px 0; vertical-align:top; *vertical-align:middle}

.list-warper {
padding: 0 0px;
letter-spacing: 0.05em;
}
.form-warper
{
 min-width:1003px;
}

.form-title { color: #6d747b; padding: 5px 15px; line-height: 16px; font-size: 24px; border-left: 3px solid #ffa200; overflow: hidden; margin: 21px 0; }

.form-tab { border-bottom: #D9D9D9 1px solid; height: 38px;}
.form-tab ul { float: left;      border-right: #D9D9D9 1px solid; height: 38px; border-bottom: #D9D9D9 1px solid;}
.form-tab ul li {
    float: left;      display: inline;      text-align: center;      border-left: #D9D9D9 1px solid;      border-top: #D9D9D9 1px solid;            background: #F2F2F2;
    height: 37px;      line-height: 37px;
    padding: 0 30px;
    cursor: pointer;
}
.form-tab ul li:hover { background: #ebebeb;}
.form-tab ul li:active { background: #ebebeb; border-top: 3px solid #dedede; line-height: 30px; }
.form-tab ul li.active {background-color: #ffffff;      border-bottom: 1px solid #fff;}

.form-content .form-table { margin: 0 20px; }
.form-table li { padding: 5px 0; }
.form-content select {     width: 100%;
    outline: none;
    border: 1px solid #cfd9db;
    padding: 5px 0px;
    background-image: none;
    border-radius: 4px;}
.form-content.double
{
 width:50%;
}
.form-content select:focus { border-color: #66afe9; -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(102,175,233,0.6); box-shadow: inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(102,175,233,0.6); }
.form-content select option { color: #4C5155; }
.form-content .input, .form-content textarea { 
	    width: 100%;   display: block;
    height: 22px;
    font-size: 14px;
    line-height: 32px;
    color: #555;
    background-color: #fff;
    background-image: none;
    border: 1px solid #cfd9db;
    border-radius: 4px;
    text-indent: 8px;
    -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    box-shadow: inset 0 1px 1px rgba(0, 0, 0, .075);
    -webkit-transition: border-color ease-in-out .15s, -webkit-box-shadow ease-in-out .15s;
    -o-transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s;
    transition: border-color ease-in-out .15s, box-shadow ease-in-out .15s; }
.form-content .input:hover, .form-content textarea:hover { border-color: #b1b9bb; }
.form-content .input:focus, .form-content textarea:focus {
    border-color: #66afe9;
}

.form-content .input.right { border-color: #69e966; }
.form-content .input.erro { border-color: #e96666; }
.form-content .input.danger { border-color: #e9c666; }
.form-content .input.right:focus { border-color: #69e966; -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(105,233,102,0.6); box-shadow: inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(105,233,102,0.6); }
.form-content .input.erro:focus { border-color: #e96666; -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(233,102,102,0.6); box-shadow: inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(233,102,102,0.6); }
.form-content .input.danger:focus { border-color: #e9c666; -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(233,198,102,0.6); box-shadow: inset 0 1px 1px rgba(0,0,0,0.075), 0 0 8px rgba(233,198,102,0.6); }
.form-content li .canEnter { font-size: 12px; color: #adadad; font-weight: normal; font-style: normal; margin-top: -43px; float: right; position: relative; z-index: 9; margin-right: 5px; }
.form-content li .canEnter em { font-size: 24px; font-style: normal; font-weight: normal; color: #757D85; font-family: Arial; }
.form-content .icon-date-r { width: 35px; height: 29px; display: block; position: relative; top: 5px; right: 0px; border-left: 1px solid #cfd9db; cursor: pointer; background: #f5f5f5 url(../image/date-icon.png) no-repeat center; float: right; margin-top: -36px; margin-top:0 \9; top:-30px \9; *top:-36px; display:inline; z-index: 9; }
.form-content .icon-date-r:hover { background-color: #ebebeb; }
.form-content .icon-date-r:active { background-color: #ebebeb; border-top: 3px solid #c8c8c8; height: 26px; }
.form-content .label { padding: 0 10px; text-indent: 10px; font-size: 12px; line-height: 20px; display: inline; *display:inline; *zoom:1; padding: 4px 0; }
.form-content .label.block { display: block; }
.form-content .label .check_btn { display: inline-block; width: 14px; height: 14px; border: 1px solid #cfd9db; vertical-align: middle; margin-right: 5px; cursor: pointer; }
.form-content .label .check_btn:hover { border: 1px solid #66afe9; }
.form-content .label .check_btn.active { border: 1px solid #66afe9; background: #66afe9 url(../image/arrow.png) center no-repeat; }
.form-content .label .radio_btn { display: inline-block; width: 16px; height: 16px; vertical-align: middle; margin-right: 5px; cursor: pointer; background: url(../image/radio-bg.png) no-repeat center top; }
.form-content .label .radio_btn:hover { background-position: center -25px; }
.form-content .label .radio_btn.active { background-position: center bottom; }
.form-content .label span { vertical-align: middle; }
.form-content .form-selectbox {    border-radius: 4px; background: #fff;cursor: pointer; border: 1px solid #cfd9db; height: 32px; line-height: 34px; text-indent: 2.5%; position: relative;  font-size: 12px; }
.form-content .form-selectbox ul { position: absolute; z-index: 1000; background: #fff; border: 1px solid #cfd9db; left: -1px; top: 32px; right: -1px; display: block; padding: 7px 0px; }
.form-content .form-selectbox .from-selecticon { background: url(../image/select-arrow.png) no-repeat center -32px; display: inline-block; *display:inline; *zoom:1; width: 20px; height: 20px; float: right; }
.form-content .form-selectbox .from-selecticon.active { background-position: center 13px; }
.form-content .form-selectbox ul li { line-height: 30px; position: relative; height: 30px; z-index: 1000; padding: 0; }
.form-content .form-selectbox ul li:hover { background: #ecf0f1; }
.form-content .form-selectbox ul li.active { background: #66afe9; color: #fff; }
.form-content .form-icon-clear { /*display: none; float: right; background: url(../image/clear_icon.png) center no-repeat; width: 25px; height: 20px; position: relative; z-index: 9; margin-top: -26px; *margin-top:-32px; font-style: normal; cursor: pointer; */}

.form-btn { text-align: left; padding: 20px 0; *padding:0; padding-left: 20px;}
.btn { color: #4C5155;  display: inline-block;
  margin-bottom: 0;
  font-weight: normal;
  text-align: center;
  vertical-align: middle;
  -ms-touch-action: manipulation;
  touch-action: manipulation;
  cursor: pointer;
  background-image: none;
  border: 1px solid transparent;
  white-space: nowrap;
  padding: 6px 12px;
  font-size: 14px;
  line-height: 1.428571429;
  border-radius: 4px;
  -webkit-user-select: none;
  -moz-user-select: none;
  -ms-user-select: none;
  user-select: none;
  border: none;
  padding: 6px 12px;
  border-bottom: 4px solid;
  -webkit-transition: border-color 0.1s ease-in-out 0s,background-color 0.1s ease-in-out 0s;
  transition: border-color 0.1s ease-in-out 0s,background-color 0.1s ease-in-out 0s;
  outline: none;
  border-radius: 3px;
  background-clip: padding-box;
  background:#f6f6f6;
}

.btn.green {background-color: #5cb85c;
  border-color: #5cb85c; color: #fff; }
.btn.green:hover {    background-color: #72D672;
    border-color: #72D672;
    color: #fff;}

.list_tab
{
  float:left;
  border-left: 1px solid #CFD9DB;
  margin-top: 8px;
}
.list_tab li
{

float: left;

padding: 5px 20px;

border: 1px solid #CFD9DB;

margin-left: -1px;
cursor:pointer;
}
.list_tab li.active
{
background: #249bf3;color: #fff;border: 1px solid #249BF3;}

.page-title {
  padding: 0px 0px 0px 16px;
  font-size: 28px;
  letter-spacing: -1px;
  display: block;
  color: #666;
  margin: 0px 0px 15px 0px;
  font-weight: 300;
  border-left: 4px solid #eb9316;
}
.form-content {
  padding: 10px 0;
  background-color: #fdfcfc; color: ; border: 0px;
}

.form-content.form-inline label.titles
{
    float:left;
    width:120px;
    padding-right: 10px;
    text-align: right;
}

.form-content.form-inline .input-content
{
  position: absolute;
  z-index: 91;
  left: 130px;
  top: 0;
  right: 10px;
}

.form-content.form-inline .form-control {
  position: relative;
  overflow: hidden;
  margin-bottom:10px;
}

.form-content span.small {
  color: #aaa;
  font-size: 12px;
}
.form-content input.input-text, .form-content select.input-text{
  width: 100%;
}
.form-content label.titles {
   padding: 10px 0;
  margin: 0;
  display: block;
  font-size: 14px;
  line-height: 14px;
  color: #4C5155;
  font-weight: 100;
}
.form-content .input-content {
  position: relative;
  z-index: 91;
}
.form-content .input-content.list-content {
  z-index: 99;
}
.form-content .input-content.list-content  input.input-text {
  cursor: pointer;
}
.form-content .input-content .icon {
  position: absolute;
  z-index: 92;
  right: 0;
  top: 0;
  height: 34px;
  width: 30px;
  text-align: center;
  line-height: 34px;
  cursor: pointer;
}
.form-content .input-content .icon:hover {
  color: #999;
}
.form-content .input-content ul.checkbox-list,
.form-content .input-content ul.ztree {
  border: solid 1px #ddd;
  width: 100%;
  padding-left: 0;
  padding-right: 0;
  border-top: none;
  max-height: 200px;
  overflow: hidden;
  overflow-y: auto;
  position: absolute;
  z-index: 929;
  left: 0;
  top: 34px;
  *top: 36px;
  background-color: #fff;
}
.form-control.active  .input-content {
  z-index: 999999;
}
.form-control label.radio {
  font-size: 14px;
  margin-right: 16px;
}
.form-content .input-content ul.checkbox-list li {
  padding: 4px 8px;
}
.form-content .input-content ul.checkbox-list li label.checkbox {
  display: block;
}
.form-content .input-content ul.checkbox-list li:hover {
  background-color: #ddd;
}
.form-btn {
  
    background:#fdfcfc;
    border:0px;
    position: static;
}
.form-btn .btn {
  margin-right: 16px;
}
</style>
</head>
<body>
<div class="form-warper" style="overflow:hidden">
	<div class="list-warper" style="min-width:1366px">
	<!--列表的面包屑区域-->
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/backIndex" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a >系统管理</a>
			</li>
			<li class="split"></li>
			<li class="active">
				区域管理
			</li>
    	</ol>
    </div>
    <!--左侧树形结构-->
    <div id="tablelist">
    <table class="tablelist" style="width:300px;float:left;min-width:0px;">
      <tbody>
        <tr>
		  <td>
		  	<div class="zTreeDemoBackground left" style="overflow-x:auto;overflow-y:auto;">
		  		<ul id="treeDemo" class="ztree" style="width:300px; height:400px; display: block;"></ul>
		   	</div>
		  </td>
		</tr>
      </tbody>
    </table>
	</div>
	
	<!--表单的标题区域--> 
	<div class="form-title">区域编辑</div>
    <!--表单的选项卡切换-->
    <form id="editForm" method="post" action="${ctx}/complat/zoneFormSave">
    <div style="display:none;">
    	<input type="hidden" id="iid" name="iid" value="${complatZone.iid}"/>
    </div>
    <!--表单的主内容区域-->
    <div class="form-content double fn-left">
        <ul class="form-table">
        	<!--,nameCheck: true,isUnique:true,cnRangelength:[1,64] -->
        	 <li><b class="mustbe">*</b>区域名称</li>
            <li>
            	<input type="text" disabled="disabled" style="background:#F0F0F0;" class="input" id="regionName1" name="name1" value="${complatZone.name}"  />
            	 <input type="hidden" class="input" id="regionName" name="name" value="${complatZone.name}"  />
            	<input type="hidden" id="oldDeptName" name="oldName" value="${complatZone.name}"  />
            	<i class="form-icon-clear"></i>
            </li>
																
            <li>区域编码</li>
            <li>
            	<input type="text" style="background:#F0F0F0;" class="input regionCode" id="regionCode1" name="deptCode1" value="${complatZone.codeId}" />
            	<input type="hidden" class="input regionCode" id="regionCode" name="deptCode" value="${complatZone.codeId}" />
            	<i class="form-icon-clear"></i>
            </li> 
            <li>区域类型</li>
            <li>
            	<input type="radio" name="type" checked="checked" ><span id="typeName"></span>
            	<i class="form-icon-clear"></i>
            </li> 
            <li>上级区域</li>
            <li>
            	<input disabled="disabled" style="background:#F0F0F0;" type="text" class="input parentDeptName" id="pName" name="pName" value="${complatZone.name}" />
            	<input style="background:#F0F0F0;" type="hidden" class="input parentDeptName" id="pid" name="pid" value="${complatZone.pid}" />
            	<i class="form-icon-clear"></i>
            </li>
         </ul>
        
        <div style="clear:both;"></div>
    	<!--表单的按钮组区域-->
    	<div class="form-btn">
    		<input type="submit" id="submit-btn" value="保存" class="btn green"/>
    	</div>
    </div>
    
    </form>
    <!--表单的底部区域-->
    <div class="form-footer"></div>
    <!--表单的预览或提示区域-->
    <div class="form-preview"></div>
    <!--表单的提示区域-->
    <div class="form-tip"></div>
    <!--表单的弹出层区域-->
    <div class="form-dialog"></div>
</div>
</div>
</body>
</html>
