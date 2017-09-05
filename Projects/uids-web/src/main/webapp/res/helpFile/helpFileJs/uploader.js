//获得上一个窗口对象
	var api = frameElement.api, W = api.opener;
	var fileId;
	var fileUrl;
	$(document).ready(function(){
		//重新设置上一个窗口的按钮。
		api.button({
		    name: '保存',
		    focus: true,
		    callback: function(e){
		    	    if($("#myform").valid()){
		    	    	if(null !=fileUrl){
							var upload_help = $("#upload_help").val(); 
							var textarea_help = $("#textarea_help").val();
							var data={
									fileTitle:upload_help,
									fileExplain:textarea_help,
									fileId:fileId,
									fileUrl:fileUrl
							};
							$.ajax({
								type: "POST",
							    url: ctx+"/sys/sysHelpUpdate",
							    data: data,
							    async:false,
							    success: function (result) {
							    	W.location.reload();
							    },
							    error: function(data) {
							        alert("error:"+data.responseText);
							        return false;
							     }
							});
							
							
						
					    	}else{
					    		$.dialog({
				    				title : "提示",
				    				width : 260,
				    				height : 64,
				    				content : "请您上传资料！",
				    				max : false,
				    				min : false,
				    				lock : true,
				    				drag : false,
				    				zIndex:9999,
				    				cancelVal : '关闭',
				    				cancel : true
				    			});
				    		  return false;
					    	}
		    	    	return false;
		    	    };
                return false;
			    }
			},{
			    name: '关闭',
			    callback: function(e){
			    	//判断文件已经上传成功
			    	if(null !=fileUrl){
			    		//判断是否是编辑按钮进入
			    		if(uploadder=="edit"){
			    			$.dialog({
			    				title : "提示",
			    				width : 260,
			    				height : 64,
			    				content : "您必须保存，否则未修改的文件也将删除！",
			    				max : false,
			    				min : false,
			    				lock : true,
			    				drag : false,
			    				zIndex:9999,
			    				cancelVal : '关闭',
			    				cancel : true
			    			});
			    		  return false;
			    		}else{
			    	var fileType="";
			    	var	fileName="";
			    		var ctxTwo= ctx+"/sys/sysHelpDelete?fileId="+fileId+"&fileName="+fileName+"&fileType="+fileType;
			    		$.ajax({
			    		    type: "get",
			    		    url: ctxTwo,
			    		    async:false,
			    		    success: function (result) {
			    		    },
			    		    error: function(data) {
			    		        alert("error:上传后的文件删除失败");
			    		     }
			    		});
			    		}
			    	}else{return true;}
			    }
			});
			
		
		
		//上传组件
		$("#uploadify").uploadify({
			'auto': true,
			'height' : 20,
			'width' : 90,
			'method':'post',
			'fileSizeLimit':1024*1024*1024*1024,
			'formData': { 'session': '<?php echo session_id();?>'}, 
			'swf': ctx+'/res/plugin/helpFile/image/uploadify.swf',
            'langFile' : ctx+'/res/plugin/helpFile/uploadifyLang_zh.js',
            'uploader'       : ctx+"/sys/helpDocument?id="+id+"&uploadder="+uploadder+"&jsessionid="+SessionGet,//后台处理的请求
            'queueID'        : 'fileQueue',//与下面的id对应
            'multi'          : false,
            'buttonText'     : '请上传资料',
            'buttonImage':null,
            'fileSizeLimit' : '50MB',//文件大小限制最大为1G
			'fileTypeExts' : '*',//允许上传的文件类型        
			'onUploadStart' : function(file){
	            $("body").showLoading();
	            
			},
			'onSelect' : uploadify_onSelect,  
	    	'onSelectError' : uploadify_onSelectError,  
	    	'onUploadError' : uploadify_onUploadError,
	    	'onUploadSuccess':function(file, data, response){
	    		var text=jQuery.parseJSON(data);
	    		fileId=text.id;
	    		fileUrl=text.path;
	    	},
            'onUploadComplete' : function(file,event, queueId,fileObj, response, data) {
            	W.$.dialog.alert('<div style="width:180px;">'+file.name + '上传成功！</div>',function(){
            		 $("body").hideLoading();
            		//$("#main").reload();
				    //W.location.reload();
				});
	        }
	 	});
		
		
		
		
  	});

	
	
	
	
	var uploadify_onSelectError = function(file, errorCode, errorMsg) {
        var msgText = "上传失败！";
        switch (errorCode) {
            case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                //this.queueData.errorMsg = "每次最多上传 " + this.settings.queueSizeLimit + "个文件";
                msgText += "每次最多上传 " + this.settings.queueSizeLimit + "个文件";
                break;
            case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                msgText += "文件大小超过限制( " + this.settings.fileSizeLimit + " )";
                break;
            case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                msgText += "文件大小为0";
                break;
            case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                msgText += "文件格式不正确，仅限 " + this.settings.fileTypeExts;
                break;
            default:
                msgText += "错误代码：" + errorCode + "\n" + errorMsg;
        }
        W.$.dialog.alert('<div style="width:180px;">'+msgText + '</div>');
    };

    var uploadify_onSelect = function(){  
    	   
    }; 

    var uploadify_onUploadError = function(file, errorCode, errorMsg, errorString) {  
        // 手工取消不弹出提示  
        if (errorCode == SWFUpload.UPLOAD_ERROR.FILE_CANCELLED  
                || errorCode == SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED) {  
            return;  
        }  
        var msgText = "上传失败！";  
        switch (errorCode) {  
            case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:  
                msgText += "HTTP 错误\n" + errorMsg;  
                break;  
            case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:  
                msgText += "上传文件丢失，请重新上传";  
                break;  
            case SWFUpload.UPLOAD_ERROR.IO_ERROR:  
                msgText += "IO错误";  
                break;  
            case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:  
                msgText += "安全性错误\n" + errorMsg;  
                break;  
            case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:  
                msgText += "每次最多上传 " + this.settings.uploadLimit + "个";  
                break;  
            case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:  
                msgText += errorMsg;  
                break;  
            case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:  
                msgText += "找不到指定文件，请重新操作";  
                break;  
            case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:  
                msgText += "参数错误";  
                break; 
            default:  
                msgText += "文件:" + file.name + "\n错误码:" + errorCode + "\n"  
                        + errorMsg + "\n" + errorString;  
        } 
         W.$.dialog.alert('<div style="width:180px;">'+msgText + '</div>',function(){
			$("body").hideLoading();
//			W.location.reload();
		});
    };  
    