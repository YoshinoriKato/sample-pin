<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sorted = request.getParameter("sorted");
	sorted = sorted == null ? "" : "?sorted=" + sorted;
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	$(window).resize(function() {
		$('#content li').wookmark({
			offset : 20
		})
	});

	$(window).load(function() {

		$sorted = $('#sorted').text();

		$.ajax({
			cache : false,
			type : 'post',
			scriptCharset : 'UTF-8',
			contentType : 'text/javascript+json; charset=utf-8',
			url : 'comments.do' + $sorted,
			data : {
				name : 'index.jsp',
				key : '0381075127472'
			},
			success : callback,
			dataType : 'json'
		});
	});
</script>
</head>


<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<ul id="content">
		</ul>
		<br style="clear: both;" />
	</div>
	<div style="display: none" id="sorted"><%=sorted%></div>
</body>

</html>
