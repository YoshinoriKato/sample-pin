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
	image = (image != null) ? image : "open";

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

	User user = Helper.getUserById(otherUserId);
	request.setAttribute("user", user);

	if ((card == null && user == null)
			|| (card != null && card.getAccessLevel() > 0 && !card
					.getUserId().equals(userId))) {
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

	attach(window, 'load', function() {
		cardId = $("#cardId").text();
		$key = $('#cardId').text();
	});

	attach(window, 'load', function() {
		// effect
		pushPull('#main', '#ajax');
		/* wookmark(); */
	});

	attach(window, 'load', function() {
		// event
		$('#image-shot').attr("onclick", "pushPull('#cover','#origin')");
		$('#ribon').attr("onclick", "pushPull('#cover','#origin')");
		$('#image-close').attr("onclick", "pushPull('#origin','#cover')");
		$('#comment-button').attr("onclick",
				"pushPull('#comment-area','#write')");
		$('#comment-close').attr("onclick",
				"pushPull('#write','#comment-area')");
	});

	attach(window, 'load', function() {
		callAjax($('#sorted').text(), 20, '', $('#userId').text(), $('#cardId')
				.text(), $('#type').text());
	});

	attach(window, 'load', function() {
		$('#children li').wookmark({
			autoResize : true,
			offset : 14,
			container : $('#card-family'),
			itemWidth : 100
		});
	});

	attach(window, 'load', function() {
		// text observer
		observeText('#comment-text', $key, true);
		$('#comment-form').submit(function() {
			if ($('#comment-text').innerHTML != '') {
				removeText($key);
				return true;
			}
			return false;
		});
		$('#main').fadeIn(1000);
	});

	attach(window, 'load', function() {
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

				<div style="margin: auto 0;">
					<img src="<%=card.getImagePath()%>" id="image-origin">
					<div id="image-close" class="close-button">&times;</div>
				</div>
				<%
					if (Helper.valid(card.getKeywords())) {
							String keywords = card.getKeywords();
				%>
				<div class="margin-top-default">
					<span class="large badge opacity60 keywords"><%=keywords%></span>
				</div>
				<%
					}
				%>
				<%
					if (Helper.valid(card.getSite())) {
							String path = card.getSite();
							path = path.length() > 40 ? path.substring(0, 40) + "..."
									: path;
				%>
				<div class="margin-top-default">
					<a href="<%=card.getSite()%>" target="_blank"
						class="large badge opacity60">URL: <%=path%></a>
				</div>
				<%
					}
				%>
			</div>
		</div>
		<%
			}
		%>
		<div class="split-l">
			<div class="split-l-left">
				<div class="card margin-bottom-default">
					<%
						if (Helper.valid(cardId)) {
					%>
					<jsp:include page="_card.jsp"></jsp:include>
					<%
						if (Helper.valid(userId)) {
					%>
					<%
						if (userId.equals(card.getUserId())) {
					%>
					<div class="cell margin-top-default opacity50">
						<p>親</p>
						<form action="update-parent.do" method="post" class="form">
							<fieldset>
								<input type="hidden" name="cardId" value="<%=cardId%>">
								<div class="control-group">
									<label for="parentId" class="control-label"></label>
									<div class="controls">
										<input type="text" class="text" name="parentId"
											value="<%=card.getParentId()%>"
											placeholder="Parent's Card ID.">
									</div>
								</div>
								<input type="submit" value="Update"
									class="btn btn-large btn-primary">
							</fieldset>
						</form>
					</div>
					<div class="cell margin-top-default opacity50">
						<p>コメント修正</p>
						<form action="update-caption.do" method="post" class="form">
							<fieldset>
								<input type="hidden" name="cardId" value="<%=cardId%>">
								<div class="control-group">
									<label for="caption" class="control-label"></label>
									<div class="controls">
										<textarea type="text" class="text" name="caption"
											placeholder="Caption"><%=card.getCaption()%></textarea>
									</div>
								</div>
								<input type="submit" value="Update"
									class="btn btn-large btn-primary">
							</fieldset>
						</form>
					</div>
					<div class="cell margin-top-default opacity50">
						<p>アクセスレベル</p>
						<form action="update-level.do" method="post" class="form">
							<fieldset>
								<input type="hidden" name="cardId" value="<%=cardId%>">
								<div class="control-group">
									<label for="accessLevel" class="control-label"></label>
									<div class="controls">
										<input type="number" min="0" max="100" name="accessLevel"
											class="text" placeholder="Caption"
											value="<%=card.getAccessLevel()%>">
									</div>
								</div>
								<input type="submit" value="Update"
									class="btn btn-large btn-primary">
							</fieldset>
						</form>
					</div>
					<%
						}
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
								href="card-comment.jsp?cardId=<%=child.getCardId()%>"><img
									class="child" src="<%=child.getImagePath()%>"></a></li>

							<%
								}
									}
							%>
						</ul>
					</div>
					<%
						} else if (Helper.valid(otherUserId)) {
					%>
					<jsp:include page="_user.jsp"></jsp:include>
					<%
						}
					%>
				</div>
			</div>
			<div class="split-l-right">
				<ul id="content">
					<%
						if (Helper.valid(cardId)) {
					%>
					<li class="cell margin-bottom-default opacity70"
						style="max-height: 170px;"><jsp:include page="_comment.jsp"></jsp:include></li>
					<%
						}
					%>
					<!--  ajax -->
					<li id="comment-insert"></li>
					<li class="margin-bottom-default"><jsp:include page="_sns.jsp"></jsp:include><br
						style="clear: both;" /></li>
					<%
						if (card != null) {
					%>
					<li id="latest-info" class="cell margin-bottom-default opacity60">
						<p>その他のカード</p>
						<ul>
							<%
								List<Card> cards = Helper.newCards(card.getUpdateDate());
									for (Card newone : cards) {
										int length = newone.getCaption().length();
										String caption = length > 40 ? newone.getCaption()
												.substring(0, 40) + "..." : newone.getCaption();
							%>
							<li><a
								href="card-comment.jsp?cardId=<%=newone.getCardId()%>"><span
									class="deco"><%=caption%></span></a> (view:<%=newone.getView()%>,
								comment:<%=newone.getLikes()%>)</li>
							<%
								}
							%>
						</ul>
					</li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
		<br style="clear: both;" />
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
</body>

</html>
