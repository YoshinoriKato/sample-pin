package com.samplepin.servlet.oauth.facebook;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/oauth-facebook.jsp" })
public class FacebookLoginOAuthServlet extends FacebookOAuthServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7700928228357051804L;

	HttpSession					session;

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
				FacebookUserInfo me = Helper.readJson(conn2.getInputStream(),
						FacebookUserInfo.class);

				if (Helper.valid(me)) {
					// take 2
					saveAccesToken(req, resp, oauth.access_token, me);
					resp.sendRedirect("facebook-oauthed.jsp");
					return;
				}
			}
		}
		resp.sendRedirect("../error.jsp");
	}
}
