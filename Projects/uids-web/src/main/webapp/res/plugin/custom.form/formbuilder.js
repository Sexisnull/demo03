//fieldsLimit表单允许存放元素
var _w=$(window).width()-$("#left").width()-3;
	$("#right").css("width",_w);
var CHANGED = false, PREGOODS = [], DSFRMS, IDX = -2,
delConfirmMsg = "建议您先将数据导出后再删除字段！\n删除将导致已提交的数据和报表中此字段对应的值被清空，且不可恢复。确认删除吗？", 
DEFFLD = {
	//<a title="复制" href="#" class="icon-btn faDup"></a>
	handle : '<div class="handle"></div>',
	icon : '<img class="arrow arrowIE6 hide" src="' + resRoot + '/res/plugin/custom.form/images/arrowg.png" />',
	instruct : '<p class="instruct hide"></p>',
	fieldActions : '<div class="fieldActions hide"><a title="删除" href="#" class="icon-btn faDel"></a></div>',
	icon_del : '<a title="删除此行" class="icononly-del"></a>',
	field_li : '<li class="field default"></li>',
	item_checkbox : '<li><input name="CHKED" value="1" class="checkbox" type="checkbox" title="默认选中此项" /><input name="VAL" type="text" class="l"/><a class="icononly-add" title="添加一个新的选择项"></a><a class="icononly-del" title="删除此选择项"></a></li>',
	item_goods : '<li class="clearfix"><div class="goods-item"><div class="goods-item-inner">    <div class="goods-image">      <img src="" class="img">    </div>    <a class="goods-name-view" href="#" title="点击编辑，拖动排序">商品名称</a>    <div class="attrs">      <div class="goods-name-edit">        <a href="#" class="edit-img" title="修改商品图片"><span>更换图片</span><input title="添加配图商品" name="editImg" class="file-prew edit-img-input" type="file" size="3" accept="image/gif,image/jpeg,image/png,image/bmp"></a>        <label for="goodsItemVal">名称：</label><input id="goodsItemVal" value="商品名称" type="text" name="VAL" class="val" maxlength="16" />      </div>      <div class="goods-price">        <label class="goods-price-label">单价：</label><input value="0" type="text" name="PRC" class="price" maxlength="6" />      </div>      <div class="goods-unit">        <label>单位：</label><input type="text" name="UNT" class="unt" maxlength="4" />      </div>      <div class="goods-def">        <label>默认：</label><input type="text" name="htmlVal" class="def number" maxlength="4" />      </div>      <div class="goods-hide">        <input type="checkbox" name="HD" class="hd" value="1" /> <label>隐藏</label>      </div>      <div class="goods-currency">        <select name="CNY" class="cny" title="币别" ><option value="">币别</option><option value="¥">人民币</option><option value="$">美元</option><option value="£">英镑</option><option value="€">欧元</option></select>      </div>      <div class="goods-description">        <label for="goodsItemDes" style="vertical-align: top;">描述：</label><textarea id="goodsItemDes" name="DES" class="des"></textarea>      </div>    </div></div></div>    <a class="icononly-del" title="删除此选商品"></a> </li>',
	item_radio : '<li><input name="CHKED" value="1" class="radio" type="radio" title="默认选中此择项" /><input name="VAL" type="text" class="l"/><a class="icononly-add" title="添加一个新的选择项"></a><a class="icononly-del" title="删除此选择项"></a></li>',
	item_dropdown2 : '<li><input name="VAL" type="text" class="xl"/><a class="icononly-add" title="添加一个新的选择项"></a><a class="icononly-del" title="删除此选择项"></a></li>',
	item_other : '<li class="dropReq hide"><input name="CHKED" disabled="disabled" type="radio" title="默认选中此择项" /><label>其他</label><input name="OTHER" disabled="disabled" type="text" class="m"/><a class="icononly-del" title="删除此选择项"></a><a style="position:absolute;margin-left:45px;float:right" href="#" class="help" title="">(?)</a></li>',
	item_likert_row : '<li><input name="alias" class="xl" value="1" type="text"/><a class="icononly-add" title="添加新行"></a><a class="icononly-del" title="删除此行"></a></li>',
	item_likert_col : '<li><input name="CHKED" value="1" class="radio" type="radio" title="默认选中此列" /><input name="VAL" type="text" class="l"/><a class="icononly-add" title="添加新列"></a><a class="icononly-del" title="删除此列"></a></li>',
	item_radio_f : '<span><input type="radio" disabled="disabled" /><label></label></span>',
	item_checkbox_f : '<span><input type="checkbox" disabled="disabled" /><label></label></span>',
	likert_td : '<td><input type="radio" disabled="disabled" /><label></label></td>',
	item_radio_other_f : '<span class="hide"><input type="radio" value="other" disabled="disabled" /><label>其他</label><input name="OTHER" disabled="disabled" type="text" class="m" /></span>',
	text : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" maxlength="255" class="input" /></div>',
		json : '({alias:"单行文本",type:"text",dataLen:"",fldSize:"m",notNull:"0",valid:"",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",flag:0,manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	number : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" maxlength="32" class="input" /></div>',
		json : '({alias:"数字框",type:"number",dataLen:"",notNull:"0",dataField:"",name:"",param:"",flag:0,manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	textarea : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><textarea disabled="disabled" class="input"></textarea></div>',
		json : '({alias:"多行文本",type:"textarea",dataLen:"",fldSize:"m",notNull:"0",MIN:"",dataField:"",flag:0,name:"",param:"",baseTable:"",baseField:"",htmlVal:"",MAX:"",formDesc:"",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:179px;width:97%"></li>'
	},
	checkbox : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"></div>',
		json : '({alias:"复选框",type:"checkbox",LAY:"one",notNull:"0",SCU:"pub",flag:0,dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",formDesc:"",CSS:"",ITMS:[{VAL:"选项 1",CHKED:"0"},{VAL:"选项 2",CHKED:"0"},{VAL:"选项 3",CHKED:"0"}],manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:92px;width:97%"></li>'
	},
	radio : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"></div>',
		json : '({alias:"单选框",type:"radio",LAY:"one",notNull:"0",OTHER:"0",flag:0,dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",RDM:"0",SCU:"pub",formDesc:"",CSS:"",ITMS:[{VAL:"选项 1",CHKED:"0"},{VAL:"选项 2",CHKED:"0"},{VAL:"选项 3",CHKED:"0"}],manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:92px;width:97%"></li>'
	},
	dropdown : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><select disabled="disabled" class="m input"></select></div>',
		json : '({alias:"下拉框",type:"dropdown",dataLen:"",fldSize:"m",notNull:"0",flag:0,dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",SCU:"pub",formDesc:"",CSS:"",ITMS:[{VAL:"选项 1",CHKED:"0"},{VAL:"选项 2",CHKED:"0"},{VAL:"选项 3",CHKED:"0"}],manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:46px;width:97%"></li>'
	},
	dropdown2 : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><select disabled="disabled" class="m input"></select> <select disabled="disabled" class="m input"></select></div>',
		json : '({alias:"两级下拉框",type:"dropdown2",dataLen:"",fldSize:"m",flag:0,dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",notNull:"0",SCU:"pub",formDesc:"",CSS:"",SUBFLDS:{DD1:{},DD2:{}},ITMS:[{VAL:"选项 1",CHKED:"0",ITMS:[{VAL:"选项 11"},{VAL:"选项 12"},{VAL:"选项 13"}]},{VAL:"选项 2",CHKED:"0",ITMS:[{VAL:"选项 21"},{VAL:"选项 22"},{VAL:"选项 23"}]},{VAL:"选项 3",CHKED:"0",ITMS:[{VAL:"选项 31"},{VAL:"选项 32"},{VAL:"选项 33"}]}],manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:46px;width:97%"></li>'
	},
	section : {
		html : '<div class="noLabelAlign"><label class="desc section">分隔符</label><div class="content">这里是分隔符说明</div></div>',
		json : '({alias:"分隔符",type:"section",SCU:"pub",flag:0,SECDESC:"这里是分隔符说明",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:45px;width:97%"></li>'
	},
	html : {
		html : '<div class="noLabelAlign"><label class="desc">HTML标题</label><div class="content"><p>这里可以显示HTML内容</p></div></div>',
		json : '({alias:"HTML标题",type:"html",SCU:"pub",flag:0,HTML:"<p>这里可以显示HTML内容</p>",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:42px;width:97%"></li>'
	},
	date : {
		html : '<label class="desc">日期 <span class="req hide"> *</span></label><div class="content oneline reduction"><span>	<input class="yyyy input" disabled="disabled" maxlength="4" type="text"/>	<label>YYYY</label></span><span> - </span><span>	<input class="mm input" disabled="disabled" maxlength="2" type="text"/>	<label>MM</label></span><span> - </span><span>	<input class="dd input" disabled="disabled" maxlength="2" type="text"/>	<label>DD</label></span><span><a class="icononly-date" title="选择日期"></a></span></div>',
		json : '({alias:"日期",type:"date",notNull:"0",flag:0,dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",FMT:"ymd",formDesc:"",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:57px;width:97%"></li>'
	},
	date_ymd : '<span><input class="yyyy input" disabled="disabled" maxlength="4" type="text"/><label>YYYY</label></span><span> - </span><span><input class="mm input" disabled="disabled" maxlength="2" type="text"/><label>MM</label></span><span> - </span><span><input class="dd input" disabled="disabled" maxlength="2" type="text"/><label>DD</label></span><span><a class="icononly-date" title="选择日期"></a></span>',
	date_mdy : '<span><input class="mm input" disabled="disabled" maxlength="2" type="text"/><label>MM</label></span><span> / </span><span><input class="dd input" disabled="disabled" maxlength="2" type="text"/><label>DD</label></span><span> / </span><span><input class="yyyy input" disabled="disabled" maxlength="4" type="text"/><label>YYYY</label></span><span><a class="icononly-date" title="选择日期"></a></span>',
	date_dmy : '<span><input class="dd input" disabled="disabled" maxlength="2" type="text"/>	<label>DD</label></span><span> / </span><span>	<input class="mm input" disabled="disabled" maxlength="2" type="text"/>	<label>MM</label></span><span> / </span><span>	<input class="yyyy input" disabled="disabled" maxlength="4" type="text"/>	<label>YYYY</label></span><span><a class="icononly-date" title="选择日期"></a></span>',
	time : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content oneline reduction"><span>	<select class="hh input" disabled="disabled"></select></span><span> : </span><span>	<select class="mm input" disabled="disabled"></select></span></div>',
		json : '({alias:"时间",type:"time",notNull:"0",flag:0,dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",formDesc:"",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:57px;width:97%"></li>'
	},
	file : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" class="m input" />&nbsp;<input type="button" disabled="disabled" value="浏览..." /></div>',
		//json : '({alias:"上传文件",type:"file",flag:0,dataLen:"",fldSize:"m",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",notNull:"0",formDesc:"",CSS:""})',
		json : '({alias:"上传文件",type:"file",flag:0,dataLen:"",fldSize:"m",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",notNull:"0",formDesc:"",CSS:"",SUBFLDS:{ID:{},TYP:{},SZ:{},NM:{}},manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:54px;width:97%"></li>'
	},
	phone : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content reduction"><input type="text" disabled="disabled" maxlength="32" class="s input" /> <button type="button" class="sendcode hide">发送验证码</button></div>',
		json : '({alias:"电话",type:"phone",flag:0,notNull:"0",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",FMT:"mobile",formDesc:"",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	phone_tel_cn : '<span><input class="input" disabled="disabled" maxlength="4" size="4" type="text"/><label>区号</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="8" size="8" type="text"/><label>总机</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="4" size="4" type="text"/><label>分机</label></span>',
	phone_tel_en : '<span><input class="input" disabled="disabled" maxlength="4" size="4" type="text"/><label>####</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="8" size="8" type="text"/><label>########</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="4" size="4" type="text"/><label>####</label></span>',
	phone_mobile_cn : '<input type="text" disabled="disabled" maxlength="32" class="s input" /> <button type="button" class="sendcode hide">发送验证码</button>',
	phone_mobile_en : '<input type="text" disabled="disabled" maxlength="32" class="s input" /> <button type="button" class="sendcode hide">发送验证码</button>',
	url : {
		html : '<label class="desc"> <span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" maxlength="256" class="m input" value="http://" /></div>',
		json : '({alias:"网址",type:"url",flag:0,dataLen:"",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",notNull:"0",formDesc:"",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	money : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><b>￥</b><input type="text" disabled="disabled" maxlength="16" size="8" class="input" /></div>',
		json : '({alias:"价格",type:"money",flag:0,notNull:"0",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",FMT:"yen",formDesc:"",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	email : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" maxlength="64" class="m input" /></div>',
		json : '({alias:"Email",type:"email",dataLen:"",fldSize:"m",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",notNull:"0",formDesc:"",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:41px;width:97%"></li>'
	},
	name : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content oneline reduction"><input type="text" disabled="disabled" maxlength="128" value="" class="s input" /></div>',
		json : '({alias:"姓名",type:"name",flag:0,notNull:"0",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",FMT:"short",formDesc:"",CSS:"",manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	name_short : '<input type="text" disabled="disabled" maxlength="128" value="" class="s input" /></div>',
	name_extend_en : '<span><input class="input"  disabled="disabled" maxlength="128" size="6" type="text"/><label>Title</label></span><span> - </span><span><input class="input" disabled="disabled" maxlength="128" size="10" type="text"/><label>First Name</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="128" size="10" type="text"/><label>Last Name</label></span>',
	name_extend_cn : '<span><input class="input" disabled="disabled" maxlength="4" size="4" type="text"/><label>姓</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="4" size="8" type="text"/><label>名</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="4" size="4" type="text"/><label>称呼</label></span>',
	address : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content onelineLeft reduction"><span class="left third clear"><select disabled="disabled" class="xxl input province"><option value="">省/自治区/直辖市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input city" ><option value="">市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input zip" ><option value="">区/县</option></select></span><span class="left" style="margin:5px 5px 0px 0px;width:100%;"><textarea style="height:40px;" disabled="disabled" class="input xxl detail" placeholder="详细地址" ></textarea></span></div>',
		json : '({alias:"地址",type:"address",flag:0,notNull:"0",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",SCU:"pub",formDesc:"",CSS:"",SUBFLDS:{ZIP:{},PRV:{},CITY:{},DTL:{}},manipulation:"empty",cfId:""})',
		//json : '({alias:"地址",type:"address",flag:0,notNull:"0",dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",SCU:"pub",formDesc:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:177px;width:97%"></li>'
	},
	address_en : '<span class="left third clear"><select disabled="disabled" class="xxl input province"><option value="">省/自治区/直辖市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input city" ><option value="">市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input zip" ><option value="">区/县</option></select></span><span class="left" style="margin:5px 5px 0px 0px;width:100%;"><textarea style="height:40px;" disabled="disabled" class="input xxl detail" placeholder="Street"></textarea></span>',
	address_cn : '<span class="left third clear"><select disabled="disabled" class="xxl input province"><option value="">省/自治区/直辖市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input city" ><option value="">市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input zip" ><option value="">区/县</option></select></span><span class="left" style="margin:5px 5px 0px 0px;width:100%;"><textarea style="height:40px;" disabled="disabled" class="input xxl detail" placeholder="详细地址"></textarea></span>',
	likert : {
		html : '<div class="content noLabelAlign"><table class="table" cellspacing="0"><caption><label class="desc"><span class="req hide"> *</span></label></caption><thead><tr></tr></thead><tbody></tbody></table></div>',
		json : '({alias:"组合单选框",type:"likert",flag:0,dataField:"",name:"",param:"",baseTable:"",baseField:"",htmlVal:"",notNull:"0",HDNM:"0",SCU:"pub",CSS:"",ITMS:[{alias:"行标签 1",ITMS:[{VAL:"列标签 1",CHKED:"0"},{VAL:"列标签 2",CHKED:"0"},{VAL:"列标签 3",CHKED:"0"},{VAL:"列标签 4",CHKED:"0"}]},         {alias:"行标签 2",ITMS:[{VAL:"列标签 1",CHKED:"0"},{VAL:"列标签 2",CHKED:"0"},{VAL:"列标签 3",CHKED:"0"},{VAL:"列标签 4",CHKED:"0"}]},         {alias:"行标签 3",ITMS:[{VAL:"列标签 1",CHKED:"0"},{VAL:"列标签 2",CHKED:"0"},{VAL:"列标签 3",CHKED:"0"},{VAL:"列标签 4",CHKED:"0"}]}],manipulation:"empty",cfId:""})',
		holder : '<li class="field prefocus" style="height:184px;width:97%"></li>'
	}
};
(function() {
	$.formatPrice = function(g, f, e, d, a, b) {
		a = f ? a : "";
		if (!e) {
			e = "¥"
		}
		g.find("input.number").val(b || "0");
		if ("1" == d) {
			f = f || "";
			g.find("div.price").text(f + (a || ""));
			g.find("a.decrease,a.increase").css( {
				display : "none"
			});
			g.find("span.number-pre").show();
			g.find("input.number").addClass("noincrease")
		} else {
			f = f || "0";
			g.find("div.price").text(e + f + (a ? "/" + a : ""));
			g.find("a.decrease,a.increase").css( {
				display : "inline-block"
			});
			g.find("span.number-pre").hide();
			g.find("input.number").removeClass("noincrease")
		}
	};
	$.widget("jsform.tab",
			{
				options : {
					ulCss : [ "y", "b", "g" ],
					normalCss : [ "ny", "nb", "ng" ],
					selectedCss : [ "sy", "sb", "sg" ],
					tabIds : [ "#t1", "#t2", "#t3" ],
					ahoverCss : "ah"
				},
				select : function(e) {
					var d = this.element, f = this.options, c = $("a:eq(" + e
							+ ")", d), b = $("a", d);
					d.removeClass().addClass(f.ulCss[e]);
					b.each(function(a) {
						$(this).removeClass().addClass(f.normalCss[e])
					});
					c.addClass(f.selectedCss[e]);
					$(f.tabIds).each(function(a) {
						if (e == a) {
							$("a", f.tabIds[a]).removeClass(f.ahoverCss)
						} else {
							$("a", f.tabIds[a]).addClass(f.ahoverCss)
						}
					});
					this._selectTab(e);
					$.autoHeight();
				},
				getSelectedIndex : function() {
					return $(this.element).data("selectedIndex")
				},
				_selectTab : function(a) {
					var c = [ "#addFields", "#fieldProperties",
							"#formProperties" ], b = [ "left-y", "left-b",
							"left-g" ];
					if (a === 0) {
						$("#addFields").css("margin-top", "0")
					}
					$("#container").removeClass("left-b left-g left-y")
							.addClass(b[a]);
					$.each(c, function(d, e) {
						$(e).hide()
					});
					$(c[a]).fadeIn()
				}
			})
})(jQuery);
//var fillMatchForms = function(a) {
//	if (!DSFRMS) {
//		$.postJSON("getdsforms", {}, function(b) {
//			DSFRMS = b;
//			$("#selMatchFrm").empty().append("<option value=''></option>");
//			$.each(DSFRMS, function(c, d) {
//				if (!M._id || d._id != M._id) {
//					$("#selMatchFrm").append(
//							$.tmpl("<option value='${_id}'>${FRMNM}</option>",
//									d))
//				}
//			});
//			$("#selMatchFrm").val(a)
//		})
//	}
//	$("#selMatchFrm").val(a)
//},
var fillMatchFields = function(d, c, a) {
	var b = {
		text : "string",
		number : "number",
		phone : "string",
		email : "email",
		url : "url",
		date : "date",
		name : "string"
	};
	$("#selMatchFld").empty().append("<option value=''>-没有指定字段-</option>");
	if (!d) {
		return
	}
//	$.postJSON(resRoot+"/autoform/tableCol", {
//		"tableName": d
//	}, function(e) {
		//F[IDX].MATFRM = d;
//		$.each(e, function(g, h) {
//			//if (b[c] === b[h.type]) {
//				$("#selMatchFld").append(
//						$.tmpl("<option value='${COLUMN_NAME}'>${COMMENTS}</option>", h))
//			//}
//		});
//		//$("#selMatchFld").val(a)
//	})
	$.ajax({
		url:resRoot+"/autoform/tableCol",
		data:{"tableName": d},
		type:"post",
		dataType:'json',
		success:function(e){
			if(e.length>0){
				$("#selMatchFld").empty().append("<option value=''>-请选择字段-</option>");
				$.each(e, function(g,h){
					if (a!=undefined && a == h.COLUMN_NAME){
						$("#selMatchFld").append($.tmpl("<option value='${COLUMN_NAME}' selected>${COMMENTS}</option>",h));
					}else{
						$("#selMatchFld").append($.tmpl("<option value='${COLUMN_NAME}'>${COMMENTS}</option>",h));
					}
				});
			}
		}
	})
	
	
}, fillAutoCompSrcFlds = function(c) {
	var b = [], a = false;
	var d = function() {
		$.each(F, function(e, g) {
			if (g.MAT && g.MATFRM && g.MATFLD) {
				b.push(g)
			}
		});
		$("#selAutoCompSrcFld").empty().append("<option value=''></option>");
		$.each(b, function(e, g) {
			$("#selAutoCompSrcFld").append(
					$.tmpl("<option value='${NM}'>${alias}</option>", g))
		});
		$("#selAutoCompSrcFld").val(c)
	};
	$.each(F, function(e, g) {
		if (g.MAT && g.MATFRM && g.MATFLD && !g.NM) {
			a = true;
			return false
		}
	});
	if (a) {
		saveForm(false, d)
	} else {
		d()
	}
}, fillAutoCompFlds = function(a, d, b) {
	var e;
	$.each(F, function(g, h) {
		if (h.NM == a) {
			e = h.MATFRM;
			return false
		}
	});
	var c = {
		text : "string",
		number : "number",
		phone : "string",
		email : "email",
		url : "url",
		date : "date",
		name : "string",
		textarea : "string",
		time : "time",
		money : "number",
		radio : "string",
		dropdown : "string",
		address : "address"
	};
	$("#selAutoCompFld").empty().append("<option value=''></option>");
	if (!e) {
		return
	}
	$.postJSON("getautocompfields", {
		FRMID : e
	}, function(f) {
		$.each(f, function(g, h) {
			if (c[d] === c[h.type]) {
				$("#selAutoCompFld").append(
						$.tmpl("<option value='${NM}'>${alias}</option>", h))
			}
		});
		$("#selAutoCompFld").val(b)
	})
};
//控制form表单项编辑设置内容
function showProperties(c) {
	var b = [ "ptype", "pfldsize", "playout", "pdateformat", "pphoneformat",
			"pmoneyformat", "pnameformat", "plikert", "pitems", "pitems2",
			"pitems_other", "pitems_radio", "pitems_batchedit",
			"pitems_checkbox", "poptions", "pgoods", "pimage", "popt_required",
			"popt_unique", "popt_random", "popt_allowother", "popt_hidenum",
			"popt_authcode", "popt_compress", "popt_dismark", "psecurity",
			"prange", "pconfine", "pconfine1", "pconfine2", "pdefval_text",
			"pdefval_number", "pdefval_date", "pdefval_time",
			"pdefval_phone_tel", "pdefval_phone_mobile", "pdefval_addr",
			"pinstruct", "psection", "phtml" ], a = {
		text : [ "ptype", "pfldsize", "poptions", "popt_required",
				"popt_unique", "psecurity", "prange", "pdefval_text",
				"pinstruct", "pconfine", "pconfine1", "pconfine2" ],
		number : [ "ptype", "pfldsize", "poptions", "popt_required",
				"popt_unique", "psecurity", "prange", "pdefval_number",
				"pinstruct", "pconfine", "pconfine2" ],
		textarea : [ "ptype", "pfldsize", "poptions", "popt_required",
				"popt_unique", "psecurity", "prange", "pdefval_text",
				"pinstruct", "pconfine", "pconfine2" ],
		checkbox : [ "ptype", "playout", "poptions", "popt_required",
				"psecurity", "prange", "pitems", "pitems_batchedit",
				"pinstruct" ],
		radio : [ "ptype", "playout", "poptions", "popt_required",
				"popt_random", "popt_allowother", "psecurity", "pitems",
				"pitems_other", "pitems_radio", "pinstruct", "pconfine",
				"pconfine2" ],
		dropdown : [ "ptype", "pfldsize", "poptions", "popt_required",
				"psecurity", "pitems", "pitems_radio", "pinstruct", "pconfine",
				"pconfine2" ],
		dropdown2 : [ "ptype", "pfldsize", "poptions", "popt_required",
				"psecurity", "pitems", "pitems_batchedit", "pitems2",
				"pinstruct" ],
		section : [ "psection" ],
		html : [ "psecurity2", "phtml" ],
		likert : [ "ptype", "plikert", "poptions", "popt_required",
				"popt_hidenum", "psecurity" ],
		date : [ "ptype", "pdateformat", "poptions", "popt_required",
				"popt_unique", "psecurity", "pdefval_date", "pinstruct",
				"pconfine", "pconfine2" ],
		time : [ "ptype", "poptions", "popt_required", "popt_unique",
				"psecurity", "pdefval_time", "pinstruct", "pconfine",
				"pconfine2" ],
		phone : [ "ptype", "poptions","pfldsize", "popt_required", "popt_unique",
				"popt_authcode", "psecurity", "pdefval_phone_mobile",
				"pinstruct", "pconfine", "pconfine1", "pconfine2" ],
		file : [ "ptype", "poptions", "popt_required", "popt_compress",
				"psecurity", "pfldsize","pinstruct" ],
		url : [ "ptype", "pfldsize", "poptions", "popt_required",
				"popt_unique", "psecurity", "pdefval_text", "pinstruct",
				"pconfine", "pconfine2" ],
		money : [ "ptype", "pmoneyformat", "poptions", "popt_required",
				"popt_unique", "psecurity", "prange", "pdefval_number",
				"pinstruct", "pconfine", "pconfine2" ],
		email : [ "ptype", "pfldsize", "poptions", "popt_required",
				"popt_unique", "psecurity", "pdefval_text", "pinstruct",
				"pconfine", "pconfine1", "pconfine2" ],
		name : [ "ptype", "pnameformat", "poptions", "popt_required",
				"psecurity", "pinstruct", "pconfine", "pconfine1", "pconfine2" ],
		address : [ "ptype", "poptions", "popt_required", "psecurity",
				"pconfine", "pconfine2", "pdefval_addr", "pinstruct" ]
	};
	$.each(b, function(d, e) {
		$("#" + e).hide()
	});
	$.each(a[c], function(d, e) {
		$("#" + e).show()
	});
	if (c === "text" || c === "textarea") {
		$("label.min", "#prange").text("最小长度");
		$("label.max", "#prange").text("最大长度")
	} else {
		if (c == "checkbox") {
			$("label.min", "#prange").text("最少选择几项");
			$("label.max", "#prange").text("最多选择几项")
		} else {
			$("label.min", "#prange").text("最小值");
			$("label.max", "#prange").text("最大值")
		}
	}
}
function initTab() {
	$("a", "#tabs").click(function(b) {
		var a = $("a", "#tabs").index(this);
		/////????
		if ($("tabs").tab("getSelectedIndex") === a) {
			return
		}
		if (a === 0) {
			$("#tabs").tab("select", 0);
			setFocused(null, -1)
		} else {
			if (a === 1) {
				if ($("li.field:first", "#fields").length > 0) {
					setFocused($("li.field:first", "#fields"), 1)
				} else {
					$("#tabs").tab("select", 1);
					setFocused(null, -1)
				}
			} else {
				if (a === 2) {
					setFocused($("#fbForm"), 0)
				}
			}
		}
	});
	$("#tabs").tab();
	$("#tabs").tab("select", 0);
}
function preFocused(c, b) {
	if (b === 0 && IDX !== -1) {
		$(c).removeClass("form-default").addClass("form-prefocus")
	}
	if (b !== 0 && IDX + 1 !== b) {
		$(c).removeClass("default").addClass("prefocus");
		var a = $("p.instruct", c);
		if (a.html()) {
			a.hide().fadeIn()
		}
	}
}
function reDefault(b, a) {
	if (a === 0 && IDX !== -1) {
		$(b).removeClass("form-prefocus").addClass("form-default")
	}
	if (a !== 0 && IDX + 1 !== a) {
		$(b).removeClass("prefocus").addClass("default").find("p.instruct")
				.hide()
	}
}

