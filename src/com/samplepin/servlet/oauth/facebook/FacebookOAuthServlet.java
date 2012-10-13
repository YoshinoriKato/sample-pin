package com.samplepin.servlet.oauth.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.samplepin.FacebookAccount;
import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;
import com.samplepin.servlet.LoginServlet;

@WebServlet(urlPatterns = { "/oauth-facebook.jsp" })
public class FacebookOAuthServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID							= -7700928228357051804L;

	public static String		APP_ID										= "279279575524882";

	public static String		APP_SECRET									= "83803e2fd2083789030a380399eb5603";

	public static String		COMMA_SEPARATED_LIST_OF_PERMISSION_NAMES	= "";

	public static String		SOME_ARBITRARY_BUT_UNIQUE_STRING			= getRandomString();

	private static String		REDIRECT_URI								= Helper.DOMAIN
																					+ "oauth-facebook.jsp";

	static String				OAUTH_PATH									= "https://www.facebook.com/dialog/oauth?client_id="
																					+ APP_ID
																					+ "&redirect_uri="
																					+ REDIRECT_URI
																					+ "&scope="
																					+ COMMA_SEPARATED_LIST_OF_PERMISSION_NAMES
																					+ "&state="
																					+ SOME_ARBITRARY_BUT_UNIQUE_STRING;

	public static String getOAuthURL() {
		return OAUTH_PATH;
	}

	public static String getOAuthUrlForFacebook() {
		StringBuilder builder = new StringBuilder();
		builder.append("https://www.facebook.com/dialog/oauth");
		builder.append("?client_id=" + APP_ID);
		builder.append("&redirect_uri=" + REDIRECT_URI);
		builder.append("&state=" + SOME_ARBITRARY_BUT_UNIQUE_STRING);
		builder.append("&scope=offline_access,publish_stream,user_status,read_stream,status_update,manage_pages");

		return builder.toString();
	}

	static final String getRandomString() {
		return UUID.randomUUID().toString();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String code = req.getParameter("code");
		String state = req.getParameter("state");

		if ((code != null) && SOME_ARBITRARY_BUT_UNIQUE_STRING.equals(state)) {
			FacebookOAuth oauth = getFacebookOAuth(req, resp, code);

			if ((oauth != null) && exists(oauth.access_token)) {
				FacebookInfo facebookInfo = getFacebookInfo(req, oauth);

				if (exists(facebookInfo)) {
					saveAccesToken(req, resp, oauth.access_token, facebookInfo);
					// RequestDispatcher dispatcher = req
					// .getRequestDispatcher("debug.jsp");
					// dispatcher.forward(req, resp);
					resp.sendRedirect("conversion.jsp");
					return;
				}
			}
		}

		Object loopCheck = session.getAttribute("loopCheck");
		if (loopCheck == null) {
			session.setAttribute("loopCheck", new Object());
			resp.sendRedirect(getOAuthURL());
		} else {
			session.removeAttribute("loopCheck");
			resp.sendRedirect("error.jsp");
		}
	}

	boolean exists(Object value) {
		return value != null;
	}

	boolean exists(String value) {
		return (value != null) && !value.isEmpty();
	}

	String getCodeURL(HttpServletRequest req, HttpServletResponse resp,
			String code) {
		StringBuilder builder = new StringBuilder();
		builder.append("https://graph.facebook.com/oauth/access_token");
		builder.append("?client_id=");
		builder.append(APP_ID);
		builder.append("&redirect_uri=");
		builder.append(REDIRECT_URI);
		builder.append("&client_secret=");
		builder.append(APP_SECRET);
		builder.append("&code=");
		builder.append(req.getParameter("code"));
		builder.append("&state=");
		builder.append(req.getParameter("state"));
		return builder.toString();
	}

	FacebookInfo getFacebookInfo(HttpServletRequest req, FacebookOAuth oauth)
			throws IOException {
		URL url = new URL("https://graph.facebook.com/me" + "?access_token="
				+ oauth.access_token + "&fields=name,picture,id");
		URLConnection conn = url.openConnection();
		FacebookInfo facebookInfo = new Gson().fromJson(new InputStreamReader(
				conn.getInputStream()), FacebookInfo.class);
		req.setAttribute("access_token", oauth.access_token);
		return facebookInfo;
	}

	FacebookOAuth getFacebookOAuth(HttpServletRequest req,
			HttpServletResponse resp, String code) throws IOException {
		URL url = new URL(getCodeURL(req, resp, code));
		URLConnection conn = url.openConnection();
		FacebookOAuth oauth = getFacebookOAuth(conn.getInputStream());
		req.setAttribute("facebook_url", url.toString());
		return oauth;
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
			String access_token, FacebookInfo me) throws IOException {
		req.setAttribute("id", me.id);
		req.setAttribute("name", me.name);
		req.setAttribute("picture", me.picture);

		try (ACMongo mongo = new ACMongo()) {
			Query<FacebookAccount> query = mongo.createQuery(
					FacebookAccount.class).filter("facebookId = ", me.id);
			FacebookAccount account = null;

			if (query.countAll() == 0) {
				// sign up
				String userId = Helper.getUserId(req);
				userId = Helper.valid(userId) ? userId : Helper
						.generatedIdString("FB_");
				account = new FacebookAccount(me.id, me.name, access_token,
						userId);
				mongo.save(account);
				User user = new User(userId, "Sign up by Facebook.", me.name,
						-1);
				mongo.save(user);
			} else {
				// login
				account = query.get();
			}
			LoginServlet.login(req, account.getUserId());
		}
	}
}
