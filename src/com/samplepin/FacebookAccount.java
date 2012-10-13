package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "accounts_facebook", noClassnameStored = true)
public class FacebookAccount {

	@Id
	ObjectId	id;

	String		facebookId;

	String		userName;

	String		accessToken;

	String		userId;

	public FacebookAccount() {
		super();
		this.facebookId = "";
		this.userName = "";
		this.accessToken = "";
		this.userId = "";
	}

	public FacebookAccount(String facebookId, String userName,
			String accessToken, String userId) {
		super();
		this.facebookId = facebookId;
		this.userName = userName;
		this.accessToken = accessToken;
		this.userId = userId;
	}

	public String getAccessToken() {
		return this.accessToken;
	}

	public String getFacebookId() {
		return this.facebookId;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setFacebookId(String facebookId) {
		this.facebookId = facebookId;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
