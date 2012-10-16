<%@page import="com.samplepin.Card"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Card card = (Card) request.getAttribute("card");
	List<Card> cards = Helper.searchCards(request, card.getKeywords());
	if (!cards.isEmpty()) {
%>

<h2 class="card-header">関連するカード</h2>
<div class="card-body">
	<ul>
		<%
			for (Card newone : cards) {
					int length = newone.getCaption().length();
					String caption = length > 40 ? newone.getCaption()
							.substring(0, 40) + "..." : newone.getCaption();
		%>
		<li><a
			href="card-comment.jsp?cardId=<%=newone.getCardId()%>&type=comment&image=open"><span
				class="deco"><%=caption%></span></a> (view:<%=newone.getView()%>,
			comment:<%=newone.getLikes()%>)</li>
		<%
			}
		%>
	</ul>
</div>
<%
	}
%>
