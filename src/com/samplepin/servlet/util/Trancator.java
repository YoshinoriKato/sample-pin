package com.samplepin.servlet.util;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.Datastore;
import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/clear.do" })
public class Trancator extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1523120751166161328L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			mongo.dropDatabase("sample-pin");
			Datastore datastore = mongo.createDatastore();
			User user = new User();
			user.setUserName("Web Master");
			user.setUserId(Helper.generatedIdString());
			user.setPassword("".hashCode());
			user.setMail("katoy@acces.co.jp");
			datastore.save(user);
			log("bye.");
		}
		resp.sendRedirect("home.jsp");
	}

}
