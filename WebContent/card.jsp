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
	<div id="origin" class="small-tab"
		onclick="pushPull('#image-holder','#origin')">
		<span class="vertical-text">Image</span>
	</div>
	<div id="write" class="small-tab"
		onclick="pushPull('#comment-area','#write')">
		<div class=" ">
			<span class="vertical-text">Comment</span>
		</div>
	</div>
	<div id="main">
		<jsp:include page="_button.jsp" flush="true" />
		<div id="image-holder">
			<div class="tab-button" onclick="pushPull('#origin','#image-holder')">x</div>
			<img src="<%=card.getImagePath()%>" class="image-origin">
		</div>
		<div id="comment-area">
			<div class="tab-button" onclick="pushPull('#write','#comment-area')">x</div>
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
							<textarea name="comment" class="textarea span6" rows="4"></textarea>
						</div>
						<div class="help-inline"><%=message%></div>
					</div>
					<div class="control-group">
						<input type="submit" class="btn btn-large btn-primary btn-cell"
							value="Comment">
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

		<ul id="content">
			<li>
				<div class="cell">
					<div>
						<img src="<%=card.getImagePath()%>" class="image-shot">
					</div>
					<% if(card.getView() != 0){ %>
					<div class="ribon">
						<span class="ribon-text"> <%=card.getView()%> view
						</span>
					</div>
					<% } %>
					<div class="caption deco">
						<%=Helper.convURLLink(card.getCaption())%>
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
			<li><div class="cell opacity80"
					style="<%=wallPaper%> <%=backgroundColor%>">
					<div class="comment" style="<%=fontColor%>">
						<a href="profile.jsp?userId=<%=comment.getUserId() %>">@<%=userName%>
						</a>
					</div>
					<div class="comment deco" style="<%=fontColor%>">
						<!-- comment -->
						<%=Helper.convURLLink(comment.getComment()) %>
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
	<jsp:include page="_footer.jsp"></jsp:include>
</body>

<script type="text/javascript">
	$(window).load(function() {
		$('#content li').wookmark({
			offset : 20
		});
		cardId = $("#cardId").text();
		pushPull('#main', '#cover');
	});
	$(window).resize(function() {
		$('#content li').wookmark({
			offset : 20
		})
	});
</script>
</html>
