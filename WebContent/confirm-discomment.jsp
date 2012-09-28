<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
</head>

<%
	String cardId = request.getParameter("cardId");
	String userId = request.getParameter("userId");
	String createDate = request.getParameter("createDate");

	Comment comment = Helper.getCommentByID(cardId, userId,
			Long.valueOf(createDate));
	request.setAttribute("comment", comment);

	String message = (String) request.getAttribute("message");
	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";
%>

<body class="home">
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">Discard</div>
	<div id="main">
		<div id="input-window">
			<div class="cell">
				<%
					if (comment != null) {
				%>
				<div class="form-horizontal">
					<form action="delete-comment.do" method="post"
						class="form-horizontal">
						<fieldset>
							<h1 class="tab-header">Confirm Discard</h1>
							<div class="control-group">
								<label class="control-label">Preview</label>
								<div class="controls">
									<div class="card opacity80">
										<div id="<%=comment.getCardId()%>" class="cell">
											<div class="bold deco comment">
												<%=Helper.escapeHTML(comment.getUserName())%><a
													class="no-hover"><img class="image-icon"
													src="<%=comment.getUserIcon()%>"></a>
											</div>
											<div class="caption comment deco"><%=Helper.escapeHTML(comment.getCaption())%></div>
											<div class="star comment right" style="clear: both;">2
												hours</div>
										</div>
									</div>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="hidden" value="<%=cardId%>" name="cardId">
									<input type="hidden" value="<%=userId%>" name="userId">
									<input type="hidden" value="<%=createDate%>" name="createDate">
									<input type="submit" value="Confirm"
										class="btn btn-large btn-danger btn-cell">
								</div>
							</div>
						</fieldset>
					</form>
				</div>
				<%
					} else {
				%>
				<div class="caption">
					<h1 class="tab-header">No exist.</h1>
				</div>
				<%
					}
				%>
			</div>
		</div>
	</div>
	<div style="display: none;" id="cardId"><%=cardId%></div>

	<jsp:include page="_footer.jsp"></jsp:include>
</body>

<script type="text/javascript">
	attach(window, 'load', function() {
		cardId = $("#cardId").text();
		pushPull('#main', '#ajax');
		wookmark();
	});
	$(window).resize(function() {
		wookmark();
	});
</script>


</html>
