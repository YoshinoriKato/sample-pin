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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Script-Type"
	content="text/javascript charset=UTF-8" />
<title>Sample-Pin</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript" src="jquery.wookmark.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="callback2.js" charset="UTF-8"></script>
<script type="text/javascript" charset="utf-8">
	$(window).resize(function() {
		$('#content li').wookmark({
			offset : 12
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
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="common.css?201208080923" rel="stylesheet">
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