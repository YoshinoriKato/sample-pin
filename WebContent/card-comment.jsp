<%@page import="com.samplepin.servlet.CommentServlet"%>
<%@page import="com.samplepin.*"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sorted = request.getParameter("sorted");
	sorted = sorted == null ? "" : sorted;

	String otherUserId = request.getParameter("userId");
	otherUserId = (otherUserId != null) ? otherUserId : "";

	String userId = (String) session.getAttribute("userId");
	userId = (userId != null) ? userId : session.getId();

	String cardId = request.getParameter("cardId");
	cardId = (cardId != null) ? cardId : "";

	String type = request.getParameter("type");
	type = (type != null) ? type : "card";

	Card card = Helper.getCardInfoByID(cardId);
	if (card != null && userId != null && !userId.isEmpty()) {
		Helper.setFootprint(card, userId);
	}

	String message = (String) request.getAttribute("message");
	message = message != null ? message : "";

	String error = message != null && !message.isEmpty() ? "error" : "";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	$(window).resize(function() {
		wookmark();
	});

	$(window).load(
			function() {
				$timer = setInterval(function() {
					checkLength();
				}, 500);
				cardId = $("#cardId").text();
				pushPull('#main', '#ajax');
				wookmark();
				$('#image-shot')
						.attr("onclick", "pushPull('#cover','#origin')");
				$('#image-close').attr("onclick",
						"pushPull('#origin','#cover')");
				$('#comment-button').attr("onclick",
						"pushPull('#comment-area','#write')");
				$('#comment-close').attr("onclick",
						"pushPull('#write','#comment-area')");
				callAjax($('#sorted').text(), 40, '', $('#userId').text(), $(
						'#cardId').text(), $('#type').text());
				$('#main').fadeIn(1000);
		});
</script>
</head>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<jsp:include page="_button.jsp" flush="true" />
	<div id="title">Comments</div>
	<div id="main">
		<ul id="content">
			<%
				if (card != null) {
			%>
			<li class="card">
				<div class="cell link" id="image-shot">
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
					<div>
						<img src="<%=card.getImagePath()%>" class="image-shot">
					</div>
					<div class="star comment">
						<%=card.getUserName()%><a class="no-hover"
							href="profile.jsp?userId=<%=card.getUserId()%>"><img
							class="image-icon" src="<%=card.getUserIcon()%>"></a>
					</div>
					<div class="caption deco">
						<%=Helper.convURLLink(Helper.escapeHTML(card
						.getCaption()))%>
					</div>
					<div class="star right">
						<%=card.getLikes()%>
						comment
					</div>
				</div>
			</li>
			<%
				}
			%>
			<!--  ajax -->
		</ul>
		<br style="clear: both;" />
	</div>

	<!-- read cards -->
	<div class="center caption star large" id="read-cards"></div>

	<!-- parameters -->
	<div style="display: none" id="sorted"><%=sorted%></div>
	<div style="display: none" id="userId"><%=otherUserId%></div>
	<div style="display: none" id="cardId"><%=cardId%></div>
	<div style="display: none" id="type"><%=type%></div>

	<%
		if (card != null) {
	%>
	<!-- image -->
	<div id="cover" class="center">
		<div class="middle">
			<div id="image-close" class="tab-button">x</div>
			<div>
				<img src="<%=card.getImagePath()%>" id="image-origin">
			</div>
		</div>
	</div>

	<!-- comment form -->
	<div id="comment-area">
		<div id="comment-close" class="tab-button">x</div>
		<div class="center page-menu">
			<%
				if (userId != null) {
			%>

			<form method="post" action="comment.do" class="form-horizontal">
				<div class="control-group <%=error%>">
					<div>
						<textarea id="comment-text" name="comment" class="textarea span6"
							rows="4"></textarea>
					</div>
					<div class="help-inline"><%=message%></div>
				</div>
				<div class="control-group">
					<input id="submit-button" type="submit"
						class="btn btn-large btn-primary btn-cell" value="Comment"
						disabled>
				</div>
				<input type="hidden" name="cardId" value="<%=cardId%>">
			</form>

			<%
				} else {
			%>
			<div class="caption large">
				Please, <a href="login.jsp?fromUrl=card.jsp?cardId=<%=cardId%>">Login</a>
				or <a href="signup.jsp">Sign up</a>.
			</div>
			<%
				}
			%>
		</div>
	</div>
	<%
		}
	%>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>

</html>
