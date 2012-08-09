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
							Sign up <input type="hidden" name="redirectUrl"
								value="<%=fromUrl%>" />
							<div class="control-group">
								<label for="mail" class="control-label">Mail Address</label>
								<div class="controls">
									<input type="text" name="mail" class="span6 input" placeholder="xxxxxx@xxx.xxx" />
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="submit" name="submit" value="Sign up" class="btn btn-large btn-primary" />
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