package com.samplepin.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.Country;
import com.samplepin.Folder;
import com.samplepin.Header;
import com.samplepin.OneTime;
import com.samplepin.Tag;
import com.samplepin.TwitterAccount;
import com.samplepin.User;
import com.samplepin.View;
import com.samplepin.WebPage;
import com.samplepin.nl.NaturalLanguageParser;
import com.samplepin.nl.WebParser;
import com.samplepin.servlet.ajax.SearchAjax;

public class Helper {

	public static final String TIMESTAMP = String.valueOf(System
			.currentTimeMillis());

	static SimpleDateFormat SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");

	static SimpleDateFormat SDF_DATE_TIME = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	static SimpleDateFormat SDF_DATE_HOUR = new SimpleDateFormat(
			"yyyy-MM-dd 'at around' HH");

	public static final Pattern convURLLinkPtn0 = Pattern.compile(
			"(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+",
			Pattern.CASE_INSENSITIVE);

	public static final Pattern convURLLinkPtn1 = Pattern.compile(
			"\\[([^\\]]*)\\]", Pattern.CASE_INSENSITIVE);

	public static final Pattern convURLLinkPtn2 = Pattern.compile(
			"[^ 　\t\f\r\n]+", Pattern.CASE_INSENSITIVE);

	static final Long MILLS_SECOND = 1000L;

	static final Long MILLS_MINUTE = 60L * MILLS_SECOND;

	static final Long MILLS_HOUR = 60L * MILLS_MINUTE;

	static final Long MILLS_DAY = 24L * MILLS_HOUR;

	public static final String LS = System.getProperty("line.separator");

	public static final String DOMAIN = "http://doya.info/";

	public static final String NAME = "DOYA.info Beta";

	static final String SEPARATOR = "[ |\\t|\\f|\\r\\n|\\r|\\n]";

