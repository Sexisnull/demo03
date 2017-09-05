<%@ page language="java" pageEncoding="utf-8"%>
<!doctype html>
<html>
<%@ include file="/include/meta.jsp"%>
	<head>
		<meta charset="utf-8" />
		<style type="text/css">
			.list-toolbar ul {
			    margin: 8px 21px;
			}
		</style>
	</head>


	<body>
		<OBJECT id="pjq"
			style="LEFT: 0px; WIDTH: 35px; TOP: 0px; HEIGHT: 16px"
			classid="clsid:61F112FC-2AEA-4E34-82A6-4C94AF61152E">
			<PARAM NAME="_Version" VALUE="65536">
			<PARAM NAME="_ExtentX" VALUE="926">
			<PARAM NAME="_ExtentY" VALUE="423">
			<PARAM NAME="_StockProps" VALUE="0">
		</OBJECT>
<SCRIPT Language="JavaScript">



	var handle; 


function PJQ_Open()
{
	var lRet;
	
	lRet = pjq.Open();		//打开设备串口
													
	
	if(lRet <= 0)
	{
		msg.value = " Open device failed.\n\n";    //失败
	}
	else
	{
		msg.value = " Open device succeed.\n\n";   //成功
		handle = lRet;														 //保存设备串口句柄
	}
}



function PJQ_Voice1() 
{
	var lRet;
	
	lRet = pjq.Voice(handle,1);			//播放语音: "您好，欢迎光临"
	if(lRet != 0)
	{
		msg.value += " failed.\n\n";		//失败
	}
	else
	{
		msg.value += " succeed.\n\n";   //成功
	}
}


function PJQ_Voice2() 
{
	var lRet;
	
	lRet = pjq.Voice(handle,2);     //播放语音: "请在一米线外排队等候"
	if(lRet != 0)
	{
		msg.value += " failed.\n\n";    //失败
	}
	else
	{
		msg.value += " succeed.\n\n";   //成功
	}
}


function PJQ_Star1() 
{
	var lRet;
	
	lRet = pjq.SetLevel(handle,1);		//设置服务星级为1星级
	if(lRet != 0)
	{
		msg.value += " failed.\n\n";		//失败
	}
	else
	{
	    msg.value += " 1 Star.\n\n";  //成功
	}	
}


function PJQ_Star2() 
{
	var lRet;
	
	lRet = pjq.SetLevel(handle,2);		//设置服务星级为2星级
	if(lRet != 0)
	{
		msg.value += " failed.\n\n";    //失败
	}
	else
	{
	    msg.value += " 2 Star.\n\n";	//成功
	}	
}


function PJQ_Star3() 								
{
	var lRet;
	
	lRet = pjq.SetLevel(handle,3);		//设置服务星级为3星级
	if(lRet != 0)
	{
		msg.value += " failed.\n\n";		//失败
	}
	else
	{
	    msg.value += " 3 Star.\n\n";	//成功
	}	
}


function PJQ_Star4() 
{
	var lRet;
	
	lRet = pjq.SetLevel(handle,4);		//设置服务星级为4星级
	if(lRet != 0)
	{
		msg.value += " failed.\n\n";		//失败
	}
	else
	{
	    msg.value += " 4 Star.\n\n";	//成功
	}	
}


function PJQ_Star5() 
{
	var lRet;
	
	lRet = pjq.SetLevel(handle,5);		//设置服务星级为5星级
	if(lRet != 0)
	{
		msg.value += " failed.\n\n";		//失败
	}
	else
	{
	    msg.value += " 5 Star.\n\n";	//成功
	}	
}


function PJQ_Evaluate() 
{
	var lRet;									//评价键返回值
	var userid;  							//员工号
	var tradecode;						//交易代码
	var netpoint;							//机构码
	var serveripaddress;			//后台服务器IP地址
	var xj;										//员工服务星级
				
	lRet = pjq.Evaluate(handle,15);		//触发评价，语音提示："请对本次服务评价". 等待时间8秒
alert(lRet);
	switch( lRet )
	{
		case 1: msg.value += " 非常满意.\n\n";					//非常满意
						break;		
		case 6: msg.value += " 基本满意.\n\n";					//基本满意
		        break;
		case 2: msg.value += " 态度不好.\n\n"; 					//态度不好
		        break;
		case 7: msg.value += " 时间太长.\n\n"; 					//时间太长
		        break;
		case 3: msg.value += " 业务不熟.\n\n";					//业务不熟
		        break;
		case 8: msg.value += " 有待改进.\n\n"; 					//有待改进
		        break;
		case 5: msg.value += " 超时未评.\n\n";					//超时未评
		        break;
		default: msg.value += " 评价失败.\n\n";					//评价失败
	}
/*
	userid = "y002";
	tradecode = "7890";
	netpoint = "abcd"
	serveripaddress = "192.168.1.142";
	pjq.SendData(userid,tradecode,netpoint,serveripaddress,lRet);		//向后台服务器发送数据
	xj = pjq.GetStarNumber();														//获取员工当前服务星级
	pjq.SetLevel(handle,xj);														//点亮星级指示灯	
*/
}


function PJQ_Close()
{
	var lRet;
	
	lRet = pjq.Close(handle);					//关闭设备串口
	if(lRet != 0)
	{
		msg.value += " failed.\n\n";		//失败
	}
	else
	{
		msg.value += " succeed.\n\n";		//成功
	}
	
}

</SCRIPT>
<div class="list-warper">
	<div class="search-content">
		<div class="list-toolbar" style="border:0px;">
									<ul>
										<li onclick="PJQ_Open()" class="query">
											<a>open</a>
										</li>
										<li onclick="PJQ_Close()" class="del">
											<a>close</a>
										</li>&nbsp;&nbsp;&nbsp;
										<li  class="new" onclick="PJQ_Voice1()" >
											<a>voice1</a>
										</li>
										<li  class="edit" onclick="PJQ_Voice2()" >
											<a>voice2</a>
										</li>
										<li  class="new" onclick="PJQ_Evaluate()">
											<a>Evaluate</a>
										</li>&nbsp;&nbsp;&nbsp;
										<li  class="edit" onclick="PJQ_Star1()">
											<a>1  star</a>
										</li>
										<li  class="new" onclick="PJQ_Star2()">
											<a>2  stars</a>
										</li>
										<li  class="edit" onclick="PJQ_Star3()">
											<a>3  stars</a>
										</li>
										<li  class="new" onclick="PJQ_Star4()">
											<a>4  stars</a>
										</li>
										<li  class="edit onclick="PJQ_Star5()">
											<a>5  stars</a>
										</li>
									</ul>
								</div>
								<br>
		<p align="center">
			<TEXTAREA style="WIDTH: 913px; HEIGHT: 350px" name="msg" rows="14"
				cols="111"></TEXTAREA>
			&nbsp;
		</p>
	</div>
</div>
	

	
		
	</body>

</html>
