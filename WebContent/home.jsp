<%@page import="com.samplepin.common.Helper"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = Helper.getUserId(request);

	String sorted = request.getParameter("sorted");
	sorted = sorted == null ? "" : sorted;

	String otherUserId = request.getParameter("userId");
	otherUserId = (otherUserId != null) ? otherUserId : "";

	String title = "Home";
	title = (Helper.valid(otherUserId)) ? "Owns" : title;
	title = ("recommend".equals(sorted)) ? "Recommend" : title;
	title = ("footprints".equals(sorted)) ? "Footprints" : title;
	title = ("mine".equals(sorted)) ? "My Cards" : title;
	title = ("search".equals(sorted)) ? "Search" : title;

	String words = request.getParameter("words");
	words = words == null ? "" : words;
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
				observeText('#search-box', 'search-box');
				callAjax($('#sorted').text(), 40, '', $('#userId').text(), "",
						"card", $('#words').text());
				$('#main').fadeIn(1000);
			});
</script>
</head>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<jsp:include page="_button.jsp" flush="true" />
	<div id="title"><%=title%></div>
	<div id="main">
		<div id="add-card">
			<div id="input-window">
				<%
					if (Helper.valid(userId)) {
				%>
				<div class="cell" style="padding-top: 25px">
					<jsp:include page="_search.jsp"></jsp:include>
				</div>
				<%
					} else {
				%>
				<div class="caption large center">
					Please, <a href="login.jsp?fromUrl=home.jsp">Login</a> or <a
						href="signup.jsp">Sign up</a>.
				</div>
				<%
					}
				%>
			</div>
		</div>
		<ul id="content">
			<!--  ajax -->
		</ul>
		<br style="clear: both;" />
	</div>
	<div class="center caption star x-large" id="read-cards"></div>
	<div style="display: none" id="sorted"><%=sorted%></div>
	<div style="display: none" id="userId"><%=otherUserId%></div>
	<div style="display: none" id="words"><%=words%></div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>

</html>
