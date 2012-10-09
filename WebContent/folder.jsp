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

<%
	String otherUserId = request.getParameter("userId");
	String userId = Helper.getUserId(session);
	userId = Helper.valid(userId) ? userId : "";
%>

<body class="home">
	<jsp:include page="_topbar.jsp"></jsp:include>
	<div id="title">Tags</div>
	<div style="height: 40px;"></div>
	<div id="main">
		<ul id="content">
			<%
				for (Folder folder : Helper.getFolders(userId)) {
					String[] cards = folder.getCards().split(",");
					if (cards.length == 0) {
						break;
					}
					Card card = Helper.getCardByID(cards[0]);
					if (card == null) {
						continue;
					}
			%>
			<li class="card">
				<div id="<%=folder.getFolderId()%>" class="cell">
					<div>
						<a class="no-hover center"
							href="home.jsp?sorted=folder&folderId=<%=folder.getFolderId()%>">
							<img class="image-shot deco" src="<%=Helper.convertThumbnailPath(card.getImagePath())%>">
						</a>
					</div>
					<div class="caption center deco">
						<a
							href="home.jsp?sorted=folder&folderId=<%=folder.getFolderId()%>"><%=folder.getFolderName()%></a>
					</div>
					<div class="star right"><%=cards.length%>
						&ensp;cards
					</div>
				</div>
			</li>
			<%
				}
			%>
		</ul>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>