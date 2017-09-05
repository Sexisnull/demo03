/*
 * IOOP.js
 * version: 1.0
 * build: guolei 2014-03
 */
(function($){
	window.IOOP = window.IOOP || {};
	IOOP.sessionId = '';
	window.uploadList = [];
    
	/*
	 *@summary ioop配置对象
	 */
	IOOP.option = {
		noteMaxSize: 170,
		defUserLogo: "res/skin/default/css/img/default-user-logo.png",
		defImage: "res/skin/default/css/img/ioop-default.png",
		defWebImg: "res/skin/default/css/img/ioop-nma.png",
		mail:{},
		info:{},
		contacts:{},
		sessionId: "",
		unloadFun: null,
		viewUrl: "",
		downAllUrl: "",
		enViewSuffix: "doc,docx,xls,xlsx,ppt,pptx,dos,pdf,wps,jpeg,jpg,png,gif",
		userTheme: "1",
		govIndex: {
			indexModule: {
				rightSet: "",
				infoType: "",
				collType: "",
				defaultNames: {
					"1000": "我的邮件",
					"1100": "通知公告",
					"1300": "我的会议",
					"1501": "我的收文",
					"1502": "我的发文",
					"1600": "我的审批",
					"1700": "我的任务",
					"1800": "我的计划",
					"1900": "我的汇报",
					"2200": "我的签到",
					"2300": "我的投票",
					"2400": "我的调查",
					"2700": "我的日程"
				}
			},
			quickOperate: {
				rightCodes: "",
				defaultCodes: "10007000,11001000,13003000,23004000,24004000,2700,20001000,17006000,18004000,19004000"
			} 
		}
	};
	/*
	 *@summary 跳转前询问配置对象
	 */
	window.leaveConfirm = IOOP.leaveConfirm = function(){
		var	leaveEnable= false,
		    leaveOKFun= null,
		    leaveCancelFun= null,
            leaveMsg= "您的数据可能还未保存，确定要离开吗？";
		return {
		    open: function(option){
				var _option = {
					leaveOKFun: null,
					leaveCancelFun: null,
			        leaveMsg: "您的数据可能还未保存，确定要离开吗？"
			    };
				$.extend(_option, option);
				leaveEnable = true;
				leaveOKFun = _option.leaveOKFun;
				leaveCancelFun = _option.leaveCancelFun;
				leaveMsg = _option.leaveMsg;
				$(window).on("beforeunload", function(){
					return leaveMsg;
				});
			},
			close: function(){
				leaveEnable = false;
				leaveFun = null;
				leaveMsg= "您的数据可能还未保存，确定要离开吗？";
				$(window).off("beforeunload");
			},
			getLeaveFlag: function(){
				return leaveEnable;
			},
			getLeaveOKFun: function(){
				return leaveOKFun;
			},
			getLeaveCancelFun: function(){
				return leaveCancelFun;
			},
	        getLeaveMsg: function(){
	        	return leaveMsg;
			}
	    }
    }();
	/*
	 *@summary 首页数据列表菜单地址跳转映射对象
	 */
	IOOP.indexUrlMapping = {
		m_dynamic : "dynamic/work-dynamic.do",
		m_dynamic_topic : "dynamic/work-dynamic.do?bizType=8300",
		m_dynamic_mail : "dynamic/work-dynamic.do?bizType=1000",
		m_dynamic_info : "dynamic/work-dynamic.do?bizType=1100",
		m_dynamic_meet : "dynamic/work-dynamic.do?bizType=1300",
		m_dynamic_vote : "dynamic/work-dynamic.do?bizType=2300",
		m_mail : "mail/mail-info!homeMail.do?mailBox=1&isRead=0",
		m_mail_unread : "mail/mail-info!homeMail.do?mailBox=1&isRead=0",
		m_mail_inbox : "mail/mail-info!homeMail.do?mailBox=1",
		m_mail_outbox : "mail/mail-info!homeMail.do?mailBox=2",
		m_info : "info/e-bulletin!indexList.do",
		m_info_list : "info/e-bulletin!indexList.do?typeId=",
		m_meet : "meeting/meeting-info!getIndexList.do?indexType=0",
		m_meet_attend : "meeting/meeting-info!getIndexList.do?indexType=0",
		m_meet_apply : "meeting/meeting-info!getIndexList.do?indexType=1",
		m_meet_host : "meeting/meeting-info!getIndexList.do?indexType=2",
		m_todo : "dynamic/work-dynamic!todoList.do",
		m_todo_coll : "collaborate/collaborate-search!indexList.do",
		m_todo_docin : "doc/doc-in!indexList.do",
		m_todo_docout : "doc/doc-out!indexList.do",
		m_report_received : "report/received-report!list.do"
	};
	
	/*
	 *@summary 地址跳转映射对象
	 */
	IOOP.urlMapping = {
		m_index: {url: "main!mIndex.do",data:{}},
		m_index_gov: {url: "main!mIndexGov.do",data:{"imgSize": 4}},
		m_mail_inbox: {url: "mail/mail-info!ReceiveList.do",data:{}},
		m_mail_inbox_unread: {url: "mail/mail-info!ReceiveList.do",data:{isRead:"0"}},
		m_mail_outbox: {url: "mail/mail-info!SendList.do",data:{}},
		m_mail_draft: {url: "mail/mail-info!draftList.do",data:{}},
		m_mail_edit: {url: "mail/mail-info!input.do",data:{}},
		m_mail_dusbin: {url:"mail/mail-info!dusbinList.do",data:{}},
		m_mail_star : {url:"mail/mail-info!starList.do",data:{}},
		m_mail_attachment:{url:"mail/mail-info!getMailAttachment.do",data:{}},
		m_attend_setting:{url:"attendance/attendance!list.do",data:{}},
		m_attend_statistics:{url:"attendance/attendance!getStatisticsByUser.do",data:{}},
		m_attend_org_statistics:{url:"attendance/attendance!getStatisticsByOrg.do",data:{}},
		m_attend_info:{url:"attendance/attendance!getAttendInfoByUser.do",data:{}},
		m_attend_org_sign:{url:"attendance/attendance!getAttendInfo.do",data:{}},
		m_system: {url: "sys/sys-user.do",data:{}},
		m_logout: {url: "main!logout.do",data:{}},
		m_user_list: {url: "sys/sys-user.do",data:{}},
		m_role_list: {url: "sys/sys-role.do",data:{}},
		m_post_list: {url: "sys/sys-post.do",data:{}},
		m_dept_list: {url: "sys/sys-dept.do",data:{}},
		m_group_list: {url: "sys/sys-group.do",data:{}},
		m_signer_list: {url: "sys/signature.do",data:{}},
		m_wfvar_list : {url: "workflow/workflow-data.do"},
		m_web_list: {url: "website/org-fav-config.do",data:{}},
		m_org_set_center: {url: "sys/sys-org-config!input.do",data:{}},
		m_meeting: {url: "meeting/meeting-info!getAllMyM.do"},
		m_own_meeting: {url: "meeting/meeting-info!getAllMyM.do"},
		m_all_meeting: {url: "meeting/meeting-info!getAllMain.do"},
		m_admin_meeting: {url: "meeting/meeting-room!list.do"},
		m_meeting_edit: {url: "meeting/meeting-apply!input.do"},
		m_index_info_list: {url: "info/e-bulletin!indexMenuClick.do",data:{"option":"list","readState":"0"}},
		m_index_info_add: {url: "info/e-bulletin!indexMenuClick.do",data:{"option":"add"}},
		m_collaborate: {url: "collaborate/collaborate-search!collborateWaitList.do",data:{}},
		m_coll_my: {url: "collaborate/collaborate-search!collborateWaitList.do",data:{}},
		m_admin_coll: {url: "collaborate/collaborate-template.do",data:{}},
		m_coll_count: {url: "collaborate/collaborate-search!toStatistics.do",data:{}},
		m_coll_search: {url: "collaborate/collaborate-search!search.do",data:{}},
		m_coll_super: {url: "collaborate/collaborate-search!collborateSuperList.do",data:{}},
		m_coll_apply: {url: "collaborate/collaborate-document!apply.do",data:{}},
		m_system_admin: {url: "sys/support/support-org.do",data:{}},
		m_system_admin_org: {url: "sys/support/support-org.do", data:{}},
		m_system_admin_user: {url: "sys/support/support-org.do", data:{listType: "user"}},
		m_system_admin_advertorial: {url: "sys/support/support-advertorial.do",data:{}},
		m_doc: {url: "doc/doc-out.do",data:{}},
		m_docin_list: {url: "doc/doc-in.do",data:{}},
		m_docin_guide: {url: "doc/doc-in!guide.do",data:{}},
		m_docout_list: {url: "doc/doc-out.do",data:{}},
		m_docout_guide: {url: "doc/doc-out!guide.do",data:{}},
		m_doc_search: {url: "doc/doc-search.do",data:{}},
		m_doc_register: {url: "doc/doc-search!docRegister.do",data:{}},
		m_doc_supervise: {url: "doc/doc-supervise.do",data:{}},
		m_exdoc_in: {url: "docexchange/doc-ex-in.do",data:{}},
		m_exdoc_out: {url: "docexchange/doc-ex-out.do",data:{}},
		m_exdoc_read: {url: "docexchange/doc-ex-read.do",data:{}},		
		m_admin_doc: {url: "doc/doc-no.do",data:{}},
		m_mail_out: {url: "mail/user-config!getUserConfig.do",data:{}},
		m_mail_outset: {url: "mail/mail-config!list.do",data:{}},
		m_mail_outuser: {url: "mail/user-config!list.do",data:{}},
		m_person: {url: "sys/sys-user-config!input.do",data:{}},
		m_basic_input: {url: "sys/sys-user-config!input.do",data:{}},
		m_logo_input: {url: "sys/sys-user-config!inputLogo.do",data:{}},
		m_password_input: {url: "sys/sys-user-config!inputPassword.do",data:{}},
		m_personal_group: {url: "sys/personal-sys-group!list.do",data:{}},
		m_person_proxy: {url: "sys/sys-user-proxy.do",data:{}},
		m_person_remind: {url: "sys/sys-user-config!remind.do",data:{}},
		m_admin_product: {url: "sys/admin/product-class.do",data:{}},
		m_my_docu: {url: "document/document-file!list.do",data:{}},
		m_my_unit: {url: "document/document-file!list.do",data:{groupId:'0',module:'0'}},
		m_my_archives: {url: "archives/archives-info!viewMainList.do",data:{"parId":"docum_archive"}},
		m_vote_publish:{url:"vote/vote!list.do",data:{state:1}},
		m_vote_join:{url:"vote/vote!list.do",data:{state:2}},
		m_vote_view:{url:"vote/vote!list.do",data:{state:3}},
		m_vote_edit:{url:"vote/vote!input.do",data:{}},
		m_vote_type:{url:"vote/vote!admin.do",data:{}},
		m_calendar_own: {url: "personalcalendar/personal-calendar!toOwnList.do",data:{}},
		m_calendar_attention: {url: "personalcalendar/personal-calendar-ask!toAttentionList.do",data:{}},
		m_sms_out: {url: "sms/sms-send!outboxList.do",data:{}},
		m_sms_in: {url: "sms/sms-send!inboxList.do",data:{}},
		m_sms_send: {url: "sms/sms-send!toSendSms.do",data:{}},
		m_sms_draft: {url: "sms/sms-send!draftBoxList.do",data:{}},
		m_sms_search: {url: "sms/sms-search!searchList.do",data:{}},
		m_sms_count: {url: "sms/sms-search!statList.do",data:{}},
		m_calendar_share: {url: "personalcalendar/personal-calendar-share-config!list.do",data:{}},
		m_calendar_entrust: {url: "personalcalendar/personal-calendar-agent!list.do",data:{}},
		m_calendar_admin: {url: "personalcalendar/personal-calendar-agent!toAdminList.do",data:{}},
		m_add_task:{url: "task/task-info!input.do",data:{}},
		m_temp_task:{url: "task/task-info!list.do",data:{taskType:"00A"}},
		m_my_task:{url: "task/task-info!list.do",data:{taskType:"00B"}},
		m_assign_task:{url: "task/task-info!list.do",data:{taskType:"00C"}},
		m_check_task:{url: "task/task-info!list.do",data:{taskType:"00D",state:"2"}},
		m_share_task:{url: "task/task-info!list.do",data:{taskType:"00E"}},
		m_account_task:{url: "task/task-info!taskInput.do",data:{}},
		m_plan_receive:{url:"plan/plan!list.do", data:{type:"",pState:"1"}},
		m_plan_own:{url:"plan/plan!myList.do", data:{type:"",pState:"0"}},
		m_plan_count:{url:"plan/plan!count.do", data:{}},
		m_my_sponsor_survey:{url: "survey/my-sponsor-survey!list.do",data:{}},
		m_my_attended_survey:{url: "survey/my-attended-survey!list.do",data:{}},
		m_survey_result:{url: "survey/survey-result!list.do",data:{}},
		m_sponsor_new_survey:{url: "survey/sponsor-new-survey!input.do",data:{}},
		m_report_own:{url: "report/my-report!commitedList.do",data:{}},
		m_report_received:{url: "report/received-report!unreadList.do",data:{}},
		m_report_collect:{url: "report/count-report!input.do",data:{}},
		m_report_edit:{url: "report/write-new-report!input.do",data:{}},
		m_plan_edit:{url: "plan/plan!input.do",data:{}},
        m_info_all:{url: "info/e-bulletin!initMenu.do",data:{"option":"list","typeId":""}}
	};
	/*
	 *@summary 地址直接跳转映射对象
	 */
	IOOP.submitMapping = {
		"/app/mail/inbox": "m_mail_inbox",
        "/app/info/all": "m_info_all",
		"/app/doc/docin": "m_docin_list",
		"/app/coll/all": "m_collaborate"
	};
	/*
	 *@summary 首页模块直接跳转方法
	 */
	IOOP.moduleRedirect = function(queryObj){
	    var _mPath = queryObj.mPath,
            _subMapping = IOOP.submitMapping[_mPath],
            _urlMapping = null;
        if(_subMapping){
			_urlMapping = IOOP.urlMapping[_subMapping];
			if(_urlMapping){
				IOOP.gotoNewLink({
					url: _urlMapping.url,
					data: _urlMapping.data
				});	
			}else{
                IOOP.backToIndex();
            }
        }else{
            IOOP.backToIndex();
        }
	};
	/*
	 *@summary 首页模块详情直接跳转方法
	 */
	IOOP.detailRedirect = function(queryObj){
        var _mPath = queryObj.mPath;
	    switch(_mPath){
        case "/app/mail/detail":
            var _id = queryObj.id,
                _rid = queryObj.rid;
            if(_id && _rid){
                IOOP.gotoNewLink({
                    url: "mail/mail-info!getMail.do",
                    data: {"mailRelationId": _rid, "mailId": _id}
                });
            }else{
                IOOP.backToIndex();
            }
            break;
        case "/app/info/detail":
            var _id = queryObj.id,
                _tid = queryObj.tid,
                _rstate = queryObj.rstate;
            if(_id && _tid){
                _rstate = !_rstate ? "" : _rstate;
                IOOP.gotoNewLink({
                   url: "info/e-bulletin!indexView.do",
                   data: {"bulletinId": _id, "typeId": _tid, "readState": _rstate}
                });
            }else{
                IOOP.backToIndex();
            }
            break;
        case "/app/coll/detail":
            var _id = queryObj.id,
                _taskId = queryObj.taskId,
                _stateView = queryObj.stateView;
            if(_id && _taskId && _stateView){
                IOOP.gotoNewLink({
                    url: "collaborate/collaborate-document!gotoEditCollaborateDocument.do",
                    data: {"documentId": _id, "currentTaskId": _taskId, "state": _stateView, "returnUrl": "wait"}
                });
            }else{
                IOOP.backToIndex();
            }
            break;
        case "/app/docin/detail":
            var _id = queryObj.id,
                _taskId = queryObj.taskId;
            if(_id && _taskId){
                IOOP.gotoNewLink({
                    url: "doc/doc-in!input.do",
                    data: {"id": _id, "taskId": _taskId}
                });
            }else{
                IOOP.backToIndex();
            }
            break;
        default:
            IOOP.backToIndex();
        }
	};
	/*
	 *@summary 首页模块跳转方法
	 */
	IOOP.indexRedirect = function(){
		var queryObj = IOOP.parseUrl(),
		    _mPath = '';			
		if(!queryObj){
		    IOOP.backToIndex();
		}else{
			_mPath = queryObj.mPath;
			if(!_mPath){
			    IOOP.backToIndex();
			}else{
                //判断地址类型
                if(_mPath.indexOf("detail") != -1){
                    IOOP.detailRedirect(queryObj);
                }else{
                    IOOP.moduleRedirect(queryObj);
                }              
			}
		}
	};
	/*
	 *@summary 邮件编辑器实例对象
	 */	
	window.editor = IOOP.editor = {
		mailEditor: null,
		mrecordEditor: null,
		infoEditor: null,
		softEditor: null,
		reportEditor: null,
		planEditor: null
	};
	/*
	 *@summary 浏览器识别对象
	 */
	IOOP.browser = function(){
		var agent = navigator.userAgent.toLowerCase(),
			opera = window.opera,
			browser = {
				ie :  /(msie\s|trident.*rv:)([\w.]+)/.test(agent),
				opera : ( !!opera && opera.version ),
				webkit	: ( agent.indexOf( ' applewebkit/' ) > -1 ),
				mac	: ( agent.indexOf( 'macintosh' ) > -1 ),
				firefox : ( agent.indexOf( 'firefox' ) > -1 ),
				quirks : ( document.compatMode == 'BackCompat' )
			};
		browser.gecko =( navigator.product == 'Gecko' && !browser.webkit && !browser.opera && !browser.ie);
	
		var version = 0;
		// Internet Explorer 6.0+
		if ( browser.ie ){
			version =  (agent.match(/(msie\s|trident.*rv:)([\w.]+)/)[2] || 0) * 1;
	
			browser.ie11Compat = document.documentMode == 11;
			browser.ie9Compat = document.documentMode == 9;
			browser.ie8 = !!document.documentMode;
			browser.ie8Compat = document.documentMode == 8;
			browser.ie7Compat = ( ( version == 7 && !document.documentMode ) || document.documentMode == 7 );
			browser.ie6Compat = ( version < 7 || browser.quirks );
			browser.ie9above = version > 8;
			browser.ie9below = version < 9;
		}
	
		// Gecko.
		if ( browser.gecko ){
			var geckoRelease = agent.match( /rv:([\d\.]+)/ );
			if ( geckoRelease )
			{
				geckoRelease = geckoRelease[1].split( '.' );
				version = geckoRelease[0] * 10000 + ( geckoRelease[1] || 0 ) * 100 + ( geckoRelease[2] || 0 ) * 1;
			}
		}
		if (/chrome\/(\d+\.\d)/i.test(agent)) {
			browser.chrome = + RegExp['\x241'];
		}
		if(/(\d+\.\d)?(?:\.\d)?\s+safari\/?(\d+\.\d+)?/i.test(agent) && !/chrome/i.test(agent)){
			browser.safari = + (RegExp['\x241'] || RegExp['\x242']);
		}
	
		// Opera 9.50+
		if ( browser.opera ){
			version = parseFloat( opera.version() );
		}
		// WebKit 522+ (Safari 3+)
		if ( browser.webkit ){
			version = parseFloat( agent.match( / applewebkit\/(\d+)/ )[1] );
		}
		browser.version = version;
		browser.isCompatible =
			!browser.mobile && (
			( browser.ie && version >= 6 ) ||
			( browser.gecko && version >= 10801 ) ||
			( browser.opera && version >= 9.5 ) ||
			( browser.air && version >= 1 ) ||
			( browser.webkit && version >= 522 ) ||
			false );
		return browser;
	}();
	/*
	 *@summary 获取url参数
	 *@return 返回参数数组
	 */
	IOOP.parseUrl = function(){
		var _url = location.href,
		    _index = _url.indexOf('?'),
			queryStr = "",
			params = [],
			param = [],
			resultObj = new Object();
		if(_index == -1){
			return false;
		}
		queryStr = _url.substr(_index + 1);
		if(queryStr.length == 0){
			return false;
		}
		params = queryStr.split('&');
		if(params.length == 0){
			return false;
		}
		for(pam in params){
			param = params[pam].split('=');
			if(param.length == 0){
				continue;
			}else{
			    resultObj[param[0]] = param[1];
			}
		}
		return resultObj;
	};
	/*
	 *@summary 获取url根路径
	 *@return 返回根路径
	 */
	IOOP.getRootPath = function(){
		var curWwwPath = window.document.location.href,
		    pathName = window.document.location.pathname,
		    pos = curWwwPath.indexOf(pathName),
		    localhostPath = curWwwPath.substring(0, pos),
		    projectName = pathName.substring(0, pathName.substr(1).indexOf("/") + 1);
		return (localhostPath + projectName + "/");
	};	
	/*
	 *@summary 获取12位随机数
	 *@return num 返回生成的随机数
	 */
	IOOP.getRandom = function(){
		return parseInt(Math.random() * 1000000000000);
	};
	/*
	 *@summary 操作textarea光标位置方法
	 */
    window.operateTextarea = IOOP.operateTextarea = {
        /*
         * @summary 获取光标位置
         * @param ele 元素dom对象
         * @return 光标位置
         */
        getCursorPosition: function(ele){
            if(!ele){
                return false;
            }
            if(document.selection){
                ele.focus();
                var ds = document.selection,
                    range = ds.createRange(),
                    stored_range = range.duplicate();
                stored_range.moveToElementText(ele);
                stored_range.setEndPoint("EndToEnd", range);
                ele.selectionStart = stored_range.text.length - range.text.length;
                ele.selectionEnd = ele.selectionStart + range.text.length;
                return ele.selectionStart;
            }else{
                return ele.selectionStart;
            }
        },
        /*
         * @summary 设置光标位置
         * @param ele 元素dom对象
         * @param pos 要设置的光标位置
         */
        setCursorPosition:function(ele, pos){
            if(document.selection){
                var range = ele.createTextRange();
                range.moveEnd('character', -ele.value.length);         
                range.moveEnd('character', pos);
                range.moveStart('character', pos);
                range.select();
            }else{
                ele.setSelectionRange(pos, pos);
                ele.focus();
            }
        },
        /*
         * @summary 添加文本内容
         * @param eleId 元素id
         * @param str 文本内容
         */
        insertText: function(eleId, str){
            if(!eleId || !str){
                return false;
            }
            var ele = document.getElementById(eleId),
                txtcont = ele.value;            
            if(document.selection){
                ele.focus()
                document.selection.createRange().text = str;  
            }else{
                var cp = ele.selectionStart,
                    ubbLength = ele.value.length,
                    _scroll = ele.scrollTop;
                ele.value = ele.value.slice(0, ele.selectionStart) + str + ele.value.slice(ele.selectionStart, ubbLength);
                operateTextarea.setCursorPosition(ele, cp + str.length);
                var isFirefox = IOOP.browser.firefox && setTimeout(function(){
                    if(ele.scrollTop != _scroll){
                        ele.scrollTop = _scroll;
                    }
                },0);
            }
        }        
    };
	/*
	 *@summary 写邮件页面相关操作方法
     *@param _url,要加载的请求地址
     *@param _data,请求时附带的数据
     *@param _titleId,邮件标题id
     *@param _onloadHandler,加载完成回调方法
     *@param _oncloseHandler,加载完成回调方法
	 */
	window.mailWinOpertate = IOOP.mailWinOpertate = function(){
        var _closeFlag = true,
            _maxWidth = 1200,
            _minWidth = 500,
            _minHeight = 550,
            _uploadId = "",
            _url = "",
            _data = {},
            _titleId = "",
            _onloadHandler = null,
            _oncloseHandler = null,
            _onminHandler = null,
            _onmaxHandler = null;
        return {
            open: function(option){
                var ww = $(window).width(),
                    winw = parseInt(ww*0.8),
                    hh = $(window).height(),
                    winh = parseInt(hh*0.9),
                    wleft = 0,
                    wtop = 0;
                _url = option.url;
                if(typeof option.data == "object"){
                    data = option.data;
                }
                _uploadId = option.uploadId;
                _titleId = option.titleId;
                _onloadHandler = option.onloadHandler;
                _oncloseHandler = option.oncloseHandler;
                _onminHandler = option.onminHandler;
                _onmaxHandler = option.onmaxHandler;
                if(!_url){
                    return false;
                }else{
                    if(_url.indexOf("&") != -1){
                        _url += "&";
                    }else{
                        if(_url.indexOf("?") != -1){
                            if(_url.indexOf("=") != -1){
                                _url += "&";
                            }
                        }else{
                            _url += "?";
                        }
                    }		
                    _url += "randID=" + Math.random();
                }
                winw = winw < _minWidth ? _minWidth : winw;
                winw = winw > _maxWidth ? _maxWidth : winw;
                winh = winh < _minHeight ? _minHeight : winh;
                wleft = parseInt((ww - winw)/2);
                wleft = wleft < 0 ? 0 : wleft;
                wtop = parseInt((hh - winh)/2);
                wtop = wtop < 0 ? 0 : wtop;
                if($("#add_mail_win").length == 0){
                    $("body").append('<div id="add_mail_win" class="ioop-dialog" style="z-index:9999;"><h6><div id="add_mail_title" class="add-mail-title over-flow">新邮件</div><b id="add_win_min" class="dialog-close amail-dialog-min" title="最小化窗口"></b><b id="add_win_close" class="dialog-close" title="关闭窗口"></b></h6><div class="dialog-main"><div id="add_mail_cont" class="add-mail-cont"></div></div></div><div id="amail_win_min" class="amail-win-min tab-hidden"><div id="amail_title_min" class="amail-title-min">新邮件</div><b id="mwin_min_max" class="mwin-min-btn mwin-min-max" title="最大化窗口"></b><b id="mwin_min_close" class="mwin-min-btn mwin-min-close" title="关闭窗口"></b></div>');
                    //初始化操作
                    $("#add_win_min").on("click", function(){
                        mailWinOpertate.minSize();
                    });
                    $("#mwin_min_max").on("click", function(){
                        mailWinOpertate.maxSize();
                    });
                    $("#add_win_close,#mwin_min_close").on("click", function(){
                        IOOP.showalert({
                            showTitle: "消息提示",
                            showMsg: "您的邮件还未保存，确定关闭吗？",
                            alertType: "confirm",
                            btnOKText: "确定",
                            btnCancelText: "取消",
                            btnOKHandler: function(){
                                if(typeof _oncloseHandler == "function"){
                                    _oncloseHandler();                            
                                }
                                mailWinOpertate.close();
                            }
                        });
                    });
                }
                $("html,body").css("overflow", "hidden");                
                $("#add_mail_title").css("width", (winw - 90));
                $("#add_mail_cont").css({"width": (winw - 22), "height": (winh - 55)});
                $("#amail_win_min").hide();
                $("#add_mail_win").css({"left": wleft, "top": wtop, "width": winw, "height": winh}).show();
                if(_closeFlag){
                    $("#add_mail_cont")
                        .btnLoading()
                        .load(_url, _data, function(){
                            if(typeof _onloadHandler == "function"){
                                _onloadHandler();
                            }
                            $("#add_mail_cont").btnLoading(true);
                            _closeFlag = false;
                        });
                }
            },
            close: function(){
                $("html,body").css("overflow", "auto");
                $("#add_mail_win,#amail_win_min").hide();
                _closeFlag = true;
                if(_uploadId){
                    try{
                        $("#"+_id).hide().uploadify('destroy');
                    }catch(e){}
                }
            },
            minSize: function(){
                $("html,body").css("overflow", "auto");
                if(_titleId && $(_titleId).length > 0){
                    var _mtitle = $(_titleId).val();
                    if(IOOP.trim(_mtitle)){
                        $("#amail_title_min").text(_mtitle);
                    }
                }
                $("#add_mail_win").hide();
                $("#amail_win_min").show();  
                if(typeof _onminHandler == "function"){
                	_onminHandler();
                }
            },
            maxSize: function(){
                $("html,body").css("overflow", "hidden");
                if(_titleId && $(_titleId).length > 0){
                    var _mtitle = $(_titleId).val();
                    if(IOOP.trim(_mtitle)){                        
                        $("#add_mail_title").text(_mtitle);
                    }
                }
                $("#amail_win_min").hide();
                $("#add_mail_win").show();
                if(typeof _onmaxHandler == "function"){
                	_onmaxHandler();
                }
            }
        };
    }();
	/*
	 *@summary 转化用户名等显示样式
	 *@param str,要转化的用逗号分隔的字符串
	 *@param cFlag,转化标志，true：转为纯文本；false：转为html代码，默认false
	 *@return num 返回转化后的字符串
	 */
	IOOP.changeListState = function(str,cFlag){
		var _list = [], _len = 0, _html = [];
		if(!str){
			return false;
		}
		_list = str.split(",");
		_len = _list.length;
		if(_len > 0 && !cFlag){
			for(var i=0; i<_len; i++){
				_html.push('<a class="users-item"><p class="over-flow" title="'+ _list[0] +'">'+ _list[0] +'</p><b class="user-remove" title="移除"></b></a>');
			}
			return _html.join("");
		}else{
			return $(str).text();
		}
	};
	/*
	 *@summary 转化用户、角色、职位等数据为显示用的html
	 *@param usersData,要转化的数据对象
	 *@return str 返回转化后的html代码
	 */
	IOOP.parseUsersToHtml = function(usersData){
		if(!usersData || usersData.length == 0){
			return false;
		}
		var _resultHtml = [];
		$(usersData).each(function(index, ele) {
			if(ele.orgName){
				_resultHtml.push('<a class="users-item" uid="'+ ele.id +'" uname="'+ ele.name +'" type="'+ ele.type +'" deptName="'+ (!ele.deptName?'':ele.deptName) +'" orgName="'+(!ele.orgName?'':ele.orgName)+'">');
				_resultHtml.push('<p class="over-flow" title="'+"【"+ele.orgName+"】"+ ele.name +'">'+ "【"+ele.orgName+"】"+ele.name +'</p>');
			}else{
				_resultHtml.push('<a class="users-item" uid="'+ ele.id +'" uname="'+ ele.name +'" type="'+ ele.type +'" deptName="'+ (!ele.deptName?'':ele.deptName) +'">');
				_resultHtml.push('<p class="over-flow" title="'+ ele.name +'">'+ ele.name +'</p>');
			}
			_resultHtml.push('<b class="user-remove" title="移除"></b></a>');
		});
		return _resultHtml.join("");
	};
	/*
	 *@summary js本地图片预览
	 *@param fileObj 文件上传表单元素
	 *@param imgPreviewId 预览图片id
	 *@return divPreviewId 预览图片容器id
	 */
	IOOP.previewImage = function(fileObj,imgPreviewId,divPreviewId,filePathId){
		if(!fileObj || !imgPreviewId || !divPreviewId || !filePathId){
			return false;
		}
		var allowExtention=".jpg,.gif,.png",  
		    extention=fileObj.value.substring(fileObj.value.lastIndexOf(".")+1).toLowerCase(),             
		    browserVersion= window.navigator.userAgent.toUpperCase(),
			previewObj = $("#"+divPreviewId),
			imgPreviewObj = $("#"+imgPreviewId),
			imgPathObj = $("#"+filePathId);
		if(allowExtention.indexOf(extention)>-1){   
			if(fileObj.files){//HTML5实现预览，兼容chrome、火狐7+等  
				if(window.FileReader){  
					var reader = new FileReader();   
					reader.onload = function(e){
						imgPreviewObj.attr("src",e.target.result);
						imgPathObj.val(e.target.result);  
					}    
					reader.readAsDataURL(fileObj.files[0]);  
				}else if(browserVersion.indexOf("SAFARI")>-1){  
					//alert("不支持Safari6.0以下浏览器的图片预览!");
					return false;  
				}  
			}else if (browserVersion.indexOf("MSIE")>-1){  
				if(browserVersion.indexOf("MSIE 6")>-1){//ie6  
				    imgPreviewObj.attr("src",fileObj.value);
					imgPathObj.val(fileObj.value);
				}else{//ie[7-9]  
					fileObj.select();  
					if(browserVersion.indexOf("MSIE 9")>-1)  
						fileObj.blur();
					var newPreview = $("#"+divPreviewId+"New");  
					if(newPreview.length == 0){  
						newPreview = $('<div id="'+ divPreviewId +'New"></div>')
						    .width(previewObj.width())
							.height(previewObj.height()); 
					}  
					newPreview.css("filter","progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod='scale',src='" + document.selection.createRange().text + "')");                              
					previewObj.hide().parent().append(newPreview);                     
				}  
			}else if(browserVersion.indexOf("FIREFOX")>-1){//firefox  
				var firefoxVersion= parseFloat(browserVersion.toLowerCase().match(/firefox\/([\d.]+)/)[1]);  
				if(firefoxVersion<7){//firefox7以下版本  
					imgPreviewObj.attr("src",fileObj.files[0].getAsDataURL()); 
					imgPathObj.attr("src",fileObj.files[0].getAsDataURL());  
				}else{//firefox7.0+
				    imgPreviewObj.attr("src",window.URL.createObjectURL(fileObj.files[0])); 
					imgPathObj.val(window.URL.createObjectURL(fileObj.files[0]));                        
				}  
			}else{  
				imgPreviewObj.attr("src",fileObj.value);
				imgPathObj.val(fileObj.value); 
			}           
		}else{  
			IOOP.showalert({
				showTitle: "消息提示",
				showMsg: "仅支持"+allowExtention+"为后缀名的文件!",
				alertType: "alert",
				btnOKText: "确定"
			});
			imgPathObj.val("");
			fileObj.value="";//清空选中文件  
			/*if(browserVersion.indexOf("MSIE")>-1){                          
				fileObj.select();
				document.selection.clear();  
			}*/                  
			fileObj.outerHTML=fileObj.outerHTML; 
			return false; 
		}
	},
	/*
	 *@summary 获取组织机构框生成html代码数据
	 *@param listId,列表容器id
	 *@return json,返回分组数据 {userId:[111,222]}
	 */
	IOOP.getOrgTreeValue = function(listId){
		var aList = null,
			resultData = {
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
		if(!listId){
			return false;
		}
		aList = $("#"+listId).find("a");
		if(aList.length == 0){
			return resultData;
		}
		aList.each(function(index, element) {
            var _this = $(this);
			if(_this.attr("type")){
				resultData[_this.attr("type")+"_ID"].push(_this.attr("uid"));
				resultData[_this.attr("type")+"_NAME"].push(_this.attr("uname"));
				resultData["ALL_ID"].push(_this.attr("uid"));
				if(_this.attr("orgName")&&_this.attr("orgName")!=null){//如果是从帐套选择的节点，节点属性orgName不为空，要拼接之后传到后台
					resultData["ALL_NAME"].push("【"+_this.attr("orgName")+"】"+_this.attr("uname"));
				}else{
					resultData["ALL_NAME"].push(_this.attr("uname"));
				}
			}
        });
		return resultData;
	};
	/*
	 *@summary 判断是否显示暂无效果
	 *@param contSelor,容器选择器
	 *@param itemSelor,数据项选择器
	 *@param showRemark,显示提示消息，默认"暂无数据"
	 *@param showSize,显示大小，可取值 normal、small,默认normal
	 */
	IOOP.isNoResult = function(option){
		var _option = {
			    contSelor: "",
				itemSelor: "",
				showRemark: "暂无数据",
				showSize: "normal"
			},
			oldResult = null,
			resultHtml = '';
		$.extend(_option, option);		
		if(!_option.contSelor || !_option.itemSelor){
			return false;
		}
		resultHtml = '<div class="no-result"><div class="no-result-'+ _option.showSize +'"><div class="no-result-remark">'+ _option.showRemark +'</div></div></div>';
		oldResult = $(_option.contSelor).parent().find(".no-result");
		if($(_option.contSelor).find(_option.itemSelor).length == 0){
			if(oldResult.length == 0){
				$(_option.contSelor).after(resultHtml);
			}
		}else{
			oldResult.remove();
		}
	};
	/*
	 *@summary 分享按钮
	 */
	window.shareBtn = IOOP.shareBtn = {
		/*
		 *@summary 分享到易信
		 *@param type 分享类型，默认text,如果是text,则text必填;如果是image,则title和url为必填
		 *@param title,分享标题,默认综合办公
		 *@param text 分享内容,默认综合办公
		 *@param userdesc 自定义分享内容
		 *@param pic 分享图片缩略图地址
		 *@param url 分享图片地址		 
		 */
		shareToYixin: function(option){
			var _option = {
				type: "text",
				title: "中国电信综合办公",
				text:"中国电信综合办公",
				userdesc: "中国电信综合办公",
				pic: "http://www.ctnma.cn/res/skin/Default/img/topimg.png",
				url: "http://www.ctnma.cn/res/skin/Default/img/topimg.png"
			},_s = [],_url="http://open.yixin.im/share?";
			$.extend(_option,option);
			if(option.url && !option.pic){
				_option.pic = option.url;
			}
			_option.appkey = "yxdfb4a93ad7104facb761e3905c9e816c";
			for(var i in _option){
				if(_option.hasOwnProperty(i)){
					_option[i]!=null && _s.push(i.toString() + '=' +encodeURIComponent(_option[i].toString() || '')); 
				}
			}
			window.open(_url + _s.join('&'));
		},
		/*
		 *@summary 分享到新浪微博
		 *@param sText 分享文本内容，默认 中国电信综合办公
		 *@param sUrl 分享来源url,默认http://www.ctnma.cn
		 *@param sPic 分享图片,默认综合办公logo
		 */
		shareToSinaWb: function(option){
			var _option = {
				sText: "中国电信综合办公",
				sUrl: "http://www.ctnma.cn",
				sPic: ""
			};
			$.extend(_option, option);
			window.open('http://service.weibo.com/share/share.php?url='+encodeURIComponent(_option.sUrl) + '&title='+encodeURIComponent(_option.sText) + '&appkey=1343713053&pic='+encodeURIComponent(_option.sPic));
		},
		/*
		 *@summary 分享到腾讯微博
		 *@param sText 分享文本内容，默认 中国电信综合办公
		 *@param sUrl 分享来源url,默认http://www.ctnma.cn
		 *@param sPic 分享图片,默认综合办公logo
		 */
		shareToQQWb: function(option){
			var _option = {
				sText: "中国电信综合办公",
				sUrl: "http://www.ctnma.cn",
				sPic: ""
			};
			$.extend(_option, option);
			window.open('http://share.v.t.qq.com/index.php?c=share&a=index&url='+encodeURIComponent(_option.sUrl) + '&title='+encodeURIComponent(_option.sText) + '&appkey=801cf76d3cfc44ada52ec13114e84a96&pic='+encodeURIComponent(_option.sPic));
		}
	};
	/*
	 * @summary 判断字符串是否有效，null,undefined,'  ',''时返回true,其它返回true;
	 * @param str 要判断的字符串
	 */
	IOOP.isBlank = function(str){
		if(!str) return true;
		if(str.replace(/\s*/,'') == '') return true;
		return false;
	};
	/*
	 * @summary 去掉字符串右边的空格
	 * @param str 字符串
	 */
	IOOP.rtrim = function(str){
		if(!str) return str;
		return str.replace(/\s*$/,'');
	};
	/*
	 * @summary 去掉字符串左边的空格
	 * @param str 字符串
	 */
	IOOP.ltrim = function(str){
		if(!str) return str;
		return str.replace(/^\s*/,'');
	};
	/*
	 * @summary 去掉字符串两边的空格
	 * @param str 字符串
	 */
	IOOP.trim = function(str){
		if(!str){
			return str;
		}
		return this.ltrim(this.rtrim(str));
	};
	/*
	 * @summary 替换字符串中的特殊字符,主要是处理处理英文的尖叫括号
	 * @param str 字符串
	 */
	IOOP.replaceSpecChar = function(str){
		 var temp=str;
		 temp = temp.replace(/\</g, "＜");
	     temp = temp.replace(/\>/g, "＞");
	     return temp;
	};
	/*
	 * @summary 字符串校验是否包含英文尖叫括号
	 * @param str 字符串
	 */
	IOOP.containSpecChar = function(str){
		var reg =/[<>]+/;
		return reg.test(str);
	};
	/*
	 *@summary 回到首页
	 */
	IOOP.backToIndex = function(){
		var mapObj = null;
		switch(IOOP.option.userTheme){
		    case "0":
		    	mapObj = IOOP.urlMapping["m_index_gov"];
		    	break;
		    case "1":
		    default:
		    	mapObj = IOOP.urlMapping["m_index"];
		}
		if(mapObj){
			appOperate.clearAppSelState();
			IOOP.gotoNewLink({
				selector: "#main-content",
				url: mapObj.url,
				data: mapObj.data
			});	
		}
	};
	/*
	 *@summary ie9下不支持placeholder的问题
	 */
	IOOP.hackPlaceholder = function(){
		if(!("placeholder" in document.createElement('input'))){
			$(".placeholder").each(function(index, element) {
                var _this = $(this),
				    _holdtext = _this.attr("placeholder"),
					_phObj = _this.parent().find(".search-placehoder");
				if(!_holdtext){
					return true;
				}
				if(_phObj.length == 0){
					_phObj = $('<div class="search-placehoder tab-hidden">'+ _holdtext +'</div>');
					_this.after(_phObj);
				}
				if(!_this.val()){
					_phObj.show();
				}else{
					_phObj.hide();
				}
				_phObj.off("click").on("click", function(){
					_phObj.hide();
					_this.focus();
				});
				_this.off("blur").on("blur",function(){
					if(!_this.val()){
						_phObj.show();
					}else{
						_phObj.hide();
					}
				});
				this.onpropertychange = function(){
					if(!_this.val()){
						_phObj.show();
					}else{
						_phObj.hide();
					}
				};
            });
		}
	};
	/*
	 *@summary 添加表情
	 */
	IOOP.addEmoticons = function(){
		if($("#ioop_emoticons").length == 0){
			var _emotCont = null,
				_emotNum = 108,
                _emotHtml = [];
			$("body").append('<div id="ioop_emoticons" class="ioop-emoticons tab-hidden"><div class="emot-cont clear"><ul></ul></div><div class="gray-arrow"></div></div>');
			_emotCont = $("#ioop_emoticons ul");
			for(var i=0; i<=_emotNum; i++){
                _emotHtml.push('<li index="'+ i +'" class="emot-'+ i +'" style="background-position:-'+ 24*i +'px 0px;"></li>');
			}
            _emotCont.append(_emotHtml.join("")).find("li")
                .on("mouseenter", function(){
					var _index = $(this).attr("index");						
					$(this).css({"background-image": "url(res/skin/default/css/img/emoticons/"+ _index +".gif)", "background-position": "0 0"});
				}).on("mouseleave", function(){		
					var _index = $(this).attr("index");				
					$(this).css({"background-image": "url(res/skin/default/css/img/emoticons/static.gif)", "background-position": "-"+ 24*parseInt(_index) +"px 0px"});
				}).on("click", function(){
					var _descId = $("#ioop_emoticons").attr("descId"),
					   _index = $(this).attr("index"); 
					if(_descId){
						operateTextarea.insertText(_descId, "[face_"+ _index +"]");
					}					
				});
		}
	};	
	/*
	 *@summary 转化表情标识为图片地址
	 *@param str,要转化的字符串
	 */
	IOOP.transEmoticons = function(str){
		if(!str){
			return "";
		}
		return str.replace(/\[face_(\d{1,})\]/g, '<img src="res/skin/default/css/img/emoticons/$1.gif" />');
	};
	/*
	 *@summary 上传图片
	 *@param selector,要预览元素的jquery选择器
	 */
	IOOP.imagesView = function(selector){
		var _ww = $(window).width(),
		    _wh = $(window).height(),
			_currentImg = null,
			_url = "",
			_imgsNum = 0,
			_imgsList = [],
			_this = null;
		if(!selector){
			return false;
		}
		_this = $(selector);
		_currentImg = _this.find("img");
		if(_currentImg.length == 0){
			return false;
		}
		if($("#img_up_preview").length == 0){
			$('<div id="img_up_preview" class="img-up-preview tab-hidden"><div class="img-preview-mask"></div><a id="img_preview_close" class="img-preview-close" title="退出预览"></a><div id="img_preview_main" class="img-preview-main">'
                + '<div id="img_preview_cont" class="img-preview-cont"><span id="img_preview_pre" class="img-preview-pre" title="上一张"><b></b></span><span id="img_preview_next" class="img-preview-next" title="下一张"><b></b></span><img id="img_preview_show" class="img-preview-show" /></div>'
				+ '<div class="img-small-list"><div class="img-pre-one tab-hidden"><a id="arrow_left_small" class="arrow-small arrow-left-small" title="上一页"><em>&lt;</em></a></div><div id="img_view_cont" class="img-view-cont"><ul id="img_view_list" class="clear"></ul></div><div class="img-next-one tab-hidden"><a id="arrow_right_small" class="arrow-small arrow-right-small" title="下一页"><em>&gt;</em></a></div></div></div></div>')
				.appendTo("body");		    
			$("#img_up_preview").on("click",function(){				
				$("#img_up_preview").hide();
				$("html,body").css("overflow", "auto");
			});	
			$("#img_preview_cont").css("height", _wh - 100).on("click",function(){
			    return false;
			});
		}
		$("html,body").css("overflow", "hidden");
		$("#img_view_list li").remove();
		_url = _currentImg.attr("src");
		if(!_url){
			return false;
		}
		$("#img_preview_show").on("load",function(){
			var _marLeft = parseInt($(this).width()/2),
				_marTop = parseInt($(this).height()/2);
			$(this).css({"margin-left": -_marLeft, "margin-top": -_marTop});
		});
		setTimeout(function(){
			$("#img_preview_show").attr("src", _url);
		},100);
		//给图片绑定点击处理事件		
		_this.parent().find("img").each(function(index, element) {
			_imgsList.push('<li'+ (index == _this.index() ?' class="current"':'') +'><a><img src="'+ element.src +'" /></a></li>');
			_imgsNum++;
		});
		$("#img_view_list").append(	_imgsList.join("")).find("li").on("click", function(){
			$("#img_preview_show").attr("src", $(this).find("img").attr("src"));
			$(this).addClass("current").siblings().removeClass("current");
			return false;
		});
		$("#img_view_cont").css("width",_imgsNum*60+10);
		//左右移动
		$("#arrow_left_small").off("click").on("click",function(){
			var _listw = $("#img_view_list").width(),
			    _listLeft = parseInt($("#img_view_list").css("left")),
			    _contw = $("#img_view_cont").width(),
				_offsetw = _listw - _contw;
			if(_offsetw > 0 && Math.abs(_listLeft) < _offsetw){
				$("#img_view_list").animate({left: _listLeft-60},500);
			}else{
				return false;
			}
		});
		$("#arrow_right_small").off("click").on("click",function(){
			var _listLeft = parseInt($("#img_view_list").css("left"));
			if(_listLeft < 0){
				$("#img_view_list").animate({left: _listLeft+60},500);
			}else{
				return false;
			}
		});
		//切换上一张及下一张
		$("#img_preview_pre").off("click").on("click", function(){
			var preImg = $("#img_view_list li.current").prev("li");
			if(preImg.length == 0){
				$("#img_view_list li:last").click();
			}else{
				preImg.click();
			}
		});
		$("#img_preview_next").off("click").on("click", function(){
			var nextImg = $("#img_view_list li.current").next("li");
			if(nextImg.length == 0){
				$("#img_view_list li:first").click();
			}else{
				nextImg.click();
			}
		});
		$("#img_up_preview").show();		
	};
	/*
	 *@summary js校验相关方法
	 */
	window.validate = IOOP.validate = {
		//是否为空
		isEmpty: function(str){
			return !/0+/.test(str) && !str;
		},
		//不能为空
		notEmpty: function(str){
			return /0+/.test(str) || str;
		},
		//数字
		isNumber: function(str){
			return /^[0-9]*$/.test(str);
		},
		//邮编
		isZipCode: function(str){
			return /^[0-9]{6}$/.test(str);
		},
		//身份证
		isIdCardNo: function(str){
			return /^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/.test(str);
		},
		//手机号码
		isMobile: function(str){
			return str.length == 11 && /^1[3|4|5|8|7][0-9]\d{4,8}$/.test(str);
		},
		//QQ号码
		isQQ: function(str){
			return /^[1-9]\d{4,9}$/.test(str);
		},
		//email
		isEmail: function(str){
			return /\w@\w*\.\w/.test(str);
		},
		//url
		isUrl: function(str){
			return /(https?|ftp|mms):\/\/([A-z0-9]+[_\-]?[A-z0-9]+\.)*[A-z0-9]+\-?[A-z0-9](\/.*)*\/?/.test(str);
		},
		//固定电话
		isPhone: function(str){
			return /^(0[0-9]{2,3}-?)?([2-9][0-9]{6,7})+(-?[0-9]{1,4})?$/.test(str);
		},
		//手机号码或固定电话
		isTel: function(str){
			var _length = str.length,
                _mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-3,5-9]{1}))+\d{8})$/,
                _tel = /^(0[0-9]{2,3}-?)?([2-9][0-9]{6,7})+(-?[0-9]{1,4})?$/;
			return (_tel.test(str) && _length <= 12) || (_mobile.test(str) && _length == 11);
		},
		//ip地址
		isIp: function(str){
			return /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(str);
		},
		/*
		 *字符串长度，smin:最小值；最大值,一个中文长度为2
		 *@param str,要验证的字符串
		 *@param params,参数数组
		 */
		cnLength: function(str, params){
			var _length = str.length,
			    _totalLen = _length,
				smin = 0,
				smax = 0;
			if(params instanceof Array){
				switch(params.length){
					case 0:
					    return false;
						break;
					case 1:
					    smax = parseInt(params[0]);
						if(smax == 0 || isNaN(smax)){
							return false;
						}
						break;
					default:
					    smin = parseInt(params[0]);
					    smax = parseInt(params[1]);
						if(smax == 0 || isNaN(smax) || isNaN(smin)){
							return false;
						}
				}
			}else{
				return false;
			}
			for(var i = 0; i < _length; i++) {   
				if(str.charCodeAt(i) > 127) {
					_totalLen++;
				}
			}
			return _totalLen >= smin && _totalLen <= smax;
		},
		//只允许输入中英文字符，数字和下划线
		userName: function(str){
			return /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(str);
		},
		//字母和数字的验证
		chrnum: function(str){
			return /^([a-zA-Z0-9]+)$/.test(str);
		},
		//中文的验证
		chinese: function(str){
			return /^[\u4e00-\u9fa5]+$/.test(str);
		},
		//密码的验证
		userPassword: function(str){
		    if(str.length == 0){
		    	return false;
		    }
		    return /^\w{6,}$/.test(str) && /^([a-zA-Z0-9]+)$/.test(str);
		},
		//是否默认密码
		isDefaultPassword: function(str){
			return ('123456' != str);
		},
		//是否简单密码
		isSimplePassword: function (str){
			function isASC(test){
				for(var i=1;i<test.length;i++){
					if(test.charCodeAt(i) != (test.charCodeAt(i-1)+1)){
						return false;
					}
				}
				return true;
			}
			
			function isDESC(test){
				for(var i=1;i<test.length;i++){
					if(test.charCodeAt(i) != (test.charCodeAt(i-1)-1)){
						return false;
					}
				}
				return true;
			}
			
			function isSame(test){
				for(var i=1;i<test.length;i++){
					if(test.charCodeAt(i) != (test.charCodeAt(i-1))){
						return false;
					}
				}
				return true;
			}
			return !(isASC(str)||isDESC(str)||isSame(str));
		},
		//图片的验证
		isPic: function(str){
			return /.jpg|.png|.gif|.jpeg$/.test(str.toLowerCase());
		},
		//校验正整数
		isPInt: function(str){
			return  /^[1-9]*[1-9][0-9]*$/.test(str);
		},
		//只允许输入英文字符，数字和下划线
		onlyEn_Num: function(str){
			return /^\w+$/.test(str);
		},
		//只允许输入中英文字符，数字和下划线
		onlyCn_En_Num: function(str){
			return /^[a-zA-Z0-9_\u4e00-\u9fa5]+$/.test(str);
		},
		//只能包括中英文字母、数字、下划线和中文标点符号
		onlyCn_En_Num_Point: function(str){
			return /^[a-zA-Z0-9_\u4e00-\u9fa5，。“”；！？@、]+$/.test(str);
		},
		/*
		 *组织机构框是否选择用户、职位等
		 *@param str,要验证的html代码
		 *@param params,参数数组,一般放置要验证的容器id
		 */
		orgTreeValid: function(str, params){
			var contId = "";
			if(params instanceof Array && params.length > 0){
				contId = params[0];
				if(contId || $("#"+contId).length != 0){
					return $("#"+contId+" a").length > 0;
				}
			}
			return false; 
		},
		//错误信息
		errorMsg: {
			"notEmpty": "不能为空哦！",
			"isNumber": "只能输入数字哦！",			
			"isZipCode": "邮政编码不正确哦!",
			"isIdCardNo": "身份证号码不正确哦!",
			"isMobile": "手机号码格式错啦!",
			"isPhone": "电话号码格式错啦!",			
			"isTel": "联系电话格式错啦!",
			"isQQ": "QQ号码格式错啦！",
			"isEmail": "email格式错啦！",
			"isUrl": "网址格式错啦！",
			"isIp": "ip地址格式错啦!",
			"cnLength": "长度要介于{0}到{1}之间哦!",
			"userName": "用户名只能包括中英文、数字和下划线哦!",
			"chrnum": "只能输入数字和字母(字符A-Z, a-z, 0-9)哦！",
			"chinese": "只能输入中文哦！",
			"userPassword": "登录密码只能为6-14位数字或英文字符哦！",
			"isDefaultPassword": "登录密码不能为默认密码哦！",
			"isSimplePassword": "登录密码太简单哦！",
			"isPic": "只能是jpg、png、gif、jpeg格式的图片哦！",
			"isPInt": "只能输入正整数哦！",
			"onlyEn_Num": "只能输入英文，数字和下划线哦!",
			"onlyCn_En_Num": "只能输入中英文，数字和下划线哦!",
			"onlyCn_En_Num_Point": "只能输入中英文，数字、下划线和中文标点符号哦!",
			"orgTreeValid": "您还没有选择用户哦！",
			"equalTo": "请输入相同的值哦！"
		},
		/*
		 *显示错误信息
		 *@param ele,要验证的元素
		 *@param funName,未通过验证的方法名字
		 *@param params,提示信息额外参数
		 */
		showErrorMsg: function(ele, errorMsg, params){
			if(!ele){
				return false;
			}
			var _parent = $(ele).parent(),
			    _offset = _parent.offset(),
				_height = _parent.outerHeight(),
				errorObj = null;
			if(params && params.length > 0){
				for(var i=0; i<params.length; i++){
					errorMsg = errorMsg.replace("{"+ i +"}", params[i]);
				}
			}
			if(!errorMsg){
				errorMsg = "格式不对哦！";
			}
			_parent.addClass("error-mark");
			errorObj = $('<div class="error-info" style="top:'+ (_offset.top + _height) +'px; left:'+ _offset.left +'px;"><div class="error-info-main"><p>'+ errorMsg +'</p><b class="error-arrow"></b><b class="error-close"></b></div></div>');
			errorObj.appendTo("body");
			errorObj.find(".error-close").on("click", function(){
				errorObj.remove();
				errorObj = null;
				_parent.removeClass("error-mark");
				_parent = null;
			});
			//滚动到元素所在位置
			$(window).scrollTop(_offset.top - 20);
			setTimeout(function() {
				if(errorObj != null){
					errorObj.remove();
					errorObj = null;
					_parent.removeClass("error-mark");
				    _parent = null;
				}else{
				    errorObj = null;
					_parent = null;
				}
			}, 2000);
		},
		/*
		 *添加新的自定义校验方法
		 *@param validFunName,验证方法的名字
		 *@param validFun,验证方法函数体
		 *@param 方法参数含义为 value,调用验证方法时传入的值，如果是表单元素，传入的是表单元素的值；否则传入的是元素的html代码;params,添加验证规则时传入的参数
		 *@param errorMsg,默认错误提示消息
		 */
		addMethod: function(option){
			var _option = {
				validFunName: "",
				validFun: null,
				errorMsg: ""
			};
			$.extend(_option, option);
			if(!_option.validFunName || !_option.validFun){
				return false;
			}else{
				IOOP.validate[_option.validFunName] = _option.validFun;
				IOOP.validate.errorMsg[_option.validFunName] = _option.errorMsg;
			}
		},
		/*
		 *添加唯一性校验方法
		 *@param validFunName,验证方法的名字
		 *@param validUrl,验证请求地址
		 *@param errorMsg,默认错误提示消息
		 */
		uniqueValidate: function(option){
			var _option = {
				validFunName: "",
				validUrl: "",
				errorMsg: ""
			};
			$.extend(_option, option);
			if(!_option.validFunName || !_option.validUrl){
				return false;
			}else{
				validate.addMethod({
					validFunName: _option.validFunName,
					validFun: function(value,params){
						var data = {},returnBoolean = false;
						$(params).each(function(index, element) {
                            data[element] = $("#"+element).val();
                        });
						$.ajax({
							url: _option.validUrl,
							data: data,
							cache: false,
							async: false,
							type: 'POST',
							dataType: 'text',
							timeout: 10000,
							error: function(){
								IOOP.validate.errorMsg[_option.validFunName] = '对不起，服务器响应超时，请联系管理员';
							},
							success: function(result){
								IOOP.validate.errorMsg[_option.validFunName] = _option.errorMsg;
								if(result == '1'){
									returnBoolean = true;
								}else{
									returnBoolean = false;
								}
							}
						});
						return returnBoolean;
					},
					errorMsg: _option.errorMsg
				});
			}
		},
		/*
		 *@summary 表单验证方法
		 *@param contId,容器id，可以是表单也可以是其他容器
		 */
		formValidate: function(option){
			var _option = {
				    contId: ""
				},
			    _result = true;
			$.extend(_option, option);
			if(!_option.contId){
				return false;
			}
			//执行校验
			$("#"+_option.contId).find("[validates]").each(function(index, element) {
				var _value = "",
					_validates = $(this).attr("validates"),
					_equalId = $(this).attr("equalTo"),
					_equalObj = null,
					_equalValue = "",
					_cList = [],
					_cLen = 0,
					_validItem = "",
					_sPaPos = 0,
					_ePaPos = 0,
					_sMsgPos = 0,
					_eMsgPos = 0,
					_errorMsg = "",
					_extParams = [],
					_validFunName = "",
					_validFun = null;
				if($(this).is("input") || $(this).is("textarea")){
					_value = $(this).val();
				}else{
					_value = $(this).html();
				}
				if(_equalId && $("#"+_equalId).length >0){
					_equalObj = $("#"+_equalId);
					if(_equalObj.is("input") || _equalObj.is("textarea")){
						_equalValue = _equalObj.val();
					}else{
						_equalValue = _equalObj.html();
					}
					if(_equalValue != _value){
						_result = false;
						_errorMsg = validate.errorMsg["equalTo"];
						IOOP.validate.showErrorMsg($(this), _errorMsg);
						return false;
					}
				}
				if(_result && _validates){
					_cList = _validates.split(" ");
					_cLen = _cList.length;					
					if(_cLen > 0){
						if(!_value && _validates.indexOf("notEmpty") == -1 && _validates.indexOf("halfEmpty") == -1 && _validates.indexOf("orgTreeValid") == -1){
							return true;
						}
						for(var i=0; i<_cLen; i++){
							_validItem = _cList[i];
							if(_validItem == "halfEmpty"){
								continue;
							}
							if(_validItem.indexOf("[") != -1){
								_sPaPos = _validItem.indexOf("[");
							    _ePaPos = _validItem.indexOf("]");
								_extParams = _validItem.substring((_sPaPos + 1), _ePaPos).split(",");																
								_validFunName = _validItem.substring(0, _sPaPos);
								_validFun = IOOP.validate[_validFunName];
								if(typeof _validFun == "function"){									
									if(!_validFun(_value, _extParams)){
										_result = false;										
										if(_validItem.indexOf("{") != -1){
											_sMsgPos = _validItem.indexOf("{");
										    _eMsgPos = _validItem.indexOf("}");
											_errorMsg = _validItem.substring((_sMsgPos + 1), _eMsgPos);
										}else{
											_errorMsg = validate.errorMsg[_validFunName];
										}										
										IOOP.validate.showErrorMsg($(this), _errorMsg, _extParams);
										return false;
									}
								}
							}else{
								if(_validItem.indexOf("{") != -1){
									_sMsgPos = _validItem.indexOf("{");
									_eMsgPos = _validItem.indexOf("}");
									_validFunName = _validItem.substring(0, _sMsgPos);
									_errorMsg = _validItem.substring((_sMsgPos + 1), _eMsgPos);
								}else{
									_validFunName = _validItem;
									_errorMsg = validate.errorMsg[_validFunName];
								}
								_validFun = IOOP.validate[_validFunName];								
								if(typeof _validFun == "function"){
									if(!_validFun(_value)){
										_result = false;
										IOOP.validate.showErrorMsg($(this), _errorMsg);
										return false;
									}
								}
							}
						}
					}
				} 
			});
			return _result;
		}
	};
	/*
	 *@summary 请求加载前执行的方法
	 *@param excepIds,例外不销毁的上传组件Id,多个用逗号隔开
	 */
	IOOP.beforeRequest = function(option){
		var _option = {
		    excepIds: ""	
		};
		if(typeof option == "object"){
		    $.extend(_option, option);
		}
		//关闭下拉列表窗口
		$(".simu-sel-list").hide();
		//销毁已有上传对象
		var len = uploadList.length, _id;
		if(len > 0){
			for(var i = 0; i<len; i++){
				_id = uploadList[i];
				if(_id && (!_option.excepIds || _option.excepIds.indexOf(_id) == -1)){
					try{						
						$("#"+_id).hide().uploadify('destroy');
					}catch(e){}
				}
			}
			uploadList = [];
		}
	};
	/*
	 *@summary 请求加载方法
	 *@param selector,放置html代码的jquery选择器
	 *@param url,请求地址
	 *@param data,请求附带的参数
	 *@param callback,请求成功的回调函数法
	 */
	IOOP.getRequest = function(selector, url, data, callback, preventTop){
		//离开页面前确认
		if(leaveConfirm.getLeaveFlag()){
			if(confirm(leaveConfirm.getLeaveMsg())){
				var _leaveOKFun = leaveConfirm.getLeaveOKFun();
				if(typeof _leaveOKFun == "function"){
					_leaveOKFun();
				}
	        }else{
	        	var _leaveCancelFun = leaveConfirm.getLeaveCancelFun();
				if(typeof _leaveCancelFun == "function"){
					_leaveCancelFun();
				}
	        	return false; 
	        }
			leaveConfirm.close();
		}
		//切换前执行的操作
		try{
		    IOOP.beforeRequest();
		}catch(e){}
		
		selector = !selector ? "#main-content" : selector;
		if(arguments.length < 2){
			return false;
		}else if(arguments.length == 2){
			data = {};
		}else if(arguments.length == 3){
			if(typeof data == "function"){
				callback = data;
				data = {};
			}else if(typeof data == "boolean"){
				preventTop = data;
				data = {};
			}else{}
	    }else if(arguments.length == 4){
			if(typeof callback == "boolean"){
				preventTop = callback;
				callback = null;				
			}			
		}else{}
		//处理url
		if(url.indexOf("&") != -1){
			url += "&";
		}else{
			//判断是否含参数
			if(url.indexOf("?") != -1){
				//判断是否含一个参数
				if(url.indexOf("=") != -1){
					url += "&";
				}
			}else{
				//不含参数的url
				url += "?";
			}
		}
		
		url = url +"sessionId=" + IOOP.sessionId +"&randID=" + Math.random();
		$(selector).loadingShow(true).load(url,data,function(){
			if(typeof callback == "function"){
			    callback();
			}
			$(selector).loadingHide();
			if(!preventTop){
			    $(window).scrollTop(0);
			}
		});
	};
	/*
	 *@summary 请求加载方法
	 *@param selector,放置html代码的jquery选择器
	 *@param url,请求地址
	 *@param data,请求附带的参数
	 *@param preventTop,是否阻止页面自动滚动到顶部，默认false
	 *@param beforeJump,请求前执行方法，如果返回false则取消请求,如果定义了该方法请务必返回true
	 *@param excepIds,不销毁的上传组件id,多个用逗号隔开
	 *@param callback,请求成功的回调函数法
	 */
	IOOP.gotoNewLink = function(option){
		var _option = {
			selector: "#main-content",
			url: "",
			data: {},
			preventTop: false,
			beforeJump: null,
			excepIds: "",
			callback: null
		},_url;
		//离开页面前确认
		if(leaveConfirm.getLeaveFlag()){
			if(confirm(leaveConfirm.getLeaveMsg())){
				var _leaveOKFun = leaveConfirm.getLeaveOKFun();
				if(typeof _leaveOKFun == "function"){
					_leaveOKFun();
				}
	        }else{
	        	var _leaveCancelFun = leaveConfirm.getLeaveCancelFun();
				if(typeof _leaveCancelFun == "function"){
					_leaveCancelFun();
				}
	        	return false; 
	        }
			leaveConfirm.close();
		}
		$.extend(_option, option);
		_url = _option.url;
		if(!_url){
			return false;
		}
		//切换前执行的操作
		try{
			if(typeof _option.beforeJump == "function"){
				if(!_option.beforeJump()){
					return false;
				}
			}
		    IOOP.beforeRequest({excepIds: _option.excepIds});
		}catch(e){}
		//处理url
		if(_url.indexOf("&") != -1){
			_url += "&";
		}else{
			//判断是否含参数
			if(_url.indexOf("?") != -1){
				//判断是否含一个参数
				if(_url.indexOf("=") != -1){
					_url += "&";
				}
			}else{
				//不含参数的url
				_url += "?";
			}
		}		
		_url += "randID=" + Math.random();		
		//执行跳转
		$(_option.selector).loadingShow(true).load(_url,_option.data,function(){
			if(typeof _option.callback == "function"){
			    _option.callback();
			}
			$(_option.selector).loadingHide();
			if(!_option.preventTop){
			    $(window).scrollTop(0);
			}
			//IOOP.option.unloadFlag = false;
		});
	};
	/*
	 *@summary app操作方法
	 */
	window.appOperate = IOOP.appOperate = {
		/*
		 *@summary app切换方法
		 *@param appObj,请求的app对象
		 */
		gotoNewApp: function(appObj){
			if(!appObj){
				return false;
			}
			var _this = $(appObj),
			    _appAction = "";
			_this.addClass("current").siblings().removeClass("current");
			_this.find(".app-item-nflag").hide();
			try{
				DWP.refresh();
			}catch(e){}
			_appAction = _this.attr("action");
			if(_appAction){
				IOOP.gotoNewLink({
					selector: "#main-content",
					url: _appAction
				});	
			}
		},
		/*
		 *@summary 增加新应用
		 *@param appCode,app编码
		 *@param appName,app名字
		 *@param appIndex,要放置的位置，从0开始
		 *@param appAction,应用跳转地址
		 */
		addNewApp: function(option){
			var _option = {
			        appCode: "",
			        appName: "",
			        appIndex: "",
			        appAction: ""
			    },
			    _index = 0,
			    _newApp = "";
			$.extend(_option, option);
			_index = parseInt(_option.appIndex);
			_newApp = '<li id="app_item_'+ _option.appCode +'" action="'+ _option.appAction +'" onclick="appOperate.gotoNewApp(this);" class=""><a><b class="menu-option menu-'+ _option.appCode +'"></b>'+ _option.appName +'<span class="app-hover"></span></a></li>';
			if(isNaN(_index)){
				$("#menu_list ul").prepend(_newApp);
			}else{
				$("#menu_list li:eq("+ _index +")").after(_newApp);
			}
		},
		/*
		 *@summary 删除某个应用
		 *@param appCode,app编码
		 */
		removeApp: function(appCode){
			if(appCode){
				$("#app_item_"+appCode).remove();
			}
		},
		/*
		 *@summary 选中某个应用
		 *@param appCode,app编码
		 */
		selectApp: function(appCode){
			if(appCode){
				$("#app_item_"+appCode).addClass("current").siblings().removeClass("current");
			}
		},
		/*
		 *@summary 清除所有应用的选中状态
		 */
		clearAppSelState: function(){
			$("#menu_list li").removeClass("current");
		},
		/*
		 *@summary 提醒应用下有新内容
		 *@param appCodes, 要提醒的app编码数组
		 */
		remindApps: function(appCodes){
			var appNum = 0;
			if(appCodes instanceof Array && appCodes.length > 0){
				$("#menu_list .app-item-nflag").hide();
				appNum = appCodes.length;
				for(var i = 0; i<appNum; i++){
					$("#app_item_"+ appCodes[i] +" .app-item-nflag").show();
				}
			}
		}
	};
	/*
	 *@summary fun操作方法
	 */
	window.funOperate = IOOP.funOperate = {
		/*
		 *@summary fun切换方法
		 *@param funObj,请求的fun对象
		 */
		gotoNewFun: function(funObj){
			var _id = $(funObj).attr("id"),
			    mapObj = null;
			mapObj = IOOP.urlMapping[_id];
			if(mapObj){
				IOOP.gotoNewLink({
					selector: "#main-content",
					url: mapObj.url,
					data: mapObj.data
				});	
			}
		}
	};
	/*
	 *@summary 获得对象对应父对象居中时的坐标
	 *@param sonObj 子对象
	 *@param parentObj,父对象
	 *@return 返回包含坐标信息的json数据,例如{posLeft:0,posTop:0}
	 */
	IOOP.getCenterPostion = function(sonObj,parentObj){
		if(!sonObj){
			return {posLeft:0,posTop:0};
		}
		if(!parentObj){
			parentObj = $(window);
		}
		var wWith = parentObj.width(),
			wHeight = parentObj.height(),
			_with = sonObj.width(),
			_height = sonObj.height(),
			_left = 0,
			_top = 0;
		_left = (wWith - _with)/2,
		_top = (wHeight - _height)/2;					
		_left = _left > 0 ? parseInt(_left) : 0;
		_top = _top > 0 ? parseInt(_top) : 0;
		return {posLeft: _left, posTop: _top};
	};
	/*
	 *@summary 显示提示窗口方法
	 *@param option 方法配制对象
	 *@param showTitle,提示窗口标题，默认"消息提示"
	 *@param showMsg,提示窗口消息，默认"消息提示"
	 *@param alertType,提示窗口类型，alert,confirm;默认alert
	 *@param btnOKText,确认按钮文本，默认”确定“
	 *@param btnCancelText,取消按钮文本，默认”取消“
	 *@param btnOKHandler,确认按钮处理方法
	 *@param btnCancelHandler,取消按钮处理方法
	 *@param closeHandler,关闭处理方法
	 */
	IOOP.showalert = function(option){
		var _option = {
			showTitle: "消息提示",
			showMsg: "消息提示",
			alertType: "alert",
			btnOKText: "确定",
			btnCancelText: "取消",
			btnOKHandler: null,
			btnCancelHandler: null,
			closeHandler: null
		},
		_this = null,
		_posObj = null;
		$.extend(_option,option);
		
		$(".alert-dialog").remove();
		_this = $('<div class="ioop-dialog alert-dialog"><h6>'+ _option.showTitle +'<b class="dialog-close dialog-close-btn"></b></h6><div class="dialog-main alert-main"><dl><dt' + (_option.alertType == 'confirm' ? ' class="icon-confirm"' : '') +'></dt><dd>'+ _option.showMsg +'</dd></dl><div class="dialog-btn"><a class="dp-inline b-btn-two dialog-btn-ok">'+ _option.btnOKText +'</a>'+ (_option.alertType == 'confirm' ? '<a class="dp-inline b-btn-two dialog-btn-cancel">'+ _option.btnCancelText +'</a>' : '')+'</div>');
		_this.find(".dialog-close-btn").click(function(){
			_this.remove();
			$.unmaskElement("1");
			if(typeof _option.closeHandler == "function"){
				_option.closeHandler();
			}
			return false;
		});
		_this.find(".dialog-btn-ok").click(function(){
			_this.remove();
			$.unmaskElement("1");
			if(typeof _option.btnOKHandler == "function"){
				_option.btnOKHandler();
			}
			return false;
		});
		_this.find(".dialog-btn-cancel").click(function(){			
			_this.remove();
			$.unmaskElement("1");
			if(typeof _option.btnCancelHandler == "function"){
				_option.btnCancelHandler();
			}
			return false;
		});
		$.maskElement("1");		
		_this.appendTo($("body"));
		_posObj = IOOP.getCenterPostion(_this,$(window));
		_this.css({"left": _posObj.posLeft,"top": _posObj.posTop}).show().draggable();		       
	};
	/*
	 *@summary 居中显示或关闭对话框窗口
	 *@param dialogId,对话框id
	 *@param isClose,关闭标识：true,关闭窗口；false：打开窗口 默认false
	 *@param preventDrag,是否阻止拖动：true,阻止；false:不阻止 默认false
	 */
	IOOP.toggleDialog = function(dialogId,isClose,preventDrag){
		var _posObj = null,_this=null;
		if(!dialogId){
			return false;
		}
		_this = $("#"+dialogId);
		if(_this.length == 0){
			return false;
		}
		if(!isClose){
			_posObj = IOOP.getCenterPostion(_this);
			$.maskElement("0");
			_this.css({"left": _posObj.posLeft, "top": _posObj.posTop}).show();
			if(!preventDrag){
				_this.draggable({cancel:".dialog-main"});
			}
		}else{
			$.unmaskElement("0");
			_this.hide();
		}
	};
	/*
	 *@summary 居中显示或关闭对话框窗口
	 *@param dialogId,对话框id
	 *@param isClose,关闭标识：true,关闭窗口；false：打开窗口 默认false
     *@param maskLevel,遮罩层级，默认"0"级
	 *@param preventDrag,是否阻止拖动：true,阻止；false:不阻止 默认false
	 */
	IOOP.centerDialogShow = function(option){
		var _posObj = null,
            _this=null,
            _option = {
                dialogId: "",
                isClose: false,
                maskLevel: "0",
                preventDrag: false
            }
        $.extend(_option, option);
		if(!_option.dialogId){
			return false;
		}
		_this = $("#"+_option.dialogId);
		if(_this.length == 0){
			return false;
		}
		if(!_option.isClose){
			_posObj = IOOP.getCenterPostion(_this);
			$.maskElement(_option.maskLevel);
			_this.css({"left": _posObj.posLeft, "top": _posObj.posTop}).show();
			if(!_option.preventDrag){
				_this.draggable({cancel:".dialog-main"});
			}
		}else{
			$.unmaskElement(_option.maskLevel);
			_this.hide();
		}
	};
	/*
	 *@summary 新建iframe对话框窗口
	 *@param option,窗口配置对象，或者窗口名字字符串
	 *@param ifmId,窗口id
	 *@param ifmTitle,窗口标题
	 *@param ifmWidth,窗口宽度
	 *@param ifmHeight,窗口高度
	 *@param ifmSrc,iframe地址
	 *@param ifmBar,是否显示iframe标题栏
	 *@param preventDrag,是否显示iframe窗口拖动
	 */
	IOOP.newIframeDialog = function(option){
		var _option = {
			ifmId: "",
			ifmTitle: "新建窗口",
			ifmWidth: 1000,
			ifmHeight: parseInt($(window).height()*0.9),
			ifmSrc: "",
			ifmBar: true,
			preventDrag: false
		}
		if(typeof option == "object"){
			$.extend(_option,option);
			if(!_option.ifmId){
				return false;
			}			
			$("#ifm_dialog_"+_option.ifmId).remove();
			$('<div id="ifm_dialog_'+ _option.ifmId +'" class="ioop-dialog" style="width:'+ _option.ifmWidth +'px; height:'+ _option.ifmHeight +'px;">'
			    + (_option.ifmBar ? '<h6>'+ _option.ifmTitle +'</h6>':'')
				+ '<div class="dialog-main"><iframe id="ifm_'+ _option.ifmId +'" class="ifm_'+ _option.ifmId +'" frameborder="0" scrolling="auto" src="'+ _option.ifmSrc +'" style="width:100%; height:'+ (_option.ifmBar? _option.ifmHeight-60 : _option.ifmHeight) +'px;"></iframe></div></div>').appendTo("body");
		    IOOP.toggleDialog("ifm_dialog_"+ _option.ifmId,false,_option.preventDrag);
		}else{
			if($("#ifm_dialog_"+option).length==0){
				return false;
			}else{
				IOOP.toggleDialog("ifm_dialog_"+option,true);
			    $("#ifm_dialog_"+option).remove();
			}
		}
	};
	/*
	 *@summary 显示或关闭手写签批窗口
	 *@param isClose,关闭标识：true,关闭窗口；false：打开窗口 默认false
	 *userId 用户id
	 *listId img所在div id
	 *orgId 单位ie
	 *ideaname 意见框id
	 */
	IOOP.toggleSignature = function(isClose,userId,listId,orgId,ideaname){
		var signHtml = '<div id="d_edit_sign" class="ioop-dialog d-edit-sign"><h6>签批办理意见</h6><div class="dialog-main"><iframe id="ifm_signature" class="ifm-signature" frameborder="0" scrolling="no"></iframe></div></div>';
        if(!isClose){				
			$("body").append(signHtml);
			IOOP.toggleDialog("d_edit_sign");
			$("#ifm_signature").attr("src","include/hand_signature.jsp?ideaname="+encodeURI(ideaname)+"&orgId="+orgId+"&listId="+listId+"&userId="+userId+"&sessionId="+IOOP.sessionId+"&randId="+IOOP.getRandom());
		}else{
			IOOP.toggleDialog("d_edit_sign",true);
			$("#d_edit_sign").remove();
		}
	}; 
	/*
	 *@summary 显示提示信息方法
	 *@param option 方法配制对象
	 *@param tipsMsg,提示消息，默认"消息提示"
	 *@param isOK,提示消息为成功还是失败，默认成功
	 *@param tipsDelay,提示消息延迟消失时间，默认2秒
	 *@param selecter,显示提示信息容器的jquery选择器，默认body
	 *@param offsetX,消息x轴偏移量，默认居中为0,负值左移，正值右移
	 *@param offsetY,消息y轴偏移量，默认容器顶部为0,负值上移，正值下移
	 *@param isFixed,是否固定显示消息提示框，true,固定；false,不固定，默认true
	 */
	IOOP.showtips = function(option){
		var _option = {
			    tipsMsg: "消息提示",
				isOK: true,
				tipsDelay: 2000,
				selecter: "body",
				offsetX: 0,
				offsetY: 0,
				isFixed: true
			},
			selecter = null,
			_offset = null,
			tipsObj = null,
			_selwidth = 0,
			_posX = 0,
			_posY = 0,
			_tipsHtml = [];
			
		$.extend(_option,option);
		selecter = $(_option.selecter);
		_offset = selecter.offset();
		_selwidth = selecter.width();
		_tipsHtml.push('<div class="tooltps ' + (_option.isOK ? "tips-success" : "tips-fail") + '"');	
		if(!_option.isFixed){
			_tipsHtml.push(' style="position:absolute;"');
		}
		_tipsHtml.push('><b class="tips-mark float-left"></b><p>' + _option.tipsMsg + '</p></div>');
		if(!_option.isFixed){
			tipsObj = $(_tipsHtml.join("")).appendTo(selecter);
		}else{
			tipsObj = $(_tipsHtml.join("")).appendTo("body");
		}
		_posX = (_selwidth - tipsObj.width()) / 2 + _option.offsetX - 20;
		_posY = _option.offsetY;
		_posX = _option.isFixed ? _posX + _offset.left : _posX;
		_posY = _option.isFixed ? _posY + _offset.top : _posY;
		tipsObj
		    .css({
		        "left" : _posX,
		        "top" : _posY
		    })
		    .show({
			    effect: "shake",
		        duration: 500
		    });
		setTimeout(function(){
			tipsObj.hide().remove();
			tipsObj = null;
			selecter = null;
			_offset = null;
		}, _option.tipsDelay);
	};
	/*
     *@summary 显示欢迎页
     */
    IOOP.showWelcome = function(){
        var _posObj = null;
        if($("#ioop_welcome").length == 0 && !$.cookie("isWelcome")){
            $("body").append('<div id="ioop_welcome" class="ioop-welcome"><span class="welcome-close">&nbsp;</span></div>');
            _posObj = IOOP.getCenterPostion($("#ioop_welcome"));
            $("#ioop_welcome").css({"left": _posObj.posLeft, "top": _posObj.posTop}).show().find(".welcome-close").on("click", function(){
                $("#ioop_welcome").remove();
                $.cookie("isWelcome", "isWelcome", {expires: 365});
            });
            /*setTimeout(function(){
                $("#ioop_welcome").remove();
                $.cookie("isWelcome", "isWelcome", {expires: 365});
            }, 8000);*/
        }        
    };
    /*
     *@summary 显示风格选择窗口
     *@param theme 默认选中风格
     *@param showed 是否已经选择风格，默认true
     */
    IOOP.showThemeSelect = function(option){
        var _option = {
                theme: "1",
                showed: true,
                standCount: 0,
                govCount: 0
            },
            standPercent = 0,
            _optionObj = null,
            _posObj = null;
    	try{
    		_optionObj = $.parseJSON(option);
    		if(typeof _optionObj == "object"){
    			$.extend(_option, _optionObj);
    		}
    	}catch(e){}
        if(!_option.showed && $("#theme_select").length == 0){
        	if(_option.standCount != 0 || _option.govCount != 0){
        		standPercent = parseInt(_option.standCount/(_option.standCount+_option.govCount)*100);        		
        	}else{
        		standPercent = 0;
        	}
        	$("body").append('<div id="theme_select" class="theme-select"><div class="theme-select-main"><span id="theme_select_close" class="theme-select-close"></span><h1>在尽情享受便捷办公之前，请您先选择一种喜欢的界面风格。您还可以随时通过系统右上角的风格切换按钮进行设置。</h1><div id="theme_select_list" class="theme-select-list clear"><ul><li theme="1"'+ (_option.theme == "1" ? ' class="current"' : "") +'><div class="theme-item-view theme-view-stand"></div><div class="theme-item-name"><b class="theme-item-icon"></b>选择标准风格</div><h2>已有<i>'+ standPercent +'%</i>的用户使用标准风格</h2><div class="theme-item-desc">互联网社交化的工作界面，清新的设计风格、灵活的互动交流模式</div></li><li theme="0"'+ (_option.theme == "0" ? ' class="current"' : "") +'><div class="theme-item-view theme-view-gov"></div><div class="theme-item-name"><b class="theme-item-icon"></b>选择传统风格</div><h2>已有<i>'+ (100 - standPercent) +'%</i>的用户使用标准风格</h2><div class="theme-item-desc">传统办公风格，可自定义显示内容，待办、待阅信息一目了然</div></li></ul></div><div id="theme_select_submit" class="theme-select-submit">选择好了</div></div></div>');
            _posObj = IOOP.getCenterPostion($("#theme_select"));
            $("#theme_select").css({"left": _posObj.posLeft, "top": _posObj.posTop});
            $("#theme_select_close").on("click", function(){
                $("#theme_select").remove();
                //保存用户设置状态
                $.getJSON("main!setThemeSet.do?themeSet=1&randId="+IOOP.getRandom(),function(data){
                    if(data.data == "1"){}                    
                });
            });
            $("#theme_select_list li").on("click", function(){
                $(this).addClass("current").siblings("li").removeClass("current");
            });
            $("#theme_select_submit").on("click", function(){
                var _theme = $("#theme_select_list li.current").attr("theme");
                if(_theme){
                    $("#theme_select").remove();
                    //保存主题设置数据
                    $.getJSON("main!setUserTheme.do?userTheme="+_theme+"&randId="+IOOP.getRandom(),function(data){
                        if(data.data == "1"){
                            IOOP.option.userTheme = _theme;
                            IOOP.backToIndex();
                        }                    
                    });
                    //保存用户设置状态
                    $.getJSON("main!setThemeSet.do?themeSet=1&randId="+IOOP.getRandom(),function(data){
                        if(data.data == "1"){}                    
                    });
                }
            });
        }      
    };
	/*
	 *@summary 富文本编辑器构造方法
	 *@summary contId,编辑器初始化元素ID
	 *@param eWidth,编辑器宽度，默认100%
	 *@param eHeight,编辑器高度，默认300px
	 *@param isFull,是否构造完整功能编辑器,true:全部功能;flase:基本文字编辑功能。默认true
	 *@param extParams, 自定义参数
	 *@param simpleToolbars, 简单模式功能条
	 *@return 返回构造好的编辑器对象
	 */	
	IOOP.getRichTextEditor = function(option){
		var _option = {
		        contId: "",
				eWidth: '100%',
				eHeight: 400,
				isFull: true,
		        simpleToolbars: [['source', '|', 'undo', 'redo', '|', 'bold', 'italic', 'underline', 'fontborder', 'strikethrough','forecolor', 'backcolor','lineheight','fontfamily', 'fontsize','indent','justifyleft', 'justifycenter', 'justifyright', 'justifyjustify', '|', 'insertimage']],
				extParams: {},
                inLayer: false
			},
			eOption = null;
		$.extend(_option, option);
		if(!_option.contId || $("#" + _option.contId).length == 0){
			return false;
		}
        eOption = {
            ioopParams: _option.extParams,
            initialFrameWidth: _option.eWidth,
            initialFrameHeight: _option.eHeight
        }
		if(!_option.isFull){
            eOption.toolbars = _option.simpleToolbars;
		}
        if(_option.inLayer){
            eOption.zIndex = 9999;
		}
		return UE.getEditor(_option.contId,eOption);
	};
	/*
	 *@summary 日期相关处理方法
	 */	
    window.dealDate = IOOP.dealDate = {
		/*
		 *@summary 获取今日日期方法
		 *@return 今日日期年月日数组 格式 2014,03,12
		 */	
		getNowDate: function(date){
			var nDate = new Date(),
				nYear = nDate.getFullYear(),
				nMonth = nDate.getMonth() + 1,
				nDay = nDate.getDate(),
				dateStr = [];
			if(!date){
				dateStr[0] = nYear;
				if(nMonth < 10){  
					nMonth = "0" + nMonth;  
				}
				dateStr[1] = nMonth;  
				if(nDay < 10){  
					nDay = "0" + nDay;  
				}
				dateStr[2] = nDay;
			}else{
				dateStr = date.split("-");
			}
			
			return dateStr;
		},
		/*
		 *@summary 获取今日或指定日期时间方法
		 *@return 今日日期时间数组 格式 2014,03,12,09,53,50
		 */	
		getNowTime: function(){
			var nDate = new Date(),
				nYear = nDate.getFullYear(),
				nMonth = nDate.getMonth() + 1,
				nDay = nDate.getDate(),
				nHour = nDate.getHours(),
				nMinit = nDate.getMinutes(),
				nSecond = nDate.getSeconds(),
				dateStr = [];
				
			dateStr[0] = nYear;
			nMonth = nMonth < 10 ? "0" + nMonth : nMonth;
			dateStr[1] = nMonth;
			nDay = nDay < 10 ? "0" + nDay : nDay;
			dateStr[2] = nDay;
			nHour = nHour < 10 ? "0" + nHour : nHour;
			dateStr[3] = nHour;
			nMinit = nMinit < 10 ? "0" + nMinit : nMinit;
			dateStr[4] = nMinit;
			nSecond = nSecond < 10 ? "0" + nSecond : nSecond;
			dateStr[5] = nSecond;
			
			return dateStr;
		},
		/*
		 *@summary 根据开始日期时间和结束日期时间返回与服务器时间比较的结果
		 *@param sDate, 目标开始日期时间对象
		 *@param eDate, 目标结束日期时间对象
		 *@param serverDate,服务器时间对象
		 *@param isText,是否返回纯文本信息，默认false
		 *@return 日期间隔字符串 格式 1天5时30分
		 */	
		getDateOffset: function(sDate,eDate,serverDate,isText){
			var smillOffset = 0,
				_temp = 0,
				timeStr = "0",
                _dayOffset = 0,
                _stdateArry = [],
                _srdateArry = [];
			if(sDate instanceof Date && sDate instanceof Date && serverDate instanceof Date){
				millOffset = sDate.getTime() - serverDate.getTime();
				if(millOffset <= 0){
					millOffset = eDate.getTime() - serverDate.getTime();
					if(millOffset <= 0){
                        timeStr = !isText ? '<span class="meet-opt-finised">已结束</span>' : "已结束";						
					}else{
                        timeStr = !isText ? '<span class="meet-opt-starting">正在开会...</span>' : "正在开会...";
					}
				}else{
                    timeStr = !isText ? '<span class="meet-opt-unstart">距会议开始还有' : "距会议开始还有";
                    _stdateArry[0] = sDate.getFullYear();
                    _stdateArry[1] = sDate.getMonth();
                    _stdateArry[2] = sDate.getDate();
                    _srdateArry[0] = serverDate.getFullYear();
                    _srdateArry[1] = serverDate.getMonth();
                    _srdateArry[2] = serverDate.getDate();
                    if(_stdateArry[0] != _srdateArry[0] || _stdateArry[1] != _srdateArry[1] || _stdateArry[2] != _srdateArry[2]){
                        _dayOffset = parseInt((new Date(_stdateArry[0], _stdateArry[1], _stdateArry[2]).getTime() - new Date(_srdateArry[0], _srdateArry[1], _srdateArry[2]).getTime())/(1000*60*60*24));                        
                        timeStr += !isText ? '<span class="meet-time-num">' + _dayOffset + '</span>天</span>' : _dayOffset + "天";
                    }else{
                        _temp = Math.floor(millOffset/(1000*60*60));
                        timeStr += !isText ? '<span class="meet-time-num">' + _temp + '</span>时' : _temp + "时";
                        millOffset = millOffset - _temp*1000*60*60;
                        _temp = Math.floor(millOffset/(1000*60));
                        timeStr += !isText ? '<span class="meet-time-num">' + _temp + '</span>分</span>' : _temp + "分";
                    }  
				}
				return timeStr;
			}else{
				return timeStr;
			}
		},
		/*
		 *@summary 格式化日期为 yyyy mm dd
		 *@param date, 要格式化的日期对象
		 *@param mark, 分割标志，默认“-”
		 *@return 日期字符串
		 */  
		formatDate: function(date,mark){
			if(!(date instanceof Date)){
				date = new Date(); 
			}   
			var _year = date.getFullYear(), 
			    _month = date.getMonth()+1;  
			    _weekday = date.getDate();   
			
			if(!mark){
				mark="-";
			}
			if(_month < 10){  
				_month = "0" + _month;  
			}   
			if(_weekday < 10){  
				_weekday = "0" + _weekday;  
			}  
			return (_year + mark + _month + mark + _weekday);   
		},
		/*
		 *@summary 封装日期为对象
		 *@param date, 要封装的日期对象”
		 *@return 封装完成的日期对象
		 */  
		sealDateObj: function(date){
			if(!(date instanceof Date)){
				date = new Date(); 
			} 
			var dateObj = new Object(),
			    dOfweek = date.getDay(); 
			dOfweek = dOfweek == 0 ? 7 : dOfweek; 
			dateObj.weekDay = dOfweek;   //本周星期
			dateObj.nowDay = date.getDate(); //当前日期
			dateObj.nowMonth = date.getMonth(); //当前月份
			dateObj.nowYear = date.getFullYear(); //当前年
			dateObj.dateString = dealDate.formatDate(date,"-");  //获得日期字符串
			return dateObj;
		}	
	}; 
    
    /*
	 *@summary 获取当前时间 yyyy-mm-dd HH:MM:SS
	 */	
    IOOP.getCurrentTime = function(){
    	var tmp = dealDate.getNowTime();
    	var timestamp = tmp[0]+'-'+tmp[1]+'-'+tmp[2]+' '+tmp[3]+':'+tmp[4]+':'+tmp[5];
    	return timestamp;
    };
    
    /*
	 *@summary 会议视图方法
	 *@param option,会议视图配置对象
	 */	
    IOOP.meetingListView = function(option){
        var _option = {
			    selector : "", //会议显示内容放置容器jquery选择器
				viewDate : "", //显示的日期，格式：2014-03-12 默认今天
				meetings : [], //会议，格式:[{"meetId":"a1","meetTitle":"会议标题","meetContent":"会议内容","meetJoiner":"张三","meetDate":"2014-03-25","startTime":"08:00","roomName":"五楼会议室","isJoin":true,"noJoin":true}]
				tabUrl: "", //点击其他会议状态的时候通过该地址异步请求所选状态的会议数据
				meetClickHander: "", //会议单击事件处理方法,参数(会议id)
				btnJoinHander: "", //会议确认参加处理方法,参数(会议id)
				btnNoJionHander: "", //会议确认不参加处理方法,参数(会议id)
				btnEditHander: "",  //会议编辑方法，参数(会议id)
				btnDeleteHander: ""  //会议删除方法，参数(会议id)
			}, 
			dataObj = null; 
		$.extend(_option,option);		
		if((typeof option != "object") || !option.selector || $(option.selector).length == 0){
			return false;
		}
		$(option.selector).find(".meet-type-list").remove();
		$('<div class="meet-type-list"><ul><li data-tab="week_all" class="first-type current"><a>本周会议</a></li><li data-tab="week_unopen"><a>未开会议</a></li><li data-tab="week_opened"><a>已开会议</a></li><li data-tab="week_opening"><a>正在进行</a></li></ul></div>')
		.find("li")
		.on("click",function(){
			var _this = $(this),
			    mState = _this.attr("data-tab"),
				_tabUrl = _option.tabUrl;
			_this.addClass("current").siblings("li").removeClass("current");
			if(!_tabUrl){
				return false;
			}
			_tabUrl = _tabUrl.indexOf("?")>=0 ? (_tabUrl + "&state=" + mState) : (_tabUrl + "?state=" + mState)
			$.getJSON(_tabUrl + "&randId=" + Math.random(),function(data){
				_option.meetings = data;
				$(".meet-opt-main").remove();
				$.getJSON("meeting/meeting-info!getServiceTime.do?randId="+Math.random(), function(data){
					_option.viewDate = data.serviceTime;
					buildMeetData();
				});
			});
		}).end().appendTo($(option.selector));
		$('<div id="meet_block_data" class="meet-list-main"></div>').appendTo($(option.selector));
		var buildMeetData = function(){
			var dataObj = $("#meet_block_data");
			    nowDate = !_option.viewDate ? new Date() : new Date(_option.viewDate.replace(/-/g,"/")),
				meetOptMain = null,
				meetOption = null,
				weekDay = [],
				_nowDateObj = dealDate.sealDateObj(nowDate),
				newDay = 0,
                todayFlag = false;
			if(_option.meetings.length == 0){
				if(dataObj.find(".no-result").length == 0){
				    dataObj.append('<div class="no-result"><div class="no-result-normal"><div class="no-result-remark">暂无会议数据</div></div></div>');
				}
				return false;
			}else{
				dataObj.find(".no-result").remove();
			}
			for(var i=1;i<=7;i++){
				newDay = _nowDateObj.nowDay + (i - _nowDateObj.weekDay);
				weekDay = dealDate.formatDate(new Date(_nowDateObj.nowYear,_nowDateObj.nowMonth,newDay),"-").split("-");
				todayFlag = weekDay.join('-') == _nowDateObj.dateString;
				meetOptMain = $('<div'+ (todayFlag ? ' id="meeting_today_mark"' : '') + ' class="meet-opt-main' + (todayFlag ?' opt-today':'') +'"><ul></ul><div class="meeting-date"><span>'+ weekDay[1] +'</span>月'+ weekDay[2] +'日</div><div class="meeting-arrow"></div></div>');
				meetOption = meetOptMain.find('ul');
				meetOptMain.appendTo(dataObj);
				$(_option.meetings).each(function(index, element){
					if(weekDay.join('-') == element.meetDate){
						var meetOptDate = element.meetDate.split("-"),
							sHourSecond = element.startTime.split(":"),
							eHourSecond = element.endTime.split(":"),
                            meetStateFlag = 0,
                            meetStateClass = "",
							meetItem = [],
							meetState = dealDate.getDateOffset(new Date(parseInt(meetOptDate[0], 10),(parseInt(meetOptDate[1], 10)-1),parseInt(meetOptDate[2], 10),parseInt(sHourSecond[0], 10),parseInt(sHourSecond[1], 10)),new Date(parseInt(meetOptDate[0], 10),(parseInt(meetOptDate[1], 10)-1),parseInt(meetOptDate[2], 10),parseInt(eHourSecond[0], 10),parseInt(eHourSecond[1], 10)), nowDate);
                        if(meetState.indexOf("meet-opt-unstart") > 0){
                            meetStateFlag = 0;
                            meetStateClass = "meet-opt-unstart";
                        }else if(meetState.indexOf("meet-opt-starting") > 0){
                            meetStateFlag = 1;
                            meetStateClass = "meet-opt-starting";
                        }else{
                            meetStateFlag = 2;
                            meetStateClass = "meet-opt-finished";
                        }
                        meetItem.push('<li class="'+ meetStateClass +'">');
						meetItem.push('<dl><dt class="meet-room-name over-flow">'+ element.roomName +'</dt><dt class="meet-opt-tilte">'+ element.meetTitle +'</dt><dt class="meet-opt-content">'+ element.meetContent +'</dt><dt class="meet-opt-joiner">申请人：<span>'+ element.meetApply +'</span></dt><dt class="meet-opt-joiner">主持人：<span>'+ element.meetHost +'</span></dt><dt class="meet-opt-joiner">参会人：<span>'+ element.meetJoiner +'</span></dt><dt class="meet-opt-joiner">会议时间：<span>'+ element.startTime.substr(0,5)+"&nbsp;至&nbsp;" +element.endTime.substr(0,5)+'</span></dt></dl>');
                        if(meetStateFlag == 0){
                            meetItem.push('<div class="meet-opt-state"><div class="mopt-state-cont over-flow">'+ meetState +'</div></div>');
                        }
						meetItem.push('<div class="meet-operate-list">');
						if(element.isDelete == "1"){
							meetItem.push('<b class="meet-opt-delete" title="删除"></b><b class="meet-opt-edit" title="编辑"></b>');
						}
						if(element.isDisplay == "0"){
							meetItem.push('<b class="meet-opt-join meet-opt-joinck" title="我要参加"></b><b class="meet-opt-nojoin meet-opt-nojoinck" title="我不参加"></b>');
						}else if(element.isDisplay == "1"){
							meetItem.push('<b class="meet-opt-join" title="我要参加"></b><b class="meet-opt-nojoin meet-opt-nojoinck" title="我不参加"></b>');
						}else if(element.isDisplay == "2"){
							meetItem.push('<b class="meet-opt-join meet-opt-joinck" title="我要参加"></b><b class="meet-opt-nojoin" title="我不参加"></b>');
						}else{}						
						meetItem.push('</div>');
                        meetItem.push('<div class="mopt-state-icon"></div>');
                        meetItem.push('</li>');
						$(meetItem.join(""))
						.on("click",function(){
							if(typeof _option.meetClickHander == "function"){
								_option.meetClickHander(element.meetId);
								return false;
							}
						}).find(".meet-opt-join").on("click",function(){
							if(typeof _option.btnJoinHander == "function"){
								_option.btnJoinHander(element.meetId,$(this).hasClass("meet-opt-joinck"),$(this));
								return false;
							}
						}).end().find(".meet-opt-nojoin").on("click",function(){
							if(typeof _option.btnNoJionHander == "function"){
								_option.btnNoJionHander(element.meetId,$(this).hasClass("meet-opt-nojoinck"),$(this));
								return false;
							}
						}).end().find(".meet-opt-edit").on("click",function(){
							if(typeof _option.btnEditHander == "function"){
								_option.btnEditHander(element.meetId);
								return false;
							}
						}).end().find(".meet-opt-delete").on("click",function(){
							if(typeof _option.btnDeleteHander == "function"){
								_option.btnDeleteHander(element.meetId,$(this).parent().parent());
								return false;
							}
						}).end().appendTo(meetOption);
					}
                });				
			}
			$("#meet_block_data .meet-opt-main").each(function(index, element) {
                if($(this).find("li").length == 0){
					$(this).remove();
				}
            });
			if($("#meet_block_data .meet-opt-main").length == 0){
				if(dataObj.find(".no-result").length == 0){
				    dataObj.append('<div class="no-result"><div class="no-result-normal"><div class="no-result-remark">暂无会议数据</div></div></div>');
				}
			}else{
				dataObj.find(".no-result").remove();
			}
			//自动滚动到今日会议位置
			if($("#meeting_today_mark").length > 0){                
                $(window).scrollTop($("#meeting_today_mark").offset().top);
            }
		};
		buildMeetData();		
	};
	
	/*
	 *@summary 附件格式化相关方法
	 */
	window.fileUpload = IOOP.fileUpload = {
		/*
		 *@summary 转化为附件列表方法
		 *@param listId,附件列表容器id
		 *@param bizId,业务主键
		 *@param appCode,应用编码
		 *@param editFlag,是否为可编辑状态，true编辑状态可删除，false查看状态
		 *@param zipName,全部压缩包的名字，传入该名字则显示全部下载按钮
		 */
		parseFileList: function(option){
			var _option = {
					listId: "",
					bizId: "",
					appCode: "",
					editFlag: false,
					zipName: ""
			    },
			    _listId = "",
			    _listHtml = [],
			    _listUl = null,
			    _attchList = null;
			$.extend(_option, option);
			_listId = _option.listId;
			if(!_listId || $("#"+_listId).length == 0 || !_option.bizId){
				return false;
			}
			_listUl = $("#"+_listId);
			$.getJSON("sys/file-upload!getFilesShow.do?bizId="+ _option.bizId +"&appCode="+ _option.appCode +"&randId="+IOOP.getRandom(),function(data){
				var _index = 0,
				    _enableView = true;
				$(data).each(function(index, element) {
					if(typeof element.bizType == "string" && element.bizType.indexOf("fck") >-1){
						return true;
					}
					_index = element.fileName.lastIndexOf(".");
					if(_index > 0){
						_enableView = IOOP.option.enViewSuffix.indexOf(element.fileName.substring(_index+1).toLowerCase()) > -1;
					}else{
						_enableView = false;
					}
					_listHtml.push('<li>');
					_listHtml.push('<span class="attch-name"><a href="'+ element.fileUrl +'" target="_blank" title="'+ element.fileName +'">'+ fileUpload.parseFileName(element.fileName, 100) +'</a></span>');
					_listHtml.push('<span class="attch-size">'+ fileUpload.parseFileSize(element.fileSize) +'</span>');
					if(element.vo && element.vo.uploadUserName){
                        _listHtml.push('<span class="attch-uploader">('+ element.vo.uploadUserName +'上传)</span>');
                    }
					_listHtml.push('<a class="attch-dload" href="'+ element.fileUrl +'" target="_blank">下载</a>');
					if(_enableView){
						_listHtml.push('<a class="attch-view" href="'+ IOOP.option.viewUrl + element.relationId +'" target="_blank">预览</a>');
					}
					if(_option.editFlag){
						_listHtml.push('<a class="attch-delete attch-delete-mark" relid="'+ element.relationId +'" style="display:inline;">删除</a>');
					}
					_listHtml.push('</li>');
				});
				if(_listHtml.length>0){
					_listUl.append(_listHtml.join("")).show();
					if(_option.zipName){
						_listUl.append('<li class="download-all"><a href="'+ IOOP.option.downAllUrl +'?zipName='+ encodeURIComponent(_option.zipName) +'&bizId='+ _option.bizId +'" title="如果您使用的解压缩软件为winrar,请保证版本为5.0以上哦！">下载全部</a></li>');
					}
				}
				if(_option.editFlag){
					_listUl.find(".attch-delete-mark").on("click",function(){
						var _parent = $(this).parent(),
							relid = $(this).attr("relid");
						if(relid){							
							$.getJSON("sys/file-upload!deleteFile.do?relationId="+ relid +"&sessionId="+IOOP.sessionId,function(data){
								if(data.success == "true"){
									_parent.remove();
									if(_listUl.find("li").length == 0){
										_listUl.hide();
									}
								}
							});
						}
					});	
				}
			});
		},
		/*
		 *@summary 格式化文件名,超出长度加...
		 *@param fileName,文件名称
		 *@param length,最大长度
		 */
		parseFileName: function(fileName,length){
			if(fileName && length){
				return (fileName.length > length) ? fileName.substring(0,length) + "..." : fileName;
			}
			return "";
		},
		/*
		 *@summary 格式化附件大小显示，小于MB显示KB，否则显示MB
		 *@param fileSize,附件大小
		 */
		parseFileSize: function(fileSize){
			if(typeof fileSize != "number"){
				fileSize = parseInt(fileSize);
			}
			if(!isNaN(fileSize)){
				if(fileSize < 1024*1024){
					return (fileSize/1024).toFixed(2) + "KB";
				}else if(fileSize < 1024*1024*1024){
					return (fileSize/(1024*1024)).toFixed(2) + "MB";
				}else{
					return (fileSize/(1024*1024*1024)).toFixed(2) + "GB";
				}
			}else{
				return 0;
			}
		}
	};
	/*
	 *@summary 调整首页侧边栏显示或隐藏状态
	 */
    IOOP.toggleLayerState = function(){
        var layerState = $.cookie("layerState");
        if(layerState == "hide"){
            $("#fback_layer,#home_tips").hide();
            $("#layer_toggle").attr("state", "hide").addClass("layer-toggle-close");
        }
        $("#layer_toggle").on("click", function(){
            if($(this).attr("state") == "hide"){
                $("#fback_layer,#home_tips").show();
                $(this).attr("state", "show").removeClass("layer-toggle-close");
                $.cookie("layerState", "show", {expires: 365});
            }else{
                $("#fback_layer,#home_tips").hide();
                $(this).attr("state", "hide").addClass("layer-toggle-close");
                $.cookie("layerState", "hide", {expires: 365});
            }
        });
    };
    /*
	 *@summary 根据配置绑定应用打开处理方法
	 */
    IOOP.bindAppHandler = function(){
    	$("#menu_list li").each(function(index, ele){
    		switch($(this).attr("opentype")){
    		//iframe方式打开
    		case "00B":
    			var _id = $(this).attr("id"),
		            _action = $(this).attr("action");
			    if(_id && _action){
			    	this.onclick = function(){
						$("#main-content").html('<div class="app-iframe"><iframe id="app_iframe" src="'+ _action +'" class="app-item-iframe" frameborder="0" scrolling="no"></iframe></div>');
						IOOP.setIframeSize();
						appOperate.selectApp(_id.substring(_id.lastIndexOf("_")+1));
						$(window).scrollTop(0);
			    	}
				}
    			break;
    		//新窗口方式打开
    		case "00C":
    			var _id = $(this).attr("id"),
			        _action = $(this).attr("action");
    			this.onclick = null;
    			if(_id && _action){
	    			$(this).find("a").attr({"href": _action, "target": "_blank"});
	    			appOperate.selectApp(_id.substring(_id.lastIndexOf("_")+1));
    			}
    			break;
    		default:
    			return true;
    		}
    	});
    };
    /*
	 *@summary 与iframe通信，获取并设置其高度
	 */
    IOOP.setIframeSize = function(){
    	var messenger = new Messenger('ioop', 'ioop_app'),
            iframe_app = document.getElementById('app_iframe');
        messenger.addTarget(iframe_app.contentWindow, 'app_iframe');
        messenger.listen(function(msg){
        	if(!isNaN(msg)){
        		iframe_app.height = msg;
        	}
        });
    };
	/*
	 *@summary 首页及各模块初始化方法
	 */
	IOOP.init = {
		/*
		 *@summary 首页框架初始化方法
		 */
		initFrame: function(){
			//标签切换通用方法
			$("body").on("click",".tab-common li",function(){
				var _this = $(this),
					 tabID = _this.attr("data-tab");
				_this.addClass("current").siblings().removeClass("current");
				if(tabID){
					$("#"+tabID).siblings().hide().end().show();
				}
			});
			//列表复选框状态切换方法
			$("body").on("click",".checkbox-all",function(){
				if(!!this.checked){
					$(".checkbox-opt[disabled!=disabled]").prop("checked",true).parent().parent().addClass("lt-row-checked");
				}else{
					$(".checkbox-opt").prop("checked",false).parent().parent().removeClass("lt-row-checked");
				}
			});
			$("body").on("click",".checkbox-opt",function(){
				if(!!this.checked){
					$(this).parent().parent().addClass("lt-row-checked");
				}else{
					$(this).parent().parent().removeClass("lt-row-checked");
				}
			});
			//去掉所选用户
			$("body").on("click",".list-users .user-remove",function(){
				var _this = $(this),
					 userID = _this.attr("userId");
				_this.parent().remove();
				if(userID){				
				}
			});
			//展开评论填写窗口
			$("body").on("click",".home-add,.issue-replay",function(){
				$(this).removeClass("editor-simple");
			});	
			//首页顶部按钮		
			$("#top_btn li").on("click",function(){
				var _id = $(this).attr("id"),
				    mapObj = null;
				switch(_id){
					case "goto_index":
					    IOOP.backToIndex();
						break;
				    case "goto_person":
					    mapObj = IOOP.urlMapping["m_person"];
						if(mapObj){
							appOperate.clearAppSelState();
							IOOP.gotoNewLink({
								selector: "#main-content",
								url: mapObj.url,
								data: mapObj.data
							});
						}
						break;
				    case "goto_system":
					    mapObj = IOOP.urlMapping["m_system"];
						if(mapObj){
							appOperate.clearAppSelState();
							IOOP.gotoNewLink({
								selector: "#main-content",
								url: mapObj.url,
								data: mapObj.data
							});
						}
						break;
				    case "log_out":
				    	//退出时断开连接
				    	DWP.stop();
					    mapObj = IOOP.urlMapping["m_logout"];
						if(mapObj){
							location.href= mapObj.url;
						}
						break;
				    default:
					    return true;
				}
			});
			//应用打开方式处理方法
			IOOP.bindAppHandler();
			//安装卸载应用
			$("#icon_app_add").click(function(){
				IOOP.gotoNewLink({
					url: "sys/sys-user-func-config.do"
				});
			});
			//回到顶部栏
			$("#tips_top").on("click",function(){
				$(window).scrollTop(0);
			});
			//点击头像后链接到用户头像设置界面
			$("#user_logo").on("click",function(){
				IOOP.gotoNewLink({
					url: "sys/sys-user-config!inputLogo.do"
				});
			});
			//鼠标点击显示单位网址
			$("#goto_website").on("click", function(){
				var _left = $(this).offset().left,
				    _html = '';
				$(".ioop-head-layer").hide();
				$("#org_webs").btnLoading();
				$.getJSON("website/org-fav-config!queryJsonList.do", function(data){
					$("#org_webs_list li").remove();
					$(data).each(function(index, element) {
                        _html += '<li><img src="'+element.webContent+'/favicon.ico" onerror="this.src=IOOP.option.defWebImg;this.onerror=null;" /><a href="'+element.webContent+'" target="_blank" title="'+element.webName+'">'+element.webName+'</a></li>';    
                    });
					if(!_html){
						$("#org_webs_list").append('<li class="no-item">暂时没有单位网址</li>');
					}else{
						$("#org_webs_list").append(_html);				
					}
					$("#org_webs").btnLoading(true).css("left", _left - 100).show();
				});
			});
			$("body").on("click",function(){
				$(".simu-sel-list").hide();
				$("#org_webs").hide();
				$("#ioop_emoticons").hide();
				$("#mtag_color_list").hide();
		        $("#tag_item_layer").hide();
		        $("#theme_switch_layer").hide();
		        $("#address_gov_layer").hide();
		        $("#help_vedio_layer").hide();
		        $("#survey_users_layer").hide();
			});
			//双击编辑个性签名
			$("#user_signed").on("dblclick",function(){
				try{
					var _editArea = $("#user_s_edit"),
						_eFlag = _editArea.attr("eflag"),
						_sCont = $("#user_s_cont").text();
					if(_eFlag == "false"){
						$("#user_s_cont").hide();
						_editArea.attr("eflag","true").val(_sCont).show();
						if('双击设置个性签名' == $("#user_s_edit").val()){
							$("#user_s_edit").val("");
						}
						$("#user_s_edit").focus();
					}
				}catch(e){
				}
			});
			//输入框失去焦点保存签名
			$("#user_s_edit").on("blur",function(){
				try{
					$("#user_s_edit").val(IOOP.trim($("#user_s_edit").val()));
					var _newCOnt = '';
					if($("#user_s_edit").val().length >45){
						 IOOP.showalert({showMsg:'个性签名超过最大45个字长度，请修改！'});
						 return ;
					}
					
					if($("#user_s_edit").val().length == 0 || $("#user_s_edit").val()=='双击设置个性签名'){
						//IOOP.showalert({showMsg:'您还没有输入任何内容哦！'});
						//$("#user_s_edit").focus();
						$("#user_s_edit").attr("eflag","false").hide();
						$("#user_s_cont").text('双击设置个性签名').show();
						$.ajax({
							   url: "sys/sys-user-config!updateUserSign.do?sessionId="+IOOP.sessionId,
							   type: "POST",
							   async: true,
							   data:{"userSign":""},
						  	   dataType:"text"
						});	
					}else{
						_newCOnt = $("#user_s_edit").attr("eflag","false").hide().val();
						_oldCOnt = $("#user_s_cont").text();
						//如果没有改变签名
						if(_newCOnt != _oldCOnt){
							$.ajax({
								   url: "sys/sys-user-config!updateUserSign.do?sessionId="+IOOP.sessionId,
								   type: "POST",
								   async: true,
								   data:{
								   		"userSign":IOOP.replaceSpecChar(_newCOnt)
							  	   },
							  	   dataType:"text",
								   success: function(result){
								     if(result == '1' ){
								    	 if(_newCOnt != ''){
								    		 IOOP.showtips({tipsMsg:'个性签名更新成功！',selecter:'#main-content'});
								    	 }
								     	return false;					     	
								     }else{
								    	 IOOP.showalert({showMsg:'个性签名更新失败！'});
								     }
								   }
			
							});	
						}
						$("#user_s_cont").text(IOOP.replaceSpecChar(_newCOnt)).show();
					}
					
		
				}catch(e){
				}
			});
			//添加表情窗口
			IOOP.addEmoticons();
			//切换首页主题            
            $("#switch_theme").on("click",function(){
				var _left = parseInt($(this).offset().left - 330);
				$(".ioop-head-layer").hide();
                $("#theme_switch_layer").css("left", _left).show();
                return false;
			});
            $("#theme_switch_layer li").on("click",function(){
            	var _theme = $(this).attr("theme");                
                //保存主题数据
                $.getJSON("main!setUserTheme.do?userTheme="+_theme+"&randId="+IOOP.getRandom(),function(data){
                    if(data.data == "1"){
                        IOOP.option.userTheme = _theme;
                        IOOP.backToIndex();
                    }                    
                });                
            });
            //打开系统帮助视频
            $("#help_vedio_btn").on("click",function(){
				var _left = parseInt($(this).offset().left - 150);
				$(".ioop-head-layer").hide();
                $("#help_vedio_layer").css("left", _left).show();
                return false;
			});
            //调整侧边栏显示状态
            IOOP.toggleLayerState();
			//首页相关静态绑定方法
			//投票项关闭
			$("body").on("click",".vote-enable-close .icon-small-close",function(){
				$(this).parent().remove();
				if($("#vote_opt_list .vote-input").length == 2){
					$("#vote_opt_list .vote-input").removeClass("vote-enable-close");
				}
			});

			//添加新便签
			$("body").on("click","#add_note",function(){
				addNewNote();
			});	

			//便签编辑事件
			$("body").on("dblclick","#h_note_list .h-note-item",function(){
				//检测是否有正在编辑的便签
				if($("#h_note_list .edit-enable").length>0){
					IOOP.showtips({
						tipsMsg: "您还有未保存的便签,请保存后编辑！",
						isOK: false,
						selecter: "#h_note_list",
						offsetX: -15,
						offsetY: 0
					});
					return false;
				}
				var _contObj = $(this).addClass("edit-enable").find(".h-note-cont"),
					_cont = _contObj.text();
				_contObj.html('<div class="h-note-edit"><textarea class="edit-note">'+ _cont +'</textarea></div>');
				return false;
			});
			//便签保存事件
			$("body").on("click","#h_note_list .note-opt-save",function(){
				var _contId = $(this).parent().siblings("input[name='np.notePadId']").val(),
					_contCreateTime = $(this).parent().siblings("input[name='np.createTime']").val(),
					_cont = $(this).parent().find(".h-note-edit").find(".edit-note").val().replace(/</g,'＜').replace(/>/g,'＞').replace(/"/g,'“');
				if($.trim(_cont) == ""){
					IOOP.showalert({showMsg:'便签内容不能为空！'});
					return false;
				}else{
					$.ajax({
						   url: "notepad/note-pad!save.do?sessionId="+IOOP.sessionId,
						   type: "POST",
						   async: false,
						   data:{
						   		"np.notePadId":_contId, 
						   		"np.noteContent":_cont,
					  		 	"np.createTime":_contCreateTime
					  	   },
					  	   dataType:"json",
						   success: function(np){
						     if(np == null ){
						     	IOOP.showalert({showMsg:'保存便签出现问题！请联系管理员！'});
						     	return false;					     	
						     }else{
						     	notePadPageOperate(0);
						     }
						   }
	
					});	
				}
				return false;
			});
			//便签删除事件
			$("body").on("click","#h_note_list .note-opt-delete",function(){
				var _contId = $(this).parent().siblings("input[name='np.notePadId']").val();
				if(_contId == undefined){
						notePadPageOperate(0);
				}else{
					$.ajax({
						   url: "notepad/note-pad!delete.do?sessionId="+IOOP.sessionId,
						   type: "POST",
						   async: false,
						   data:{
						   		"np.notePadId":_contId
					  	   },
					  	   dataType:"text",
					  	   success: function(result){
						     if(result == "true"){
							   	var dl_num = $("#h_note_list dl").length;	
							   	var note_current_page_no = $("#note_current_page_no").val();
							   	//如果删除的是非第1页的，最后1条记录，则显示上页信息
								if(note_current_page_no!=1 && dl_num==1){
							   		notePadPageOperate(-1);
								//否则，就刷新本页信息
								}else{
									notePadPageOperate(0);
								}	
						     }else{
						     	IOOP.showalert({showMsg:'删除便签出现问题！请联系管理员！'});
						     	return false;					     	
						     }
						   }
						   
					});
				}
				return false;
			});				
			//便签显示信息
			function getNotePadHtml(notePad){
				return '<dl><input type="hidden" name="np.notePadId" value="'+ notePad.notePadId +'"/><input type="hidden" name="np.createTime" value="'+ notePad.createTime +'"/><dt>'+ notePad.createTime +'</dt><dd class="h-note-item"><span class="h-note-cont"><div title="双击编辑">'+ notePad.noteContent +'</div></span><b class="note-opt-save" title="保存"></b><b class="note-opt-delete" title="删除"></b></dd></dl>';
			}
			//便签每页记录数
			var notePadPageSize = 5;
			//便签分页栏
			function getNotePadPageBar(notePadPage){
				var currentPageNo = notePadPage.currentPageNo;
				var totalPageCount = notePadPage.totalPageCount;
				var notePadPageBar = '<input type="hidden" id="note_current_page_no"  value="'+ currentPageNo +'"/> '
									+'<input type="hidden" id="note_total_page"  value="'+ totalPageCount +'"/>'
				if(currentPageNo > 1){
					notePadPageBar += '<b id="note_prev_page" class="adds-prev-page" title="上一页"></b>';
				}
				if(totalPageCount > 1){	
					notePadPageBar += '<a>第'+ currentPageNo +'页</a>';
				}
				if(currentPageNo < totalPageCount){
					notePadPageBar += '<b id="note_next_page" class="adds-next-page" title="下一页"></b>';
				}	
						            
				return notePadPageBar;
			}
			//便签分页事件
			//@param operater -1:上页，0：刷新本页，+1下页，
			function notePadPageOperate(operater){
				var note_current_page_no = $("#note_current_page_no").val();
				var note_total_page = $("#note_total_page").val();
				if(note_current_page_no > note_total_page){
					note_current_page_no = parseInt(note_current_page_no) -1;
				}
				$.post(
					"notepad/note-pad!list.do?sessionId="+IOOP.sessionId,
				  	{
				  		pageNo: parseInt(note_current_page_no)+parseInt(operater),
				  		pageSize: notePadPageSize
				  	},
				  	function(notePadPage){
				  		var home_note_list = "";
				  		$.each(notePadPage.data,function(key,notePad){
							home_note_list += getNotePadHtml(notePad);
				  		});
				  		$("#h_note_list").html(home_note_list);
				  		$("#note_more").html(getNotePadPageBar(notePadPage));
				  	},
				  	"json"
				);					
			}
			//便签上一页
			$("body").on("click","#note_prev_page",function(){
				notePadPageOperate(-1);
				return false;
			});
			//便签下一页
			$("body").on("click","#note_next_page",function(){
				notePadPageOperate(+1);
				return false;
			});
			//控制便签输入字数
			$("body").on("keyup focus blur click mousedown mousemove",".edit-note",function(){
				var _this = $(this),
				    _cont = _this.val(),
					maxLen = IOOP.option.noteMaxSize;
				if(_cont.length > maxLen){
					_this.val(_cont.substring(0,maxLen));
				}
			});	
		},
		/*
		 *@summary 内嵌首页初始化方法
		 */
		initIndex: function(){
			$("#home-ed-tab li").on("click",function(){
				var _this = $(this),
					 tabID = _this.attr("data-tab"),
					 arrowObj = $("#home-ed-arrow"),
					 preObj = $("#home-ed-tab li.current"),
					 preObjLeft = preObj.offset().left,
					 preObjWidth = preObj.width(),
					 nowObjLeft = _this.offset().left,
					 nowObjWidth = _this.width(),
					 moveToPx = 0,
					 prePos = arrowObj.css("left");
				_this.addClass("current").siblings().removeClass("current");			
				try{
					moveToPx = parseInt(prePos) + parseInt(nowObjLeft) + parseInt(nowObjWidth)/2 - parseInt(preObjLeft) -  parseInt(preObjWidth)/2;
					arrowObj.animate({left:moveToPx+"px"},200,function(){
						if(tabID){
							$("#"+tabID).siblings().hide().end().show();
						}
					});
				}catch(e){}
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
			//添加表情
			$("#new-add-face").on("click",function(){
				var _offset = $(this).offset();
				$("#ioop_emoticons").attr("descId", "dyamic-content").css({"left": _offset.left-15, "top": _offset.top+30}).show();
				$("#dyamic-content").focus();
				return false;
			});
			//上传图片
			$("#new-add-img").on("click",function(){
				$("#img_upload_dynamic").toggle();
			});
			//关闭导向窗口
			$("#guide_close").on("click", function(){
				$("#guide_main").remove();
			});
			//向导第一步上传头像
			$(".g-step-one").not(".is-finished").on("click", function(){
				IOOP.getRequest("#main-content","sys/sys-user-config!inputLogo.do",{});
			});
			//向导第二步设置个性签名
			$(".g-step-two").not(".is-finished").on("click", function(){
				IOOP.getRequest("#main-content","sys/sys-user-config!input.do",{});
			});
			//向导第三步创建工作创建工作圈
			$(".g-step-three").not(".is-finished").on("click", function(){
				IOOP.getRequest("#main-content","sys/personal-sys-group!list.do",{});
			});
			
			//取消左侧菜单的选中状态
			$("#menu_list li.current").removeClass("current");
		},
		/*
		 *@summary 邮件初始化方法
		 */	
		initMail: function(){
			$(".btn-style b").on("click",function(){
				var _this = $(this),
					_class = _this.attr("class");
				_this.addClass("current").siblings("b").removeClass("current");
				if(_class.indexOf("style-to-block") >=0 ){
					$(".mail-list-main").removeClass("lstyle-two").addClass("lstyle-one"); 
					$.cookie("mail_listStyle", "true", {expires: 365});
					IOOP.option.mail.listStyle = "true";
				}else{
					$(".mail-list-main").removeClass("lstyle-one").addClass("lstyle-two");
					$.cookie("mail_listStyle", "false", {expires: 365});
					IOOP.option.mail.listStyle = "false";
				}
			});
			//绑定功能切换方法
			$("#btn_mail .is-btn-mail").on("click",function(){
				funOperate.gotoNewFun(this);				
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 会议通用初始化方法
		 */	
		initMeeting: function(){
			//绑定功能切换方法
			$("#btn_meeting .is-btn-meet").on("click",function(){
				funOperate.gotoNewFun(this);			
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 单位管理初始化方法
		 */	
		initSys: function(){
			//绑定功能切换方法
			$(".tab-module li").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
			//取消左侧菜单的选中状态
			$("#menu_list li.current").removeClass("current");
		},
		/*
		 *@summary 系统管理初始化方法
		 */	
		initSysAdmin: function(){
			//绑定功能切换方法
			$("#btn_sys_admin .is-btn-sys-admin").on("click",function(){
				funOperate.gotoNewFun(this);
			});
		},
		/*
		 *@summary 公告初始化方法
		 */	
		initInfo: function(){
			//绑定功能切换方法
			$("#btn_info .is-btn-info").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//初始化公告列表处于哪种视图模式 IOOP.option.info.listStyle true 代表列表视图
			IOOP.option.info.listStyle  = $.cookie("infoListStyle");
			if(IOOP.option.info.listStyle==null ){
				$("#info_opt_list").removeClass("info-list").addClass("info-block");
				IOOP.option.info.listStyle = false;
			}else{
				if(IOOP.option.info.listStyle=='true'){
					$("#info_opt_list").removeClass("info-block").addClass("info-list");
					IOOP.option.info.listStyle = true;
				}else{
					IOOP.option.info.listStyle = false;
				}
			}
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 通讯录初始化方法
		 */	
		initContacts: function(){
			//初始化通讯录列表处于哪种视图模式 IOOP.option.contacts.listStyle true 代表列表视图,默认平铺模式
			IOOP.option.contacts.listStyle = $.cookie("constactsListStyle");
			if(IOOP.option.contacts.listStyle==null ){
				IOOP.option.contacts.listStyle = false;
			}else{
				if(IOOP.option.contacts.listStyle=='true'){
					IOOP.option.contacts.listStyle= true;
				}else{
					IOOP.option.contacts.listStyle = false;
				}
			}
			//绑定功能切换方法
			$("#btn_contact .is-btn-contact").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 审批初始化方法
		 */	
		initColl: function(){
			//绑定功能切换方法
			$("#btn_coll .is-btn-coll").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			$(".sub-m-tab li").not(".first-item").on("click",function(){
				$(this).addClass("current").siblings().removeClass("current");
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 公文初始化方法
		 */	
		initDoc: function(){
			//绑定功能切换方法
			$("#btn_doc .is-btn-doc").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 汇报初始化方法
		 */	
		initReport: function(){
			//绑定功能切换方法
			$("#btn_report .is-btn-report").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 计划初始化方法
		 */	
		initPlan: function(){
			//绑定功能切换方法
			$("#btn_plan .is-btn-plan").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 个人设置初始化方法
		 */	
		initPerson: function(){
			//绑定功能切换方法
			$("#btn_person .is-btn-person").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
			//取消左侧菜单的选中状态
			$("#menu_list li.current").removeClass("current");
		},
		/*
		 *@summary 超级管理员初始化方法
		 */	
		initSuper: function(){
			//绑定功能切换方法
			$("#btn_super .is-btn-super").on("click",function(){
				funOperate.gotoNewFun(this);
			});
		},
		/*
		 *@summary 文件柜初始化方法
		 */	
		initDocu: function(){
			//绑定功能切换方法
			$("#btn_docu .is-btn-docu").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 投票初始化方法
		 */	
		initVote: function(){
			//绑定功能切换方法
			$("#btn_vote .is-btn-vote").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 考勤初始化方法
		 */	
		initAttend: function(){
			//绑定功能切换方法
			$("#btn_attend .is-btn-attend").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 日程初始化方法
		 */	
		initCalender: function(){
			//绑定功能切换方法
			$("#btn_calendar .is-btn-calendar").on("click",function(){
				funOperate.gotoNewFun(this);
			});
		},
		/*
		 *@summary 短信彩信初始化方法
		 */	
		initSms: function(){
			//绑定功能切换方法
			$("#btn_sms .is-btn-sms").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 任务初始化方法
		 */	
		initTask: function(){
			//绑定功能切换方法
			$("#btn_task .is-btn-task").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		},
		/*
		 *@summary 调查初始化方法
		 */	
		initSurvey: function(){
			//绑定功能切换方法
			$("#btn_survey .is-btn-survey").on("click",function(){
				funOperate.gotoNewFun(this);
			});
			//ie9下初始化placehodler
			IOOP.hackPlaceholder();
		}
	};
	/*
	 *@summary扩展jquery方法
	 *
	 *@summary maskElement,给body添加遮罩
	 *@param maskLevel,遮罩等级：0，遮罩普通元素；1，遮罩全部元素用于显示alert
	 */
	$.maskElement = function(maskLevel){
		if(!maskLevel){
			maskLevel = '0';
		}
		if($(".mask-level-"+maskLevel).length == 0){
			$("body").append('<div class="ioop-loading-mask mask-level-'+ maskLevel +'"></div>');
		}
	};
	/*
	 *@summary unmaskElement给body取消遮罩
	 *@param maskLevel,遮罩等级：0，遮罩普通元素；1，遮罩全部元素用于显示alert
	 */
	$.unmaskElement = function(maskLevel){
		if(!maskLevel){
			maskLevel = '0';
		}
		$(".mask-level-"+maskLevel).remove();
	};
	/*
	 *@summary扩展jquery实例方法
	 */
	$.fn.extend({
		/*
		 *@summary loading添加显示方法
		 *@param str,要显示的文字内容,默认 '正在加载中，请稍后...'
		 *@param isMask,是否添加遮罩层
		 */
		loadingShow: function(str,isMask){			
            var _width = $(window).width(),
				_left = 0;
			if(typeof str == "boolean"){
				isMask = str;
				str = undefined;
			}
			if(isMask){
				$.maskElement("0");
			}
			if($(".ioop-loading").length == 0){
				_left = (_width - 300)/2;
				$("body").append('<div class="ioop-loading" style="left:'+ _left +'px;"><b></b><div class="loading-info">' + (!str ? '正在努力加载中...' : str) + '</div></div>');
			}
			return this;			
		},
		/*
		 *@summary loading隐藏去除方法
		 */
		loadingHide: function(){
			$.unmaskElement("0");
			$(".ioop-loading").remove();
			return this;
	    },
	    /*
		 *@summary 对话框显示方法
		 *@param preventDrag,是否阻止拖动窗口，默认false
		 *@param closeHandler,关闭按钮回调方法
		 */
	    dialogShow: function(option){
			var _option = {
				preventDrag: false,
				closeHandler: null
			};
			$.extend(_option, option);	
		    $(this).each(function(){
				var _this = $(this),
				    _id = _this.attr("id"),
				    _posObj = null;
				try{
					if(_id && _this.parent()[0] != document.body){
						$("body>#"+_id).remove();
						_this.appendTo("body");					
					}
					_posObj = IOOP.getCenterPostion(_this);	
					$.maskElement("0");
				    _this.css({"left": _posObj.posLeft, "top": _posObj.posTop}).show();					
					if(!_option.preventDrag){
						_this.draggable({cancel:".dialog-main"});
					}
				    _this.find(".dialog-close-btn").on("click",function(){
						if(typeof _option.closeHandler == "function"){
							_option.closeHandler();
						}
						_this.dialogHide();
					});				
				}catch(e){}
			});
			return this;
	    },
	    /*
		 *@summary 对话框隐藏方法
		 */
	    dialogHide: function(){
			$(this).hide();
			$.unmaskElement("0");
			return this;
		},
		/*
		 *@summary 按钮loading方法
		 *@param isClose:显示隐藏标识,true:隐藏；false:显示，默认false
		 */
	    btnLoading: function(isClose){
			$(this).each(function(){
				var _this = $(this);
				if(!isClose){					
					if(_this.find(".ioop-btn-mask").length > 0){
				        _this.find(".ioop-btn-mask").show();
					}else{
						_this.append('<b class="ioop-btn-mask"></b>');
					}
				}else{
					_this.find(".ioop-btn-mask").remove();
				}				
			});
			return this;	
		},		
		/*
		 *@summary 校验textarea长度
		 */
		checkLength: function(options){
			var defaults = {   
				len: 10
			};  
			var opts = $.extend(defaults, options);
			return this.each(function() {    
				  $this = $(this);
				  checklength($this,opts.len);		    
			}); 
			
			function getByteLen(str) {
				var len = 0;
				val = str.split("");
				for (var i = 0; i < val.length; i++) {
					if (val[i].match(/[^\x00-\xff]/ig) != null)
						len += 2;
					else
						len += 1;
				}
				return len;
			}
			function getByteVal(str, max) {
				var returnValue = '';
				var byteValLen = 0;
				val = str.split("");
				for (var i = 0; i < val.length; i++) {
					if (val[i].match(/[^\x00-\xff]/ig) != null)
						byteValLen += 2;
					else
						byteValLen += 1;
		
					if (byteValLen > max)
						break;
		
					returnValue += val[i];
				}
				return returnValue;     
			}
			function checklength(obj,len) {
				var _area = obj;
				var _max = len;
				var _val;
				_area.on('keydown keyup change', function() { 
					_val = $(this).val();
					_cur = getByteLen(_val);
					if (_cur == 0){
					} 
					else if (_cur < _max){
					} 
					else {
						obj.val(getByteVal(_val,_max));					 
					}
				});
			}
		},
		/*
		 *@summary 校验字符长度,汉子算一个字符
		 */
        checkCharLength: function(option){
			var _option = {   
				len: 10
			};  
			$.extend(_option, option);
			return this.each(function(){    
                var _max = _option.len,
				    _val = "",
                    _len = 0;
				$(this).on('keydown keyup change', function(){ 
					_val = $(this).val();
					_len = _val.length;
					if(_len > _max){
                        $(this).val(_val.substring(0, _max));
					}
				});  		    
			}); 
        },
		/*
		 *@summary 美化select选择框
		 */
		initSelect: function(){
			$(this).each(function(){		
				var _this = $(this),
					_id = _this.attr("id"),
					defValue =  '',
					defText = '',
					optList = _this.find("option"),
					optSelcted = null,
					selHtml = '',
					listHtml = '';
				if(optList.length == 0){
					return this;
				}
				if(!_id){
					_id = "sel_" + IOOP.getRandom();
					_this.attr("id", _id);
				}
				optSelcted = _this.find("option:selected");
				if(!optSelcted){
					optSelcted = _this.find("option:first");
				}
				defValue = optSelcted.attr("value");
				defText = optSelcted.text();
				defValue = !defValue ? defText : defValue;				
				selHtml = $('<div id="simu_'+ _id +'" class="simu-select"><div class="sel-cont"><span id="selval_'+ _id +'" class="sel-text">'+ defText +'</span><span class="sel-icon"></span></div></div>');
				selHtml.css("width",_this.width());
				_this.next(".simu-select").remove();
				_this.after(selHtml).hide();
				$("#slist_"+_id).remove();
				listHtml = '<div id="slist_'+ _id +'" class="simu-sel-list"><div class="sel-list-cont"><ul>';					
				optList.each(function(){
					var _optValue = $(this).attr("value"),
					    _optText = $(this).text();
					_optValue = !_optValue ? _optText : _optValue;
					if(_optValue == defValue){
						listHtml += '<li data-value="'+ defValue +'" class="isSelected" title="'+ defText +'">'+ defText +'</li>';
					}else{
						listHtml += '<li data-value="'+ _optValue +'" title="'+ _optText +'">'+ _optText +'</li>';
					}
				});
				listHtml += '</ul></div></div>';
				$("body").append(listHtml);
				
				selHtml.click(function(){
					var selObj = $(this),
						selObjOff = selObj.offset(),
						selListLeft = selObjOff.left,
						selListTop = selObjOff.top + selObj.outerHeight(),
						selListWidth = selObj.width();
					$(".simu-sel-list").hide();
					$("#slist_"+ _id).css({"width":selListWidth,"left":selListLeft,"top":selListTop}).toggle();					
					return false;
				});
				$("#slist_"+ _id +" li").click(function(){
					var simuLi = $(this),
						newValue = simuLi.attr("data-value"),
						newText = simuLi.text(),
						oldValue = _this.val();
					if(oldValue != newValue){
						$("#selval_"+ _id).text(newText);							
						simuLi.addClass("isSelected").siblings("li").removeClass("isSelected");
						_this.val(newValue).change();
					}
					$("#slist_"+ _id).hide();
					return false;
				});
			});
			return this;
		},
		/*
		 *@summary 设置虚拟select选择框的值
		 */
		setVal: function(val){
			$(this).each(function(){
				var _this = $(this),
					_id = _this.attr("id"),
					tosetLi = $("#slist_"+ _id +" li[data-value="+ val +"]"),
					oldValue = _this.val();
				if(tosetLi.length ==0){
					return this;
				}
				if(oldValue != val){
					$("#selval_"+ _id).text(tosetLi.text());
					tosetLi.addClass("isSelected").siblings("li").removeClass("isSelected");
					_this.val(val).change();
				}
			});
			return this;
		},
		/*
		 *@summary 获得虚拟select选择框的值
		 */
		getVal: function(){
			return $(this).val();
		},
		/*
		 *@summary 表单验证方法
		 */
		formValidate: function(){
			$(this).each(function(){
				var _this = $(this);
				_this.submit();				
			});
		},
		/*
		 *@summary 获得分页代码
		 *@param option,方法配置对象
		 */
		getPageHtml: function(option){
			var _option = {
					selector:"#main-content",
					totalCount: 0,
					currentPage: 1,
					totalPage: 1,
					pageSize: 12,
					url: "#",
					pageLabelTotal: 9,
					maxTotalCount: 1000,
					maxTotalPage: 100,
					showGotoPageBtn: true
				},
				_this = $(this),
				gotoSomePage = function(value){
				    var _pageNum = 1;
					if(value && /^[0-9]*$/.test(value)){
						value =  parseInt(value);
						if(value < 1){
							_pageNum = 1;
						}else if(value > _option.totalPage){
							_pageNum = _option.totalPage;
						}else{
							_pageNum = value;
						}
						IOOP.gotoNewLink({
							selector: _option.selector,
		                    url: _option.url + _pageNum
		                });
				    }else{
				    	IOOP.showalert({
	                        showTitle: "消息提示",
	                        showMsg: "跳转页码只能是数字！"
	                    });
				    }
			    };
			$.extend(_option,option);
			_option.totalCount = parseInt(_option.totalCount);
			_option.pageSize = parseInt(_option.pageSize);
			_option.currentPage = parseInt(_option.currentPage);
			_option.totalPage = parseInt(_option.totalPage);
			if(_option.totalCount <= _option.pageSize){
				return false;
			}
			
			//处理url
			if(_option.url.indexOf("&") != -1){
				//说明url含多个参数
				_option.url += "&";
			}else{
				//判断是否含参数
				if(_option.url.indexOf("?") != -1){
					//判断是否含一个参数
					if(_option.url.indexOf("=") != -1){
						_option.url += "&";
					}
				}else{
					//不含参数的url
					_option.url += "?";
				}
			}
			
			_option.url += "pageSize=" + _option.pageSize + "&pageNo=";
			
			//生成上一页标签
			if(_option.currentPage > 1){
				$('<a class="pre-page">上一页<b class="pre-arrow"></b></a>').on("click",function(){
					IOOP.gotoNewLink({
						selector: _option.selector,
	                    url: _option.url + (_option.currentPage - 1)
	                });
				}).appendTo(_this);
			}
			
			//是否将第一页显示...
			var page1 = (_option.currentPage > (_option.pageLabelTotal/2+1) && _option.totalPage > _option.pageLabelTotal) ? 
					'<a '+(_option.currentPage ==1 ? 'class="current"' : '')+'>1...</a>' : '<a '+(_option.currentPage ==1 ? 'class="current"' : '')+'>1</a>'
			$(page1).on("click",function(){
				IOOP.gotoNewLink({
					selector: _option.selector,
                    url: _option.url + '1'
                });
			}).appendTo(_this);
			
			//起始页数字计算公式
			var startPageNum = _option.currentPage <= Math.floor(_option.pageLabelTotal/2) ? 1 : (_option.currentPage - Math.floor(_option.pageLabelTotal/2));
			//结束页数字计算公式
			var endPageNum = _option.currentPage >= (_option.totalPage - Math.floor(_option.pageLabelTotal/2) ) ? 
					_option.totalPage : (_option.currentPage + Math.floor(_option.pageLabelTotal/2));
			
			//迭代具体页数
			for(var i = startPageNum + 1 ; i < endPageNum ; i++){
				$('<a '+ (_option.currentPage == i ? 'class="current"' : '') +' currentPageNo='+ i +'>' + i + '</a>').on("click",function(){
					IOOP.gotoNewLink({
						selector: _option.selector,
	                    url: _option.url + $(this).attr('currentPageNo')
	                });
				}).appendTo(_this);
			}
			
			//是否将最后一页显示...
			var pageLast = (_option.currentPage < (_option.totalPage - _option.pageLabelTotal/2 ) && _option.totalPage > _option.pageLabelTotal) ? 
					'<a '+(_option.currentPage ==_option.totalPage ? 'class="current"' : '')+'>...'+_option.totalPage+'</a>' : '<a '+(_option.currentPage ==_option.totalPage ? 'class="current"' : '')+'>'+_option.totalPage+'</a>';
			$(pageLast).on("click",function(){
				IOOP.gotoNewLink({
					selector: _option.selector,
                    url: _option.url + _option.totalPage
                });
			}).appendTo(_this);
			
			//生成下一页标签
			if( _option.currentPage < _option.totalPage ){
				$('<a class="next-page">下一页<b class="next-arrow"></b></a>').on("click",function(){
					IOOP.gotoNewLink({
						selector: _option.selector,
	                    url: _option.url + (_option.currentPage + 1)
	                });
				}).appendTo(_this);
			}

			//验证总记录数是否小于0，如果小于0则显示0，如果大于最大记录数则显示最大记录数+ ，例如：2000条记录就显示 1000+
			var tc = _option.totalCount < 0 ? 0 : (_option.totalCount >= _option.maxTotalCount ? _option.maxTotalCount + '+' : _option.totalCount);
			$('<span class="total-page">总记录数 ' + tc + '</span>').appendTo(_this);
			
			//验证总页数是否小于1，如果小于1则显示1，如果大于最大页数则显示最大数+ ，例如：200页就显示 100+
			var tp = _option.totalPage < 1 ? 1 : (_option.totalPage >= _option.maxTotalPage ? _option.maxTotalPage + '+' : _option.totalPage);
			$('<span class="total-page">总页数 '+ tp +'</span>').appendTo(_this);
			
			//判断是否显示跳转到指定页码按钮
			if(_option.showGotoPageBtn){
				$('<span class="goto-page-cono">到第</span><span class="goto-page-text"><input type="text" class="goto-page-input" /></span><span class="goto-page-cont">页</span><span class="goto-page-btn">确定</span>').appendTo(_this);
				_this.find(".goto-page-input").on("keyup",function(e){
					if(e.keyCode == 13){
					    gotoSomePage($(this).val());
					}
				});
				_this.find(".goto-page-btn").on("click", function(){
					var _pageNumInput = _this.find(".goto-page-input");
					if(_pageNumInput.length > 0){
						gotoSomePage(_pageNumInput.val());
					}else{
						return false;
					}
				});
			}
		}
	});		
})(jQuery);
/*
 *@summary 选择日期方法
 */
/*
 *@summary 只选择日期
 *@param selector,input对象或者input对象id
 */
function setDay(selector){
	var _obj = null;
	if(!selector){
		return false;
	}
	if(typeof selector == "string"){
		_obj = $("#"+selector);
	}else{
		_obj = $(selector);
	}
	_obj.datetimepicker({
		dateFormat: "yy-mm-dd",
		showTimepicker:false,
		changeYear: true,
		showButtonPanel: true,
		yearRange:"-100:+10"
	})
	.blur()
	.focus();
}
/*
 *@summary 选择日期和小时
 *@param selector,input对象或者input对象id
 */
function setDayH(selector){
	var _obj = null;
	if(!selector){
		return false;
	}
	if(typeof selector == "string"){
		_obj = $("#"+selector);
	}else{
		_obj = $(selector);
	}
	_obj.datetimepicker({
		timeFormat: "HH",
		dateFormat: "yy-mm-dd",
		changeYear: true,
		showMinute:false,
		showButtonPanel: true,
		yearRange:"-100:+10"
	})
	.blur()
	.focus();
}
/*
 *@summary 选择日期和小时及分钟
 *@param selector,input对象或者input对象id
 */
function setDayHM(selector){
	var _obj = null;
	if(!selector){
		return false;
	}
	if(typeof selector == "string"){
		_obj = $("#"+selector);
	}else{
		_obj = $(selector);
	}
	_obj.datetimepicker({
		timeFormat: "HH:mm",
		dateFormat: "yy-mm-dd",
		changeYear: true,
		showButtonPanel: true,
		yearRange:"-100:+10"
	})
	.blur()
	.focus();
}
/*
 *@summary 选择日期、小时、分钟及秒
 *@param selector,input对象或者input对象id
 */
function setDayHMs(selector){
	var _obj = null;
	if(!selector){
		return false;
	}
	if(typeof selector == "string"){
		_obj = $("#"+selector);
	}else{
		_obj = $(selector);
	}
	_obj.datetimepicker({
		timeFormat: "HH:mm:ss",
		dateFormat: "yy-mm-dd",
		changeYear: true,
		showSecond:true,
		showButtonPanel: true,
		yearRange:"-100:+10"
	})
	.blur()
	.focus();
}
/*
 *@summary 选择日期和时间
 *@param selector, input对象或者input对象id
 *@param dtFormat, 日期和时间格式，可选值： D,DH,DHM,DHMS;默认D
 *@param minDate, 最小日期
 *@param maxDate, 最大日期
 */
function setDayTime(option){
	var _option = {
            selector: "",
            dtFormat: "D",
            minDate: null,
            maxDate: null
        }
        _obj = null,
        _dtOption = {
            dateFormat: "yy-mm-dd",
            changeYear: true,
            showButtonPanel: true,
            yearRange:"-100:+10" 
        };
    $.extend(_option, option);
	if(!_option.selector){
		return false;
	}
	if(typeof _option.selector == "string"){
		_obj = $("#"+_option.selector);
	}else{
		_obj = $(_option.selector);
	}
    switch(_option.dtFormat){
        case "DH":
            _dtOption.timeFormat = "HH";
            _dtOption.showMinute = false;
            break;
        case "DHM":
            _dtOption.timeFormat = "HH:mm";
            break;
        case "DHMS":
            _dtOption.timeFormat = "HH:mm:ss";
            _dtOption.showSecond = true;
            break;
        case "D":
        default:
            _dtOption.showTimepicker = false;
    }
    _dtOption.minDate = _option.minDate;
    _dtOption.maxDate = _option.maxDate;
	_obj.datetimepicker(_dtOption)
	.blur()
	.focus();
}
/*
 *多附件上传封装类
 */	 
function FileMultiUpload(option){
	this.option = {
		formData: {"bizId": "", "appCode": "", "orgId": "", sessionId: ""},
		fileEleId: "", //文件上传元素id
		fileListId: "", //附件列表容器id
		url: "sys/file-upload!uploadFile.do", //附件上传地址
		delUrl: "sys/file-upload!deleteFile.do", //附件删除地址
		delParamKey: "relationId",
		enableClear: true,
		width: 60,
		height: 34,
		buttonClass: "file-upload",
		buttonText: '添加附件',
		fileObjName: 'uploads',
		fileSizeLimit: "50MB", //单个文件大小，默认50M
		fileUploadError: null, //文件上传错误回调方法
		fileUploadSuccess: null //文件上传成功处理方法
	};
	$.extend(this.option, option);
	this.init();
}
FileMultiUpload.prototype = {
	init: function(){
		var _this = this,
		    _option = this.option;
		if(!_option.fileEleId || !_option.fileListId){
			return false;
		}
		if(_option.enableClear){
            uploadList.push(_option.fileEleId);
        }
		$("#"+_option.fileEleId).uploadify({
			'width': _option.width,
			'height': _option.height,
			'swf': 'res/skin/default/plugin/uploadify/uploadify.swf',
			'uploader': _option.url + ';jsessionid=' + IOOP.option.sessionId,
			'queueID': _option.fileListId,
			'buttonClass': _option.buttonClass,
			'buttonText': _option.buttonText,
			'multi': true,
			'fileObjName': _option.fileObjName,
			'fileSizeLimit': _option.fileSizeLimit,
			'formData': _option.formData,
			'itemTemplate': '<li id="\${fileID}" class="file-uploading"><span class="attch-name">\${fileName}</span><span class="attch-size">\${fileSize}</span><span class="attch-state">(0%)</span><div class="attch-progress"><div class="progress-bar"></div></div></li>',
			'onSelect': function(file) {
				$("#"+_option.fileListId).show();
				$("#"+file.id).find('.attch-progress').show();
			},
			'onSelectError': function(file, errorCode) {
				switch(errorCode){
				    case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "亲，单个附件大小最大为"+_option.fileSizeLimit,
							alertType: "alert",
							btnOKText: "确定"
						});
					    break;
					case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "亲，允许上传的文件类型为"+_option.fileTypeExts,
							alertType: "alert",
							btnOKText: "确定"
						});
					    break; 
				    default:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "上传失败，请您检查上传的文件",
							alertType: "alert",
							btnOKText: "确定"
						});  
				}
				
            },
			'onUploadError': function(file, errorCode, errorMsg, errorString) {
				if(typeof _option.fileUploadError == "function"){
					_option.fileUploadError();
				}
				$('#'+file.id).remove();
			},
			'onUploadProgress': function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
				var percentage = Math.round(bytesUploaded / bytesTotal * 100);
				$("#"+file.id).find(".attch-state").text('(' + percentage + '%)').end().find('.progress-bar').css('width', percentage + '%');
			},
			'onUploadSuccess': function(file, data, response) {
				var resObj = null,
			        _index = 0,
			        _enableView = true,
			        _fileHtml = [];
				try{
					if(data.indexOf(";location.href=")>-1){
						eval(data.replace(/[\n\r]/g,'').replace(/<script.*?>(.*?)<\/.*?>/mi,'$1'));
						return false;
					}
				    resObj = $.parseJSON(data);
				}catch(e){}
				_index = file.name.lastIndexOf(".");
				if(_index > 0){
					_enableView = IOOP.option.enViewSuffix.indexOf(file.name.substring(_index+1).toLowerCase()) > -1;
				}else{
					_enableView = false;
				}
				_fileHtml.push('<li id="file_'+ file.id +'">');
				_fileHtml.push('<span class="attch-name"><a href="'+ resObj.fileUrl +'" target="_blank" title="'+ file.name +'">'+ fileUpload.parseFileName(file.name,100) +'</a></span>');
				_fileHtml.push('<span class="attch-size">'+ fileUpload.parseFileSize(file.size) +'</span><span class="attch-state">(完成)</span>');
				_fileHtml.push('<a class="attch-dload" href="'+ resObj.fileUrl +'" target="_blank">下载</a>');
				if(_enableView){
					_fileHtml.push('<a class="attch-view" href="'+ IOOP.option.viewUrl + resObj.relationId +'" target="_blank">预览</a>');
				}
				_fileHtml.push('<a class="attch-delete">删除</a>');
				_fileHtml.push('</li>');
				$(_fileHtml.join(""))
					.find(".attch-delete").on("click", function(){
						_this.deleteFile(resObj.relationId,this);
					})
					.end().appendTo($("#"+_option.fileListId));
			
				if(typeof _option.fileUploadSuccess == "function"){
					_option.fileUploadSuccess(resObj);
				}		
			}
		});
		//添加未安装flash组件提示信息
        $("#"+_option.fileEleId).after('<div class="fileup-help"><a href="http://get.adobe.com/cn/flashplayer/" target="_blank">无法上传?</a></div>');
	},
	//删除附件
	deleteFile: function(relid,obj){
		var _option = this.option;
		if(!relid || !obj){
			return false;
		}
		$.getJSON(_option.delUrl + "?"+ _option.delParamKey +"=" + relid,function(data){
			if(data.success == "true"){
				$(obj).parent().remove();
				if($("#"+_option.fileListId + " li").length == 0){
					$("#"+_option.fileListId).hide();
				}
			}
		});
	},
	//设置formdata
	setFormData: function(dataObj){ 
		if(typeof dataObj == "object"){
			this.option.formData = dataObj;
			$("#"+this.option.fileEleId).uploadify('settings', 'formData', dataObj);
		}else{
			return false;
		}
	}
};

/*
 *多附件上传封装类
 */	 
function DocumentFileUpload(option){
	this.option = {
		formData: {"folderId": "", "appCode": "", "groupId": ""},
		fileEleId: "", //文件上传元素id
		fileListId: "", //附件列表容器id
		url: "document/document-file!uploadFile.do", //附件上传地址
		delUrl: "document/document-file!deleteDocumFile.do", //附件删除地址
		delParamKey: "documentId",
		width: 60,
		height: 34,
		buttonClass: "file-upload",
		buttonText: '上传文件',
		fileObjName: 'uploads',
		fileSizeLimit: "500MB", //单个文件大小，默认500M
		fileUploadError: null, //文件上传错误回调方法
		fileUploadSuccess: null //文件上传成功处理方法
	};
	$.extend(this.option, option);
	this.init();
}
/*
 * 文档上传
 */
DocumentFileUpload.prototype = {
	init: function(){
		var _this = this,
		    _option = this.option;
		if(!_option.fileEleId || !_option.fileListId){
			return false;
		}
		uploadList.push(_option.fileEleId);
		$("#"+_option.fileEleId).uploadify({
			'width': _option.width,
			'height': _option.height,
			'swf': 'res/skin/default/plugin/uploadify/uploadify.swf',
			'uploader': _option.url + ';jsessionid=' + IOOP.option.sessionId,
			'queueID': _option.fileListId,
			'buttonClass': _option.buttonClass,
			'buttonText': _option.buttonText,
			'multi': true,
			'fileObjName': _option.fileObjName,
			'fileSizeLimit': _option.fileSizeLimit,
			'formData': _option.formData,
			'itemTemplate': '<li id="\${fileID}" class="file-uploading"><span class="attch-name">\${fileName}</span><span class="attch-size">\${fileSize}</span><span class="attch-state">(0%)</span><div class="attch-progress"><div class="progress-bar"></div></div></li>',
			'onSelect': function(file) {
				$("#"+_option.fileListId).show();
				$("#"+file.id).find('.attch-progress').show();
			},
			'onSelectError': function(file, errorCode) {
				switch(errorCode){
				    case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "亲，单个附件大小最大为"+_option.fileSizeLimit,
							alertType: "alert",
							btnOKText: "确定"
						});
					    break;
					case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "亲，允许上传的文件类型为"+_option.fileTypeExts,
							alertType: "alert",
							btnOKText: "确定"
						});
					    break; 
				    default:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "上传失败，请您检查上传的文件",
							alertType: "alert",
							btnOKText: "确定"
						});  
				}
				
            },
            'onUploadStart': function(file) {
            	$("#"+_option.fileEleId).uploadify('settings', 'formData', _option.formData);
            },
			'onUploadError': function(file, errorCode, errorMsg, errorString) {
				if(typeof _option.fileUploadError == "function"){
					_option.fileUploadError();
				}
				$('#'+file.id).remove();
			},
			'onUploadProgress': function(file, bytesUploaded, bytesTotal, totalBytesUploaded, totalBytesTotal) {
				var percentage = Math.round(bytesUploaded / bytesTotal * 100);
				$("#"+file.id).find(".attch-state").text('(' + percentage + '%)').end().find('.progress-bar').css('width', percentage + '%');
			},
			'onUploadSuccess': function(file, data, response) {
				var resObj = null,
			        _index = 0,
			        _enableView = true,
			        _fileHtml = [];
				try{
					if(data.indexOf(";location.href=")>-1){
						eval(data.replace(/[\n\r]/g,'').replace(/<script.*?>(.*?)<\/.*?>/mi,'$1'));
						return false;
					}
				    resObj = $.parseJSON(data);
				}catch(e){}
				_index = file.name.lastIndexOf(".");
				if(_index > 0){
					_enableView = IOOP.option.enViewSuffix.indexOf(file.name.substring(_index+1).toLowerCase()) > -1;
				}else{
					_enableView = false;
				}
				//添加后台错误信息提示
				if(resObj.vo.success == '0'){
					 IOOP.showalert({
							showTitle: "消息提示",
							showMsg: resObj.vo.msg,
							alertType: "alert",
							btnOKText: "确定"
						});
					 $("#"+_option.fileListId).hide();
					 return false;
				}
				_fileHtml.push('<li id="file_'+ file.id +'">');
				_fileHtml.push('<span class="attch-name"><a href="'+ resObj.fileWebUrl +'" target="_blank" title="'+ file.name +'">'+ fileUpload.parseFileName(file.name,100) +'</a></span>');
				_fileHtml.push('<span class="attch-size">'+ fileUpload.parseFileSize(file.size) +'</span><span class="attch-state">(完成)</span>');
				_fileHtml.push('<a class="attch-dload" href="'+ resObj.fileWebUrl +'" target="_blank">下载</a>');
				if(_enableView){
				//	_fileHtml.push('<a class="attch-view" href="'+ IOOP.option.viewUrl + resObj.relationId +'" target="_blank">预览</a>');
				}
				_fileHtml.push('<a class="attch-delete">删除</a>');
				_fileHtml.push('</li>');
				$(_fileHtml.join(""))
					.find(".attch-delete").on("click", function(){
						_this.deleteFile(resObj.documentId,this);
					})
					.end().appendTo($("#"+_option.fileListId));
			
				if(typeof _option.fileUploadSuccess == "function"){
					_option.fileUploadSuccess(resObj);
				}		
			}
		});
	},
	//删除附件
	deleteFile: function(relid,obj){
		var _option = this.option;
		if(!relid || !obj){
			return false;
		}
		$.getJSON(_option.delUrl + "?"+ _option.delParamKey +"=" + relid,function(data){
			if(data.success == "true"){
				$(obj).parent().remove();
				if($("#"+_option.fileListId + " li").length == 0){
					$("#"+_option.fileListId).hide();
				}
			}
		});
	},
	//设置formdata
	setFormData: function(dataObj){ 
		if(typeof dataObj == "object"){
			this.option.formData = dataObj;
		}else{
			return false;
		}
	}
};
/*
 *单附件上传封装类
 */
function FileSingleImgUpload(option){
	this.option = {
	    formData: {"bizId": "", "appCode": "", "orgId": ""},
		fileEleId: "", //文件上传元素id
		url: "sys/file-upload!uploadFile.do", //附件上传地址
		width: 70,
		height: 24,
		buttonClass: "upload-text",
		buttonText: '上传图片',
		fileObjName: 'uploads',
		fileTypeExts: '*.gif; *.jpg; *.png',
		fileSizeLimit: "2MB",
		fileUploadError: null,
		fileUploadSuccess: null
	};
	$.extend(this.option, option);
	this.init();
}
FileSingleImgUpload.prototype = {
	init: function(){
		var _this = this,
		    _option = this.option;
		if(!_option.fileEleId){
			return false;
		}
		if($("#"+ _option.fileEleId + "_temp_list").length == 0){
			$("#"+_option.fileEleId).after('<div id='+ _option.fileEleId + '_temp_list' +' style="display:none;"></div>');
		}
		uploadList.push(_option.fileEleId);
		$("#"+_option.fileEleId).uploadify({
			'width': _option.width,
			'height': _option.height,
			'swf': 'res/skin/default/plugin/uploadify/uploadify.swf',
			'uploader': _option.url + ';jsessionid=' + IOOP.option.sessionId,
			'queueID': _option.fileEleId + '_temp_list',
			'buttonClass': _option.buttonClass,
			'buttonText': _option.buttonText,
			'multi': false,
			'fileObjName': _option.fileObjName,
			'fileSizeLimit': _option.fileSizeLimit,
			'formData': _option.formData,
			'fileTypeExts': _option.fileTypeExts,
			'itemTemplate': '<span id="\${fileID}" class="attch-name">\${fileName}</span>',
			'onSelect': function(file) {
				$("#"+_option.fileEleId).uploadify('settings','buttonText','上传中');
				$("#"+_option.fileEleId).uploadify('disable', true);
			},
			'onSelectError': function(file, errorCode) {
				switch(errorCode){
				    case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "亲，单个附件大小最大为"+_option.fileSizeLimit,
							alertType: "alert",
							btnOKText: "确定"
						});
					    break;
					case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "亲，允许上传的文件类型为"+_option.fileTypeExts,
							alertType: "alert",
							btnOKText: "确定"
						});
					    break; 
				    default:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "上传失败，请您检查上传的文件",
							alertType: "alert",
							btnOKText: "确定"
						}); 
				    if(typeof _option.fileUploadError == "function"){
						_option.fileUploadError();
					}
				}
				
            },
			'onUploadError': function(file, errorCode, errorMsg, errorString) {
				if(typeof _option.fileUploadError == "function"){
					_option.fileUploadError();
				}
				$('#'+file.id).remove();
				$("#"+_option.fileEleId).uploadify('settings','buttonText',_option.buttonText);
				$("#"+_option.fileEleId).uploadify('disable', false);
			},
			'onUploadSuccess': function(file, data, response) {
				var resObj = null;
				try{
					if(data.indexOf(";location.href=")>-1){
						eval(data.replace(/[\n\r]/g,'').replace(/<script.*?>(.*?)<\/.*?>/mi,'$1'));
						return false;
					}
				    resObj = $.parseJSON(data);
				}catch(e){}
                $("#"+_option.fileEleId).uploadify('settings','buttonText',_option.buttonText);
				$("#"+_option.fileEleId).uploadify('disable', false);		
				if(typeof _option.fileUploadSuccess == "function"){
					_option.fileUploadSuccess(resObj);
				}		
			}
		});
	}
};
/*
 *首页动态多图片上传封装类
 */