function setFocused(a, c) {
	var d = $(a);
	if (c > 0) {
		$("#allProps").show();
		$("#noFieldSelected").hide();
		$("#liPos").text(c + ".");
		if (IDX + 1 === c) {
			setPropertieValues(F[c - 1]);
			return
		}
	} else {
		$("#allProps").hide();
		$("#noFieldSelected").show()
	}
	
	if (IDX === -1) {
		$("#fbForm").removeClass("form-focused").addClass("form-default").find(
				"img.arrow").hide()
	} else {
		$("li.focused", "#fields").removeClass("focused").addClass("default")
				.find("img.arrow,p.instruct,div.fieldActions").hide()
	}
	if (c == -1) {
		IDX = -2;
		return
	}
	if (c === 0) {
		//d.removeClass("form-default form-prefocus").addClass("form-focused");
		$("#tabs").tab("select", 2)
	} else {
		d.removeClass("default prefocus").addClass("focused");
		$("#tabs").tab("select", 1)
	}
	var b = $("p.instruct", d);
	if (b.html()) {
		b.show()
	}
	$("img.arrow,div.fieldActions", d).show();
	IDX = c - 1;
	if (c > 0) {
		showProperties(F[IDX].type);
		$("#allProps").animate( {
			//marginTop : d.position().top + $("#fbForm").height()
		}, 500, "easeOutCubic", function() {
			setPropertieValues(F[IDX])
		})
	} else {
		$("#liGoods").hide();
		$("#salem,#salej").prop("disabled", !M.SALE);
		$(F).each(function(e, g) {
			if ("goods" === g.type) {
				$("#liGoods").show();
				return false
			}
		});
		if (c === 0) {
			setFormValues(M)
		}
		IDX = -1
	}
}

