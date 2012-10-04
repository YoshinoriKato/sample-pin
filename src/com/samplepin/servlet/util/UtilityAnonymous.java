package com.samplepin.servlet.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.samplepin.User;
import com.samplepin.common.ACMongo;

@WebServlet(urlPatterns = { "/anonymous.do" })
public class UtilityAnonymous extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5036134172073312539L;

	public static String		USER_ID				= "Anonymous";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException, ServletException {
		try (ACMongo mongo = new ACMongo()) {
			Query<User> query = mongo.createQuery(User.class).filter(
					"userId = ", USER_ID);
			mongo.createDatastore().delete(query);
			if (query.countAll() == 0) {
				User user = new User(USER_ID, "xxx@xxx.xxx", USER_ID, -1);
				user.setBirthDay("1605-10-26");
				// Date date = DateFormat.getDateInstance().parse("1605/10/26");
				user.setCreateDate(-11492528400000L);
				user.setLastUpdate(-11492528400000L);
				user.setImagePath("img/anonymous.png");
				user.setComment(USER_ID);
				mongo.save(user);
			}

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", e);
			throw new ServletException(e);
		}
		response.sendRedirect("home.jsp");
	}
}
