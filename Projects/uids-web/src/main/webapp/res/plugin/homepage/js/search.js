// JavaScript Document

$(document).ready(function(e) {
	//菜单开启关闭动画
    $(".list_search li:not('.search_li')").click(function(e) {
		var eventEl = $(this);
		var arrow = $(this).find(".arrow");
		if($(this).find(".dropdown_list").is(":hidden"))
		{
			$(".search_box").slideUp(300);
			$(".search_li").removeClass("list_li-boder");
			
			$(".dropdown_list").hide();			
			$(".list_search li:not('.search_li')").removeClass("list_li-boder");
			$(".list_search li:not('.search_li')").find(".arrow").css("border-color"," #aaa transparent transparent transparent").css("margin-top",13);
				
			var left = eventEl.offset().left;
			eventEl.addClass("list_li-boder");
			var height =$(this).find(".dropdown_list").height();
			arrow.css("border-color"," transparent transparent #aaa transparent").css("margin-top",8);		
			$(this).find(".dropdown_list").show().height(0).animate({height:height},0);
		}	
		else
		{
			arrow.css("border-color"," #aaa transparent transparent transparent").css("margin-top",13);	
			$(this).find(".dropdown_list").animate({height:0},0,function()
			{
				eventEl.removeClass("list_li-boder");				
				$(this).css("height",'auto').css("display","none");
			});
		}	
    });
	//选中文字替换
	$(".dropdown_list dl").click(function(e) {
        var str = $(this).text();
		$(this).parent().parent().find(".li_content span:first").text(str);
    });
	//高级查询效果
	$(".search_li").click(function(e) {
		var arrow = $(this).find(".arrow");
		if( $(".search_box").is(":hidden"))
		{
			$(".dropdown_list").hide();			
			$(".list_search li:not('.search_li')").removeClass("list_li-boder");
			$(".list_search li:not('.search_li')").find(".arrow").css("border-color"," #aaa transparent transparent transparent").css("margin-top",13);
			arrow.css("border-color"," transparent transparent #aaa transparent").css("margin-top",8);	
			$(this).addClass("list_li-boder");
		}
		else
		{
			arrow.css("border-color"," #aaa transparent transparent transparent").css("margin-top",13);	
			$(this).removeClass("list_li-boder");
		}
        $(".search_box").slideToggle(0);
    });
});