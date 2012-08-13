package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Helper;
import com.samplepin.OneTime;
import com.samplepin.User;

@WebServlet(urlPatterns = { "/signup.do" })
public class SignupServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7038314414171973611L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		// HttpSession session = req.getSession();
		String mail = req.getParameter("mail");

		if ((mail != null) && validateMail(mail)) {
			String userId = Helper.generatedUserId("ID_");
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

					String oneTime = geneOneTime(user, password);
					Helper.sendMail(mail,
							getMessage(userId, password, oneTime),
							"Sign up was successful.");

				} else {
					user = query.get();
					user.setPassword(password.hashCode());
					user.setLastUpdate(System.currentTimeMillis());
					user.setLoginFaileds(0);
					datastore.save(user);
					Helper.sendMail(mail, getMessage(userId, password),
							"Change password was successful.");
				}
				resp.sendRedirect("send-mail.jsp");
				return;

			} catch (UnknownHostException | MongoException e) {
				e.printStackTrace();
				req.setAttribute("error", e);
			}
		}
		req.setAttribute("message", "Please, write your mail address.");
		RequestDispatcher dispathcer = req.getRequestDispatcher("signup.jsp");
		dispathcer.forward(req, resp);
		// resp.sendRedirect("signup.jsp");
	}

	private String geneOneTime(User user, String password)
			throws UnknownHostException, MongoException {
		try (ACMongo mongo = new ACMongo()) {
			String oneTimePassword = Helper.generatedUserId("O");
			OneTime oneTime = new OneTime(user.getMail(), oneTimePassword,
					password);
			Datastore datastore = mongo.createDatastore();
			datastore.save(oneTime);
			return oneTimePassword;
		}
	}

	final String getMessage(String userId, String password) {
		String LS = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append("パスワードの再設定が完了しました。").append(LS);
		builder.append(LS);
		builder.append("仮パスワード: ").append(password).append(LS);
		builder.append(LS);
		builder.append("URL: ")
				.append("http://www45022u.sakura.ne.jp/sample-pin/login.jsp")
				.append(LS);
		return builder.toString();
	}

	final String getMessage(String userId, String password, String oneTime) {
		String LS = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append("サインアップ完了しました。").append(LS);
		builder.append(LS);
		builder.append("仮パスワード: ").append(password).append(LS);
		builder.append(LS);
		builder.append("URL: ")
				.append("http://www45022u.sakura.ne.jp/sample-pin/login.jsp?onetime="
						+ oneTime).append(LS);
		return builder.toString();
	}

	private boolean validateMail(String mail) {
		return mail
				.matches("[a-zA-Z][a-zA-Z0-9\\.\\-]+@[a-zA-Z0-9\\.\\-]+[a-zA-Z]");
	}

}
