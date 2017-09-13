<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>

<%@ include file="/include/meta.jsp"%>
<script type="text/javascript" src="${ctx}/res/plugin/lhgdialog/lhgcore.lhgdialog.min.js"></script>
	<script type="text/javascript" src="${ctx}/res/plugin/ztree/js/jquery.ztree.all-3.5.js"></script>
	<link rel="stylesheet" href="${ctx}/res/plugin/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css"></link>
<head>
<meta charset="utf-8"/>
<title>甘肃万维JUP课题</title>
<style>
/*设置按钮样式*/
.addUser{
    width:60px;
    height:25px;
    background:#659be0;
    color:#fff;
    border:1px solid #659be0;
    border-radius:3px;
    cursor: pointer;
}
.deleteUser{
    width:60px;
    height:25px;
    background:#659be0;
    color:#fff;
    border:1px solid #659be0;
    border-radius:3px;
    cursor: pointer;
}
.userInput{
    width:60px;
    height:25px;
    background:#659be0;
    color:#fff;
    border:1px solid #659be0;
    border-radius:3px;
    cursor: pointer;
}
.userOutput{
    width:60px;
    height:25px;
    background:#659be0;
    color:#fff;
    border:1px solid #659be0;
    border-radius:3px;
    cursor: pointer;
}

/*设置弹出层样式*/
.alert_tb {	
	left:180px;
	top:120px;
	border:1px solid #F68A8A;
	width:640px;
	height:150px;
	background-color:#e2ecf5;
	z-index:1000;
	position:absolute;
} 
.userForm{
   height: 0px;
}
.input_one{
  line-height:30px;
  background-color:#fbedce;
  height:30px;
  font-size:13px;
}
.input_one p .inputUser{
    margin-left:5px;
}
.input_two{
  line-height:90px;
  height:90px;
}
.input_two ul li{
    text-align:center;
    display: inline;
    list-style-type: none;
}


.input_three{
  height:40px;
  background-color:#fbedce;
}
.input_three p{
   line-height:40px;
   background-color:#fbedce;
   text-align:center;
   
}
.input_three p .input,.cancel{
  line-height:20px;
  margin:0px 30px 0px 30px;
   
}


.alert_tb p span {
	float:left;
}
		
/*.alert_tb p span#close {
	margin-left:575px;
	font-weight:500;
	cursor:pointer;
	font-size:18px;
}*/ 
.mybg{
	background-color:#000;
	width:100%;
	height:100%;
	position:absolute;
	top:0; 
	left:0; 
	zIndex:500; 
	opacity:0.3; 
	filter:Alpha(opacity=30); 
}

</style>
<script type="text/javascript"> 
	$(function(){
		//高级搜索按钮点击事件
		$('.advanced-btn').on('click',function(){
			$('.advanced-content').toggle('fast');
		});
		$(".advanced-search-btn").click(function(){
			$("#form2").submit();
		});
		//阻止按键盘Enter键提交表单
		var $inp = $('input');
		$inp.keypress(function (e) { 
		    var key = e.which; 
		    if (key == 13) {
		        return false;
		    }
		});			
});
	
/**搜索表单校验**/
	function checkSubmitForm(){
		var loginNameSearch=$("#loginnameSearch").val();
		if(loginNameSearch==''||isNumbOrLett(loginNameSearch)){			
			form1.submit();
		}else{
			$.validator.errorShow($("#loginnameSearch"),'只能包括数字和字母');
		}
}	

/*
	用途：检查输入字符串是否只由汉字、字母、数字组成
	输入：
	value：字符串
	返回：
	如果通过验证返回true,否则返回false
	*/
	function isNumbOrLett( s ){//判断是否是字母、数字组成
		//var regu = "^[0-9a-zA-Z\u4e00-\u9fa5]+$";
		var regu = /^([a-zA-Z0-9]+)$/;
		var re = new RegExp(regu);
		if (re.test(s)) {
			return true;
		}else{
			return false;
		}
}


/***新增***/
function addComplatUser(){
     window.location.href="${ctx}/complat/complatUserEdit";
}

/***删除***/
function deleteComplatUser(){
     window.location.href="${ctx}/complat/complatUserDelete?complatUserId="+complatUserId;
}

