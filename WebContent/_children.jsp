<%@page import="com.samplepin.Card"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String cardId = request.getParameter("cardId");
	Card card = (Card) request.getAttribute("card");
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
