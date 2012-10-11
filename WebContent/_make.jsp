<%@page import="com.samplepin.common.Helper"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String userId = Helper.getUserId(request);

	String message = (String) request.getAttribute("message");
	String imagePath = (String) request.getAttribute("imagePath");
/* 	imagePath = imagePath != null ? URLDecoder.decode(imagePath,
			"UTF-8") : "";
 */
	String keywords = (String)request.getAttribute("keywords");
/* 	keywords = keywords != null ? URLDecoder.decode(keywords, "UTF-8")
			: keywords; */
	keywords = (keywords != null) ? keywords : "";
	String site = (String)request.getAttribute("site");
/* 	site = Helper.valid(site) ? URLDecoder.decode(site, "UTF-8") : site; */
	site = Helper.valid(site) ? site : "";
	
	String parentId = (String)request.getAttribute("parentId");
	parentId = Helper.valid(parentId) ? parentId : request.getParameter("parentId");
	parentId = Helper.valid(parentId) ? parentId : "";

	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";
	String external = (String) request.getParameter("external");
%>


<div id="input-window">
	<%
		if (Helper.valid(userId)) {
	%>
	<div class="cell">
		<h1 class="tab-header">Add Card</h1>
		<jsp:include page="_search.jsp"></jsp:include>
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
					<label for="accessLevel" class="control-label">Access Level</label>
					<div class="controls">
						<input type="number" min="0" max="100" name="accessLevel" class="text" value="0">
						<span class="help-inline">level 0 ... 公開, level 100 ... 非公開</span>
					</div>
				</div>
				<div class="control-group">
					<div class="controls">
						<input type="submit" value="Make" id="submit-button"
							class="btn btn-large btn-primary btn-cell">
						<!-- <input
							type="checkbox" name="anonymous"
							class="btn btn-large btn-primary btn-cell"
							style="margin-left: 50px;"> <img src="img/anonymous.png"
							class="image-anonymous"> -->
					</div>
				</div>
				<input type="hidden" name="imagePath" value="<%=imagePath%>">
				<input type="hidden" name="parentId" value="<%=parentId%>">
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
	<div style="display: none" id="parentId"><%=parentId%></div>
</div>