function createItems(g, b, d) {
	var f, a = {
		checkbox : DEFFLD.item_checkbox,
		goods : DEFFLD.item_goods,
		radio : DEFFLD.item_radio,
		dropdown : DEFFLD.item_radio,
		dropdown2 : DEFFLD.item_radio,
		likertRow : DEFFLD.item_likert_row,
		likertCol : DEFFLD.item_likert_col
	};
	if (a[b] === undefined) {
		return
	}
	g.empty();
	$.each(d, function(j, h) {
		f = $(a[b]);
		g.append(f);
		f.setValues(h)
	});
	if (b === "radio") {
		f = $(DEFFLD.item_other);
		if ($("#allowOther").prop("checked")) {
			f.removeClass("hide")
		}
		g.append(f)
	} else {
		if ("dropdown2" == b && d.length > 0) {
			var e = $("#itemList2").empty();
			$.each(d[0].ITMS, function(h, c) {
				f = $(DEFFLD.item_dropdown2);
				f.find(":text").val(c.VAL);
				e.append(f)
			});
			e.attr("IDX", "0").find("a.icononly-del:first").remove();
			g.find(":text:first").focus()
		}
	}
	if ("goods" != b) {
		$("a.icononly-del:first", g).remove()
	}
}

function setFormValues(b) {
	if (b.CFMTYP === "T") {
		$("#confirmMsg_text").show()
	} else {
		if (b.CFMTYP === "U") {
			$("#confirmMsg_url").show()
		}
	}
	if (b.SCHACT === "1") {
		$("#listDateRange").show()
	}
	if (b.ALIPAY === "1") {
		$("#divPay").show()
	}
	if (b.PUBDT == "1") {
		$("#btnPubDataSetting").removeClass("hide")
	}
	var a = false;
	$("#noAliConditionField").empty().append("<option value=''></option>");
	$.each(F, function(e, d) {
		if ((d.TYP == "radio" || d.TYP == "dropdown") && !d.NM) {
			a = true;
			return false
		}
	});
	var c = function(d) {
		$.each(F,function(e, g){
							if(g.TYP === "email") {
								$("#sendTo").append($.tmpl('<option value="${k}">${v}</option>',
																{
																	k : g.NM,
																	v : g.alias
																}))
							} else {
								if (g.type === "phone" && g.FMT == "mobile") {
									$("#sendSmsTo").append($.tmpl('<option value="${k}">${v}</option>',
																	{
																		k : g.NM,
																		v : g.alias
																	}))
								} else {
									if ("radio" == g.type || "dropdown" == g.type) {
										$("#noAliConditionField")
												.append($.tmpl('<option value="${k}">${v}</option>',
																		{
																			k : g.NM,
																			v : g.alias
																		}));
										if (d.PAYCONFLD === g.NM && g.ITMS) {
											$("#noAliConditionValue")
													.empty()
													.append("<option value=''></option>");
											$.each(
															g.ITMS,
															function(f, h) {
																$("#noAliConditionValue")
																		.append($.tmpl('<option value="${VAL}">${VAL}</option>',
																								h))
															})
										}
									}
								}
							}
						})
	};
	if (a || !M._id) {
		saveForm(false, function(d) {
			b = d;
			c(b)
		})
	} else {
		c(b)
	}
	$("#formProperties").setValues(b, true);
	if (b.STIME) {
		$("#startTime").setDateTimeValue(b.STIME)
	}
	if (b.ETIME) {
		$("#endTime").setDateTimeValue(b.ETIME)
	}
}
function showPhoneFormat(a) {
	if (a === "tel") {
		$("#pdefval_phone_tel").show();
		$("#pdefval_phone_mobile").hide();
		$("#popt_authcode").hide().setValues( {})
	} else {
		$("#pdefval_phone_tel").hide();
		$("#pdefval_phone_mobile").show();
		$("#popt_authcode").show()
	}
}
function setSplitTelValue() {
	var a = $("#defval_phone_tel").val().split("-");
	$.each(a, function(b, c) {
		$(":text:eq(" + b + ")", "#pdefval_phone_tel").val(a[b])
	})
}
//开始设置字段值
function setPropertieValues(c){
	$("#allProps").setValues(c);
	if (c.type === "phone" && c.FMT) {
		$("#phoneformat").val(c.FMT);
		showPhoneFormat(c.FMT);
		if (c.FMT === "tel") {
			$("#defval_phone_mobile").val("");
			$("#defval_phone_tel").val(c.htmlVal);
			setSplitTelValue()
		} else {
			$("#defval_phone_tel").val("");
			$("#defval_phone_mobile").val(c.htmlVal)
		}
	} else {
		if (c.type == "address" && c.htmlVal) {
			var b = c.htmlVal.split("-"), a = b[0], f = b[1], e = b[2];
			$("#defval_province").val(a);
			if (a) {
				$("#f" + IDX).find("select.province").empty().append(
						$.format("<option>{0}</option>", a));
				$("#defval_city").empty().append("<option value=''>市</option>");
				if (a) {
					var d = "";
					$.each(address.provinces[a], function(h, g) {
						d += $.format('<option value="{0}">{1}</option>', h, h)
					});
					$("#defval_city").append(d);
					$("#defval_city").val(f)
				}
			}
			if (f) {
				$("#f" + IDX).find("select.city").empty().append(
						$.format("<option>{0}</option>", f));
				$("#defval_zip").empty()
						.append("<option value=''>区/县</option>");
				var d = "";
				$.each(address.provinces[a][f], function(h, g) {
					d += $.format('<option value="{0}">{1}</option>', g, g)
				});
				$("#defval_zip").append(d);
				$("#defval_zip").val(e)
			}
			if (e) {
				$("#f" + IDX).find("select.zip").empty().append(
						$.format("<option>{0}</option>", e))
			}
		} else {
			if (c.type == "file") {
				if (c.CPRS) {
					$("#chkCompress").prop("checked", true);
					$("#divCompress").removeClass("hide")
				} else {
					$("#chkCompress").prop("checked", false);
					$("#divCompress").addClass("hide")
				}
			}
		}
	}
	if (c.type === "likert") {
		createItems($("#likertRows"), "likertRow", c.ITMS);
		createItems($("#likertCols"), "likertCol", c.ITMS[0].ITMS)
	} else {
		if (c.type == "goods") {
			createItems($("#goodsList"), c.type, c.ITMS);
			if ("1" == c.FBUY) {
				$("#goodsList").find(".goods-price-label").text("数量：")
			} else {
				$("#goodsList").find(".goods-price-label").text("单价：")
			}
			if ("1" == c.NOIMG) {
				$("#goodsList").addClass("noimg");
				$("#pgoods").addClass("noimg");
				$("#type").val("goodsnoimg")
			} else {
				$("#goodsList").removeClass("noimg");
				$("#pgoods").removeClass("noimg")
			}
		} else {
			if (c.ITMS) {
				createItems($("#itemList"), c.type, c.ITMS)
			}
		}
	}
	if (c.FLDID) {
		$("#type").prop("disabled", true)
	} else {
		$("#type").prop("disabled", false)
	}
	if (c.MAT == "1") {
		$("#divMatchContainer").removeClass("hide");
		//fillMatchForms(c.MATFRM);
		fillMatchFields(c.baseTable, c.type, c.baseField)
	} else {
		$("#divMatchContainer").addClass("hide")
	}
	if (c.ACMP == "1") {
		$("#divAutoCompContainer").removeClass("hide");
		//fillAutoCompSrcFlds(c.ACMPSRCFLD);
		//fillAutoCompFlds(c.ACMPSRCFLD, c.type, c.ACMPFLD)
	} else {
		$("#divAutoCompContainer").addClass("hide")
	}
}
//结束
//组合html元素
function isInstruct(a) {
	if (M.formDesc !== "1") {
		return false
	} else {
		if (a === "likert" || a === "section" || a === "html" || a === "goods"
				|| a === "image") {
			return false
		}
	}
	return true
}
function isOneCol(a) {
	if (a === "radio" || a === "checkbox") {
		return true
	} else {
		return false
	}
}
function setDefFieldDom(li, type, index, srcJSON) {
	var dels=getDels();
	var newJSON = eval(DEFFLD[type].json);
	$.mergJSON(srcJSON, newJSON);
	F[index] = newJSON;
	createField(li, newJSON);
	if (li.index() == 0) {
		li.addClass("first")
	} else {
		li.removeClass("first")
	}
	F[index].manipulation="add";
	if(edit=="true"){
		changeTableFlag("update");
	}
	//设置排序
	F[index].flag=(index+1);
	$.autoHeight()
	if(dels.length>0){
		for(var i=0;i<dels.length;i++){
			F.push(dels[i]);
		}
	}
}
function addDefFieldDom(c, type, index, srcJSON) {
	var newJSON = eval(DEFFLD[type].json);
	$.mergJSON(srcJSON, newJSON);
	F.splice(index, 0, newJSON);
	createField(c, newJSON);
	$.autoHeight()
}
function judgeFirst(manipulation){
	if(manipulation.indexOf("update")>-1) 
		return false;
	if(manipulation.indexOf("modify")>-1)
		return false;
	return true;
}
function chargeUpdateFlag(obj){
	var fJson=F[IDX];
	if(fJson.manipulation=="empty"||fJson.manipulation=="add"){
		return;
	}
	if(fJson.manipulation.indexOf("update")==-1&&obj=="update"&&judgeFirst(fJson.manipulation)){
		F[IDX].manipulation="update";
	}
	if(fJson.manipulation.indexOf("update")==-1&&obj=="update"&&!judgeFirst(fJson.manipulation)){
		F[IDX].manipulation+="_update";
	}
	if(fJson.manipulation.indexOf("modify")==-1&&obj=="modify"){
		F[IDX].manipulation="update_modify";
	}
	if(M.tableManipulation!="delete")
		M.tableManipulation="update";
}

function changeTableFlag(obj){
	var tableManipulation=M.tableManipulation;
	if(tableManipulation=="empty"||tableManipulation=="delete"){
		return;
	}
	if(tableManipulation.indexOf("update")==-1&&obj=="update"&&judgeFirst(tableManipulation)){
		M.tableManipulation="update";
	}
	if(tableManipulation.indexOf("update")==-1&&obj=="update"&&!judgeFirst(tableManipulation)){
		M.tableManipulation+="_update";
	}
	if(tableManipulation.indexOf("modify")==-1&&obj=="modify"&&judgeFirst(tableManipulation)){
		M.tableManipulation="modify";
	}
	if(tableManipulation.indexOf("modify")==-1&&obj=="modify"&&!judgeFirst(tableManipulation)){
		M.tableManipulation+="_modify";
	}
}

