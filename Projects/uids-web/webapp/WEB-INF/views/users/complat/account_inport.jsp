<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>

<%@ include file="/include/meta.jsp"%> 
<script type="text/javascript" src="${ctx}/res/js/region/checkpwd.js"></script>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
	<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
    <link rel="stylesheet" type="text/css" href="${ctx}/res/plugin/uploadify/css/uploadify.css">
<head>
<title>甘肃万维JUP课题</title>
    <script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.js"></script>
    <script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/uploadifyLang_zh.js"></script>
    <script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="${ctx}/res/plugin/uploadify/js/jquery.uploadify-3.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/res/skin/login/js/tree.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/menu.css" />
	<script type="text/javascript" src="${ctx}/res/skin/login/js/menu.js"></script>
	<link type="text/css" rel="stylesheet" href="${ctx}/res/jslib/ztree/css/zTreeStyle/zTreeStyle.css" />
	<link type="text/css" rel="stylesheet" href="${ctx}/res/skin/login/css/tree.css" />
	<script type="text/javascript" src="${ctx}/res/jslib/ztree/js/jquery.ztree.all-3.5.min.js"></script>


<style type="text/css">
.btnImport {
    width:102px;
    height:34px;
    text-align:center;
    background-color:#fff;
    background-color锛�dfdfdf;
	border: 1px solid #357ebd;
	color: #000;
	cursor: pointer;
	margin-right: 30px;
	outline: none;
	padding:2px 0;
	font-family: "Microsoft Yahei";
	font-size: 14px;
}
.btnImport:hover {
	background: #3b7db5;
	border-color: #3b7db5;
	color: #000;
}
</style>

<script type="text/javascript">
//下载模板
function downloadTemplate(fileName){
	window.location.href="${ctx}/complat/uploadFile";
	//var api = frameElement.api, W = api.opener;    
	//window.location.href="${ctx}/uploadFile/complat/userList.xlsx";
	//W.location.href = "${ctx}/complat/complatList?msg=success";
}	  

//导入
$(document).ready(function(){
	//支持选择多个文件同时上传
	var api = frameElement.api, W = api.opener;
    $("#file_upload").uploadify({
    	'swf': '${ctx}/res/plugin/uploadify/js/uploadify.swf',
        multi: false,//是否能选择多个文件
        auto:true,//文件选择完成后，是否自动上传
        fileObjName : 'files',
        'uploader' : '${ctx}/complat/complatImport',//文件上传后台处理类
        // Your options here
        'langFile':'${ctx}/res/plugin/uploadify/js/uploadifyLang_zh.js',
        'height':28,
        'width':100,
        'fileSizeLimit':'1GB',//文件大小限制最大为1G
        'buttonText':'选择文件',
        fileTypeExts : '*.xls;*.xlsx',//允许上传的文件类型           
        'removeCompleted':true,
        'onUploadComplete' : function(file) {
        	W.location.href = "${ctx}/complat/complatList?msg=success";
            //上传队列全部完成后执行的回调函数    
         }
    });
});

</script>

</head>
<body>

	<!-- 导入用户的弹出层 -->
			<div class="Popup">
				<!-- 隐藏div -->				
				<div class="Popup_cen">
				    <ul class="mainInput">
				    	<li style="float:left;"><input  type="button" class="btnImport" value="模板下载" onclick="downloadTemplate()"/></li>
				        <li style="float:left;"><input type="file" id="file_upload" name="file_upload" class="uploadify"></li>
				        
				    </ul>					
			   </div>

</body>
</html>
