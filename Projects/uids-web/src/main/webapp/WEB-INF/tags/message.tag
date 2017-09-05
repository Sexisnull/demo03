<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="msgMap" type="java.util.HashMap" required="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<% if (msgMap!=null && msgMap.get("type")!=""){ %>
<% if (msgMap.get("type").equals("success")){%>
	<div class="form-alertbox green" id="alert" style="display:none;">
<%}else if (msgMap.get("type").equals("error")){%>
	<div class="form-alertbox red" id="alert" style="display:none;">
<%}else{ %>
	<div class="form-alertbox green" id="alert" style="display:none;">
<%} %>
		<strong>${msgMap['msg']}</strong>
		<i class="form-alert-close"></i>
	</div>
<%} %>
<%session.removeAttribute("msgMap");%>