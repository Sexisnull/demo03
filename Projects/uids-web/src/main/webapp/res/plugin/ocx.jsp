<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
	<%@ include file="/include/meta.jsp"%>
	<head>
		<title></title>
		<style type="text/css">
.list-toolbar ul {
	margin: 5px 40px;
}
#operation{
	resize: none;
}
.list-toolbar ul li a {
	margin-right: 40px;
}

.list-toolbar ul li {
	margin-left: -35px;
}
</style>
		<script type="text/javascript">
		 var mytime;  //全局变量，定时器
    var maxwait = 15;  	
//接收按键
function receive() {
	var flag = 0;
	var ckarray = AppOcx.GetComAndDeviceID(1, 8);
	if (ckarray == "") {
		alert("设备未找到！");
	} else {
		var ck = ckarray.split("-");
		var resutl = AppOcx.Appraisal(ck[0], ck[1], 1);//8 1 1
		AppOcx.SetInterval(ck[0], ck[1], 15);//8 1 15
		var key = AppOcx.GetRecord(ck[0], ck[1]);//8 1
		while (key == "-1" && flag < 15) {
			flag++;
			key = AppOcx.GetRecord(ck[0], ck[1]);
		}
		AppOcx.SetStar(ck[0], ck[1], parseInt(key) + 1)

		document.getElementById("operation").value += key + "\n\n";

	}
}
//function onPageLoad(){
//setTimeout("receive()",1000);
//}
//星级
function setstar() {
	var result = AppOcx.SetStar(3, 0, 3); //设置5
	window.alert(result);
}
function wel() {
	var ckarray = AppOcx.GetComAndDeviceID(1, 8);
	if (ckarray == "") {
		alert("设备未找到！");
	} else {
		var ck = ckarray.split("-");
		var resutl = AppOcx.Welcome(ck[0], ck[1], 1);
		if (resutl == 0) {
				document.getElementById("operation").value += "调用Welcome成功！\n\n";
				document.getElementById("operation").value += "串口号：" +ck[0]+"\t设备号："+ck[1]+ "\n\n";
		} else {
				document.getElementById("operation").value += "调用Welcome失败！\n\n";
		}
		AppOcx.SetStar(ck[0], ck[1], 1);
		AppOcx.SetStar(ck[0], ck[1], 2);
		AppOcx.SetStar(ck[0], ck[1], 3);
		AppOcx.SetStar(ck[0], ck[1], 4);
		AppOcx.SetStar(ck[0], ck[1], 5);
		AppOcx.SetStar(ck[0], ck[1], 0);
		
	}
}
function showtime() {
	    if (maxwait >= 1) {
	        maxwait = maxwait - 1;
	        document.getElementById("btntime").value = maxwait;
	    }
	    else {

	        clearTimeout(mytime);
	        on_click_DllGetRecordOnTopAndAutoDelete();
	    }
	}
//定时器  客户参与评价
	function on_click_judger() {
        //发完语音必须等待 评价时间（默认10S）  后才能取数据 ,只用取一次！！！！(相当于10s内如果未评价，超过10S用也不能评价了！)
	    mytime = setInterval('showtime()', 1500);
	     
	}
 //得到当前的系统时间
	function getNowDateTime(){
		var date=new Date();
		var year=date.getYear();
		var month=addZeroSuffix(date.getMonth()+1);
		var day=addZeroSuffix(date.getDate());
		var hour=addZeroSuffix(date.getHours());
		var minute=addZeroSuffix(date.getMinutes());
		var second=addZeroSuffix(date.getSeconds());
		var now=year+"-"+month+"-"+day+"-"+hour+"-"+minute+"-"+second;
		return now;
	}
	//补0前缀
	function addZeroSuffix(number){
		if(number<10){
			number="0"+number;
		}
		return number
	}
</script>
	</head>
	<body >
		<object classid="clsid:DE7E4ED4-BB95-457B-BDBA-43F6DEF595EE" id="AppOcx"  width="0"  height="0">
		</object>
		&nbsp;
		<div class="list-warper">
			<div class="search-content">
				<div class="list-toolbar" style="border: 0px;">
					<ul>
						<li onclick="wel()" class="query" value="welcome" id="btn3">
							<a>欢迎</a>
						</li>
						<li onclick="receive()" class="del" value="Apprise" id="Button4">
							<a>评价</a>
						</li>
					</ul>
				</div>
			</div>
		</div>
		<p align="center">
			<textarea id="operation" readonly="readonly"
				style="width: 800px; height: 300px;" cols="" rows="" TYPE="application/x-itst-activex" >
			</textarea>
		</p>
	</body>
</html>