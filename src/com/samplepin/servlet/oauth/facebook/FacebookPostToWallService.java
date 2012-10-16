package com.samplepin.servlet.oauth.facebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.query.Query;
import com.samplepin.FacebookAccount;
import com.samplepin.common.ACMongo;

/*
 * Twitterでつぶやくサンプル。
 */
public class FacebookPostToWallService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6859695248316214764L;

	HttpURLConnection connectToFacebook(FacebookAccount facebook)
			throws IOException {
		URL url = new URL("https://graph.facebook.com/"
				+ facebook.getUserName() + "/feed");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		return connection;
	}

	private void display(HttpURLConnection connection)
			throws UnsupportedEncodingException, IOException {
		System.out.println(connection.getResponseCode() + " "
				+ connection.getResponseMessage());
		BufferedReader br = new BufferedReader(
				new InputStreamReader(
						connection.getResponseCode() == HttpURLConnection.HTTP_OK ? connection
								.getInputStream() : connection.getErrorStream(),
						"UTF-8"));
		String line = null;
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}
	}

	public void post(HttpServletRequest req, HttpServletResponse resp,
			String message) throws ServletException, IOException {
		HttpSession session = req.getSession();
		try (ACMongo mongo = new ACMongo()) {
			Query<FacebookAccount> query = mongo
					.createQuery(FacebookAccount.class);
			FacebookAccount facebook = query.get();

			if (facebook != null) {
				message = URLEncoder.encode(message.toString(), "UTF-8")
						.replace("+", "%20");
				String access_token = facebook.getAccessToken();
				HttpURLConnection connection = connectToFacebook(facebook);
				post(connection, access_token, message);
				display(connection);
			}
		} catch (Exception e) {
			session.setAttribute("ERROR", e);
		}
	}

	private void post(HttpURLConnection connection, String access_token,
			String message) throws IOException {
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
	}
}