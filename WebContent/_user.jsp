<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%!User user;%>

<%
	user = (User) request.getAttribute("user");
	if (user != null) {
%>

<div id="<%=user.getUserId()%>" class="cell">
	<div>
		<img class="image-shot deco link" src="<%=user.getImagePath()%>"
			id="image-shot">
	</div>
	<div class="bold deco break-word">
		<%=Helper.escapeHTML(user.getUserName())%>
	</div>
	<div class="caption deco"><%=Helper.escapeHTML(user.getComment())%></div>
	<div class="star right" style="clear: both;">
		Signed up:
		<%=Helper.formatToDateString(user.getCreateDate())%>
	</div>
</div>

<%
	}
%>
