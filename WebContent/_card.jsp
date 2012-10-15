<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%!Card card;%>

<%
	String select = request.getParameter("select");
	boolean isSelectMode = "true".equals(select);
	
	card = (Card) request.getAttribute("card");
	if (card != null) {
%>

<div class="cell">
	<%
		int height = card.getHeight();
			int width = card.getWidth();
			float cardWidth = 240;
			height = Math.round(height * (cardWidth / width));
			width = Math.round(cardWidth);
			if (card.getView() > 0) {
	%>
	<div class="ribon">
		<div class="ribon-text color-red link" id="ribon"><%=card.getView()%>
			view
		</div>
	</div>
	<%
		}
	%>
	<div class="card-main">
		<img class="image-shot deco link" src="<%=Helper.convertThumbnailPath(card.getImagePath())%>"
			width="<%=width%>" height="<%=height%>" id="image-shot">
	</div>
	<div class="card-comment">
		<div class="card-icon">
			<div>
				<a class="no-hover"
					href="profile.jsp?userId=<%=card.getUserId()%>"><img
					class="menu-user-icon img-circle" src="<%=card.getUserIcon()%>"></a>
			</div>
		</div>
		<div class="card-subtext">
			<div class="bold deco break-word">
			<%=Helper.escapeHTML(card.getUserName())%></div>
			<div class="caption deco"><%=Helper.escapeHTML(card.getCaption())%></div>
		</div>
	</div>
	<br style="clear: both;">
	<%-- <div class="card-sub">
		<div class="card-icon">
			<%
				if (Helper.valid(card.getParentId())) {
						Card parent = Helper.getCardByID(card.getParentId());
						if (parent != null) {
			%>
			<div class="card-info break-word">
				<a class="no-hover"
					href="card-comment.jsp?cardId=<%=card.getParentId()%>&type=comment"><img
					class="image-icon" src="<%=parent.getImagePath()%>"></a>
			</div>
			<%
				}
					}
			%>
		</div>
		<div class="card-subtext">
			<%
				if (Helper.valid(card.getKeywords())) {
			%>
			<div class="card-info break-word">
				Keywords:<span class="keywords"><%=card.getKeywords()%></span>
			</div>
			<%
				}
			%>
			<%
				if (Helper.valid(card.getSite())) {
			%>
			<div class="card-info break-word">
				<a class="no-hover"
					href="jump.jsp?cardId=<%=card.getCardId()%>&redirectUrl=<%=card.getSite()%>"
					target="_blank">URL:<%=card.getSite()%></a>
			</div>
			<%
				}
			%>
		</div>
	</div>
	<br style="clear: both;"> --%>
	<div class="star right" style="clear: both;"><%=card.getLikes()%>
		comment
	</div>
	<%
		String userId = Helper.getUserId(session);
			String url = request.getRequestURI();
			if (url.contains("/card-comment.jsp")
					&& card.getUserId().equals(userId)) {
	%>
	<div class="close-button">
		<a href="confirm-discard.jsp?cardId=<%=card.getCardId()%>">&times;</a>
	</div>
	<%
		}
	%>
</div>
<%
	}
%>
