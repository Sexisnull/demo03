document.writeln("<div class=\"pagecon\" id=\"pagecon\">");
document.writeln("        <div class=\"topleft\"><a href=\"http://www.gansu.gov.cn/\" target=\"_blank\"><img src=\"../ui/images/1512101414170988388.png\" />\"中国.甘肃\"门户网站</a></div>  ");
document.writeln("        <div class=\"topright\" style=\"height:30px;\">");
document.writeln("        <div style=\"float:left;width:165px;display:none;\">");
document.writeln("        <a><img src=\"../ui/images/1512101414171737037.png\" />移动版</a>");
document.writeln("        <a><img src=\"../ui/images/1512101414175034652.png\" />微信版</a></div>");
document.writeln(" <div id=\"login\" style=\"float:left;position:relative;height:30px;\">");
document.writeln("</div>");
document.writeln("<div id='div_c' style='margin-left:3px; font-weight:bold; position:relative;height:30px;float:left'><a href='javascript:;' onclick='toggleToolBar();' id='font_c' style='font-family:'微软雅黑'; font-size:14px;'>无障碍阅读</a></div>");
document.writeln("        </div>");
document.writeln("    </div> ");


document.writeln("<script src='../ui/js/qlsx.js'></script>");


document.writeln("<script type=\"text/javascript\">var domain=document.domain;");
document.writeln("function jsload(text){  ");
document.writeln("		var html=text.html;");
document.writeln("			if($(\'#login\').length>0){");
document.writeln("			$(\'#login\').html(html);");
document.writeln("			}else{");
document.writeln("			setTimeout(function(){$(\'#login\').html(html);},100);}");
document.writeln("			};");
document.writeln("var script=document.createElement('script');	");
document.writeln("script.type='text/javascript';	");
document.writeln("script.src='userresult?webId=1&domain='+encodeURIComponent(encodeURIComponent(domain))+'&callback=jsload&rand='+Math.random();	");

document.writeln("document.getElementsByTagName('head')[0].appendChild(script);	");
document.writeln("</script>");

document.writeln("  <style>#scrollUp{margin-right:3000px;}</style>");


$(document).ready(function(){
function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}
a=GetQueryString("web");
if (a=="zwdt")
{
$("#foot").append("<style>body{width:910px;padding:0}.top,.topcon,.nav,.bigtittle,#foot,.dqwz,.dqwzt{display:none}.mainWth{background:#fff}.mainWth .maincon{box-shadow:none;background: #faeeee;width:910px}#content{background:#fff;height: 277px;overflow-x:hidden; overflow-y:auto}.mainWth{padding-bottom:0}#con marquee{height:256px}#gjbm{margin-left:156px}</style>");
$("#wcagnav").remove();
}
$("#gjbm").css("top","278px");
});

    var colors = new Array("#C30","#D58C00","#090","#237FE8");
    
    function changeColor(){
        //var colorIndex = Math.round(Math.random()*3);
        var color_font=colors[Math.round(Math.random()*4)];
        document.getElementById("font_c").style.color=color_font;
        //document.wirte(colorIndex);
        setTimeout("changeColor()",200);
    }
	window.onload = function(){
    changeColor();
}