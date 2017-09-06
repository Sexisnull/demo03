var path = '/uums-web/';
$(function() {
	// 操作框的点击效果
	$(".listHandler").on("click", function() {
		$(".listHandler>ul").slideUp('fast', function() {
		});
		if ($(this).find("ul").find("li").length > 0) {
			$(this).find("ul").slideToggle(300);
		}
	});
	
	// 点击页面其它部分隐藏操作下拉层
	$(document).on("click", function(e) {
		if ($(e.target).parents(".listHandler").length == 0) {
			$(".listHandler").find("ul").hide();
		}
	});
	// 选择窗口方法
	$(".form-selectinput").on("click", function() {
		$(this).next("ul").slideToggle(200);
		$(this).find(".from-selecticon").toggleClass("active");
	});
	$(".select-radio ul li").on("click", function() {
		$(this).siblings("li").removeClass("active");
		$(this).addClass("active");
		$(this).parent("ul").slideUp(200);
		var selectVal = $(this).text();
		$(this).parent("ul").next("select").val(selectVal);

		$(this).parent("ul").prev(".form-selectinput").find("span")
				.text(selectVal);
		var id = $(this).attr("id");
		if (id == "system-manager" || id == "org-manager"
				|| id == "file-manager" || id == "doc-manager") {
			$('#resIcon').val(id);
		} else {
			$('#parentId').val(id);
		}
	});

	// 控制显示提示框
	var add = $("#alert");
	add.slideUp(1000);
	setTimeout(function() {
		add.slideUp(1500);
	}, 1300);
	add.slideDown(1000);
	setTimeout(function() {
		add.slideUp(1000);
	}, 1300);

	$(".check-label").click(function(e) {
		e = e || window.event;
		if (e.preventDefault) {
			e.preventDefault();
			e.stopPropagation();
		} else {
			e.returnValue = false;
			e.cancelBubble = true;
		}

		//多选 checkbox
		if ($(this).find("i.check_btn") && $(this).find("i.check_btn").length > 0) {
			if (!$(this).find("i.check_btn").hasClass("check_all")) { //排除多选按钮
				if (!$(this).find("i.check_btn").hasClass("basecase")) {
					if ($(this).find("i.check_btn").hasClass("active")) {
						$(this).find("i.check_btn").removeClass("active");
						$(this).find("input[type=checkbox]").removeAttr("checked");
					} else {
						$(this).find("i.check_btn").addClass("active");
						$(this).find("input[type=checkbox]").attr("checked", "checked");
					}
				}
			} 
		} 
		//单选 radio
		if ($(this).find("i.radio_btn") && $(this).find("i.radio_btn").length > 0) {
			var checkbtn=$(this).find("i.radio_btn").length;
			if (!$(this).find("i.radio_btn").hasClass("active")) {
				checkbtn--;
				$(this).siblings().find(".radio_btn").removeClass("active");
				$(this).siblings().find("input[type=radio]").removeAttr("checked");
				$(this).find("i.radio_btn").addClass("active");
				$(this).find("input[type=radio]").attr("checked", "checked");
			}
			var btnno=0;
			var btn=0;
			$(".list-table .check-label input[type=radio]").each(function(){
				btn++;
				if(this.value=="0"&&$(this).attr("checked")=="checked"){
					btnno++;
				}
			});
			$(".list-table .check-label input[type=checkbox]").each(function(){
				btn++;
				if(this.value=="0"&&$(this).attr("checked")=="checked"){
					btnno++;
				}
			});
			var _r=$(".list-table th .check_all");
			if(btn>0){
				if(btnno>0){
					if(_r.hasClass("active")){
						_r.removeClass("active");
					}
				}else{
					_r.addClass("active");
				}
			}
		}
	});

	// 列表复选框
	$(".list-table .check_btn").on("click", function() {
		if ($(this).hasClass("active")) {
			$(this).next("input").removeAttr('checked');
		} else {
			$(this).next("input").attr('checked', 'checked');
		}
		$(this).toggleClass("active");
		//全选操作
		if ($(this).hasClass("check_all")) {
			if (!$(this).hasClass("active")) {
				$(".list-table td .check_btn").removeClass("active");
				$(".list-table td input[type=checkbox]").removeAttr("checked");
				///////
				$(".list-table td .radio_btn").removeClass("active");
				$(".list-table td input[type=radio][value=1]").removeAttr("checked");
				$(".list-table td input[type=radio][value=0]").attr("checked", "checked");
				$(".list-table td input[type=radio][value=0]").parent().find("i").addClass("active");
			} else {
				$(".list-table td .check_btn").addClass("active");
				$(".list-table td input[type=checkbox]").attr("checked", "checked");
				//////
				$(".list-table td .radio_btn").removeClass("active");
				$(".list-table td input[type=radio][value=0]").removeAttr("checked");
				$(".list-table td input[type=radio][value=1]").attr("checked", "checked");
				$(".list-table td input[type=radio][value=1]").parent().find("i").addClass("active");
			}
		}
		var btnno=0;
		var btnyes=0;
		$(".list-table input[type=checkbox]").each(function(){
			btnyes++;
			if($(this).attr("checked")=="checked"){
				
			}else{
				btnno++;
			}
		});
		var _r=$(".list-table th .check_all");
		if(btnyes>0){
			if(btnno>0){
				if(_r.hasClass("active")){
					_r.removeClass("active");
				}
			}else{
				_r.addClass("active");
			}
		}
	});
});
/** 批量删除* */
function deleteData(url, parm) {
	if ($(".check_btn:checked").length != 0 && $('.list-table tbody input:checkbox:checked').length != 0) {
		$.dialog.confirm('您确认要删除吗？', function() {
			var ids = "";
			$('.list-table tbody input[type=checkbox]').each(function(i, o) {
				if ($(o).attr('checked')) {
					ids += $(o).val() + ",";
				}
			});
			window.location.href = path + url + "?" + parm + "="
					+ ids.substring(0, ids.length - 1);
		});

	} else {
		$.dialog.alert('请您至少选择一条数据', function() {
			return null;
		});
	}
}

