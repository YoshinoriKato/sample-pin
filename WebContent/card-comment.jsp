<%@page import="com.samplepin.servlet.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sorted = request.getParameter("sorted");
	sorted = sorted == null ? "" : sorted;

	String otherUserId = request.getParameter("userId");
	otherUserId = (otherUserId != null) ? otherUserId : "";

	String userId = Helper.getUserId(request);
	String selfUserId = (userId != null) ? userId : session.getId();

	String cardId = request.getParameter("cardId");
	cardId = (cardId != null) ? cardId : "";

	String type = request.getParameter("type");
	type = (type != null) ? type : "card";

	Card card = Helper.getCardInfoByID(cardId);
	if (card != null && selfUserId != null && !selfUserId.isEmpty()) {
		Helper.setFootprint(card, selfUserId);
	}
	request.setAttribute("card", card);

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
				cardId = $("#cardId").text();
				pushPull('#main', '#ajax');
				wookmark();
				$key = $('#cardId').text();
				$('#image-shot')
						.attr("onclick", "pushPull('#cover','#origin')");
				$('#ribon').attr("onclick", "pushPull('#cover','#origin')");
				$('#image-close').attr("onclick",
						"pushPull('#origin','#cover')");
				$('#comment-button').attr("onclick",
						"pushPull('#comment-area','#write')");
				$('#comment-close').attr("onclick",
						"pushPull('#write','#comment-area')");
				callAjax($('#sorted').text(), 40, '', $('#userId').text(), $(
						'#cardId').text(), $('#type').text());
				recoveryText($key);
				$('#comment-form').submit(function() {
					if ($('#comment-text').innerHTML != '') {
						removeText($key);
						return true;
					}
					return false;
				});
				$timer = setInterval(function() {
					checkLength($key);
				}, 500);
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
			<li class="card"><jsp:include page="_card.jsp"></jsp:include></li>
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
			<div id="image-close" class="tab-button">X</div>
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
				if (Helper.valid(userId)) {
			%>

			<form id="comment-form" method="post" action="comment.do"
				class="form-horizontal">
				<div class="control-group">
					<div>
						<input type="url" class="text input-text" name="url" placeholder="If you need, write a resource url.">
					</div>
				</div>
				<div class="control-group <%=error%>">
					<div>
						<textarea id="comment-text" name="comment"
							class="textarea input-text" rows="4" placeholder="Please, write a comment."></textarea>
					</div>
					<div class="help-inline"><%=message%></div>
				</div>
				<div class="control-group">
					<input id="submit-button" type="submit"
						class="btn btn-large btn-primary btn-cell" value="Comment"
						disabled>
					<%
						if (Helper.canTweet(session)) {
					%>
					<input type="checkbox" name="tweet" 
						class="btn btn-large btn-primary btn-cell"
						style="margin-left: 50px;"> <img
						src="img/bird_gray_48.png">
					<%
						}
					%>
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
