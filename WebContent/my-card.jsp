<%@page import="java.util.Random"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sample-Pin</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="common.css" rel="stylesheet">
</head>

<%
	String userId = (String) session.getAttribute("userId");
	if (userId == null) {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.sendRedirect("login.jsp");
	}
	User user = Helper.getUserById(userId);
	if (user == null) {
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.sendRedirect("login.jsp");
	}
%>

<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="cell">
					<div class="form-horizontal">
						<form action="my-card.do" enctype="multipart/form-data"
							method="post" class="form-horizontal">
							<fieldset>
								My Card
								<div class="control-group">
									<label for="iconPath" class="control-label">Background
										Image</label>
									<div class="controls">
										<img src="<%=user.getBackgroundImage()%>" class="image-shot">
										<input type="file" class="span8" name="iconPath" />
									</div>
								</div>
								<div class="control-group">
									<label for="userName" class="control-label">Name</label>
									<div class="controls">
										<input type="text" name="userName" value="<%=user.getUserName()%>"
											class="span8">
									</div>
								</div>
								<div class="control-group">
									<label for="font-color" class="control-label">Font
										Coror</label>
									<div class="controls">
										<input type="text" name="font-color"
											value="<%=user.getFontColor()%>" class="span8">
									</div>
								</div>

								<div class="control-group">
									<div class="controls">
										<input type="submit" value="Make" class="btn">
									</div>
								</div>
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

</html>