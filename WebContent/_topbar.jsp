<%@page import="com.samplepin.common.Helper"%>
<%@page import="com.samplepin.servlet.*"%>
<%@page import="java.util.*"%>
<%@page import="com.samplepin.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- メニュー -->
<%!final String CLASS_ACTIVE = "class=\"active btn-success bold btn btn-large btn-cell opacity80\"";%>
<%!final String CLASS_NEGATIVE = "class=\"bold btn btn-large btn-cell opacity80\"";%>
<%
	String url = request.getRequestURI();

	String classIndex = url.contains("/index.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classTag = url.contains("/tag.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classHome = url.contains("/home.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	classHome = url.contains("/footprints.jsp") ? CLASS_ACTIVE
			: classHome;
	classHome = url.contains("/recommend.jsp") ? CLASS_ACTIVE
			: classHome;
	classHome = url.contains("/card.jsp") ? CLASS_ACTIVE : classHome;

	String classMakeCard = url.contains("/make-card.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	classMakeCard = url.contains("/confirm-make-card.jsp") ? CLASS_ACTIVE
			: classMakeCard;
	String classMyCard = url.contains("/my-card.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classLogin = url.contains("/login.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	String classLogout = url.contains("/logout.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	String classSignup = url.contains("/signup.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	String classAccount = url.contains("/account.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	String classProfile = url.contains("/profile.jsp") ? CLASS_ACTIVE
			: CLASS_NEGATIVE;
%>

<script type="text/javascript" charset="utf-8">
	$(window).load(function() {
		$('.dropdown-toggle').dropdown();
	});
</script>

<div id="navigation">
	<div id="navi-menu">
		<ul id="menu">
			<li><a <%=classTag%> href="tag.jsp">タグ</a></li>
			<li><a <%=classHome%> href="home.jsp">ホーム</a></li>
			<%
				String userId = Helper.getUserId(request);
				if (userId != null) {
					User user = Helper.getUserById(userId);
			%>
			<li><a <%=classMakeCard%> href="make-card.jsp">+追加</a></li>

			<!-- 
		<li><a <%=classProfile%> href="profile.jsp">My Profile</a></li>
		<li><a <%=classMyCard%> href="my-card.jsp">Design My Card</a></li>

		<li><a class="divider-vertical"></li>
		 -->
			<li><a <%=classAccount%> href="account.jsp">ユーザー</a> <a
				<%=classLogout%> href="logout.do">ログアウト</a></li>
			<%
				} else {
			%>
			<li><a <%=classLogin%> href="login.jsp">ログイン</a> <a
				<%=classSignup%> href="signup.jsp">サインアップ</a></li>
			<%
				}
			%>
			<li><a <%=classIndex%> href="index.jsp">ヘルプ</a></li>

			<li id="card-search-box"><form class="form-search" method="get"
					action="home.jsp">
					<input type="hidden" name="sorted" value="search"> <input
						type="text" class="input-medium search-query" name="words"
						placeholder="Search by Keywords">
						<!--  <input type="submit"
						value="^" class="btn bold btn-cell"> -->
				</form></li>
		</ul>
	</div>
</div>