/**
 * 禁止鼠标右键
 * */
function stop(){return false;}
document.oncontextmenu=stop;



/**
 * 禁止Google浏览器页面放大缩小
 * */
var scrollFunc=function(e){ 
	  e=e || window.event; 
	  if(e.wheelDelta && event.ctrlKey){//IE/Opera/Chrome 
	   event.returnValue=false;
	  }else if(e.detail){//Firefox 
	   event.returnValue=false; 
	  } 
	 }  
	 
/*注册事件*/ 
if(document.addEventListener){ 
document.addEventListener('DOMMouseScroll',scrollFunc,false); 
}//W3C 
window.onmousewheel=document.onmousewheel=scrollFunc;//IE/Opera/Chrome/Safari 

function getInfo(){ 
    var s = "";   
     s += " 网页可见区域宽："+ document.body.clientWidth+"\n";    
     s += " 网页可见区域高："+ document.body.clientHeight+"\n";    
     s += " 网页可见区域宽："+ document.body.offsetWidth + " (包括边线和滚动条的宽)"+"\n";    
     s += " 网页可见区域高："+ document.body.offsetHeight + " (包括边线的宽)"+"\n";    
     s += " 网页正文全文宽："+ document.body.scrollWidth+"\n";    
     s += " 网页正文全文高："+ document.body.scrollHeight+"\n";    
     s += " 网页被卷去的高(ff)："+ document.body.scrollTop+"\n";    
     s += " 网页被卷去的高(ie)："+ document.documentElement.scrollTop+"\n";    
     s += " 网页被卷去的左："+ document.body.scrollLeft+"\n";    
     s += " 网页正文部分上："+ window.screenTop+"\n";    
     s += " 网页正文部分左："+ window.screenLeft+"\n";    
     s += " 屏幕分辨率的高："+ window.screen.height+"\n";    
     s += " 屏幕分辨率的宽："+ window.screen.width+"\n";    
     s += " 屏幕可用工作区高度："+ window.screen.availHeight+"\n";    
     s += " 屏幕可用工作区宽度："+ window.screen.availWidth+"\n";    
     s += " 你的屏幕设置是 "+ window.screen.colorDepth +" 位彩色"+"\n";    
     s += " 你的屏幕设置 "+ window.screen.deviceXDPI +" 像素/英寸"+"\n";    
     alert (s);
   }
   getInfo();