	public static boolean canTweet(HttpSession session) throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			String userId = getUserId(session);
			Query<TwitterAccount> query = mongo.createQuery(
					TwitterAccount.class).filter("userId = ", userId);
			TwitterAccount account = query.get();
			return account != null;
		}
	}

	public static String convertThumbnailPath(String imagePath) {
		return imagePath.replace("icon-keeper/", "icon-keeper/t/t_");
	}

	public static String convKeywordLink(String str) {
		Matcher matcher = convURLLinkPtn2.matcher(str);
		return matcher
				.replaceAll("<a href=home.jsp?sorted=search&words=\"$0\">$0</a> ");
	}

	public static String convURLLink(String str) {
		Matcher matcher0 = convURLLinkPtn0.matcher(str);
		String temp = matcher0
				.replaceAll("<a href=\"$0\" target=\"_blank\">$0</a>");

		Matcher matcher1 = convURLLinkPtn1.matcher(temp);
		return matcher1
				.replaceAll("<a href=home.jsp?sorted=search&words=\"$0\">$0</a>");
	}

	public static <T> Long countByID(Class<T> clazz, String ex, String userId) {
		try (ACMongo mongo = new ACMongo()) {
			Query<T> query = mongo.createQuery(clazz).filter(ex, userId)
					.filter("isDeleted", false);
			return query.countAll();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return -1L;
	}

	public static Long countCardByUserId(String userId) {
		return countByID(Card.class, "userId = ", userId);
	}

	public static Long countCommentByUserId(String userId) {
		return countByID(Comment.class, "userId = ", userId);
	}

	public static String decryptToString(String accessToken) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String encrypt(String value) throws NoSuchAlgorithmException {
		return IdGenerator.randowMD5(value);
	}

	public static String escapeHTML(String input) {
		// tag
		input = substitute(input, "&", "&amp;");
		input = substitute(input, "<", "&lt;");
		input = substitute(input, ">", "&gt;");
		input = substitute(input, "\"", "&quot;");
		// line separator
		input = substitute(input, "\r\n", "<br />");
		input = substitute(input, "\n\r", "<br />");
		input = substitute(input, "\r", "<br />");
		input = substitute(input, "\n", "<br />");
		return input;
	}

	public static String escapeSQL(String input) {
		input = substitute(input, "'", "''");
		input = substitute(input, "\\", "\\\\");
		return input;
	}

	public static String formatToAboutTimeString(long mills) {
		Long current = System.currentTimeMillis();
		Long gap = current - mills;
		if (gap <= MILLS_SECOND) {
			return "1秒前";
		} else if (gap <= (MILLS_MINUTE)) {
			long seconds = gap / MILLS_SECOND;
			return seconds + "秒前";
		} else if (gap <= (MILLS_HOUR)) {
			long minutes = gap / (MILLS_MINUTE);
			return minutes + "分前";
		} else if (gap <= (2 * MILLS_DAY)) {
			long hours = gap / (MILLS_HOUR);
			return hours + "時間前";
		}
		Date date = new Date(mills);
		return SDF_DATE_HOUR.format(date);
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

	public static String generatedIdString() {
		return IdGenerator.randomAlphanumeric(32);
	}

	public static String generatedIdString(String prefix) {
		return prefix + IdGenerator.randomAlphanumeric(32);
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

	public static Card getCardByID(String cardId) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Card> query = datastore.createQuery(Card.class)
					.filter("cardId = ", cardId).filter("isDeleted", false);
			Card card = query.get();
			if (card != null) {
				User user = Helper.getUserById(card.getUserId());
				if (user != null) {
					setUserInfoToComment(card, user);
				}
			}
			return card;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new Card();
	}

	public static Card getCardByImagePath(String imagePath) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Card> query = datastore.createQuery(Card.class).filter(
					"imagePath = ", imagePath);
			Card card = query.get();
			if (card != null) {
				User user = Helper.getUserById(card.getUserId());
				if (user != null) {
					setUserInfoToComment(card, user);
				}
			}
			return card;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new Card();
	}

	public static List<Card> getCardsByID(String cardId) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Card> query = datastore.createQuery(Card.class)
					.filter("parentId = ", cardId).filter("isDeleted", false)
					.order("-updateDate");
			return query.asList();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new ArrayList<Card>();
	}

	public static Comment getCommentByID(String cardId, String userId,
			Long createDate) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Comment> query = datastore.createQuery(Comment.class)
					.filter("cardId = ", cardId).filter("userId = ", userId)
					.filter("createDate = ", createDate)
					.filter("isDeleted", false);
			Comment comment = query.get();
			if (comment != null) {
				User user = Helper.getUserById(comment.getUserId());
				if (user != null) {
					setUserInfoToComment(comment, user);
				}
			}
			return comment;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new Comment();
	}

	public static List<Comment> getCommentsInfoByID(String cardId) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Comment> query = datastore.createQuery(Comment.class)
					.filter("cardId = ", cardId).order("-createDate")
					.limit(1000);
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
			Query<Country> query = datastore.createQuery(Country.class).order(
					"code");
			return query.asList();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new ArrayList<Country>();
	}

	public static String getCountryEnName(int code) {
		try (ACMongo mongo = new ACMongo()) {
			Query<Country> query = mongo.createQuery(Country.class).filter(
					"code = ", code);
			Country country = query.get();
			if (country != null) {
				return country.getEnName();
			}
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return "Unkown";
	}

	public static Folder getFolderById(String folderId) {
		try (ACMongo mongo = new ACMongo()) {
			Query<Folder> query = mongo.createQuery(Folder.class)
					.filter("folderId = ", folderId)
					.filter("isDeleted = ", false);
			return query.get();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new Folder();
	}

	public static List<Folder> getFolders() throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			Query<Folder> query = mongo.createQuery(Folder.class)
					.filter("accessLevel = ", 0).filter("isDeleted = ", false)
					.order("-createDate");
			return query.asList();
		}
	}

	public static List<Folder> getFolders(String userId) throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			Query<Folder> query = mongo.createQuery(Folder.class)
					.filter("isDeleted = ", false).order("-createDate");
			query.or(query.criteria("userId").equal(userId),
					query.criteria("accessLevel").equal(0));
			return query.asList();
		}
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

	public static List<Header> getHeaders() throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			return mongo.createQuery(Header.class).order("-createDate")
					.asList();
		}
	}

	public static String getImageFileName(String userId) {
		int len = userId.length();
		String prefix = userId.substring(len - 8, len);
		String fileName = "I_" + prefix + System.nanoTime();
		return fileName;
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

	public static List<Tag> getTags() throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			Query<Tag> query = mongo.createQuery(Tag.class)
					.filter("size > ", 12).order("-size, -createDate");
			return query.asList();
		}
	}

	public static User getUserById(HttpSession session) {
		return getUserById(getUserId(session));
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

	public static String getUserId(HttpServletRequest request) {
		return getUserId(request.getSession());
	}

	public static String getUserId(HttpSession session) {
		return (String) session.getAttribute("userId");
	}

	public static List<View> getViewsInfoByID(String userId) {
		return getViewsInfoByID(userId, 100, 0);
	}

	public static List<View> getViewsInfoByID(String userId, int offset,
			int limit) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<View> query = datastore.createQuery(View.class)
					.filter("userId = ", userId).filter("isDeleted = ", false);
			if (offset != 0) {
				query.offset(offset);
			}
			if (limit != 0) {
				query.limit(limit);
			}
			query.order("-visitedDate");
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

	public static WebPage getWebPageByURL(String url) throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<WebPage> query = datastore.createQuery(WebPage.class).filter(
					"url = ", url);
			WebPage page = query.get();
			if (page == null) {
				try {
					WebParser.parse(url, null);
				} catch (Exception e) {
					e.printStackTrace();
				}
				page = query.get();
			}
			return page;
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return null;
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
		builder.append(DOMAIN).append(LS);

		sendMail("katoy.acces.co.jp@gmail.com", builder.toString(),
				"Sign up was successful.");
		System.out.println("OK?");
	}

	public static List<Card> newCards(Long updateDate) {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Card> query = datastore.createQuery(Card.class)
					.filter("accessLevel = ", 0).filter("isDeleted", false)
					.filter("updateDate < ", updateDate).order("-updateDate")
					.limit(10);
			return query.asList();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		return new ArrayList<Card>();
	}

	public static List<Card> searchCards(HttpServletRequest req, String keywords)
			throws IOException {
		String dic = NaturalLanguageParser.getDictionaryPath(req);
		String userId = getUserId(req);
		if (keywords != null && !keywords.isEmpty())
			try (ACMongo mongo = new ACMongo()) {
				Set<String> searched = NaturalLanguageParser.cardIds(dic,
						keywords);
				if (valid(searched)) {
					return new SearchAjax().cards(mongo, "", "search", "0",
							"10", "", "", "", "card", userId, "", searched);
				}
			} catch (UnknownHostException | MongoException e) {
				e.printStackTrace();
			}
		return new ArrayList<Card>();
	}

	public static <T> T readJson(InputStream is, Class<T> clazz)
			throws IOException {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
			String line;
			StringBuilder builder = new StringBuilder();
			while ((line = br.readLine()) != null) {
				builder.append(line);
			}
			return new Gson().fromJson(builder.toString(), clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IOException(e);
		}
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
					"web.doya.info@gmail.com", "DOYA.info Web Master");

			message.setRecipients(Message.RecipientType.TO, mail);
			message.setFrom(from);
			message.setSubject(title, "ISO-2022-JP");
			message.setText(text, "ISO-2022-JP");

			Transport transport = session.getTransport("smtp");
			transport.connect("web.doya.info@gmail.com", "doyainfo2012");
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
					.filter("cardId = ", card.getCardId())
					.filter("isDeleted", false);
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

	public static void setUserAgent(URLConnection conn) {
		conn.setRequestProperty(
				"user-agent",
				"Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_4) AppleWebKit/537.4 (KHTML, like Gecko) Chrome/22.0.1229.79 Safari/537.4");
	}

	public static void setUserInfoToComment(Comment comment, User user) {
		// if (comment.getAnonymous()) {
		// comment.setUserName("Anonymous");
		// comment.setUserIcon("img/anonymous.png");
		// } else {
		comment.setUserName(user.getUserName());
		comment.setUserIcon(user.getImagePath());
		// }

		if (comment instanceof Card) {
			Card card = (Card) comment;
			if (Helper.valid(card.getParentId())
					&& !"self".equals(card.getParentId())) {
				Card parent = Helper.getCardByID(card.getParentId());
				if (Helper.valid(parent)) {
					card.setParentIcon(parent.getImagePath());
				}
			}
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

	public static final boolean valid(Object val) {
		return (val != null);
	}

	public static final boolean valid(String val) {
		return (val != null) && !val.isEmpty() && !"null".equals(val);
	}

}
