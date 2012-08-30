package com.samplepin.servlet.oauth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import com.mongodb.MongoException;
import com.samplepin.TwitterAccount;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;
import com.samplepin.servlet.LoginServlet;

@WebServlet(urlPatterns = { "/oauth-twitter.jsp" })
public class OAuthTwitterServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -8159111239999377057L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		HttpSession session = request.getSession();
		try {
			Twitter twitter = (Twitter) session.getAttribute("Twitter");
			if (twitter == null) {
				request(request, response, session);
			} else {
				redirect(request, response, session);
			}
		} catch (Exception e) {
			session.setAttribute("error", e);
			throw new ServletException(e);
		}
	}

	void redirect(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException, MongoException,
			TwitterException {
		Twitter twitter = (Twitter) session.getAttribute("Twitter");
		String verifier = request.getParameter("oauth_verifier");
		AccessToken accessToken = null;
		session.removeAttribute("Twitter");

		try {
			// RequestTokenからAccessTokenを取得
			accessToken = twitter.getOAuthAccessToken(
					(RequestToken) session.getAttribute("RequestToken"),
					verifier);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (accessToken != null) {
			// AccessTokenをセッションに保持
			// session.setAttribute("AccessToken", accessToken.getToken());
			// session.setAttribute("AccessTokenSecret",
			// accessToken.getTokenSecret());

			String userId = Helper.generatedIdString("TW_");
			TwitterService.storeAccessToken(userId, twitter.verifyCredentials()
					.getId(), accessToken);

			// login
			try (ACMongo mongo = new ACMongo()) {
				TwitterAccount twitterAccount = mongo
						.createQuery(TwitterAccount.class)
						.filter("user_id = ", accessToken.getUserId()).get();
				LoginServlet.login(request, twitterAccount.getUserId());
				LoginServlet.makeCookie(response, twitterAccount.getUserId());
			}

			response.sendRedirect("home.jsp");
		} else {
			response.setContentType("text/plain");
			response.getWriter().println("AccessTokenがNullってます！");
		}
	}

	void request(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		// Titterオブジェクトの生成
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(TwitterService.consumerKey,
				TwitterService.consumerSecret);

		try {
			// リクエストトークンの生成
			RequestToken reqToken = twitter.getOAuthRequestToken();

			// RequestTokenとTwitterオブジェクトをセッションに保存
			session.setAttribute("RequestToken", reqToken);
			session.setAttribute("Twitter", twitter);

			// 認証画面にリダイレクトするためのURLを生成
			String strUrl = reqToken.getAuthorizationURL();
			response.sendRedirect(strUrl);
		} catch (Exception e) {
			log(e.getMessage());
		}
	}
}
