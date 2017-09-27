package com.gsww.jup.controller.sys;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gsww.jup.controller.BaseController;
import com.gsww.jup.entity.sys.SysOperator;
import com.gsww.jup.service.sys.SysOperatorService;
import com.gsww.jup.util.RSAUtil;

@Controller
@RequestMapping(value = "/sys/mybatis")
public class MybatisController extends BaseController{
        
        @Autowired
        private SysOperatorService sysOperatorService;
        
        @RequestMapping(value = "/show", method = RequestMethod.POST)
        @ResponseBody
        public  List<SysOperator> accountView(String userAcctId,Model model,HttpServletRequest request,HttpServletResponse response)  throws Exception {                
                
                List<SysOperator> list=sysOperatorService.findList(null);
                return list;
        }
        
        @RequestMapping(value = "/getkey")  
        @ResponseBody 
        public Object getPublicKey(HttpServletRequest request)throws Exception{
                 Map<String,String> result = new HashMap<String,String>(); 
                 KeyPair kp = RSAUtil.generateKeyPair(request);
                 RSAPublicKey pubk = (RSAPublicKey) kp.getPublic();// 生成公钥
                 RSAPrivateKey prik = (RSAPrivateKey) kp.getPrivate();// 生成私钥
                 
                 String publicKeyExponent = pubk.getPublicExponent().toString(16);// 16进制
                 String publicKeyModulus = pubk.getModulus().toString(16);// 16进制
                 request.getSession().setAttribute("prik", prik);
                 result.put("pubexponent", publicKeyExponent);
                 result.put("pubmodules", publicKeyModulus);
                 return result;  
        }

}