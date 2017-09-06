/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.gsww.jup.util.ReadProperties;
import com.gsww.jup.util.UploadFileToServer;

/**
 * 
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>公司名称 : 中国电信甘肃万维公司</p>
 * <p>项目名称 : jup-web</p>
 * <p>创建时间 : 2014-7-24 下午04:15:31</p>
 * <p>类描述 :附件上传组件控制类         </p>
 *
 *
 * @version 1.0.0
 * @author <a href=" ">zhangxj</a>
 */
@Controller
@RequestMapping(value="/upload")
public class UploadFiledController {

	@RequestMapping(value="/uploadToDisk")
	public  @ResponseBody String uploadToDisk(
			@RequestParam(value = "fileName")File fileName,
			HttpServletRequest hrequest){
		ReadProperties rp = new ReadProperties("/ApplicationResources.properties");
		String realPath=hrequest.getSession().getServletContext().getRealPath("/");
		String filePath=rp.getProperty("uploadfile.path").toString();
		String allPath=filePath+fileName.getName();
		boolean b=UploadFileToServer.uploadToDisk(fileName, realPath+allPath);
		if(b){			
			return allPath;
		}else{
			return "false";
		}
	}
}
