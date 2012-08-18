<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- メニュー -->
<%!final String CLASS_ACTIVE = "class=\"active\""; %>
<%
	String url = request.getRequestURI();

	String classIndex = url.contains("/index.jsp") ? CLASS_ACTIVE : "";
	classIndex = url.contains("/footprints.jsp") ? CLASS_ACTIVE : classIndex;
	classIndex = url.contains("/recommend.jsp") ? CLASS_ACTIVE : classIndex;
	classIndex = url.contains("/card.jsp") ? CLASS_ACTIVE : classIndex;
	
	String classMakeCard = url.contains("/make-card.jsp") ? CLASS_ACTIVE
			: "";
	classMakeCard = url.contains("/confirm-make-card.jsp") ? CLASS_ACTIVE : classMakeCard;
	String classMyCard = url.contains("/my-card.jsp") ? CLASS_ACTIVE : "";
	String classLogin = url.contains("/login.jsp") ? CLASS_ACTIVE : "";
	String classLogout = url.contains("/logout.jsp") ? CLASS_ACTIVE : "";
	String classSignup = url.contains("/signup.jsp") ? CLASS_ACTIVE : "";
	String classAccount = url.contains("/account.jsp") ? CLASS_ACTIVE : "";
	String classProfile = url.contains("/profile.jsp") ? CLASS_ACTIVE : "";
%>

<script type="text/javascript" charset="utf-8">
	$(window).load(function() {
		$('.dropdown-toggle').dropdown();
	});
</script>

<div class="navbar navbar-fixed-top opacity90">
	<div class="navbar-inner">
		<div class="container">
			<button type="button" class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse">
				<span class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="brand"><img src="img/icon.png" style="height: 24px"></a>
			<div class="nav-collapse collapse">
				<ul class="nav">
					<li <%=classIndex%>><a href="index.jsp">Cards</a></li>
					<%
						String userId = (String) session.getAttribute("userId");
						if (userId != null) {
					%>
					<li <%=classMakeCard%>><a href="make-card.jsp">Make Card</a></li>
					<li class="divider-vertical"></li>
					<li <%=classProfile%>><a href="profile.jsp">My Profile</a></li>
					<li <%=classMyCard%>><a href="my-card.jsp">Design My Card</a></li>
					<li <%=classAccount%>><a href="account.jsp">Setting
							Account</a></li>
					<li class="divider-vertical"></li>
					<li <%=classLogout%>><a href="logout.do">Logout</a></li>
					<%
						} else {
					%>
					<li <%=classLogin%>><a href="login.jsp">Login</a></li>
					<li <%=classSignup%>><a href="signup.jsp">Sign up</a></li>
					<%
						}
					%>
				</ul>
			</div>
		</div>
	</div>
</div>