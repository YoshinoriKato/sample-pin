<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getId();
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	$(window).resize(function() {
		$('#content li').wookmark({
			offset : 20
		});
	});

	$(window).load(function() {

		$('#main').fadeIn(1000);

		$.ajax({
			cache : false,
			type : 'post',
			scriptCharset : 'UTF-8',
			contentType : 'text/javascript+json; charset=utf-8',
			url : 'recommend.do',
			data : {
				name : 'index.jsp',
				key : '0381075127472'
			},
			success : callback,
			error : function(XMLHttpRequest, textStatus, errorThrown) {
				$('#cover').fadeOut(10);
				$('#cover2').fadeIn(10);
			},
			dataType : 'json'
		});
	});
</script>
</head>


<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<jsp:include page="button.jsp" flush="true" />
		<ul id="content">
		</ul>
		<br style="clear: both;" />
	</div>
	<jsp:include page="footer.jsp"></jsp:include>
</body>

</html>
