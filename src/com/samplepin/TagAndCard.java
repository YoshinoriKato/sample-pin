package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "tags_and_cards", noClassnameStored = true)
public class TagAndCard {

	@Id
	ObjectId id;

	String tag;

	String cardId;

	Long createDate;

	Boolean isDeleted;

	public TagAndCard() {
		super();
		this.createDate = System.currentTimeMillis();
		this.isDeleted = false;
	}

	public TagAndCard(String tag, String cardId) {
		this();
		this.tag = tag;
		this.cardId = cardId;
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

	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public String getTag() {
		return this.tag;
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

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
