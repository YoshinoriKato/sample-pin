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
<script type="text/javascript">
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
			});
	$(window).resize(function() {
		wookmark();
	});
</script>
</head>

<%
	String cardId = request.getParameter("cardId");
	String userId0 = (String) session.getAttribute("userId");
	String userId1 = (userId0 != null) ? userId0 : session.getId();

	Card card = Helper.getCardInfoByID(cardId);
	Helper.setFootprint(card, userId1);
	Random dice = new Random(System.nanoTime());
	List<Comment> comments = Helper.getCommentsInfoByID(cardId);

	String message = (String) request.getAttribute("message");
	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";
%>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<jsp:include page="_button.jsp" flush="true" />
	<div id="main">
		<ul id="content">
			<li class="card">
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
						<%=Helper.convURLLink(Helper.escapeHTML(card.getCaption()))%>
					</div>
					<div class="star right">
						<%=card.getLikes()%>
						comment
					</div>
				</div>
			</li>

			<%
				int i = comments.size();
				for (Comment comment : comments) {
					User user = Helper.getUserById(comment.getUserId());
					String wallPaper = Helper.getWallPaper(user);
					String fontColor = Helper.getFontColor(user);
					String backgroundColor = Helper.getBackgroundColor(user);
					String userName = user != null ? user.getUserName() : "nanashi";
			%>
			<li class="card"><div class="cell"
					>
					<div class="comment caption2 star">
						No.<%=i %></div>
					<div class="comment caption2 deco">
						<!-- comment -->
						<a href="profile.jsp?userId=<%=comment.getUserId()%>"><img
							src="<%=user.getImagePath()%>" class="image-icon"></a>
						<%=Helper.convURLLink(Helper.escapeHTML(comment
						.getCaption()))%>
						<br style="clear: both;">
					</div>
					<div class="comment caption2 star right">
						<%=Helper.formatToAboutTimeString(comment
						.getCreateDate())%></div>
				</div></li>
			<%
				i--;
				}
			%>
		</ul>
		<br style="clear: both;" />
	</div>

	<div style="display: none;" id="cardId"><%=cardId%></div>

	<div id="cover" class="center">
		<div class="middle">
			<div id="image-close" class="tab-button">x</div>
			<div>
				<img src="<%=card.getImagePath()%>" id="image-origin">
			</div>
		</div>
	</div>

	<div id="comment-area">
		<div id="comment-close" class="tab-button">x</div>
		<div class="center page-menu">
			<%
				if (comments.size() >= CommentServlet.COMMENTS_LIMIT) {
			%>
			<p>
				Thanks. This card received
				<%=CommentServlet.COMMENTS_LIMIT%>
				comments.
			</p>

			<%
				} else if (userId0 != null) {
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

	<jsp:include page="_footer.jsp"></jsp:include>
</body>

</html>
