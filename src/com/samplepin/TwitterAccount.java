package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

@Entity(value = "accounts_twitter", noClassnameStored = true)
public class TwitterAccount {

	@Id
	ObjectId id;

	String accessToken;

	String screen_name;

	String tokenSecret;

	Long twitterId;

	@Indexed
	Long user_id;

	@Indexed
	String userId;

	public TwitterAccount() {
		super();
		this.userId = "";
		this.twitterId = 0L;
		this.accessToken = "";
		this.tokenSecret = "";
		this.user_id = 0L;
		this.screen_name = "";
	}

	public TwitterAccount(String userId, Long twitterID, String accessToken,
			String tokenSecret, Long user_id, String screen_name) {
		super();
		this.userId = userId;
		this.twitterId = twitterID;
		this.accessToken = accessToken;
		this.tokenSecret = tokenSecret;
		this.user_id = user_id;
		this.screen_name = screen_name;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public String getScreen_name() {
		return this.screen_name;
	}

	public String getTokenSecret() {
		return this.tokenSecret;
	}

	public Long getTwitterID() {
		return this.twitterId;
	}

	public Long getUser_id() {
		return this.user_id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public void setTokenSecret(String tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

	public void setTwitterID(Long twitterID) {
		this.twitterId = twitterID;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
