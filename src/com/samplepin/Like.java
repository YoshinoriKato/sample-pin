package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "likes", noClassnameStored = true)
public class Like implements Createable, Updateable, Deleteable {

	@Id
	ObjectId id;

	String cardId;

	String userId;

	Long createDate;

	Long updateDate;

	Boolean isDeleted;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public Long getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Like() {
		super();
		this.cardId = "";
		this.userId = "";
		this.createDate = System.currentTimeMillis();
		this.updateDate = createDate;
		this.isDeleted = false;
	}

	public Like(String cardId, String userId) {
		this();
		this.cardId = cardId;
		this.userId = userId;
	}

}
