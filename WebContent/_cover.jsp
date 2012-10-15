<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Card card = (Card) request.getAttribute("card");
%>


<div>
	<div>
		<h4 class="card-header">画像</h4>
		<div class="card-body">
			<img src="<%=card.getImagePath()%>" class="card-image">
		</div>
	</div>
	<%
		String keywords = card.getKeywords();
		if (Helper.valid(keywords)) {
	%>
	<div class="margin-top-default">
		<h4 class="card-header">検索キーワード</h4>
		<div class="card-body">
			<p class="bold opacity60 keywords"><%=keywords%></p>
		</div>
	</div>
	<%
		}
	%>
	<%
		if (Helper.valid(card.getSite())) {
			String path = card.getSite();
			path = path.length() > 40
					? path.substring(0, 40) + "..."
					: path;
			WebPage webPage = Helper.getWebPageByURL(card.getSite());
			if (Helper.valid(webPage)

			&& Helper.valid(webPage.getTitle())) {
	%>
	<div class="margin-top-default">
		<%
			if (Helper.valid(webPage.getFavicon())) {
		%><%-- <img src=<%=webPage.getFavicon()%>> --%>
		<%
			}
		%>
		<h4 class="card-header">参照元</h4>
		<div class="card-body">
			<a href="<%=card.getSite()%>" target="_blank"><%=webPage.getTitle()%><i class="icon-globe"></i></a>
		</div>
	</div>
	<%
		}
		}
	%>
</div>

