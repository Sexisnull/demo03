/**
 * 全局的ajax访问，处理ajax清求时sesion超时
 * 
 * @author taolm
 */   
$.ajaxSetup({  
    contentType:"application/x-www-form-urlencoded;charset=utf-8",  
    complete:function(XMLHttpRequest,textStatus){  
        //通过XMLHttpRequest取得响应头:sessionstatus，  
        var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
        var basepath = XMLHttpRequest.getResponseHeader("basepath");
        if ( sessionstatus == "timeout" ){         	
            window.location = basepath + "/gsjis/login.uids";  
        }  
    }  
});