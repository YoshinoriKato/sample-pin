
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@page import="com.samplepin.common.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	final String CSS_BASE = "btn btn-large btn-cell opacity80";

	final String CSS_ACTIVE = CSS_BASE + " active bold btn-success";

	final String CSS_NEGATIVE = CSS_BASE + " ";

	String url = request.getRequestURI();

	String cardId = request.getParameter("cardId");
	
	boolean inCardPage = url.contains("/card-comment.jsp")
	&& (cardId != null && !cardId.isEmpty());
	
	Card card = cardId != null ? Helper.getCardByID(cardId) : null;
	
	String userId = Helper.getUserId(request);
	
	String sorted = request.getParameter("sorted");
	
	String classLatest = (url.contains("/home.jsp") && sorted == null
	&& cardId == null) ? CSS_ACTIVE : CSS_NEGATIVE;
	
	String classMine = "mine".equals(sorted) ? CSS_ACTIVE : CSS_NEGATIVE;
	
	String classFootprints = "footprints".equals(sorted) ? CSS_ACTIVE
	: CSS_NEGATIVE;
	
	String classRecommend = "recommend".equals(sorted) ? CSS_ACTIVE : CSS_NEGATIVE;
%>

<div id="float-bottun" class="center opacity70">
	<a href="home.jsp" class="<%=classLatest%>">最新</a>

	<!-- 
	<a href="home.jsp?sorted=view" class="<%=CSS_BASE%>">Views</a>
	<a href="home.jsp?sorted=comment" class="<%=CSS_BASE%>">Comments</a>
	 -->

	<%
		if (!inCardPage && session.getAttribute("userId") != null) {
	%>
	<a href="home.jsp?sorted=mine" class="<%=classMine%>">所有</a>
	<a href="home.jsp?sorted=footprints"
		class="<%=classFootprints%>">足あと</a> <a
		href="home.jsp?sorted=recommend"
		class="<%=classRecommend%>">オススメ</a>
	<%
		}
	%>

<%-- 	<%
		if (card != null && userId != null
				&& card.getUserId().equals(userId)) {
	%>
	<a href="confirm-discard.jsp?cardId=<%=cardId%>" id="comment-button"
		class="btn btn-large btn-danger btn-cell x-large">Discard</a>
	<%
		}
	%> --%>

</div>
