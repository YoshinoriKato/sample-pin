<%@page import="java.util.Random"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sample-Pin</title>
<script type="text/javascript" src="jquery-1.7.2.js"></script>
<script type="text/javascript" src="jquery.wookmark.js"></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.js"></script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="common.css" rel="stylesheet">
</head>



<%
	String cardId = request.getParameter("cardId");
	Card card = Helper.getCardInfoByID(cardId);
%>

<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<div class="center"><img src="<%=card.getUrl()%>" class="image"></div>
		<ul id="content">
			<li>
				<div class="cell">
					<div>
						<a href="card.jsp?cardId=<%=cardId%>"> <img
							src="<%=card.getUrl()%>" class="image-shot"></a>
					</div>
					<div class="star">
						★<%=card.getLikes()%></div>
					<div class="ribon">
						<span class="ribon-text"> <%=card.getView()%> view
						</span>
					</div>
					<div class="caption deco">
						<%=card.getCaption()%>
					</div>
				</div>
			</li>
			<%
				Random dice = new Random(System.nanoTime());
				for (int i = 0; i < dice.nextInt(500); i++) {
			%>

			<li><div class="cell"
					style="min-height: <%=dice.nextInt(400) + 60%>px;">
					<div>
						No.<%=i + 1%></div>
					<div class="caption deco">
						<!-- comment -->
						ほげほげ。
					</div>
				</div></li>


			<%
				}
			%>
		</ul>
		<br style="clear: both;" />
	</div>
</body>
<script type="text/javascript">
	$(document).ready(function() {
		$('#content li').wookmark({
			offset : 12
		});
		$(window).resize(function() {
			$('#content li').wookmark({
				offset : 12
			})
		});
	});
</script>
</html>