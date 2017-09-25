var contextPath = $('script:first').attr('src');
contextPath = contextPath.substr(0, contextPath.indexOf('/ui/lib/'));
$(function(){
	$(window).resize(function(){
		if($('.dialog-wrap').size() > 0){
			$('.dialog-wrap').dialog('center');
		}
	});
});
/**
 * 打开窗口
 * 
 * @param url
 * @param width
 * @param height
 * @param options
 * @param id
 * @returns
 */
function openDialog(url, width, height, options, id) {
	var tmp_id = "";
	if (typeof (id) == 'undefined') {
		tmp_id = 'dialog_id_' + new Date().getTime();
	} else {
		tmp_id = id;
	}
	var dialogContent = top
			.$('<div id="' + tmp_id + '" style="width:100%;height:100%;" class="dialog-wrap">'
					+ '<iframe name="dialog_frame" style="width:100%;height:100%;" frameborder="0"'
					+ '></iframe></div>');
	var opt = {
		title : ' ',
		width : width,
		height : height,
		closed : false,
		cache : false,
		parentWindow : window,
		modal : true,
		onOpen : function() {
			dialogContent.find('iframe[name=dialog_frame]').attr('src',
					url).attr('data-dialog-id', tmp_id);;
		}
	};
	$.extend(opt, options);
	dialogContent.dialog(opt);
}

/**
 * 获得父页面Window对象
 * 
 * @returns
 */
function getParentWindow() {
	var tb = getDialog();
	return tb.dialog('options').parentWindow;
}

/**
 * 刷新父页�?
 * 
 * @returns
 */
function refreshParentWindow() {
	getParentWindow().location.reload();
}

/**
 * 获得当前窗口
 * 
 * @returns
 */
function getDialog() {
	var url = window.location.href;
	var tb = $(window.frameElement).attr('data-dialog-id');
	return top.$('div#' + tb);
}

/**
 * 关闭窗口
 * 
 * @param refreshParent
 * @returns
 */
function closeDialog(refreshParent) {
	var tb = getDialog();
	if (refreshParent == true) {
		refreshParentWindow();
	}
	window.setTimeout(function(){
		tb.dialog('close');
		tb.dialog('destroy');
	}, 0);
}

/**
 * 通过隐藏的Iframe提交表单
 * 
 * @param url
 * @returns
 */
function hideSubmit(url){
	var size = $('body#hiddenSubmit').size();
	if(size==0){
		$('body').append('<iframe frameborder="0" id="hiddenSubmit" name="hiddenSubmit" style="width: 0;height: 0"></iframe>');
	}
	$('body#hiddenSubmit').attr('src',url);
}

/**
 * Ajax方式提交表单
 * 
 * @param url
 * @param setting
 * @returns
 */
function ajaxSubmit(url,setting){
	var opt = {
			type:'json',
			error:function(a,b,msg){alert('操作失败');},
			success:function(){},
			data:null
	};
	opt = $.extend(opt, setting);
	$.ajax({
		type: "post",
		url: url,
		dataType: opt.type,
		error : opt.error,
		data:opt.data,
		success : opt.success
	});
}

