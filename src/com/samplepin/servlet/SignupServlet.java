package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Helper;
import com.samplepin.User;

public class SignupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7038314414171973611L;

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

				String LS = System.getProperty("line.separator");
				StringBuilder builder = new StringBuilder();
				builder.append("サインアップ完了しました。").append(LS);
				builder.append(LS);
				builder.append("仮ユーザーID: ").append(userId).append(LS);
				builder.append("仮パスワード: ").append(password).append(LS);
				builder.append(LS);
				builder.append("URL: ").append(LS);
				builder.append("http://localhost:8080/sample-pin/").append(LS);

				Helper.sendMail(mail, builder.toString());

				resp.sendRedirect("index.jsp");

			}
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("error.jsp");
	}

}
