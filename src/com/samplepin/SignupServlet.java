package com.samplepin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;

public class SignupServlet extends HttpServlet {

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String mail = req.getParameter("req");
		String userId = Helper.generatedUserId();
		String password = Helper.generatedUserId();

		User user = new User();
		user.setUserId(userId);
		user.setPassword(password.hashCode());
		user.setCreateDate(System.currentTimeMillis());
		user.setMail(mail);

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<User> query = datastore.createQuery(User.class).filter(
					"mail = ", mail);
			if (query.countAll() == 0) {
				datastore.save(user);
				new LoginServlet().login(req, userId);
				resp.sendRedirect("index.jsp");

			}
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("error.jsp");
	}

}
