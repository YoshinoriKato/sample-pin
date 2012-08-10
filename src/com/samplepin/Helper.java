package com.samplepin;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;

public class Helper {

	static String ID_LETTER = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	static String PASSWORD_LETTER = ID_LETTER + "0123456789_";

	static int DEFAULT_LEN = 12;

	static int FIRST_CHAR = 1;

	static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	static Comparator<Comment> LATEST_COMMENT = new Comparator<Comment>() {

		@Override
		public int compare(Comment o1, Comment o2) {
			return o2.getCreateDate().compareTo(o1.getCreateDate());
		}
	};

	public static String formatToDateTimeString(Long mills) {
		Date date = new Date(mills);
		return sdf.format(date);
	}

	public static String generatedUserId() {
		Random r = new Random(System.nanoTime());
		StringBuilder builder = new StringBuilder();

		int len = ID_LETTER.length();
		char[] array = ID_LETTER.toCharArray();
		builder.append(array[r.nextInt(len)]);

		len = PASSWORD_LETTER.length();
		array = PASSWORD_LETTER.toCharArray();

		int range = r.nextInt(24);

		for (int i = 0; i < ((DEFAULT_LEN - FIRST_CHAR) + range); i++) {
			builder.append(array[r.nextInt(len)]);
		}
		return builder.toString();
	}

	public static Card getCardInfoByID(String cardId, String userId) {
		try (ACMongo mongo = new ACMongo()) {

			Datastore datastore = mongo.createDatastore();
			Query<Card> query = datastore.createQuery(Card.class).filter(
					"cardId = ", cardId);
			Card card = query.get();
			if (card != null) {
				if (userId != null) {
					Query<View> query2 = datastore.createQuery(View.class)
							.filter("userId =", userId)
							.filter("cardId = ", cardId);
					if (query2.countAll() == 0) {
						card.setView(card.getView() + 1);
						datastore.save(card);

						View view = new View(System.currentTimeMillis(),
								cardId, userId);
						datastore.save(view);
					}
				} else {
					card.setView(card.getView() + 1);
					datastore.save(card);
				}
			}
			return card;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new Card();
	}

	public static List<Comment> getCommentsInfoByID(String cardId, String userId) {
		try (ACMongo mongo = new ACMongo()) {

			Datastore datastore = mongo.createDatastore();
			Query<Comment> query = datastore.createQuery(Comment.class).filter(
					"cardId = ", cardId);
			List<Comment> comments = query.asList();
			Collections.sort(comments, LATEST_COMMENT);
			return comments;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new ArrayList<Comment>();
	}

	public static void main(String[] args) {
		String LS = System.getProperty("line.separator");
		StringBuilder builder = new StringBuilder();
		builder.append("サインアップ完了しました。").append(LS);
		builder.append(LS);
		builder.append("仮ユーザーID: ").append("hoge").append(LS);
		builder.append("仮パスワード: ").append("foo").append(LS);
		builder.append(LS);
		builder.append("URL: ").append(LS);
		builder.append("http://localhost:8080/sample-pin/").append(LS);

		sendMail("katoy.acces.co.jp@gmail.com", builder.toString());
		System.out.println("OK?");
	}

	public static void sendMail(String mail, String text) {
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");

		Session session = Session.getDefaultInstance(properties, null);
		MimeMessage message = new MimeMessage(session);

		try {
			InternetAddress from = new InternetAddress(
					"katoy.acces.co.jp@gmail.com", "Web Master");

			message.setRecipients(Message.RecipientType.TO, mail);
			message.setFrom(from);
			message.setSubject("サインアップ完了", "ISO-2022-JP");
			message.setText(text, "ISO-2022-JP");

			Transport transport = session.getTransport("smtp");
			transport.connect("katoy.acces.co.jp@gmail.com", "kato2003");
			transport.sendMessage(message, message.getAllRecipients());
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
	}
}
