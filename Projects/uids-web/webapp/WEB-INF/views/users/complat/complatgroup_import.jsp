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
.mainInput{
    height:60px;
}

.mainInput li{
    fioat:left !important;
}
.btnImport {
    width:87px;
    height:35px;
    text-align:center;
    border:1px red #659be0;
    background-color:#fff;
}
</style>

<script type="text/javascript">
//下载模板
function downloadTemplate(fileName){	    
	window.location.href="${ctx}/uploadFile/complat/groupList.xlsx";
}	  

//导入
$(function(){
	//支持选择多个文件同时上传
    $("#file_upload").uploadify({
    	'swf': '${ctx}/res/plugin/uploadify/js/uploadify.swf',
        multi: false,//是否能选择多个文件
        auto:true,//文件选择完成后，是否自动上传
        fileObjName : 'files',
        'uploader' : '${ctx}/uids/complatgroupImport',//文件上传后台处理类
        // Your options here
        'langFile':'${ctx}/res/plugin/uploadify/js/uploadifyLang_zh.js',
        'height':28,
        'width':100,
        'fileSizeLimit':'1GB',//文件大小限制最大为1G
        'buttonText':'选择文件',
        fileTypeExts : '*.xls;*.xlsx',//允许上传的文件类型           
        'removeCompleted':true,
        'onQueueComplete' : function(queueData) {
        console.log(queueData);
            //上传队列全部完成后执行的回调函数    
         }, 
        'onUploadSuccess' : function(file,data,response) {//上传完成时触发（每个文件触发一次）
        	
             
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
				        <li style="float:left;"><input type="file" id="file_upload" name="file_upload" class="uploadify"></li>
				        <li style="width:50px; float:right;margin-right:37px;"><input id="templet" type="button" class="btnImport" value="模板下载" onclick="downloadTemplate()"/></li>
				    </ul>					
			    <div id="uploadfileQueue" style="height: 55px;"></div>
					<p id="msg" style="color: red;"></p>
				</div>

				<%--<div class="Popup_bottom">
					<input type="button" class="btn red btnImport" value="导入" onclick="javascript:$('#file_upload').uploadify('upload', '*')" />
					
				</div>
			--%></div>

</body>
</html>
