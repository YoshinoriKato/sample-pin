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
<script type="text/javascript">
	function change(id) {
		var $textarea = $(id);
		var text = $textarea;

		alert(text);

		$textarea.empty();

		var $form = $("<form/>").attr("method", "post")
				.attr("action", "xxx.do");

		var $input = $("<input/>").attr("type", "submit");

		$form.append("<textarea/>");
		$form.append($input);

		$textarea.append($form);
		$textarea.attr("onclick", "");
	}
</script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="common.css" rel="stylesheet">
</head>



<%
	String cardId = request.getParameter("cardId");
	Card card = Helper.getCardInfoByID(cardId);
	String userId = (String) session.getAttribute("userId");
	Random dice = new Random(System.nanoTime());
%>

<body>
	<jsp:include page="topbar.jsp" flush="true" />
	<div id="main">
		<ul id="content">
			<li>
				<div class="cell">
					<div>
						<a href="card.jsp?cardId=<%=cardId%>"> <img
							src="<%=card.getUrl()%>" class="image-shot"></a>
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

			<li><div class="cell">
					<div class="caption">
						@<%=userId%></div>
					<div id="comment-area" onclick="change('#comment-area');"
						class="caption deco"
						style="min-height: <%=dice.nextInt(400) + 60%>px;">
						<!-- comment -->
						ほげほげ。
					</div>
					<div class="caption right">
						<%=Helper.formatToDateTimeString(System.currentTimeMillis())%></div>
				</div></li>

			<%
				for (int i = 0; i < dice.nextInt(500); i++) {
			%>

			<li><div class="cell">
					<div class="caption">
						No.<%=i + 1%>
						@<%=userId%></div>
					<div class="caption deco"
						style="min-height: <%=dice.nextInt(400) + 60%>px;">
						<!-- comment -->
						ほげほげ。
					</div>
					<div class="caption right">
						<%=Helper.formatToDateTimeString(System
						.currentTimeMillis())%></div>
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