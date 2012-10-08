package com.samplepin.servlet.oauth.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.query.Query;
import com.samplepin.FacebookAccount;
import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

/*
 * Twitterでつぶやくサンプル。
 */
public class FacebookPostToWallServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6859695248316214764L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String message = req.getParameter("message");
		doPost(req, resp, message);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp,
			String message) throws ServletException, IOException {
		HttpSession session = req.getSession();
		User user = Helper.getUserById(req.getSession());
		try (ACMongo mongo = new ACMongo()) {
			Query<FacebookAccount> q = mongo.createQuery(FacebookAccount.class)
					.filter("userId", user.getUserId());
			FacebookAccount facebook = q.get();

			if (facebook != null) {
				// プロキシサーバの設定
				// System.setProperty("http.proxyHost", "proxy.example.net");
				// System.setProperty("http.proxyPort", "3128");

				String access_token = facebook.getAccesToken();

				// メッセージの作成
				message = URLEncoder.encode(message.toString(), "UTF-8")
						.replace("+", "%20");

				// HTTPリクエスト
				URL url = new URL("https://graph.facebook.com/"
						+ facebook.getPageID() + "/feed");
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("POST");

				// POSTによる送信
				connection.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(
						connection.getOutputStream());
				StringBuilder builder = new StringBuilder();
				builder.append("access_token=");
				builder.append(access_token);
				builder.append("&message=");
				builder.append(message);
				writer.write(builder.toString());
				writer.flush();
				writer.close();

				// 結果の表示
				logging(connection);
			}
		} catch (Exception e) {
			session.setAttribute("ERROR", e);
		}
	}

	void logging(HttpURLConnection connection) throws IOException {

		log(connection.getResponseCode() + " "
				+ connection.getResponseMessage());
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						connection.getResponseCode() == HttpURLConnection.HTTP_OK ? connection
								.getInputStream() : connection.getErrorStream(),
						"UTF-8"));
		String line = null;
		while ((line = br.readLine()) != null) {
			log(line);
		}
	}
}