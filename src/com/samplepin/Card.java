package com.samplepin;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "cards", noClassnameStored = true)
public class Card implements Serializable, Deleteable, Createable {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2686047770755644944L;

	@Id
	ObjectId					id;

	String						url;

	String						caption;

	String						userId;

	Integer						likes;

	Integer						view;

	String						cardId;

	Long						createDate;

	Boolean						isDeleted;

	String						imagePath;

	public Card() {
		this("", "", "", "", "", 0, 0, System.currentTimeMillis());
	}

	public Card(String cardId, String userId, String imagePath, String url,
			String caption, int likes, int view, long createDate) {
		super();
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

	public String getCaption() {
		return this.caption;
	}

	public String getCardId() {
		return this.cardId;
	}

	@Override
	public Long getCreateDate() {
		return this.createDate;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	@Override
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public Integer getLikes() {
		return this.likes;
	}

	public String getUrl() {
		return this.url;
	}

	public String getUserId() {
		return this.userId;
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

	@Override
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setView(Integer view) {
		this.view = view;
	}

}
