<%@page import="com.samplepin.common.Helper"%>
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- メニュー -->
<%!final String CLASS_ACTIVE = " active";%>
<%!final String CLASS_NEGATIVE = " ";%>
<%
	String url = request.getRequestURI();

	String classIndex = url.contains("/index.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classHome = url.contains("/home.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	classHome = url.contains("/tag.jsp") ? CLASS_ACTIVE : classHome;
	classHome = url.contains("/footprints.jsp") ? CLASS_ACTIVE
			: classHome;
	classHome = url.contains("/recommend.jsp") ? CLASS_ACTIVE
			: classHome;

	String classMakeCard = url.contains("/make-card.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	classMakeCard = url.contains("/confirm-make-card.jsp") ? CLASS_ACTIVE
			: classMakeCard;

	String classLogin = url.contains("/login.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	String classSignup = url.contains("/signup.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	String classAccount = url.contains("/account.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String userId = Helper.getUserId(request);
%>

<script type="text/javascript" charset="utf-8">
	attach(window, 'load', function() {
		$('.dropdown-toggle').dropdown();
	});
</script>

<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container">
			<ul class="nav pills">
			<li class="<%=classHome%>"><a href="home.jsp"><img src="img/home32.png" class="menu-icon" alt="home">&emsp;ホーム</a></li>
				<li class="dropdown <%=classHome%>"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"><b
						class="caret"></b></a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
						<li><a href="tag.jsp">タグ</a></li>
						<li class="divider"></li>
						<li><a href="home.jsp">最新</a></li>
						<li><a href="home.jsp?sorted=view">注目</a></li>
						<li><a href="home.jsp?sorted=comment">沸騰</a></li>
						<%
							if (userId != null && session.getAttribute("userId") != null) {
						%>
						<li class="divider"></li>
						<li><a href="home.jsp?sorted=mine">所有</a></li>
						<li><a href="home.jsp?sorted=footprints">足あと</a></li>
						<li><a href="home.jsp?sorted=recommend">オススメ</a></li>
						<%
							}
						%>
					</ul></li>
				<%
					if (Helper.valid(userId)) {
				%>
				
				<li class="<%=classMakeCard%>"><a href="make-card.jsp"><img src="img/linedpaperplus32.png" class="menu-icon" alt="add a card">&emsp;＋カード</a></li>
				
				<li class="dropdown <%=classAccount%>"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown"><img src="img/gear32.png" class="menu-icon" alt="etc.">&emsp;その他<b
						class="caret"></b></a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
						<li><a href="account.jsp"><img src="img/user32.png" class="menu-icon" alt="account">&emsp;設定</a></li>
						<li class="divider"></li>
						<li><a href="logout.do"><img src="img/stop32.png" class="menu-icon" alt="logout">&emsp;ログアウト</a></li>
						<li class="divider"></li>
						<li><a href="index.jsp"><img src="img/lightbulb32.png" class="menu-icon" alt="help">&emsp;ヘルプ</a></li>
					</ul></li>
				<%
					} else {
				%>
				
				<li class="<%=classLogin%>"><a href="login.jsp"><img src="img/check32.png" class="menu-icon" alt="login">&emsp;ログイン</a></li>
				<li class="<%=classSignup%>"><a href="signup.jsp"><img src="img/pencil32.png" class="menu-icon" alt="signup">&emsp;サインアップ</a></li>
				
				<li class="<%=classIndex%>"><a href="index.jsp"><img src="img/lightbulb32.png" class="menu-icon" alt="help">&emsp;ヘルプ</a></li>
				<%
					}
				%>
			</ul>
			<%
				if (Helper.valid(userId)) {
			%>
			<form class="navbar-search pull-right" method="get" action="home.jsp"
				id="card-search-box">
				<input type="hidden" name="sorted" value="search"><input
					type="search" name="words" placeholder="Search by Keywords"
					class="search-query">
			</form>
			<%
				}
			%>
		</div>
	</div>
</div>

