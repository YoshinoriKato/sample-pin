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

	String classIndex = url.contains("/index.jsp")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classHome = url.contains("/home.jsp")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	classHome = url.contains("/tag.jsp") ? CLASS_ACTIVE : classHome;
	classHome = url.contains("/footprints.jsp")
			? CLASS_ACTIVE
			: classHome;
	classHome = url.contains("/recommend.jsp")
			? CLASS_ACTIVE
			: classHome;

	String classTag = url.contains("/tag.jsp")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classFolder = url.contains("/folder.jsp")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	classFolder = "folder".equals(request.getParameter("sorted"))
			? CLASS_ACTIVE
			: classFolder;

	String classMakeFolder = url.contains("/home.jsp?select=true")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classMakeCard = url.contains("/make-card.jsp")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	classMakeCard = url.contains("/confirm-make-card.jsp")
			? CLASS_ACTIVE
			: classMakeCard;

	String classLogin = url.contains("/login.jsp")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	String classSignup = url.contains("/signup.jsp")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classAccount = url.contains("/account.jsp")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;
	classAccount = url.contains("/index.jsp")
			? CLASS_ACTIVE
			: classAccount;

	String classProfile = url.contains("/profile.jsp")
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String userId = Helper.getUserId(session);
	User user = Helper.getUserById(userId);

	String cardId = request.getParameter("cardId");

	String sorted = request.getParameter("sorted");

	String classLatest = (url.contains("/home.jsp") && sorted == null && cardId == null)
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classMine = "mine".equals(sorted)
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classView = "view".equals(sorted)
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classComment = "comment".equals(sorted)
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classFootprints = "footprints".equals(sorted)
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String classRecommend = "recommend".equals(sorted)
			? CLASS_ACTIVE
			: CLASS_NEGATIVE;

	String select = request.getParameter("select");
	boolean isSelectMode = "true".equals(select);

	String folderId = request.getParameter("folderId");
	Folder folder = Helper.valid(folderId) ? Helper
			.getFolderById(folderId) : null;
%>


<script type="text/javascript" charset="utf-8">
	attach(window, 'load', function() {
		$('.dropdown-toggle').dropdown();
	});

	var $height = $('#main-menu').height;
	attach(window, 'scroll', function() {
		if (isHided()) {
			pushPull('#main-menu', '#null', 5);
		} else {
			pushPull('#null', '#main-menu', 5);
		}
	});
</script>

