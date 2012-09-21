
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
	
	String classView = "view".equals(sorted) ? CSS_ACTIVE : CSS_NEGATIVE;
	
	String classFootprints = "footprints".equals(sorted) ? CSS_ACTIVE
	: CSS_NEGATIVE;
	
	String classRecommend = "recommend".equals(sorted) ? CSS_ACTIVE : CSS_NEGATIVE;
%>

<div id="float-bottun" class="opacity70">
<ul class="menu">
	<li><a href="home.jsp" class="<%=classLatest%>">最新</a></li>

	<li><a href="home.jsp?sorted=view" class="<%=classView%>">注目</a></li>
	<!-- 
	<a href="home.jsp?sorted=comment" class="<%=CSS_BASE%>">Comments</a>
	 -->

	<%
		if (!inCardPage && session.getAttribute("userId") != null) {
	%>
	<li><a href="home.jsp?sorted=mine" class="<%=classMine%>">所有</a></li>
	<li><a href="home.jsp?sorted=footprints"
		class="<%=classFootprints%>">足あと</a></li>
	<li><a
		href="home.jsp?sorted=recommend"
		class="<%=classRecommend%>">オススメ</a></li>
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

	<li><form class="form-search" method="get"
			action="home.jsp" id="card-search-box">
		<input type="hidden" name="sorted" value="search"> <input
			type="text" class="input-medium search-query" name="words"
			placeholder="Search by Keywords">
			<!--  <input type="submit"
			value="^" class="btn bold btn-cell"> -->
	</form></li>
</ul>
</div>
