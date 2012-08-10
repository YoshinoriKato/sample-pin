<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
</head>

<%
	/* 	Exception e = (Exception) session.getAttribute("error");
	 String message = e != null ? e.getMessage() : "";
	 */

	 String fromUrl = (String) session.getAttribute("fromUrl");
	 fromUrl = fromUrl != null ? fromUrl : "index.jsp";
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
							<input type="hidden" name="redirectUrl" value="<%=fromUrl%>" />
							<div class="control-group">
								<label for="userId" class="control-label">User ID</label>
								<div class="controls">
									<input type="text" name="userId" class="span6"
										placeholder="User ID" />
								</div>
							</div>
							<div class="control-group">
								<label for="password" class="control-label">Password</label>
								<div class="controls">
									<input type="password" name="password" class="span6"
										placeholder="password" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="submit" name="submit" value="Login"
										class="btn btn-large btn-primary" />
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>