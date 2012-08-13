<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
</head>

<%
	String userId = (String)session.getAttribute("userId");
	User user = Helper.getUserById(userId);
%>

<body>
	<jsp:include page="topbar.jsp"></jsp:include>
	<div id="main">
		<div class="container">
			<div class="row">
				<h1>Account</h1>
				<div><%=user.getUserName() %></div>
				<div><%=user.getMail() %></div>
				<div><%=user.getBirthDay() %></div>
			</div>
		</div>
	</div>
</body>
</html>