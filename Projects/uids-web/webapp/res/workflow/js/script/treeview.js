var orgSettings = [], sftPoolIndex = -1, sftResultIndex = -1, sftPoolMouse = 0, sftResultMouse = 0;
if($('#tree_dialog').length < 1){
	//var $dialog_wrap = $('<div id="tree_dialog" class="ioop-dialog tree_dialog"style="display:none;"><h6>组织机构选择窗口<b id="dialog-close-btn" class="dialog-close dialog-close-btn"></b></h6><div class="dialog-main"><div class="btn-content"><a class=""id="thisUnitBtn">本单位</a><a id="positionBtn"class="a-active">职位</a><a id="groupBtn">群组</a><a id="recentContactsBtn">最近联系人</a><a id="commonContactsBtn">常用联系人</a><a id="processBtn">流程变量</a><a class="last-a"id="dialog-closed"><em></em>保存并关闭</a></div><div class="dialogBox-content"><div class="dialogBox dialogbBox-frist"><div class="box-title"><h2>组织机构</h2></div><!--组织机构树--><div id="_thisUnitBtn"class="person-data"><div id="tree-warpe"><ul id="tree-view"class="tree-view ztree dialog-tree"></ul></div></div><!--所有单位--><div id="_allUnitBtn"class="person-data"><div id="tree-warpe1"><ul id="unit-view"class="tree-view ztree dialog-tree"></ul></div></div><!--群组--><div class="list-persions person-data"id="_groupBtn"style="display:none;"><ul></ul></div><!--职位--><div class="list-persions person-data"id="_positionBtn"style="display:none;"><ul></ul></div><!--最近联系人--><div class="person-data"id="_recentContactsBtn"style="display:none;"><ul class="box-content"></ul></div><!--常用联系人--><div class="person-data"id="_commonContactsBtn"style="display:none;"><ul class="box-content"></ul></div></div><div class="step"></div><div class="dialogBox"><div class="box-title"><h2>用户列表</h2><div class="input-search"><input type="text" id="search-input" class="placeholder" placeholder="简全拼及用户名" /><em id="search-btn"></em></div></div><div id="person-warp"><ul class="box-content"id="person-content"></ul></div></div><div class="step step2"><a class="prev-a" id="r-to-p" title="移除已选择列表"></a><a id="p-to-r"title="将选中人员添加到选择列表"></a></div><div class="dialogBox last-dialogBox"><div class="box-title"><h2>已选择项</h2><em id="sort-up"title="向上"></em><em class="last-em-btn"id="sort-down"title="向下"></em><a id="saveAsCommon"title="保存为常用联系人"></a><a id="saveAsGroup"title="保存为群组"class="last-a-btn"></a></div><div id="result-warp"><ul class="box-content"id="result-list"></ul></div></div></div></div></div>') ;
	var $dialog_wrap = $('<div id="tree_dialog" class="ioop-dialog tree_dialog"style="display:none;"><h6>组织机构选择窗口<b id="dialog-close-btn" class="dialog-close dialog-close-btn"></b></h6><div class="dialog-main"><div class="btn-content"><a class=""id="thisUnitBtn">本单位</a><a class="last-a"id="dialog-closed"><em></em>保存并关闭</a></div><div class="dialogBox-content"><div class="dialogBox dialogbBox-frist"><div class="box-title"><h2>组织机构</h2></div><!--组织机构树--><div id="_thisUnitBtn"class="person-data"><div id="tree-warpe"><ul id="tree-view"class="tree-view ztree dialog-tree"></ul></div></div><!--所有单位--><div id="_allUnitBtn"class="person-data"><div id="tree-warpe1"><ul id="unit-view"class="tree-view ztree dialog-tree"></ul></div></div><!--群组--><div class="list-persions person-data"id="_groupBtn"style="display:none;"><ul></ul></div><!--职位--><div class="list-persions person-data"id="_positionBtn"style="display:none;"><ul></ul></div><!--最近联系人--><div class="person-data"id="_recentContactsBtn"style="display:none;"><ul class="box-content"></ul></div><!--常用联系人--><div class="person-data"id="_commonContactsBtn"style="display:none;"><ul class="box-content"></ul></div></div><div class="step"></div><div class="dialogBox"><div class="box-title"><h2>用户列表</h2><div class="input-search"><input type="text" id="search-input" class="placeholder" placeholder="简全拼及用户名" /><em id="search-btn"></em></div></div><div id="person-warp"><ul class="box-content"id="person-content"></ul></div></div><div class="step step2"><a class="prev-a" id="r-to-p" title="移除已选择列表"></a><a id="p-to-r"title="将选中人员添加到选择列表"></a></div><div class="dialogBox last-dialogBox"><div class="box-title"><h2>已选择项</h2><em id="sort-up"title="向上"></em><em class="last-em-btn"id="sort-down"title="向下"></em><a id="saveAsCommon"title="保存为常用联系人"></a><a id="saveAsGroup"title="保存为群组"class="last-a-btn"></a></div><div id="result-warp"><ul class="box-content"id="result-list"></ul></div></div></div></div></div>') ;
	$("body").on("mouseup", function(){
		sftPoolMouse = 0;
		sftResultMouse = 0;
	}).append($dialog_wrap);
	//禁止选中文本
	if (typeof($("#person-warp")[0].onselectstart) != "undefined") {       
	    // IE下禁止元素被选取       
		$("#person-warp")[0].onselectstart = $("#result-warp")[0].onselectstart = function(){
			return false;
		};   
	}
}

