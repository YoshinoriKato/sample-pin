package com.samplepin;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@WebServlet(urlPatterns = { "/login.do" })
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5426777241563315344L;

	final boolean acceptPassword(String userId, Integer password)
			throws UnknownHostException, MongoException {
		Mongo mongo = new Mongo("127.0.0.1");

		try {
			Morphia morphia = new Morphia();
			Datastore datastore = morphia.createDatastore(mongo, "sample-pin");
			Query<User> query = datastore.createQuery(User.class).filter(
					"userId = ", userId);
			User user = query.get();
			return password.equals(user.getPassword());
		} finally {
			mongo.close();
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setCharacterEncoding("UTF-8");
		try {
			String userId = req.getParameter("userId");
			Integer password = req.getParameter("password").hashCode();
			if (acceptPassword(userId, password)) {
				HttpSession sessionOld = req.getSession();
				sessionOld.invalidate();
				HttpSession sessionNew = req.getSession(true);
				log(sessionOld.getId() + " -> " + sessionNew.getId());
				String redirectUrl = req.getParameter("redirectUrl");
				log("forward: " + redirectUrl);
				sessionNew.setAttribute("userId", userId);
				resp.sendRedirect(redirectUrl);
			}
		} catch (Exception e) {
			log(e.getMessage());
			req.setAttribute("error", e);
			resp.sendRedirect("login.jsp");
		}
	}

}
