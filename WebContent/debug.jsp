<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	buffer="16kb" autoFlush="true"%>
<%@page session="true"%>
<%@page import="java.util.*"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta NAME="ROBOTS" CONTENT="NOINDEX,NOFOLLOW,NOARCHIVE">
<title>Welcome to Debug</title>
</head>
<body>
	<h1>
		JSP Info
		<%=request.getServletPath()%></h1>
	<hr>
	<h2>Header</h2>
	<ol>
		<%
			for (String key : Collections.list(request.getHeaderNames())) {
				String value = request.getHeader(key);
		%>
		<li><%=key%><ol>
				<li><%=value%></li>
			</ol></li>
		<%
			}
		%>
	</ol>
	<hr>
	<h2>Request Parameter</h2>
	<ol>
		<%
			Map<String, String[]> params = request.getParameterMap();
			for (String key : params.keySet()) {
				String[] values = params.get(key);
		%>
		<li><%=key%><ol>
				<%
					for (String value : values) {
				%>
				<li><%=value%></li>
				<%
					}
				%>
			</ol></li>
		<%
			}
		%>
	</ol>
	<h2>Request Attribute</h2>
	<ol>
		<%
			for (String key : Collections.list(request.getAttributeNames())) {
				String value = request.getAttribute(key).toString();
		%>
		<li><%=key%><ol>
				<li><%=value%></li>
			</ol></li>
		<%
			}
		%>
	</ol>
	<hr>
	<h2>Session</h2>
	<ol>
		<%
			for (String key : Collections.list(session.getAttributeNames())) {
				String value = (String) session.getAttribute(key);
		%>
		<li><%=key%><ol>
				<li><%=value%></li>
			</ol></li>
		<%
			}
		%>
	</ol>
</body>
</html>
