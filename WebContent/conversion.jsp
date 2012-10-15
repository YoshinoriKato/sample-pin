<%@page import="java.net.URLDecoder"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<META http-equiv="refresh" CONTENT="15; URL=home.jsp">
<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_effect.jsp"></jsp:include>
</head>

<body class="home" id="full-body">
	<jsp:include page="_topbar.jsp"></jsp:include>
	<table height="100%" width="100%" class="gradient-white">
		<tbody>
			<tr>
				<td align="center" valign="middle">
					<div>
						<h1>
							ようこそ
							<%=Helper.NAME%>
							へ
						</h1>
						<p class="margin-top-default">ホーム画面から登録ユーザー専用の機能をご利用ください。</p>
						<p class="margin-top-default">
							<a href="home.jsp" class="btn btn-large">Home</a>
						</p>
					</div>
				</td>
			</tr>
		</tbody>
	</table>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>