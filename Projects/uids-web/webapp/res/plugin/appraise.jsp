<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>

<head>
        <title>评价</title>

</head>

<Script language="javascript">
		//接收按键
		function receive(){		
			var flag=0;
			var ckarray=AppOcx.GetComAndDeviceID(1, 8);
			if(ck==""){
				alert("设备未找到！");
			}else{
				var ck=ckarray.split("-");
	            var resutl = AppOcx.Appraisal(ck[0],ck[1], 1);//8 1 1
				AppOcx.SetInterval(ck[0],ck[1], 15);//8 1 15
				var key = AppOcx.GetRecord(ck[0],ck[1]);//8 1
				while(key=="-1"&&flag<15){
					flag++;
					key = AppOcx.GetRecord(ck[0],ck[1]);
				}
				AppOcx.SetStar(8,1,parseInt(key)+1)
				
				document.getElementById("operation").value+=key+"\n\n";;
					if(key=="1"){
							document.all.jieguo.value=0;
							document.getElementById("temppystate").value=5;
							//document.all.form1.submit();
					}
					else if(key=="2"){
							document.all.jieguo.value=1;
							document.getElementById("temppystate").value=4;
							//document.all.form1.submit();
					}
					else if(key=="3"){
							document.all.jieguo.value=2;
							document.getElementById("temppystate").value=3;
							//document.all.form1.submit();
							}
					else if(key=="4"){
							document.all.jieguo.value=3;
							document.getElementById("temppystate").value=2;
							//document.all.form1.submit();
					}
					else if(key=="5"){
		                    document.all.jieguo.value=4;
		                    document.getElementById("temppystate").value=1;
							//document.all.form1.submit();
					}
					else if(key=="6"){
							document.all.jieguo.value=5;
							document.getElementById("temppystate").value=0;		
							//document.all.form1.submit();
					}
					else if(key=="0"){
							document.all.jieguo.value=5;
							document.getElementById("temppystate").value=0;		
							//document.all.form1.submit();
					}
					

				}
				
		}
		//function onPageLoad(){
			//setTimeout("receive()",1000);
		//}
		//星级
		 function setstar() {
            var result = AppOcx.SetStar(3,0,3); //设置5
            window.alert(result);
        }
		
		//欢迎光临
		function Startwelcome() {
		
				var resutl = AppOcx.welcome(8,0,1);
	
				if (resutl == 0) {
                window.alert("调用Welcome成功");
				}
				 else {
					window.alert("调用Welcome失败！");
				}
	
			
		}
		 
		 //请求评价
		function Startappraise() {
            var resutl = AppOcx.Appraisal(8,0, 1);
            if (resutl == 0) {
                window.alert("调用Appraisal成功");
            }
			function getrecord() {

				readdata();
			}
        }
		//重置
		function reset(){
			var pjqobj = document.getElementById('pjqobj');
			pjqobj.reset();	
		}
</script>
<BODY><!--加载页面时初始化控件，结束了释放掉-->
<div align="center">	
<br/>
<img src="./images/1.png" align="center" OnClick="receive()" id="dopj"/>
</div>
<br/>
<P>

<object
   classid="clsid:DE7E4ED4-BB95-457B-BDBA-43F6DEF595EE" id="AppOcx" width="0" height="0">
   
</object>

<input id=selectEvalueType name=selectEvalueType type="hidden" size=3 value="3"/>
<input name="selectPort" type="hidden" size=3 value="4"/>
<input id=selectBRate name=selectBRate type="hidden" size=3 value="9600"/>
<INPUT id=TextAddr type="hidden" size=7 value=1 name=TextAddr>
<input name="pjResult" type="hidden" size=3 value=1/>
<Input Type="Button" OnClick="Startappraise()" value="请求评价">
<Input Type="Button" OnClick="Startwelcome()" value="欢迎光临">
<Input Type="Button" OnClick="SetComPort()" value="设置串口">
<input Type="Text" id="ComPort" value="-1"/>
<textarea id="operation" readonly="readonly"  style="width:250px; height: 300px;"></textarea>
</P>
    <form name="form1" method="post" action="">
    <input type="hidden" name="pydvo.pystate" id="temppystate" value="0"/><!-- 默认是非常满意 -->
    <input type="hidden" name="pydvo.businessid" value="<%=bvo.getPid() %>"/>
    <input type="hidden" name="pydvo.source" value="2"/>
    <input type="hidden" name="pydvo.pydate" value="<%=nowdate %>"/>
   
        <input type="hidden" name="isdeal" value="1"/>
        <input type="hidden" name="flag" value="1"/>
        <input type="hidden" name="jieguo" value="1"/>
        <input type="hidden" name="tijiao" value="" size=1>
        <!-- <input type="submit" value="评议"> -->
    </form>
</body>
</html>
