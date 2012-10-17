<%@page import="com.samplepin.Card"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Card card = (Card) request.getAttribute("card");
%>

<h2 class="card-header">その他のカード</h2>
<div class="card-body">
	<ul>
		<%
			List<Card> cards = Helper.newCards(card.getUpdateDate());
			for (Card newone : cards) {
				String caption = Helper
						.getOmitedString(newone.getTitle(), 40);
		%>
		<li><a
			href="card-comment.jsp?cardId=<%=newone.getCardId()%>"><span
				class="deco"><%=caption%></span></a> (view:<%=newone.getView()%>,
			comment:<%=newone.getLikes()%>)</li>
		<%
			}
		%>
	</ul>
</div>
