<%@page import="com.samplepin.Card"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Card card = (Card) request.getAttribute("card");
%>

<h4>その他のカード</h4>
<ul>
	<%
		List<Card> cards = Helper.newCards(card.getUpdateDate());
		for (Card newone : cards) {
			int length = newone.getCaption().length();
			String caption = length > 40 ? newone.getCaption().substring(0,
					40)
					+ "..." : newone.getCaption();
	%>
	<li><a
		href="card-comment.jsp?cardId=<%=newone.getCardId()%>&type=comment&image=open"><span
			class="deco"><%=caption%></span></a> (view:<%=newone.getView()%>,
		comment:<%=newone.getLikes()%>)</li>
	<%
		}
	%>
</ul>
