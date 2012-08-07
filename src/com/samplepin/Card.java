package com.samplepin;

import java.io.Serializable;

public class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2686047770755644944L;
	String url;
	String caption;
	int likes;
	int view;

	public Card(String url, String caption, int likes, int view) {
		super();
		this.url = url;
		this.caption = caption;
		this.likes = likes;
		this.view = view;
	}
	
	public Card(){
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public int getView() {
		return view;
	}

	public void setView(int view) {
		this.view = view;
	}
	
	
}