package com.samplepin.servlet.util;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@WebServlet(urlPatterns = { "/_oauth-twitter.jsp" })
public class Utility6 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4773438253766653956L;

	public static void main(String[] args) {
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		Twitter twitter = (Twitter) request.getSession()
				.getAttribute("twitter");
		if (twitter != null) {
			RequestToken requestToken = (RequestToken) request.getSession()
					.getAttribute("requestToken");
			String verifier = request.getParameter("oauth_verifier");
			// アクセストークンを取得
			try {
				AccessToken accessToken = twitter.getOAuthAccessToken(
						requestToken, verifier);
				request.getSession().removeAttribute("requestToken");
				String token = accessToken.getToken();
				// String tokenSecret = accessToken.getTokenSecret();

				// 永続化する
				request.setAttribute("AT", token);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		} else {
			// Configureationを生成するためのビルダーオブジェクトを生成
			ConfigurationBuilder builder = new ConfigurationBuilder();

			// 既にこのアプリケーションでOAuth認証済みかどうかを確認するとき
			try {
				builder.setOAuthAccessToken("");
				builder.setOAuthAccessTokenSecret("");
				Configuration conf = builder.build();
				twitter = new TwitterFactory(conf).getInstance();
				// twitter4j.User user =
				twitter.verifyCredentials(); //
				// 認証済みならUserが取得できる。認証されてない場合例外がスローされる。
			} catch (TwitterException e1) {
				// コンシューマーキーとアクセスキーを設定
				builder.setOAuthConsumerKey("consumer key");
				builder.setOAuthConsumerSecret("consumer secret");
				// Configurationを生成
				Configuration conf = builder.build();
				// このファクトリインスタンスは再利用可能でスレッドセーフです
				twitter = new TwitterFactory(conf).getInstance();
			}

			request.getSession().setAttribute("twitter", twitter);
			try {
				RequestToken requestToken = null;
				requestToken = twitter
						.getOAuthRequestToken("http://localhost:8888/twitter/end_oauth");
				request.getSession().setAttribute("requestToken", requestToken);
				String oauthUrl = requestToken.getAuthorizationURL(); // このURLをHTMLのリンク先として出力する
				request.setAttribute("oauthUrl", oauthUrl);
			} catch (TwitterException e) {
				e.printStackTrace();
			}
			response.sendRedirect("twitter_start.vm");

		}

		response.sendRedirect("index.jsp");
	}
}
