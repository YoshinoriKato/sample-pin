package com.samplepin;

import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

	static SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");

	static SimpleDateFormat SDF_DATE_TIME = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	public static String escapeHTML(String input) {
		input = substitute(input, "&", "&amp;");
		input = substitute(input, "<", "&lt;");
		input = substitute(input, ">", "&gt;");
		input = substitute(input, "\"", "&quot;");
		return input;
	}

	public static String escapeSQL(String input) {
		input = substitute(input, "'", "''");
		input = substitute(input, "\\", "\\\\");
		return input;
	}

	public static String formatToDateString(Long mills) {
		Date date = new Date(mills);
		return SDF_DATE.format(date);
	}

	public static String formatToDateTimeString(Long mills) {
		Date date = new Date(mills);
		return SDF_DATE_TIME.format(date);
	}

	public static String generateColorString() {
		Random dice = new Random(System.nanoTime());
		String color = "#" + Integer.toHexString(dice.nextInt(255))
				+ Integer.toHexString(dice.nextInt(255))
				+ Integer.toHexString(dice.nextInt(255));
		return color;
	}

	public static String generatedUserId() {
		return generatedUserId("");
	}

	public static String generatedUserId(String prefix) {
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
		return prefix + builder.toString();
	}

	public static String getBackgroundColor(User user) {
		if (user != null) {
			String color = user.getBackgroundColor();
			color = (color != null) && !color.isEmpty() ? "background-color: "
					+ color + "; " : "";
			return color;
		}
		return "";
	}

	public static Card getCardInfoByID(String cardId) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Card> query = datastore.createQuery(Card.class).filter(
					"cardId = ", cardId);
			Card card = query.get();
			return card;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new Card();
	}

	public static List<Comment> getCommentsInfoByID(String cardId) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Comment> query = datastore.createQuery(Comment.class)
					.filter("cardId = ", cardId).order("-createDate")
					.limit(100);
			List<Comment> comments = query.asList();
			return comments;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new ArrayList<Comment>();
	}

	public static List<Country> getCountries() {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Country> query = datastore.createQuery(Country.class);
			return query.asList();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new ArrayList<Country>();
	}

	public static String getFontColor(User user) {
		if (user != null) {
			String color = user.getFontColor();
			color = (color != null) && !color.isEmpty() ? "color: " + color
					+ "; " : "";
			return color;
		}
		return "";
	}

	public static OneTime getOneTimeByOneTimePassword(String oneTimePassword) {
		if (oneTimePassword != null) {
			try (ACMongo mongo = new ACMongo()) {
				Datastore datastore = mongo.createDatastore();
				Query<OneTime> query1 = datastore.createQuery(OneTime.class)
						.filter("oneTime = ", oneTimePassword);
				return query1.get();
			} catch (UnknownHostException | MongoException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static User getUserById(String userId) {
		try (ACMongo mongo = new ACMongo()) {

			Datastore datastore = mongo.createDatastore();
			Query<User> query = datastore.createQuery(User.class).filter(
					"userId = ", userId);
			return query.get();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<View> getViewsInfoByID(String userId) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<View> query = datastore.createQuery(View.class)
					.filter("userId = ", userId).limit(100)
					.order("-visitedDate");
			List<View> comments = query.asList();
			return comments;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new ArrayList<View>();
	}

	public static String getWallPaper(User user) {
		if (user != null) {
			String wallpaper = user.getBackgroundImage();
			wallpaper = (wallpaper != null) && !wallpaper.isEmpty()
					&& user.getUseBackgroundImage() ? "background-image: url('"
					+ wallpaper + "'); " : "";
			return wallpaper;
		}
		return "";
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

		sendMail("katoy.acces.co.jp@gmail.com", builder.toString(),
				"Sign up was successful.");
		System.out.println("OK?");
	}

	public static void sendMail(String mail, String text, String title) {
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
			message.setSubject(title, "ISO-2022-JP");
			message.setText(text, "ISO-2022-JP");

			Transport transport = session.getTransport("smtp");
			transport.connect("katoy.acces.co.jp@gmail.com", "kato2003");
			transport.sendMessage(message, message.getAllRecipients());
		} catch (UnsupportedEncodingException | MessagingException e) {
			e.printStackTrace();
		}
	}

	public static void setFootprint(Card card, String userId) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<View> query2 = datastore.createQuery(View.class)
					.filter("userId = ", userId)
					.filter("cardId = ", card.getCardId());
			View view = query2.get();

			if (view == null) {
				if (!userId.equals(card.getUserId())) {
					card.setView(card.getView() + 1);
					datastore.save(card);
				}

				view = new View(0L, card.getCardId(), userId);
			}
			view.setTimes(view.getTimes() + 1);
			view.setVisitedDate(System.currentTimeMillis());
			datastore.save(view);
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
	}

	static public String substitute(String input, String pattern,
			String replacement) {
		int index = input.indexOf(pattern);
		if (index == -1) {
			return input;
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(input.substring(0, index) + replacement);
		if ((index + pattern.length()) < input.length()) {
			String rest = input.substring(index + pattern.length(),
					input.length());
			buffer.append(substitute(rest, pattern, replacement));
		}
		return buffer.toString();
	}
}
