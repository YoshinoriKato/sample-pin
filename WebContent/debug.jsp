<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	buffer="16kb" autoFlush="true" errorPage="error.jsp"%>
<%@page session="true"%>
<%@page import="java.util.*"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<title>Welcome to Debug</title>
<jsp:include page="_header.jsp" flush="true" />
</head>
<body>
	<h1>
		JSP Info
		<%=request.getServletPath()%></h1>
	<hr>
	<h2>Request</h2>
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