function FileMultiImgUpload(option){
	this.option = {
	    formData: {"bizId": "", "appCode": "", "orgId": ""},
	    url: "sys/file-upload!uploadFile.do", //附件上传地址
		delUrl: "sys/file-upload!deleteFile.do", //附件删除地址
		fileTypeExts: '*.gif; *.jpg; *.png',
		fileSizeLimit: "2MB",
		maxCount: 6  //最大上传图片数量
	};
	$.extend(this.option, option);
	this.init();
}
FileMultiImgUpload.prototype = {
	init: function(){
		var _this = this,
		    _option = this.option;
		$("#img_num_isup").text("0");
		$("#img_num_remd").text(_option.maxCount);
		uploadList.push("dyimg_item_state");
		$("#dyimg_item_state").uploadify({
			'width': 78,
			'height': 78,
			'swf': 'res/skin/default/plugin/uploadify/uploadify.swf',
			'uploader': _option.url + ';jsessionid=' + IOOP.option.sessionId,
			'queueID': 'dyimg_items',
			'buttonClass': 'dyimg-item-state',
			'buttonText': '+',
			'multi': true,
			'fileObjName': 'uploads',
			'fileSizeLimit': _option.fileSizeLimit,
			'queueSizeLimit': _option.maxCount,
			'formData': _option.formData,
			'fileTypeExts': _option.fileTypeExts,
			'itemTemplate': '<li id="\${fileID}" class="dynamic-img file-uploading"><span class="dyimg-item-bg"></span><div class="dyimg-item-cont"></div><b class="dyimg-item-del"></b></li>',
			'onSelect': function(file) {
			    $('#'+ file.id).btnLoading();
				if($("#dyimg_items li").length > _option.maxCount) {
					this.cancelUpload(file.id);
					this.queueData.filesCancelled++;
					this.queueData.queueSize -= file.size;
			        delete this.queueData.files[file.id];					
					IOOP.showalert({
						showTitle: "消息提示",
						showMsg: "亲，最多允许上传"+_option.maxCount+"个图片",
						alertType: "alert",
						btnOKText: "确定"
					});
					return false;
				}
			},
			'onSelectError': function(file, errorCode) {
				switch(errorCode){
					case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "亲，最多允许上传"+_option.maxCount+"个图片",
							alertType: "alert",
							btnOKText: "确定"
						});
					    break;
				    case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "亲，单个附件大小最大为"+_option.fileSizeLimit,
							alertType: "alert",
							btnOKText: "确定"
						});
					    break;
					case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "亲，允许上传的文件类型为"+_option.fileTypeExts,
							alertType: "alert",
							btnOKText: "确定"
						});
					    break; 
				    default:
					    IOOP.showalert({
							showTitle: "消息提示",
							showMsg: "上传失败，请您检查上传的文件",
							alertType: "alert",
							btnOKText: "确定"
						});  
				}
            },
            'onUploadStart': function(file) {
            	$("#dyimg_item_state").uploadify('settings', 'formData', _option.formData);
            },
			'onUploadError': function(file, errorCode, errorMsg, errorString) {
				$('#'+file.id).remove();
			},
			'onUploadSuccess': function(file, data, response) {
				var resObj = null,
				    isupNum = $("#dyimg_items li").length,
					desId = $("#img_upload_dynamic").attr("descId"),
					desObj = null;
				try{
					if(data.indexOf(";location.href=")>-1){
						eval(data.replace(/[\n\r]/g,'').replace(/<script.*?>(.*?)<\/.*?>/mi,'$1'));
						return false;
					}
				    resObj = $.parseJSON(data);
				}catch(e){}
				
				$('<li id="file_'+ file.id +'" class="dynamic-img" imgurl="'+ resObj.fileUrl +'"><span class="dyimg-item-bg"></span><div class="dyimg-item-cont" style="background-image:url('+ resObj.fileUrl +')"></div><b class="dyimg-item-del"></b></li>')	
   				    .find(".dyimg-item-del").on("click", function(){
					     _this.deleteFile(resObj.relationId, this);
					})
					.end().appendTo($("#dyimg_items"));
									
				$("#img_num_isup").text(isupNum);
				$("#img_num_remd").text(_option.maxCount-isupNum);
				if(desId && $("#"+desId).length>0){
					desObj = $("#"+desId);
					if(!desObj.val()){
						desObj.val("分享图片");
					}
					desObj = null;
				}
				$("#dyimg_items").sortable({
					items: 'li.dynamic-img',
					opacity: "0.6",
					placeholder: "img-sort-ph"
				}); 	
			}
		});
	},
	//删除附件
	deleteFile: function(relid,obj){
		var _option = this.option, isupNum = 0;
		if(!relid || !obj){
			return false;
		}
		$.getJSON(_option.delUrl + "?relationId=" + relid,function(data){
			if(data.success == "true"){
				$(obj).parent().remove();
				isupNum = $("#dyimg_items li").length;
                $("#img_num_isup").text(isupNum);
		        $("#img_num_remd").text(_option.maxCount - isupNum);
			}
		});
	},
	//设置formdata
	setFormData: function(dataObj){
		if(typeof dataObj == "object"){
			this.option.formData = dataObj;
		}else{
			return false;
		}
	}
};
var innerMail = innerMail || {};
innerMail.loadEdit = function(url,titleId,uploadId){
	if(!titleId) titleId = "";
	if(!uploadId) uploadId = "";
    mailWinOpertate.open({
        url: url,
        titleId: titleId,
        uploadId: uploadId,
        onloadHandler: function(){
        	var mailTitle=$("#mailForm #mailTitle").val();
        	if(!IOOP.isBlank(mailTitle)){
        		$("#add_mail_title").text(mailTitle);
        		$("#amail_title_min").text(mailTitle);
        	}else{
        		$("#add_mail_title").text("新邮件");
        		$("#amail_title_min").text("新邮件");
        	}
        },
        onminHandler:function(){
        	if(mailSaveDraftId){
        		window.clearInterval(mailSaveDraftId);
        		mailSaveDraftId = null;
        	};
        },
        onmaxHandler:function(){
        	if(!mailSaveDraftId){
        		mailSaveDraftId = window.setInterval(timesSaveDraft,180000);
        	}
        },
        oncloseHandler:function(){
        	if(mailSaveDraftId){
        		window.clearInterval(mailSaveDraftId);
        	};
        	$("#add_mail_title").text("新邮件");
        }
    });
};
/*
 * @summary 在线服务
 */
