<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@page import="java.net.*"%>
<%@page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	Card card = (Card) request.getAttribute("card");
	boolean isNews = card.getUserId().equals("DOYA_NEWS");
	if(!isNews){
%>


<div class="margin-bottom-default">
	<h4 class="sub-header">画像</h4>
	<div class="card-body">
		<img src="<%=card.getImagePath()%>" alt="<%=card.getKeywords()%>"
			class="card-image">
	</div>
</div>
<%
	String keywords = card.getKeywords();
	if (Helper.valid(keywords)) {
%>
<div class="margin-bottom-default">
	<h4 class="sub-header">検索キーワード</h4>
	<div class="card-body">
		<p class="keywords"><%=keywords%></p>
	</div>
</div>
<%
	}
%>
<%
	if (Helper.valid(card.getSite())) {
		String path = card.getSite();
		path = path.length() > 40 ? path.substring(0, 40) + "..."
				: path;
		WebPage webPage = Helper.getWebPageByURL(card.getSite());
		if (Helper.valid(webPage)

		) {
%>
<div class="margin-bottom-default">
	<h4 class="sub-header">参照元</h4>
	<div class="card-body">
		<a href="<%=card.getSite()%>" target="_blank"> <%
 	if (Helper.valid(webPage.getTitle())) {
 %> <%=webPage.getTitle()%> <%
 	} else {
 %> <%=card.getSite()%> <%
 	}
 %><i class="icon-globe"></i></a>
	</div>
</div>
<%
	}
	}
	}
%>

