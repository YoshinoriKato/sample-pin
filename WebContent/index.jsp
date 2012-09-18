<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="com.samplepin.common.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="_header.jsp"></jsp:include>
<jsp:include page="_effect.jsp"></jsp:include>
</head>

<body>
	<jsp:include page="_topbar.jsp" flush="true" />
	<div id="title">What?</div>
	<div id="main">
		<div id="input-window">
			<div class="cell">
				<h1 class="tab-header">
					What's
					<%=Helper.NAME%>
					?
				</h1>
				<div class="profile large">
					<div class="item-group">
						<div class="items">
							<img src="img/title.png" style="min-width: 240px;">
						</div>
					</div>
					<div class="item-group">
						<div class="item-label">概要</div>
						<div class="items">
							<p>気軽に使えるキュレーションサービスです。</p>
							<p>検索した画像とそれを見たときの感想、</p>
							<p>あなたの興味を共有してください。</p>
						</div>
					</div>
					<hr>
					<div class="item-group">
						<div class="item-label">はじめに</div>
						<div class="items">
							メニューの中の<a href="signup.jsp">"サインアップ"</a>をクリックしてください。
							<ol>
								<li>メールアドレスを書いていただくか、<a href="oauth-twitter.jsp">Twitter
										Login</a>を実施してください。</li>
								<li>もし、メールアドレスを登録していただけたなら、<a href="login.jsp">ログイン</a>のページへお送りしたメールのURLから飛んでください。</li>
								<li>ログインのページでは、登録したメールアドレスとお送りした仮パスワードでログインしていただけます。</li>
								<li>仮パスワードは、<a href="account.jsp">ユーザー</a>のページから変更ができます。</li>
							</ol>
						</div>
					</div>
					<hr>
					<div class="item-group">
						<div class="item-label">使い方 1</div>
						<div class="items">
							メニューの中の<a href="home.jsp">"ホーム"</a>を押してください。
							<ol>
								<li>並んでいるカードを眺めてください。</li>
								<li>興味を感じたカードをクリックしてください。</li>
								<li>コメントの欄から、感想を投稿してください。</li>
								<li>なお、コメントの付いたカードは一覧の一番上に移動します。</li>
							</ol>
						</div>
					</div>
					<div class="item-group">
						<div class="item-label">使い方 2</div>
						<div class="items">
							<a href="home.jsp">"ホーム"</a>の上部に注目してください。
							<ol>
								<li>Search Imagesという欄があります。</li>
								<li>キーワードを入力して、画像を検索してください。</li>
								<li>気に入った画像を選択してください。</li>
								<li>感想や説明を書いて投稿してください。</li>
								<li>確認画面で承認すれば、あなたのカードが作成されます。</li>
							</ol>
						</div>
					</div>
					<div class="item-group">
						<div class="item-label">使い方 3</div>
						<div class="items">使い方 1、2を繰り返してください。</div>
					</div>
					<hr>
					<div class="item-group">
						<div class="item-label">SNS ページ</div>
						<div class="items">
							<a href="https://twitter.com/doya_info" target="_blank"
								style="margin-left: 50px;"><img src="img/bird_gray_48.png"></a><a
								href="http://www.facebook.com/pages/DOYAinfo/495135587181076"
								target="_blank" style="margin-left: 80px;"><img
								src="img/f_logo.png" style="height: 40px; width: 40px;"></a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>