/***用户导入***/
//弹出层
function intPutComplatUser(){
    var mybg = document.createElement("div"); 
	mybg.setAttribute("class","mybg"); 
	$(".mybg").addClass("mybg");
    document.body.appendChild(mybg);
	document.body.style.overflow = "hidden"; 
	$("#alerttb").show(); 				

}
	
 
//导入
$(function(){
   $("#excel_button").click(function(){
	   $("#form1").submit();
   });

});


//下载模板
function downloadTemplate(fileName){	    
	window.location.href="${ctx}/complat/downloadComplaUserTemplate";
}	  


/***导出***/
function outPutComplatUser(){
    window.location.href="${ctx}/complat/complatExport";
}

	
	
	
</script>

</head>
<body>

<div class="list-warper">
	<!--列表的面包屑区域-->
	<div class="position">
		<ol class="breadcrumb">
			<li>
				<a href="${ctx}/index" target="_top">首页</a>
			</li>
			<li class="split"></li>
			<li>
				<a >政府用户</a>
			</li>
			<li class="split"></li>
			<li class="active">
				用户列表
			</li>
    	</ol>
    </div>
    
    <div class="search-content">
		<form id="form1" name="pageForm" action="${ctx}/complat/complatList" method="get">
			<table class="">
				<tr>					
					<!--<th style="padding-left: 300px">用户姓名：</th>
					<td width="20%">
						<input type="text"  style="width: 170px;" placeholder="用户姓名" value="${sParams['LIKE_name']}" id="nameSearch" name="search_LIKE_name" />
					</td>
					-->
					<th  style="padding-left: 300px"> 请输入登录名：</th>
					<td width="20%">
						<input type="text"  style="width: 170px;" placeholder="请输入登录名" value="${sParams['LIKE_loginname']}" id="loginnameSearch" name="search_LIKE_loginname" />
					</td> 
					<td class="btn-group"> <a class="btnSearch" onclick="javascript:checkSubmitForm()">搜索</a></td>
				</tr>
			</table>	
		</form>
	</div>
    
    
	<!--列表内容区域-->
	<div class="list">
	<input type="hidden" id="orderField" name="orderField" value="${orderField}"/> 
	<input type="hidden" id="orderSort" name="orderSort" value="${orderSort}"/>
		
        <div class="list-topBar  advanced-search">
        
        
        
        
        	<div class="list-toolbar">
             <!--  操作按钮开始 	    -->   
              <button class="addUser" onclick="addComplatUser()"><i class="btn_icon3"></i><span>新 增</span></button>
              <button class="deleteUser" onclick="deleteComplatUser()"><i class="btn_icon4"></i><span>删 除</span></button>
              <button class="userInput"  id="userInput" onclick="intPutComplatUser()"><i class="btn_icon4"></i><span>用户导入</span></button>
              <button class="userOutput"  id="userOutput"" onclick="outPutComplatUser()"><i class="btn_icon4"></i><span>用户导出</span></button>  
           <!--   操作按钮结束  --> 
           </div> 
            
             
            
        </div>
        <!-- 高级探索表单 -->
        <form id="form2" name="form2" action="${ctx}/complat/complatList">        
        <ul class="advanced-content" style="display:none;">
        	<li>
        		<input type="hidden"  name="orderField" value="${orderField}"/> 
				<input type="hidden"  name="orderSort" value="${orderSort}"/>
            	<label>姓名:</label>
                <input type="text" class="" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
            </li>
            <li>
            	<label>登录名:</label>
                <input type="text" class="" name="search_LIKE_loginname" value="${sParams['LIKE_loginname']}"/>
            </li><!--  
            <li>
            	<label>企业或机构名称:</label>
                <input type="text" class="" name="search_LIKE_name" value="${sParams['LIKE_name']}"/>
            </li>
            <li>
            	<label>身份证号码:</label>
                <input type="text" class="" name="search_LIKE_cardNumber" value="${sParams['LIKE_cardNumber']}"/>
            </li>          
            --><li class="advanced-search-btn">搜索</li>
        </ul>
        </form>
        
        
       
        
        <!-- 提示信息开始 -->
         <div class="form-alert;" >
         	<tags:message msgMap="${msgMap}"></tags:message>
    	</div>
    	<!-- 提示信息结束 -->
    	<!-- 列表开始 -->
        <table cellpadding="0" cellspacing="0" border="0" width="100%" class="list-table">
        	<thead>
            	<tr>
                	<th width="10px">   
                		<div class="label">
									<i class="check_btn check_all"></i>
									<input type="checkbox" class="check_btn" style="display: none;" />
								</div>             		
                	</th>
                	
                	
                	<th width="10%" style="text-align: center;">姓名</th>
                    <th width="10%" style="text-align: center;">登录名</th>
                    <th width="10%" style="text-align: center;">登录全名</th>
                    <th width="15%" style="text-align: center;">手机号码</th>
                    <th width="20%" style="text-align: center;">邮箱</th>
                    <th width="5%" style="text-align: center;">账号开启</th>                                                     
                    <!--<th width="15%" class="alignL" style="text-align: center;">用户职务</th>-->
                    <th width="15%" class="alignL" style="text-align: center;">注册时间</th>    
                    <!--<th width="5%" style="text-align: center;">办公电话</th> -->
                    <th width="15%" style="text-align: center;">操作</th> 
                </tr>
            </thead> 
            <tbody>
                <c:forEach items="${pageInfo.content}" var="complatUser" varStatus="state">
					<tr class="myclass">
	                	<td>
	                    	<div class="label">
	                            <i class="check_btn"></i><input id="${complatUser.iid}" value="${complatUser.iid}" type="checkbox" class="check_btn" style="display:none;"/>
	                        </div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatUser.name}" class="word_break">${complatUser.name}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatUser.loginname}" class="word_break">${complatUser.loginname}</div>
	                    </td>
	                	<td style="text-align: center;">
	                    	<div title="${complatUser.loginallname}" class="word_break">${complatUser.loginallname}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatUser.mobile}" class="word_break">${complatUser.mobile}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div title="${complatUser.email}" class="word_break">${complatUser.email}</div>
	                    </td>
	                    <td style="text-align: center;">
	                    	<div class="alignL">
	                    		<div class="list-longtext">
	                    			<c:if test="${complatUser.enable == '0'}"><font color="red">禁用</font></c:if>
	                           		<c:if test="${complatUser.enable == '1'}"><font color="#32CD32">启用</font></c:if>
	                    		</div>
	                        </div>
	                    </td>
	                    
	                    
	                     <td style="text-align: center;">
	                    	<div title="${complatUser.createtime}" class="word_break">${complatUser.createtime}</div>
	                    </td>	                    	                   
	                	<td class="position-content" style="text-align: center;" >
	                        <gsww:opTag menuId="8a929c9e5e5fbde5015e5fdb125c0002" tabIndex="1" operatorType="2"></gsww:opTag>
	                    </td>
	                </tr>
				</c:forEach>
                
            </tbody>       
        </table>
        <!-- 列表结束 -->
    </div>
    <!-- 分页 -->
   <tags:pagination page="${pageInfo}" paginationSize="5"/> 
