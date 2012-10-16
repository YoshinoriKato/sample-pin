package com.samplepin;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;

@Entity(value = "cards", noClassnameStored = true)
public class Card extends Comment implements Updateable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2686047770755644944L;

	String parentId;

	@Transient
	String parentIcon;

	String url;

	Integer likes;

	Integer view;

	Integer width;

	Integer height;

	String keywords;

	String site;
	
	String title;

	
	
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Indexed
	Long updateDate;

	public Card() {
		this("self", "", "", "", "", "", "", 0, 0, System.currentTimeMillis());
	}

	public Card(String parentId, String cardId, String userId,
			String imagePath, String url, String title, String caption, int likes,
			int view, long createDate) {
		super();
		this.parentId = parentId;
		this.cardId = cardId;
		this.userId = userId;
		this.imagePath = imagePath;
		this.url = url;
		this.title = title;
		this.caption = caption;
		this.likes = likes;
		this.view = view;
		this.createDate = createDate;
		this.updateDate = createDate;
		this.isDeleted = false;
		this.width = 400;
		this.height = 400;
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

	public String getParentIcon() {
		return this.parentIcon;
	}

	public String getParentId() {
		return this.parentId;
	}

	public String getSite() {
		return this.site;
	}

	@Override
	public Long getUpdateDate() {
		return this.updateDate;
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

	public void setParentIcon(String parentIcon) {
		this.parentIcon = parentIcon;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setSite(String site) {
		this.site = site;
	}

	@Override
	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
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
