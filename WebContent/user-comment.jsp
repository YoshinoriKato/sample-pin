<%@page import="java.util.*"%>
<%@page import="com.samplepin.servlet.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String otherUserId = request.getParameter("userId");
	otherUserId = (otherUserId != null) ? otherUserId : "";

	String userId = Helper.getUserId(session);
	String selfUserId = (userId != null) ? userId : session.getId();

	String type = "comment";

	User user = Helper.getUserById(otherUserId);
	request.setAttribute("user", user);

	if (user == null) {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	$(window).resize(function() {
		/* wookmark(); */
	});

	attach(window, 'load', function() {
		// effect
		pushPull('#main', '#ajax');
		/* wookmark(); */
	});

	attach(window, 'load', function() {
		callAjax($('#sorted').text(), 20, '', $('#userId').text(), $('#cardId')
				.text(), $('#type').text());
	});

	attach(window, 'scroll', function() {
		if (isNeed()) {
			readMore();
		}
	});
</script>
</head>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">User's Comments</div>
	<div id="main">
		<div class="split-l">
			<div class="split-l-left">
				<div class="card margin-bottom-default">
					<jsp:include page="_user.jsp"></jsp:include>
				</div>
			</div>
			<div class="split-l-right">
				<ul id="content">
					<!--  ajax -->
					<li id="comment-insert"></li>
				</ul>
			</div>
		</div>
		<br style="clear: both;" />
	</div>

	<!-- read cards -->
	<div class="center caption star large" id="read-cards"></div>

	<!-- parameters -->
	<div style="display: none" id="userId"><%=otherUserId%></div>
	<div style="display: none" id="type"><%=type%></div>

	<jsp:include page="_footer.jsp"></jsp:include>
</body>

</html>
