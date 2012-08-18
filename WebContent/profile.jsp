<%@page import="java.util.List"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_effect.jsp"></jsp:include>
</head>

<%
	String userId = request.getParameter("userId");
	userId = userId != null ? userId : (String) session
			.getAttribute("userId");
	User user = Helper.getUserById(userId);

	String message = (String) request.getAttribute("message");
	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";
%>

<body>
	<jsp:include page="_topbar.jsp"></jsp:include>
	<div id="title">Profile</div>
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="cell">
					<div class="profile">
						<h3>Account</h3>
						<div class="item-group">
							<div class="item-label">Image</div>
							<div class="items">
								<div>
									<img src="<%=user.getImagePath()%>" class="image-shot">
								</div>
							</div>
						</div>
						<div class="item-group">
							<div class="item-label">Create Account</div>
							<div class="items"><%=Helper.formatToDateString(user.getCreateDate())%>
							</div>
						</div>
						<div class="item-group">
							<div class="item-label">Name</div>
							<div class="items"><%=Helper.escapeHTML(user.getUserName())%>
							</div>
						</div>
						<div class="item-group">
							<div class="item-label">Comment</div>
							<div class="items"><%=Helper.escapeHTML(user.getComment())%>
							</div>
						</div>
						<div class="item-group">
							<div class="item-label">Make Card</div>
							<div class="items">
								<a href="index.jsp?userId=<%=user.getUserId()%>"><%=Helper.countCardByUserId(userId)%></a>
							</div>
						</div>
						<div class="item-group">
							<div class="item-label">Comment</div>
							<div class="items"><%=Helper.countCommentByUserId(userId)%>
							</div>
						</div>
						<div class="item-group">
							<div class="item-label">Mail</div>
							<div class="items"><%=user.getMail()%>
							</div>
						</div>
						<div class="item-group">
							<div class="item-label">Birth Day</div>
							<div class="items"><%=user.getBirthDay()%>
							</div>
						</div>
						<div class="item-group">
							<div class="item-label">Country</div>
							<div class="items">
								<%=Helper.getCountryEnName(user.getCode())%>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>