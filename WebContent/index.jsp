<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.samplepin.common.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_effect.jsp"></jsp:include>
</head>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">What?</div>
	<div id="main">
		<div id="input-window">
			<div class="cell">
				<h1 class="tab-header">
					What's
					<%=Helper.NAME%>
					?
				</h1>
				<div class="profile large">
					<div class="item-group">
						<div class="items">
							<img src="img/title.png" style="min-width: 240px;">
						</div>
					</div>
					<div class="item-group">
						<div class="item-label">Abstract</div>
						<div class="items">
							<p>A curating service for easy use.</p>
							<p>And sharing images and feelings,</p>
							<p>about your interest.</p>
						</div>
					</div>
					<hr>
					<div class="item-group">
						<div class="item-label">How to use 0</div>
						<div class="items">
							Click <a href="signup.jsp">"@1st"</a>
							<ol>
								<li>Write your mail address, or <a href="oauth-twitter.jsp">Twitter
										Login</a></li>
								<li>If written your mail address, go to <a href="login.jsp">Login Page</a>, from sign-up mail</li>
								<li>And then, write your mail address and password</li>
							</ol>
						</div>
					</div>
					<hr>
					<div class="item-group">
						<div class="item-label">How to use 1</div>
						<div class="items">
							Click <a href="home.jsp">"Home"</a> in the menu bar
							<ol>
								<li>View cards</li>
								<li>Click a card</li>
								<li>Click "Comment" button in the sub menu bar</li>
								<li>Write your comment</li>
							</ol>
						</div>
					</div>
					<div class="item-group">
						<div class="item-label">How to use 2</div>
						<div class="items">
							Focus on "Add" in <a href="home.jsp">"Home"</a>
							<ol>
								<li>Search Images</li>
								<li>Write a caption</li>
								<li>Make and share your card</li>
							</ol>
						</div>
					</div>
					<div class="item-group">
						<div class="item-label">How to use 3</div>
						<div class="items">Repeat 1, 2</div>
					</div>
					<hr>
					<div class="item-group">
						<div class="item-label">SNS Page</div>
						<div class="items">
							<a href="https://twitter.com/doya_info" target="_blank"
								style="margin-left: 50px;"><img src="img/bird_gray_48.png"></a><a
								href="http://www.facebook.com/pages/DOYAinfo/495135587181076"
								target="_blank" style="margin-left: 80px;"><img
								src="img/f_logo.png" style="height: 40px; width: 40px;"></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>