function getDels(){
	var Dels=[];
	if(edit!="true")
		return Dels;
	
	for(var i=0;i<F.length;i++){
		var v=F[i];
		if(v.manipulation=="delete"){
			F.splice(i, 1);
			Dels.push(v);
		}
	}
	return Dels;
}
//初始化字段设置
function fieldInit() {
	var a = "63%";
	$("#fields").sortable( {
		axis : "y",
		delay : 100
	}).disableSelection();
	$("li", "#addFields").draggable({
		revert : "invalid",
		helper : "clone",
		connectToSortable : "#fields"
	}).disableSelection();
	
	$("#fields")
			.live(
					{
						sortstart:function(c, b){
							if (!b.item.hasClass("field")) {
								return
							}
							b.item.css("left", "");
							b.placeholder.css("height", b.item.height())
						},
						sortover:function(g, f){
							if (f.helper.hasClass("field")) {
								return
							}
							var d = f.item.attr("ftype"), j = f.item
									.attr("subtype")
									|| "", b = $(DEFFLD[d + j].holder).css(
									"height");
							f.placeholder.addClass("field default").css({
								height : b,
								width : "100%"
							});
							var c = $(DEFFLD[d + j].holder);
							if (isInstruct(d)) {
								f.placeholder.css({
									width : a,
									height : b
								})
							}
							c.css({
								width : f.placeholder.css("width"),
								height : b
							});
							f.helper.empty().append(c);
							CHANGED = true
						},
						sortupdate:function(h, d){
							var j = d.item.index();
							if (!d.item.hasClass("field")) {
								var g = $(this).find("li.field").size();
								if (g >= fieldsLimit) {
									$.alert("最多只能添加" + fieldsLimit + "个字段。");
									setTimeout(function() {
										d.item.remove()
									}, 50);
									return false
								}
								var c = d.item.attr("ftype")
										+ (d.item.attr("subtype") || "");
								$("#fields>li").each(function(l, e) {
									$(e).attr("id", "f" + l)
								});
								addDefFieldDom(d.item, c, j)
							} else {
								var f = parseInt(d.item.attr("id").substring(1)), b = F[f];
								F.splice(f, 1);
								F.splice(j, 0, b);
								$("li.field", "#fields").each(function(l, e) {
									$(e).attr("id", "f" + l)
									F[l].flag=(l+1);
								});
								IDX = f;
								setFocused(d.item, j + 1)
							}
							$("#fields").find("li.first").removeClass("first")
									.end().find("li.field:first").addClass(
											"first");
							//F[IDX].flag=IDX;
							CHANGED = true
						}
					});
	$("#fbForm").hover(function() {
		preFocused(this, 0)
	}, function() {
		reDefault(this, 0)
	});
	$("#fbForm").click(function(){
		setFocused(this, 0)
	});
	$("li.field", "#fields").live({
		mouseenter : function() {
			preFocused(this, $("li.field", "#fields").index(this) + 1)
		},
		mouseleave : function() {
			reDefault(this, $("li.field", "#fields").index(this) + 1)
		},
		click : function() {
			setFocused(this, $("li.field", "#fields").index(this) + 1)
		}
	})
}


function createRadioItemsPreview(a, d) {
	d.empty();
	var b;
	$.each(a.ITMS, function(e, c) {
		b = $(DEFFLD.item_radio_f);
		b.find("label").text(c.VAL);
		b.find(":radio").prop("checked", c.CHKED === "1");
		d.append(b)
	});
	b = $(DEFFLD.item_radio_other_f);
	if (a.OTHER === "1") {
		b.show()
	} else {
		b.hide()
	}
	d.append(b)
}


function createLikertPreview(j, f) {
	var h = j.ITMS[0].ITMS, k = j.ITMS, e = f.find("table.table>thead>tr"), d = f
			.find("table.table>tbody"), g, a;
	e.empty();
	d.empty();
	var b = Math.ceil((100 - 30) / h.length) + "%";
	$.each(h, function(l, c) {
		if (l === 0) {
			e.append("<th>&nbsp;</th>")
		}
		e.append($.tmpl('<td style="width:${width}">${val}</td>', {
			width : b,
			val : c.VAL
		}))
	});
	$.each(k, function(c, l) {
		g = $($.tmpl("<tr><th>${$data}</th></tr>", l.alias));
		$.each(h, function(o, n) {
			a = $(DEFFLD.likert_td);
			a.find("label").text(o + 1);
			if (n.CHKED === "1") {
				a.find(":radio").prop("checked", true)
			}
			if (j.HDNM === "1") {
				a.find("label").hide()
			}
			g.append(a)
		});
		d.append(g)
	})
}
//消息开始

