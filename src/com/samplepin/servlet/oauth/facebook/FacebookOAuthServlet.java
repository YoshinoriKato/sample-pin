package com.samplepin.servlet.oauth.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.FacebookAccount;
import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/oauth-facebook.jsp" })
public class FacebookOAuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7700928228357051804L;

	private String redirect_uri = "http://www45022u.sakura.ne.jp/mcq/sys/facebook.jsp";

	HttpSession session;

	static String FACEBOOK_APP_ID = "";

	static String FACEBOOK_APP_ID_SECRET = "";

	static String KEY_FACEBOOK_INFO = "";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.session = req.getSession();
		String code = req.getParameter("code");

		if (code != null) {
			// take 1
			URL url = new URL(getCode(req, resp, code));
			URLConnection conn = url.openConnection();
			FacebookOAuth oauth = getFacebookOAuth(conn.getInputStream());
			this.session.setAttribute("facebook_url", url.toString());

			log("access_token: " + oauth.access_token);

			if ((oauth != null) && Helper.valid(oauth.access_token)) {
				URL url2 = new URL("https://graph.facebook.com/me"
						+ "?access_token=" + oauth.access_token
						+ "&fields=name,picture,id");
				URLConnection conn2 = url2.openConnection();
				FacebookUserInfo facebookInfo = Helper.readJson(
						conn2.getInputStream(), FacebookUserInfo.class);

				if (Helper.valid(facebookInfo)) {
					// take 2
					saveAccesToken(req, resp, oauth.access_token, facebookInfo);
					resp.sendRedirect("facebook-oauthed.jsp");
					return;
				}
			}
		}
		resp.sendRedirect("../error.jsp");
	}

	String getCode(HttpServletRequest req, HttpServletResponse resp, String code) {
		StringBuilder builder = new StringBuilder();
		builder.append("https://graph.facebook.com/oauth/access_token");
		builder.append("?client_id=");
		builder.append(FACEBOOK_APP_ID);
		builder.append("&redirect_uri=");
		builder.append(this.redirect_uri);
		builder.append("&client_secret=");
		builder.append(FACEBOOK_APP_ID_SECRET);
		builder.append("&code=");
		builder.append(req.getParameter("code"));
		builder.append("&state=");
		builder.append(req.getParameter("state"));
		return builder.toString();
	}

	final FacebookOAuth getFacebookOAuth(InputStream inputStream)
			throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(
				inputStream));
		String line = null;
		StringBuilder builder = new StringBuilder();
		while ((line = br.readLine()) != null) {
			builder.append(line);
		}
		line = builder.toString();
		log(line);
		this.session.setAttribute("facebook_ret", line);
		String[] params = line.split("&");
		FacebookOAuth oauth = new FacebookOAuth();
		for (String param : params) {
			String[] token = param.split("=");
			if ("access_token".equals(token[0])) {
				oauth.access_token = token[1];
			}
			if ("expires".equals(token[0])) {
				oauth.expires = Integer.valueOf(token[1]);
			}
		}
		return oauth;
	}

	void saveAccesToken(HttpServletRequest req, HttpServletResponse resp,
			String access_token, FacebookUserInfo me)
			throws UnknownHostException, MongoException {
		User user = Helper.getUserById(req.getSession());
		Long mills = System.currentTimeMillis();
		Long facebookID = mills;

		try (ACMongo mongo = new ACMongo()) {
			Query<FacebookAccount> query0 = mongo
					.createQuery(FacebookAccount.class);

			FacebookAccount facebook = query0.filter("access_token",
					access_token).get();
			if (Helper.valid(facebook)) {
				log("has facebook info in mongo.");
				facebook.setUserId(user.getUserId());
				mongo.save(facebook);
			} else {
				log("no facebook info in mongo.");
				facebook = new FacebookAccount(facebookID, user.getUserId(),
						"", "", "", mills, mills, access_token, me.getId());
				mongo.save(facebook);
			}

			req.getSession().setAttribute(KEY_FACEBOOK_INFO, facebook);
		}
	}
}
