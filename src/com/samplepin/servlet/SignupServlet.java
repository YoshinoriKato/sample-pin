package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;
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

@WebServlet(urlPatterns = { "/signup.do" })
public class SignupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7038314414171973611L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession session = req.getSession();
		String mail = req.getParameter("mail");
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
				Helper.sendMail(mail, getMessage(userId, password));
				new LoginServlet().login(req, userId);
				resp.sendRedirect("index.jsp");
				return;
			}

			session.setAttribute("warning", "already exists.");

		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
			session.setAttribute("error", e);
		}
		resp.sendRedirect("signup.jsp");
	}

	final String getMessage(String userId, String password) {
		String LS = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append("サインアップ完了しました。").append(LS);
		builder.append(LS);
		builder.append("ユーザーID: ").append(userId).append(LS);
		builder.append("仮パスワード: ").append(password).append(LS);
		builder.append(LS);
		builder.append("URL: ")
				.append("http://www45022u.sakura.ne.jp/sample-pin/").append(LS);
		return builder.toString();
	}

}
