/**
 * Copyright 中国电信甘肃万维公司 All rights reserved.
 * 中国电信甘肃万维公司 专有/保密源代码,未经许可禁止任何人通过任何* 渠道使用、修改源代码.
 */
package com.gsww.jup.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class FileUploadServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8382832509729035231L;
	private static final int BUFFER_SIZE = 16 * 1024 ;
	
	private String bizId="";
    private String relId="";
    private String configId="";
    private String fileType="";
    private String subPath="";
	/**
	 * Constructor of the object.
	 */
	public FileUploadServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		uploadFiles(request, response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
	}
	/**
	 * 实现多文件的同时上传
	 * @param request
	 * @param response
	 * @return
	 */
	private String uploadFiles(HttpServletRequest request, HttpServletResponse response){
		String name="";
        String extName = "";
		try {
			   String filePath = this.getServletConfig().getServletContext().getRealPath("") + "/uploads/";
			   java.util.Enumeration<Object> e = request.getHeaderNames();
			   for (Object object = null;e.hasMoreElements();){
				   object = e.nextElement();
				 //  System.out.println(object + ": " + request.getHeader(""+object));
			   }
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
			   //System.err.println(iter.hasNext());
			   while (iter.hasNext()) {
					FileItem item = (FileItem) iter.next();
	
					if(item.isFormField()) {
							name = item.getFieldName();
							if (name.lastIndexOf(".") >= 0) {

			                    extName = name.substring(name.lastIndexOf("."));

			                }

							String value = item.getString();
							if(name.equals("bizId")){
								bizId = value;
							}else if(name.equals("fileType")){
								fileType = value;
							}else if(name.equals("configId")){
								configId = value;
							}else if(name.equals("subPath")){
								subPath = value;
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

						//System.err.println(name + extName);
						response.setContentType("text/xml;charset=UTF-8"); 

						response.getWriter().write(fileName);
						//返回已经上传文件
						response.getWriter().flush();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		 return null;
	}
}
