package com.samplepin.servlet.oauth.facebook;

public class FacebookUserInfo {

	String id;

	String name;

	String picture;

	public FacebookUserInfo() {

	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getPicture() {
		return this.picture;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

}