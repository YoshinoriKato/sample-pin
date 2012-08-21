package com.samplepin;

import com.google.code.morphia.annotations.Entity;

@Entity(value = "cards", noClassnameStored = true)
public class Card extends Comment {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2686047770755644944L;

	String						parentId;

	String						url;

	Integer						likes;

	Integer						view;

	Integer						width;

	Integer						height;

	String						keywords;

	String						site;

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

	public Integer getHeight() {
		return this.height;
	}

	public String getKeywords() {
		return this.keywords;
	}

	public Integer getLikes() {
		return this.likes;
	}

	public String getParentId() {
		return this.parentId;
	}

	public String getSite() {
		return this.site;
	}

	public String getUrl() {
		return this.url;
	}

	public Integer getView() {
		return this.view;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setView(Integer view) {
		this.view = view;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

}
