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
	final String LS = System.getProperty("line.separator");
	String cardId = request.getParameter("cardId");

	Card card = (Card) request.getAttribute("confirm");
	session.setAttribute("confirmed", card);
	/* Card card = (Card) session.getAttribute("confirm"); */

	String message = (String) request.getAttribute("message");
	message = (message != null) ? message : "";
	String error = (message != null && !message.isEmpty())
			? "error"
			: "";
%>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">Make Card</div>
	<div id="main">
		<div id="input-window">
			<div class="cell padding20">
				<div class="form-horizontal">
					<form id="comment-form" action="confirm-make-card.do" method="post"
						class="form-horizontal">
						<fieldset>
							<h1 class="tab-header">Confirm Card</h1>
							<div class="control-group">
								<label class="control-label">Preview</label>
								<div class="controls">
									<div class="card">
										<div id="<%=card.getCardId()%>" class="cell">
											<%
												int height = card.getHeight();
												int width = card.getWidth();
												float cardWidth = 240;
												height = Math.round(height * (cardWidth / width));
												width = Math.round(cardWidth);
												if (card.getView() > 0) {
											%>
											<div class="ribon">
												<div class="ribon-text color-red"><%=card.getView()%>
													view
												</div>
											</div>
											<%
												}
											%>
											<div>
												<img class="image-shot deco" src="<%=card.getImagePath()%>"
													width="<%=width%>" height="<%=height%>">
											</div>
											<div class="bold deco break-word">
												管理人<img class="image-icon" src="<%=card.getUserIcon()%>">
											</div>
											<div class="caption deco"><%=card.getCaption()%></div>
											<br style="clear: both;">
											<%
												if (Helper.valid(card.getKeywords())) {
											%>
											<div class="card-info break-word">
												Keywords:<%=card.getKeywords()%></div>
											<%
												}
											%>
											<%
												if (Helper.valid(card.getSite())) {
											%>
											<div class="card-info break-word">
												URL:<%=card.getSite()%>
											</div>
											<%
												}
											%>
											<div class="star right" style="clear: both;"><%=card.getLikes()%>
												comment
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="control-group">
								<div class="controls">
									<input type="submit" value="Confirm"
										class="btn btn-large btn-primary btn-cell">
									<%
										if (Helper.canTweet(session)) {
									%>
									<input type="checkbox" name="tweet" checked
										class="btn btn-large btn-primary btn-cell"
										style="margin-left: 50px;"> <img
										src="img/bird_gray_48.png">
									<%
										}
									%>
								</div>
							</div>
						</fieldset>
					</form>
				</div>
			</div>
		</div>
	</div>
	<div style="display: none;" id="cardId"><%=cardId%></div>

	<jsp:include page="_footer.jsp"></jsp:include>
</body>

<script type="text/javascript">
	$(window).load(function() {
		$cardId = $("#cardId").text();
		pushPull('#main', '#ajax');
		wookmark();
		$('#comment-form').submit(function() {
			removeText('make-card');
			return true;
		});
	});
	$(window).resize(function() {
		wookmark();
	});
</script>


</html>
