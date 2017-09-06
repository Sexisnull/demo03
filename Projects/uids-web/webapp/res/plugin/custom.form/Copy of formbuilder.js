//fieldsLimit表单允许存放元素
var CHANGED = false, PREGOODS = [], DSFRMS, IDX = -2,
delConfirmMsg = "建议您先将数据导出后再删除字段！\n删除将导致已提交的数据和报表中此字段对应的值被清空，且不可恢复。确认删除吗？", 
DEFFLD = {
	handle : '<div class="handle"></div>',
	icon : '<img class="arrow arrowIE6 hide" src="' + resRoot + '/res/jslib/custom/images/arrowg.png" />',
	instruct : '<p class="instruct hide"></p>',
	fieldActions : '<div class="fieldActions hide"><a title="复制" href="#" class="icon-btn faDup"></a><a title="删除" href="#" class="icon-btn faDel"></a></div>',
	icon_del : '<a title="删除此行" class="icononly-del"></a>',
	field_li : '<li class="field default"></li>',
	item_checkbox : '<li><input name="CHKED" value="1" class="checkbox" type="checkbox" title="默认选中此项" /><input name="VAL" type="text" class="l"/><a class="icononly-add" title="添加一个新的选择项"></a><a class="icononly-del" title="删除此选择项"></a></li>',
	item_goods : '<li class="clearfix"><div class="goods-item"><div class="goods-item-inner">    <div class="goods-image">      <img src="" class="img">    </div>    <a class="goods-name-view" href="#" title="点击编辑，拖动排序">商品名称</a>    <div class="attrs">      <div class="goods-name-edit">        <a href="#" class="edit-img" title="修改商品图片"><span>更换图片</span><input title="添加配图商品" name="editImg" class="file-prew edit-img-input" type="file" size="3" accept="image/gif,image/jpeg,image/png,image/bmp"></a>        <label for="goodsItemVal">名称：</label><input id="goodsItemVal" value="商品名称" type="text" name="VAL" class="val" maxlength="16" />      </div>      <div class="goods-price">        <label class="goods-price-label">单价：</label><input value="0" type="text" name="PRC" class="price" maxlength="6" />      </div>      <div class="goods-unit">        <label>单位：</label><input type="text" name="UNT" class="unt" maxlength="4" />      </div>      <div class="goods-def">        <label>默认：</label><input type="text" name="DEF" class="def number" maxlength="4" />      </div>      <div class="goods-hide">        <input type="checkbox" name="HD" class="hd" value="1" /> <label>隐藏</label>      </div>      <div class="goods-currency">        <select name="CNY" class="cny" title="币别" ><option value="">币别</option><option value="¥">人民币</option><option value="$">美元</option><option value="£">英镑</option><option value="€">欧元</option></select>      </div>      <div class="goods-description">        <label for="goodsItemDes" style="vertical-align: top;">描述：</label><textarea id="goodsItemDes" name="DES" class="des"></textarea>      </div>    </div></div></div>    <a class="icononly-del" title="删除此选商品"></a> </li>',
	item_radio : '<li><input name="CHKED" value="1" class="radio" type="radio" title="默认选中此择项" /><input name="VAL" type="text" class="l"/><a class="icononly-add" title="添加一个新的选择项"></a><a class="icononly-del" title="删除此选择项"></a></li>',
	item_dropdown2 : '<li><input name="VAL" type="text" class="xl"/><a class="icononly-add" title="添加一个新的选择项"></a><a class="icononly-del" title="删除此选择项"></a></li>',
	item_other : '<li class="dropReq hide"><input name="CHKED" disabled="disabled" type="radio" title="默认选中此择项" /><label>其他</label><input name="OTHER" disabled="disabled" type="text" class="m"/><a class="icononly-del" title="删除此选择项"></a><a style="position:absolute;margin-left:45px;float:right" href="#" class="help" title="">(?)</a></li>',
	item_likert_row : '<li><input name="LBL" class="xl" value="1" type="text"/><a class="icononly-add" title="添加新行"></a><a class="icononly-del" title="删除此行"></a></li>',
	item_likert_col : '<li><input name="CHKED" value="1" class="radio" type="radio" title="默认选中此列" /><input name="VAL" type="text" class="l"/><a class="icononly-add" title="添加新列"></a><a class="icononly-del" title="删除此列"></a></li>',
	item_radio_f : '<span><input type="radio" disabled="disabled" /><label></label></span>',
	item_checkbox_f : '<span><input type="checkbox" disabled="disabled" /><label></label></span>',
	item_goods_f : '<div class="goods-item"><div class="image-center"><img src="" class="img"></div>	  <div class="text-wrapper">	    <label class="name">商品名称</label><label class="des"></label>		<div class="price-number clearfix">	    	<div class="price">0</div>	    	<div class="number-container"><span class="number-pre hide">¥</span>	      		<a href="#" class="decrease"><i class="icon-minus">-</i></a><input class="number" type="text" maxlength="6" value="0"><a href="#" class="increase"><i class="icon-plus">+</i></a>	    	</div>		</div>	 </div></div>',
	likert_td : '<td><input type="radio" disabled="disabled" /><label></label></td>',
	item_radio_other_f : '<span class="hide"><input type="radio" value="other" disabled="disabled" /><label>其他</label><input name="OTHER" disabled="disabled" type="text" class="m" /></span>',
	text : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" maxlength="255" class="input" /></div',
		json : '({LBL:"单行文本",TYP:"text",FLDSZ:"m",REQD:"0",UNIQ:"0",SCU:"pub"})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	number : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" maxlength="32" class="input" /></div>',
		json : '({LBL:"数字框",TYP:"number",FLDSZ:"s",REQD:"0",UNIQ:"0",SCU:"pub"})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	textarea : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><textarea disabled="disabled" class="input"></textarea></div>',
		json : '({LBL:"多行文本",TYP:"textarea",FLDSZ:"m",REQD:"0",UNIQ:"0",SCU:"pub",MIN:"",MAX:"",DEF:"",INSTR:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:179px;width:97%"></li>'
	},
	checkbox : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"></div>',
		json : '({LBL:"复选框",TYP:"checkbox",LAY:"one",REQD:"0",SCU:"pub",INSTR:"",CSS:"",ITMS:[{VAL:"选项 1",CHKED:"0"},{VAL:"选项 2",CHKED:"0"},{VAL:"选项 3",CHKED:"0"}]})',
		holder : '<li class="field prefocus" style="height:92px;width:97%"></li>'
	},
	goods : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><div class="nogoods-holder">请在左侧添加商品</div></div>',
		json : '({LBL:"商品",TYP:"goods",REQD:"0",SCU:"pub",INSTR:"",ITMS:[],FBUY:"0",UNT:"",NOIMG:""})',
		holder : '<li class="field prefocus" style="height:270px;width:97%"></li>'
	},
	goodsnoimg : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><div class="nogoods-holder">请在左侧添加商品</div></div>',
		json : '({LBL:"商品",TYP:"goods",REQD:"0",SCU:"pub",INSTR:"",ITMS:[],FBUY:"0",UNT:"",NOIMG:"1"})',
		holder : '<li class="field prefocus" style="height:130px;width:97%"></li>'
	},
	radio : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"></div>',
		json : '({LBL:"单选框",TYP:"radio",LAY:"one",REQD:"0",OTHER:"0",RDM:"0",SCU:"pub",INSTR:"",CSS:"",ITMS:[{VAL:"选项 1",CHKED:"0"},{VAL:"选项 2",CHKED:"0"},{VAL:"选项 3",CHKED:"0"}]})',
		holder : '<li class="field prefocus" style="height:92px;width:97%"></li>'
	},
	dropdown : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><select disabled="disabled" class="m input"></select></div>',
		json : '({LBL:"下拉框",TYP:"dropdown",FLDSZ:"m",REQD:"0",SCU:"pub",INSTR:"",CSS:"",ITMS:[{VAL:"选项 1",CHKED:"0"},{VAL:"选项 2",CHKED:"0"},{VAL:"选项 3",CHKED:"0"}]})',
		holder : '<li class="field prefocus" style="height:46px;width:97%"></li>'
	},
	dropdown2 : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><select disabled="disabled" class="m input"></select> <select disabled="disabled" class="m input"></select></div>',
		json : '({LBL:"两级下拉框",TYP:"dropdown2",FLDSZ:"m",REQD:"0",SCU:"pub",INSTR:"",CSS:"",SUBFLDS:{DD1:{},DD2:{}},ITMS:[{VAL:"选项 1",CHKED:"0",ITMS:[{VAL:"选项 11"},{VAL:"选项 12"},{VAL:"选项 13"}]},{VAL:"选项 2",CHKED:"0",ITMS:[{VAL:"选项 21"},{VAL:"选项 22"},{VAL:"选项 23"}]},{VAL:"选项 3",CHKED:"0",ITMS:[{VAL:"选项 31"},{VAL:"选项 32"},{VAL:"选项 33"}]}]})',
		holder : '<li class="field prefocus" style="height:46px;width:97%"></li>'
	},
	image : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><img style="width:100%;" src="/rs/images/defaultimg.png"></div>',
		json : '({LBL:"图片",TYP:"image",IMG:"",REQD:"0",SCU:"pub",INSTR:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:271px;width:97%"></li>'
	},
	section : {
		html : '<div class="noLabelAlign"><label class="desc section">分隔符</label><div class="content">这里是分隔符说明</div></div>',
		json : '({LBL:"分隔符",TYP:"section",SCU:"pub",SECDESC:"这里是分隔符说明",CSS:""})',
		holder : '<li class="field prefocus" style="height:45px;width:97%"></li>'
	},
	html : {
		html : '<div class="noLabelAlign"><label class="desc">HTML标题</label><div class="content"><p>这里可以显示HTML内容</p></div></div>',
		json : '({LBL:"HTML标题",TYP:"html",SCU:"pub",HTML:"<p>这里可以显示HTML内容</p>",CSS:""})',
		holder : '<li class="field prefocus" style="height:42px;width:97%"></li>'
	},
	date : {
		html : '<label class="desc">日期 <span class="req hide"> *</span></label><div class="content oneline reduction"><span>	<input class="yyyy input" disabled="disabled" maxlength="4" type="text"/>	<label>YYYY</label></span><span> - </span><span>	<input class="mm input" disabled="disabled" maxlength="2" type="text"/>	<label>MM</label></span><span> - </span><span>	<input class="dd input" disabled="disabled" maxlength="2" type="text"/>	<label>DD</label></span><span><a class="icononly-date" title="选择日期"></a></span></div>',
		json : '({LBL:"日期",TYP:"date",REQD:"0",UNIQ:"0",SCU:"pub",FMT:"ymd",DEF:"",INSTR:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:57px;width:97%"></li>'
	},
	date_ymd : '<span><input class="yyyy input" disabled="disabled" maxlength="4" type="text"/><label>YYYY</label></span><span> - </span><span><input class="mm input" disabled="disabled" maxlength="2" type="text"/><label>MM</label></span><span> - </span><span><input class="dd input" disabled="disabled" maxlength="2" type="text"/><label>DD</label></span><span><a class="icononly-date" title="选择日期"></a></span>',
	date_mdy : '<span><input class="mm input" disabled="disabled" maxlength="2" type="text"/><label>MM</label></span><span> / </span><span><input class="dd input" disabled="disabled" maxlength="2" type="text"/><label>DD</label></span><span> / </span><span><input class="yyyy input" disabled="disabled" maxlength="4" type="text"/><label>YYYY</label></span><span><a class="icononly-date" title="选择日期"></a></span>',
	date_dmy : '<span><input class="dd input" disabled="disabled" maxlength="2" type="text"/>	<label>DD</label></span><span> / </span><span>	<input class="mm input" disabled="disabled" maxlength="2" type="text"/>	<label>MM</label></span><span> / </span><span>	<input class="yyyy input" disabled="disabled" maxlength="4" type="text"/>	<label>YYYY</label></span><span><a class="icononly-date" title="选择日期"></a></span>',
	time : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content oneline reduction"><span>	<select class="hh input" disabled="disabled"></select></span><span> : </span><span>	<select class="mm input" disabled="disabled"></select></span></div>',
		json : '({LBL:"时间",TYP:"time",REQD:"0",UNIQ:"0",SCU:"pub",DEF:"",INSTR:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:57px;width:97%"></li>'
	},
	file : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" class="m input" />&nbsp;<input type="button" disabled="disabled" value="浏览..." /></div>',
		json : '({LBL:"上传文件",TYP:"file",FLDSZ:"m",REQD:"0",UNIQ:"0",SCU:"pub",INSTR:"",CSS:"",SUBFLDS:{ID:{},TYP:{},SZ:{},NM:{}}})',
		holder : '<li class="field prefocus" style="height:54px;width:97%"></li>'
	},
	phone : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content reduction"><input type="text" disabled="disabled" maxlength="32" class="s input" /> <button type="button" class="sendcode hide">发送验证码</button></div>',
		json : '({LBL:"电话",TYP:"phone",REQD:"0",UNIQ:"0",SCU:"pub",FMT:"mobile",DEF:"",INSTR:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	phone_tel_cn : '<span><input class="input" disabled="disabled" maxlength="4" size="4" type="text"/><label>区号</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="8" size="8" type="text"/><label>总机</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="4" size="4" type="text"/><label>分机</label></span>',
	phone_tel_en : '<span><input class="input" disabled="disabled" maxlength="4" size="4" type="text"/><label>####</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="8" size="8" type="text"/><label>########</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="4" size="4" type="text"/><label>####</label></span>',
	phone_mobile_cn : '<input type="text" disabled="disabled" maxlength="32" class="s input" /> <button type="button" class="sendcode hide">发送验证码</button>',
	phone_mobile_en : '<input type="text" disabled="disabled" maxlength="32" class="s input" /> <button type="button" class="sendcode hide">发送验证码</button>',
	url : {
		html : '<label class="desc"> <span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" maxlength="256" class="m input" value="http://" /></div>',
		json : '({LBL:"网址",TYP:"url",FLDSZ:"xxl",REQD:"0",UNIQ:"0",SCU:"pub",DEF:"",INSTR:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	money : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><b>￥</b><input type="text" disabled="disabled" maxlength="16" size="8" class="input" /></div>',
		json : '({LBL:"价格",TYP:"money",REQD:"0",UNIQ:"0",SCU:"pub",FMT:"yen",DEF:"",INSTR:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	email : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><input type="text" disabled="disabled" maxlength="64" class="m input" /></div>',
		json : '({LBL:"Email",TYP:"email",FLDSZ:"m",REQD:"0",UNIQ:"0",SCU:"pub",DEF:"",INSTR:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:41px;width:97%"></li>'
	},
	name : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content oneline reduction"><input type="text" disabled="disabled" maxlength="128" value="" class="s input" /></div>',
		json : '({LBL:"姓名",TYP:"name",REQD:"0",UNIQ:"0",SCU:"pub",FMT:"short",DEF:"",INSTR:"",CSS:""})',
		holder : '<li class="field prefocus" style="height:43px;width:97%"></li>'
	},
	name_short : '<input type="text" disabled="disabled" maxlength="128" value="" class="s input" /></div>',
	name_extend_en : '<span><input class="input"  disabled="disabled" maxlength="128" size="6" type="text"/><label>Title</label></span><span> - </span><span><input class="input" disabled="disabled" maxlength="128" size="10" type="text"/><label>First Name</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="128" size="10" type="text"/><label>Last Name</label></span>',
	name_extend_cn : '<span><input class="input" disabled="disabled" maxlength="4" size="4" type="text"/><label>姓</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="4" size="8" type="text"/><label>名</label></span><span> - </span><span><input class="input"  disabled="disabled" maxlength="4" size="4" type="text"/><label>称呼</label></span>',
	map : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content"><input type="text" class="xxl location" readonly="readonly" /><div class="map"><img src="http://rs.jsform.com/rs/images/map.jpg"</div></div>',
		json : '({LBL:"地理位置",TYP:"map",REQD:"0",SCU:"pub",INSTR:"",CSS:"",SUBFLDS:{TXT:{},J:{},W:{}}})',
		holder : '<li class="field prefocus" style="height:294px;width:97%"></li>'
	},
	address : {
		html : '<label class="desc"><span class="req hide"> *</span></label><div class="content onelineLeft reduction"><span class="left third clear"><select disabled="disabled" class="xxl input province"><option value="">省/自治区/直辖市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input city" ><option value="">市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input zip" ><option value="">区/县</option></select></span><span class="left" style="margin:5px 5px 0px 0px;width:100%;"><textarea style="height:40px;" disabled="disabled" class="input xxl detail" placeholder="详细地址" ></textarea></span></div>',
		json : '({LBL:"地址",TYP:"address",REQD:"0",SCU:"pub",INSTR:"",CSS:"",SUBFLDS:{ZIP:{},PRV:{},CITY:{},DTL:{}}})',
		holder : '<li class="field prefocus" style="height:177px;width:97%"></li>'
	},
	address_en : '<span class="left third clear"><select disabled="disabled" class="xxl input province"><option value="">省/自治区/直辖市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input city" ><option value="">市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input zip" ><option value="">区/县</option></select></span><span class="left" style="margin:5px 5px 0px 0px;width:100%;"><textarea style="height:40px;" disabled="disabled" class="input xxl detail" placeholder="Street"></textarea></span>',
	address_cn : '<span class="left third clear"><select disabled="disabled" class="xxl input province"><option value="">省/自治区/直辖市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input city" ><option value="">市</option></select></span><span class="left third"><select disabled="disabled" class="xxl input zip" ><option value="">区/县</option></select></span><span class="left" style="margin:5px 5px 0px 0px;width:100%;"><textarea style="height:40px;" disabled="disabled" class="input xxl detail" placeholder="详细地址"></textarea></span>',
	likert : {
		html : '<div class="content noLabelAlign"><table class="table" cellspacing="0"><caption><label class="desc"><span class="req hide"> *</span></label></caption><thead><tr></tr></thead><tbody></tbody></table></div>',
		json : '({LBL:"组合单选框",TYP:"likert",REQD:"0",HDNM:"0",SCU:"pub",CSS:"",ITMS:[{LBL:"行标签 1",ITMS:[{VAL:"列标签 1",CHKED:"0"},{VAL:"列标签 2",CHKED:"0"},{VAL:"列标签 3",CHKED:"0"},{VAL:"列标签 4",CHKED:"0"}]},         {LBL:"行标签 2",ITMS:[{VAL:"列标签 1",CHKED:"0"},{VAL:"列标签 2",CHKED:"0"},{VAL:"列标签 3",CHKED:"0"},{VAL:"列标签 4",CHKED:"0"}]},         {LBL:"行标签 3",ITMS:[{VAL:"列标签 1",CHKED:"0"},{VAL:"列标签 2",CHKED:"0"},{VAL:"列标签 3",CHKED:"0"},{VAL:"列标签 4",CHKED:"0"}]}]})',
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
					$(d).data("selectedIndex", e);
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
					$.autoHeight()
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
		F[IDX].MATFRM = d;
//		$.each(e, function(g, h) {
//			//if (b[c] === b[h.TYP]) {
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
			F[IDX].MATFRM = d;
			if(e.length>0){
				$("#selMatchFld").empty().append("<option value=''>-请选择字段-</option>");
				$.each(e, function(g, h) {
					//if (b[c] === b[h.TYP]) {
					$("#selMatchFld").append($.tmpl("<option value='${COLUMN_NAME}'>${COMMENTS}</option>",h));
							//}
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
					$.tmpl("<option value='${NM}'>${LBL}</option>", g))
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
			if (c[d] === c[h.TYP]) {
				$("#selAutoCompFld").append(
						$.tmpl("<option value='${NM}'>${LBL}</option>", h))
			}
		});
		$("#selAutoCompFld").val(b)
	})
};
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
		goods : [ "ptype", "poptions", "popt_required", "psecurity", "pgoods" ],
		goodsnoimg : [ "ptype", "poptions", "popt_required", "psecurity",
				"pgoods" ],
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
		image : [ "ptype", "pimage", "psecurity" ],
		map : [ "poptions", "popt_required", "psecurity", "pinstruct",
				"popt_dismark" ],
		section : [ "psecurity", "psection" ],
		html : [ "psecurity", "phtml" ],
		likert : [ "ptype", "plikert", "poptions", "popt_required",
				"popt_hidenum", "psecurity" ],
		date : [ "ptype", "pdateformat", "poptions", "popt_required",
				"popt_unique", "psecurity", "pdefval_date", "pinstruct",
				"pconfine", "pconfine2" ],
		time : [ "ptype", "poptions", "popt_required", "popt_unique",
				"psecurity", "pdefval_time", "pinstruct", "pconfine",
				"pconfine2" ],
		phone : [ "ptype", "poptions", "popt_required", "popt_unique",
				"popt_authcode", "psecurity", "pdefval_phone_mobile",
				"pinstruct", "pconfine", "pconfine1", "pconfine2" ],
		file : [ "ptype", "poptions", "popt_required", "popt_compress",
				"psecurity", "pinstruct" ],
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
	$("#tabs").tab("select", 0)
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
		d.removeClass("form-default form-prefocus").addClass("form-focused");
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
		showProperties(F[IDX].TYP);
		$("#allProps").animate( {
			marginTop : d.position().top + $("#fbForm").height()
		}, 500, "easeOutCubic", function() {
			setPropertieValues(F[IDX])
		})
	} else {
		$("#liGoods").hide();
		$("#salem,#salej").prop("disabled", !M.SALE);
		$(F).each(function(e, g) {
			if ("goods" === g.TYP) {
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
																	v : g.LBL
																}))
							} else {
								if (g.TYP === "phone" && g.FMT == "mobile") {
									$("#sendSmsTo").append($.tmpl('<option value="${k}">${v}</option>',
																	{
																		k : g.NM,
																		v : g.LBL
																	}))
								} else {
									if ("radio" == g.TYP || "dropdown" == g.TYP) {
										$("#noAliConditionField")
												.append($.tmpl('<option value="${k}">${v}</option>',
																		{
																			k : g.NM,
																			v : g.LBL
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
//开始设置字段设置
function setPropertieValues(c){
	$("#allProps").setValues(c);
	if (c.TYP === "phone" && c.FMT) {
		$("#phoneformat").val(c.FMT);
		showPhoneFormat(c.FMT);
		if (c.FMT === "tel") {
			$("#defval_phone_mobile").val("");
			$("#defval_phone_tel").val(c.DEF);
			setSplitTelValue()
		} else {
			$("#defval_phone_tel").val("");
			$("#defval_phone_mobile").val(c.DEF)
		}
	} else {
		if (c.TYP == "address" && c.DEF) {
			var b = c.DEF.split("-"), a = b[0], f = b[1], e = b[2];
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
			if (c.TYP == "file") {
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
	if (c.TYP === "likert") {
		createItems($("#likertRows"), "likertRow", c.ITMS);
		createItems($("#likertCols"), "likertCol", c.ITMS[0].ITMS)
	} else {
		if (c.TYP == "goods") {
			createItems($("#goodsList"), c.TYP, c.ITMS);
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
				createItems($("#itemList"), c.TYP, c.ITMS)
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
		fillMatchFields(c.MATFRM, c.TYP, c.MATFLD)
	} else {
		$("#divMatchContainer").addClass("hide")
	}
	if (c.ACMP == "1") {
		$("#divAutoCompContainer").removeClass("hide");
		fillAutoCompSrcFlds(c.ACMPSRCFLD);
		fillAutoCompFlds(c.ACMPSRCFLD, c.TYP, c.ACMPFLD)
	} else {
		$("#divAutoCompContainer").addClass("hide")
	}
}
//结束
//组合html元素
function isInstruct(a) {
	if (M.INSTR !== "1") {
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
	var newJSON = eval(DEFFLD[type].json);
	$.mergJSON(srcJSON, newJSON);
	F[index] = newJSON;
	createField(li, newJSON);
	if (li.index() == 0) {
		li.addClass("first")
	} else {
		li.removeClass("first")
	}
	$.autoHeight()
}
function addDefFieldDom(c, type, index, srcJSON) {
	var newJSON = eval(DEFFLD[type].json);
	$.mergJSON(srcJSON, newJSON);
	F.splice(index, 0, newJSON);
	createField(c, newJSON);
	$.autoHeight()
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
						sortstart : function(c, b) {
							if (!b.item.hasClass("field")) {
								return
							}
							b.item.css("left", "");
							b.placeholder.css("height", b.item.height())
						},
						sortover : function(g, f) {
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
						sortupdate : function(h, d) {
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
								if (c == "image") {
									var k = 0;
									$(F).each(function(e, l) {
										if ("image" === l.TYP) {
											k++
										}
									});
									if (k >= imageNumber) {
										$.alert("最多只能添加" + imageNumber + "张图片。");
										setTimeout(function() {
											d.item.remove()
										}, 50);
										return false
									}
								}
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
								});
								IDX = f;
								setFocused(d.item, j + 1)
							}
							$("#fields").find("li.first").removeClass("first")
									.end().find("li.field:first").addClass(
											"first");
							CHANGED = true
						}
					});
	$("#fbForm").hover(function() {
		preFocused(this, 0)
	}, function() {
		reDefault(this, 0)
	});
	$("#fbForm").click(function() {
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


function createGoodsItemsPreView(a, d) {
	if (a.ITMS.length > 0) {
		d.empty()
	}
	var b;
	$.each(a.ITMS, function(e, c) {
		b = $(DEFFLD.item_goods_f);
		if ("1" == a.NOIMG) {
			b.find("div.image-center").hide()
		} else {
			b.find("img.img").attr("src",
					GOODSIMAGEURL + c.IMG + GOODSIMAGESTYLE)
		}
		b.find("label.name").text(c.VAL);
		b.find("label.des").html(c.DES || "");
		$.formatPrice(b.find("div.price-number"), c.PRC, c.CNY, a.FBUY, c.UNT,
				c.DEF);
		if ("1" == c.HD) {
			b.hide()
		}
		d.append(b)
	})
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
		g = $($.tmpl("<tr><th>${$data}</th></tr>", l.LBL));
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
		if (F[IDX].TYP === "radio") {
			createRadioItemsPreview(F[IDX], $("#f" + IDX).find("div.content"))
		} else {
			if (F[IDX].TYP === "dropdown") {
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
										y(F[IDX].TYP);
										$("#itemList").find(
												"input[name='VAL']:first")
												.trigger("focus");
										CHANGED = true
									},
									loaded : function() {
										var R = "";
										if (F[IDX].TYP == "dropdown2") {
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
		F[IDX].LBL = $.trim($(this).val());
		CHANGED = true
	};
	$("#lbl").bind({
		keyup : s,
		change : s
	});
	$("#type").change(function(){
		var T = $("#f" + IDX), R = F[IDX];
		R.TYP = $(this).val();
		if (R.ITMS) {
			R.ITMS = DEFFLD[R.TYP].json.ITMS
		}
		if (R.SUBFLDS) {
			R.SUBFLDS = DEFFLD[R.TYP].json.SUBFLDS
		}
		if (R.TYP == "goodsnoimg") {
			R.NOIMG = "1";
			R.TYP = "goods"
		} else {
			if (R.TYP == "goods") {
				R.NOIMG = ""
			}
		}
		setDefFieldDom(T, R.TYP, IDX, R);
		var S = IDX;
		IDX = -2;
		setFocused(T, S + 1);
		CHANGED = true
	});
	$("#fldsize").change(function(){
				$("#f" + IDX).find("select,:text,textarea").removeClass(
						"s m l xl xxl").addClass($(this).val());
				F[IDX].FLDSZ = $(this).val();
				CHANGED = true
			});
	$("#layout").change(function(){
				$("#f" + IDX).removeClass("one two three oneByOne").addClass($(this).val());
				F[IDX].LAY = $(this).val();
				CHANGED = true
			});
	$("#dateformat").change(function() {
				$("#f" + IDX).find("div.content").html(DEFFLD["date_" + $(this).val()]);
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
		F[IDX].ITMS[R].LBL = $.trim($(this).val());
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
					$.postJSON(R, {
						_id : M._id,
						FLDID : F[IDX].FLDID,
						NM : F[IDX].ITMS[S].NM
					}, function() {
						a(T);
						$.hideStatus()
					})
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
					F[IDX].DEF = S ? "" + R : "";
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
	$("#likertCols>li>a.icononly-del").live( {
		click : function() {
			var T = this, S = $("#likertCols>li>a.icononly-del").index(T) + 1;
			if (F[IDX].ITMS[0].ITMS[S].ITMID) {
				if (confirm(delConfirmMsg)) {
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
	$("#reqd").click(function(){
		var S = $(this).prop("checked"), R = $("#f" + IDX).find("span.req");
		if (S) {
			R.show()
		} else {
			R.hide()
		}
		F[IDX].REQD = S ? "1" : "0";
		CHANGED = true
	});
	$("#uniq").click(function() {
		F[IDX].UNIQ = $(this).prop("checked") ? "1" : "0";
		CHANGED = true
	});
	$("#random").click(function() {
		F[IDX].RDM = $(this).prop("checked") ? "1" : "0";
		CHANGED = true
	});
	$("#hidenum").click(
			function() {
				var R = $(this).prop("checked");
				if (R) {
					$("#f" + IDX).find("div.content>table>tbody label").css(
							"display", "none")
				} else {
					$("#f" + IDX).find("div.content>table>tbody label").css(
							"display", "block")
				}
				F[IDX].HDNM = R ? "1" : "0";
				CHANGED = true
			});
	$("#allowOther").click(
			function() {
				var T = $(this).prop("checked"), R = $("li.dropReq",
						"#itemList"), S = $("span:last", "#f" + IDX);
				if (T) {
					R.show();
					S.show()
				} else {
					R.find(":radio").prop("checked", false).end().hide();
					S.find(":radio").prop("checked", false).end().hide()
				}
				F[IDX].OTHER = T ? "1" : "0";
				CHANGED = true
			});
	$("#internal").click(function() {
		F[IDX].INTERNAL = $(this).prop("checked") ? "1" : "0";
		F[IDX].DEF = $(this).prop("checked") ? "+" : "";
		$("#defval_phone_mobile").val(F[IDX].DEF).trigger("change")
	});
	$("#authcode").click(function() {
		if ($(this).prop("checked")) {
			$("#f" + IDX).find("div.content .sendcode").show();
			$("#signcnt").show();
			F[IDX].AUTH = "1"
		} else {
			$("#f" + IDX).find("div.content .sendcode").hide();
			$("#signcnt").hide();
			F[IDX].AUTH = "0"
		}
		$("#internal").prop("checked", F[IDX].INTERNAL == "1")
	});
	$("#sign").change(function() {
		F[IDX].SIGN = $.trim($(this).val())
	});
	$("#btnSignSumbmit").click(function() {
		var R = $(this).attr("href");
		if (R.indexOf("?") > 0) {
			R = R.substring(0, R.indexOf("?"))
		}
		$(this).attr("href", R + "?field2=" + $("#sign").val())
	});
	$("#chkCompress").click(function() {
		if ($(this).prop("checked")) {
			$("#divCompress").removeClass("hide");
			$("#selCompress").val("30");
			F[IDX]["CPRS"] = "30"
		} else {
			$("#divCompress").addClass("hide");
			delete F[IDX]["CPRS"]
		}
	});
	$("#selCompress").change(function() {
		F[IDX].CPRS = $(this).val()
	});
	$("#chkDismark").click(function() {
		F[IDX].DISMK = $(this).prop("checked") ? "1" : "0";
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
		if (F[IDX].TYP === "goods"
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
						_id : M._id,
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
	$("a.icononly-del", "#goodsList").live( {
		click : B
	});
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
		S.attr("IDX", R).find("a.icononly-del:first").remove()
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
			.live(
					{
						click : function() {
							var S = $(":radio", "#itemList").index(this), T = $(
									this).prop("checked");
							$(":radio", "#f" + IDX).prop("checked", false);
							$("select", "#f" + IDX).empty();
							$.each(F[IDX].ITMS, function(V, U) {
								U.CHKED = "0"
							});
							if (F[IDX].TYP === "radio") {
								$("#f" + IDX)
										.find(
												"div.content>span:eq(" + S
														+ ") :radio").prop(
												"checked", T)
							} else {
								if ((F[IDX].TYP === "dropdown" || F[IDX].TYP === "dropdown2")
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
							F[IDX].DEF = T ? "" + S : "";
							CHANGED = true
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
				F[IDX].FBUY, F[IDX].ITMS[R].UNT, F[IDX].ITMS[R].DEF);
		CHANGED = true
	}, e = function() {
		var R = $("#goodsList").find("li").has(this).index();
		F[IDX].ITMS[R].CNY = $(this).val();
		$.formatPrice($("#f" + IDX).find("div.content>div:eq(" + R + ")").find(
				"div.price-number"), F[IDX].ITMS[R].PRC, F[IDX].ITMS[R].CNY,
				F[IDX].FBUY, F[IDX].ITMS[R].UNT, F[IDX].ITMS[R].DEF);
		CHANGED = true
	}, m = function() {
		var R = $("#goodsList").find("li").has(this).index();
		F[IDX].ITMS[R].UNT = $.trim($(this).val());
		$.formatPrice($("#f" + IDX).find("div.content>div:eq(" + R + ")").find(
				"div.price-number"), F[IDX].ITMS[R].PRC, F[IDX].ITMS[R].CNY,
				F[IDX].FBUY, F[IDX].ITMS[R].UNT, F[IDX].ITMS[R].DEF);
		CHANGED = true
	}, N = function() {
		var R = $("#goodsList").find("li").has(this).index();
		F[IDX].ITMS[R].DEF = $.trim($(this).val());
		$.formatPrice($("#f" + IDX).find("div.content>div:eq(" + R + ")").find(
				"div.price-number"), F[IDX].ITMS[R].PRC, F[IDX].ITMS[R].CNY,
				F[IDX].FBUY, F[IDX].ITMS[R].UNT, F[IDX].ITMS[R].DEF);
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
	$("#goodsList").find("input.val").live( {
		keyup : C,
		change : C
	});
	$("#goodsList").find("textarea.des").live( {
		keyup : H,
		change : H
	});
	$("#goodsList").find("input.price").live( {
		keyup : E,
		change : E
	});
	$("#goodsList").find("input.unt").live( {
		keyup : m,
		change : m
	});
	$("#goodsList").find("input.hd").live( {
		click : A
	});
	$("#goodsList").find("select.cny").live( {
		change : e
	});
	$("#goodsList").find("input.def").live( {
		keyup : N
	});
	$("#goodsForBuy").click(j);
	$("#goodsList").find("a.goods-name-view").live( {
		click : function() {
			$("#goodsList").find("div.expand").removeClass("expand");
			$(this).parent().parent().addClass("expand");
			return false
		}
	});
	var x = function() {
		var U = 0, S = $(this).hasClass("edit-img-input"), T = -1, R = "fileToUpload";
		if (!S) {
			$(F).each(function(V, W) {
				if ("goods" === W.TYP) {
					U += W.ITMS.length
				}
			});
			if (U >= goodsNumber) {
				$.alert("最多只能添加" + goodsNumber + "件商品。");
				return false
			}
		} else {
			T = $("#goodsList").find("li").has(this).index();
			R = $.format("F{0}ITMS{1}UPLOAD", IDX, T)
		}
		$.showStatus("正在上传图片...");
		$.ajaxFileUpload({
			url : "ajaxuploadgoods",
			secureuri : false,
			fileElementId : R,
			dataType : "json",
			data : {
				FRMID : M._id,
				OLDIMG : S ? F[IDX].ITMS[T].IMG : ""
			},
			success : function(Z, W) {
				if (Z.status != "success") {
					var Y = {
						emptyFile : "上传文件为空，请重新选择。",
						sizeMsg : "单张图片不能大于500KB。",
						formatMsg : "只能导入jpg,gif,png,bmp类型的图片文件。"
					};
					$.alert(Y[Z.msgCode])
				} else {
					var V = S ? $("#goodsList").find("li:eq(" + T + ")")
							: $(DEFFLD.item_goods);
					V.find("input[name='IMG']").val(Z.filePath);
					V.find("img.img").attr("src",
							GOODSIMAGEURL + Z.filePath + GOODSIMAGESTYLE);
					if ("1" == F[IDX].FBUY) {
						V.find("label.goods-price-label").text("数量：")
					}
					if (!S) {
						$("#goodsList").append(V)
					}
					var X = S ? $("#f" + IDX).find(
							"div.goods-item:eq(" + T + ")")
							: $(DEFFLD.item_goods_f);
					X.find("img.img").attr("src",
							GOODSIMAGEURL + Z.filePath + GOODSIMAGESTYLE);
					if (S) {
						F[IDX].ITMS[T].IMG = Z.filePath
					} else {
						$("#f" + IDX).find("div.content").find(
								"div.nogoods-holder").remove().end().append(X);
						if (!F[IDX].ITMS) {
							F[IDX].ITMS = []
						}
						F[IDX].ITMS.push( {
							IMG : Z.filePath,
							VAL : "商品名称",
							DES : "",
							PRC : 0,
							UNT : ""
						})
					}
				}
				$.hideStatus()
			},
			error : function(W, V, X) {
				$.hideStatus();
				$.alert("上传图片时发生错误：" + X)
			}
		});
		CHANGED = true;
		return false
	};
	$("#fileToUpload").live( {
		change : x
	});
	$("#goodsList input.edit-img-input").live( {
		change : x
	});
	$("#btnAddNoImgGoods").click(
			function() {
				var T = 0;
				$(F).each(function(U, V) {
					if ("goods" === V.TYP) {
						T += V.ITMS.length
					}
				});
				if (T >= goodsNumber) {
					$.alert("最多只能添加" + goodsNumber + "件商品。");
					return false
				}
				var R = $(DEFFLD.item_goods).addClass("noimg");
				if ("1" == F[IDX].FBUY) {
					R.find("label.goods-price-label").text("数量：")
				}
				$("#goodsList").append(R);
				var S = $(DEFFLD.item_goods_f);
				$("#f" + IDX).find("div.content").find("div.nogoods-holder")
						.remove().end().append(S).find("div.image-center")
						.hide();
				if (!F[IDX].ITMS) {
					F[IDX].ITMS = []
				}
				F[IDX].ITMS.push( {
					VAL : "商品名称",
					DES : "",
					PRC : 0,
					UNT : ""
				});
				return false
			});
	var n, w;
	$("#goodsList").sortable(
			{
				axis : "y",
				delay : 100,
				start : function(S, R) {
					n = R.item.index()
				},
				stop : function(U, T) {
					w = T.item.index();
					if (n != w) {
						var S = F[IDX].ITMS[n];
						F[IDX].ITMS.splice(n, 1);
						F[IDX].ITMS.splice(w, 0, S);
						var R = $("#f" + IDX).find("div.content").find(
								"div.goods-item:eq(" + n + ")");
						R.remove();
						if (w == 0) {
							$("#f" + IDX).find("div.content").find(
									"div.goods-item:eq(" + w + ")").before(R)
						} else {
							$("#f" + IDX).find("div.content").find(
									"div.goods-item:eq(" + (w - 1) + ")")
									.after(R)
						}
						CHANGED = true
					}
				}
			});
	$("#uploadImage").live(
			{
				change : function() {
					$.showStatus("正在上传图片...");
					$.ajaxFileUpload( {
						url : "ajaxuploadimage",
						fileElementId : "uploadImage",
						secureuri : false,
						dataType : "json",
						data : {
							OLDIMG : F[IDX].IMG,
							FRMID : M._id
						},
						success : function(T, R) {
							if (T.status != "success") {
								var S = {
									emptyFile : "上传文件为空，请重新选择。",
									sizeMsg : "单张图片不能大于500KB。",
									formatMsg : "只能导入jpg,gif,png,bmp类型的图片文件。"
								};
								$.alert(S[T.msgCode])
							} else {
								$("#f" + IDX).find("div.content img").attr(
										"src", IMAGESURL + T.filePath);
								F[IDX].IMG = T.filePath
							}
							$.hideStatus()
						},
						error : function(S, R, T) {
							$.hideStatus();
							$.alert("上传图片时发生错误：" + T)
						}
					});
					CHANGED = true
				}
			});
	var p = function() {
		var R = $.trim($(this).val());
		if (R) {
			F[IDX].MIN = R
		} else {
			delete F[IDX]["MIN"]
		}
		CHANGED = true
	}, J = function() {
		var R = $.trim($(this).val());
		if (R) {
			F[IDX].MAX = R
		} else {
			delete F[IDX]["MAX"]
		}
		CHANGED = true
	};
	$("#min").bind( {
		keyup : p
	});
	$("#max").bind( {
		keyup : J
	});
	$("#chkMatch,#chkAutoComp")
			.click(
					function() {
						delete F[IDX]["MAT"];
						delete F[IDX]["MATFRM"];
						delete F[IDX]["MATFLD"];
						delete F[IDX]["ACMP"];
						delete F[IDX]["ACMPSRCFLD"];
						delete F[IDX]["ACMFLD"];
						if ($(this).attr("id") == "chkMatch"
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
						}
						var R = $("#chkMatch").prop("checked"), S = $(
								"#chkAutoComp").prop("checked");
						if (R) {
							$("#divMatchContainer").removeClass("hide");
							F[IDX]["MAT"] = "1"
						} else {
							$("#divMatchContainer").addClass("hide")
						}
						if (S) {
							$("#divAutoCompContainer").removeClass("hide");
							F[IDX]["ACMP"] = "1"
						} else {
							$("#divAutoCompContainer").addClass("hide")
						}
						CHANGED = true
					});
	$("#selMatchFrm").change(function() {
		var R = $(this).val();
		fillMatchFields(R, F[IDX].TYP);
		CHANGED = true
	});
	$("#selMatchFld").change(function() {
		F[IDX].MATFLD = $(this).val();
		CHANGED = true
	});
	$("#selAutoCompSrcFld").change(function() {
		var R = $(this).val(), S = "";
		F[IDX].ACMPSRCFLD = R;
		fillAutoCompFlds(R, F[IDX].TYP);
		CHANGED = true
	});
	$("#selAutoCompFld").change(function() {
		F[IDX].ACMPFLD = $(this).val();
		CHANGED = true
	});
	$("#sec_pub,#sec_pri").click(function() {
		if ($("#sec_pub").prop("checked")) {
			F[IDX].SCU = "pub";
			$("#f" + IDX).removeClass("private")
		} else {
			F[IDX].SCU = "pri";
			$("#f" + IDX).addClass("private")
		}
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
			F[IDX].DEF = R
		} else {
			delete F[IDX]["DEF"]
		}
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
			F[IDX].DEF = R
		} else {
			delete F[IDX]["DEF"]
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
						delete F[IDX]["DEF"];
						F[IDX].FMT = $(this).val();
						CHANGED = true
					});
	$("#defval_phone_mobile").bind( {
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
		F[IDX].DEF = R;
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
						F[IDX].DEF = o();
						if (!F[IDX].DEF) {
							delete F[IDX].DEF
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
				F[IDX].DEF = o();
				if (!F[IDX].DEF) {
					delete F[IDX].DEF
				}
				CHANGED = true
			});
	$("#defval_zip").change(
			function() {
				var R = $(this).val();
				$("#f" + IDX).find("select.zip").empty().append(
						$.format('<option value="">{0}</option>', R || "区/县"));
				F[IDX].DEF = o();
				if (!F[IDX].DEF) {
					delete F[IDX].DEF
				}
				CHANGED = true
			});
	$("#dataTable").change(function(){
		var _v=$(this).val();
		if(_v.length>0){
			$.ajax({
				url:resRoot+'/autoform/fieldSingle?tbId='+_v,
				dataType:'json',
				type:'post',
				success:function(data){
					var re=new Array();
					if(data.length>0){
						re.push("<select>");
						for(var i in data){
							re.push("<option value=\""+data[i].fieldName+"\" >"+data[i].fieldAlias+"</option>");
						}
						re.push("</select>");
						$("#dataField").html(re.join(""));
					}
				}
			})
		}
	})
	var v = function(R) {
		if (R) {
			M.INSTR = "1"
		} else {
			M.INSTR = "0";
			$.each(F, function(T, S) {
				if (S.INSTR) {
					M.INSTR = "1";
					return false
				}
			})
		}
		$.each(F, function(T, S) {
			if (M.INSTR === "1" && S.TYP !== "likert" && S.TYP !== "html"
					&& S.TYP !== "section" && S.TYP !== "goods"
					&& S.TYP !== "image") {
				$("#f" + T).addClass("fieldInstruct")
			} else {
				if (M.INSTR !== "1") {
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
			F[IDX].INSTR = S
		} else {
			delete F[IDX]["INSTR"]
		}
		CHANGED = true
	};
	$("#instruct").bind({
		keyup : function() {
			k(this);
			if ((M.INSTR !== "1" && $(this).val())
					|| (M.INSTR === "1" && !$(this).val())) {
				v($(this).val())
			}
		},
		change : function() {
			k(this);
			v($(this).val())
		}
	});
	var I = function() {
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
	$("#html").bind( {
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
		CHANGED = true
	};
	$("#selLayout").bind({
		click : L
	});
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
	$("#btnDup,#fields a.faDup").live(
			{
				click : function() {
					var U = $("#fields>li.field").size();
					if (U >= fieldsLimit) {
						$.alert("最多只能添加" + fieldsLimit + "个字段。");
						return false
					}
					if (F[IDX].TYP == "image") {
						var W = 0;
						$(F).each(function(X, Y) {
							if ("image" === Y.TYP) {
								W++
							}
						});
						if (W >= imageNumber) {
							$.alert("最多只能添加" + imageNumber + "个图片字段。");
							return false
						}
					}
					if (F[IDX].TYP == "goods") {
						var S = 0;
						$(F).each(function(X, Y) {
							if ("goods" === Y.TYP) {
								S += Y.ITMS.length
							}
						});
						if (S >= goodsNumber) {
							$.alert("最多只能添加" + goodsNumber + "件商品。");
							return false
						}
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
					if (T.TYP === "likert") {
						$.each(T.ITMS, function(X, Y) {
							$.each(Y.ITMS, function(Z, aa) {
								delete aa.ITMID
							})
						})
					} else {
						if (T.TYP === "address") {
							delete T.SUBFLDS["ZIP"]["NM"];
							delete T.SUBFLDS["PRV"]["NM"];
							delete T.SUBFLDS["CITY"]["NM"];
							delete T.SUBFLDS["DTL"]["NM"]
						} else {
							if (T.TYP === "map") {
								delete T.SUBFLDS["TXT"]["NM"];
								delete T.SUBFLDS["J"]["NM"];
								delete T.SUBFLDS["W"]["NM"]
							} else {
								if (T.TYP === "file") {
									delete T.SUBFLDS["ID"]["NM"];
									delete T.SUBFLDS["TYP"]["NM"];
									delete T.SUBFLDS["SZ"]["NM"];
									delete T.SUBFLDS["NM"]["NM"]
								} else {
									if (T.TYP === "dropdown2") {
										delete T.SUBFLDS["DD1"]["NM"];
										delete T.SUBFLDS["DD2"]["NM"]
									} else {
										if (T.TYP === "image") {
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
						if (T.TYP === "goods") {
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
							if (T.TYP === "dropdown2") {
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
		F.splice(IDX, 1);
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
					if (F[IDX].FLDID !== undefined && F[IDX].TYP !== "section"
							&& F[IDX].TYP !== "html") {
						if (confirm(delConfirmMsg)) {
							$.showStatus("正在执行删除操作 ...");
							var R = "deleteField";
							$.postJSON(R, {
								_id : M._id,
								FLDID : F[IDX].FLDID,
								TYP : F[IDX].TYP
							}, function() {
								O();
								$.hideStatus()
							})
						}
					} else {
						O()
					}
					CHANGED = true;
					return false
				}
			})
}

//结束
function saveForm(d, e) {
	M.HEIGHT = $("#right").outerHeight() + 50;
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
		$.showStatus("正在保存表单数据 ...")
	}
	/**
	$.postJSON(b, c, function(f) {
		$.hideStatus();
		if (f.ERRMSG) {
			$.alert(f.ERRMSG);
			return
		}
		$.extend(true, M, f.M);
		$.extend(true, F, f.F);
		$("#type").prop("disabled", true);
		if (e) {
			e(M)
		}
		CHANGED = false;
		if (d) {
			$.lightBox( {
				url : "/rs/html/formsaved.html",
				loaded : function() {
					$("#btnPrew").attr("href", "/web/formview/" + M._id)
				}
			})
		}
	})
	*/
}
function addFieldsInit() {
	$("#addFields")
			.find("li")
			.click(
					function() {
						var d = $("#fields>li").size(), b = d - 1, e = "f" + d, g = $(DEFFLD.field_li);
						if (d === 0) {
							$("#fields").append(g)
						} else {
							if (d >= fieldsLimit) {
								$.alert("最多只能添加" + fieldsLimit + "个字段。");
								return false
							}
							if ($(this).attr("ftype") == "image") {
								var f = 0;
								$(F).each(function(c, h) {
									if ("image" === h.TYP) {
										f++
									}
								});
								if (f >= imageNumber) {
									$.alert("最多只能添加" + imageNumber + "个图片字段。");
									return false
								}
							}
							$(".field:eq(" + b + ")", "#fields").after(g)
						}
						g.attr("id", e);
						setDefFieldDom(g, $(this).attr("ftype")
								+ ($(this).attr("subtype") || ""), b + 1);
						CHANGED = true;
						return false
					});
	var a = false;
	$(window).scroll(function() {
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
		/**if (window.CURUSER && window.CURUSER.USERNAME == testUser) {
			return true
		}*/
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
	e = $(DEFFLD[j.TYP].html);
	if ("goods" == j.TYP && "1" == j.NOIMG) {
		e = $(DEFFLD.goodsnoimg.html)
	}
	if (j.TYP === "html" || j.TYP === "section") {
		h = e.find("label.desc");
		f = e.find("div.content")
	} else {
		h = e.filter("label.desc");
		f = e.filter("div.content")
	}
	if (j.TYP === "likert") {
		h = f.find("label.desc")
	}
	g = h.find("span");
	if (j.REQD === "1") {
		g.removeClass("hide")
	}
	h.text(j.LBL);
	h.append(g);
	if (j.TYP === "phone" && j.FMT) {
		f.html(DEFFLD[$.format("phone_{0}_{1}", j.FMT, M.LANG)]);
		"1" == j.AUTH ? f.find(".sendcode").show() : f.find(".sendcode").hide();
		"1" == j.AUTH ? $("#signcnt").show() : $("#signcnt").hide()
	} else {
		if (j.TYP === "date" && j.FMT) {
			f.html(DEFFLD[$.format("date_{0}", j.FMT)])
		} else {
			if (j.TYP === "name" && j.FMT) {
				if (j.FMT === "short") {
					f.html(DEFFLD.name_short)
				} else {
					f.html(DEFFLD[$.format("name_{0}_{1}", j.FMT, M.LANG)])
				}
			} else {
				if (j.TYP === "address") {
					f.html(DEFFLD[$.format("address_{0}", M.LANG)]);
					if (j.DEF) {
						var b = j.DEF.split("-");
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
					if (j.TYP === "radio") {
						createRadioItemsPreview(j, f)
					} else {
						if (j.TYP === "checkbox") {
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
							if (j.TYP === "image") {
								f.find("img").attr(
										"src",
										j.IMG ? IMAGESURL + j.IMG
												: "/rs/images/defaultimg.png")
							} else {
								if (j.TYP === "goods") {
									createGoodsItemsPreView(j, f)
								} else {
									if (j.TYP === "section") {
										f.text(j.SECDESC || "")
									} else {
										if (j.TYP === "html") {
											f
													.html(j.HTML ? j.HTML
															.replace(
																	/<script[\S\s]*<\/script>/gim,
																	"")
															: "")
										} else {
											if (j.TYP === "likert") {
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
	if (j.DEF) {
		var l = [ "text", "textarea", "number", "ulr", "email", "money","phone"];
		if ($.inArray(j.TYP, l) >= 0) {
			if (j.TYP === "phone" && j.FMT === "tel") {
				$.each(j.DEF.split("-"), function(m, c) {
					f.find(":text:eq(" + m + ")").val(c)
				})
			} else {
				e.find(":input").val(j.DEF)
			}
		}
	}
	if (j.FLDSZ) {
		f.find(":text,textarea,select").removeClass("s m xxl")
				.addClass(j.FLDSZ)
	}
	var d = $(DEFFLD.instruct);
	if (j.INSTR) {
		d.text(j.INSTR)
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
	if ("L" === M.LBLAL) {
		a.addClass("leftLabel")
	} else {
		if ("R" === M.LBLAL) {
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
	$("#fTitle").text(M.FRMNM);
	$("#fDescription").html(M.DESC.replace(/\n/gi, "<br/>"))
}
function formInit() {
	var f = function() {
		var j = $(this).val();
		$("#fTitle").text(j);
		M.FRMNM = j;
		CHANGED = true
	};
	$("#formName").bind( {
		keyup : f,
		change : f
	});
	var h = function() {
		var j = $(this).val();
		$("#fDescription").html(M.DESC.replace(/\n/gi, "<br/>"));
		M.DESC = j;
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
		M.LBLAL = k;
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
		if (LVL == 0) {
			$.alert("此功能仅在付费版本中提供，您需要升级才能使用。");
			return false
		}
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
	if (window.CURUSER && CURUSER.USERNAME == testUser) {
		$("#menu").find("li.frm,li.rpt,li.usr,li.act,li.app,li.thm").hide();
		//$("#saveForm").attr("href", "/web/register")
		$("#saveForm").html("<b></b>保存");
	}
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