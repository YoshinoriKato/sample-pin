<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	$(window).load(function() {
		$('#main').fadeIn(1000);
		$('#cover').fadeOut(1000);
	});
</script>
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
	fromUrl = fromUrl != null ? fromUrl : "index.jsp";

	String mail = (String) request.getAttribute("mail");
	mail = mail != null ? mail : "";
	mail = oneTime != null ? oneTime.getMail() : mail;

	String password = oneTime != null ? oneTime.getPassword() : "";
	
	String message = (String)request.getAttribute("message");
	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";

%>

<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="cell">
					<form action="login.do" method="post" class="form-horizontal">
						<fieldset>
							<h3>Login</h3>
							<input type="hidden" name="oneTimePassword"
								value="<%=oneTimePassword%>" /> <input type="hidden"
								name="redirectUrl" value="<%=fromUrl%>" />
							<div class="control-group <%=error%>">
								<label for="mail" class="control-label">Mail</label>
								<div class="controls">
									<input type="email" name="mail" class="span6 input"
										placeholder="xxxxxx@xxx.xxx" value="<%=mail%>" /><span class="help-inline"><%=message%></span>
								</div>
							</div>
							<div class="control-group <%=error%>">
								<label for="password" class="control-label">Password</label>
								<div class="controls">
									<input type="password" name="password" class="span6"
										placeholder="password" value="<%=password%>" /><span class="help-inline"><%=message%></span>
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
				</div>
			</div>
		</div>
	</div>
	<div id="cover" class="center"></div>
</body>
</html>