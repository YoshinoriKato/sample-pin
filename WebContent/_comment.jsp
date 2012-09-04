<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
String userId = Helper.getUserId(request);
String selfUserId = (userId != null) ? userId : session.getId();

String cardId = request.getParameter("cardId");
cardId = (cardId != null) ? cardId : "";

String message = (String) request.getAttribute("message");
message = message != null ? message : "";

String error = message != null && !message.isEmpty() ? "error" : "";

%>

<div id="comment-area">
	<div id="comment-close" class="tab-button">x</div>
	<div class="center page-menu">
		<%
			if (Helper.valid(userId)) {
		%>

		<form id="comment-form" method="post" action="comment.do"
			class="form-horizontal">
			<!-- <div class="control-group">
					<div>
						<input type="url" class="text input-text" name="url" placeholder="If you need, write a resource url.">
					</div>
				</div> -->
			<div class="control-group <%=error%>">
				<div>
					<textarea id="comment-text" name="comment"
						class="textarea input-text" rows="4"
						placeholder="Please, write a comment."></textarea>
				</div>
				<div class="help-inline"><%=message%></div>
			</div>
			<div class="control-group">
				<input id="submit-button" type="submit"
					class="btn btn-large btn-primary btn-cell" value="Comment" disabled>
				<%
					if (Helper.canTweet(session)) {
				%>
				<input type="checkbox" name="tweet"
					class="btn btn-large btn-primary btn-cell"
					style="margin-left: 50px;"> <img src="img/bird_gray_48.png">
				<%
					}
				%>
				<input type="checkbox" name="anonymous"
					class="btn btn-large btn-primary btn-cell"
					style="margin-left: 50px;"> <img src="img/anonymous.png" class="image-anonymous">
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