/** 删除单条数据* */
function deleteSingle(url, parm, obj) {
	var singleId = $(obj).parents("td").parent().find('td:first')
			.find('input').attr('id');
	$.dialog.confirm('您确认要删除吗？', function() {
		window.location.href = path + url + "?" + parm + "=" + singleId;
	});
}
/** 进入新增或编辑页面，新增时不需要传后面两个参数* */
function add(url, parm, obj) {
	var singleId = $(obj).parents("td").parent().find('td:first')
			.find('input').attr('id');
	var orderField = $("#orderField").val();
	var orderSort = $("#orderSort").val();
	if (orderField != '' && orderSort != '') {
		window.location.href = path + url + "?" + parm + "=" + singleId
				+ "&orderField=" + orderField + "&orderSort=" + orderSort;
	} else {
		window.location.href = path + url + "?" + parm + "=" + singleId;
	}
}

/** 列表跳转指定页* */
function pageGoto(sortType, searchParams, totalPages, orderField, orderSort) {
	var page = $("#page").val();
	var re = /^[1-9]+[0-9]*]*$/;
	if (!re.test(page)) {
		$("#page").addClass("erro");
		$("#page").val("");
	} else if (parseInt(page) < 1) {
		$("#page").addClass("erro");
		$("#page").val("");
	} else if (parseInt(page)>parseInt(totalPages)) {
		$("#page").addClass("erro");
		$("#page").val("");
	} else {
		location.href = "?page=" + page + "&sortType=" + sortType + "&"
				+ searchParams + "&orderField=" + orderField + "&orderSort="
				+ orderSort;
	}
}

/*
 * 用途：检查输入字符串是否只由汉字、字母、数字组成 输入： value：字符串 返回： 如果通过验证返回true,否则返回false
 */
function isChinaOrNumbOrLett(s) {// 判断是否是汉字、字母、数字组成
// var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
	var regu = /^(\w|[\u4E00-\u9FA5]|@)*$/;
	var re = new RegExp(regu);
	if (re.test(s)) {
		return true;
	} else {
		return false;
	}
}

