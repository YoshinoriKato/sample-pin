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
			<h2 class="tab-header">
				What's
				<%=Helper.NAME%>
				?
			</h2>
			<div class="chapter">
				<div class="profile large">
					<div class="section">
						<div class="item-group">
							<div class="item-label">概要</div>
							<div class="items">
								<p>
									<img src="img/doya_page.png?20121107" class="screen"
										width="400px" />
								</p>
								<h4>気軽に使える画像キュレーションサービスです。</h4>
								<p>あなたが検索した画像、それを見たときの感想・興味を共有してください。</p>
							</div>
						</div>
					</div>
					<div class="section">
						<div class="item-group">
							<div class="item-label">由来</div>
							<div class="items">
								<p>
									<img src="img/menu_doya_info.png">
								</p>
								<h4>大阪弁の「どうやろか？」、「どや！？」から来ています。</h4>
								<p>コミュニケーションで感想・興味を共有するさいのフレーズです。</p>
								<p>DOYA.infoでは、コミュニケーションの点を共有し、面へと転じさせます。</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- <div class="margin-bottom-default">
				<h3 class="tab-header">動画</h3>
				<div class="profile large">
					<div class="item-group">
						<div class="item-label"></div>
						<div class="items">
							<div class="margin-bottom-default">
								<video controls="controls"> <source
									src="../../icon-keeper/movie.mp4"></source></video>
							</div>
							<p>サイトでの利用例です。</p>
							<p>ヘルプからTwitterアカウントでログイン。</p>
							<p>ホーム画面、コメント、カード作成、ホーム画面切り替えをしています。</p>
						</div>
					</div>
				</div>
			</div> -->
			<div class="chapter">
				<h3 class="tab-header">ご登録</h3>
				<div class="profile large">
					<div class="section">
						<div class="item-group">
							<div class="item-label"></div>
							<div class="items">
								<p>
									<img src="img/ps_signup.png?20121107" class="screen"
										width="400px" />
								</p>
								<!--  -->
								<p>
									メニューの中の<a href="signup.jsp">"サインアップ"</a>をクリックしてください。
								</p>
								<p>
									メールアドレスを登録いただくか、<a href="oauth-twitter.jsp">Twitterログイン</a>、<a
										href="oauth-facebook.jsp">Facebookログイン</a>をしてください。
								</p>
								<p>
									迷ったら、<a href="oauth-twitter.jsp">Twitterログイン</a>をお選びください。
								</p>
							</div>
						</div>
					</div>
					<div class="section">
						<div class="item-group">
							<div class="item-label">メール</div>
							<div class="items">
								<p>
									<img src="img/ps_send-mail.png?20121107" class="screen"
										width="400px" />
								</p>
								<h4>メールアドレスを登録いただいた場合</h4>
								<ol>
									<li><a href="login.jsp">ログイン</a>のページへお送りしたメールのURLから飛んでください。</li>
									<li>ログインのページでは、登録したメールアドレスとお送りした仮パスワードでログインしていただけます。</li>
									<li>仮パスワードは、<a href="account.jsp">ユーザー</a>のページから変更ができます。
									</li>
								</ol>
							</div>
						</div>
					</div>
					<div class="section">
						<div class="item-group">
							<div class="item-label">Twitter</div>
							<div class="items">
								<p>
									<img src="img/ps_login-twitter.png?20121107" class="screen"
										width="400px" />
								</p>
								<h4>
									<a href="oauth-twitter.jsp">Twitterログイン</a>をされた場合
								</h4>
								<ol>
									<li>Twitterからの指示に沿って、アクセスを許可してください。</li>
								</ol>
							</div>
						</div>
					</div>

					<div class="section">
						<div class="item-group">
							<div class="item-label">Facebook</div>
							<div class="items">
								<p>
									<img src="img/ps_login-facebook.png?20121107" class="screen"
										width="400px" />
								</p>
								<h4>
									<a href="oauth-facebook.jsp">Facebookログイン</a>をされた場合
								</h4>
								<ol>
									<li>Facebookからの指示に沿って、アクセスを許可してください。</li>
								</ol>
								<p>※サインアップしなくても、ホーム画面やコメントをご覧いただけます。</p>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="chapter">
				<h3 class="tab-header">使い方</h3>
				<div class="profile large">
					<div class="section">
						<div class="item-group">
							<div class="item-label">使い方 1</div>
							<div class="items">
								<img src="img/doya_page.png?20121107" class="screen"
									width="400px" />
								<h4>
									メニューの中の<a href="home.jsp">"ホーム"</a>を押してください。
								</h4>
								<ol>
									<li>並んでいるカードを眺めてください。</li>
									<li>興味を感じたカードをクリックしてください。</li>
									<li>コメントの欄から、感想を投稿してください。</li>
									<li>なお、コメントの付いたカードは一覧の一番上に移動します。</li>
								</ol>
							</div>
						</div>
					</div>
					<div class="section">
						<div class="item-group">
							<div class="item-label">使い方 2</div>
							<div class="items">
								<img src="img/ps_add-card.png?20121107" class="screen"
									width="400px" />
								<h4>
									メニューの中の<a href="make-card.jsp">"+カード"</a>を押してください。
								</h4>
								<ol>
									<li>キーワードを入力して、画像を検索してください。</li>
									<li>気に入った画像を選択してください。</li>
									<li>タイトルとキャプションに感想や説明を書いて投稿してください。</li>
									<li>確認画面で承認すれば、あなたのカードが作成されます。</li>
								</ol>
							</div>
						</div>
					</div>
					<div class="section">
						<div class="item-group">
							<div class="item-label">使い方 3</div>
							<div class="items">使い方 1、2を繰り返してください。</div>
						</div>
					</div>
				</div>
			</div>
			<div class="chapter">
				<h3 class="tab-header">SNS ページ</h3>
				<div class="profile large">
					<div class="section">
						<div class="item-group">
							<div class="item-label">
								<a href="https://twitter.com/doya_info" target="_blank"
									style="margin-left: 50px;"><img src="img/bird_gray_48.png"></a>
							</div>
							<div class="items"></div>
						</div>
						<div class="item-group">
							<div class="item-label">Twitter</div>
							<div class="items">フォローしていただけると、最新の情報がタイムラインでご覧いただけます。</div>
						</div>
					</div>
					<div class="section">
						<div class="item-group">
							<div class="item-label">
								<a href="http://www.facebook.com/pages/DOYAinfo/495135587181076"
									target="_blank" style="margin-left: 50px;"><img
									src="img/f_logo.png" style="height: 48px; width: 48px;"></a>
							</div>
							<div class="items"></div>
						</div>
						<div class="item-group">
							<div class="item-label">Facebook</div>
							<div class="items">イイネ！していただけると、うれしいです。</div>
						</div>
					</div>
					<div class="section">
						<div class="item-group">
							<div class="item-label">
								<a href="http://dev-doya-info.tumblr.com/" target="_blank"
									style="margin-left: 50px;"><img src="img/Tumblr.png"
									style="height: 48px; width: 48px;"></a>
							</div>
							<div class="items"></div>
						</div>
						<div class="item-group">
							<div class="item-label">Tumblr</div>
							<div class="items">開発に関わる記録を残してます。</div>
						</div>
					</div>

				</div>
			</div>

			<div class="chapter">
				<h3 class="tab-header">お問い合わせフォーム</h3>
				<div class="profile large">
					<div class="section">
						<form method="post" class="form-horizontal" action="make-message.do">
							<div class="control-group">
								<div class="control-label">お問い合わせ</div>
								<div class="controls">
									<textarea id="message-text" class="textarea input-text"
										name="message" rows="8" placeholder="Please write a message."></textarea>
								</div>
							</div>
							<div class="control-group">
								<div class="control-label">メールアドレス</div>
								<div class="controls">
									<input type="mail" name="mail" class="text span4"><span
										class="help-inline small">必須ではありません、よろしければご記入ください。</span>
								</div>
							</div>
							<div class="control-group">
								<div class="control-label"></div>
								<div class="controls">
									<input type="submit" value="Send"
										class="btn btn-large btn-primary btn-cell">
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>

		</div>
	</div>
	<jsp:include page="_footer.jsp"></jsp:include>
</body>
</html>