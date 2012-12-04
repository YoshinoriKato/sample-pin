<%@page import="com.samplepin.common.Helper"%>
<%@page import="com.samplepin.Inquiry"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	buffer="16kb" autoFlush="true"%>
<%@page session="true"%>
<%@page import="java.util.*"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta NAME="ROBOTS" CONTENT="NOINDEX,NOFOLLOW,NOARCHIVE">
<title>Inquiries</title>
</head>
<body>
	<h1>
		JSP Info
		<%=request.getServletPath()%></h1>
	<hr>
	<h2>Inquiries</h2>
	<ol>
		<%
			for (Inquiry inquiry : Helper.getInquiries()) {
				String message = inquiry.getMessage();
		%>
		<li><%=Helper.formatToDateString(inquiry.getCreateDate())%><ol>
				<li><%=inquiry.getMail() != null
						&& !inquiry.getMail().isEmpty() ? inquiry.getMail()
						: "no-mail"%>
					: <%=Helper.escapeHTML(inquiry.getMessage())%></li>
			</ol></li>
		<%
			}
		%>
	</ol>
	<hr>
</body>
</html>
