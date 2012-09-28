<%@page import="com.google.code.morphia.query.*"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="UTF-8">
	attach(window, 'load', function() {
		$('#main').fadeIn(1000);
		$('#ajax').fadeOut(1000);
		wookmark();
	});
</script>
</head>

<body class="home">
	<jsp:include page="_topbar.jsp"></jsp:include>
	<div id="title">Tags</div>
	<div style="height: 40px;"></div>
	<div id="main">
		<ul id="content">
			<%
				for (Tag tag : Helper.getTags()) {
			%>
			<li class="card">
				<div class="cell">
					<div>
						<a class="no-hover center"
							href="home.jsp?sorted=search&words=<%=tag.getTag()%>"> <img
							class="image-shot deco" src="<%=tag.getImagePath()%>">
						</a>
					</div>
					<div class="caption center deco">
						<a href="home.jsp?sorted=search&words=<%=tag.getTag()%>"><%=tag.getTag()%></a>
					</div>
					<div class="star right"><%=tag.getSize()%>
						cards
					</div>
				</div>
			</li>
			<%
				}
			%>
		</ul>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
	<jsp:include page="_sns.jsp"></jsp:include>
</body>
</html>