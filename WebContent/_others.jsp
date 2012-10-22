<%@page import="com.samplepin.Card"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String cardId = request.getParameter("cardId");
	Card card = (Card) request.getAttribute("card");
	List<Card> cards = Helper.newCards(card.getUpdateDate());
%>

<h2 class="card-header">その他のカード</h2>
<div id="card-recent">
	<ul id="recent">
		<%
			for (Card other : cards) {
				request.setAttribute("card", other);
		%>
		<li class="card"><jsp:include page="_card.jsp"></jsp:include></li>
		<%
			}
		%>
	</ul>
</div>

<%
	request.setAttribute("card", card);
%>
