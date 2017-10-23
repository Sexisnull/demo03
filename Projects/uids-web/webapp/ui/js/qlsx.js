
var domain=document.domain;
/**
 * 切换左侧部门、主题
 * @param num
 * @return
 */
function qiehuan(num){
	if(num==1){
		$("#bml").show();
		$("#ztl").hide();
		$("#bmjt").hide();
		$("#ztjt").show();
		
		 $("#grdept").hide();
		 $("#gddept").show();
		 $("#sqdept").hide();
		
		$("#bm").attr("class","bm");
		$("#zt").attr("class","zt");
	}
	if(num==2){
		$("#bml").hide();
		$("#ztl").show();
		$("#bmjt").show();
		$("#ztjt").hide();
		
		$("#grtheme").hide();
		 $("#gdspan").show();
		 $("#sqspan").hide();
		
		$("#zt").attr("class","bm");
		$("#bm").attr("class","zt");
		
	}
}
/**
 * 主题点击更多
 * @param num
 * @return
 */
function gdtheme(num){
	if(num==1){
	$("#grtheme").show();
	$("#sqspan").show();
	$("#gdspan").hide();
	
}
if(num==2){
	$("#grtheme").hide();
	$("#sqspan").hide();
	$("#gdspan").show();
}
}

/**
 * 部门点击更多
 * @param num
 * @return
 */
function gddept(num){
	if(num==1){
	$("#grdept").show();
	$("#sqdept").show();
	$("#gddept").hide();
	
}
if(num==2){
	$("#grdept").hide();
	$("#sqdept").hide();
	$("#gddept").show();
}
}
/**
 * 机构用户切换 并且拼接内容页 url
 * @param obj
 * @param type
 * @param from
 * @param id
 * @param name
 * @return
 */
function godownurl(obj, type,from,id,name,webid){
	$("#bmzt").find('a').removeClass('spansx').addClass('spans');
	   $(obj).addClass("spansx").removeClass('spans'); 
	var end="";
	name=encodeURIComponent(encodeURIComponent(name));
	if(from=="them"){
		end="from=them&name="+name+"&webid="+webid+"&themid="+id+"&deptid=";
	}else if(from=="dept"){
		end="from=dept&name="+name+"&webid="+webid+"&deptid="+id+"&themid=";
	}
	if(type=='sp'||type=='gr'||type=='fr'){
		var src="show_"+type+"down.do";
		$.ajax({
			  url: src,
			  data:end,
			  cache: false,	  
			  success: function(html){
			  $("#down").html(html);
			  }
			});
	}
}
/**
 * 清除关键字
 * @param id
 * @return
 */
function clearword(id) {
	if (word == "请输入关键字") {
		$("#"+id).val("");
	}
}
/**
 * 显示办事按钮
 * @param id
 * @return
 */
function showdo(id){
	$("#banshi"+id).show();
}
/**
 * 隐藏办事按钮
 * @param id
 * @return
 */
function hidedo(id){
	$("#banshi"+id).hide();
}
/**
 * 子项显示办事按钮
 * @param id
 * @return
 */
function childshow(id,num){
	$("#childdiv"+num+id).show();
}
/**
 * 子项隐藏办事按钮
 * @param id
 * @return
 */
function childhide(id,num){
	$("#childdiv"+num+id).hide();
}

/**
 * 办事指南跳转
 * @param itemid
 * @param itemname
 * @param webid
 * @return
 */
function showdetail(webid,itemcode) {
		var rand = Math.random();
		$.ajax( {
					url : "http://"+domain+"/gszw/item/detail/item_detail.do?&itemcode="+ itemcode
							+ "&webid="+ webid + "&dotype=1&rand=" + rand,
					async : false,
					success : function(text) {
					if(text=="" ||text==null){
						alert("该事项暂无办事指南！");
					}else{
						window.open(text);
					}
				}
				});
		
		
}
/**
 * 收藏
 * @param itemcode
 * @param itemname
 * @param webid
 * @return
 */
function collect(itemcode,webid) {
	var rand = Math.random();
	$
			.ajax( {
				url : "http://"+domain+"/gszw/item/detail/collectitem.do?itemcode="
						+ itemcode
						+ "&webid="
						+ webid + "&rand=" + rand,
				async : false,
				success : function(text) {
					if (text == "请先登录") {
						var src = encodeURIComponent(encodeURIComponent(location.href));
						window.open("http://"+domain+"/gszw/member/login/login.do?src="+ src 

+"&domain="+domain);
					} else {
						alert(text);
					}
				}
			});
}

/**
 * 跳转方法
 * @param itemcode
 * @param sysapp
 * @param url1
 * @param url2
 * @param type
 * @param type2
 * @return
 */
