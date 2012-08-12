<%@page import="com.samplepin.servlet.CommentServlet"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="header.jsp"></jsp:include>
</head>



<%
	String userId = (String) session.getAttribute("userId");
	List<View> views = Helper.getViewsInfoByID(userId);
	Random dice = new Random(System.nanoTime());
%>

<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<ul id="content">
			<%
				for (View view : views) {
					Card card = Helper.getCardInfoByID(view.getCardId());
			%>

			<li>
				<div class="cell">
					<div>
						<a href="card.jsp?cardId=<%=card.getCardId()%>"> <img
							src="<%=card.getImagePath()%>" class="image-shot"></a>
					</div>
					<div class="ribon">
						<span class="ribon-text"> <%=card.getView()%> view
						</span>
					</div>
					<div class="caption deco">
						<%=card.getCaption()%>
					</div>
					<div class="star right">
						★<%=card.getLikes()%></div>
				</div>
			</li>

			<%
				}
			%>
		</ul>
		<br style="clear: both;" />
	</div>
</body>
<script type="text/javascript">
	$(window).load(function() {
		$('#content li').wookmark({
			offset : 20
		});
		cardId = $("#cardId").text();
	});
	$(window).resize(function() {
		$('#content li').wookmark({
			offset : 20
		})
	});
</script>
</html>