</div>


   <!-- 导入数据时的弹出层 -->
        <div id="alerttb" class="alert_tb" style="display:none;"> 
          <div class="input_one">
               <p>
                  <span id="inputUser">用户导入</span>
                 <!--     <span id="close">X</span>   -->                                   
               </p>  
          </div>    
          <div class="input_two">
            	  
	 		   <ul style="width:610px;">
	  		      <li>
	  		         <form id="form1" class="userForm" action="${ctx }/complat/complatImport" method="post" enctype="multipart/form-data" name="batchAdd"> 	 	
	  		           <label>选择文件：</label>             
                       <input style="width:300px;margin-top:-5px; border-radius: 4px;background: #659be0;color: #fff;line-height: 20px;font-size: 12px;padding: 2px;cursor: pointer;" id="excel_file" type="file" name="filename" class="file_upload" accept="xls" size="50">                 
                     </form>	                    
              </li> 
              <li>
                  <button style="width:60px;height:25px;margin-left:495px;background:#f79638;color:#fff;border:1px solid #f79638;border-radius:3px;cursor: pointer;" class="pb_bg_c_qb_1n7XT"  onclick="downloadTemplate();">模板下载</button>                    
              </li>
	  		</ul>	    	   
          </div> 
          <div class="input_three">            
                <p>
                   <button style="width:60px;height:25px;background:#659be0;color:#fff;border:1px solid #f79638;border-radius:3px;cursor: pointer;" type="button" class="input"  id="excel_button">导入</button>
                   <button style="width:60px;height:25px;background:#f79638;color:#fff;border:1px solid #f79638;border-radius:3px;cursor: pointer;" type="button" class="cancel" id="cancel" onclick="javascript:window.location.href='${ctx}/complat/complatList'">取消</button>
                </p>	 		      	 		     
          </div>                  
        </div>

</body>
</html>
