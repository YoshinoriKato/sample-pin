package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Helper;
import com.samplepin.User;

@WebServlet(urlPatterns = { "/login.do" })
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5426777241563315344L;

	public static String		KEY_FIRST			= "Dioscuri";

	public static String		KEY_SECOND			= "Pollux";

	public static void makeCookie(HttpServletResponse resp, String userId) {
		Cookie cookie0 = new Cookie(KEY_FIRST, Helper.generatedIdString("ID_"));
		Cookie cookie1 = new Cookie(KEY_SECOND, userId);
		cookie0.setMaxAge(Integer.MAX_VALUE);
		cookie1.setMaxAge(Integer.MAX_VALUE);
		resp.addCookie(cookie0);
		resp.addCookie(cookie1);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		resp.setCharacterEncoding("UTF-8");
		try {
			String mail = req.getParameter("mail");
			String password = req.getParameter("password");
			Integer hashCode = password.hashCode();
			User user = getUserByMailAndPassword(mail, hashCode);
			if (user != null) {
				login(req, user.getUserId());
				makeCookie(resp, user.getUserId());
				String redirectUrl = req.getParameter("redirectUrl");
				log("forward: " + redirectUrl);
				resp.sendRedirect(redirectUrl);
				return;
			}
		} catch (Exception e) {
			log(e.getMessage());
			req.setAttribute("error", e);
		}
		req.setAttribute("message",
				"Please, write your mail address and password.");
		RequestDispatcher dispathcer = req.getRequestDispatcher("login.jsp");
		dispathcer.forward(req, resp);
	}

	final User getUserByMailAndPassword(String mail, int password)
			throws UnknownHostException, MongoException {

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<User> query = datastore.createQuery(User.class).filter(
					"mail = ", mail);
			User user = query.get();
			if (user != null) {
				if (password == user.getPassword()) {
					return user;
				} else {
					user.setLoginFaileds(user.getLoginFaileds() + 1);
					user.setLastUpdate(System.currentTimeMillis());
					datastore.save(user);
				}
			}
			return null;
		}
	}

	final void login(HttpServletRequest req, String userId)
			throws UnknownHostException, MongoException {
		HttpSession sessionOld = req.getSession();
		sessionOld.invalidate();
		HttpSession sessionNew = req.getSession(true);
		sessionNew.setAttribute("userId", userId);
		sessionNew.setMaxInactiveInterval(-1);// 無制限
	}

}
