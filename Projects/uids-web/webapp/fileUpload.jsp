<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<c:set var="ctx" value="<%=basePath%>"/>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title>jQuery 文件上传下载</title>
	<link rel="stylesheet" type="text/css" href="${ctx}res/skin/default/plugin/uploadify/css/uploadify.css">
    <script type="text/javascript" src="${ctx}res/skin/default/js/jquery-1.8.3.min.js"></script>
    <script type="text/javascript" src="${ctx}res/skin/default/plugin/uploadify/js/jquery.uploadify-3.1.js"></script>
    <script type="text/javascript" src="${ctx}res/skin/default/plugin/uploadify/js/uploadifyLang_zh.js"></script>
 	<link type="text/css" rel="stylesheet" href="${ctx}res/skin/common/plugin/codehighlight/css/shCoreMidnight.css" />
    <script type="text/javascript" src="${ctx}res/skin/common/plugin/codehighlight/js/shCore.js"></script>
    <script type="text/javascript" src="${ctx}res/skin/common/plugin/codehighlight/js/shBrushJScript.js"></script>    <script type="text/javascript">
    $(function(){
    	SyntaxHighlighter.all();
    	//支持选择多个文件同时上传
        $('#file_upload').uploadify({
            'swf': '${ctx}res/skin/default/plugin/uploadify/image/uploadify.swf',
            multi: true,//是否能选择多个文件
            //auto:false,//文件选择完成后，是否自动上传
            'uploader' : 'fileUpload',//文件上传后台处理类
            // Your options here
            'langFile':'${ctx}res/skin/default/plugin/uploadify/js/uploadifyLang_zh.js',
            'height':29,
            'width':100,
            'fileSizeLimit':'1GB',//文件大小限制最大为1G
            'buttonText':'选择文件',
            //'fileTypeExts': '*.jpg;*.png;*.gif',//允许上传的文件类型           
            'removeCompleted':false,
            onUploadSuccess : function(file,data,response) {//上传完成时触发（每个文件触发一次）
　　alert( 'id: ' + file.id
　　+ ' - 索引: ' + file.index
　　+ ' - 文件名: ' + file.name
　　+ ' - 文件大小: ' + file.size
　　+ ' - 类型: ' + file.type
　　+ ' - 创建日期: ' + file.creationdate
　　+ ' - 修改日期: ' + file.modificationdate
　　+ ' - 文件状态: ' + file.filestatus
　　+ ' - 服务器端消息: ' + data
　　+ ' - 是否上传成功: ' + response);
}

            
        });
    });
    </script>

  </head>
  
  <body>
	<div class="upload_box">
    	<input type="file" name="file_upload" id="file_upload" />	
	</div>

<h5>源代码如下：</h5>
  <pre class="brush:javascript;">
   $(function(){
    	//支持选择多个文件同时上传
        $('#file_upload').uploadify({
            'swf': '${ctx}res/skin/default/plugin/uploadify/image/uploadify.swf',
            multi: true,//是否能选择多个文件
            //auto:false,//文件选择完成后，是否自动上传
            'uploader' : 'fileUpload',//文件上传后台处理类
            // Your options here
            'langFile':'${ctx}res/skin/default/plugin/uploadify/js/uploadifyLang_zh.js',
            'height':29,
            'width':100,
            'fileSizeLimit':'1GB',//文件大小限制最大为1G
            'buttonText':'选择文件',
            //'fileTypeExts': '*.jpg;*.png;*.gif',//允许上传的文件类型           
            'removeCompleted':false,
            onUploadSuccess : function(file,data,response) {//上传完成时触发（每个文件触发一次）
　　alert( 'id: ' + file.id
　　+ ' - 索引: ' + file.index
　　+ ' - 文件名: ' + file.name
　　+ ' - 文件大小: ' + file.size
　　+ ' - 类型: ' + file.type
　　+ ' - 创建日期: ' + file.creationdate
　　+ ' - 修改日期: ' + file.modificationdate
　　+ ' - 文件状态: ' + file.filestatus
　　+ ' - 服务器端消息: ' + data
　　+ ' - 是否上传成功: ' + response);
}

            
        });
    });
  </pre>
  </body>
</html>
