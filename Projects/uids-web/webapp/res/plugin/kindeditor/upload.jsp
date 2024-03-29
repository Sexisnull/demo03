<%@ page language="java" pageEncoding="GBK"%>
<%@page
	import="java.util.*,java.io.*,
	org.apache.struts2.dispatcher.multipart.MultiPartRequestWrapper,
	java.util.concurrent.locks.*"%>
<%
	//Struts2  请求 包装过滤器
	MultiPartRequestWrapper wrapper = (MultiPartRequestWrapper) request;
	// 获得上传的文件名 
	String fileName = wrapper.getFileNames("imgFile")[0];
	//获得未见过滤器 
	File file = wrapper.getFiles("imgFile")[0];
	//----------- 重新构建上传文件名----------------------
	final Lock lock = new ReentrantLock();
	String newName = null;
	lock.lock();
	try {
		//加锁为防止文件名重复 
		newName = System.currentTimeMillis()
				+ fileName.substring(fileName.lastIndexOf("."),
						fileName.length());
	}finally {
		lock.unlock();
	}
	//------------ 锁结束 -------------
	//获取文件输出流 
	FileOutputStream fos = new FileOutputStream(request.getSession()
			.getServletContext().getRealPath("/")
			+ "upload/kindeditorImage\\" + newName);
	//设置 KE 中的图片文件地址 
	String newFileName = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ request.getContextPath() + "/upload/kindeditorImage/" + newName;
	byte[] buffer = new byte[1024];
	//获取内存中当前文件输入流 
	InputStream in = new FileInputStream(file);
	try {
		int num = 0;
		while ((num = in.read(buffer)) > 0) {
			fos.write(buffer, 0, num);
		}
	} catch (Exception e) {
		e.printStackTrace(System.err);
	} finally {
		in.close();
		fos.close();
	}
	//发送给KE 
	out.println("<html><head><title>Insert Image</title><meta http-equiv='content-type' content='text/html; charset=gbk'/></head><body>");
	out.println("<script type='text/javascript'>");
	out.println("parent.parent.KE.plugin['image'].insert('"
			+ wrapper.getParameter("id") + "','" + newFileName + "','"
			+ wrapper.getParameter("imgTitle") + "','"
			+ wrapper.getParameter("imgWidth") + "','"
			+ wrapper.getParameter("imgHeight") + "','"
			+ wrapper.getParameter("imgBorder") + "','"
			+ wrapper.getParameter("align") + "');</script>");
	out.println("</body></html>");
%>