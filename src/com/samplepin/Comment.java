package com.samplepin;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "comments", noClassnameStored = true)
public class Comment implements Serializable, Deleteable, Createable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2819722174698713202L;

	@Id
	ObjectId					id;

	String						userId;

	String						cardId;

	String						caption;

	Long						createDate;

	Boolean						isDeleted;

	String						imagePath;

	public Comment() {
		super();
	}

	public Comment(String userId, String cardId, String caption, Long createDate) {
		super();
		this.userId = userId;
		this.cardId = cardId;
		this.caption = caption;
		this.createDate = createDate;
		this.isDeleted = false;
		this.imagePath = "";
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

	public String getUserId() {
		return this.userId;
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

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
