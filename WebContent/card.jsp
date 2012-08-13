<%@page import="com.samplepin.servlet.CommentServlet"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript">
	function change(id) {
		var $clicked = $(id);
		var $textarea = $("#comment-area");
		var $form = $("<form/>").attr("method", "post").attr("action",
				"comment.do").addClass("form-horizontal");
		var $hidden = $("<input/>").attr("type", "hidden").attr("name",
				"cardId").attr("value", cardId).addClass("btn btn-large");
		var $input = $("<input/>").attr("type", "submit").addClass(
				"btn btn-large");
		var $divText = $("<div/>").addClass("control-group").append(
				$("<textarea/>").attr("name", "comment").addClass("span8")
						.attr("rows", "6"));
		var $divSubmit = $("<div/>").addClass("control-group").append($input);
		$clicked.attr("onclick", "");
		$textarea.empty();
		$textarea.append($form);
		$form.append($divText).append($divSubmit).append($hidden);
	};
</script>
</head>

<%
	String cardId = request.getParameter("cardId");
	String userId = (String) session.getAttribute("userId");
	Card card = Helper.getCardInfoByID(cardId, userId, session);
	Random dice = new Random(System.nanoTime());
	List<Comment> comments = Helper.getCommentsInfoByID(cardId, userId);

	String message = (String) request.getAttribute("message");
	message = message != null ? message : "";
	String error = message != null && !message.isEmpty() ? "error" : "";
%>

<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<div class="image-holder">
			<img src="<%=card.getImagePath()%>" class="image-origin">
		</div>
		<div class="center page-menu">
			<div id="comment-area">
				<%
					if (comments.size() >= CommentServlet.COMMENTS_LIMIT) {
				%>
				<p>
					Thanks. This card received
					<%=CommentServlet.COMMENTS_LIMIT%>
					comments.
				</p>

				<%
					} else if (userId != null) {
				%>
				<!-- 				<input type="button" value="Comment" class="btn btn-large"
					onclick="change('#comment');" id="comment" />
 -->

				<form method="post" action="comment.do" class="form-horizontal">
					<div class="control-group <%=error%>">
						<div>
							<textarea name="comment" class="textarea span8" rows="4"></textarea>
						</div>
						<div class="help-inline"><%=message%></div>
					</div>
					<div class="control-group">
						<input type="submit" class="btn btn-large btn-info" value="Comment">
					</div>
					<input type="hidden" name="cardId" value="<%=cardId%>"
						class="btn btn-large">
				</form>

				<%
					} else {
				%>
				<p>
					Please, <a href="login.jsp">Login</a> or <a href="signup.jsp">Sign
						up</a>.
				</p>
				<%
					}
				%>
			</div>
		</div>
		<ul id="content">
			<li>
				<div class="cell">
					<div>
						<img src="<%=card.getImagePath()%>" class="image-shot">
					</div>
					<div class="ribon">
						<span class="ribon-text"> <%=card.getView()%> view
						</span>
					</div>
					<div class="caption deco">
						<%=card.getCaption()%>
					</div>
					<div class="star right">
						<%=card.getLikes()%>
						comment
					</div>
				</div>
			</li>
			<%
				for (Comment comment : comments) {
					User user = Helper.getUserById(comment.getUserId());
					String wallPaper = Helper.getWallPaper(user);
					String fontColor = Helper.getFontColor(user);
					String backgroundColor = Helper.getBackgroundColor(user);
					String userName = user != null ? user.getUserName() : "nanashi";
			%>
			<li><div class="cell"
					style="<%=wallPaper%> <%=backgroundColor%>">
					<div class="comment" style="<%=fontColor%>">
						@<%=userName%></div>
					<div class="comment deco" style="<%=fontColor%>">
						<!-- comment -->
						<%=comment.getComment()%>
					</div>
					<div class="comment right" style="<%=fontColor%>">
						<%=Helper.formatToDateTimeString(comment.getCreateDate())%></div>
				</div></li>
			<%
				}
			%>
		</ul>
		<br style="clear: both;" />
	</div>
	<div style="display: none;" id="cardId"><%=cardId%></div>
</body>
<script type="text/javascript">
	$(window).load(function() {
		$('#content li').wookmark({
			offset : 20
		});
		cardId = $("#cardId").text();
	});
	$(window).resize(function() {
		$('#content li').wookmark({
			offset : 20
		})
	});
</script>
</html>
