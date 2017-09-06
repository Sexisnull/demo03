
function serchHelpFile() {
	form1.submit();
}
//添加
var editHelp=function(id){
	
	location.href = ctx+"/sys/sysHelpEdit?id="+id;
	
};
//批量删除
function sysHelpDeleteSelect() {
		if ($(".check_btn:checked").length != 0
				&& $('.list-table tbody input:checkbox:checked').length != 0) {
			$
					.dialog({
						title : "批量删除",
						width : 260,
						height : 64,
						content : "您确定删除文档？",
						max : false,
						min : false,
						lock : true,
						drag : false,
						okVal : '确定',
						ok : function() {
							var ids = "";
							$('.list-table tbody input[type=checkbox]').each(
									function(i, o) {
										if ($(o).attr('checked')) {
											ids += $(o).val() + ",";
										}
									});
							window.location.href = ctx+"/sys/sysHelpDelete?fileId="
									+ ids.substring(0, ids.length - 1);
						},
						cancel : true
					});

		} else {
			$.dialog.alert('请您至少选择一条数据');
		}
	}

//删除单个
function deletesc(fileId,fileName,fileType) {
	$.dialog({
		title : "删除",
		width : 260,
		height : 64,
		content : "您确定删除文档？",
		max : false,
		min : false,
		lock : true,
		drag : false,
		okVal : '确定',
		ok : function() {
			window.location.href = ctx+"/sys/sysHelpDelete?fileId="+fileId+"&fileName="+fileName+"&fileType="+fileType;
		},
		cancelVal : '关闭',
		cancel : true
	});
};

//编辑
var edit=function(id,fileTitle,fileExplain){
	$.dialog({
		title : '上传资料',
		width : 500,
		height : 280,
		max : false,
		min : false,
		lock : true,
		padding : '40px 20px',
		content : "url:"+ctx+"/sys/uploader?id="+id+"&uploadder=edit&fileTitle="+encodeURI(fileTitle)+"&fileExplain="+encodeURI(fileExplain),
		fixed : true,
		drag : false,
		resize : false,
		cancelVal: '关闭',
		cancel: false 
	});
	
};

//导入文档
var uploader = function(id){
	$.dialog({
		title : '上传资料',
		width : 500,
		height : 280,
		max : false,
		min : false,
		lock : true,
		padding : '40px 20px',
		content :  "url:"+ctx+"/sys/uploader?id="+id+"&uploadder=uploadder",
		fixed : true,
		drag : false,
		resize : false,
		cancelVal: '关闭',
		cancel: false 
	});
};

//下载
var Download = function(fileId,fileName,fileType){
	window.location.href=ctx+"/sys/fileHelpDownload?fileId="+fileId+"&fileName="+fileName+"&fileType="+fileType;
	       
};

