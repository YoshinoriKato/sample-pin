
<!-- メニュー -->

<%
	String url = request.getRequestURI();
	final String CLASS_ACTIVE = "class=\"active\"";
	String classIndex = url.contains("index") ? CLASS_ACTIVE : "";
	String classMakeCard = url.contains("make-card")
			? CLASS_ACTIVE
			: "";
	String classMyCard = url.contains("my-card") ? CLASS_ACTIVE : "";
	String classLogin = url.contains("login") ? CLASS_ACTIVE : "";
	String classLogout = url.contains("logout") ? CLASS_ACTIVE : "";
	String classSignup = url.contains("signup") ? CLASS_ACTIVE : "";
%>

<script type="text/javascript" charset="utf-8">
	$(window).load(function() {
		$('.dropdown-toggle').dropdown();
	});
</script>

<div class="navbar navbar-fixed-top">
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
					<li class="divider-vertical"></li>
					<%
						String userId = (String) session.getAttribute("userId");
						if (userId != null) {
					%>
					<li <%=classMakeCard%>><a href="make-card.jsp">Make Card</a></li>
					<li <%=classMyCard%>><a href="my-card.jsp">My Card</a></li>
					<li <%=classLogout%>><a href="footprints.jsp">Footprints</a></li>
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
