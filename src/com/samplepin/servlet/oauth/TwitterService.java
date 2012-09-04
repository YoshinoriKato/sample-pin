package com.samplepin.servlet.oauth;

import static com.samplepin.common.CryptMaker.decode;
import static com.samplepin.common.CryptMaker.encode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.TwitterAccount;
import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

public class TwitterService {

	public static final String	consumerKey				= "dV2mPk3qpbF0WjRzAG4WA";

	public static final String	consumerSecret			= "fNPyRGxguYusu4CVGarKAVVsivJSMqHaERsWqPu0MhQ";

	private static final String	accessToken				= "549106982-sopZwtHopVicxBWgkSDSOdAGGr2WbevJJ9AflVFT";

	private static final String	oauthAccessTokenSecret_	= "10EXax8KjrVdcYg3UirwYMdST2TuLSuPoC4s11UBU";

	public static final String	callbackUrl				= Helper.DOMAIN
																+ "oauth-twitter.jsp";

	private static AccessToken loadAccessToken(String userId)
			throws UnknownHostException, MongoException,
			UnsupportedEncodingException {
		try (ACMongo mongo = new ACMongo()) {
			TwitterAccount twitterAccount = mongo
					.createQuery(TwitterAccount.class).filter("userId", userId)
					.get();
			if (twitterAccount == null) {
				return null;
			}
			String token = decode(twitterAccount.getAccessToken());
			String tokenSecret = decode(twitterAccount.getTokenSecret());
			return new AccessToken(token, tokenSecret);
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
			throws UnknownHostException, MongoException,
			UnsupportedEncodingException {

		try (ACMongo mongo = new ACMongo()) {
			Query<TwitterAccount> query = mongo.createQuery(
					TwitterAccount.class).filter("user_id = ",
					accessToken.getUserId());

			TwitterAccount twitterAccount = query.get();

			String token = encode(accessToken.getToken());
			String tokenSecret = encode(accessToken.getTokenSecret());

			if (twitterAccount != null) {
				twitterAccount.setAccessToken(token);
				twitterAccount.setTokenSecret(tokenSecret);
				twitterAccount.setScreen_name(accessToken.getScreenName());

			} else {
				twitterAccount = new TwitterAccount(userId, 0L, token,
						tokenSecret, accessToken.getUserId(),
						accessToken.getScreenName());
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
			UnknownHostException, MongoException, UnsupportedEncodingException {
		AccessToken accessToken = loadAccessToken(userId);
		if (accessToken != null) {
			Twitter twitter = getTwitter(accessToken);
			if (twitter != null) {
				twitter.updateStatus(message);
			}
		}
	}

}
