if($(".rsxl p a").length!=0){
	var boxa=$(".rsxl p a");
	for(var i=0;i<boxa.length;i++){
		var str=decodeURI(boxa[i].search);
		str=str.substring(str.lastIndexOf("/")+1,str.lastIndexOf("."));
		$(boxa[i]).text(str)
	}
}


// 搜索框

document.writeln("<script type=\"text/javascript\">");
document.writeln("");
document.writeln("");
document.writeln("	$(\'#search-form\').submit(function(){");
document.writeln("		var q = $(\'#q\').val();");
document.writeln("		");
document.writeln("	    if($.trim($(\'#q\').val()) == \"请输入关键字\"){");
document.writeln("		alert(\" 请输入关键字！\")");
document.writeln("		$(\'#q\').focus();");
document.writeln("		return false;");
document.writeln("	}");
document.writeln("	");
document.writeln("		if($.trim(q).length == 0){");
document.writeln("			");
document.writeln("			$(\'#q\').focus();");
document.writeln("			return false;");
document.writeln("		}");
document.writeln("		if($.trim(q).length > 38){");
document.writeln("			alert(\'检索词限制在38个汉字以内\');");
document.writeln("			return false;");
document.writeln("		}");
document.writeln("		return true;");
document.writeln("	}).keydown(function(event){");
document.writeln("		if(event.keyCode == 13){");
document.writeln("			firstPageSearch();");
document.writeln("		}");
document.writeln("	});");
document.writeln("");
document.writeln("	");
document.writeln("	");
document.writeln("	if($.trim($(\'#q\').val()).length == 0){");
document.writeln("		");
document.writeln("		$(\'#q\').focus();");
document.writeln("	}");
document.writeln("	");
document.writeln("	");
document.writeln("	");
document.writeln("	$(\'.jsearch-search-button\').click(function(){");
document.writeln("		firstPageSearch();");
document.writeln("	});");
document.writeln("");
document.writeln("");
document.writeln("</script>   ");
document.writeln("");
document.writeln("<style>");
//搜索框优化
$("#criteria_webid").attr("name","criteria_webid");

//常见问题
document.writeln("	#wtjd { border:1px solid #cccccc; border-bottom:none; width:636px;}");
document.writeln("	.wtjdwttd1 { width:45px; text-align:center; padding:3px; background-color:#eeeeee; font-weight:600;}");
document.writeln("	.wtjdwttd2 { border-left:1px solid #cccccc; padding:3px; background-color:#eeeeee; font-weight:600;}");
document.writeln("	.wtjddatd1 { border:1px solid #cccccc; text-align:center; border-left:none; border-right:none; padding:3px; font-weight:600;}");
document.writeln("	.wtjddatd2 { border:1px solid #cccccc; padding:3px; border-right:none; padding-left:10px;}");
//申报材料
document.writeln("	.sbclth1 { width:200px;}");
document.writeln("	.sbclth2 { width:55px;}");
document.writeln("	.sbclth3 { width:85px;}");
document.writeln("	.sbclth4 { width:83px;}");
document.writeln("	.sbclth7 { width:167px;}");
document.writeln("      .sbcltd1 { text-align:left; padding:5px;}#result{overflow:hidden}");
document.writeln("</style>");


//临时隐藏电子证照按钮
$("[src='/tysb/resources/front/tysb/declare/zxtx/dzzz0.jpg']").hide();
$("[src='/tysb/resources/front/tysb/declare/zxtx/dzzz1.jpg']").hide();
$("[src='/tysb/resources/front/tysb/declare/zxtx/dzzz2.jpg']").hide();

//无障碍
document.writeln("<script language='javascript' src='../../ui/js/barrierfree/barrierfree.js'></script><link type='text/css' rel='stylesheet' href='../../ui/css/barrierfree.css'>");


var bc=$("#barrierfree_container").index();
if (bc==-1)
{
	$("body div:eq(0)").attr("id","barrierfree_container");
}

//自由裁量修改
$("#zycl td:eq(1)").html("违法名称");
$("#zycl td:eq(2)").html("违法情形");

//子站底部标识
$("[src='/picture/53/1512101420508163537.png']").hide();
$("[src='http://www.gszwfw.gov.cn/picture/53/1512101420508163537.png']").hide();
document.writeln("<style>.footet-text2{margin-left:60px}</style>");