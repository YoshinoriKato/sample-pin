<%@page import="com.samplepin.common.Helper"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_effect.jsp"></jsp:include>
<script type="text/javascript">
	$(window).load(function() {
		observeText('#comment-text', 'make-card');
		observeText('#search-box', 'search-box');
	});
</script>
</head>
<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">Add</div>
	<div id="main">
		<jsp:include page="_make.jsp"></jsp:include>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>
