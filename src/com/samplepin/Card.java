package com.samplepin;

import com.google.code.morphia.annotations.Entity;

@Entity(value = "cards", noClassnameStored = true)
public class Card extends Comment {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2686047770755644944L;

	String parentId;

	String url;

	Integer likes;

	Integer view;

	public Card() {
		this("self", "", "", "", "", "", 0, 0, System.currentTimeMillis());
	}

	public Card(String parentId, String cardId, String userId,
			String imagePath, String url, String caption, int likes, int view,
			long createDate) {
		super();
		this.parentId = parentId;
		this.cardId = cardId;
		this.userId = userId;
		this.imagePath = imagePath;
		this.url = url;
		this.caption = caption;
		this.likes = likes;
		this.view = view;
		this.createDate = createDate;
		this.isDeleted = false;
	}

	public Integer getLikes() {
		return this.likes;
	}

	public String getUrl() {
		return this.url;
	}

	public Integer getView() {
		return this.view;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setView(Integer view) {
		this.view = view;
	}

}
