<%@page import="com.samplepin.common.Helper"%>
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- メニュー -->
<%!final String CLASS_ACTIVE = "class=\"active em3 bold\""; %>
<%!final String CLASS_NEGATIVE = "class=\"em5\""; %>
<%
	String url = request.getRequestURI();

	String classIndex = url.contains("/index.jsp") ? CLASS_ACTIVE : CLASS_NEGATIVE;

	String classTag = url.contains("/tag.jsp") ? CLASS_ACTIVE : CLASS_NEGATIVE;

	String classHome = url.contains("/home.jsp") ? CLASS_ACTIVE : CLASS_NEGATIVE;
	classHome = url.contains("/footprints.jsp") ? CLASS_ACTIVE : classHome;
	classHome = url.contains("/recommend.jsp") ? CLASS_ACTIVE : classHome;
	classHome = url.contains("/card.jsp") ? CLASS_ACTIVE : classHome;
	
	String classMakeCard = url.contains("/make-card.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	classMakeCard = url.contains("/confirm-make-card.jsp") ? CLASS_ACTIVE : classMakeCard;
	String classMyCard = url.contains("/my-card.jsp") ? CLASS_ACTIVE : CLASS_NEGATIVE;
		
	String classLogin = url.contains("/login.jsp") ? CLASS_ACTIVE : CLASS_NEGATIVE;
	String classLogout = url.contains("/logout.jsp") ? CLASS_ACTIVE : CLASS_NEGATIVE;
	String classSignup = url.contains("/signup.jsp") ? CLASS_ACTIVE : CLASS_NEGATIVE;
	String classAccount = url.contains("/account.jsp") ? CLASS_ACTIVE : CLASS_NEGATIVE;
	String classProfile = url.contains("/profile.jsp") ? CLASS_ACTIVE : CLASS_NEGATIVE;
%>

<script type="text/javascript" charset="utf-8">
	$(window).load(function() {
		$('.dropdown-toggle').dropdown();
	});
</script>

<div class="navbar navbar-fixed-top opacity90">
	<div class="navbar-inner center">
		<div class="container">
			<button type="button" class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse">
				<span class="icon-bar"></span>
			</button>
			
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li <%=classIndex%>><a href="index.jsp">?</a></li>
					<%-- <li <%=classTag%>><a href="tag.jsp">Tags</a></li> --%>
					<li <%=classHome%>><a href="home.jsp">Home</a></li>
					<%
						String userId = (String) session.getAttribute("userId");
						if (userId != null) {
							User user = Helper.getUserById(userId);
					%>
					<li <%=classMakeCard%>><a href="make-card.jsp">+</a></li>

					<!-- 
					<li <%=classProfile%>><a href="profile.jsp">My Profile</a></li>
					<li <%=classMyCard%>><a href="my-card.jsp">Design My Card</a></li>

					<li class="divider-vertical"></li>
					 -->
					<li <%=classAccount%>><a href="account.jsp">U</a></li>
					<%-- <li <%=classLogout%>><a href="logout.do">x</a></li> --%>
					<li>
						<form class="navbar-search pull-left" method="get" action="home.jsp">
							<input type="hidden" name="sorted" value="search"> 
							<input type="text" class="search-query input-short" name="words" placeholder="search">
						</form>
					</li>
					<%
						} else {
					%>
					<li <%=classLogin%>><a href="login.jsp">Start</a></li>
					<li <%=classSignup%>><a href="signup.jsp">@1st</a></li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
	</div>
</div>
