<%@page import="java.util.*"%>
<%@page import="com.samplepin.servlet.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String sorted = request.getParameter("sorted");
	sorted = sorted == null ? "" : sorted;

	String otherUserId = request.getParameter("userId");
	otherUserId = (otherUserId != null) ? otherUserId : "";

	String userId = Helper.getUserId(session);
	String selfUserId = (userId != null) ? userId : session.getId();

	String cardId = request.getParameter("cardId");
	cardId = (cardId != null) ? cardId : "";

	String type = request.getParameter("type");
	type = (type != null) ? type : "comment";

	String image = request.getParameter("image");
	image = (image != null) ? image : "";

	Card card = Helper.getCardByID(cardId);
	if (card != null) {
		if (selfUserId != null && !selfUserId.isEmpty()) {
			request.setAttribute("card", card);
			Helper.setFootprint(card, selfUserId);
		}

		String subTitle = card.getKeywords();
		subTitle = Helper.valid(subTitle) ? subTitle : card
				.getCaption().split("[\r|\n|\r\n]")[0];
		request.setAttribute("subTitle", " [" + subTitle + "]");
	}

	User user = Helper.getUserById(userId);
	request.setAttribute("user", user);

	if ((card == null
			&& user == null)
			|| (card.getAccessLevel() > 0 && !card.getUserId().equals(
					userId))) {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<script type="text/javascript" charset="utf-8">
	$(window).resize(function() {
		/* wookmark(); */
	});

	attach(window, 'load', 
			function() {
				cardId = $("#cardId").text();
				$key = $('#cardId').text();
	});

	attach(window, 'load', 
			function() {
				// effect
				pushPull('#main', '#ajax');
				/* wookmark(); */
	});

	attach(window, 'load', 
			function() {
				// event
				$('#image-shot')
						.attr("onclick", "pushPull('#cover','#origin')");
				$('#ribon').attr("onclick", "pushPull('#cover','#origin')");
				$('#image-close').attr("onclick",
						"pushPull('#origin','#cover')");
				$('#comment-button').attr("onclick",
						"pushPull('#comment-area','#write')");
				$('#comment-close').attr("onclick",
						"pushPull('#write','#comment-area')");
	});

	attach(window, 'load', 
			function() {
				callAjax($('#sorted').text(), 40, '', $('#userId').text(), $(
						'#cardId').text(), $('#type').text());
	});

	attach(window, 'load', 
			function() {
				$('#children li').wookmark({
					autoResize: true,
					offset : 14,
					container : $('#card-family'),
					itemWidth: 100
				});
	});

	attach(window, 'load', 
			function() {
				// text observer
				observeText('#comment-text', $key);
				$('#comment-form').submit(function() {
					if ($('#comment-text').innerHTML != '') {
						removeText($key);
						return true;
					}
					return false;
				});
				$('#main').fadeIn(1000);
	});

	attach(window, 'load', 
			function() {
				// open image
				if ($('#image').text() == 'open') {
					$('#cover').fadeIn(1000);
				}
			});

	attach(window, 'scroll', function() {
		if (isNeed()) {
				readMore();
			}
	});
</script>
</head>

<body class="home">
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">Comments</div>
	<div id="main">
	<%
		if (card != null) {
	%>
	<!-- image -->
		<div id="cover" class="center">
			<div class="middle">
				<div id="image-close" class="close-button">x</div>
				<img src="<%=card.getImagePath()%>" id="image-origin">
			</div>
		</div>
		<%
			}
		%>
		<div id="split">
			<div id="split-left">
				<div class="card margin-bottom-default">
					<%
						if (Helper.valid(cardId)) {
					%>
					<jsp:include page="_card.jsp"></jsp:include>
					<%
						if (Helper.valid(userId)) {
					%>
					<div>
						<a
							class="bold btn btn-large btn-cell opacity80 margin-top-default btn-info"
							href="make-card.jsp?parentId=<%=cardId%>">+連結</a>
					</div>
					<%
						}
					%>
					<div id="card-family" class="margin-top-default">
						<ul id="children">
							<%
								List<Card> cards = Helper.getCardsByID(cardId);
									if (Helper.valid(cards)) {
										for (Card child : cards) {
							%>

							<li><a
								href="card-comment.jsp?cardId=<%=child.getCardId()%>&type=comment"><img
									class="child" src="<%=child.getImagePath()%>"></a></li>

							<%
								}
									}
							%>
						</ul>
					</div>
					<%
						} else if (Helper.valid(userId)) {
					%>
					<jsp:include page="_user.jsp"></jsp:include>
					<%
						}
					%>
				</div>
			</div>
			<div id="split-right">
				<ul id="content">
					<%
						if (Helper.valid(cardId)) {
					%>
					<li class="cell margin-bottom-default" style="max-height: 170px;"><jsp:include
							page="_comment.jsp"></jsp:include></li>
					<%
						}
					%>
					<!--  ajax -->
				</ul>
			</div>
			<br style="clear: both;" />
		</div>
	</div>

	<!-- read cards -->
	<div class="center caption star large" id="read-cards"></div>

	<!-- parameters -->
	<div style="display: none" id="sorted"><%=sorted%></div>
	<div style="display: none" id="userId"><%=otherUserId%></div>
	<div style="display: none" id="cardId"><%=cardId%></div>
	<div style="display: none" id="type"><%=type%></div>
	<div style="display: none" id="image"><%=image%></div>

	<jsp:include page="_footer.jsp"></jsp:include>
	<jsp:include page="_sns.jsp"></jsp:include>
</body>

</html>
