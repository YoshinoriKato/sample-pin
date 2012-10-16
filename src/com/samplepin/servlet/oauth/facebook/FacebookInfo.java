package com.samplepin.servlet.oauth.facebook;

public class FacebookInfo {

	String id;

	String name;

	Object picture;

	public FacebookInfo() {

	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Object getPicture() {
		return this.picture;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPicture(Object picture) {
		this.picture = picture;
	}

}