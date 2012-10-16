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

	public Like() {
		super();
		this.cardId = "";
		this.userId = "";
		this.createDate = System.currentTimeMillis();
		this.updateDate = this.createDate;
		this.isDeleted = false;
	}

	public Like(String cardId, String userId) {
		this();
		this.cardId = cardId;
		this.userId = userId;
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

	@Override
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	@Override
	public Long getUpdateDate() {
		return this.updateDate;
	}

	public String getUserId() {
		return this.userId;
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

	@Override
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