function doonline(webid,itemcode, sysapp, url1, url2, type, type2) {
	url2 = encodeURIComponent(url2);
	type = encodeURIComponent(type);
	var rand = Math.random();
	$
			.ajax( {
				url :"http://"+domain+"/gszw/item/detail/doitem.do?type="
						+ type
						+ "&itemcode="
						+ itemcode
						+ "&sysapp="
						+ sysapp
						+ "&url1="
						+ url1
						+ "&url2="
						+ url2
						+ "&domain="
						+ encodeURIComponent(encodeURIComponent(domain))
						+ "&webid="
						+ webid
						+ "&dotype=2&type2=" + type2 + "&rand=" + rand,
				context : document.body,
				datetype : "text",
				async : false,
				success : function(text) {

					if (text) {
						var data = text.split(";");
						if (data[0] == "true") {
							if (data[1]) {
								var us = data[1].split('&gotoUrl=');
								if (us.length > 1) {
									data[1] = us[0] + "&gotoUrl="
											+ encodeURIComponent(us[1]);
								}
								if (data[1].indexOf("http") < 0) {
									data[1] = "http://" + data[1];
								}
								// 修改
								if (data[1].indexOf("ticket=") >= 0) { // 有票据用post
									var tz = data[1].substr(0, data[1]
											.indexOf("&gotoUrl="));
									window.open(tz);
								} else {
									if (type2 == 1) {// 个人办事

										if (sysapp == '') {
											window.open(data[1]);
										} else {
											$
													.ajax( {
														type : "post",
														url :  "http://"+domain+"/gszw/member/login/islogin.do",
														async : false,
														success : function(
																datas) {
															if (datas != ""&& datas != null) {
																window.open(data[1]);
															} else {
																window.open(data[1]);
															}
														}
													});
										}
									} else if (type2 == 2) {// 法人办事
										if (url1 == '') {
											window.open(data[1]);
										} else {
											$
													.ajax( {
														type : "post",
														url :  "http://"+domain+"/gszw/member/login/islogin.do",
														async : false,
														success : function(
																datas) {
															if (datas != ""
																	&& datas != null) {
																window.open(data[1]);
															} else {
																window.open(data[1]);
															}
														}
													});
										}
									} else if (type2 == 3) {

										type = decodeURIComponent(type);
										if (type.indexOf("个人") >= 0
												&& type.indexOf("法人") < 0
												&& type.indexOf("其他组织") < 0) {
											type = "1";
										} else if (type.indexOf("个人") < 0) {
											type = "2";
										} else {

											type = "3";
										}
										if (type == '1') {
											if (sysapp == '') {
												window.open(data[1]);
											} else {
												$
														.ajax( {
															type : "post",
															url :  "http://"+domain+"/gszw/member/login/islogin.do",
															async : false,
															success : function(
																	datas) {
																if (datas != ""
																		&& datas != null) {
																	window.open(data[1]);
																} else {
																	window.open(data[1]);
																}
															}
														});
											}
										}
										if (type == '2') {
											if (url1 == '') {
												window.open(data[1]);
											} else {
												$
														.ajax( {
															type : "post",
															url :  "http://"+domain+"/gszw/member/login/islogin.do",
															async : false,
															success : function(
																	datas) {
																if (datas != ""
																		&& datas != null) {
																	window.open(data[1]);
																} else {
																	window.open(data[1]);
																}
															}
														});
											}
										}
										if (type == '3') {
											window.parent
													.showloginforms( "http://"+domain+"/gszw/item/detail/reserves.do?type="
															+ type
															+ "&itemcode="
															+ itemcode
															+ "&sysapp="
															+ sysapp
															+ "&url1="
															+ url1
															+ "&url2="
															+ url2
															+ "&webid="
															+ webid
+ "&domain="
						+ encodeURIComponent(encodeURIComponent(domain))
															+ "&dotype=2&type2="
															+ type2
															+ "&rand="
															+ rand);
										}
									}
								}
							}
						} else {
							data[1] = decodeURIComponent(data[1]);
							if (data[1] == "行政审批") {
								window.parent
										.showloginforms( "http://"+domain+"/gszw/item/detail/reserve.do?type="
												+ type
												+ "&itemcode="
												+ itemcode
												+ "&sysapp="
												+ sysapp
												+ "&url1="
												+ url1
												+ "&url2="
												+ url2
												+ "&webid="
												+ webid
+ "&domain="
						+ encodeURIComponent(encodeURIComponent(domain))
												+ "&dotype=2&rand=" + rand);
							}
							if (data[1] == "只有法人可以办理") {
								alert(data[1]);
							}
                                                        if (data[1] == "只有个人可以办理") {
								alert(data[1]);
							}
							if (data[1] == "token失效") {
								alert(data[1]);
								window.location.href = data[2];
								
							}
							if (data[1] == "行政审批登录个人法人其他组织") {
								window.parent
										.showloginforms( "http://"+domain+"/gszw/item/detail/reserves.do?type="
												+ type
												+ "&itemcode="
												+ itemcode
												+ "&sysapp="
												+ sysapp
												+ "&url1="
												+ url1
												+ "&url2="
												+ url2
												+ "&webid="
												+ webid
+ "&domain="
												+ encodeURIComponent(encodeURIComponent(domain))
												+ "&dotype=2&type2="
												+ type2 + "&rand=" + rand);
							}
							//if (data[1] == "请先进行实名认证") {
							//	window.parent.gotoIsauth();
							//}
						}
					}
				}
			});
}

