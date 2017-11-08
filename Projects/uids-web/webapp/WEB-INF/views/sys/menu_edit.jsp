<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<%@ include file="/include/meta.jsp"%> 
<!-- 上传图片 -->
<head>
<meta charset="utf-8">
<title>甘肃万维JUP课题</title>
<script type="text/javascript">
$().ready(function() {
//表单校验
var menuNameInput=$("#menuName").val();
 $("#editForm").validate({
    rules: {
	   menuName: {
	    required: true,
	    cnRangelength: [0,16],
	    stringCheck:menuNameInput
	   },
	   menuUrl: {
	    maxlength: 256
	   },
	   menuSeq: {
	    required: true,
	    digits:true,
	    maxlength: 3
	   }
	  }
    });
    
	var editParentMenuId='${sysMenu.parentMenuId}';
	if(editParentMenuId==0){
		$("#menuImgLab").show();
		$("#menuImgInput").show();
	}else{
		$("#menuImgLab").hide();
		$("#menuImgInput").hide();
	}
});
function changeImageDiv(){
	var parentMenuId=$("#parentMenuId").val();	
	if(parentMenuId==0){
		$("#menuImgLab").show();
		$("#menuImgInput").show();
	}else{
		$("#menuImgLab").hide();
		$("#menuImgInput").hide();
	}
}
</script>
</head>
<body>
<div class="form-warper">
	<!--表单的面包屑区域-->
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/backIndex" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li class="active">
				系统管理
			</li>
			<li class="split"></li>
			<li class="active">
    			<c:if test="${empty sysMenu.menuId}">菜单新增</c:if><c:if test="${not empty sysMenu.menuId}">菜单编辑</c:if>
			</li>
    	</ol>
    	
    </div>
	<!--表单的标题区域--> 
	<%-- <div class="form-title"><c:if test="${empty sysMenu.menuId}">菜单新增</c:if><c:if test="${not empty sysMenu.menuId}">菜单编辑</c:if></div> --%>
    <!--表单的选项卡切换-->
    <form id="editForm" method="post" action="${ctx}/menu/menuSave">
    
    <div style="display:none;">
    	<input type="hidden" id="id" name="menuId" value="${sysMenu.menuId}"/>	
    </div>
    
    <!--表单的主内容区域-->
    <div class="form-content">
    	<table class="form-table single">
    		<tr>
    			<th><b class="mustbe">*</b>请输入菜单名称：</th>
    			<td>
    				<input type="text" class="input" id="menuName" name="menuName" value="${sysMenu.menuName}"/>
    			</td>
    		</tr>
    	
        <ul class="form-table">
        	<!--,nameCheck: true,isUnique:true,cnRangelength:[1,64] -->
           	<tr>
    			<th><b class="mustbe">*</b>请选择父级菜单：</th>
    			<td>
    				<select id="parentMenuId" name="parentMenuId"  class="select right" onchange="changeImageDiv();">
	          	  		<option value="0">--请选择--</option>
		          		<c:forEach var="parentMenuList" items="${parentMenuList}">
		            	<option value="${parentMenuList.menuId}" 
		            	<c:if test="${sysMenu.parentMenuId==parentMenuList.menuId}">selected </c:if>>
							${parentMenuList.menuName}
		            	</option>
		          		</c:forEach>
	          		</select>
    			</td>
    		</tr>
    		<tr>
    			<th><b class="mustbe">*</b>请输入菜单URL：</th>
    			<td>
    				<input type="text" class="input"  name="menuUrl" value="${sysMenu.menuUrl}" />
    			</td>
    		</tr>
    		<tr>
    			<th><b class="mustbe">*</b>请输入一级菜单图片名称：</th>
    			<td>
    				<input type="text" class="input" name="menuImg"  value="${sysMenu.menuImg}" />
    				<p style="width: 100%; white-space: normal; line-height: 20px;">
		            	src:home_nav_icon1.png,home_nav_icon2.png,<br/>
		            	home_nav_icon3.png,home_nav_icon4.png,home_nav_icon5.png,<br/>
		            	home_nav_icon6.png暂时提供这几个图片地址
	            	</p>
    			</td>
    		</tr>
    		<tr>
    			<th><b class="mustbe">*</b>请输入排序值：</th>
    			<td>
    				<input type="text" class="input" name="menuSeq" value="${sysMenu.menuSeq}" />
    			</td>
    		</tr>
    		</table>
            
           
             <%-- <li id="menuImgLab">请输入一级菜单图片名称</li>
            <li id="menuImgInput">
            	<input type="text" class="input" name="menuImg"  value="${sysMenu.menuImg}" />
            	src:home_nav_icon1.png,home_nav_icon2.png,
            	home_nav_icon3.png,home_nav_icon4.png,home_nav_icon5.png,home_nav_icon6.png暂时提供这几个图片地址
            	<i class="form-icon-clear"></i>
            </li>  --%>
        </ul>
    </div>
    <div style="clear:both;"></div>
    <!--表单的按钮组区域-->
    <div class="form-btn">
    
    	<input type="submit" value="保存" class="btn bluegreen"/>
    	&nbsp;&nbsp;
        <input type="button" value="返回" onclick="javascript:window.location.href='${ctx}/menu/menuList?findNowPage=true'" class="btn gray"/>
        
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




</body>
</html>