<div class="navbar navbar-fixed-top">
	<div id="main-menu" class="navbar-inner">
		<div class="container">
			<ul class="nav pills">
				<li class="hidden-phone center"><a><img src="img/menu_doya_info.png"
						style="height: 32px;"><br>
					<span class="user-name">Forbusからトトロまで</span></a></li>
				<%
					if (Helper.valid(user)) {
				%>
				<li class="hidden-phone <%=classProfile%>"><a href="profile.jsp"
					class="center"><img src="<%=user.getImagePath()%>"
						class="menu-user-icon img-circle"><br>
					<span class="user-name">あなた</span></a></li>
				<%
					}
				%>
				<li class="<%=classHome%>"><a href="home.jsp"
					class="center x-small"><img src="img/home32.png"
						class="menu-icon" alt="home"><br>ホーム</a></li>
				<%
					if (Helper.valid(userId)) {
				%>

				<li class="<%=classMakeCard%>"><a href="make-card.jsp"
					class="center x-small"><img src="img/linedpaperplus32.png"
						class="menu-icon" alt="add a card"><br>＋カード</a></li>

				<li class="<%=classMakeFolder%>"><a href="home.jsp?select=true"
					class="center x-small"><img src="img/folderplus32.png"
						class="menu-icon" alt="add a card"><br>＋フォルダ</a></li>

				<li class="dropdown <%=classAccount%>"><a href="#"
					class="dropdown-toggle center x-small" data-toggle="dropdown"><img
						src="img/gear32.png" class="menu-icon" alt="etc."><b
						class="caret"></b><br>その他</a>
					<ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
						<li><a href="account.jsp" class="x-small"><img
								src="img/user32.png" class="menu-icon" alt="account">&emsp;設定</a></li>
						<li class="divider"></li>
						<li><a href="logout.do" class="x-small"><img
								src="img/stop32.png" class="menu-icon" alt="logout">&emsp;ログアウト</a></li>
						<li class="divider"></li>
						<li><a href="index.jsp" class="x-small"><img
								src="img/lightbulb32.png" class="menu-icon" alt="help">&emsp;ヘルプ</a></li>
					</ul></li>
				<%
					} else {
				%>

				<li class="<%=classLogin%>"><a href="login.jsp"
					class="center x-small"><img src="img/check32.png"
						class="menu-icon" alt="login"><br>ログイン</a></li>
				<li class="<%=classSignup%>"><a href="signup.jsp"
					class="center x-small"><img src="img/pencil32.png"
						class="menu-icon" alt="signup"><br>サインアップ</a></li>

				<li class="<%=classIndex%>"><a href="index.jsp"
					class="center x-small"><img src="img/lightbulb32.png"
						class="menu-icon" alt="help"><br>ヘルプ</a></li>
				<%
					}
				%>
			</ul>
			<%
				if (Helper.valid(userId)) {
			%>
			<form class="navbar-search pull-right hidden-phone" method="get" action="home.jsp"
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

	<%
		if (url.contains("/home.jsp") || url.contains("/tag.jsp")
				|| url.contains("/folder.jsp")) {
	%>
	<div class="navbar"
		style="margin: 0px; width: 100%; position: absolute;">
		<div class="navbar-inner center">
			<ul class="nav">
				<li class="hidden-phone"><a>フィルター：</a></li>
				<li class="<%=classLatest%>"><a href="home.jsp">最新</a></li>
				<li class="hidden-phone <%=classView%>"><a href="home.jsp?sorted=view">注目</a></li>
				<li class="hidden-phone <%=classComment%>"><a href="home.jsp?sorted=comment">沸騰</a></li>
				<%
					if (userId != null && session.getAttribute("userId") != null) {
				%>
				<li class="divider-vertical"></li>
				<li class="hidden-phone <%=classMine%>"><a href="home.jsp?sorted=mine">所有</a></li>
				<li class="hidden-phone <%=classFootprints%>"><a
					href="home.jsp?sorted=footprints">足あと</a></li>
				<li class="hidden-phone <%=classRecommend%>"><a
					href="home.jsp?sorted=recommend">オススメ</a></li>
				<%
					}
				%>
				<li class="divider-vertical"></li>
				<li class="<%=classTag%>"><a href="tag.jsp">タグ</a></li>
				<li class="divider-vertical"></li>
				<li class="<%=classFolder%>"><a href="folder.jsp">フォルダ</a></li>
				<li class="divider-vertical"></li>
				<li><a id="scrolled-val"></a></li>
			</ul>
		</div>
	</div>
	<%
		if (isSelectMode) {
	%>
	<jsp:include page="_folder.jsp"></jsp:include>
	<%
		}
	%>
	<%
		if (folder != null) {
	%>
	<div class="float-card" style="width: 360px;">
		<div id="select-small-card" class="x-large"><%=folder.getFolderName()%></div>
		<%
			if (folder.getUserId().equals(userId)) {
		%>
		<div class="close-button">
			<a href="confirm-disfolder.jsp?folderId=<%=folder.getFolderId()%>">&times;</a>
		</div>
		<%
			}
		%>
	</div>
	<%
		}
	%>
	<%
		}
	%>
</div>

<noscript>
	<div>
		<p>現在<strong>JavaScriptが無効</strong>になっています。</p>
		<p><%=Helper.NAME%>のすべての機能を利用するためには、<strong>JavaScriptの設定を有効</strong>にしてください。</p>
	</div>
</noscript>