var serverStr= "", winObj= null;
//显示在线服务窗口
function showServiceDialog(){
  var winWidth = 590,
      winHight = 600,
      winLeft = ($(window).width()-winWidth)/2,
      winTop = ($(window).height()-winHight)/2;
  winLeft = winLeft>0?winLeft:0;
  winTop = winTop>0?winTop:0;
  if(!serverStr){
      serverStr = "?account=09314644557&UserName=&UserPhone=&AreaCode=&strType=web";
  }
  if(winObj && !winObj.closed){
	    winObj.focus();
	}else{
      winObj = window.open("http://web.msjbm.cn/oem/nma/pc_index.html"+serverStr+"&ranId="+Math.random(),"综合办公在线服务","width="+winWidth+",height="+winHight+",top="+winTop+",left="+winLeft+",toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no,status=no");   
	}
}
/*
 *@summary 政务版首页自定义模块相关方法
 */
var govIndex = {
    /*
     *@summary 跳转到app方法
     */
    gotoApp: function(option){
        var _option = {
            appCode: "",
            action: "",
            appData: {}
        };
        $.extend(_option, option);
        if(_option.appCode && _option.action){
            IOOP.gotoNewLink({
                url: _option.action,
                data: _option.appData
            });
        }
    },
    /*
     *@summary 跳转到app下子功能方法
     */
    gotoModule: function(moduId){
        if(!moduId){
            return false;
        }
        var mapObj = IOOP.urlMapping[moduId];
        if(mapObj){
            IOOP.gotoNewLink({
                url: mapObj.url,
                data: mapObj.data
            });	
        }
    },
    /*
     *@summary 跳转到子功能数据详情方法
     */
    gotoModuleDetail: function(detailObj){
        if(!detailObj){
            return false;
        }
        var _this = $(detailObj),
            _id = _this.attr("id"),
            _type = _this.attr("type");
        switch(_type){
            case "mail":
                var rId = _this.attr("rid");
                IOOP.gotoNewLink({
                    url: "mail/mail-info!getMail.do",
                    data: {"mailRelationId": rId, "mailId": _id}
                });
                break;
            case "info":
                var tid = _this.attr("tid"),
                    rstate = _this.attr("rstate");
                IOOP.gotoNewLink({
                   url: "info/e-bulletin!indexView.do",
                   data: {"bulletinId": _id, "typeId": tid, "readState": rstate}
                });
                break;
            case "meeting":
                IOOP.gotoNewLink({
                    url: "meeting/meeting-info!indexView.do",
                    data: {"mId": _id}
                });
                break;
            case "docin":
                var taskId = _this.attr("taskId");
                IOOP.gotoNewLink({
                    url: "doc/doc-in!input.do",
                    data: {"id": _id, "taskId": taskId}
                });
                break;
            case "docout":
                var taskId = _this.attr("taskId");
                IOOP.gotoNewLink({
                    url: "doc/doc-out!input.do",
                    data: {"id": _id, "taskId": taskId}
                });
                break;
            case "coll":
                var stateView = _this.attr("stateView"),
                    taskId = _this.attr("taskId");
                IOOP.gotoNewLink({
                    url: "collaborate/collaborate-document!gotoEditCollaborateDocument.do",
                    data: {"documentId": _id, "currentTaskId": taskId, "state": stateView, "returnUrl": "wait"}
                });
                break;            
            case "task":
                IOOP.gotoNewLink({
                    url: "task/task-info!view.do",
                    data: {"taskId": _id, "taskType": "00B", "pageNo": 1}
                });
                break;
            case "plan":
        		var pType = _this.attr("ptype");
        		IOOP.gotoNewLink({
        			url: "plan/plan!showDetail.do",
        			data: {id: _id, type: pType, pState: "1", curNo: 1, pType: "s", myOrReceive:"receive"}
        		});
            	break;
            case "report":
            	IOOP.gotoNewLink({
        			url: "report/received-report!reportDetail.do",
        			data: {"reportId": _id, "auditState": "1"}
        		});
            	break;
            case "survey":
            	IOOP.gotoNewLink({
            		url: "survey/my-attended-survey!view.do",
            		data: {"ss.surveyId": _id, "keyword":""}
            	});
            	break;
            case "vote":
            	IOOP.gotoNewLink({
            		url: "vote/vote!list.do",
            		data: {"voteId": _id, "state":"2"}
            	});
            	break;
            default:
                _this = null;
                return true;
        }
    },
    /*
     *@summary 获取子功能数据方法
     */
    getModuleData: function(appData){
    	var _appData = [{"appId": "1100", "appName": "通知公告", "appType": ""}, {"appId": "1000","appName": "我的邮件", "appType": ""}, {"appId": "1300","appName": "我的会议", "appType": ""}, {"appId": "1600","appName": "我的审批", "appType": ""}],
    	    _userRightSet = IOOP.option.govIndex.indexModule.rightSet;
    	if(!_userRightSet){
    		return false;
    	}
    	if(!!appData && appData.length > 10){
    		try{
        		_appData = eval(appData);
        	}catch(e){}
    	}
        $(_appData).each(function(index, ele){
            var _appId = ele.appId;
            if(_userRightSet.indexOf(_appId) == -1 && !(_appId.indexOf("15") == 0 && _userRightSet.indexOf("1500") > -1)){
            	return true;
            }
            switch(_appId){
                //邮件
                case "1000":
                    var _action = "mail/mail-info!ReceiveList.do";
                    $("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="1000" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_1000","action":"'+ _action +'"});\'>more</span><span class="modu-item-icon mitem-icon-1000"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_mail" class="gtitem-list-modu"><b class="ioop-btn-mask"></b></div></div>');
                    $.getJSON("main!getModuleList.do", {"moduleId": "mail", "moduleState": "2"}, function(data){
                        var mdata = [];
                        mdata.push('<ul>');
                        $(data).each(function(key, ele){
                            mdata.push('<li><div class="gtitem-modu-cont">');
                            mdata.push('<a id="'+ ele.mailId +'" type="mail" rid="'+ ele.mailRelId +'" class="gtitem-modu-title over-flow'+ (ele.readState == "0" ? " font-bold" : "") +'" onclick="govIndex.gotoModuleDetail(this);">');                        
                            mdata.push(ele.mailTitle + '</a>');
                            mdata.push('<span class="gtitem-modu-time">'+ ele.mailTime.substring(5, 10) +'</span>');
                            mdata.push('</div></li>');
                        });
                        mdata.push('</ul>');
                        $("#wstate_mitem_mail").html(mdata.join(""));
                        IOOP.isNoResult({
                            contSelor: "#wstate_mitem_mail ul",
                            itemSelor: "li",
                            showSize: "middle"
                        });
                    });
                    break;
                //公告
                case "1100":
                    var _appType = ele.appType,
                        _appContainer = "wstate_mitem_info",
                        _action = "info/e-bulletin!initMenu.do";                        
                    if(_appType){
                        _appContainer = "wstate_mitem_info_" + _appType;
                        _action = "info/e-bulletin!indexMenuClick.do";
                    }
                    $("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="1100" typeid="'+ _appType +'" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_1100","action":"'+ _action +'","appData":{"option":"list","typeId":"'+ _appType +'"}});\'>more</span><span class="modu-item-icon mitem-icon-1100"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="'+ _appContainer +'" class="gtitem-list-modu"><b class="ioop-btn-mask"></b></div></div>');
                    $.getJSON("main!getModuleList.do", {"moduleId": "info", "moduleState": "", "moduleType": _appType}, function(data){
                        var mdata = [];
                        mdata.push('<ul>');
                        $(data).each(function(key, ele){
                            mdata.push('<li><div class="gtitem-modu-cont">');
                            mdata.push('<a id="'+ ele.infoId +'" type="info" tid="'+ ele.infoTypeId +'" rstate="" class="gtitem-modu-title over-flow'+ (ele.readState == "0" ? " font-bold" : "") +'" onclick="govIndex.gotoModuleDetail(this);">');
                            if(!_appType){
                                mdata.push('<span class="gtitem-modu-type">['+ ele.infoType +']</span>');
                            }
                            mdata.push(ele.infoTitle + '</a>');
                            mdata.push('<span class="gtitem-modu-time">'+ ele.infoTime.substring(5, 10) +'</span>');
                            mdata.push('</div></li>');
                        });
                        mdata.push('</ul>');
                        $("#"+_appContainer).html(mdata.join(""));
                        IOOP.isNoResult({
                            contSelor: "#"+ _appContainer +" ul",
                            itemSelor: "li",
                            showSize: "middle"
                        });
                    });
                    break;
                //会议
                case "1300":
                    var _action = "meeting/meeting-info!getAllMyM.do";
                    $("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="1300" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_1300","action":"'+ _action +'"});\'>more</span><span class="modu-item-icon mitem-icon-1300"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_meet" class="gtitem-modu-multi"><b class="ioop-btn-mask"></b></div></div>');
                    $.getJSON("main!getModuleList.do", {"moduleId": "meeting", "moduleState": "0", "dataCount": "4"}, function(data){
                        var mdata = [];
                        mdata.push('<ul>');            
                        $(data).each(function(key, ele){
                            mdata.push('<li>');
                            mdata.push('<div id="'+ ele.meetId +'" type="meeting" class="gtitem-multi-title over-flow" onclick="govIndex.gotoModuleDetail(this);">');
                            mdata.push(ele.meetTitle + '</div>');
                            mdata.push('<div class="gtitem-multi-info clear">');
                            mdata.push('<div class="gtitem-minfo-time">时间：<span class="gtitem-minfo-date">'+ ele.meetDate.substring(0, 10) +'</span>'+ ele.meetStartTime.substring(0, 5) +'-'+ ele.meetEndTime.substring(0, 5) +'</div>');
                            mdata.push('<div class="gtitem-minfo-room over-flow">地点：'+ ele.meetRoomName +'</div>');
                            mdata.push('</div></li>');
                        });            
                        mdata.push('</ul>');
                        $("#wstate_mitem_meet").html(mdata.join(""));
                        IOOP.isNoResult({
                            contSelor: "#wstate_mitem_meet ul",
                            itemSelor: "li",
                            showSize: "middle"
                        });
                    });
                    break;
                //收文
                case "1501":
                	$("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="1501" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoModule("m_docin_list");\'>more</span><span class="modu-item-icon mitem-icon-1500"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_docin" class="gtitem-modu-multi"><b class="ioop-btn-mask"></b></div></div>');
                	$.getJSON("main!getModuleList.do", {"moduleId": "docin", "moduleState": "0"}, function(data){
                        var mdata = [];
                        mdata.push('<ul>');
                        $(data).each(function(key, ele){
                        	mdata.push('<li><div class="gtitem-modu-cont">');
                            mdata.push('<a id="'+ ele.docId +'" type="docin" taskId="'+ ele.taskId +'" class="gtitem-modu-title over-flow" onclick="govIndex.gotoModuleDetail(this);">');
                            mdata.push(ele.docTitle + '</a>');
                            mdata.push('<span class="gtitem-modu-time">'+ ele.docTime.substring(5, 10) +'</span>');
                            mdata.push('</div></li>');
                        });
                        mdata.push('</ul>');
                        $("#wstate_mitem_docin").html(mdata.join(""));
                        IOOP.isNoResult({
                            contSelor: "#wstate_mitem_docin ul",
                            itemSelor: "li",
                            showSize: "middle"
                        });
                    });
                	break;
                //发文
                case "1502":
                	$("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="1502" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoModule("m_docout_list");\'>more</span><span class="modu-item-icon mitem-icon-1500"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_docout" class="gtitem-modu-multi"><b class="ioop-btn-mask"></b></div></div>');
                	$.getJSON("main!getModuleList.do", {"moduleId": "docout", "moduleState": "0"}, function(data){
                        var mdata = [];
                        mdata.push('<ul>');
                        $(data).each(function(key, ele){
                        	mdata.push('<li><div class="gtitem-modu-cont">');
                            mdata.push('<a id="'+ ele.docId +'" type="docout" taskId="'+ ele.taskId +'" class="gtitem-modu-title over-flow" onclick="govIndex.gotoModuleDetail(this);">');
                            mdata.push(ele.docTitle + '</a>');
                            mdata.push('<span class="gtitem-modu-time">'+ ele.docTime.substring(5, 10) +'</span>');
                            mdata.push('</div></li>');
                        });
                        mdata.push('</ul>');
                        $("#wstate_mitem_docout").html(mdata.join(""));
                        IOOP.isNoResult({
                            contSelor: "#wstate_mitem_docout ul",
                            itemSelor: "li",
                            showSize: "middle"
                        });
                    });
                	break;
                //审批
                case "1600":
                    var _appType = ele.appType,
                        _appContainer = "wstate_mitem_coll",
                        _action = "collaborate/collaborate-search!collborateWaitList.do";
                    if(_appType){
                        _appContainer = "wstate_mitem_coll_" + _appType;
                    }
                    $("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="1600" typeid="'+ _appType +'" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_1600","action":"'+ _action +'","appData":{"temType":"'+ _appType +'"}});\'>more</span><span class="modu-item-icon mitem-icon-1600"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="'+ _appContainer +'" class="gtitem-list-modu"><b class="ioop-btn-mask"></b></div></div>');
                    $.getJSON("main!getModuleList.do", {"moduleId": "coll", "moduleState": "0", "moduleType": _appType}, function(data){
                        var mdata = [];
                        mdata.push('<ul>');
                        $(data).each(function(key, ele){
                            mdata.push('<li><div class="gtitem-modu-cont">');
                            mdata.push('<a id="'+ ele.collId +'" type="coll" stateView="'+ ele.stateView +'" taskId="'+ ele.taskId +'" class="gtitem-modu-title over-flow" onclick="govIndex.gotoModuleDetail(this);">');
                            mdata.push(ele.collTitle + '</a>');
                            mdata.push('<span class="gtitem-modu-time">'+ ele.collTime.substring(5, 10) +'</span>');
                            mdata.push('</div></li>');
                        });            
                        mdata.push('</ul>');
                        $("#"+_appContainer).html(mdata.join(""));
                        IOOP.isNoResult({
                            contSelor: "#"+ _appContainer +" ul",
                            itemSelor: "li",
                            showSize: "middle"
                        });
                    });
                    break;
                //任务
                case "1700":
                	var _action = "task/task-info!list.do";
                	$("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="1700" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_1700","action":"'+ _action +'","appData":{"taskType":"00B"}});\'>more</span><span class="modu-item-icon mitem-icon-1700"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_task" class="gtitem-modu-multi"><b class="ioop-btn-mask"></b></div></div>');
                	$.getJSON("main!getModuleList.do", {"moduleId": "task", "moduleState": "0", "dataCount": "4"}, function(data){
                        var mdata = [];
                        mdata.push('<ul>');            
                        $(data).each(function(key, ele){
                        	mdata.push('<li><div class="gtitem-multi-title over-flow" id="'+ ele.taskId +'" type="task" onclick="govIndex.gotoModuleDetail(this);">'+ ele.taskTitle +'</div>');
                        	mdata.push('<div class="gtitem-multi-info clear"><div class="gtitem-minfo-ftime">要求完成时间：<span class="gtitem-ftime-time">'+ ele.taskEndTime +'</span></div>');
                        	mdata.push('<div class="gtitem-mpro-info clear"><div class="gtitem-mpinfo-head">进度：</div>');
                        	mdata.push('<div class="gtitem-mpinfo-bar"><div class="gtitem-mpinfo-progress" style="width: '+ ele.taskPercent +'%;"></div></div><div class="gtitem-mpinfo-percent">'+ ele.taskPercent +'%</div>');
                        	mdata.push('</div></div></li>');
                        });            
                        mdata.push('</ul>');
                        $("#wstate_mitem_task").html(mdata.join(""));
                        IOOP.isNoResult({
                            contSelor: "#wstate_mitem_task ul",
                            itemSelor: "li",
                            showSize: "middle"
                        });
                    });
                	break;
                //计划
                case "1800":
                	var _action = "plan/plan!list.do";
                	$("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="1800" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_1800","action":"'+ _action +'","appData":{"pState":"1","type":""}});\'>more</span><span class="modu-item-icon mitem-icon-1800"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_plan" class="gtitem-list-modu"><b class="ioop-btn-mask"></b></div></div>');
                	$.getJSON("main!getModuleList.do", {"moduleId": "plan", "moduleState": "0"}, function(data){
                        var mdata = [];
                        mdata.push('<ul>');           
                        $(data).each(function(key, ele){
                            mdata.push('<li><div class="gtitem-modu-cont">');
                            mdata.push('<a id="'+ ele.planId +'" type="plan" ptype="'+ ele.planType +'" class="gtitem-modu-title over-flow" onclick="govIndex.gotoModuleDetail(this);">');
                            mdata.push(ele.planTitle + '</a>');
                            mdata.push('<span class="gtitem-modu-time">'+ ele.planTime.substring(5, 10) +'</span>');
                            mdata.push('</div></li>');
                        });            
                        mdata.push('</ul>');
                        $("#wstate_mitem_plan").html(mdata.join(""));
                        IOOP.isNoResult({
                            contSelor: "#wstate_mitem_plan ul",
                            itemSelor: "li",
                            showSize: "middle"
                        });
                    });
                	break;
                //汇报
                case "1900":
                	var _action = "report/received-report!index.do";
                	$("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="1900" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_1900","action":"'+ _action +'"});\'>more</span><span class="modu-item-icon mitem-icon-1900"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_report" class="gtitem-list-modu"><b class="ioop-btn-mask"></b></div></div>');
                	$.getJSON("main!getModuleList.do", {"moduleId": "report", "moduleState": "0"}, function(data){
                        var mdata = [];
                        mdata.push('<ul>');            
                        $(data).each(function(key, ele){
                            mdata.push('<li><div class="gtitem-modu-cont">');
                            mdata.push('<a id="'+ ele.reportId +'" type="report" class="gtitem-modu-title over-flow" onclick="govIndex.gotoModuleDetail(this);">');
                            mdata.push(ele.reportTitle + '</a>');
                            mdata.push('<span class="gtitem-modu-time">'+ ele.reportTime.substring(5, 10) +'</span>');
                            mdata.push('</div></li>');
                        });            
                        mdata.push('</ul>');
                        $("#wstate_mitem_report").html(mdata.join(""));
                        IOOP.isNoResult({
                            contSelor: "#wstate_mitem_report ul",
                            itemSelor: "li",
                            showSize: "middle"
                        });
                    });
                	break;
                //考勤
                case "2200":
                    var _action = "attendance/attendance!getAttendInfoByUser.do";
	                $("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="2200" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_2200","action":"'+ _action +'"});\'>more</span><span class="modu-item-icon mitem-icon-2200"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_attend" class="gtitem-list-modu"><b class="ioop-btn-mask"></b></div></div>');
	            	$.getJSON("main!getModuleList.do", {"moduleId": "attend", "moduleState": "0"}, function(data){
	                    var mdata = [];
	                    mdata.push('<ul>');            
	                    $(data).each(function(key, ele){
	                        mdata.push('<li><div class="gtitem-modu-cont">');
	                        mdata.push('<a class="gtitem-modu-otitle gtitem-modu-otitle-notime over-flow">');
	                        mdata.push('您于'+ ele.attendTime +' 在 '+ ele.attendAddr +' 签到</a>');
	                        mdata.push('</div></li>');
	                    });            
	                    mdata.push('</ul>');
	                    $("#wstate_mitem_attend").html(mdata.join(""));
	                    IOOP.isNoResult({
	                        contSelor: "#wstate_mitem_attend ul",
	                        itemSelor: "li",
	                        showSize: "middle"
	                    });
	                });
                    break;
                //投票
                case "2300":
                    var _action = "vote/vote!list.do";
                    $("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="2300" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_2300","action":"'+ _action +'","appData":{"state":"2"}});\'>more</span><span class="modu-item-icon mitem-icon-2300"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_vote" class="gtitem-modu-multi"><b class="ioop-btn-mask"></b></div></div>');
                    $.getJSON("main!getModuleList.do", {"moduleId": "vote", "moduleState": "2", "dataCount": "4"}, function(data){
	                    var mdata = [];
	                    mdata.push('<ul>');            
	                    $(data).each(function(key, ele){
                            mdata.push('<li><div id="'+ ele.voteId +'" type="vote" class="gtitem-multi-title over-flow" onclick="govIndex.gotoModuleDetail(this);">'+ ele.voteTitle +'</div>');
                            mdata.push('<div class="gtitem-multi-info over-flow">');
                            mdata.push('<div class="gtitem-mupd-info">'+ ele.voteUser +'<span class="gtitem-mupd-time">'+ ele.voteTime +'</span>发起</div>');
                            mdata.push('</div></li>');
                        });          
	                    mdata.push('</ul>');
	                    $("#wstate_mitem_vote").html(mdata.join(""));
	                    IOOP.isNoResult({
	                        contSelor: "#wstate_mitem_vote ul",
	                        itemSelor: "li",
	                        showSize: "middle"
	                    });
	                });
                    break;
                //调查
                case "2400":
                    var _action = "survey/my-attended-survey!list.do";
	                $("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="2400" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_2400","action":"'+ _action +'"});\'>more</span><span class="modu-item-icon mitem-icon-2400"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_survey" class="gtitem-modu-multi"><b class="ioop-btn-mask"></b></div></div>');
	                $.getJSON("main!getModuleList.do", {"moduleId": "survey", "moduleState": "0", "dataCount": "4"}, function(data){
	                    var mdata = [];
	                    mdata.push('<ul>');            
	                    $(data).each(function(key, ele){
	                        mdata.push('<li><div id="'+ ele.surveyId +'" type="survey" class="gtitem-multi-title over-flow" onclick="govIndex.gotoModuleDetail(this);">'+ ele.surveyTitle +'</div>');
	                        mdata.push('<div class="gtitem-multi-info over-flow">');
	                        mdata.push('<div class="gtitem-mupd-info">'+ ele.surveyUser +'<span class="gtitem-mupd-time">'+ ele.surveyTime +'</span>发起</div>');
	                        mdata.push('</div></li>');
	                    });        
	                    mdata.push('</ul>');
	                    $("#wstate_mitem_survey").html(mdata.join(""));
	                    IOOP.isNoResult({
	                        contSelor: "#wstate_mitem_survey ul",
	                        itemSelor: "li",
	                        showSize: "middle"
	                    });
	                });
                    break;
                //日程
                case "2700":
                    var _action = "personalcalendar/personal-calendar!toOwnList.do";
                    $("#mitem_list_main").append('<div class="moudle-item-list moudle-item-sort" code="2700" typeid="" name="'+ ele.appName +'"><div class="ghome-bar-two"><span class="ghome-bar-two-more" onclick=\'govIndex.gotoApp({"appCode":"app_item_2700","action":"'+ _action +'"});\'>more</span><span class="modu-item-icon mitem-icon-2700"></span><div class="ghome-bar-two-title over-flow">'+ ele.appName +'</div></div><div id="wstate_mitem_schedule" class="gtitem-list-modu"><b class="ioop-btn-mask"></b></div></div>');
                    $.getJSON("main!getModuleList.do", {"moduleId": "schedule", "moduleState": "0"}, function(data){
	                    var mdata = [];
	                    mdata.push('<ul>');            
	                    $(data).each(function(key, ele){
                            mdata.push('<li><div class="gtitem-modu-cont">');
                            mdata.push('<a class="gtitem-modu-otitle over-flow">');
                            mdata.push(ele.scheduleTitle + '</a>');
                            mdata.push('<span class="gtitem-modu-time">'+ ele.scheduleTime.substring(5, 10) +'</span>');
                            mdata.push('</div></li>');
                        });          
	                    mdata.push('</ul>');
	                    $("#wstate_mitem_schedule").html(mdata.join(""));
	                    IOOP.isNoResult({
	                        contSelor: "#wstate_mitem_schedule ul",
	                        itemSelor: "li",
	                        showSize: "middle"
	                    });
	                });
                    break;
                default:
                    return true;
            }
        });        
    },
    /*
     *@summary 调整模块数据列表间距
     */
    addItemSpace: function(){
        $("#mitem_list_main .moudle-item-sort").removeClass("moudle-item-right").not(".moudle-item-current").each(function(index, ele){
            if(index%2 == 1){
                $(ele).addClass("moudle-item-right");
            }
        });
    },
    /*
     *@summary 生成应用html代码
     *@param appData,应用模块数据
     */
    parseAppHtml: function(appData){    	
        $("#mitem_list_main .moudle-item-list").remove();
        //解析应用模块数据
        govIndex.getModuleData(appData);
        //添加应用模块排序功能
        $("#mitem_list_main").sortable({
            items: '.moudle-item-list',
            opacity: "0.8",
            cursor: "move",
            placeholder: "moudle-item-sort module-item-phold",
            start: function(event, ui){
                ui.item.addClass("moudle-item-current");
                govIndex.addItemSpace();
            },
            change: function(event, ui){
                govIndex.addItemSpace();
            },
            update: function(event, ui){
            	ui.item.removeClass("moudle-item-current");
                govIndex.addItemSpace();
                //保存新的设置
                govIndex.moduleSet.save($("#mitem_list_main .moudle-item-list"), true);
            },
            stop: function(event, ui){
                ui.item.removeClass("moudle-item-current");
                govIndex.addItemSpace();
            }
        });
        govIndex.addItemSpace();
    },
    /*
     *@summary 自定义模块设置方法
     */
    moduleSet: {
        /*
         *@summary 自定义模块设置窗口初始化方法
         */
        init: function(){
            if($("#govm_item_edit").length == 0){
                $("body").append('<div id="govm_item_edit" class="ioop-dialog govm-item-edit"><h6>自定义您首页要显示的模块<b class="dialog-close dialog-close-btn"></b></h6><div class="dialog-main gmitem-edit-main clear"><div class="gmitem-edit-list"><div class="gmitem-list-title">模块列表</div><div class="gmitem-list-items"><ul id="gmitem_items_src"></ul></div></div><div class="gmitem-list-arrow"><span id="gmitem_list_add" class="gmitem-arrow-icon" title="添加模块"></span></div><div class="gmitem-edit-list"><div class="gmitem-list-title gmitem-ltitle-select"><b id="gmitem_sort_down" class="gmitem-select-sort gmitem-sort-down" title="向下"></b><b id="gmitem_sort_up" class="gmitem-select-sort gmitem-sort-up" title="向上"></b>已选模块</div><div class="gmitem-list-items"><ul id="gmitem_items_des"></ul></div></div><div class="gmitem-list-save"><a id="gmitem_set_save" class="gmitem-save-btn">保存并关闭</a></div></div></div>');
                //添加模块
                $("#gmitem_list_add").on("click", function(){
                    govIndex.moduleSet.addItems($("#gmitem_items_src li.selected"));
                });
                //向上移动所选模块
                $("#gmitem_sort_up").on("click", function(){
                    var items = $("#gmitem_items_des li.selected"),
                        itemLen = items.length,
                        itemTotal = $("#gmitem_items_des li").length;
                    if(itemLen == 0 || itemTotal <= itemLen){
                        return false;
                    }			
                    items.each(function(index, ele){
                        var _this = $(this);
                        if(_this.prev('li').length > 0){
                            _this.prev('li').before(_this);
                        }else{
                            $('#gmitem_items_des li:last').after(_this);
                        }
                    });
                });
                //向下移动所选模块
                $("#gmitem_sort_down").on("click", function(){
                    var items = $("#gmitem_items_des li.selected"),
                        itemLen = items.length,
                        itemTotal = $("#gmitem_items_des li").length,
                        _this = null;
                    if(itemLen == 0 || itemTotal <= itemLen){
                        return false;
                    }
                    for(var i = itemLen; i >0; i--){
                        _this = items.eq(i-1);
                        if(_this && _this.next("li").length > 0){
                            _this.next('li').after(_this);
                        }else{
                            $("#gmitem_items_des li:first").before(_this);
                        }
                    }
                });
                //保存并应用新设置
                $("#gmitem_set_save").on("click", function(){                   
                    var _result = [];
                    //保存新设置
                    _result = govIndex.moduleSet.save($("#gmitem_items_des li"));
                    $("#govm_item_edit").dialogHide();
                    //应用新设置
                    govIndex.parseAppHtml(_result);
                });
                //禁止选中文本
                if (typeof($("#gmitem_items_src")[0].onselectstart) != "undefined"){      
                    $("#gmitem_items_src")[0].onselectstart = $("#gmitem_items_src")[0].onselectstart = function(){
                        return false;
                    };   
                }
            }
        },
        /*
         *@summary 添加所选模块到结果列表中
         */
        addItems: function(items){
            var _desHtml = [];
            items.each(function(){
                var _this = $(this),
                    _code = _this.attr("code"),
                    _typeid = _this.attr("typeid"),
                    _name = _this.attr("name"),
                    _checkid = "gmitem_";
                _typeid = !_typeid ? "" : _typeid;
                _checkid = !_typeid ? (_checkid + _code) : (_checkid + _code + "_" + _typeid);
                if($("#"+_checkid).length == 0){
                    _desHtml.push('<li id="'+ _checkid +'" code="'+ _code +'" typeid="'+ _typeid +'" name="'+ _name +'" class="gmitem-level-one gmitem-has-close"><a class="over-flow" title="'+ _name +'">'+ _name +'</a><span class="gmitem-icon-close"></span></li>');
                }
            });
            $("#gmitem_items_des").append(_desHtml.join(""));
            //绑定点击事件
            $("#gmitem_items_des li").off("click").on("click", function(){
                var _this = $(this);
                if(_this.hasClass("selected")){
                    _this.removeClass("selected");
                }else{
                    _this.addClass("selected");
                }
            });
            //绑定删除事件
            $("#gmitem_items_des .gmitem-icon-close").off("click").on("click", function(){
                $(this).parent().remove();
            });
        },
        /*
         *@summary 自定义模块设置窗口显示方法
         */
        show: function(){
            var _rightSet = IOOP.option.govIndex.indexModule.rightSet,
                _rightSetList = [],
                _infoType = IOOP.option.govIndex.indexModule.infoType,
                _infoTypeList = [],
                _collType = IOOP.option.govIndex.indexModule.collType,
                _collTypeList = [],
                _defaultName = IOOP.option.govIndex.indexModule.defaultNames;
            //获取权限内可配置模块
            if(_rightSet){
            	_rightSetList = _rightSet.split(",");
            }
            //获取公告分类
            if(_infoType){
            	_infoTypeList = _infoType.split("|||");
            }
            //获取审批分类
            if(_collType){
            	_collTypeList = _collType.split("|||");
            }
            //设置可选模块列表
            var _rightNum = _rightSetList.length,
                _infoTypeNum = _infoTypeList.length,
                _collTypeNum = _collTypeList.length,
                _srcHtml = [];
            if(_rightNum > 0){
	            for(var i = 0; i < _rightNum; i++){
	            	switch(_rightSetList[i]){
	            	case "1000":
	            		_srcHtml.push('<li code="1000" typeid="" name="'+ _defaultName["1000"] +'" class="gmitem-level-one"><a class="over-flow" title="'+ _defaultName["1000"] +'">'+ _defaultName["1000"] +'</a></li>');
	            		break;
	            	case "1100":
	            		_srcHtml.push('<li code="1100" typeid="" name="'+ _defaultName["1100"] +'" class="gmitem-level-one"><a class="over-flow" title="'+ _defaultName["1100"] +'">'+ _defaultName["1100"] +'</a></li>');
	            		var _infoTypeObj = [];
	            		for(var j = 0; j < _infoTypeNum; j++){
	            			_infoTypeObj = _infoTypeList[j].split("===");
	            			if(_infoTypeObj.length > 1){
	            				_srcHtml.push('<li code="1100" typeid="'+ _infoTypeObj[0] +'" name="'+ _infoTypeObj[1] +'" class="gmitem-level-two"><a class="over-flow" title="'+ _infoTypeObj[1] +'">'+ _infoTypeObj[1] +'</a></li>');
	            			}
	            		}
	            		break;
	            	case "1300":
	            		_srcHtml.push('<li code="1300" typeid="" name="'+ _defaultName["1300"] +'" class="gmitem-level-one"><a class="over-flow" title="'+ _defaultName["1300"] +'">'+ _defaultName["1300"] +'</a></li>');
	            		break;
	            	case "1500":
	            		_srcHtml.push('<li code="1501" typeid="" name="'+ _defaultName["1501"] +'" class="gmitem-level-one"><a class="over-flow" title="'+ _defaultName["1501"] +'">'+ _defaultName["1501"] +'</a></li>');
	            		_srcHtml.push('<li code="1502" typeid="" name="'+ _defaultName["1502"] +'" class="gmitem-level-one"><a class="over-flow" title="'+ _defaultName["1502"] +'">'+ _defaultName["1502"] +'</a></li>');
	            		break;
	            	case "1600":
	            		_srcHtml.push('<li code="1600" typeid="" name="'+ _defaultName["1600"] +'" class="gmitem-level-one"><a class="over-flow" title="'+ _defaultName["1600"] +'">'+ _defaultName["1600"] +'</a></li>');
	            		var _collTypeObj = [];
	            		for(var j = 0; j < _collTypeNum; j++){
	            			_collTypeObj = _collTypeList[j].split("===");
	            			if(_collTypeObj.length > 1){
	            				_srcHtml.push('<li code="1600" typeid="'+ _collTypeObj[0] +'" name="'+ _collTypeObj[1] +'" class="gmitem-level-two"><a class="over-flow" title="'+ _collTypeObj[1] +'">'+ _collTypeObj[1] +'</a></li>');
	            			}
	            		}
	            		break;
	            	case "1700":
	            	case "1800":
	            	case "1900":
	            	case "2200":
	            	case "2300":
	            	case "2400":
	            	case "2700":
	            		_srcHtml.push('<li code="'+ _rightSetList[i] +'" typeid="" name="'+ _defaultName[_rightSetList[i]] +'" class="gmitem-level-one"><a class="over-flow" title="'+ _defaultName[_rightSetList[i]] +'">'+ _defaultName[_rightSetList[i]] +'</a></li>');
	            	default:
	            		//其他模块暂不支持自定义
	            	}
	            }
	            $("#gmitem_items_src").html(_srcHtml.join("")).find("li").on("click", function(){
	                var _this = $(this);
	                if(_this.hasClass("selected")){
	                    _this.removeClass("selected");
	                }else{
	                    _this.addClass("selected");
	                }
	            }).on("dblclick", function(){
	                govIndex.moduleSet.addItems($(this));
	            });
            }else{
            	$("#gmitem_items_src li").remove();
            }
            //设置已选模块列表
            $("#gmitem_items_des li").remove();
            govIndex.moduleSet.addItems($("#mitem_list_main .moudle-item-list"));
            //显示设置对话框
            $("#govm_item_edit").dialogShow();
        },
        /*
         *@summary 自定义模块设置保存方法
         *@param items,模块jquery对象集合
         *@param preventTips,是否阻止提示保存状态, 默认false
         *@return 要保存的模块设置json对象
         */
        save: function(items, preventTips){
            var _resultList = [],
                _result = "";
            items.each(function(){
                var _this = $(this),
                    _code = _this.attr("code"),
                    _typeid = _this.attr("typeid"),
                    _name = _this.attr("name");
                _resultList.push('{"appId": "'+ _code + '", "appName": "'+ _name + '", "appType": "'+ _typeid + '"}');
            });
            _result = "[" + _resultList.join(",") + "]";
            //保存新设置
            $.post("main!setIndexModules.do", {"indexModules": _result}, function(data){
                if(data.success && !preventTips){
                    IOOP.showtips({
                        tipsMsg: "保存成功！",
                        isOK: true,
                        tipsDelay: 2000,
                        selecter: "#main-content",
                        offsetY: 30
                    });
                }
            });
            return _result;
        }
    },
    /*
     *@summary 自定义操作模块相关方法
     */
    moduleOperate: {
        /*
         *@summary 自定义操作模块设置初始化方法
         */
        init: function(modCodes){
            var _modCodes = IOOP.option.govIndex.quickOperate.defaultCodes,
                _rightCodes = IOOP.option.govIndex.quickOperate.rightCodes,
                _rightCodesList = [],
                _rightCodesLen = 0,
                _desCodesList = [],
                _srcCodesList = [];              
            if(!_rightCodes){
                return false;
            }
            _rightCodesList = _rightCodes.split(",");
            _rightCodesLen = _rightCodesList.length;
            if(modCodes){
                _modCodes = modCodes;
            }
            for(var i = 0; i < _rightCodesLen; i++){
                if(_modCodes.indexOf(_rightCodesList[i]) > -1){
                    _desCodesList.push(_rightCodesList[i]);
                }else{
                    _srcCodesList.push(_rightCodesList[i]);
                }
            }           
            //添加已使用模块            
            govIndex.moduleOperate.addDesItems(_desCodesList);
            //添加未使用模块            
            govIndex.moduleOperate.addSrcItems(_srcCodesList);
            //绑定自定义按钮处理方法
            $("#qmopate_item_edit").on("click", function(){
                $("#quick_opate_used").addClass("quick-opate-edit");
                govIndex.moduleOperate.desEnableEdit();                
                $("#quick_opate_unused").show();
            });
            //绑定保存自定义按钮设置结果处理方法
            $("#quick_opate_save").on("click", function(){
                govIndex.moduleOperate.saveItems();
            });   
        },
        /*
         *@summary 添加操作模块方法
         *@param modCodeList,要添加的模块code数组
         */
        getItemsHtml: function(modCodeList){
            var _html = [],
                modCodeListLen = 0;
            if(modCodeList instanceof Array && modCodeList.length > 0){
                modCodeListLen = modCodeList.length;
                for(var i=0; i<modCodeListLen; i++){
                    switch(modCodeList[i]){
                    //写邮件
                    case "10007000":
                        _html.push('<li id="10007000" class="qopate-list-1">写邮件<span class="qopate-add-remove"></span></li>');
                        break;
                    case "11001000":
                        _html.push('<li id="11001000" class="qopate-list-2">发公告<span class="qopate-add-remove"></span></li>');
                        break;
                    case "13003000":
                        _html.push('<li id="13003000" class="qopate-list-3">申请会议<span class="qopate-add-remove"></span></li>');
                        break;
                    case "23004000":
                        _html.push('<li id="23004000" class="qopate-list-7">发起投票<span class="qopate-add-remove"></span></li>');
                        break;
                    case "24004000":
                        _html.push('<li id="24004000" class="qopate-list-6">发起调查<span class="qopate-add-remove"></span></li>');
                        break;
                    case "2700":
                        _html.push('<li id="2700" class="qopate-list-8">创建日程<span class="qopate-add-remove"></span></li>');
                        break;
                    case "20001000":
                        _html.push('<li id="20001000" class="qopate-list-9">发短信<span class="qopate-add-remove"></span></li>');
                        break;
                    case "17006000":
                        _html.push('<li id="17006000" class="qopate-list-10">创建任务<span class="qopate-add-remove"></span></li>');
                        break;
                    case "18004000":
                        _html.push('<li id="18004000" class="qopate-list-11">写计划<span class="qopate-add-remove"></span></li>');
                        break;
                    case "19004000":
                        _html.push('<li id="19004000" class="qopate-list-12">写汇报<span class="qopate-add-remove"></span></li>');
                        break;
                    default:
                        //其他模块
                    }   
                }
            }
            return _html.join("");
        },
        /*
         *@summary 添加已使用操作模块方法
         *@param modCodeList,要添加的模块code数组
         */
        addDesItems: function(modCodeList){
            var _html = "";
            if(modCodeList instanceof Array && modCodeList.length > 0){
                _html = govIndex.moduleOperate.getItemsHtml(modCodeList);
                if(_html){
                    $("#quick_opate_des").html(_html);
                    govIndex.moduleOperate.desEnableClick();
                }
            }            
        },
        /*
         *@summary 添加未使用操作模块方法
         *@param modCodeList,要添加的模块code数组
         */
        addSrcItems: function(modCodeList){
            var _html = "";
            if(modCodeList instanceof Array && modCodeList.length > 0){
                _html = govIndex.moduleOperate.getItemsHtml(modCodeList);
                if(_html){
                    $("#quick_opate_src").html(_html);
                    govIndex.moduleOperate.srcEnableEdit();
                }
            }            
        },
        /*
         *@summary 保存已使用操作模块设置的方法
         */
        saveItems: function(){
            var _result = [];
            $("#quick_opate_des li").each(function(index, ele){
                _result.push($(this).attr("id"));
            });
            //保存新设置
            $.post("main!setIndexOpeation.do", {"indexOpeation": _result.join(",")}, function(data){
                if(data.success){
                    IOOP.showtips({
                        tipsMsg: "保存成功！",
                        isOK: true,
                        tipsDelay: 2000,
                        selecter: "#main-content",
                        offsetY: 30
                    });
                }
            });
            $("#quick_opate_used").removeClass("quick-opate-edit");
            govIndex.moduleOperate.desEnableClick();
            $("#quick_opate_unused").hide();
        },
        /*
         *@summary 已使用模块可点击
         */
        desEnableClick: function(){
            $("#quick_opate_des li").off("click").each(function(index, ele){
                var _id = ele.id;
                switch(_id){
                //写邮件
                case "10007000":
                    $(this).on("click", function(){
                        openAddMailWin();
                    });
                    break;
                case "11001000":
                    $(this).on("click", function(){
                        govIndex.gotoModule("m_index_info_add");
                    });
                    break;
                case "13003000":
                    $(this).on("click", function(){
                        govIndex.gotoModule("m_meeting_edit");
                    });
                    break;
                case "23004000":
                    $(this).on("click", function(){
                        govIndex.gotoModule("m_vote_edit");
                    });
                    break;
                case "24004000":
                    $(this).on("click", function(){
                        govIndex.gotoModule("m_sponsor_new_survey");
                    });
                    break;
                case "2700":
                    $(this).on("click", function(){
                        govIndex.gotoModule("m_calendar_own");
                    });
                    break;
                case "20001000":
                    $(this).on("click", function(){
                        govIndex.gotoModule("m_sms_send");
                    });
                    break;
                case "17006000":
                    $(this).on("click", function(){
                        govIndex.gotoModule("m_add_task");
                    });
                    break;
                case "18004000":
                    $(this).on("click", function(){
                        govIndex.gotoModule("m_plan_edit");
                    });
                    break;
                case "19004000":
                    $(this).on("click", function(){
                        govIndex.gotoModule("m_report_edit");
                    });
                    break;
                default:
                    //其他模块
                }
            });
            govIndex.moduleOperate.desDisableSort();
        },
        /*
         *@summary 已使用模块可移除
         */
        desEnableEdit: function(){
            $("#quick_opate_des li").off("click").find(".qopate-add-remove").off("click").on("click", function(){
            	if($("#quick_opate_des li").length > 1){
            		$(this).parent().appendTo($("#quick_opate_src"));
            		govIndex.moduleOperate.srcEnableEdit();                
            	}else{
            		IOOP.showtips({
                        tipsMsg: "至少要保留一个哦！",
                        isOK: false,
                        tipsDelay: 2000,
                        selecter: "#main-content",
                        offsetY: 30
                    });
            	}
            });
            govIndex.moduleOperate.desEnableSort();
        },
        /*
         *@summary 已使用模块可排序
         */
        desEnableSort: function(){
            $("#quick_opate_des").sortable({
                items: 'li',
                opacity: "0.8",
                cursor: "move",
                placeholder: "quick-opate-phold"
            });
        },
        /*
         *@summary 已使用模块取消排序
         */
        desDisableSort: function(){
        	if($("#quick_opate_des").hasClass("ui-sortable")){ 
        		try{
        			$("#quick_opate_des").sortable("destroy");
        		}catch(e){}
        	}
        },
        /*
         *@summary 未使用模块可添加
         */
        srcEnableEdit: function(){
            $("#quick_opate_src .qopate-add-remove").off("click").on("click", function(){
                $(this).parent().appendTo($("#quick_opate_des"));
                govIndex.moduleOperate.desEnableEdit();
                govIndex.moduleOperate.desEnableSort();
            });
        }
    }
};