function propertyInit() {
	var Q = null;
	$(":radio:not('#sec_pub','#sec_pri')", "#allProps").live( {
		click : function() {
			if (Q === this) {
				$(Q).prop("checked", false);
				Q = null
			} else {
				Q = this
			}
			CHANGED = true
		}
	});
	function q(R) {
		if (Q == R) {
			Q.checked = false;
			Q = null
		} else {
			Q = R
		}
	}
	var c = function(U, V) {
		var Z = $("#prepop");
		var Y = [], T = Z.val().split("\n");
		for (i = 0, i1 = 0; i < T.length; i++) {
			var X = {
				VAL : T[i],
				CHKED : "0"
			};
			if (V == "radio" || V == "dropdown") {
				if (F[IDX].ITMS[i1] && F[IDX].ITMS[i1].ITMID) {
					X.ITMID = F[IDX].ITMS[i1].ITMID
				}
			}
			if (V == "checkbox") {
				if (F[IDX].ITMS[i1] && F[IDX].ITMS[i1].NM) {
					X.NM = F[IDX].ITMS[i1].NM
				}
			}
			if (V == "dropdown2" && T[i].indexOf("-") == -1) {
				var R = 0, S = [];
				i++;
				while (T[i] && T[i].indexOf("-") == 0) {
					var W = {
						VAL : T[i].substring(1)
					};
					if (F[IDX].ITMS[i1] && F[IDX].ITMS[i1].ITMS[R]
							&& F[IDX].ITMS[i1].ITMS[R].ITMID) {
						W.ITMID = F[IDX].ITMS[i1].ITMS[R].ITMID
					}
					S.push(W);
					R++;
					i++;
					if (T[i] && T[i].indexOf("-") == -1) {
						i--;
						break
					}
				}
				X.ITMS = S;
				i1++
			}
			Y.push(X)
		}
		createItems(U, V, Y);
		if (V === "radio" || V === "dropdown" || V == "checkbox"
				|| V == "dropdown2") {
			F[IDX].ITMS = Y
		} else {
			if (V === "likertCol") {
				$.each(F[IDX].ITMS, function(ac, ab) {
					var aa = JSON.parse(JSON.stringify(Y));
					$.each(aa, function(ad, ae) {
						if (F[IDX].ITMS[ac] && F[IDX].ITMS[ac].ITMS[ad]
								&& F[IDX].ITMS[ac].ITMS[ad].ITMID) {
							aa[ad].ITMID = F[IDX].ITMS[ac].ITMS[ad].ITMID
						}
					});
					ab.ITMS = aa
				})
			}
		}
	}, y = function(R) {
		c($("#itemList"), R);
		if (F[IDX].type === "radio") {
			createRadioItemsPreview(F[IDX], $("#f" + IDX).find("div.content"))
		} else {
			if (F[IDX].type === "dropdown") {
				$("#f" + IDX).find("select").empty()
			}
		}
	};
	$("#btnItemsPredefine").click(function() {
		if ($.browser.msie && $.browser.version === "6.0") {
			$("#lightBox").css("margin-top", $(document).scrollTop() - 210)
		}
		$.lightBox({
			url : "/rs/html/predefinechoices.html",
			confirm : function() {
				y("radio");
				CHANGED = true
			},
			loaded : function() {
				var R = "";
				$.each(F[IDX].ITMS, function(S, T) {
					if (S > 0) {
						R += "\n"
					}
					R += T.VAL
				});
				$("#prepop").val(R)
			}
		});
		$("li a", "#choiceMenu").live("click", function() {
			$("#prepop").val($(this).attr("list").replace(/;/gi, "\n"));
			return false
		});
		return false
	});
	$("#btnItemsBatch")
			.click(
					function() {
						if ($.browser.msie && $.browser.version === "6.0") {
							$("#lightBox").css("margin-top",
									$(document).scrollTop() - 210)
						}
						$
								.lightBox({
									url : "/rs/html/itembatchedit.html",
									confirm : function() {
										y(F[IDX].type);
										$("#itemList").find(
												"input[name='VAL']:first")
												.trigger("focus");
										CHANGED = true
									},
									loaded : function() {
										var R = "";
										if (F[IDX].type == "dropdown2") {
											$
													.each(
															F[IDX].ITMS,
															function(S, T) {
																R += T.VAL
																		+ "\n";
																$
																		.each(
																				F[IDX].ITMS[S].ITMS,
																				function(
																						U,
																						V) {
																					R += "-"
																							+ V.VAL;
																					if (S != F[IDX].ITMS.length - 1
																							|| U != F[IDX].ITMS[S].ITMS.length - 1) {
																						R += "\n"
																					}
																				})
															})
										} else {
											$
													.each(
															F[IDX].ITMS,
															function(S, T) {
																R += T.VAL;
																if (S != F[IDX].ITMS.length - 1) {
																	R += "\n"
																}
															})
										}
										$("#prepop").val(R)
									}
								});
						$("li a", "#choiceMenu").live(
								"click",
								function() {
									$("#prepop").val(
											$(this).attr("list").replace(/;/gi,
													"\n"));
									return false
								});
						return false
					});
	$("#btnLikertPredefine").click(function(){
		if ($.browser.msie && $.browser.version === "6.0") {
			$("#lightBox").css("margin-top", $(document).scrollTop() - 210)
		}
		$.lightBox({
			url : "/rs/html/predefinelikert.html",
			confirm : function() {
				c($("#likertCols"), "likertCol");
				createLikertPreview(F[IDX], $("#f" + IDX).find("div.content"));
				CHANGED = true
			},
			loaded : function() {
				var R = "";
				$.each(F[IDX].ITMS[0].ITMS, function(S, T) {
					if (S > 0) {
						R += "\n"
					}
					R += T.VAL
				});
				$("#prepop").val(R)
			}
		});
		$("li a", "#choiceMenu").live("click", function() {
			$("#prepop").val($(this).attr("list").replace(/;/gi, "\n"));
			return false
		});
		return false
	});
	
	var s = function() {
		var S = $("#f" + IDX), R = S.find("label.desc span");
		S.find("label:first").text($(this).val()).append(R);
		F[IDX].alias = $.trim($(this).val());
		//c.alias = $.trim($(this).val());
		chargeUpdateFlag("modify");
		chargeUpdateFlag("update");
		CHANGED = true;
	};
	//名称
	$("#lbl").bind({
		keyup : s,
		change : s
	});
	//html表单项
	$("#type").change(function(){
		var T = $("#f" + IDX), R = F[IDX];
		R.type = $(this).val();
		if (R.ITMS) {
			R.ITMS = DEFFLD[R.type].json.ITMS
		}
		if (R.SUBFLDS) {
			R.SUBFLDS = DEFFLD[R.type].json.SUBFLDS
		}
		var manipulation=F[IDX].manipulation;
		setDefFieldDom(T, R.type, IDX, R);
		var S = IDX;
		IDX = -2;
		setFocused(T, S + 1);
		if(manipulation=="save")
			F[IDX].manipulation="update";
		CHANGED = true
	});
	
	$("#formColumnNum").change(function(){
		M["formColumnNum"]=$(this).val();
		changeTableFlag("update");
		CHANGED = true;
	})
	
	//多字段布局
	$("#layout").change(function(){
				$("#f" + IDX).removeClass("one two three oneByOne").addClass($(this).val());
				F[IDX].LAY = $(this).val();
				CHANGED = true
			});
	$("#dateformat").change(
			function(){
				$("#f" + IDX).find("div.content").html(
						DEFFLD["date_" + $(this).val()]);
				F[IDX].FMT = $(this).val();
				CHANGED = true
			});
	$("#nameformat").change(function() {
		var R;
		if ($(this).val() === "extend") {
			R = "name_extend_" + M.LANG
		} else {
			R = "name_" + $(this).val()
		}
		$("#f" + IDX).find("div.content").html(DEFFLD[R]);
		F[IDX].FMT = $(this).val();
		CHANGED = true
	});
	$("#moneyfomat").change(function() {
		$("#f" + IDX).find("div.content>b").text(currencys[$(this).val()]);
		F[IDX].FMT = $(this).val();
		CHANGED = true
	});
	var r = function() {
		var R = $("#likertRows>li>:text").index(this);
		$("#f" + IDX).find("div.content>table>tbody>tr:eq(" + R + ") th").text(
				$(this).val());
		F[IDX].ITMS[R].alias = $.trim($(this).val());
		CHANGED = true
	};
	$("#likertRows>li>:text").live( {
		keyup : r,
		change : r
	});
	var a = function(S) {
		var R = $(S).parent().index();
		$(S).parent().remove();
		$("#f" + IDX).find("div.content>table>tbody>tr:eq(" + R + ")").remove();
		F[IDX].ITMS.splice(R, 1);
		CHANGED = true
	};
	$("#likertRows>li>a.icononly-del").live( {
		click : function() {
			var T = this, S = $(this).parent().index();
			if (F[IDX].ITMS[S].NM) {
				if (confirm(delConfirmMsg)) {
					$.showStatus("正在执行删除操作 ...");
					var R = "deleteItem";
					/**$.postJSON(R, {
						_id : M._id,
						FLDID : F[IDX].FLDID,
						NM : F[IDX].ITMS[S].NM
					}, function() {
						a(T);
						$.hideStatus()
					})
					*/
				}
			} else {
				a(T)
			}
			$.autoHeight()
		}
	});
	$("#likertRows>li>a.icononly-add").live(
			{
				click : function() {
					var R = $("#likertRows>li>a.icononly-add").index(this);
					var T = $(this).parent().clone();
					if (R === 0) {
						T.append(DEFFLD.icon_del)
					}
					$(this).parent().after(T);
					var S = $("#f" + IDX).find(
							"div.content>table>tbody>tr:eq(" + R + ")");
					S.after($(S).clone());
					var U = JSON.parse(JSON.stringify(F[IDX].ITMS[R]));
					delete U.FLDID;
					delete U.NM;
					$.each(U.ITMS, function(V, W) {
						delete W.ITMID
					});
					F[IDX].ITMS.splice(R + 1, 0, U);
					CHANGED = true;
					$.autoHeight()
				}
			});
	var z = function() {
		var S = $.trim($(this).val()), R = $("#likertCols>li>:text")
				.index(this);
		$("#f" + IDX).find("div.content>table>thead>tr>td:eq(" + R + ")").text(
				S);
		$.each(F[IDX].ITMS, function(U, T) {
			T.ITMS[R].VAL = S
		});
		CHANGED = true
	};
	$("#likertCols>li>:text").live( {
		keyup : z,
		change : z
	});
	$("#likertCols>li>:radio").live(
			{
				click : function() {
					var R = $("#likertCols>li>:radio").index(this), S = $(this)
							.prop("checked");
					$("div.content>table>tbody :radio").prop("checked", false);
					$("#f" + IDX).find("div.content>table>tbody>tr").each(
							function(T, U) {
								$("td:eq(" + R + ")", U).find(":radio").prop(
										"checked", S)
							});
					$.each(F[IDX].ITMS, function(U, T) {
						$.each(T.ITMS, function(V, W) {
							W.CHKED = "0"
						});
						T.ITMS[R].CHKED = S ? "1" : "0"
					});
					F[IDX].htmlVal = S ? "" + R : "";
					CHANGED = true
				}
			});
	$("#likertCols>li>a.icononly-add")
			.live(
					{
						click : function() {
							var R = $("#likertCols>li>a.icononly-add").index(
									this);
							var S = $(this).parent().clone().find(":radio")
									.prop("checked", false).end();
							if (R === 0) {
								S.append(DEFFLD.icon_del)
							}
							$(this).parent().after(S);
							$("#f" + IDX)
									.find("div.content>table tr")
									.each(
											function(T, V) {
												var W = $("td:eq(" + R + ")", V);
												W.after(W.clone()
														.find(":radio").prop(
																"checked",
																false).end());
												if (T > 0) {
													$("td", V)
															.each(
																	function(Z,
																			aa) {
																		if (Z > R) {
																			var X = $(
																					aa)
																					.find(
																							"label"), Y = parseInt(X
																					.text()) + 1;
																			X
																					.text(Y)
																		}
																	})
												} else {
													if (T === 0) {
														var U = Math
																.ceil((100 - 30)
																		/ (F[IDX].ITMS[0].ITMS.length + 1))
																+ "%";
														$("td", V)
																.each(
																		function(
																				X,
																				Y) {
																			$(Y)
																					.css(
																							"width",
																							U)
																		})
													}
												}
											});
							$.each(F[IDX].ITMS, function(U, T) {
								var V = JSON.parse(JSON.stringify(T.ITMS[R]));
								V.CHKED = "0";
								delete V.ITMID;
								T.ITMS.splice(R + 1, 0, V)
							});
							CHANGED = true
						}
					});
	var K = function(S) {
		var R = $("#likertCols>li>a.icononly-del").index(S) + 1;
		$(S).parent().remove();
		$("#f" + IDX)
				.find("div.content>table tr")
				.each(
						function(T, V) {
							if (T > 0) {
								$("td", V)
										.each(
												function(Y, Z) {
													if (Y > R) {
														var W = $(Z).find(
																"label"), X = parseInt(W
																.text()) - 1;
														W.text(X)
													}
												})
							} else {
								if (T === 0) {
									var U = Math.ceil((100 - 30)
											/ (F[IDX].ITMS[0].ITMS.length - 1))
											+ "%";
									$("td", V).each(function(W, X) {
										$(X).css("width", U)
									})
								}
							}
							$("td:eq(" + R + ")", V).remove()
						});
		$.each(F[IDX].ITMS, function(U, T) {
			T.ITMS.splice(R, 1)
		});
		CHANGED = true
	};
	$("#likertCols>li>a.icononly-del").live({
		click : function() {
			var T = this, S = $("#likertCols>li>a.icononly-del").index(T) + 1;
			if (F[IDX].ITMS[0].ITMS[S].ITMID){
				if (confirm(delConfirmMsg)){
					$.showStatus("正在执行删除操作 ...");
					var R = "deleteLikertCol";
					$.postJSON(R, {
						_id : M._id,
						FLDID : F[IDX].FLDID,
						COLINDEX : S
					}, function() {
						K(T);
						$.hideStatus()
					})
				}
			} else {
				K(T)
			}
			CHANGED = true;
			return false
		}
	});
	//非空验证
	$("#reqd").click(function(){
		var S = $(this).prop("checked"), R = $("#f" + IDX).find("span.req");
		if (S) {
			R.show()
		} else {
			R.hide()
		}
		F[IDX].notNull = S ? "1" : "0";
		chargeUpdateFlag("update");
		CHANGED = true
	});
	$("#valid_join").change(function() {
		var _v=$(this).val();
		F[IDX].valid =_v;
		chargeUpdateFlag("update");
		CHANGED = true
	});
	var b = function(T) {
		if ($(T).parent().hasClass("dropReq")) {
			$(T).parent().find(":radio,:checkbox").prop("checked", false).end()
					.hide();
			span = $("span:last", "#f" + IDX).find(":radio,:checkbox").prop(
					"checked", false).end().hide();
			$("#allowOther").prop("checked", false);
			return
		}
		var S = $(T).parent().index(), U = $(T).parent().parent();
		$(T).parent().remove();
		$("#f" + IDX).find("div.content>span:eq(" + S + ")").remove();
		$("#f" + IDX).find("div.content>div.goods-item:eq(" + S + ")").remove();
		if (F[IDX].type === "goods"
				&& $("#f" + IDX).find("div.content>div.goods-item").length == 0) {
			$("#f" + IDX).find("div.content").empty().append(
					'<div class="nogoods-holder">请在左侧添加商品</div>')
		}
		if ("itemList2" == U.attr("id")) {
			var R = parseInt(U.attr("IDX"));
			F[IDX].ITMS[R].ITMS.splice(S, 1)
		} else {
			F[IDX].ITMS.splice(S, 1);
			if ($("#itemList2").is(":visible")) {
				$("#itemList2").hide()
			}
		}
	};
	var B = function() {
		var T = this, S = $(this).parent().index();
		if (F[IDX].ITMS[S].NM || F[IDX].ITMS[S].ITMID) {
			if (confirm(delConfirmMsg)) {
				$.showStatus("正在执行删除操作 ...");
				var R = "deleteItem", U;
				if (F[IDX].ITMS[S].NM) {
					U = {
						_id : _id,
						FLDID : F[IDX].FLDID,
						NM : F[IDX].ITMS[S].NM
					};
					if (F[IDX].ITMS[S].IMG) {
						U.IMG = F[IDX].ITMS[S].IMG
					}
				} else {
					U = {
						_id : M._id,
						FLDID : F[IDX].FLDID,
						ITMID : F[IDX].ITMS[S].ITMID,
						TYP : F[IDX].TYP
					}
				}
				$.postJSON(R, U, function() {
					b(T);
					$.hideStatus()
				})
			}
		} else {
			b(T)
		}
		CHANGED = true;
		$.autoHeight();
		chargeUpdateFlag("update");
		return false
	}, d = function() {
		var T = this, S = $(this).parent().index(), U = $("#itemList2"), R = parseInt(U
				.attr("IDX"));
		if (F[IDX].ITMS[R].ITMS[S].ITMID) {
			if (confirm(delConfirmMsg)) {
				$.showStatus("正在执行删除操作 ...");
				var V = {
					_id : M._id,
					FLDID : F[IDX].FLDID,
					ITMID1 : F[IDX].ITMS[R].ITMID,
					ITMID : F[IDX].ITMS[R].ITMS[S].ITMID,
					TYP : "dropdown"
				};
				$.postJSON("deleteItem", V, function() {
					b(T);
					$.hideStatus()
				})
			}
		} else {
			b(T)
		}
		CHANGED = true;
		$.autoHeight();
		return false
	};
	$("a.icononly-del", "#itemList2").live( {
		click : d
	});
	$("a.icononly-del", "#itemList").live( {
		click : B
	});
	/**$("a.icononly-del", "#goodsList").live( {
		click : B
	});*/
	$("#itemList").find("a.icononly-add").live(
			{
				click : function() {
					var R = $("a.icononly-add", "#itemList").index(this);
					var T = $(this).parent().clone();
					T.find(":radio,:checkbox").prop("checked", false);
					if (R === 0) {
						T.append(DEFFLD.icon_del)
					}
					$(this).parent().after(T);
					var S = $("#f" + IDX)
							.find("div.content>span:eq(" + R + ")");
					S.after($(S).clone().find(":radio,:checkbox").prop(
							"checked", false).end());
					var U = JSON.parse(JSON.stringify(F[IDX].ITMS[R]));
					U.CHKED = "0";
					delete U.ITMID;
					delete U.NM;
					if (U.ITMS) {
						U.ITMS = [ {
							VAL : "选项 1"
						}, {
							VAL : "选项 2"
						}, {
							VAL : "选项 3"
						} ]
					}
					F[IDX].ITMS.splice(R + 1, 0, U);
					T.find(":text").focus();
					CHANGED = true;
					$.autoHeight();
					return false
				}
			});
	$("#itemList2").find("a.icononly-add")
			.live(
					{
						click : function() {
							var S = $("a.icononly-add", "#itemList2").index(
									this), R = parseInt($("#itemList2").attr(
									"IDX"));
							var T = $(this).parent().clone();
							if (S === 0) {
								T.append(DEFFLD.icon_del)
							}
							$(this).parent().after(T);
							var U = JSON.parse(JSON
									.stringify(F[IDX].ITMS[R].ITMS[S]));
							delete U.ITMID;
							delete U.NM;
							F[IDX].ITMS[R].ITMS.splice(S + 1, 0, U);
							CHANGED = true;
							$.autoHeight();
							return false
						}
					});
	var G = function() {
		if ($(this).parent().hasClass("dropReq")) {
			return
		}
		var R = $(":text", "#itemList").index(this);
		$("#f" + IDX).find("div.content>span:eq(" + R + ") label").text(
				$(this).val());
		F[IDX].ITMS[R].VAL = $.trim($(this).val());
		chargeUpdateFlag("update")
		CHANGED = true
	}, u = function() {
		var S = $(":text", "#itemList2").index(this), R = parseInt($(
				"#itemList2").attr("IDX"));
		F[IDX].ITMS[R].ITMS[S].VAL = $.trim($(this).val()).replace(/[',\,]/g,
				"");
		CHANGED = true
	}, l = function() {
		if (F[IDX].TYP != "dropdown2") {
			return
		}
		var R = $(":text", "#itemList").index(this), S = $("#itemList2");
		S.show().empty();
		$.each(F[IDX].ITMS[R].ITMS, function(U, T) {
			itm = $(DEFFLD.item_dropdown2);
			itm.find(":text").val(T.VAL);
			S.append(itm)
		});
		S.attr("IDX", R).find("a.icononly-del:first").remove();
	};
	$(":text", "#itemList").live( {
		keyup : G,
		change : G,
		focus : l
	});
	$(":text", "#itemList2").live( {
		keyup : u,
		focuout : u
	});
	$(":radio", "#itemList")
			.live({
						click : function() {
							var S = $(":radio", "#itemList").index(this), T = $(
									this).prop("checked");
							$(":radio", "#f" + IDX).prop("checked", false);
							$("select", "#f" + IDX).empty();
							$.each(F[IDX].ITMS, function(V, U) {
								U.CHKED = "0"
							});
							if (F[IDX].type === "radio") {
								$("#f" + IDX)
										.find(
												"div.content>span:eq(" + S
														+ ") :radio").prop(
												"checked", T)
							} else {
								if ((F[IDX].type === "dropdown" || F[IDX].type === "dropdown2")
										&& T) {
									var R = $(this).parent().find(":text")
											.val();
									if (R) {
										$("#f" + IDX)
												.find("select:first")
												.empty()
												.append(
														$
																.tmpl(
																		"<option>${$data}</option>",
																		R))
									}
								}
							}
							F[IDX].ITMS[S].CHKED = T ? "1" : "0";
							F[IDX].htmlVal = T ? "" + S : "";
							CHANGED = true
							chargeUpdateFlag("update");
						}
					});
	$(":checkbox", "#itemList")
			.live(
					{
						click : function() {
							var R = $(":checkbox", "#itemList").index(this), S = $(
									this).prop("checked");
							$("#f" + IDX).find(
									"div.content>span:eq(" + R + ") :checkbox")
									.prop("checked", S);
							F[IDX].ITMS[R].CHKED = S ? "1" : "0";
							CHANGED = true
							chargeUpdateFlag("update");
						}
					});
	var C = function() {
		var R = $("#goodsList").find("li").has(this), T = R.index(), S = $
				.trim($(this).val());
		$("#f" + IDX).find("div.content>div:eq(" + T + ")").find("label.name")
				.text(S);
		F[IDX].ITMS[T].VAL = S;
		R.find("a.goods-name-view").text(S);
		CHANGED = true
	}, H = function() {
		var R = $("#goodsList").find("li").has(this).index();
		$("#f" + IDX).find("div.content>div:eq(" + R + ")").find("label.des")
				.html($(this).val());
		F[IDX].ITMS[R].DES = $.trim($(this).val());
		CHANGED = true
	}, E = function() {
		var R = $("#goodsList").find("li").has(this).index();
		F[IDX].ITMS[R].PRC = parseFloat($.trim($(this).val())) || 0;
		$.formatPrice($("#f" + IDX).find("div.content>div:eq(" + R + ")").find(
				"div.price-number"), F[IDX].ITMS[R].PRC, F[IDX].ITMS[R].CNY,
				F[IDX].FBUY, F[IDX].ITMS[R].UNT, F[IDX].ITMS[R].htmlVal);
		CHANGED = true
	}, e = function() {
		var R = $("#goodsList").find("li").has(this).index();
		F[IDX].ITMS[R].CNY = $(this).val();
		$.formatPrice($("#f" + IDX).find("div.content>div:eq(" + R + ")").find(
				"div.price-number"), F[IDX].ITMS[R].PRC, F[IDX].ITMS[R].CNY,
				F[IDX].FBUY, F[IDX].ITMS[R].UNT, F[IDX].ITMS[R].htmlVal);
		CHANGED = true
	}, m = function() {
		var R = $("#goodsList").find("li").has(this).index();
		F[IDX].ITMS[R].UNT = $.trim($(this).val());
		$.formatPrice($("#f" + IDX).find("div.content>div:eq(" + R + ")").find(
				"div.price-number"), F[IDX].ITMS[R].PRC, F[IDX].ITMS[R].CNY,
				F[IDX].FBUY, F[IDX].ITMS[R].UNT, F[IDX].ITMS[R].htmlVal);
		CHANGED = true
	}, N = function() {
		var R = $("#goodsList").find("li").has(this).index();
		F[IDX].ITMS[R].htmlVal = $.trim($(this).val());
		$.formatPrice($("#f" + IDX).find("div.content>div:eq(" + R + ")").find(
				"div.price-number"), F[IDX].ITMS[R].PRC, F[IDX].ITMS[R].CNY,
				F[IDX].FBUY, F[IDX].ITMS[R].UNT, F[IDX].ITMS[R].htmlVal);
		CHANGED = true
	}, A = function() {
		var R = $("#goodsList").find("li").has(this).index();
		if ($(this).prop("checked")) {
			F[IDX].ITMS[R].HD = "1";
			$("#f" + IDX).find("div.content>div.goods-item:eq(" + R + ")")
					.hide()
		} else {
			F[IDX].ITMS[R].HD = "0";
			$("#f" + IDX).find("div.content>div.goods-item:eq(" + R + ")")
					.show()
		}
		CHANGED = true
	}, j = function() {
		if ($(this).attr("checked")) {
			F[IDX].FBUY = "1";
			$("#goodsList").find(".goods-price-label").text("数量：")
		} else {
			F[IDX].FBUY = "0";
			$("#goodsList").find(".goods-price-label").text("单价：")
		}
		$("#f" + IDX).find("div.price-number").each(
				function(R, S) {
					$.formatPrice($(S), F[IDX].ITMS[R].PRC, F[IDX].ITMS[R].CNY,
							F[IDX].FBUY, F[IDX].ITMS[R].UNT)
				})
	};
	var p = function() {
		var R = $.trim($(this).val());
		if (R) {
			F[IDX].MIN = R
		} else {
			delete F[IDX]["MIN"]
		}
		chargeUpdateFlag("update");
		CHANGED = true
	}, J = function() {
		var R = $.trim($(this).val());
		if (R) {
			F[IDX].MAX = R
		} else {
			delete F[IDX]["MAX"]
		}
		chargeUpdateFlag("update");
		CHANGED = true
	};
	$("#min").bind({
		keyup : p
	});
	$("#max").bind({
		keyup : J
	});
	//指定数据来源并自动匹配
	//根据匹配数据自动带出值
	$("#chkMatch,#chkAutoComp").click(
					function() {
						delete F[IDX]["param"];
						delete F[IDX]["baseTable"];
						delete F[IDX]["baseField"];
						delete F[IDX]["ACMP"];
						delete F[IDX]["MAT"];
						/**if ($(this).attr("id") == "chkMatch"
								&& $(this).prop("checked")) {
							$("#chkAutoComp").prop("checked", false);
							$("#selMatchFld").empty();
							//fillMatchForms()
						} else {
							if ($(this).attr("id") == "chkAutoComp"
									&& $(this).prop("checked")) {
								$("#chkMatch").prop("checked", false);
								$("#selAutoCompFld").empty();
								fillAutoCompSrcFlds()
							}
						}*/
						var R = $("#chkMatch").prop("checked"), S = $(
								"#chkAutoComp").prop("checked");
						//关联数据库字段
						if (R) {
							$("#divMatchContainer").removeClass("hide");
							F[IDX]["MAT"] = "1"
							F[IDX].baseTable=$("#selMatchFrm").val();
							F[IDX].baseField=$("#selMatchFld").val();
						} else {
							$("#divMatchContainer").addClass("hide")
						}
						if (S) {
							$("#divAutoCompContainer").removeClass("hide");
							F[IDX]["ACMP"] = "1"
						//	F[IDX].param=$("#selAutoCompSrcFld").val();
						} else {
							$("#divAutoCompContainer").addClass("hide")
						}
						CHANGED = true
					});
	$("#selMatchFrm").change(function() {
		var R = $(this).val();
		fillMatchFields(R, F[IDX].type,F[IDX].baseField);
		F[IDX].baseTable=R;
		CHANGED = true
	});
	$("#selMatchFld").change(function() {
//		F[IDX].MATFLD = $(this).val();
		F[IDX].baseField=$(this).val();
		CHANGED = true
	});
	$("#selAutoCompSrcFld").change(function(){
		var R = $(this).val(), S = "";
		//F[IDX].ACMPSRCFLD = R;
		F[IDX].param=R;
		//fillAutoCompFlds(R, F[IDX].type);
		CHANGED = true
	});
	var f = function() {
		var R = $.trim($(this).val());
		if ("defval_number" === $(this).attr("id")) {
			R = R.replace(/[^(\d\.?)]/g, "").replace(/[\(\)\?]/g, "");
			$(this).val(R)
		}
		$("#f" + IDX).find("div.content :input").val(R);
		if (R) {
			F[IDX].htmlVal = R
		} else {
			delete F[IDX]["htmlVal"]
		}
		chargeUpdateFlag("update");
		CHANGED = true
	};
	$("#defval_text").bind( {
		keyup : f,
		fucusout : f
	});
	$("#defval_number").bind( {
		keyup : f
	});
	var h = function() {
		var R = $.trim($(this).val());
		if (R) {
			F[IDX].htmlVal = R
		} else {
			delete F[IDX]["htmlVal"]
		}
		CHANGED = true
	};
	$("#defval_date").bind( {
		keyup : h
	});
	$("#defval_time").bind( {
		keyup : h
	});
	$("#phoneformat")
			.change(
					function() {
						$("#f" + IDX).find("div.content").html(
								DEFFLD[$.format("phone_{0}_{1}", $(this).val(),
										M.LANG)]);
						showPhoneFormat($(this).val());
						$("#defval_phone_mobile").val("");
						$("#defval_phone_tel").val("");
						$(
								"#defval_phone_tel_1,#defval_phone_tel_2,#defval_phone_tel_3")
								.val("");
						delete F[IDX]["htmlVal"];
						F[IDX].FMT = $(this).val();
						CHANGED = true
					});
	$("#defval_phone_mobile").bind({
		keyup : f,
		change : f
	});
	var D = function() {
		var R = $("#defval_phone_tel_1").val() + "-"
				+ $("#defval_phone_tel_2").val() + "-"
				+ $("#defval_phone_tel_3").val();
		$("#defval_phone_tel").val(R);
		$.each(R.split("-"), function(T, S) {
			$("#f" + IDX).find(":text:eq(" + T + ")").val(S)
		});
		F[IDX].htmlVal = R;
		CHANGED = true
	};
	$("#pdefval_phone_tel :input").bind( {
		keyup : D
	});
	var t = "<option value=''>省/自治区/直辖市</option>";
	$.each(address.provinces, function(S, R) {
		t += $.format('<option value="{0}">{1}</option>', S, S)
	});
	$("#defval_province").append(t);
	var o = function() {
		var R = $("#defval_province").val(), T = $("#defval_city").val(), S = $(
				"#defval_zip").val();
		if (R || T) {
			return R + "-" + T + "-" + S
		} else {
			return ""
		}
	};
	$("#defval_province")
			.change(
					function() {
						var R = $(this).val();
						$("#f" + IDX).find("select.province").empty().append(
								$.format("<option>{0}</option>", R
										|| "省/自治区/直辖市"));
						$("#f" + IDX).find("select.city").empty().append(
								$.format('<option value="">{0}</option>', "市"));
						$("#f" + IDX)
								.find("select.zip")
								.empty()
								.append(
										$
												.format(
														'<option value="">{0}</option>',
														"区/县"));
						$("#defval_city").empty().append(
								"<option value=''>市</option>");
						$("#defval_zip").empty().append(
								"<option value=''>区/县</option>");
						if (R) {
							var S = "";
							$.each(address.provinces[R], function(U, T) {
								S += $.format(
										'<option value="{0}">{1}</option>', U,
										U)
							});
							$("#defval_city").append(S)
						}
						F[IDX].htmlVal = o();
						if (!F[IDX].htmlVal) {
							delete F[IDX].htmlVal
						}
						CHANGED = true
					});
	$("#defval_city").change(
			function() {
				var T = $(this).val(), R = $("#defval_province").val();
				$("#f" + IDX).find("select.city").empty().append(
						$.format('<option value="">{0}</option>', T || "市"));
				$("#f" + IDX).find("select.zip").empty().append(
						$.format('<option value="">{0}</option>', "区/县"));
				$("#defval_zip").empty()
						.append("<option value=''>区/县</option>");
				if (T) {
					var S = "";
					$.each(address.provinces[R][T], function(V, U) {
						S += $.format('<option value="{0}">{1}</option>', U, U)
					});
					$("#defval_zip").append(S)
				}
				F[IDX].htmlVal = o();
				if (!F[IDX].htmlVal) {
					delete F[IDX].htmlVal
				}
				CHANGED = true
			});
	$("#defval_zip").change(
			function() {
				var R = $(this).val();
				$("#f" + IDX).find("select.zip").empty().append(
						$.format('<option value="">{0}</option>', R || "区/县"));
				F[IDX].htmlVal = o();
				if (!F[IDX].htmlVal) {
					delete F[IDX].htmlVal
				}
				CHANGED = true
			});
	$("#isParent").change(function(){
		var _v=$(this).val();
		/*if(_v.length>0){
			$.ajax({
				url:resRoot+'/autoform/fieldSingle?tbId='+_v,
				dataType:'json',
				type:'post',
				success:function(data){
					var re=new Array();
					if(data.length>0){
						//re.push("<select>");
						re.push("<option value=\"\" >-请选择字段-</option>");
						for(var i in data){
							re.push("<option value=\""+data[i].fieldName+"\" >"+data[i].fieldAlias+"</option>");
						}
						//re.push("</select>");
						$("#dataField").html(re.join(""));
					}
				}
			})	}*/

			//setFocused(this, 0);
			//F[IDX].baseTable=_v;
			M["isParent"]=_v;
			CHANGED = true
			changeTableFlag("update");
	})
	var v = function(R) {
		if (R) {
			M.formDesc = "1"
		} else {
			M.formDesc = "0";
			$.each(F, function(T, S) {
				if (S.formDesc) {
					M.formDesc = "1";
					return false
				}
			})
		}
		$.each(F, function(T, S) {
			if (M.formDesc === "1" && S.type !== "likert" && S.type !== "html"
					&& S.type !== "section" && S.type !== "goods"
					&& S.type !== "image") {
				$("#f" + T).addClass("fieldInstruct")
			} else {
				if (M.formDesc !== "1") {
					$("#f" + T).removeClass("fieldInstruct")
				}
			}
		})
	}, k = function(R){
		var T = $("#f" + IDX).find("p.instruct"), S = $.trim($(R).val());
		T.text(S);
		if (S) {
			T.show()
		} else {
			T.hide()
		}
		if (S) {
			F[IDX].formDesc = S
		} else {
			delete F[IDX]["formDesc"]
		}
		CHANGED = true
	};
	$("#instruct").bind({
		keyup : function() {
			k(this);
			if ((M.formDesc !== "1" && $(this).val())
					|| (M.formDesc === "1" && !$(this).val())) {
				v($(this).val());
				chargeUpdateFlag("update");
			}
		},
		change : function() {
			k(this);
			v($(this).val());
			chargeUpdateFlag("update");
		}
	});
	var I = function(){
		var R = $.trim($(this).val());
		$("#f" + IDX).find("div.content").text(R);
		if (R) {
			F[IDX].SECDESC = R
		} else {
			delete F[IDX]["SECDESC"]
		}
	};
	$("#secdesc").bind({
		keyup : I,
		change : I
	});
	var g = function() {
		var R = $.trim($(this).val());
		$("#f" + IDX).find("div.content").html(
				R.replace(/<script[\S\s]*<\/script>/gim, ""));
		if (R) {
			F[IDX].HTML = R
		} else {
			delete F[IDX]["HTML"]
		}
	};
	$("#html").bind({
		keyup : g,
		change : g
	});
	var P = function() {
		var R = $.trim($(this).val());
		if (R) {
			F[IDX].CSS = $(this).val()
		} else {
			delete F[IDX]["CSS"]
		}
		chargeUpdateFlag("update");
		CHANGED = true
	};
	$("#css").bind( {
		keyup : P,
		change : P
	});
	var L = function() {
		var R = $.trim($(this).val());
		if (R) {
			F[IDX].LAYOUT = $(this).val()
		} else {
			delete F[IDX]["LAYOUT"]
		}
		chargeUpdateFlag("update");
		CHANGED = true
	};
	$("#selLayout").bind({
		click : L
	});
	/**
	$("#exprop").bind({
		change : function() {
			var R = $.trim($(this).val());
			if (R) {
				F[IDX].EX = $(this).val()
			} else {
				delete F[IDX]["EX"]
			}
			CHANGED = true
		}
	});
	*/
	$("#btnDup,#fields a.faDup").live(
			{
				click : function() {
					var U = $("#fields>li.field").size();
					if (U >= fieldsLimit) {
						$.alert("最多只能添加" + fieldsLimit + "个字段。");
						return false
					}
					var T = JSON.parse(JSON.stringify(F[IDX])), R = $(
							"#f" + IDX).clone();
					R.find("img.arrow,p.instruct,div.fieldActions").hide();
					R.removeClass("prefocus focused").addClass("default");
					$("#f" + IDX).after(R);
					$("li.field", "#fields").each(function(Y, X) {
						if (Y > IDX) {
							$(X).attr("id", "f" + Y)
						}
					});
					delete T.NM;
					delete T.FLDID;
					if (T.type === "likert") {
						$.each(T.ITMS, function(X, Y) {
							$.each(Y.ITMS, function(Z, aa) {
								delete aa.ITMID
							})
						})
					} else {
						if (T.type === "address") {
							delete T.SUBFLDS["ZIP"]["NM"];
							delete T.SUBFLDS["PRV"]["NM"];
							delete T.SUBFLDS["CITY"]["NM"];
							delete T.SUBFLDS["DTL"]["NM"]
						} else {
							if (T.type === "map") {
								delete T.SUBFLDS["TXT"]["NM"];
								delete T.SUBFLDS["J"]["NM"];
								delete T.SUBFLDS["W"]["NM"]
							} else {
								if (T.type === "file") {
									delete T.SUBFLDS["ID"]["NM"];
									delete T.SUBFLDS["type"]["NM"];
									delete T.SUBFLDS["SZ"]["NM"];
									delete T.SUBFLDS["NM"]["NM"]
								} else {
									if (T.type === "dropdown2") {
										delete T.SUBFLDS["DD1"]["NM"];
										delete T.SUBFLDS["DD2"]["NM"]
									} else {
										if (T.type === "image") {
											var V = [ T.IMG ];
											$.showStatus("正在复制字段...");
											$.postJSON("duplicateimages", {
												FRMID : M._id,
												IMGS : V
											}, function(X) {
												T.IMG = X[0];
												$.hideStatus()
											})
										}
									}
								}
							}
						}
					}
					if (T.ITMS) {
						if (T.type === "goods") {
							var V = [];
							$.each(T.ITMS, function(X, Y) {
								if (Y.IMG) {
									V.push(Y.IMG)
								}
							});
							if (V.length > 0) {
								$.showStatus("正在复制字段...");
								$.postJSON("duplicategoods", {
									FRMID : M._id,
									IMGS : V
								}, function(X) {
									$.each(T.ITMS, function(Y, Z) {
										T.ITMS[Y].IMG = X[Y]
									});
									$.hideStatus()
								})
							}
						}
						$.each(T.ITMS, function(X, Y) {
							delete Y.ITMID;
							delete Y.NM;
							if (T.type === "dropdown2") {
								$.each(Y.ITMS, function(Z, aa) {
									delete aa.ITMID
								})
							}
						})
					}
					F.splice(IDX + 1, 0, T);
					CHANGED = true;
					return false
				}
			});
	var O = function() {
		$("#f" + IDX).remove();
		if(F[IDX].manipulation!="add"){
			if(edit=="true"){
				F[IDX].manipulation="delete";
				M.tableManipulation="delete";
				var cc=F[IDX];
				F.splice(IDX, 1);
				F[F.length]=cc;
			}
		}else{
			F.splice(IDX, 1);
		}
		IDX = -2;
		v("");
		$("#allProps").hide();
		$("#noFieldSelected").show();
		if ($.isEmptyObject(F)) {
			$("#nofields").show();
			$("#formButtons").hide()
		}
		$("li.field", "#fields").each(function(S, R) {
			if (S >= IDX) {
				$(R).attr("id", "f" + S)
			}
		})
	};
	$("#btnDel,#fields a.faDel").live(
			{
				click : function() {
					if (F[IDX].FLDID !== undefined && F[IDX].type !== "section"
							&& F[IDX].type !== "html") {
						if (confirm(delConfirmMsg)) {
						
							$.showStatus("正在执行删除操作 ...");
							var R = "deleteField";
							$.postJSON(R, {
								_id : M._id,
								FLDID : F[IDX].FLDID,
								type : F[IDX].type
							}, function() {
								O();
								$.hideStatus()
							})
						}
					} else {
						O();
					}
					CHANGED = true;
					return false
				}
			})
}

//结束
function saveForm(d, e) {
	// M.HEIGHT = $("#right").outerHeight() + 50;
	delete M.F;
	var a = $("#liSale").getValues();
	if (a.SALE == "1") {
		$.extend(true, M, a)
	} else {
		delete M.SALE;
		delete M.SALEM;
		delete M.SALEJ
	}
	var c = {
		M : M,
		F : F
	}, b = "save";
	if (d) {
		var len=F.length;
		for(var i=0;i<len;i++){
			var f=F[i];
		/*	if(typeof f.name=="undefined"||f.name==""){
				$.alert(f.alias+"：字段名称不能为空");
				return;
			}*/
		/*	if(typeof f._dataType=="undefined"||f._dataType==""){
				$.alert(f.alias+"：数据类型不能为空");
				return;
			}*/
			
			if(typeof f._fieldLength=="undefined"||f._fieldLength==""){
				if(f._dataType!="CLOB"&&f._dataType!="TIMESTAMP"){
					$.alert(f.alias+"：字段长度不能为空");
					return;
				}
			}
			
/*			if(f._dataType=="CLOB"||f._dataType=="TIMESTAMP"){
				if(typeof f._fieldLength=="undefined"||f._fieldLength==""){
					
				}else{
					$.alert(f.alias+"：类型为CLOB和TIMESTAMP不能输入字段长度");
					return;
				}
			}*/
			
		/*	if(typeof f._aliasName=="undefined"||f._aliasName==""){
				$.alert(f.alias+"：别名不能为空");
				return;
			}*/
		}
	/*	if(document.getElementById("noBase").checked&&$("#isParent").val()==""){
			$.alert("子表必须选择主表");
			return;
		}*/
		if(M.tableManipulation=="empty"){
			M.tableManipulation="add";
		}
			$.showStatus("正在保存表单数据 ...")
			$.ajax({
				url:resRoot+'/autoform/formOrg',
				dataType:'json',
				type:'post',
				data:{
					"form":JSON.stringify(M),
					"fields":JSON.stringify(F),
					"service":service
				},
				success:function(data){
					if(data.result=="success"){
						window.location.href=resRoot+"/autoform/formList";
					}else{
						$.alert(data.msg);
					}
					$.hideStatus();
					
				}
			});
	}
	/**
	 * $.postJSON(b, c, function(f) { $.hideStatus(); if (f.ERRMSG) {
	 * $.alert(f.ERRMSG); return } $.extend(true, M, f.M); $.extend(true, F,
	 * f.F); $("#type").prop("disabled", true); if (e) { e(M) } CHANGED = false;
	 * if (d) { $.lightBox( { url : "/rs/html/formsaved.html", loaded :
	 * function() { $("#btnPrew").attr("href", "/web/formview/" + M._id) } }) } })
	 */
	
}
function addFieldsInit() {
	$("#addFields").find("li").click(function(){
		var d = $("#fields>li").size(), b = d - 1, e = "f" + d, g = $(DEFFLD.field_li);
		if (d === 0) {
			$("#fields").append(g)
		} else {
			if (d >= fieldsLimit) {
				$.alert("最多只能添加" + fieldsLimit + "个字段。");
				return false
			}
			$(".field:eq(" + b + ")", "#fields").after(g)
		}
		g.attr("id", e);
		setDefFieldDom(g, $(this).attr("ftype")+($(this).attr("subtype") || ""), b + 1);
		CHANGED = true;
		return false
	});
	var a = false;
	$(window).scroll(function(){
		if ($("#tabs").tab("getSelectedIndex") === 0) {
			if (a) {
				clearTimeout(a)
			}
			a = setTimeout(function() {
				$("#addFields").animate( {
					marginTop : $(document).scrollTop()
				}, 500, "easeOutCubic")
			}, 100)
		}
	});
	$("#addFromButton,#btnAddField,#btnAddField2").click(function() {
		if ($("#tabs").tab("getSelectedIndex") === 0) {
			$("#addFields").effect("shake", {}, 100);
			$("#legy").find("div.ui-effects-wrapper").css( {
				height : ""
			})
		} else {
			$("#tabs").tab("select", 0);
			setFocused(null, -1);
			$("#addFields").animate( {
				marginTop : $(document).scrollTop()
			}, 300, "easeOutCubic")
		}
		return false
	});
	$("#saveForm").click(function() {
		saveForm(true);
		return false
	});
	window.onbeforeunload = function() {
		if (CHANGED) {
			return "离开此页将导致数据丢失，建议先保存数据。"
		}
	}
}
function needHandle(b) {
	var a = [ "checkbox", "radio", "likert", "html" ];
	if ($.inArray(b, a) >= 0) {
		return false
	} else {
		return true
	}
}
//创建拖动的html
function createField(k, j) {
	//h：label值显示名称，f：配置的表单元素
	//g:验证标识，
	var h, g, e, f;
	k.removeClass("one two three oneByOne fieldInstruct").addClass("field default");
	k.attr("title", "点击编辑，拖动排序");
	k.empty();
	$("#nofields").hide();
	$("#formButtons").show();
	e = $(DEFFLD[j.type].html);
	if ("goods" == j.type && "1" == j.NOIMG) {
		e = $(DEFFLD.goodsnoimg.html)
	}
	if (j.type === "html" || j.type === "section") {
		h = e.find("label.desc");
		f = e.find("div.content")
	} else {
		h = e.filter("label.desc");
		f = e.filter("div.content")
	}
	if (j.type === "likert") {
		h = f.find("label.desc")
	}
	g = h.find("span");
	if (j.notNull === "1") {
		g.removeClass("hide")
	}
	h.text(j.alias);
	h.append(g);
	if (j.type === "phone" && j.FMT) {
		f.html(DEFFLD[$.format("phone_{0}_{1}", j.FMT, M.LANG)]);
		"1" == j.AUTH ? f.find(".sendcode").show() : f.find(".sendcode").hide();
		"1" == j.AUTH ? $("#signcnt").show() : $("#signcnt").hide()
	} else {
		if (j.type === "date" && j.FMT) {
			f.html(DEFFLD[$.format("date_{0}", j.FMT)])
		} else {
			if (j.type === "name" && j.FMT) {
				if (j.FMT === "short") {
					f.html(DEFFLD.name_short)
				} else {
					f.html(DEFFLD[$.format("name_{0}_{1}", j.FMT, M.LANG)])
				}
			} else {
				if (j.type === "address") {
					f.html(DEFFLD[$.format("address_{0}", M.LANG)]);
					if (j.htmlVal) {
						var b = j.htmlVal.split("-");
						f.find("select.province").empty().append(
								$.format("<option>{0}</option>", b[0]
										|| "省/自治区/直辖市"));
						f.find("select.city").empty().append(
								$.format("<option>{0}</option>", b[1] || "市"));
						f.find("select.zip").empty()
								.append(
										$.format("<option>{0}</option>", b[2]
												|| "区/县"))
					}
				} else {
					if (j.type === "radio") {
						createRadioItemsPreview(j, f)
					} else {
						if (j.type === "checkbox") {
							f.empty();
							var a;
							$.each(j.ITMS, function(m, c) {
								a = $(DEFFLD.item_checkbox_f);
								a.find("label").text(c.VAL);
								a.find(":checkbox").prop("checked",
										c.CHKED === "1");
								f.append(a)
							})
						} else {
									if (j.type === "section") {
										f.text(j.SECDESC || "")
									} else {
										if (j.type === "html") {
											f
													.html(j.HTML ? j.HTML
															.replace(
																	/<script[\S\s]*<\/script>/gim,
																	"")
															: "")
										} else {
											if (j.type === "likert") {
												createLikertPreview(j, e)
											}
										}
									}
						}
					}
				}
			}
		}
	}
	if (j.TYP === "dropdown") {
		$.each(j.ITMS, function(m, c) {
			if (c.CHKED === "1" && c.VAL) {
				f.find("select").append($.tmpl("<option>${$data}</option>", c.VAL));
				return false
			}
		})
	}
	if (j.htmlVal) {
		var l = [ "text", "textarea", "number", "ulr", "email", "money","phone"];
		if ($.inArray(j.TYP, l) >= 0) {
			if (j.TYP === "phone" && j.FMT === "tel") {
				$.each(j.htmlVal.split("-"), function(m, c) {
					f.find(":text:eq(" + m + ")").val(c)
				})
			} else {
				e.find(":input").val(j.htmlVal)
			}
		}
	}
	if (j.fldSize) {
		f.find(":text,textarea,select").removeClass("s m xxl")
				.addClass(j.fldSize)
	}
	var d = $(DEFFLD.instruct);
	if (j.formDesc) {
		d.text(j.formDesc)
	}
	k.append(DEFFLD.icon).append(e).append(d).append(DEFFLD.fieldActions);
	if (isInstruct(j.TYP)) {
		k.addClass("fieldInstruct")
	}
	if (j.LAY) {
		k.addClass(j.LAY)
	}
	if (j.SCU == "pri") {
		k.addClass("private")
	}
}
function createFields() {
	var b, a = $("#fields").empty();
	if ("L" === M.alignCss) {
		a.addClass("leftLabel")
	} else {
		if ("R" === M.alignCss) {
			a.addClass("leftLabel labelRight")
		}
	}
	$.each(F, function(d, c) {
		b = $(DEFFLD.field_li);
		b.attr("id", "f" + d);
		createField(b, c);
		a.append(b);
		if (needHandle(c.TYP)) {
			var e = $(DEFFLD.handle);
			b.append(e);
			e.position( {
				of : b,
				my : "left top",
				at : "left top"
			});
			e.css("width", b.outerWidth()).css("height", b.outerHeight())
		}
	});
	if ($.isEmptyObject(F)) {
		$("#nofields").show();
		$("#formButtons").hide()
	}
	$("#fields").find("li.first").removeClass("first").end().find("li:first")
			.addClass("first")
}
function createForm() {
	$("#fTitle").text(M.formName);
	$("#fDescription").html(M.formDesc.replace(/\n/gi, "<br/>"))
}
function formInit() {
	var f = function() {
		var j = $(this).val();
		$("#fTitle").text(j);
		M.formName = j;
		changeTableFlag("update");
		CHANGED = true
	};
	$("#formName").bind( {
		keyup : f,
		change : f
	});
	var h = function() {
		var j = $(this).val();
		$("#fDescription").html(M.formDesc.replace(/\n/gi, "<br/>"));
		M.formDesc = j;
		changeTableFlag("update")
		CHANGED = true
	};
	$("#desc").bind( {
		keyup : h,
		change : h
	});
	$("#language").change(
			function() {
				M.LANG = $(this).val();
				$.each(F, function(k, j) {
					if ((j.TYP === "name" && j.FMT === "extend")
							|| j.TYP === "address" || j.TYP === "phone") {
						createField($("#f" + k), j)
					}
				});
				CHANGED = true
			});
	$("#dataField").change(function(){
		var _v=$(this).val();
		$("#lbl").val($("#dataField").find('option:selected').text());
		F[IDX].dataField=_v;
		CHANGED = true;
	});
	$("#_fieldLength").keyup(function(){
		var _v=$(this).val();
		F[IDX]._fieldLength=_v;
		chargeUpdateFlag("modify");
		CHANGED = true;
	});
	$("#_aliasName").keyup(function(){
		var _v=$(this).val();
		F[IDX]._aliasName=_v;
		chargeUpdateFlag("modify");
		CHANGED = true;
	});
	
	$("#name").keyup(function(){
		var _v=$(this).val();
		F[IDX].name=_v;
		chargeUpdateFlag("update");
		CHANGED = true;
	});
	var fldsizeClass=document.getElementById("fldsize");
	fldsizeClass.onchange=function(){
		$("#f" + IDX).find("select,:text,textarea").removeClass(
		"s m l xl xxl").addClass($(this).val());
		F[IDX].fldSize = $(this).val();
		chargeUpdateFlag("update");
		CHANGED = true;
	};
	
	var dataTypeClass=document.getElementById("_dataType");
	dataTypeClass.onchange=function(){
		var _v=$(this).val();
		F[IDX]._dataType=_v;
		chargeUpdateFlag("modify");
		CHANGED = true;
	};
	$("#labelAlign").change(function() {
		var k = $(this).val(), j = $("#fields");
		j.removeClass("leftLabel labelRight");
		if (k === "L") {
			j.addClass("leftLabel")
		} else {
			if (k === "R") {
				j.addClass("leftLabel labelRight")
			}
		}
		M.alignCss = k;
		changeTableFlag("update");
		CHANGED = true
	});
	var e = function() {
		M = $.extend(M, $("#liSale").getValues())
	};
	$("#sale").change(function() {
		$("#salem,#salej").prop("disabled", !$(this).prop("checked"));
		if (!$(this).prop("checked")) {
			$("#salem,#salej").val("")
		}
		e()
	});
	$("#salem,#salej").bind( {
		keyup : e,
		change : e
	});
	var a = function() {
		var j = $("#liPay").getValues(true);
		$.extend(true, M, j)
	};
	$("#noAliConditionValue").bind( {
		change : a
	});
	$("#btnPaySetting").click(function() {
		var j = function() {
			$.lightBox( {
				url : "/rs/html/paysetting.html?v=1",
				confirm : function() {
					$("#lightBox").css( {
						width : "",
						marginLeft : ""
					});
					savePaySetting()
				},
				loaded : function() {
					$("#lightBox").css( {
						width : "600",
						marginLeft : "-300px"
					});
					loadPaySetting()
				},
				cancel : function() {
					$("#lightBox").css( {
						width : "",
						marginLeft : ""
					})
				}
			})
		};
		if (!M._id) {
			saveForm(false, j)
		} else {
			j()
		}
	});
	$("#chkAliPay").click(function() {
		var j = $(this).prop("checked");
		if (j) {
			$("#divPay").show();
			a()
		} else {
			$("#divPay").hide();
			delete M.ALIPAY;
			delete M.PAYCONFLD;
			delete M.ALICONVAL
		}
	});
	$("#noAliConditionField")
			.change(
					function() {
						var j = $(this).val();
						$("#noAliConditionValue").empty().append(
								"<option value=''></option>");
						if (j) {
							$
									.each(
											F,
											function(l, k) {
												if (k.NM === j && k.ITMS) {
													$
															.each(
																	k.ITMS,
																	function(m,
																			n) {
																		$(
																				"#noAliConditionValue")
																				.append(
																						$
																								.tmpl(
																										"<option value='${VAL}'>${VAL}</option>",
																										n))
																	});
													return false
												}
											})
						}
						a()
					});
	$("#confirmType_text").click(function() {
		$("#confirmMsg_text").show().focus();
		$("#confirmMsg_url").hide();
		M.CFMTYP = "T";
		CHANGED = true
	});
	$("#confirmType_url").click(function() {
		$("#confirmMsg_url").show().focus();
		$("#confirmMsg_text").hide();
		M.CFMTYP = "U";
		CHANGED = true
	});
	var c = function() {
		if (M.CFMTYP === "U") {
			M.CFMURL = $(this).val()
		} else {
			M.CFMMSG = $(this).val()
		}
		CHANGED = true
	};
	$("#confirmMsg_url").bind( {
		keyup : c,
		change : c
	});
	$("#confirmMsg_text").bind( {
		keyup : c,
		change : c
	});
	$("#captcha").change(function() {
		M.CAPTCHA = $(this).val();
		CHANGED = true
	});
	var g = function() {
		if ($(this).val()) {
			M.ENLMT = parseInt($(this).val())
		} else {
			delete M.ENLMT
		}
		CHANGED = true
	};
	$("#entriesLimit").bind( {
		keyup : g,
		change : g
	});
	$("#onePerIp").click(function() {
		M.IPLMT = $(this).prop("checked") ? "1" : "0";
		CHANGED = true
	});
	$("#chkAutoFill").click(function() {
		M.AUTOFILL = $(this).prop("checked") ? "1" : "0";
		CHANGED = true
	});
	$("#schActive").click(function() {
		var k = $(this).prop("checked"), j = $("#listDateRange");
		if (k) {
			j.show()
		} else {
			j.hide()
		}
		M.SCHACT = k ? "1" : "0";
		CHANGED = true
	});
	Date.abbrDayNames = [ "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" ];
	Date.dayNames = [ "日", "一", "二", "三", "四", "五", "六" ];
	Date.monthNames = [ "一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月",
			"十月", "十一月", "十二月" ];
	Date.abbrMonthNames = [ "一", "二", "三", "四", "五", "六", "七", "八", "九", "十",
			"十一", "十二" ];
	$.dpText = {
		TEXT_PREV_YEAR : "上一年",
		TEXT_PREV_MONTH : "上一月",
		TEXT_NEXT_YEAR : "下一年",
		TEXT_NEXT_MONTH : "下一月",
		TEXT_CLOSE : "关闭",
		TEXT_CHOOSE_DATE : "选择日期",
		HEADER_FORMAT : "mmmm yyyy"
	};
	var d = function(j, k) {
		var l = $(k).parent().parent();
		if (j) {
			l.find(":text.yyyy").val(j.getFullYear());
			l.find(":text.mm").val($.formatHH(j.getMonth() + 1));
			l.find(":text.dd").val($.formatHH(j.getDate()))
		}
		b(l)
	}, b = function(p) {
		var n = new Date(), o = p.find(":text.yyyy").val(), m = p.find(
				":text.mm").val(), j = p.find(":text.dd").val(), l = p.find(
				"select.ho").val(), k = p.find("select.mi").val();
		if (o && m && j) {
			n.setFullYear(parseInt(o), parseInt(m, 10) - 1, parseInt(j, 10));
			n.setHours(parseInt(l, 10), parseInt(k, 10), 0, 0);
			if (p.hasClass("start")) {
				M.STIME = n.getTime()
			} else {
				if (p.hasClass("end")) {
					M.ETIME = n.getTime()
				}
			}
		} else {
			if (p.hasClass("start")) {
				delete M.STIME
			} else {
				if (p.hasClass("end")) {
					delete M.ETIME
				}
			}
		}
		CHANGED = true
	};
	$("select.ho,select.mi,input.yyyy,input.mm,input.dd", "#listDateRange")
			.bind( {
				change : function() {
					b($(this).parent().parent())
				},
				keyup : function() {
					b($(this).parent().parent())
				}
			});
	$.initDate($("#listDateRange"), d);
	$("#chkHideEmpty").click(function() {
		if ($(this).prop("checked")) {
			M.HDEMP = "1"
		} else {
			delete M.HDEMP
		}
		CHANGED = true
	});
	$("#chkPublicData").click(function() {
		if ($(this).prop("checked")) {
			M.PUBDT = "1";
			$("#btnPubDataSetting").removeClass("hide")
		} else {
			delete M.PUBDT;
			$("#btnPubDataSetting").addClass("hide")
		}
		CHANGED = true
	});
	$("#btnPubDataSetting").click(function() {
		var j = function() {
			$.showStatus("正在加载...");
			$.lightBox( {
				url : "/rs/html/publicdatasetting.html",
				loaded : function() {
					loadedSetting()
				},
				confirm : function() {
					var k = $("#divSetting").getValues();
					if (k.PUBTYP == "pwd" && !k.PWD) {
						$.alert("请先设置查询密码");
						return false
					}
					if (!k.FLT1 && !k.FLT2) {
						$.alert("必须至少设置一个字段作为查询的过滤条件。");
						return false
					}
					if (k.FLT1 == k.FLT2) {
						delete k.FLT2
					}
					$.showStatus("正在保存设置...");
					$.postJSON("/app/pubdata/savesetting", k, function() {
						$.hideStatus()
					})
				}
			})
		};
		if (CHANGED) {
			$.showStatus("正在加载...");
			saveForm(false, j)
		} else {
			j()
		}
		return false
	})
}
head.ready(function() {
	$("#saveForm").html("<b></b>保存");
	$("a.help", "#left").helpTip();
	initTab();
	createForm();
	createFields();
	fieldInit();
	propertyInit();
	addFieldsInit();
	formInit();
	$("#helpLink").attr("href", resRoot + "/help/formbuilder.html");
	var b = function(d) {
		var c = $(this).val();
		$(this).val(c.replace(/\D/g, ""))
	}, a = function(d) {
		var c = $(this).val();
		$(this).val(c.replace(/[^(\d\.?\-?)]/g, ""))
	};
	$("input.yyyy,input.mm,input.dd,input.intnumber", "#left").bind({
		keyup : b,
		change : b
	});
	$("input.number,input.money,input.price", "#left").live({
		keyup : a,
		change : a
	});
	$.initPlat()
});


var fields=document.getElementById("fields");
fields.onclick=function(event){
	var doc=document;
	var Event=event||window.event;
	var target=Event.target;
	var fieldss = fields.childNodes;
	
	var fldsize=document.getElementById("fldsize");
	fldsize.innerHTML="";
	var option1=new Option("短","s");
	var option2=new Option("中","m");
	var option3=new Option("长","xxl");
	for(var i=0;i<fieldss.length;i++){
		var field=fieldss[i];
		if(field==target.parentNode){
			var f=F[i];
			var fld_size=f.fldSize;
			if(fld_size=="s"){
				fldsize.appendChild(option1);
				fldsize.appendChild(option2);
				fldsize.appendChild(option3);
			}
			if(fld_size=="m"){
				fldsize.appendChild(option2);
				fldsize.appendChild(option1);
				fldsize.appendChild(option3);
			}
			if(fld_size=="xxl"){
				fldsize.appendChild(option3);
				fldsize.appendChild(option1);
				fldsize.appendChild(option2);
			}
		}
	}
}