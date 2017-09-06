<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="org.dom4j.*"%>
<%@page import="java.io.FileOutputStream"%>
<%@page import="com.hanweb.sso.ldap.util.MD5"%>
<%@page import="com.hanweb.receive.UserGroupBLF"%>
<%@page import="org.w3c.dom.Node"%>
<%@page import="org.dom4j.io.XMLWriter"%>
<%@page import="org.dom4j.io.SAXReader"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%
	request.setCharacterEncoding("utf-8");
	Map paramMap = new HashMap();
	//是否成功标志位
	boolean isSuccess = false;
	String state = request.getParameter("state") == null ? "" : request
			.getParameter("state");
	String result = request.getParameter("result") == null ? ""
			: request.getParameter("result");
	String ssourl = request.getParameter("ssourl") == null ? ""
			: request.getParameter("ssourl");
	String ndlogin = request.getParameter("ndlogin") == null ? ""
			: request.getParameter("ndlogin");

	if (state.equals("S")) {
		/**应用注册写xml*/
		paramMap.put("enckey", request.getParameter("enckey"));
		paramMap.put("sysurl", request.getParameter("sysurl"));
		paramMap.put("encrypttype", request.getParameter("encrypttype"));
		paramMap.put("appname", request.getParameter("appname"));
		paramMap.put("ldapurl", request.getParameter("ldapurl"));
		paramMap.put("onlydecryptpaw", request.getParameter("onlydecryptpaw"));

		String xmlPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "/interface/ldap/ldapconf.xml";
		try {
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(xmlPath);
			Element element = (Element) doc
					.selectSingleNode("/ldap/appname");
			element.getText();
			if (element != null) {
				element.setText((String) paramMap.get("appname"));
			}
			element = (Element) doc.selectSingleNode("/ldap/enckey");
			if (element != null) {
				element.setText((String) paramMap.get("enckey"));
			}
			element = (Element) doc.selectSingleNode("/ldap/ldapurl");
			if (element != null) {
				element.setText((String) paramMap.get("ldapurl"));
			}
			element = (Element) doc
					.selectSingleNode("/ldap/encrypttype");
			if (element != null) {
				element.setText((String) paramMap.get("encrypttype"));
			}

			/** 将document中的内容写入文件中 */
			XMLWriter writer = new XMLWriter(new FileOutputStream(
					request.getSession().getServletContext()
							.getRealPath("/")
							+ "/interface/ldap/ldapconf.xml"));
			writer.write(doc);
			writer.close();
		} catch (Exception e) {
			isSuccess = false;
		}
	}

	//2,用户实时同步->新增、修改
	else if (state.equals("C") && result.equals("T")) {
		String xmlPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "/interface/ldap/ldapconf.xml";
		String encrypttype = this.getXmlNodeText(xmlPath,
				"/ldap/encrypttype");
		String enckey = this.getXmlNodeText(xmlPath, "/ldap/enckey");
		String onlydecryptpaw = this.getXmlNodeText(xmlPath, "/ldap/onlydecryptpaw");
		String loginuser = request.getParameter("loginuser");
		String loginpass = request.getParameter("loginpass");
		//解密
		if (encrypttype.equals("1")) {
			MD5 md5 = new MD5();
			if("1".equals(onlydecryptpaw)){
			}else{
				loginuser = md5.decrypt(loginuser, enckey);
			}
			loginpass = md5.decrypt(loginpass, enckey);
			
		} else if (encrypttype.equals("2")) {
			MD5 md5 = new MD5();
			if("1".equals(onlydecryptpaw)){
			}else{
				loginuser = md5.decryptMB(loginuser, enckey);
			}
			loginpass = md5.decryptMB(loginpass, enckey);
		}

		paramMap.put("pardomainname", request.getParameter("pardomainname"));
		paramMap.put("loginpass", loginpass);
		paramMap.put("Domainid", request.getParameter("Domainid"));
		paramMap.put("domainname", request.getParameter("domainname"));
		paramMap.put("email", request.getParameter("email"));
		paramMap.put("appname", request.getParameter("appname"));
		paramMap.put("loginuser", loginuser);
		paramMap.put("t_name", request.getParameter("t_name"));
		paramMap.put("alldomainname", request.getParameter("alldomainname"));
		paramMap.put("mobile", request.getParameter("mobile"));

		UserGroupBLF userGroupBLF = new UserGroupBLF();
		//如果该机构不存在那么这次同步失败
		if (userGroupBLF.isGroupExist(paramMap)){
			//如果用户存在	则修改，否则新增
			if (userGroupBLF.isUserExist(paramMap)) {
				isSuccess = userGroupBLF.doUserUpdateExcute(paramMap);
			} else {
				isSuccess = userGroupBLF.doUserInsertExcute(paramMap);
			}
		}
		else{
			isSuccess=false;
		}
		//如果是用户同步值为1否则无值为默认为""
		if (!ndlogin.equals("1")) {
			//ssourl有值的话，则跳向ssourl指定的位置，否则跳转到用户配置的登录地址
			if (ssourl != null && !"".equals(ssourl)
					&& ssourl.length() > 5) {
				out.println("<script language='javascript'>");
				out.println("location.href='" + ssourl + "'");
				out.println("</script>");
				return;
			}
			//判断web是为了兼容老接口
			if ("".equals(ssourl)
					|| "0".equals(request.getParameter("web"))) {
				if (userGroupBLF.isCanPass(paramMap)) {
					//得到xml所配置的参数
					Map map = this.getXmlNodeMap(xmlPath, "loginform");
					out
							.println("<form name='FormName' method='post' action='"
									+ map.get("action") + "'>");
					Set keys = map.keySet();
					for (Iterator it = keys.iterator(); it.hasNext();) {
						String key = (String)it.next();
						if (key != null && !key.equals("action")) {
							String value = (String) map.get(key);
							if (value.equals("{loginuser}")) {
								out
										.println("<input type='hidden'  name='"
												+ key
												+ "' value='"
												+ (String) paramMap
														.get("loginuser")
												+ "'>");
							} else if (value.equals("{loginpass}")) {
								out
										.println("<input type='hidden'  name='"
												+ key
												+ "' value='"
												+ (String) paramMap
														.get("loginpass")
												+ "'>");
							} else {
								out
										.println("<input type='hidden'  name='"
												+ key
												+ "' value='"
												+ map.get(key) + "'>");
							}
						}
					}

					out.println("</form>");
					out.println("<script language='javascript'>");
					out.println("document.FormName.submit()");
					out.println("</script>");
				} else {
					String strAlert = "alert('单点登录失败，请检查！');";
					out.println("<script>" + strAlert + "</script>");
					
					return ;
				}
			}
		}
	}

	//3,用户实时同步->删除
	else if (state.equals("D") && result.equals("D")) {
		String xmlPath = request.getSession().getServletContext()
				.getRealPath("/")
				+ "/interface/ldap/ldapconf.xml";
		String encrypttype = this.getXmlNodeText(xmlPath,
				"/ldap/encrypttype");
		String enckey = this.getXmlNodeText(xmlPath, "/ldap/enckey");
		String loginuser = request.getParameter("loginuser");

		//解密
		if (encrypttype.equals("1")) {
			MD5 md5 = new MD5();
			if("1".equals(onlydecryptpaw)){
			}else{
				loginuser = md5.decrypt(loginuser, enckey);
			}
			
		} else if (encrypttype.equals("2")) {
			MD5 md5 = new MD5();
			if("1".equals(onlydecryptpaw)){
			}else{
				loginuser = md5.decryptMB(loginuser, enckey);
			}
		}
		paramMap.put("loginuser", loginuser);
		UserGroupBLF userGroupBLF = new UserGroupBLF();
		isSuccess = userGroupBLF.doUserDeleteExcute(paramMap);
	}

	//4,机构实时同步新增、修改
	else if (state.equals("C") && result.equals("TG")) {
		paramMap.put("Domainid", request.getParameter("Domainid"));
		paramMap.put("domainname", request.getParameter("domainname"));
		paramMap.put("Pardomainid", request.getParameter("Pardomainid"));
		paramMap.put("pardomainname", request.getParameter("pardomainname"));
		paramMap.put("alldomainname", request.getParameter("alldomainname"));
		UserGroupBLF userGroupBLF = new UserGroupBLF();
		//如果机构存在	则更新，否则增加
		if (userGroupBLF.isGroupExist(paramMap))
			isSuccess = userGroupBLF.doGroupUpdateExcute(paramMap);
		else
			isSuccess = userGroupBLF.doGroupInsertExcute(paramMap);
	}

	//5,机构实时同步 ->删除
	else if (state.equals("C") && result.equals("DG")) {
		paramMap.put("Domainid", request.getParameter("Domainid"));
		UserGroupBLF userGroupBLF = new UserGroupBLF();
		isSuccess = userGroupBLF.doGroupDeleteExcute(paramMap);
	}
%>
<%=isSuccess == true ? "OK" : "error"%>

<%!//读取给定的xml文件节点值
	public String getXmlNodeText(String xmlPath, String nodeName) {
		try {
			SAXReader saxReader = new SAXReader();
			Document doc = saxReader.read(xmlPath);
			Element element = (Element) doc.selectSingleNode(nodeName);
			if (element != null) {
				return element.getText();
			} else
				return "";
		} catch (Exception e) {
			return "";
		}
	}

	//读取用户配置的form表单中的参数
	public Map getXmlNodeMap(String path, String nodeName) {
		Map map = new java.util.HashMap();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File(path));
			List projects = doc.selectNodes("/ldap/loginform/hidden");
			Iterator it = projects.iterator();
			while (it.hasNext()) {
				Element elm = (Element) it.next();
				{
					String id = elm.attribute("id").getValue();
					String value = elm.attribute("value").getValue();
					map.put(id, value);
				}
			}

		} catch (Exception ex) {
			map=null;
		}
		return map;
	}%>