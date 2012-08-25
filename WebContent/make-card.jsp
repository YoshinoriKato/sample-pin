<%@page import="com.samplepin.common.Helper"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_effect.jsp"></jsp:include>
<script type="text/javascript">
	$(window).load(function() {
		recoveryText('make-card');
		$timer = setInterval(function() {
			checkLength('make-card');
		}, 500);
	});
</script>
</head>

<%
	String message = (String) request.getAttribute("message");
	String imagePath = request.getParameter("imagePath");
	imagePath = imagePath != null ? URLDecoder.decode(imagePath,
			"UTF-8") : "";
	String keywords = request.getParameter("keywords");
	keywords = keywords != null ? URLDecoder.decode(keywords, "UTF-8")
			: keywords;
	keywords = (keywords != null) ? keywords : "";
	String site = request.getParameter("site");
	site = site != null ? URLDecoder.decode(site, "UTF-8") : site;
	site = (site != null) ? site : "";

	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";
	String external = (String) request.getParameter("external");
%>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">Make Card</div>
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="cell">
					<div class="form-horizontal">
						<form class="form-search" action="javascript:searchGoogle()">
							<fieldset>
								<h1 class="tab-header">Make Card</h1>
								<div class="control-group">
									<label for="iconPath" class="control-label">Image</label>
									<div class="controls">
										<input type="search" id="search-box" value=""
											placeholder="Keyword" class="input-medium span6 text"> <input
											type="submit" value="Search" id="search-action"
											class="btn btn-large btn-primary btn-cell">
									</div>
								</div>
							</fieldset>
						</form>
						<ul id="search-result"></ul>
						<form action="make-card.do" enctype="multipart/form-data"
							method="post" class="form-horizontal">
							<fieldset>
								<div class="control-group">
									<div class="controls">
										<%
											if (Helper.valid(imagePath)) {
										%>
										<img alt="" src="<%=imagePath%>" class="image-shot" style="max-width: 600px">
										<%
											} else {
										%>
										<input type="file" class="span8" name="iconPath" />
										<%
											}
										%>
									</div>
								</div>
								<%
									if (Helper.valid(imagePath)) {
								%>
								<div class="control-group">
									<label for="keywords" class="control-label">Keywords</label>
									<div class="controls">
										<input type="text" name="keywords" value="<%=keywords%>"
											class="span6 text" readonly="readonly">
									</div>
								</div>
								<div class="control-group">
									<label for="site" class="control-label">URL</label>
									<div class="controls">
										<input type="text" name="site" value="<%=site%>" class="span6 text"
											readonly="readonly">
									</div>
								</div>
								<%
									}
								%>
								<div class="control-group <%=error%>">
									<label for="caption" class="control-label">Caption</label>
									<div class="controls">
										<textarea name="caption" id="comment-text"
											class="textarea span8" rows="8"></textarea>
										<span class="help-inline"><%=message%></span>
									</div>
								</div>
								<%
									if (external != null) {
								%>
								<div class="control-group">
									<label for="url" class="control-label">URL</label>
									<div class="controls">
										<input type="url" name="url" class="text">
									</div>
								</div>
								<%
									}
								%>
								<div class="control-group">
									<div class="controls">
										<input type="submit" value="Make" id="submit-button"
											class="btn btn-large btn-primary btn-cell">
									</div>
								</div>
								<input type="hidden" name="imagePath" value="<%=imagePath%>">
							</fieldset>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>

</html>
