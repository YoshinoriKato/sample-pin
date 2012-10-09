package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "accounts_facebook", noClassnameStored = true)
public class FacebookAccount {

	@Id
	ObjectId id;

	String accesToken;

	String applicationID;

	String applicationSecret;

	String code;

	Long createDate;

	Long facebookID;

	String pageID;

	Long updateDate;

	String userId;

	public FacebookAccount() {
		super();
		this.facebookID = 0L;
		this.userId = "";
		this.applicationID = "";
		this.applicationSecret = "";
		this.createDate = 0L;
		this.updateDate = 0L;
		this.code = "";
		this.accesToken = "";
		this.pageID = "";
	}

	public FacebookAccount(Long facebookID, String userId,
			String applicationID, String applicationSecret, String code,
			Long createDate, Long updateDate, String accesToken, String pageID) {
		super();
		this.facebookID = facebookID;
		this.userId = userId;
		this.applicationID = applicationID;
		this.applicationSecret = applicationSecret;
		this.createDate = createDate;
		this.updateDate = updateDate;
		this.code = code;
		this.accesToken = accesToken;
		this.pageID = pageID;
	}

	public String getAccesToken() {
		return this.accesToken;
	}

	public String getApplicationID() {
		return this.applicationID;
	}

	public String getApplicationSecret() {
		return this.applicationSecret;
	}

	public String getCode() {
		return this.code;
	}

	public Long getCreateDate() {
		return this.createDate;
	}

	public Long getFacebookID() {
		return this.facebookID;
	}

	public String getPageID() {
		return this.pageID;
	}

	public Long getUpdateDate() {
		return this.updateDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setAccesToken(String accesToken) {
		this.accesToken = accesToken;
	}

	public void setApplicationID(String applicationID) {
		this.applicationID = applicationID;
	}

	public void setApplicationSecret(String applicationSecret) {
		this.applicationSecret = applicationSecret;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setFacebookID(Long facebookID) {
		this.facebookID = facebookID;
	}

	public void setPageID(String pageID) {
		this.pageID = pageID;
	}

	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
