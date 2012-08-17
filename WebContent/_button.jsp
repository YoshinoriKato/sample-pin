<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	String addClass = "btn btn-large btn-info btn-cell";
	String url = request.getRequestURI();
	boolean inCardPage = url.contains("/card.jsp");
%>

<div id="float-bottun" class="center page-menu">
	<%
		if (inCardPage) {
	%>
	<a id="comment-button" class="btn btn-large btn-primary btn-cell">Comment</a>
	<%
		}
	%>
	<a href="index.jsp" class="<%=addClass%>">Latest</a> <a
		href="index.jsp?sorted=view" class="<%=addClass%>">Viewed Most</a> <a
		href="index.jsp?sorted=comment" class="<%=addClass%>">Commented
		Most</a>
	<%
		if (session.getAttribute("userId") != null) {
	%>
	<a href="footprints.jsp" class="<%=addClass%>">My Footprints</a> <a
		href="recommend.jsp" class="<%=addClass%>">Recommend</a>
	<%
		}
	%>
</div>
