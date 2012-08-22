package com.samplepin.servlet.oauth;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Helper;
import com.samplepin.TwitterAccount;
import com.samplepin.User;

public class TwitterService {

	public static final String	consumerKey				= "joSnhvS8KCTy5ODs91vDzA";

	public static final String	consumerSecret			= "eRPqHoM3axEiFpJTmtIZTXwRb3KGxAB1AN0qXOPyOE";

	private static final String	accessToken				= "549106982-88C08uNpiRHyLTmmBCJ0Ei80WJPctI9Lrn2Zpn80";

	private static final String	oauthAccessTokenSecret_	= "BKcGv4rLSX7GKf9nhVq1440eaWSeYDOr1kb1j0921I";

	public static final String	callbackUrl				= "http://219.94.246.60/sample-pin/oauth-twitter.jsp";

	private static AccessToken loadAccessToken(String userId)
			throws UnknownHostException, MongoException {
		try (ACMongo mongo = new ACMongo()) {
			TwitterAccount twitterAccount = mongo
					.createQuery(TwitterAccount.class).filter("userId", userId)
					.get();
			if (twitterAccount == null) {
				return null;
			}
			return new AccessToken(twitterAccount.getAccessToken(),
					twitterAccount.getTokenSecret());
		}
	}

	public static void main(String[] args) throws Exception {
		// このファクトリインスタンスは再利用可能でスレッドセーフです

		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		RequestToken requestToken = twitter.getOAuthRequestToken();
		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == accessToken) {
			System.out
					.println("Open the following URL and grant access to your account:");
			System.out.println(requestToken.getAuthorizationURL());
			System.out
					.print("Enter the PIN(if aviailable) or just hit enter.[PIN]:");
			String pin = br.readLine();
			try {
				if (pin.length() > 0) {
					accessToken = twitter
							.getOAuthAccessToken(requestToken, pin);
				} else {
					accessToken = twitter.getOAuthAccessToken();
				}
			} catch (TwitterException te) {
				if (401 == te.getStatusCode()) {
					System.out.println("Unable to get the access token.");
				} else {
					te.printStackTrace();
				}
			}
		}
		// 将来の参照用に accessToken を永続化する
		String userId = Helper.generatedIdString("TW_");
		storeAccessToken(userId, twitter.verifyCredentials().getId(),
				accessToken);
		twitter.updateStatus("login successful.");
	}

	static void storeAccessToken(String userId, long l, AccessToken accessToken)
			throws UnknownHostException, MongoException {

		try (ACMongo mongo = new ACMongo()) {
			Query<TwitterAccount> query = mongo.createQuery(
					TwitterAccount.class).filter("user_id = ",
					accessToken.getUserId());

			TwitterAccount twitterAccount = query.get();

			if (twitterAccount != null) {
				twitterAccount.setAccessToken(accessToken.getToken());
				twitterAccount.setTokenSecret(accessToken.getTokenSecret());
				twitterAccount.setUser_id(accessToken.getUserId());
				twitterAccount.setScreen_name(accessToken.getScreenName());

			} else {
				twitterAccount = new TwitterAccount(userId, 0L,
						accessToken.getToken(), accessToken.getTokenSecret(),
						accessToken.getUserId(), accessToken.getScreenName());
				User user = new User(userId, "Sign up by Twitter.",
						accessToken.getScreenName(), -1);
				mongo.save(user);
			}
			mongo.save(twitterAccount);
		}
	}

	// Twitterインスタンスの取得
	private Twitter getTwitter() {
		ConfigurationBuilder builder = new ConfigurationBuilder();

		builder.setOAuthAccessToken(accessToken);
		builder.setOAuthAccessTokenSecret(oauthAccessTokenSecret_);

		builder.setOAuthConsumerKey(consumerKey);
		builder.setOAuthConsumerSecret(consumerSecret);

		return new TwitterFactory(builder.build()).getInstance();
	}

	// public static void main(String[] args) {
	// try {
	// new TwitterService().tweet("テスト");
	// } catch (TwitterException e) {
	// e.printStackTrace();
	// }
	// }

	private Twitter getTwitter(AccessToken accessToken) {
		ConfigurationBuilder builder = new ConfigurationBuilder();

		builder.setOAuthAccessToken(accessToken.getToken());
		builder.setOAuthAccessTokenSecret(accessToken.getTokenSecret());

		builder.setOAuthConsumerKey(consumerKey);
		builder.setOAuthConsumerSecret(consumerSecret);

		return new TwitterFactory(builder.build()).getInstance();
	}

	// つぶやく
	public void tweet(String message) throws TwitterException {
		Twitter twitter = getTwitter();
		twitter.updateStatus(message);
	}

	// つぶやく
	public void tweet(String userId, String message) throws TwitterException,
			UnknownHostException, MongoException {
		Twitter twitter = getTwitter(loadAccessToken(userId));
		if (twitter != null) {
			twitter.updateStatus(message);
		}
	}

}
