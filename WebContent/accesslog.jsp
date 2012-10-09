<%@page import="com.samplepin.common.Helper"%>
<%@page import="com.samplepin.Header"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
	buffer="16kb" autoFlush="true" errorPage="error.jsp"%>
<%@page session="true"%>
<%@page import="java.util.*"%>

<!DOCTYPE html>
<html lang="ja">
<head>
<meta NAME="ROBOTS" CONTENT="NOINDEX,NOFOLLOW,NOARCHIVE">
<title>Welcome to Access Log</title>
</head>
<body>
	<h2>Session</h2>
	<ol>
		<%
			for (Header header : Helper.getHeaders()) {
		%>
		<li><ol>
				<li>create-date:_____<%=Helper.formatToDateTimeString(header.getCreateDate())%></li>
				<li>protocol:________<%=header.getProtocol()%></li>
				<li>host:____________<%=header.getHost()%></li>
				<li>remote-address:__<%=header.getRemoteAddress()%></li>
				<li>uri:_____________<%=header.getUri()%></li>
				<li>user-agent:______<%=header.getUserAgent()%></li>
				<li>accept:__________<%=header.getAccept()%></li>
				<li>accept-charset:__<%=header.getAcceptCharset()%></li>
				<li>accept-encoding:_<%=header.getAcceptEncoding()%></li>
				<li>accept-language:_<%=header.getAcceptLanguage()%></li>
				<li>cache-control:___<%=header.getCacheControl()%></li>
				<li>connection:______<%=header.getConnection()%></li>
				<%-- <li>cookie:__________<%=header.getCookie()%></li> --%>
			</ol>
			</li>
		<%
			}
		%>
	</ol>
</body>
</html>
