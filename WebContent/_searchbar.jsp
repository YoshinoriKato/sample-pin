<%@page import="com.samplepin.common.Helper"%>
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String userId = Helper.getUserId(request);
%>

<div id="add-card">
	<%
		if (Helper.valid(userId)) {
	%>
	<div class="center cell" style="padding-top: 25px">
		<jsp:include page="_search.jsp"></jsp:include>
	</div>
	<%
		} else {
	%>
	<div class="caption large center">
		Please, <a href="login.jsp?fromUrl=home.jsp">Login</a> or <a
			href="signup.jsp">Sign up</a>.
	</div>
	<%
		}
	%>
</div>