// 实名认证
function gotoIsauth() {
	layer.confirm(
					'在线办理需要进行实名认证，点击确定跳转实名认证页面',
					{
						icon : 3
					},
					function(index) {
						layer.close(index);
						window.location.href = "http://"+domain+"/gszw/member/login/gotoIsauth.do";
					});
}

//未登录状态跳转
function showloginform(data){
	layer.open({
	    type: 2,
	    title: '甘肃政务服务网统一申报入口',
	    shadeClose: true,
	    shade: 0.8,
	    area: ['650px', '350px'],
	    content: 'http://www.gszwfw.gov.cn/gszw/item/detail/showlogin.do?url='+ encodeURIComponent(encodeURIComponent(data))
	}); 
}
//登录状态跳转
function showloginforms(data){
	layer.open({
	    type: 2,
	    title: '甘肃政务服务网统一申报入口',
	    shadeClose: true,
	    shade: 0.8,
	    area: ['650px', '350px'],
	    content:data
	}); 
}

/**
 * 咨询投诉
 * @param webid
 * @param type
 * @param itemcode
 * @param orgcode
 * @param centercode
 * @return
 */
function zxts(webid,type, itemcode, orgcode,centercode) {
	var rand = Math.random();
	$.ajax( {
				url : "/gszw/item/detail/zxts.do?webid="+webid
					    +"&type="
						+ type
						+ "&itemcode="
						+ itemcode
						+ "&orgcode="
						+ orgcode
						+ "&domain="
						+ encodeURIComponent(encodeURIComponent(domain))
						+ "&centercode="
						+ centercode
						+ "&rand=" + rand,
				context : document.body,
				datetype : "text",
				async : false,
				success : function(text) {
							if(text){
								var data = text.split(";");
								if (data[0] == "true") {
									if (data[1]) {
										var us = data[1].split('&gotoUrl=');
										if (us.length > 1) {
											data[1] = us[0] + "&gotoUrl="
													+ encodeURIComponent(us[1]);
										}
										if (data[1].indexOf("http") < 0) {
											data[1] = "http://" + data[1];
										}
										window.open(data[1]);
									}
								}
							}
						}
			});
}
/**
 * 食药监审批对接
 * @param sysapp
 */
function gotzsp(){
	$.ajax({
		url: "/gszw/item/detail/syxmsp.do?sysapp=yjxzsp&domain="+domain,
		context : document.body,
		datetype : "text",
		async : false,
		success : function(text) {
			if(text){
				var data = text.split(";");
				if (data[0] == "true") {
					if (data[1]) {
						var us = data[1].split('&gotoUrl=');
						if (us.length > 1) {
							data[1] = us[0] + "&gotoUrl="+us[1];
						}
						window.open(data[1]);
					}
					
				}
				}
			}
		});
}
/**
 * 发改委审批系统对接
 * @param sysapp
 */
function fgwtzsp(webid){
	$.ajax({
		url: "/gszw/item/detail/fgwsp.do?sysapp=fgwtzsp",
		context : document.body,
		datetype : "text",
		async : false,
		success : function(text) {
			if(text){
				var data = text.split(";");
				if (data[0] == "true") {
					if (data[1]!="http://tzxm.gszwfw.gov.cn:8090/tzxmspweb/tzxmweb/pages/dynamic/guide/projectcatalog/catalog.jsp?audit_type=01&ticket=&spbs=fgwtzsp&usename=&userType=") {
						window.open(data[1]);
					}else{
						window.open(webid);
				}
					
				}
				}
			}
		});
}
 /**
  * 显示评价
  * @param dcode
  * @param itemname
  * @param localid
  * @param deptid
  * @param webid
  * @param id
  * @return
  */
function showcomments(dcode, itemname, localid, deptid, webid, id) {
	itemname = encodeURIComponent(encodeURIComponent(itemname));
	window.open( "http://"+domain+"/gszw/eval/list/showpubeval.do?itemId="
			+ dcode
			+ "&itemname="
			+  itemname 
			+ "&localid="
			+ localid
			+ "&deptid=" + deptid + "&webid=" + webid + "&id=" + id);
}


function bszndoonline(id) {
	var rand = Math.random();
	$.ajax( {
				url : "/gszw/item/detail/dobsznitem.do?id="
						+ id + "&rand=" + rand,
				context : document.body,
				datetype : "text",
				async : false,
				success : function(text) {
					if (text) {
						var data = text.split(";");
						if (data[0] == "true") {
							if ("@" != data[1]) {
								if (data[1].indexOf("http") < 0) {
									data[1] = "http://" + data[1];
								}
								if (data[1].indexOf("ticket=") >= 0) { //有票据用post
									window.open(data[1]);
									
								} else {
									//未登录
									if ("@" == data[1]) {
								    window.open(data[2]+"&domain="+domain);
									}
								}
							} else {
								//未登录
								if ("@" == data[1]) {
									window.open(data[2]+"&domain="+domain);
								}
							}
						}
					}
				}
			});
}