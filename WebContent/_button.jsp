
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String addClass = "btn btn-large btn-cell large opacity80";
	String url = request.getRequestURI();
	String cardId = request.getParameter("cardId");
	boolean inCardPage = url.contains("/card-comment.jsp")
			&& (cardId != null && !cardId.isEmpty());
	Card card = cardId != null ? Helper.getCardInfoByID(cardId) : null;
	String userId = Helper.getUserId(request);
	String sorted = request.getParameter("sorted");
	final String activeCSS = "x-large bold";
	String classLatest = url.contains("/home.jsp") && sorted == null
			&& cardId == null ? activeCSS : "";
	String classMine = "mine".equals(sorted) ? activeCSS : "";
	String classFootprints = "footprints".equals(sorted) ? activeCSS
			: "";
	String classRecommend = "recommend".equals(sorted) ? activeCSS : "";
%>

<div id="float-bottun" class="center page-menu">
	<%
		if (inCardPage) {
	%>
	<a id="comment-button"
		class="btn btn-large btn-primary btn-cell xx-large">Comment</a>
	<%
		}
	%>
	<a href="home.jsp" class="<%=addClass%> <%=classLatest%>">Latest</a>

	<!-- 
	<a href="home.jsp?sorted=view" class="<%=addClass%>">Views</a>
	<a href="home.jsp?sorted=comment" class="<%=addClass%>">Comments</a>
	 -->

	<%
		if (session.getAttribute("userId") != null) {
	%>
	<a href="home.jsp?sorted=mine" class="<%=addClass%> <%=classMine%>">Mine</a>
	<a href="home.jsp?sorted=footprints"
		class="<%=addClass%> <%=classFootprints%>">Footprints</a> <a
		href="home.jsp?sorted=recommend"
		class="<%=addClass%> <%=classRecommend%>">Recommend</a>
	<%
		}
	%>

	<%
		if (card != null && userId != null
				&& card.getUserId().equals(userId)) {
	%>
	<a href="confirm-discard.jsp?cardId=<%=cardId%>" id="comment-button"
		class="btn btn-large btn-danger btn-cell x-large">Discard</a>
	<%
		}
	%>

</div>
