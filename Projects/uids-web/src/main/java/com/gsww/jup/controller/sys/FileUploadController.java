/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.controller.sys;

import java.io.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gsww.jup.controller.BaseController;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@Controller
@RequestMapping(value = "/fileUpload")
public class FileUploadController extends BaseController {
	
	private static final int BUFFER_SIZE = 16 * 1024 ;
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@RequestMapping(value="/fileUploadToDisk", method = RequestMethod.POST)
	public void uploadFile(HttpServletRequest request, HttpServletResponse response){
		String name="";
		String extName = "";
        System.err.println("!!!!!!!!!fileUploadStart!!!!!!!!!!!!!!!");
        try {
        	String path = request.getContextPath();
        	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			   String filePath = basePath + "uploads";
			   System.err.println("filePath="+filePath+ "/");
			   java.util.Enumeration<Object> e = request.getHeaderNames();
			   System.err.println("start");
			   for (Object object = null;e.hasMoreElements();){
				   object = e.nextElement();
			   }
			   System.err.println("end");
			   File f1 = new File(filePath);
			   if (!f1.exists()) {
				   f1.mkdirs();
			
			   }
			   request.setCharacterEncoding("UTF-8");
			   DiskFileItemFactory factory = new DiskFileItemFactory();
			   factory.setSizeThreshold(BUFFER_SIZE);
			   factory.setRepository(new File(filePath));
				
			   ServletFileUpload upload = new ServletFileUpload(factory);
			   //允许上传的文件最大1GB
			   upload.setFileSizeMax(1024 * 1024 * 1024);
			   //文件最大1GB
			   upload.setSizeMax(1024 * 1024 * 1024);
			   List items = (List) upload.parseRequest(request);
				
			   Iterator iter = items.iterator();
			   System.err.println(iter.hasNext());
			   while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
	
					if(item.isFormField()) {
							name = item.getFieldName();
							if (name.lastIndexOf(".") >= 0) {

			                    extName = name.substring(name.lastIndexOf("."));

			                }

							
					}else {
						String fieldName = item.getFieldName();
						String fileName = item.getName();
						String contentType = item.getContentType();
						boolean isInMemory = item.isInMemory();
						long sizeInBytes = item.getSize();
						System.err.println("fileName:"+fileName);

						
						//
						File uploadFile = new File(filePath + "/" + fileName);  
						item.write(uploadFile);

						System.err.println(name + extName);
						response.setContentType("text/xml;charset=UTF-8"); 

						response.getWriter().write(fileName);
						//返回已经上传文件
						response.getWriter().flush();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
	}
}
