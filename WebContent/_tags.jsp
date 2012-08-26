<%@page import="com.google.code.morphia.query.*"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="java.util.*"%>
<%@page import="java.util.concurrent.atomic.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	$(window).load(function() {
		$('#main').fadeIn(1000);
		$('#ajax').fadeOut(1000);
	});
</script>
</head>

<body>
	<jsp:include page="_topbar.jsp"></jsp:include>
	<div id="main">
		<ul id="content">
			<%
				try (ACMongo mongo = new ACMongo()) {
					Query<TagAndCard> query = mongo.createQuery(TagAndCard.class);

					for (TagAndCard kac : query.asList()) {
					}
				}
			%>
		</ul>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>