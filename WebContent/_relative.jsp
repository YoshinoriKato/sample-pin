<%@page import="com.samplepin.Card"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String cardId = request.getParameter("cardId");
	Card card = (Card) request.getAttribute("card");
	List<Card> cards = Helper.searchCards(request, card.getKeywords());
	if (cards.size() > 1) {
%>

<h2 class="card-header">関連するカード</h2>
<div id="card-near">
	<ul id="near">
		<%
			for (Card newone : cards) {
					if (!newone.getCardId().equals(cardId)) {
						request.setAttribute("card", newone);
		%>
		<li class="card"><jsp:include page="_card.jsp"></jsp:include></li>
		<%
			}
			}
					request.setAttribute("card", card);
		%>
	</ul>
</div>
<%
	}
%>