/* 创建一个闭包 */
(function ($) {
{    /* 插件的定义 */
    $.fn.treeView = function (options) {
        var opts = $.extend( {}, $.fn.treeView.defaults, options);
        return this.each(function () {
            $this = $(this);
            var o =  $.extend(true ,{}, opts, $this.data());
			opts.handlerBtn = $this.attr('id');
			orgSettings.push(opts);
			$("#tree_dialog").data($this.attr('id'), opts);
			//初始化
			init($this);
        });
    };
	function init($this){
		$this.on('click',function(){
			if($('#tree_dialog').css('display') == 'none'){
				//权限配置数组
				var r = $("#tree_dialog").data($(this).attr('id')) ;
				//初始化Dom元素
				resetDom();
				//配置头部菜单显示隐藏
				topBtnRights(r);
				//默认按钮点击
				$(r._defaultBtn).trigger('click');
				
				//$('#tree_dialog').dialogShow();
				if(!r.treeViewBar){
				    $("#tree_dialog>h6").hide();
				    $("#tree_dialog").height(454);
				}else{
					$("#tree_dialog>h6").show();
					$("#tree_dialog").height(493);
				}
                IOOP.centerDialogShow({
                    dialogId: 'tree_dialog',
                    isClose: false,
                    maskLevel: "01",
                    preventDrag: r.preventDrag
                });
				$("html,body").css("overflow", "hidden");
				//保存为常用联系人 保存为最近联系人  搜索框自动搜索人员
				bindSaveResult(r);
				
				//加载默认数据
				fillingResult($this,r);
				
				//绑定选人按钮事件
				l_r_btnHandler(r);
				
				//已选择项排序
				bindSortHander();
				
				//配置弹出框关闭按钮事件
				closeBtnHander(r ,$this);				
			}
		});
	}
	function resetDom(){
		//$('#tree_dialog').find('.btn-content').html('<a class=""id="thisUnitBtn">本单位</a><a id="positionBtn"class="a-active">职位</a><a id="groupBtn">群组</a><a id="recentContactsBtn">最近联系人</a><a id="commonContactsBtn">常用联系人</a><a id="processBtn">流程变量</a><a class="last-a"id="dialog-closed"><em></em>保存并关闭</a>');
		$('#tree_dialog').find('.btn-content').html('<a class=""id="thisUnitBtn">本单位</a><a class="last-a"id="dialog-closed"><em></em>保存并关闭</a>');
		$('#tree_dialog').find('.dialogBox:first').html('<div class="box-title"><h2>组织机构</h2></div><!--组织机构树--><div id="_thisUnitBtn"class="person-data"style="display: none;"><div id="tree-warpe"><ul id="tree-view"class="tree-view ztree dialog-tree"></ul></div></div><!--所有单位--><div id="_allUnitBtn"class="person-data"style="display: none;"><div id="tree-warpe1"><ul id="unit-view"class="tree-view ztree dialog-tree"></ul></div></div><!--群组--><div class="list-persions person-data"id="_groupBtn"style="display: none;"><ul></ul></div><!--职位--><div class="list-persions person-data"id="_positionBtn"style="display: block;"><ul></ul></div><!--最近联系人--><div class="person-data"id="_recentContactsBtn"style="display: none;"><ul class="box-content"></ul></div><!--常用联系人--><div class="person-data"id="_commonContactsBtn"style="display: none;"><ul class="box-content"></ul></div><!--流程变量--><div class="person-data"id="_processBtn"style="display: none;"><ul class="box-content"></ul></div>');
	}
	
	//注册保存为群组和保存为常用联系人按钮事件 , input框事件监听
	function bindSaveResult(r){
		//保存为常用联系人
		$('#saveAsCommon').off('click').on('click',function(){
			var resultList = $("#result-list").find('li'),
			    ids = '',
			    names = '';
			if(resultList.length == 0){
				IOOP.showalert({
				    showTitle:'保存常用联系人',
				    showMsg:'您没有选择联系人哦！'
				});
				return false;
			}
			$(resultList).each(function(index, e) {
				ids =ids + $(e).find('a').attr('uid')+',';
				names = names + $(e).find('a').attr('uname')+',';
			});
			IOOP.showalert({showTitle:'保存常用联系人',showMsg:'您确定要保存常用联系人吗？',alertType: "confirm",btnOKHandler:function(){
				dwr.engine.setAsync(false);
				orgTreeAjax.saveUsedContacts(ids,names,r._USER_ID,function(data){
					if(data){
						var json = eval("("+data+")");
						if(json.succ == 1){
							IOOP.showtips({
								tipsMsg: json.msg,
								isOK: true,
								tipsDelay: 2000,
								selecter: "#tree_dialog",
								offsetX: 0,
								offsetY: 30,
								isFixed: false
							});
							$('#commonContactsBtn').trigger('click') ;
						}else{
							IOOP.showalert({showTitle:'保存常用联系人',showMsg:json.msg});
						}
						
					}
				});
				dwr.engine.setAsync(true);
			}});
		});
		//搜索按钮事件监听
		$("#search-btn").off("click").on("click",function(){
			$("#search-input").keyup();
		});
		//搜索框值清空
		$('#search-input').val('');
		//搜索框事件监听
		$('#search-input').off('keyup').on('keyup',function() {
			var str = $(this).val() ;
			if(!str){
				return false;
			}
			//dwr.engine.setAsync(false);
			var isFocusAllUnitBtn = $("#allUnitBtn").hasClass("a-active");//组织机构框顶部按钮是否聚焦到所有单位
			if(r._allUnitBtn&&isFocusAllUnitBtn){
				orgTreeAjax.getAccountsetUserTreeVoListByNameSpl(r._ORG_ID,str,function(data){
					if(data){
						appendAllUnitPersonList(data,r);
					}
				});
			}else{
				orgTreeAjax.getUserTreeVoListByNameSpl(r._ORG_ID,str,function(data){
					if(data){
						appendPersonList(data,r);
					}
				});
			}

			//dwr.engine.setAsync(true);
		});
		
		//添加关闭按钮事件
		$("#dialog-close-btn").off("click").on("click",function(){
            IOOP.centerDialogShow({
                dialogId: 'tree_dialog',
                isClose: true,
                maskLevel: "01"
            });
			$("html,body").css("overflow", "auto");
		});
	}
	//关闭按钮事件
	function closeBtnHander(r ,t){
		var resultData = {
			"ORG_ID":[],
			"ORG_NAME":[],
			"DEPT_ID":[],
			"DEPT_NAME":[],
			"POST_ID":[],
			"POST_NAME":[],
			"ACCOUNTSET_ID":[],
			"ACCOUNTSET_NAME":[],
			"RECENTCONTACTS_ID":[],
			"RECENTCONTACTS_NAME":[],
			"GROUP_ID":[],
			"GROUP_NAME":[],
			"USEDCONTACTS_ID":[],
			"USEDCONTACTS_NAME":[],
			"USER_ID":[],
			"USER_NAME":[],
			"WORKFLOW_ID":[],
			"WORKFLOW_NAME":[],
			"ALL_ID":[],
			"ALL_NAME":[]
		};
		$('#dialog-closed').off('click').on('click',function(){
			//清空选择项
			$(r.showValue).html("");
			var _deptName = "", _resultHtml = [];
			$('#result-list li a').each(function(index, element) {
				_deptName = $(element).attr('deptName');
				_orgName = $(element).attr('orgName');
				if(!r.resultJson){
				    if(r._allUnitBtn&&_orgName&&_orgName!='null'&&_orgName!='undefined'){
				    	_resultHtml.push('<a class="users-item" uid="'+ $(element).attr('uid') +'" uname="'+ $(element).attr('uname') +'" type="'+ $(element).attr('type') +'" deptName="'+ (!_deptName?'':_deptName) +'" orgName="'+_orgName+'">');
					   _resultHtml.push('<p class="over-flow" title="'+"【"+_orgName+"】"+$(element).attr('uname') +'">'+"【"+_orgName+"】"+ $(element).attr('uname') +'</p>');
				   }else{
					   _resultHtml.push('<a class="users-item" uid="'+ $(element).attr('uid') +'" uname="'+ $(element).attr('uname') +'" type="'+ $(element).attr('type') +'" deptName="'+ (!_deptName?'':_deptName) +'">');
					   _resultHtml.push('<p class="over-flow" title="'+ $(element).attr('uname') +'">'+ $(element).attr('uname') +'</p>');
				   }
				    _resultHtml.push('<b class="user-remove" title="移除"></b></a>');
				}
                resultData[$(element).attr("type")+"_ID"].push($(element).attr("uid"));
                
                if($(element).attr("orgName")&&$(element).attr("orgName")!=null
                		&&$(element).attr("orgName")!='null'&&$(element).attr("orgName")!='undefined'){//如果是从帐套选择的节点，节点属性orgName不为空，要拼接之后传到后台
					resultData[$(element).attr("type")+"_NAME"].push("【"+$(element).attr("orgName")+"】"+$(element).attr("uname"));
				}else{
					resultData[$(element).attr("type")+"_NAME"].push($(element).attr("uname"));
				}
                
				resultData["ALL_ID"].push($(element).attr("uid"));
				if($(element).attr("orgName")&&$(element).attr("orgName")!=null
						&&$(element).attr("orgName")!='null'&&$(element).attr("orgName")!='undefined'){//如果是从帐套选择的节点，节点属性orgName不为空，要拼接之后传到后台
					resultData["ALL_NAME"].push("【"+$(element).attr("orgName")+"】"+$(element).attr("uname"));
				}else{
					resultData["ALL_NAME"].push($(element).attr("uname"));
				}
			});
			$(r.showValue).append(_resultHtml.join(""));
            IOOP.centerDialogShow({
                dialogId: 'tree_dialog',
                isClose: true,
                maskLevel: "01"
            });
			$("html,body").css("overflow", "auto");
			if(typeof r.resultHandler == 'function'){
				r.resultHandler(resultData);
			}
		});
	}
	
	//删除常用联系人列表数据
	function deleteCommonContacts(r,id,name){
		IOOP.showalert({showTitle:'删除常用联系人',showMsg:'您确定将'+name+'从常用联系人列表中删除吗？',alertType: "confirm",btnOKHandler:function(){
			dwr.engine.setAsync(false);
			orgTreeAjax.deleteUsedContacts(id,r._USER_ID,function(data){
				if(data){
					var json = eval("("+data+")");
					if(json.succ == 1){
						IOOP.showtips({
							tipsMsg: json.msg,
							isOK: true,
							tipsDelay: 2000,
							selecter: ".dialog-main",
							offsetX: 0,
							offsetY: 0
						});
						$('#commonContactsBtn').trigger('click') ;
					}else{
						IOOP.showalert({showTitle:'删除常用联系人',showMsg:json.msg});
					}
					
					
				}
			});
			dwr.engine.setAsync(true);
		}});
	}
	//加载默认数据
	function fillingResult(t,r){
		$('#result-list').html("");
		var fillings = [],_resJson = r.resultJson; 
		if(!!_resJson){
			//遍历单位
			if(_resJson.ORG_ID && _resJson.ORG_ID.length>0){
				$(_resJson.ORG_ID).each(function(index, element){
					if(!!element){
					    fillings.push({name:_resJson.ORG_NAME[index],id:_resJson.ORG_ID[index],type:"ORG",deptName:""});
					}
				});
			}
			//遍历部门
			if(_resJson.DEPT_ID && _resJson.DEPT_ID.length>0){
				$(_resJson.DEPT_ID).each(function(index, element){
					if(!!element){
					    fillings.push({name:_resJson.DEPT_NAME[index],id:_resJson.DEPT_ID[index],type:"DEPT",deptName:""});
					}
				});
			}
			//遍历职位
			if(_resJson.POST_ID && _resJson.POST_ID.length>0){
				$(_resJson.POST_ID).each(function(index, element){
					if(!!element){
					    fillings.push({name:_resJson.POST_NAME[index],id:_resJson.POST_ID[index],type:"POST",deptName:""});
					}
				});
			}
			//遍历单位帐套
			if(_resJson.ACCOUNTSET_ID && _resJson.ACCOUNTSET_ID.length>0){
				$(_resJson.ACCOUNTSET_ID).each(function(index, element){
					if(!!element){
					    fillings.push({name:_resJson.ACCOUNTSET_NAME[index],id:_resJson.ACCOUNTSET_ID[index],type:"ACCOUNTSET",deptName:""});
					}
				});
			}
			//遍历最近联系人
			if(_resJson.RECENTCONTACTS_ID && _resJson.RECENTCONTACTS_ID.length>0){
				$(_resJson.RECENTCONTACTS_ID).each(function(index, element){
					if(!!element){
					    fillings.push({name:_resJson.RECENTCONTACTS_NAME[index],id:_resJson.RECENTCONTACTS_ID[index],type:"RECENTCONTACTS",deptName:""});
					}
				});
			}
			//遍历群组
			if(_resJson.GROUP_ID && _resJson.GROUP_ID.length>0){
				$(_resJson.GROUP_ID).each(function(index, element){
					if(!!element){
					    fillings.push({name:_resJson.GROUP_NAME[index],id:_resJson.GROUP_ID[index],type:"GROUP",deptName:""});
					}
				});
			}
			//遍历常用联系人
			if(_resJson.USEDCONTACTS_ID && _resJson.USEDCONTACTS_ID.length>0){
				$(_resJson.USEDCONTACTS_ID).each(function(index, element){
					if(!!element){
					    fillings.push({name:_resJson.USEDCONTACTS_NAME[index],id:_resJson.USEDCONTACTS_ID[index],type:"USEDCONTACTS",deptName:""});
					}
				});
			}
			//遍历用户
			if(_resJson.USER_ID && _resJson.USER_ID.length>0){
				$(_resJson.USER_ID).each(function(index, element){
					if(!!element){
					    fillings.push({name:_resJson.USER_NAME[index],id:_resJson.USER_ID[index],type:"USER",deptName:""});
					}
				});
			}
			//遍历流程变量
			if(_resJson.WORKFLOW_ID && _resJson.WORKFLOW_ID.length>0){
				$(_resJson.WORKFLOW_ID).each(function(index, element){
					if(!!element){
					    fillings.push({name:_resJson.WORKFLOW_NAME[index],id:_resJson.WORKFLOW_ID[index],type:"WORKFLOW",deptName:""});
					}
				});
			}
		}else{
			$(r.showValue).find('a').each(function(index, element) {
				fillings.push({name:$(element).attr('uname'),id:$(element).attr('uid'),type:$(element).attr('type'),deptName:$(element).attr('deptName'),orgName:$(element).attr('orgName')});
	        });
		}
		 if(r._allUnitBtn){
			 appendAllUnitResultList(fillings, r);
		 }else{
			 appendResultList(fillings, r);
		 }
	}
	
	//根据职位ID加载人员数据
	function getPersonByPositionID(p_id){
		var positionDate ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getUserTreeVoListByPost(p_id,function(data){
			if(data){
				positionDate = data;
			}
		});
		dwr.engine.setAsync(true);
		
		/*	$.ajax({
				url: "sys/org-tree!getUserTreeVoListByPost.do",   
				global: false,
				async:false,
				type: "POST" ,
				data: {postId:p_id},
		    	dataType: "json",
		    	success: function (data) {
		    		positionDate = data;
		    	}	
			});
		*/
		return positionDate ;
	}
	
	//加载本单位职位数据
	function getPositionDate(r){
		/**/
		var positionDate ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getPostTree(r._ORG_ID,function(data){
			if(data){
				positionDate = data;
			}
		});
		dwr.engine.setAsync(true);
		return positionDate ;
	}
	
	//根据群组ID加载人员数据
	function getPersonByGroupID(g_id){

		var groupDate ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getUserTreeVoListByGroup(g_id,function(data){
			if(data){
				groupDate = data;
			}
		});
		dwr.engine.setAsync(true);
		
		/**/
		return groupDate ;
	}
	//加载群组数据
	function getGroupDate(r){
		var groupDate ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getGroupTree(r._ORG_ID,r._USER_ID,function(data){
			if(data){
				groupDate = data;
			}
		});
		dwr.engine.setAsync(true);
		

		return groupDate ;
	}
	//根据最近联系人ID加载最近联系人数据
	function getRecentDateById(id , r){
		var RecentDate ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getUserTreeVoListByObject(id,function(data){
			if(data){
				RecentDate = data;
			}
		});
		dwr.engine.setAsync(true);
		/**/
		return RecentDate ;
	}
	
	//加载最近联系人数据
	function getRecentDate(r){
		var RecentDate ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getRecentContactsTree(r._USER_ID,function(data){
			if(data){
				RecentDate = data;
			}
		});
		dwr.engine.setAsync(true);
		/**/
		return RecentDate ;
	}
	//根据常用联系人ID加载常用联系人数据
	function getCommonDateById(id , r){
		var CommonDate ;
		
		dwr.engine.setAsync(false);
		orgTreeAjax.getUserTreeVoListByObject(id,function(data){
			if(data){
				CommonDate = data;
			}
		});
		dwr.engine.setAsync(true);
		/**/
		return CommonDate ;
	}
	//加载常用联系人数据
	function getCommonDate(r){
		var CommonDate ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getUsedContactsTree(r._USER_ID,function(data){
			if(data){
				CommonDate = data;
			}
		});
		dwr.engine.setAsync(true);
		/**/
		return CommonDate ;
	}
	
	//根据本单位部门ID获取人员列表
	function getTreeByPID(r,nId){
		var treeDate ;
		dwr.engine.setAsync(false);
		if(r._ORG_ID == nId){
			orgTreeAjax.getUserTreeVoListByOrg(nId,function(data){
				if(data){
					treeDate = data;
				}
			});
		}else{
			orgTreeAjax.getUserTreeVoListByDept(nId,function(data){
				if(data){
					treeDate = data;
				}
			});
		}
		
		dwr.engine.setAsync(true);
		
		/****/
		return treeDate ;
	}
	
	//加载本单位树组件数据
	function getUnitDate(r){
		var zNodes ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getOrgAndDeptTree(r._ORG_ID,function(data){
		if(data){
				zNodes = data;
		}
		});
		dwr.engine.setAsync(true);
 		return zNodes ; 	
	}
	
	//删除结果列表by uid
	function deleteResultByUid(uid , r){
		$('#result-list').find("[uid='" + uid + "']").parent().remove();
	}
	
	//加载所有单位树组件数据
	function getAllUnitDate(r){
		/*
		var zNodes =[
			{ "id":"123321asddd", pId:0, name:"甘肃万维", type : "ORG",icon:"res/skin/default/plugin/ztree/css/img/computer.png"},
			{ id:11, pId:"123321asddd", name:"公司领导", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:12, pId:"123321asddd", name:"办公室",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:2, pId:"123321asddd", name:"财务部", type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:3, pId:"123321asddd", name:"移动互联事业部", type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:31, pId:3, name:"流量运营部", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:4, pId:"123321asddd", name:"技术服务事业部", type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:41, pId:4, name:"集成部",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:411, pId:41, name:"行业外项目组", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:412, pId:41, name:"行业内项目组",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:413, pId:41, name:"技术支撑组",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:42, pId:4, name:"云计算部",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:5, pId:"123321asddd", name:"软件事业部",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:51, pId:5, name:"综合办公产品部", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:511, pId:51, name:"综合办公", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:512, pId:51, name:"协同办公", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:52, pId:5, name:"产品部",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:"sa123321asd", pId:0, name:"甘肃万维2", type : "ORG",icon:"res/skin/default/plugin/ztree/css/img/computer.png"},
			{ id:11, pId:"sa123321asd", name:"公司领导", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:12, pId:"sa123321asd", name:"办公室",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:2, pId:"sa123321asd", name:"财务部", type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:3, pId:"sa123321asd", name:"移动互联事业部", type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:31, pId:3, name:"流量运营部", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:4, pId:"sa123321asd", name:"技术服务事业部", type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:41, pId:4, name:"集成部",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:411, pId:41, name:"行业外项目组", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:412, pId:41, name:"行业内项目组",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:413, pId:41, name:"技术支撑组",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:42, pId:4, name:"云计算部",  type : "DEPT",open:true,i;con:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:5, pId:"sa123321asd", name:"软件事业部",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:51, pId:5, name:"综合办公产品部", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:511, pId:51, name:"综合办公", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:512, pId:51, name:"协同办公", type : "DEPT", open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"},
			{ id:52, pId:5, name:"产品部",  type : "DEPT",open:true,icon:"res/skin/default/plugin/ztree/css/img/org-dialog-tree-element.png"}
			];*/
		var zNodes ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getAccountsetTreeByOrgId(r._ORG_ID,"","","",function(data){
		if(data){
			zNodes = data;
		}
		});
		dwr.engine.setAsync(true);
 		return zNodes ; 
	}
	
	//根据所有单位树节点ID获取人员列表
	function getAllUnitTreeByPID(nId,nodeType,accountSetId,currentOrgId){
		
		/*var treeDate = [	
							{name : "连博博博" ,id:(new Date()).getTime()+1, type:'部门', deptName : '软件事业部',pId:(new Date()).getTime()-1},
							{name : "连2博" , id:(new Date()).getTime()+2,type:'部门',deptName : '软件事业部',pId:(new Date()).getTime()-1},
							{name : "连博博博" ,id:(new Date()).getTime()+3,type:'部门', deptName : '软件事业部',pId:(new Date()).getTime()-1},
							{name : "部门" , id:(new Date()).getTime()+4,type:'部门',deptName : '软件事业部',pId:(new Date()).getTime()-1}
										 ];
		treeDate[0].name = "连博" + Math.ceil(Math.random()*10);*/
		var treeDate ;
		dwr.engine.setAsync(false);
		if(nodeType == "ORG"){
			orgTreeAjax.getUserTreeVoListByAccountOrg(nId.split("_")[2],function(data){
				if(data){
					treeDate = data;
				}
			});
		}else if(nodeType=="DEPT"){
			orgTreeAjax.getUserTreeVoListByAccountDept(nId.split("_")[2],function(data){
				if(data){
					treeDate = data;
				}
			});
		}else if(nodeType=="ACCOUNTSET"){
			/*
			orgTreeAjax.getUserTreeVoListByAccountSet(currentOrgId,nId,function(data){
				if(data){
					treeDate = data;
				}
			});*/
		}
		
		dwr.engine.setAsync(true);
		return treeDate ;
	}
	
	//根据流程变量数据
	function getProcessDate(r){
		var treeDate ;
		dwr.engine.setAsync(false);
		orgTreeAjax.getWorkFlowDataTree(r._ORG_ID,function(data){
			if(data){
				treeDate = data;
			}
		});
		dwr.engine.setAsync(true);
		return treeDate ;
	}
	
	//配置所有单位树事件
	function allUnitTreeClick(r){
		//绑定权限 1、是否能选择 ORG 单位 2、是否能选择所有单位的部门DEPT 部门
		r._allUnitTreeSetting.callback.beforeCheck = function (treeId,treeNode){
			//如果为单位 控制是否能选择
			if(treeNode.type == 'ORG'){
				if(!r._orgSelect){
				    return false;
				}
			}
			//如果为部门 控制是否能选择
			if(treeNode.type == 'DEPT'){ 
				if(!r._orgDeptSelect){
				    return false
				};
			}
			//如果为帐套是否能选人 
			if(treeNode.type == 'ACCOUNTSET'){ 
				    return false
			}
			return true ;
		}
		
		//绑定节点点击事件
		r._allUnitTreeSetting.callback.onClick = function (event,treeId,treeNode,clickFlag){
			//填充用户列表
			var personDate = getAllUnitTreeByPID(treeNode.id,treeNode.type,treeNode.accountSetId,r._ORG_ID) ;
			appendAllUnitPersonList(personDate, r);
		}
		//添加节点选择事件
		r._allUnitTreeSetting.callback.onCheck = function (event,treeId,treeNode){
			//如果树组件是选中的
			if(treeNode.checked){
				if(treeNode.type=="ORG"){
					selectAllChildrenNodes(treeNode,r);
				}else{
					appendAllUnitResultList([{name:treeNode.name,id:treeNode.id,type:treeNode.type,tel:treeNode.tel,pId:treeNode.pId,orgName:treeNode.orgName}],r);
				}
			}else{
				 //去掉当前节点
				//deleteResultByUid(treeNode.id , r);
				 //如果是父节点取消该节点及所有子节点选中状态
				cancleSelectAllChildrenNodes(treeNode,r);
			}	
		}
	}
	//递归选择所有的类型为org的子节点
	function selectAllChildrenNodes(treeNode,r){
			  //debugger;
		      if (treeNode.isParent) {
		        var childrenNodes = treeNode.children;
				//添加节点信息到 所选择项 
				if(treeNode.type=="ORG"){
					appendAllUnitResultList([{name:treeNode.name,id:treeNode.id,type:treeNode.type,tel:treeNode.tel,pId:treeNode.pId,orgName:treeNode.orgName}],r);
				}
		        if (childrenNodes) {
		            for (var i = 0; i < childrenNodes.length; i++) {
		                selectAllChildrenNodes(childrenNodes[i],r);
		            }
		        }
		    }
	}
	//递归选择所有的类型为org的子节点
	function cancleSelectAllChildrenNodes(treeNode,r){
		deleteResultByUid(treeNode.id , r);
		if(treeNode.isParent){
			//debugger;
			 var childrenNodes = treeNode.children;
			if (childrenNodes) {
	            for (var i = 0; i < childrenNodes.length; i++) {
		            cancleSelectAllChildrenNodes(childrenNodes[i],r);
		        }
			}
		}
	}
	//配置本单位树事件
	function unitTreeClick(r){
		//绑定权限 1、是否能选择 ORG 单位 2、是否能选择部门DEPT 部门
		r._treeSetting.callback.beforeCheck = function (treeId,treeNode){
			//如果为单位 控制是否能选择
			if(treeNode.type == 'ORG'){
				if( !r._orgSelect){ return false;}
			}
			//如果为部门 控制是否能选择
			if(treeNode.type == 'DEPT'){ 
				if(!r._depSelect){return false};
			}
			return true ;
		}
		
		//绑定节点点击事件
		r._treeSetting.callback.onClick = function (event,treeId,treeNode,clickFlag){
			//填充用户列表
			var personDate = getTreeByPID(r,treeNode.id) ;
			appendPersonList(personDate , r);
		}
		//添加节点选择事件
		r._treeSetting.callback.onCheck = function (event,treeId,treeNode){
			//如果树组件是选中的
			if(treeNode.checked){
				//添加节点信息到 所选择项
				appendResultList([{name:treeNode.name,id:treeNode.id,type:treeNode.type,tel:treeNode.tel,pId:treeNode.pId}],r);
			}else{
				 //去掉当前节点
				deleteResultByUid(treeNode.id , r);
				 //如果父节点，取消所有子节点
				if(treeNode.isParent){
					$(treeNode.children).each(function(index, element) {
						deleteResultByUid(element.id , r);
					});
				}
			}	
		}
	}
	//头部按钮事件绑定
	function topBtnRights(r){
		//所有单位按钮
		if(!r._allUnitBtn){
			$('#allUnitBtn').remove();
			$('#_allUnitBtn').remove();
		}else{
			$("#allUnitBtn").off('click').on('click',function(event){
				//清空用户列表
				$('#person-content li').remove();
				//加载职位列表
				//显示隐藏
				$('.dialogBox .person-data').css('display','none');
				$('#_allUnitBtn').css('display','block');
				//样式变化
				if(!$(this).hasClass('a-active')){
					$(this).parent().find('.a-active').removeClass('a-active');
					$(this).addClass('a-active');
				}else{return};
				//获取所有单位树组件数据
				r._allUnitDate =  getAllUnitDate(r) ;
				//配置树事件
				allUnitTreeClick(r);
				//实例化树组件
				r._allUnitTree = $.fn.zTree.init($('#unit-view'), r._allUnitTreeSetting, r._allUnitDate);
			});
		}
			
		//本单位按钮
		if(!r._thisUnitBtn){
			$('#thisUnitBtn').remove();
			$('#_thisUnitBtn').remove();
		}else{
			$("#thisUnitBtn").on('click',function(event){
				//清空用户列表
				$('#person-content li').remove();
				//显示隐藏
				$('.dialogBox .person-data').css('display','none');
				$('#_thisUnitBtn').css('display','block');
				//样式变化
				if(!$(this).hasClass('a-active')){
					$(this).parent().find('.a-active').removeClass('a-active');
					$(this).addClass('a-active');
				}else{
				    return false;
				};
				//获取本单位树组件数据
				r._treeDate =  getUnitDate(r) ;
				//配置树事件
				unitTreeClick(r);
				//实例化树组件
				r._unitTree = $.fn.zTree.init($('#tree-view'), r._treeSetting, r._treeDate);
			});
		}
		//职位按钮
		if(!r._positionBtn){
			$('#positionBtn').remove();
			$('#_positionBtn').remove();
		}else{
			//加载职位列表
			$("#positionBtn").off('click').on('click',function(event){
				var _positionDate = getPositionDate(r);
				//显示隐藏
				$('.dialogBox .person-data').css('display','none');
				$('#_positionBtn').css('display','block');
				//清空用户列表
				$('#person-content li').remove();
				//样式变化
				if(!$(this).hasClass('a-active')){
					$(this).parent().find('.a-active').removeClass('a-active');
					$(this).addClass('a-active');
				}else{
				    return false;
				}
				//填充组织机构数据
				var $p_content = $('#_positionBtn ul');
				$p_content.find('li').remove();
				var _positionHtml = [];
				$(_positionDate).each(function(index, element) {
				    _positionHtml.push('<li class="over-flow items" uid="'+ element.id +'"');
				    _positionHtml.push(' uname="'+ element.name +'"');
				    _positionHtml.push(' type="'+ element.type +'"');
				    _positionHtml.push(' pId="'+ element.pId +'"');
				    _positionHtml.push(' deptName="'+ element.deptName +'"');
				    _positionHtml.push(' title="'+ element.name + (element.tel ? ("(") + element.tel + (")") : '') +'"');
				    _positionHtml.push('>'+ element.name + (element.tel ? ("(") + element.tel + (")") : '') +'</li>');
				});
				$p_content.append(_positionHtml.join(""));
				 
				//绑事件 单击
				$p_content.find('li').on('click',function(){
				    if(!$(this).hasClass('li-active')){
						//样式变化
						$('.li-active').removeClass('li-active');
						$(this).addClass('li-active');
						//根据职位ID获得用户列表，并填充至用户列表
						appendPersonList(getPersonByPositionID($(this).attr("uid")) , r);
				    }
				});
				//绑事件 双击-填充已选择项列表
				//如果不能选择职位 ， 则不绑事件 _positionSelect
				if(r._positionSelect){
					$p_content.find('li').on('dblclick',function(){
					    $('.li-active').removeClass('li-active');
						$(this).addClass('li-active');
						//将职位信息直接填充至已选择项
						appendResultList([{id:$(this).attr("uid"), name:$(this).attr("uname"), deptName:$(this).attr("deptName"), type:$(this).attr("type"), pId:$(this).attr("pId")}], r);
				    });  
			 	 }
			});
		}
		
		//群组按钮
		if(!r._groupBtn){
			$('#groupBtn').remove();
			$('#_groupBtn').remove();
		}else{
			//加载群组列表
			$("#groupBtn").off('click').on('click',function(event){
				var _GroupDate = getGroupDate(r);
				//清空用户列表
				$('#person-content li').remove();
				//显示隐藏
				$('.dialogBox .person-data').css('display','none');
				$('#_groupBtn').css('display','block');
				//样式变化
				if(!$(this).hasClass('a-active')){
					$(this).parent().find('.a-active').removeClass('a-active');
					$(this).addClass('a-active');
				}else{
				    return false;
				}
				//填充组织机构数据
				var $p_content = $('#_groupBtn').find('ul'); 
				$p_content.find('li').remove();
				var _groupHtml = [];
				$(_GroupDate).each(function(index, element) {
				    _groupHtml.push('<li class="over-flow titles" uid="'+ element.id  +'"');
				    _groupHtml.push(' type="'+ element.type +'"');
				    _groupHtml.push(' title="'+ element.titles +'"');
				    _groupHtml.push('>'+ element.titles +'</li>');
					$(element.items).each(function(i, e) {
					    _groupHtml.push('<li class="over-flow items" uid="'+ e.id  +'"');
				        _groupHtml.push(' uname="'+ e.name +'"');
				        _groupHtml.push(' type="'+ e.type +'"');
				        _groupHtml.push(' deptName="'+ e.deptName +'"');
				        _groupHtml.push(' title="'+ e.name +'"');
				        _groupHtml.push('>'+ e.name +'</li>');
					});
				});
				$p_content.append(_groupHtml.join(""));
				 
				//绑事件 单击
				$p_content.find('li').on('click',function(){
				    if(!$(this).hasClass('li-active')){
					    //样式变化
						$('.li-active').removeClass('li-active');
						$(this).addClass('li-active');
						//根据群组ID获得用户列表，并填充至用户列表
						appendPersonList(getPersonByGroupID($(this).attr("uid")), r);
				    }
				});
				//绑事件 双击-填充已选择项列表
				//如果不能选择职位 ， 则不绑事件
				if(r._groupSelect){
					$p_content.find('li').on('dblclick',function(){
				        $('.li-active').removeClass('li-active');
						$(this).addClass('li-active');
						//处理双击单位群组和个人群组的情况
						var id = $(this).attr("uid");
						if('unitGroup' == id || 'personalGroup' == id){
						    return false;
						}
						//根据职位ID获得用户列表，并填充至已选择项
						appendResultList([{id:$(this).attr("uid"), name:$(this).attr("uname"), deptName:$(this).attr("deptName"), type:$(this).attr("type"), pId:$(this).attr("pId")}], r);
				    });  
				 }
			});
		}
		//最近联系人按钮
		if(!r._recentContactsBtn){
			$('#recentContactsBtn').remove();
			$('#_recentContactsBtn').remove();
		}else{
			//加载最近联系人列表
			$("#recentContactsBtn").off('click').on('click',function(event){
				var _recentContactsDate = getRecentDate(r);
				//清空用户列表
				$('#person-content').html("");
				//显示隐藏 
				$('.dialogBox .person-data').css('display','none');
				$('#_recentContactsBtn').css('display','block');
				
				//样式变化
				if(!$(this).hasClass('a-active')){
					$(this).parent().find('.a-active').removeClass('a-active');
					$(this).addClass('a-active');
				}else{
					return false;
				}
				
				//填充组织机构数据
				var $p_content = $('#_recentContactsBtn').find('ul');
				$p_content.find('li').remove();
				var _recentHtml = [];
				$(_recentContactsDate).each(function(index, element) {
				    _recentHtml.push('<li uid="'+ element.id  +'"');
				    _recentHtml.push(' uname="'+ element.name +'"');
				    _recentHtml.push(' type="'+ element.type +'"');
				    _recentHtml.push(' deptName="'+ element.deptName +'"');
				    _recentHtml.push('><a class="over-flow" title="'+ element.name + (element.tel ? ("(") + element.tel + (")") : '') +'">'+ element.name + (element.tel ? ("(") + element.tel + (")") : '') +'</a></li>');
				});
				$p_content.append(_recentHtml.join(""));
				  
				//绑事件 单击
				$p_content.find('li').on('click',function(){
					if(!$(this).hasClass('li-active')){
						//样式变化
						$('#_recentContactsBtn .li-active').removeClass('li-active');
						$(this).addClass('li-active');
						//根据职位ID获得用户列表，并填充至用户列表
						appendPersonList(getRecentDateById( $(this).attr("uid"), r) , r) ;
					  }
				});
			    //绑事件 双击-填充已选择项列表
				//如果不能选择最近联系人 ， 则不绑事件 
				if(r._recentSelect){
				    $p_content.find('li').on('dblclick',function(){
					    $('#_recentContactsBtn .li-active').removeClass('li-active');
						$(this).addClass('li-active');
						//根据职位ID获得用户列表，并填充至已选择项
						appendResultList([{id:$(this).attr("uid") , name:$(this).attr("uname") , type:$(this).attr("type"), deptName:$(this).attr("deptName") , pId:$(this).attr("pId")}] , r);
					});  
				}
		    });
		}
		//常用联系人
		if(!r._commonContactsBtn){
			$('#commonContactsBtn').remove();
			$('#_commonContactsBtn').remove();
		}else{
			//加载常用联系人列表
			$("#commonContactsBtn").off('click').on('click',function(event){
			    var _commonContactsDate = getCommonDate(r);
				//清空用户列表
				$('#person-content').html("");
				//显示隐藏
				$('.dialogBox .person-data').css('display','none');
				$('#_commonContactsBtn').css('display','block');
				
				//填充组织机构数据
				var $p_content = $('#_commonContactsBtn ul');
				$p_content.find('li').remove();
				var _contactHtml = [];
				$(_commonContactsDate).each(function(index, element) {
				    _contactHtml.push('<li uid="'+ element.id  +'"');
				    _contactHtml.push(' uname="'+ element.name +'"');
				    _contactHtml.push(' type="'+ element.type +'"');
				    _contactHtml.push(' deptName="'+ element.deptName +'"');
				    _contactHtml.push('><em></em><a class="over-flow" title="'+ element.name + (element.tel ? ("(") + element.tel + (")") : '') +'">'+ element.name + (element.tel ? ("(") + element.tel + (")") : '') +'</a></li>');
				});
				$p_content.append(_contactHtml.join(""));
				  
				//绑事件 单击
				$p_content.find('li').on('click',function(){
				    if(!$(this).hasClass('li-active')){
				        //样式变化
						$('#_commonContactsBtn .li-active').removeClass('li-active');
						$(this).addClass('li-active');
						//根据职位ID获得用户列表，并填充至用户列表
						appendPersonList(getCommonDateById($(this).attr("uid"), r ) , r);
				    }
				});
				//单击删除按钮删除
				$p_content.find('li>em').on("click",function(e){
					var _this = $(this).parent();
					deleteCommonContacts(r, _this.attr("uid"), _this.attr("uname"));
				});
				//绑事件 双击-填充已选择项列表
				//如果不能选择最近联系人 ， 则不绑事件 
				if(r._commonSelect){
				     $p_content.find('li').on('dblclick',function(){
						  $('#_commonContactsBtn .li-active').removeClass('li-active');
						  $(this).addClass('li-active');
						  //根据职位ID获得用户列表，并填充至已选择项
						  appendResultList([{id:$(this).attr("uid") , name:$(this).attr("uname") , type:$(this).attr("type"), deptName:$(this).attr("deptName") , pId:$(this).attr("pId")}] , r);
					 });  
				 }
				 //样式变化
				 if(!$(this).hasClass('a-active')){
				     $(this).parent().find('.a-active').removeClass('a-active');
					 $(this).addClass('a-active');
				 }else{
					 return false;
				 }
			});	
		}
		//流程变量
		if(!r._processBtn){
			$('#processBtn').remove();
			$('#_processBtn').remove();
		}else{
			//加载流程变量列表
			$("#processBtn").off('click').on('click',function(event){
				var _processDate = getProcessDate(r);
				//清空用户列表
				$('#person-content').html("");
				//显示隐藏
				$('.dialogBox .person-data').css('display','none');
				$('#_processBtn').css('display','block');
				
				//样式变化
				if(!$(this).hasClass('a-active')){
					$(this).parent().find('.a-active').removeClass('a-active');
					$(this).addClass('a-active');
				}else{
					return false;
				}
				//填充组织机构数据
				var $p_content = $('#_processBtn').find('ul');
				$p_content.find('li').remove();
				var _processHtml = [];
				$(_processDate).each(function(index, element) {
					_processHtml.push('<li uid="'+ element.id  +'"');
					_processHtml.push(' uname="'+ element.name + '"');
					_processHtml.push(' type="'+ element.type + '"');
					_processHtml.push(' deptName="'+ element.deptName + '"');
					_processHtml.push('><a class="over-flow" title="'+ element.name +'">'+ element.name +'</a></li>');
				});
				$p_content.append(_processHtml.join(""));
				//绑事件 单击
				$p_content.find('li').on('click',function(){
					if(!$(this).hasClass('li-active')){
						//样式变化
						$('.li-active').removeClass('li-active');
						$(this).addClass('li-active');
			        }
				});
				  
				//绑事件 双击-填充已选择项列表
				//如果不能选择流程变量 ， 则不绑事件 
				if(r._processSelect){
				    $p_content.find('li').on('dblclick',function(){
					    $('.li-active').removeClass('li-active');
						$(this).addClass('li-active');
						//根据职位ID获得用户列表，并填充至已选择项
						appendResultList([{id:$(this).attr("uid") , name:$(this).attr("uname") , type:$(this).attr("type"), deptName:$(this).attr("deptName") , pId:$(this).attr("pId")}] , r);
					});  
				}
			});	
		}
		//显示默认按钮
//		if($(r._defaultBtn)){
//			$("#tree_dialog").find('.btn-content').find('a:first').trigger('click');
//		}else{
//			$(r._thisUnitBtn).trigger('click');
//		}
	}
	
	//填充用户列表
	function appendPersonList(p_data, r){
		var $r_c = $('#person-content'),
			_userResult = [];
		//清空用户列表数据
		$r_c.find('li').remove();
		//遍历数组，添加信息
		$(p_data).each(function(index, element) {
			_userResult.push('<li><a class="over-flow" uid="'+ element.id +'"');
			_userResult.push(' type="'+ (element.type ? element.type : '') +'"');
			_userResult.push(' uname="'+ element.name +'"');
			_userResult.push(' deptName="'+ element.deptName +'"');
			_userResult.push('>' + element.name + (element.deptName ? ("(" + element.deptName + ")") : '') + '</a></li>');
	    });
		$r_c.append(_userResult.join(""));
		//如果不能选人 , 则不绑定单击事件
		if(r._personSelect){
			//绑事件 单击选择样式变化
			$r_c.find('li').on('click',function(event){
				var _this = $(this),
				    thIndex = _this.index();
				if(event.shiftKey){
					var sftStartFlag = ($r_c.find('li.ui-selected').length > 0 && sftPoolIndex !=-1);
					$r_c.find('li').removeClass('ui-selected');
					if(sftStartFlag){
						if(thIndex > sftPoolIndex){
							$r_c.find('li:lt('+ (thIndex+1) +'):gt('+ sftPoolIndex +')').addClass('ui-selected');
							$r_c.find('li:eq('+ sftPoolIndex +')').addClass('ui-selected');
						}else{
							$r_c.find('li:lt('+ (sftPoolIndex+1) +'):gt('+ thIndex +')').addClass('ui-selected');
							_this.addClass('ui-selected');
						}
					}else{
						_this.addClass('ui-selected');
					}
				}else if(event.ctrlKey){
					_this.addClass('ui-selected');
				}else{
					_this.addClass('ui-selected');
					_this.siblings("li").removeClass('ui-selected');
				}
				sftPoolIndex = thIndex;
			}).on("mousedown", function(event){
				sftPoolMouse = 1;
				$(this).addClass('ui-selected');
			}).on("mouseover", function(event){
				if(sftPoolMouse == 1){
					sftPoolIndex = $(this).addClass('ui-selected').index();
				}
			});			
		}		
		//右键将选中的添加到结果
		$r_c.off('mousedown').on('mousedown',function(e){
			if(e.which == 3){
				var _resultList = [];
				$('#person-content li.ui-selected a').each(function(index, element) {
					_resultList.push({id: $(this).attr('uid'), name: $(this).attr('uname'), type: $(this).attr('type'), deptName: $(this).attr('deptName'), pId: $(this).attr('pId')});
                });
				appendResultList(_resultList, r);
				return false;
			}			
		});
		$r_c.off('contextmenu').on('contextmenu',function(e){  //屏蔽右键
			if(e.which == 3){
				return false;
			}
		});
		//如果不能选人 , 则不绑定双击事件
		if(r._personSelect){
			$('#person-content li').on('dblclick',function(){
				var eleObj = $(this).find("a");
				appendResultList([{id: eleObj.attr('uid'),name: eleObj.attr('uname'),type: eleObj.attr('type'),deptName: eleObj.attr('deptName'),pId: eleObj.attr('pId')}],r);
			});
		}
		//清空shift索引存储
		sftPoolIndex = -1;
	}
	
	//填充从帐套点击接节点时填充用户列表
	function appendAllUnitPersonList(p_data, r){
		var $r_c = $('#person-content'),
		    _userResult = [];
		//清空用户列表数据
		$r_c.find('li').remove();
		//遍历数组，添加信息
		$(p_data).each(function(index, element) {
			_userResult.push('<li><a class="over-flow" uid="'+ element.id +'"');
			_userResult.push(' type="'+ (element.type ? element.type : '') +'"');
			_userResult.push(' uname="'+ element.name +'"');
			_userResult.push(' orgName="'+ element.orgName +'"');
			_userResult.push(' deptName="'+ element.deptName +'"');
			_userResult.push('>' + element.name + (element.deptName ? ("(" + element.deptName + ")") : '') + '</a></li>');
	    });
		$r_c.append(_userResult.join(""));
		 
		//绑事件
		//如果不能选人 , 则不绑定单击事件
		if(r._personSelect){
			//绑事件 单击选择样式变化
			$r_c.find('li').on('click',function(event){
				var _this = $(this),
				    thIndex = _this.index();
				if(event.shiftKey){
					var sftStartFlag = $r_c.find('li.ui-selected').length > 0;
					$r_c.find('li').removeClass('ui-selected');
					if(sftStartFlag){
						if(thIndex > sftPoolIndex){
							$r_c.find('li:lt('+ (thIndex+1) +'):gt('+ sftPoolIndex +')').addClass('ui-selected');
							$r_c.find('li:eq('+ sftPoolIndex +')').addClass('ui-selected');
						}else{
							$r_c.find('li:lt('+ (sftPoolIndex+1) +'):gt('+ thIndex +')').addClass('ui-selected');
							_this.addClass('ui-selected');
						}
					}else{
						_this.addClass('ui-selected');
					}
				}else if(event.ctrlKey){
					_this.addClass('ui-selected');
				}else{
					_this.addClass('ui-selected');
					_this.siblings("li").removeClass('ui-selected');
				}
				sftPoolIndex = thIndex;
			}).on("mousedown", function(event){
				sftPoolMouse = 1;
				$(this).addClass('ui-selected');
			}).on("mouseover", function(event){
				if(sftPoolMouse == 1){
					sftPoolIndex = $(this).addClass('ui-selected').index();
				}
			});
		}
		//右键将选中的添加到结果
		$r_c.off('mousedown').on('mousedown',function(e){
			if(e.which == 3){
				var _resultList = [];
				$('#person-content li.ui-selected a').each(function(index, element) {
					_resultList.push({id: $(this).attr('uid'), name: $(this).attr('uname'), type: $(this).attr('type'), deptName: $(this).attr('deptName'), pId: $(this).attr('pId')});
                });
				appendResultList(_resultList, r);
				return false;
			}
		});
		$r_c.off('contextmenu').on('contextmenu',function(e){ //屏蔽右键
			if(e.which == 3){
				return false;
			}
		});
		//如果不能选人 , 则不绑定双击事件
		if(r._personSelect){
			$('#person-content li').on('dblclick',function(){
				var eleObj = $(this).find("a");
				appendAllUnitResultList([{id: eleObj.attr('uid'),name: eleObj.attr('uname'),type: eleObj.attr('type'),deptName: eleObj.attr('deptName'),pId: eleObj.attr('pId'),orgName: eleObj.attr('orgName')}],r);
			});
		}
		//清空shift索引存储
		sftPoolIndex = -1;
	}
	
	//添加结果
	function appendResultList(r_data, r){
		var $r_c = $('#result-list'),
		    _resNum = $('#result-list a');
		//如果单选配置项为true
		if(r._singleSelect){
			if(_resNum.length == 0){			
				addObjToResult($r_c, r_data[0]);
			}else if(!indexOfKeyValue(r_data,"id",_resNum.attr("uid"))){
				$r_c.find("li").remove();
				addObjToResult($r_c,r_data[0]);
			}else{}			
		}else{
		    //遍历数组，添加到已选择项列表
			$(r_data).each(function(index, element) {			
				if($r_c.find("[uid='" + element.id + "']").length == 0){
					addObjToResult($r_c,element);
				}	
		    });
		}
		
		//绑事件 单击选择样式变化
		$r_c.find('li').on('click',function(event){
			var _this = $(this),
			    thIndex = _this.index();
			if(event.shiftKey){
				var sftStartFlag = ($r_c.find('li.ui-selected').length > 0 && sftResultIndex !=-1);
				$r_c.find('li').removeClass('ui-selected');
				if(sftStartFlag){
					if(thIndex > sftResultIndex){
						$r_c.find('li:lt('+ (thIndex+1) +'):gt('+ sftResultIndex +')').addClass('ui-selected');
						$r_c.find('li:eq('+ sftResultIndex +')').addClass('ui-selected');
					}else{
						$r_c.find('li:lt('+ (sftResultIndex+1) +'):gt('+ thIndex +')').addClass('ui-selected');
						_this.addClass('ui-selected');
					}
				}else{
					_this.addClass('ui-selected');
				}
			}else if(event.ctrlKey){
				_this.addClass('ui-selected');
			}else{
				_this.addClass('ui-selected');
				_this.siblings("li").removeClass('ui-selected');
			}
			sftResultIndex = thIndex;
		}).on("mousedown", function(event){
			sftResultMouse = 1;
			$(this).addClass('ui-selected');
		}).on("mouseover", function(event){
			if(sftResultMouse == 1){
				sftResultIndex = $(this).addClass('ui-selected').index();
			}
		});
		
		//双击删除
		$r_c.find('li').on("dblclick",function(){
			$(this).remove();
		});
		
		//单击删除按钮删除
		$r_c.find('li>em').on("click",function(e){
			$(this).parent().remove();
		});
		
		//清空shift索引存储
		sftResultIndex = -1;
	}
	
	//填充指定对象到结果列表中
	function addObjToResult(resObj,element){
		if(resObj && element){
			var eleHtml = [],
			    deptName_content = element.deptName ? ("(") + element.deptName + (")") : '';
			eleHtml.push('<li><em></em>');
			eleHtml.push('<a class="over-flow" uid="'+ element.id +'"');
			eleHtml.push(' uname="'+ element.name +'"');
			eleHtml.push(' pId="'+ element.pId +'"');
			eleHtml.push(' type="'+ (element.type?element.type:'') +'"');
			eleHtml.push(' deptName="'+ element.deptName +'"');
			if(element.type === 'USER'){
				eleHtml.push(' title="' + element.name + deptName_content +'">' + element.name + deptName_content + '</a>');
			}else{
				eleHtml.push(' title="' + element.name  +'">' + element.name + '</a>');
			}
			eleHtml.push('</li>');
			resObj.append(eleHtml.join(""));
		}
	}
	
	//添加帐套树下的结果
	function appendAllUnitResultList (r_data, r){
		var $r_c = $('#result-list'),
		    _resNum = $('#result-list a');
		//如果单选配置项为true
		if(r._singleSelect){
			if(_resNum.length == 0){			
				addAllUnitObjToResult($r_c, r_data[0]);
			}else if(!indexOfKeyValue(r_data,"id",_resNum.attr("uid"))){
				$r_c.find("li").remove();
				addAllUnitObjToResult($r_c,r_data[0]);
			}else{}			
		}else{
		    //遍历数组，添加到已选择项列表
			$(r_data).each(function(index, element) {			
				if($r_c.find("[uid='" + element.id + "']").length == 0){
					addAllUnitObjToResult($r_c,element);
				}	
		    });
		}
		
		//绑事件 单击选择样式变化
		$r_c.find('li').on('click',function(event){
			var _this = $(this),
			    thIndex = _this.index();
			if(event.shiftKey){
				var sftStartFlag = $r_c.find('li.ui-selected').length > 0;
				$r_c.find('li').removeClass('ui-selected');
				if(sftStartFlag){
					if(thIndex > sftResultIndex){
						$r_c.find('li:lt('+ (thIndex+1) +'):gt('+ sftResultIndex +')').addClass('ui-selected');
						$r_c.find('li:eq('+ sftResultIndex +')').addClass('ui-selected');
					}else{
						$r_c.find('li:lt('+ (sftResultIndex+1) +'):gt('+ thIndex +')').addClass('ui-selected');
						_this.addClass('ui-selected');
					}
				}else{
					_this.addClass('ui-selected');
				}
			}else if(event.ctrlKey){
				_this.addClass('ui-selected');
			}else{
				_this.addClass('ui-selected');
				_this.siblings("li").removeClass('ui-selected');
			}
			sftResultIndex = thIndex;
		}).on("mousedown", function(event){
			sftResultMouse = 1;
			$(this).addClass('ui-selected');
		}).on("mouseover", function(event){
			if(sftResultMouse == 1){
				sftResultIndex = $(this).addClass('ui-selected').index();
			}
		});
		
		//双击删除
		$r_c.find('li').on("dblclick",function(){
			$(this).remove();
		});
		
		//单击删除按钮删除
		$r_c.find('li>em').on("click",function(e){
			$(this).parent().remove();
		});
		
		//清空shift索引存储
		sftResultIndex = -1;
	}
	
	//填充帐套树下的指定对象到结果列表中
	function addAllUnitObjToResult(resObj,element){
		if(resObj && element){
			var eleHtml = [],
			    deptName_content = element.deptName ? ("(")+element.deptName + (")") : '';
			eleHtml.push('<li><em></em>');
			eleHtml.push('<a class="over-flow" uid="'+ element.id +'"');
			eleHtml.push(' uname="'+ element.name +'"');
			eleHtml.push(' pId="'+ element.pId +'"');
			eleHtml.push(' type="'+ (element.type?element.type:'') +'"');
			eleHtml.push(' deptName="'+ element.deptName +'"');
			eleHtml.push(' orgName="'+ element.orgName +'"');
			if(element.type === 'USER'){
				if(element.orgName&&element.orgName!='undefined'){
					eleHtml.push(' title="' + "【"+element.orgName+"】"+element.name + deptName_content +'">' +"【"+element.orgName+"】"+ element.name + deptName_content + '</a>');
				}else{
					eleHtml.push(' title="' + element.name + deptName_content +'">' + element.name + deptName_content + '</a>');
				}
			}else if(element.type === 'DEPT'){
				if(element.orgName&&element.orgName!='undefined'){
					eleHtml.push(' title="'+"【"+element.orgName+"】" + element.name + '">'+"【"+element.orgName+"】" + element.name + '</a>');
				}else{
					eleHtml.push(' title="'+element.name + '">'+element.name + '</a>');
				}
			}else{
				eleHtml.push(' title="' + element.name  +'">' + element.name + '</a>');
			}
			eleHtml.push('</li>');
			resObj.append(eleHtml.join(""));
		}
	}
	
	//搜索json数据是否包含指定键值对
	function indexOfKeyValue(_data,_key,_val){
	    if(typeof _data == "object" && _key && _val){
	    	$(_data).each(function(index, element) {
	    		if(element[_key] == _val){
	    			return true;
	    		}
	    	});
	    }
	    return false;
	}
	
	//已选择项和用户列表 框 按钮事件
	function l_r_btnHandler(r){
		//移除已选结果列表
		$('#r-to-p').off('click').on('click',function(){
			//移除已选结果列表
			$('#result-list li.ui-selected').remove();
			//清空shift索引存储
			sftResultIndex = -1;
		});
		//选中的人过去
		//如果权限配置不能选人 ，不绑定事件
		if(r._personSelect){
			$('#p-to-r').off('click').on('click',function(){
				var _resultList = [];
				$('#person-content li.ui-selected a').each(function(index, element) {
					_resultList.push({name: $(element).attr('uname'), id: $(element).attr('uid'), type: $(element).attr('type'), deptName: $(element).attr('deptName')});
				});
				appendResultList(_resultList, r); 
			});
		}
	}
	
	//选择结果排序按钮
	function bindSortHander(){
		//绑定向上排序
		$('#sort-up').off('click').on('click',function(){
			var items = $('#result-list').find('.ui-selected'),
			    itemLen = items.length,
			    itemTotal = $('#result-list li').length;
			if(itemLen == 0 || itemTotal <= itemLen){
				return false;
			}			
			items.each(function(index, ele){
				var _this = $(this);
				if(_this.prev('li').length > 0){
					_this.prev('li').before(_this);
				}else{
					$('#result-list li:last').after(_this);
				}
				_this = null;
			});
			sftResultIndex = -1;
		});
		//绑定向下排序
		$('#sort-down').off('click').on('click',function(){
			var items = $('#result-list').find('.ui-selected'),
			    itemLen = items.length,
			    itemTotal = $('#result-list li').length,
			    _this = null;
			if(itemLen == 0 || itemTotal <= itemLen){
				return false;
			}
			for(var i = itemLen; i >0; i--){
				_this = items.eq(i-1);
				if(_this && _this.next('li').length > 0){
					_this.next('li').after(_this);
				}else{
					$('#result-list li:first').before(_this);
				}
			}
			sftResultIndex = -1;
		});	
	}
	
	/* 定义暴露format函数 */
    $.fn.treeView.format = function (txt) {
        
    };
	
    // 插件的defaults
    $.fn.treeView.defaults = {
			//按照分组保存结果的对象
			resultJson : null,
			//是否返回按照类型排序后的字符串
			groupByType : false ,
			//传入按照类型排序后的字符串数据
			groupByTypeData : null,
			//本单位按钮是否显示  default ： true
			_thisUnitBtn : true ,
			//职位按钮是否显示 
			_positionBtn : true ,
			//群组按钮是否显示
			_groupBtn : true ,
			//最近联系人
			_recentContactsBtn :true , 
			//常用联系人
			_commonContactsBtn : true , 
			//所有联系人
			_allUnitBtn : true ,
			//是否显示流程变量
			_processBtn : true ,
			//保存为常用联系人的后台地址
			saveAsCommon_ul : null ,
			//默认显示按钮
			_defaultBtn : "#thisUnitBtn" ,
			//是否能选人 1、用户列表数据不能选择到已选择项 
			_personSelect : true ,
			//是否能选部门 1、本单位部门
			_depSelect : true ,
			//是否能选职位 1、职位能否能选择
			_positionSelect : true ,
			//是否能选群组 1、群组能否能选择
			_groupSelect : true ,
			//是否能选单位  1、本单位所有单位 2、所有单位的单位
			_orgSelect : true ,
			//所有单位的部门是否能选择
			_orgDeptSelect : true ,
			//所有单位是否能选人
			_orgUserSelect : true ,
			//是否能选最近联系人
			_recentSelect : true ,
			//是否能选常用联系人
			_commonSelect : true ,		
			//是否能选流程变量
			_processSelect : true ,
			//是否能选
			_ebleChecked : true ,
			//默认图标位置
			iconSrc : 'res/skin/default/plugin/ztree/css/img/computer.png',
			//树组件缓存数据
			_treeDate : null,
			//全部单位树组件数据
			_allUnitDate : null ,
			//formHidden Value 
			hiddenValue : $('#input-hidden'),
			//fromshow value 
			showValue : '',
			//树组件缓存对象
			_unitTree : null ,
			_allUnitTree :null ,			
			resultHandler : null,
			//是否显示头部
			treeViewBar: true,
			//是否阻止拖动
			preventDrag: false,
			//限制选择结果为一个
			_singleSelect: false,
			//树组件默认配置
			_treeSetting : {
						check: {
							enable: true
						},
						data: {
							simpleData: {
								enable: true
							}
						},
						callback: {
							onClick: null,
							onCheck: null
						}	
					},
			//树组件默认配置
			_allUnitTreeSetting : {
						check: {
							enable: true
						},
						data: {
							simpleData: {
								enable: true
							}
						},
						callback: {
							onClick: null,
							onCheck: null
						},
						async: {
							enable: false,
							url: "",
							autoParam: ["id"]
						}	
					}
	    };
    /* 设置版本号 */
    $.fn.treeView.version = 1.0;
    //  闭包结束
}
})(jQuery);

