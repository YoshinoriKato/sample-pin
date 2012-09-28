<%@page import="com.samplepin.common.Helper"%>
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String url = request.getRequestURI();
	String logined = (String)request.getAttribute("login");
%>

<!-- 共有 -->

<div style="display: none" id="page"><%=url%></div>

<div id="copyrights" class="center caption large">&copy; <%=Helper.NAME %></div>

<div id="ajax">
	<% if(logined !=null) { %>
		<div class="caption star">Auto login...</div>
	<% } %>
	<img src="img/ajax-loader.gif">
	<div class="caption star">Working for you.</div>
</div>

<div id="error-dialog">
	<div id="error-close" class="close-button">x</div>
	<img src="img/ERROR.png?<%=Helper.TIMESTAMP %>" class="image-shot">
	<div class="caption large">Sorry, server error.</div>
</div>

<a href="#top"><span id="bottom-label">Go top</span></a>

<script type="text/javascript" charset="UTF-8">
	attach(window, 'load', function() {	
		$('#error-dialog').attr("onclick",
		"pushPull('#null','#error-dialog')");
	});
</script>

