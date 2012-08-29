<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_effect.jsp"></jsp:include>
</head>

<%
	/* 	Exception e = (Exception) session.getAttribute("error");
	 String message = e != null ? e.getMessage() : "";
	 */

	String oneTimePassword = (String) session
			.getAttribute("oneTimePassword");
	OneTime oneTime = Helper
			.getOneTimeByOneTimePassword(oneTimePassword);

	String fromUrl = (String) request.getAttribute("fromUrl");
	fromUrl = fromUrl != null ? fromUrl : "home.jsp";

	String mail = (String) request.getAttribute("mail");
	mail = mail != null ? mail : "";
	mail = oneTime != null ? oneTime.getMail() : mail;

	String password = oneTime != null ? oneTime.getPassword() : "";

	String message = (String) request.getAttribute("message");
	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";
%>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">Login</div>
	<div id="main">
		<div id="input-window">
			<div class="cell">
				<form action="login.do" method="post" class="form-horizontal">
					<fieldset>
						<h1 class="tab-header">Login</h1>
						<input type="hidden" name="oneTimePassword"
							value="<%=oneTimePassword%>" /> <input type="hidden"
							name="redirectUrl" value="<%=fromUrl%>" />
						<div class="control-group <%=error%>">
							<label for="mail" class="control-label">Mail</label>
							<div class="controls">
								<input type="email" name="mail" class="input-text input"
									placeholder="xxxxxx@xxx.xxx" value="<%=mail%>" /><span
									class="help-inline"><%=message%></span>
							</div>
						</div>
						<div class="control-group <%=error%>">
							<label for="password" class="control-label">Password</label>
							<div class="controls">
								<input type="password" name="password" class="input-text"
									placeholder="password" value="<%=password%>" /><span
									class="help-inline"><%=message%></span>
							</div>
						</div>
						<div class="control-group">
							<div class="controls">
								<input type="submit" name="submit" value="Login"
									class="btn btn-large btn-primary btn-cell" />
							</div>
						</div>
					</fieldset>
				</form>
				<hr>
				<div class="form-horizontal">
					<div class="control-group">
						<label for="mail" class="control-label">Twitter Account</label>
						<div class="controls">
							<a href="oauth-twitter.jsp"
								class="btn btn-large btn-primary btn-cell"><img
								src="img/bird_gray_48.png"></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>