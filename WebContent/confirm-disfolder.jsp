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
	String folderId = request.getParameter("folderId");
	String userId = Helper.getUserId(request);

	Folder folder = Helper.getFolderById(folderId);
	request.setAttribute("folder", folder);

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
					if (folder != null) {
						String[] cards = folder.getCards().split(",");
						if (cards.length != 0) {
							Card card = Helper.getCardByID(cards[0]);
							if (card != null) {
				%>
				<div class="form-horizontal">
					<form action="delete-folder.do" method="post"
						class="form-horizontal">
						<fieldset>
							<h1 class="tab-header">Confirm Discard</h1>
							<div class="control-group">
								<label class="control-label">Preview</label>
								<div class="controls">
									<div class="card opacity80">
										<div class="cell">
											<div>
												<a class="no-hover center"
													href="home.jsp?sorted=folder&folderId=<%=folder.getFolderId()%>">
													<img class="image-shot deco" src="<%=card.getImagePath()%>">
												</a>
											</div>
											<div class="caption center deco">
												<a
													href="home.jsp?sorted=folder&folderId=<%=folder.getFolderId()%>"><%=folder.getFolderName()%></a>
											</div>
											<div class="star right"><%=cards.length%>
												&ensp;cards
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="hidden" value="<%=folderId%>" name="folderId">
									<input type="hidden" value="<%=userId%>" name="userId">
									<input type="submit" value="Confirm"
										class="btn btn-large btn-danger btn-cell">
								</div>
							</div>
						</fieldset>
					</form>
				</div>
				<%
					}
						}
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
	<div style="display: none;" id="folderId"><%=folderId%></div>

	<jsp:include page="_footer.jsp"></jsp:include>
</body>

<script type="text/javascript">
	attach(window, 'load', function() {
		pushPull('#main', '#ajax');
	});
</script>

</html>
