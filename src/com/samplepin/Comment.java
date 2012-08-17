package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "comments", noClassnameStored = true)
public class Comment {

	@Id
	ObjectId id;

	String userId;

	String cardId;

	String comment;

	Long createDate;

	Boolean isDeleted;

	public Comment() {
		super();
	}

	public Comment(String userId, String cardId, String comment, Long createDate) {
		super();
		this.userId = userId;
		this.cardId = cardId;
		this.comment = comment;
		this.createDate = createDate;
		this.isDeleted = false;
	}

	public String getCardId() {
		return this.cardId;
	}

	public String getComment() {
		return this.comment;
	}

	public Long getCreateDate() {
		return this.createDate;
	}

	public ObjectId getId() {
		return this.id;
	}

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
