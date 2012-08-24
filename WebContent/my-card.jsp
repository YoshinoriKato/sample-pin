<%@page import="java.util.Random"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	function previewChange(from, to) {
		$value = $(from);
		$changed = $(to).text(value.text());
	}
</script>
<jsp:include page="_effect.jsp"></jsp:include>
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

<%
	String wallPaper = Helper.getWallPaper(user);
	String fontColor = Helper.getFontColor(user);
	String backgroundColor = Helper.getBackgroundColor(user);
	String userName = user != null ? user.getUserName() : "nanashi";
	String useBackgroundImage = user.getUseBackgroundImage() ? "checked"
			: "";
%>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">My Card</div>
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="cell">
					<div calss="padding20">
						<div class="form-horizontal">
							<form action="my-card.do" enctype="multipart/form-data"
								method="post" class="form-horizontal">
								<fieldset>
									<h1 class="tab-header">My Card</h1>
									<div class="control-group">
										<label class="control-label">Preview</label>
										<div class="controls">
											<ul id="content">
												<li class="card"><div class="cell opacity80"
														style="<%=wallPaper%> <%=backgroundColor%>">
														<div class="comment caption2 deco" style="<%=fontColor%>">
															<!-- comment -->
															<img src="<%=user.getImagePath()%>" class="image-icon">
															<%=Helper.convURLLink(Helper.escapeHTML("Hello, world."))%>
															<br style="clear: both;">
														</div>
														<div class="comment right" style="<%=fontColor%>">
															<%=Helper.formatToAboutTimeString(System.currentTimeMillis())%></div>
													</div></li>
											</ul>
										</div>
									</div>
									<div class="control-group">
										<label for="userName" class="control-label">Name</label>
										<div class="controls">
											<input type="text" name="userName" maxlength="40"
												value="<%=Helper.escapeHTML(user.getUserName())%>"
												class="text span8">
										</div>
									</div>
									<div class="control-group">
										<label for="iconPath" class="control-label">Background
											Image</label>
										<div class="controls">
											<input type="file" class="span8" name="iconPath" />
										</div>
									</div>
									<div class="control-group">
										<label for="useBackgroundImage" class="control-label">Use
											Background Image</label>
										<div class="controls">
											<input type="checkbox" class="span8"
												name="useBackgroundImage" value="on" <%=useBackgroundImage%> />
										</div>
									</div>
									<div class="control-group">
										<label for="backgroundColor" class="control-label">Background
											Color</label>
										<div class="controls">
											<input type="color" name="backgroundColor" class="text"
												value="<%=user.getBackgroundColor()%>">
										</div>
									</div>
									<div class="control-group">
										<label for="fontColor" class="control-label">Font
											Color</label>
										<div class="controls">
											<input type="color" name="fontColor" class="text"
												value="<%=user.getFontColor()%>" id="fontColor">
										</div>
									</div>
									<div class="control-group">
										<label for="textShadowColor" class="control-label">Text
											Shadow Color</label>
										<div class="controls">
											<input type="color" name="textShadowColor" class="text"
												value="<%=user.getTextShadowColor()%>" id="textShadowColor">
										</div>
									</div>

									<div class="control-group">
										<div class="controls">
											<input type="submit" value="Update"
												class="btn btn-large btn-primary btn-cell">
										</div>
									</div>
								</fieldset>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>

</html>
