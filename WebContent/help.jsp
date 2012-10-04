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
	<div id="main">
		<div class="margin-bottom-default">
			<h1 class="tab-header">
				What's
				<%=Helper.NAME%>
				?
			</h1>
			<div class="profile">
				<h3 class="tab-header">はじめに</h3>
				<div class="item-group">
					<div class="item-label">概要</div>
					<div class="items">
						<p>気軽にキュレーションができるソーシャル検索サービスです。</p>
						<p>あなたが検索した画像、それを見たときの感想・興味を共有してください。</p>
						<p>現在はBeta運用中です。</p>
					</div>
				</div>
				<div class="item-group">
					<div class="item-label">由来</div>
					<div class="items">
						<p>DOYAとは、大阪弁の「どうやろか？」、「どや！」を意味する言葉から来ています。</p>
						<p>DOYAな情報だから、DOYA.infoです。。</p>
						<p>関西のカルチャーも織り交ぜつつ展開する大阪発のソーシャルサービスです。</p>
					</div>
				</div>
				<div class="item-group">
					<div class="item-label">動画</div>
					<div class="items">
						<div class="margin-bottom-default">
							<video controls="controls"> <source
								src="../../icon-keeper/movie.mp4"></source></video>
						</div>
						<p>サイト利用の一例です。</p>
						<p>ヘルプからTwitterアカウントでログイン、ホーム画面、コメント、カード作成、ホーム画面切り替えをしています。</p>
					</div>
				</div>
			</div>
		</div>
		<div class="margin-bottom-default">
			<h3 class="tab-header">ご登録</h3>
			<div class="profile">
				<div class="item-group">
					<div class="item-label"></div>
					<div class="items">
						<p>
							メニューの中の<a href="signup.jsp">"サインアップ"</a>をクリックしてください。
						</p>
						<p>※サインアップしなくても、ホーム画面やコメントをご覧いただけます。</p>
						<ol>
							<li>メールアドレスを登録いただくか、<a href="oauth-twitter.jsp">Twitterログイン</a>をしてください。
							</li>
							<li>メールアドレスを登録いただいた場合
								<ol>
									<li><a href="login.jsp">ログイン</a>のページへお送りしたメールのURLから飛んでください。</li>
									<li>ログインのページでは、登録したメールアドレスとお送りした仮パスワードでログインしていただけます。</li>
									<li>仮パスワードは、<a href="account.jsp">ユーザー</a>のページから変更ができます。
									</li>
								</ol>
							</li>
							<li><a href="oauth-twitter.jsp">Twitterログイン</a>をされた場合
								<ol>
									<li>Twitterからの指示に沿って、アクセスを許可してください。</li>
								</ol></li>
						</ol>
						<p>※Bata運用中にご登録いただけたユーザーさまには、今後のサービスの変遷のなかで優遇処置をとらさせていただきます。</p>
					</div>
				</div>
			</div>
		</div>
		<div class="margin-bottom-default">
			<h3 class="tab-header">使い方</h3>
			<div class="profile">
				<div class="item-group">
					<div class="item-label">使い方 1</div>
					<div class="items">
						<p>
							メニューの中の<a href="home.jsp">"ホーム"</a>を押してください。
						</p>
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
						<p>
							<a href="home.jsp">"ホーム"</a>の上部に注目してください。
						</p>
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
			</div>
		</div>
		<div class="margin-bottom-default">
			<h3 class="tab-header">SNS ページ</h3>
			<div class="profile">
				<div class="item-group">
					<div class="item-label">Twitter</div>
					<div class="items">
						<a href="https://twitter.com/doya_info" target="_blank"
							><img src="img/bird_gray_48.png"></a>
					</div>
					<div class="item-label">Facebook</div>
					<div class="items">
						<a href="http://www.facebook.com/pages/DOYAinfo/495135587181076"
							target="_blank" ><img
							src="img/f_logo.png" style="height: 40px; width: 40px;"></a>
					</div>
				</div>
				<div class="item-group">
					<div class="item-label"></div>
					<div class="items">
						<p>サイト外のユーザーさまの交流のためにTwitter、Facebookの専用アカウントをご用意しました。</p>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>