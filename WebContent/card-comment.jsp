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

	String type = "comment";

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

	if ((card == null)
			|| (card != null && card.getAccessLevel() > 0 && !card
					.getUserId().equals(userId))) {
		response.sendError(HttpServletResponse.SC_NOT_FOUND);
	}
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="robots" content="index,follow">
<meta name="description" content="検索キーワードと画像のアンマッチが醸し出す趣きを楽しむサービスです。">
<meta name="keywords" content="<%=card.getKeywords()%>">
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
		$('#near li').wookmark({
			autoResize : true,
			offset : 14,
			container : $('#card-near'),
			itemWidth : 220
		});
	});

	attach(window, 'load', function() {
		$('#recent li').wookmark({
			autoResize : true,
			offset : 14,
			container : $('#card-recent'),
			itemWidth : 220
		});
	});

	attach(window, 'load', function() {
		// text observer
		observeText('#card-title', $key, true);
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

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">Comments</div>
	<div id="main">
		<div class="middle">
			<div class="split-l">
				<h1>Card & Comments</h1>
				<div class="split-l-left"><jsp:include page="_card.jsp"></jsp:include>
					<%
						if (Helper.valid(userId) && userId.equals(card.getUserId())) {
					%>
					<jsp:include page="_parent.jsp"></jsp:include>
					<jsp:include page="_access-level.jsp"></jsp:include>
					<jsp:include page="_caption.jsp"></jsp:include>
					<jsp:include page="_children.jsp"></jsp:include>
					<%
						}
					%>
				</div>
				<div class="split-l-right">
					<!-- image -->
					<div class="margin-bottom-default">
						<h2 class="card-header">タイトル</h2>
						<div class="card-body">
							<h2><%=Helper.escapeHTML(card.getTitle())%></h2>
						</div>
					</div>
					<div style="margin-left: 20px;">
						<jsp:include page="_cover.jsp"></jsp:include></div>
					<div class="margin-bottom-default">
						<h2 class="card-header">キャプション</h2>
						<div class="card-body well deco break-word"><%=Helper.convURLLink(Helper.escapeHTML(card.getCaption()))%></div>
					</div>
					<div class="margin-bottom-default">
						<jsp:include page="_scamper.jsp"></jsp:include>
					</div>
					<div class="margin-bottom-default">
						<h2 class="card-header">コメント</h2>
						<ul id="content">
							<li class="margin-bottom-default" style="max-height: 170px;"><jsp:include
									page="_comment.jsp"></jsp:include></li>
							<!--  ajax -->
							<li id="comment-insert"></li>
							<li id="latest-info" class="margin-bottom-default"></li>
						</ul>
					</div>
					<div class="margin-bottom-default">
						<h2 class="card-header">SNS</h2>
						<div class="card-body"><jsp:include page="_sns.jsp"></jsp:include><br
								style="clear: both;" />
						</div>
					</div>
					<div class="margin-bottom-default">
						<jsp:include page="_relative.jsp"></jsp:include>
						<br style="clear: both;" />
					</div>
					<div class="margin-bottom-default">
						<jsp:include page="_others.jsp"></jsp:include>
						<br style="clear: both;" />
					</div>
				</div>
			</div>
			<br style="clear: both;" />
		</div>
	</div>

	<!-- parameters -->
	<div style="display: none" id="sorted"><%=sorted%></div>
	<div style="display: none" id="userId"><%=otherUserId%></div>
	<div style="display: none" id="cardId"><%=cardId%></div>
	<div style="display: none" id="type"><%=type%></div>
	<div style="display: none" id="image"><%=image%></div>

	<jsp:include page="_footer.jsp"></jsp:include>

</body>

</html>
