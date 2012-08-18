<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
</head>

<%
	String cardId = request.getParameter("cardId");

	Card card = Helper.getCardInfoByID(cardId);

	String message = (String) request.getAttribute("message");
	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";
%>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">Discard</div>
	<div id="main">
		<div class="container">
			<div class="row">
				<div class="cell">
					<%
						if (card != null) {
					%>
					<div class="form-horizontal">
						<form action="confirm-discard.do" method="post"
							class="form-horizontal">
							<fieldset>
								<h3>Confirm Discard</h3>
								<div class="control-group">
									<label class="control-label">Preview</label>
									<div class="controls">
										<div class="card">
											<div class="cell link" id="image-shot">
												<div>
													<img src="<%=card.getImagePath()%>" class="image-shot">
												</div>
												<%
													if (card.getView() != 0) {
												%>
												<div class="ribon">
													<span class="ribon-text color-red"> <%=card.getView()%>
														view
													</span>
												</div>
												<%
													}
												%>
												<div class="caption deco">
													<%=Helper.convURLLink(Helper.escapeHTML(card
						.getCaption()))%>
												</div>
												<div class="star right">
													<%=card.getLikes()%>
													comment
												</div>
											</div>
										</div>
									</div>
								</div>
								<div class="control-group">
									<div class="controls">
										<input type="hidden" value="<%=cardId%>" name="cardId">
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
						<h1>No exist.</h1>
					</div>
					<%
						}
					%>
				</div>
			</div>
		</div>
	</div>
	<div style="display: none;" id="cardId"><%=cardId%></div>

	<jsp:include page="_footer.jsp"></jsp:include>
</body>

<script type="text/javascript">
	$(window).load(function() {
		cardId = $("#cardId").text();
		pushPull('#main', '#ajax');
		wookmark();
	});
	$(window).resize(function() {
		wookmark();
	});
</script>


</html>