function post(URL, PARAMS) {      
    var temp = document.createElement("form");      
    temp.action = URL;      
    temp.method = "post";      
    temp.style.display = "none";      
    for (var x in PARAMS) {      
        var opt = document.createElement("textarea");      
        opt.name = x;      
        opt.value = PARAMS[x];      
        temp.appendChild(opt);      
    }      
    document.body.appendChild(temp);      
    temp.submit();      
    return temp;      
}

/* 取消弹出层 */
$.PopDown = function () {
    $('.dropdown').hide();
    $('.pop_clear').hide();
}
/* 定自下拉选择框 */
$.fn.Popup = function (content, options) {
    var $this = $(this);
    $this.attr("readonly", "readonly");
    var PopDiv;
    var defaultOptions = { width: false, keyname: false, showClear: true };
    options = $.extend(true, defaultOptions, options);

    if (options.keyname) {
        $this.after('<input class="FormElement ui-widget-content ui-corner-all" id="' + options.keyname + '" name="' + options.keyname + '" type="hidden" />');
    }
    var PopDiv = $('<div id=PopDiv' + parseInt(Math.random() * 100) + '></div>').css('display', 'none');
    var clearDiv;
    if (options.showClear == true) clearDiv = $('<a class="pop_clear" style="display: none; text-align: center" title="清除选项"></a>');
    $(document.body).append(PopDiv).append(clearDiv);
    //PopDiv;
    PopDiv.append(content).addClass('dropdown');

    $this.bind('click', function (event) {
        $.PopDown();

        event.stopPropagation(); //取消事件冒泡

        content.show();
        if (clearDiv) clearDiv.show();
        var offset = $this.offset();
        PopDiv.css({ width: ((options.width) ? $this.innerWidth() : 165), left: offset.left + 'px', top: offset.top + $this.outerHeight() + 1 + 'px', 'z-index': '9999' })

        if (options.height) { PopDiv.css({ height: options.height + 'px' }); }

        if (clearDiv) clearDiv.css({ position: 'absolute', top: offset.top + 2, left: offset.left + $this.innerWidth() - 32, margin: '0 auto', cursor: 'pointer', 'z-index': '10000' });
        PopDiv.show();

        if (clearDiv) {
            clearDiv.bind('click', function (e) {
                $this.val('');
                if (options.clearEvent && $.isFunction(options.clearEvent)) {
                    options.clearEvent.call(options.clearEvent, "");
                }
            });
        }

        //单击空白区域隐藏弹出层
        $(document).click(function (e) {
            $.PopDown();
            if (options.btn && options.btnEvent && $.isFunction(options.btnEvent)) {
                options.btnEvent.call(options.btnEvent, "");
            }
        });
        $(PopDiv).click(function (e) {
            e.stopPropagation();
        });
    });
};

$.showMessage = function(type, message, callback) {
    if (!type) type = "info";
    var cssName = "yellow";
    var cssTitle = "消息！";
    switch (type) {
        case "success":
            cssName = "green";
            cssTitle = "成功！";
            break;
        case "error":
            cssName = "red";
            cssTitle = "失败！";
            break;
        case "warning":
            cssName = "yellow";
            cssTitle = "警告！";
            break;
        case "info":
            cssName = "blue";
            cssTitle = "消息！";
        	break;
        default:
            cssName = "blue";
            break;
    }

    var timeId = new Date().getTime();
    
    var html = "<div class=\"form-alertbox " + cssName + "\" id=\"alert_" + timeId + "\" style=\"display: none;\">";
    html += "<label class=\"icon\">" + cssTitle + "</label>";
    html += "<label class=\"message\">" + message + "</label>";
    html += "<i class=\"form-alert-close\"></i>";

    $("body").append(html);
    var obj = $("#alert_" + timeId);
//    obj.slideUp(1000);
//	setTimeout(function() { obj.slideUp(1500); }, 1300);
	obj.slideDown(1000);
	setTimeout(function() { 
		obj.slideUp(1500);
		//obj.remove();
	}, 1300);
}
/*
 * loading事件
 * 弹出一个窗口(带按钮)
 * msgtitle： 提示框标题
 * urls:页面url
 * width,heigth 宽高
 * sussback 确定后的事件
 */
function urlloading(urls) {
	$('body').showLoading();
	location.href = urls;
	//setInterval(function(){$('body').hideLoading();},2000);
}
