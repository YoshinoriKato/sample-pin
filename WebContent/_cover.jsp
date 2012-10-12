<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Card card = (Card) request.getAttribute("card");
%>

<div class="middle">
	<%
		if (Helper.valid(card.getSite())) {
			String path = card.getSite();
			path = path.length() > 40 ? path.substring(0, 40) + "..."
					: path;
			WebPage webPage = Helper.getWebPageByURL(card.getSite());
			if (Helper.valid(webPage)

			&& Helper.valid(webPage.getTitle())) {
	%>
	<h2 class="margin-top-default">
		<%
			if (Helper.valid(webPage.getFavicon())) {
		%><%-- <img src=<%=webPage.getFavicon()%>> --%>
		<%
			}
		%>
		<a href="<%=card.getSite()%>" target="_blank"><%=webPage.getTitle()%></a>
	</h2>
	<%
		}
		}
	%>

	<div class="margin-top-default">
		<img src="<%=card.getImagePath()%>" id="image-origin">
	</div>
	
	<%
		String keywords = card.getKeywords();
		if (Helper.valid(keywords)) {
	%>
	<div class="margin-top-default">
		<span class="large bold opacity60 keywords"><%=keywords%></span>
	</div>
	<%
		}
	%>


</div>