$(function(){
	// 不可用控件样�?
	$(':disabled').addClass('disabled');
	
	// 不可编辑控件样式
	// $('[readonly]').addClass('readonly');
	
	// 日历
	if(typeof($dp)!='undefined'&&typeof(WdatePicker)!='undefined'&&jQuery.fn.jcalendar){
		$('.jcalendar').jcalendar();
	}
	
	// 校验
	if(typeof($.validity) != 'undefined'){
		$.validity.setup({ 
			outputMode:"showErr",
			elementSupport : ":text, :password, textarea, select, :radio, :checkbox, input[type='hidden'], input[type='tel'], input[type='email'], input[type='file']"
		});
	}
	
	// Ajax提交表单
	$('form[data-posttype=ajax]').each(function(index, element){
		var form = $(element);
		form.on('submit', function(){
			var url = form.attr('action');
			var submitBtn = $(form.find('[type=submit]'));
			var data = form.serialize();
			submitBtn.attr('disabled',true).addClass('disabled');
			$.ajax({
				type: "post",
				url: url,
				data:data,
				dataType: "script",
				error : function(a,b,msg){
					submitBtn.attr('disabled',false).removeClass('disabled');
					alert('操作失败');
				}
			});
			return false;
		});
	});
	
	// tip小提�?
	if(jQuery.fn.poshytip){
		jQuery.fn.tip = jQuery.fn.poshytip;
		// tip基本配置
		var tipDefaultConfig = {
			className: 'tip-yellowsimple',
			alignTo: 'target',
			alignX: 'right',
			alignY: 'center',
			offsetX: 5,
			fade: false,
			slide: false
		};
		$('.tip[title]').tip($.extend({},tipDefaultConfig,{showOn: 'focus'}));
		$('.help[title]').tip($.extend({},tipDefaultConfig,{alignX: 'center',alignY: 'top', offsetY: 5}));
	}
	
	// 复选框
	$(':checkbox').each(function(index,element){
		var data_values = $(element).attr('data-value');
		if(data_values){
			var list_data = eval(data_values);
			var length = $(list_data).size();
			// 如果是数�?
			if(length >= 1 && $.isArray(list_data)){
				$.each(list_data,function(i, n){
					if(n == $(element).val()){
						$(element).attr("checked",true);
					}
				});
			}else{
				if(data_values.indexOf(',') > -1){
					var data_value = data_values.split(',');
					$.each(data_value,function(i, n){
						if(n == $(element).val()){
							$(element).attr("checked",true);
						}
					});
				}else{
					if(data_values == $(element).val()){
						$(element).attr("checked",true);
					}
				}
			}
		}
	});
	
	// 单选框
	$(':radio').each(function(index,element){
		var data_value = $(element).attr('data-value');
		if(data_value){
			if(data_value == $(element).val()){
				$(element).attr("checked",true);
			}
		}
	});
});
if(top.$.messager){
	//弹出提示 error、info、question、warning
	window.alert = function (msg,type,fu){
		top.$.messager.alert(' ',msg,type,fu);
	};
	// 弹出确认�?
	window.confirm = function(msg,okCall,cancelCall){
		msg = msg.replace(/\n/g, '<br/>');
		top.$.messager.confirm(' ',msg,function(flag){
			if(flag){
				if(typeof(okCall) != 'undefined'){
					okCall();
				}
			}else{
				if(typeof(cancelCall) != 'undefined'){
					cancelCall();
				}
			}
		});
	};
}

// 获得URL中的参数�?
$.extend({
	getUrlParam: function(name, url) {
		url = url ? url : window.location.href;
		var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); 
		var r = url.substr(url.indexOf("\?")+1).match(reg); 
		if (r!=null) {
			return unescape(r[2]);
		}
		
		return null; 
	}
});
/**
 * 获得一个tree实例
 * @param setting	json格式参数：{domPath:树的dom路径，默认为top.menu, treeDomId：树的dom的Id，默认为tree}
 * @returns	tree
 */
var Tree = {
	getInstance:function(setting){
		var options = {
				domPath : top.menu,
				treeDomId : 'tree'
		};
		options = $.extend(options, setting);
		return options.domPath.$('#'+options.treeDomId).tree();
	}	
};
/**
 * 改变树和列表�?
 * @param menuSrc	菜单url
 * @param pageSrc	页面url
 * @param isCookie	是否记录cookie
 * @return
 */
function changeMainFrame(menuSrc, pageSrc, isCookie){
	top.menuUrl = menuSrc;
	top.$('#menu').attr('src', menuSrc);

	top.pageUrl = pageSrc;
	top.$('#page').attr('src', pageSrc);
	
	top.initLayout();
	
	top.$.cookie('menuUrl', null, {path: top.cookiePath});
	top.$.cookie('pageUrl', null, {path: top.cookiePath});
	
	if(isCookie){
		top.$.cookie('menuUrl', menuSrc, {path: top.cookiePath});
		top.$.cookie('pageUrl', pageSrc, {path: top.cookiePath});
	}
}