package com.samplepin;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

public class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2686047770755644944L;

	@Id
	ObjectId id;

	String url;

	String caption;

	int likes;

	int view;

	String cardId;

	Long createDate;

	public Card() {

	}

	public Card(String cardId, String url, String caption, int likes, int view,
			long createDate) {
		super();
		this.cardId = cardId;
		this.url = url;
		this.caption = caption;
		this.likes = likes;
		this.view = view;
		this.createDate = createDate;
	}

	public String getCaption() {
		return this.caption;
	}

	public String getCardId() {
		return this.cardId;
	}

	public Long getCreateDate() {
		return this.createDate;
	}

	public ObjectId getId() {
		return this.id;
	}

	public int getLikes() {
		return this.likes;
	}

	public String getUrl() {
		return this.url;
	}

	public int getView() {
		return this.view;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setView(int view) {
		this.view = view;
	}

}