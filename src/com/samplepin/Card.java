package com.samplepin;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "cards", noClassnameStored = true)
public class Card implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2686047770755644944L;

	@Id
	ObjectId id;

	String url;

	String caption;

	Integer likes;

	Integer view;

	String cardId;

	Long createDate;

	public Card() {
		this("", "", "", 0, 0, System.currentTimeMillis());
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

	public Integer getLikes() {
		return this.likes;
	}

	public String getUrl() {
		return this.url;
	}

	public Integer getView() {
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