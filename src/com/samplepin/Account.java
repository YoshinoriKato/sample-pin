package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

public class Account {

	@Id
	ObjectId	id;

	String		sns;

	String		appKey;

	String		secretKey;

	public Account() {
		super();
	}

	public Account(String sns, String appKey, String secretKey) {
		super();
		this.sns = sns;
		this.appKey = appKey;
		this.secretKey = secretKey;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getSecretKey() {
		return this.secretKey;
	}

	public String getSns() {
		return this.sns;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public void setSns(String sns) {
		this.sns = sns;
	}

}
