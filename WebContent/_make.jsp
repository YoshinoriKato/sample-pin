<%@page import="com.samplepin.common.Helper"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String userId = Helper.getUserId(request);

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


<div id="input-window">
	<%
		if (Helper.valid(userId)) {
	%>
	<div class="cell">
		<div class="form-horizontal">
			<form class="form-search" action="javascript:searchGoogle()">
				<fieldset>
					<h1 class="tab-header">Add</h1>
					<div class="control-group">
						<label for="search-box" class="control-label">Image Search</label>
						<div class="controls">
							<input type="search" id="search-box" name="search-box" value=""
								placeholder="Please search images. At last, press enter key." class="input-medium input-text text">
							<!-- <input type="submit" value="Search" id="search-action"
										class="btn btn-large btn-primary btn-cell"> -->
						</div>
					</div>
				</fieldset>
			</form>
		</div>
		<ul id="search-result"></ul>
		<form action="make-card.do" enctype="multipart/form-data"
			method="post" class="form-horizontal">
			<fieldset>
				<div class="control-group">
				<label for="iconPath" class="control-label">or Image File</label>
					<div class="controls">
						<%
							if (Helper.valid(imagePath)) {
						%>
						<img alt="" src="<%=imagePath%>" class="image-shot"
							style="max-width: 85%">
						<%
							} else {
						%>
						<input type="file" name="iconPath" />
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
						<input type="hidden" name="keywords" value="<%=keywords%>">
						<%=keywords%>
					</div>
				</div>
				<div class="control-group">
					<label for="site" class="control-label">URL</label>
					<div class="controls">
						<input type="hidden" name="site" value="<%=site%>"> <a
							href="<%=site%>" target="_blank" class="break-word"><%=site%></a>
					</div>
				</div>
				<%
					}
				%>
				<div class="control-group <%=error%>">
					<label for="caption" class="control-label">Caption</label>
					<div class="controls">
						<textarea id="comment-text" class="textarea input-text"
							name="caption" rows="4" placeholder="Please write a comment."></textarea>
						<span class="help-inline"><%=message%></span>
					</div>
				</div>
				<%
					if (external != null) {
				%>
				<div class="control-group">
					<label for="url" class="control-label">URL</label>
					<div class="controls">
						<input type="url" name="url" class="text input-text">
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
	<%
		} else {
	%>
	<div class="caption large center">
		Please, <a href="login.jsp?fromUrl=home.jsp">Login</a> or <a
			href="signup.jsp">Sign up</a>.
	</div>
	<%
		}
	%>
</div>

