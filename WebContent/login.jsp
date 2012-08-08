<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sample-Pin</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript" src="jquery.wookmark.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="common.css?201208080923" rel="stylesheet">
</head>

<%
	Exception e = (Exception) request.getAttribute("error");
	String message = e != null ? e.getMessage() : "";

	String fromUrl = (String) request.getAttribute("fromUrl");
	fromUrl = fromUrl != null ? fromUrl : "index.jsp";
%>

<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<div class="container">
			<div class="row">
				<h1><%=message%></h1>
				<div class="cell">
					<form action="login.do" method="post" class="form-horizontal">
						<fieldset>
							Login <input type="hidden" name="redirectUrl"
								value="<%=fromUrl%>" />
							<div class="control-group">
								<label for="userId" class="control-label">User ID</label>
								<div class="controls">
									<input type="text" name="userId" class="span6" />
								</div>
							</div>
							<div class="control-group">
								<label for="password" class="control-label">Password</label>
								<div class="controls">
									<input type="password" name="password" class="span6" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="submit" name="submit" value="Login" class="btn" />
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