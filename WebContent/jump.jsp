<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
</head>
<%
	String userId = (String) session.getAttribute("userId");
	String cardId = request.getParameter("cardId");
	String redirectUrl = request.getParameter("redirectUrl");
	response.sendRedirect(redirectUrl);
	Card card = Helper.getCardInfoByID(cardId);
	Helper.setFootprint(card, userId);
%>
<body>
	<jsp:include page="_topbar.jsp"></jsp:include>
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="hero-unit deco">
					<h1>Jump</h1>
					<p>
						<a href="<%=redirectUrl%>" target="_blank"><%=redirectUrl%></a>
					</p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>