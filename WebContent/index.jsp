<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sorted = request.getParameter("sorted");
	sorted = sorted == null ? "" : sorted;
	String userId = request.getParameter("userId");
	userId = (userId != null) ? userId : "";
	String title = "Cards";
	title = ("recommend".equals(sorted)) ? "Recommend" : title;
	title = ("footprints".equals(sorted)) ? "Footprints" : title;
	title = ("mine".equals(sorted)) ? "My Cards" : title;

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

	$(window).load(function() {
		callAjax($('#sorted').text(), 40, '', $('#userId').text(), "", "card", $('#words').text());
		$('#main').fadeIn(1000);
	});
</script>
</head>


<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<jsp:include page="_button.jsp" flush="true" />
	<div id="title"><%=title%></div>
	<div id="main">
		<ul id="content">
			<!--  ajax -->
		</ul>
		<br style="clear: both;" />
	</div></div></div>
	<div class="center caption star large" id="read-cards"></div>
	<div style="display: none" id="sorted"><%=sorted%></div>
	<div style="display: none" id="userId"><%=userId%></div>
	<div style="display: none" id="words"><%=words%></div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>

</html>
