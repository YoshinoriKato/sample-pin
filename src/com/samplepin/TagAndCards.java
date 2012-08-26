package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "tags_and_cardlists", noClassnameStored = true)
public class TagAndCards {

	@Id
	ObjectId id;

	String tag;

	String[] cardIds;

	Integer size;

	Long createDate;

	Boolean isDeleted;

	public TagAndCards() {
		super();
		this.createDate = System.currentTimeMillis();
		this.isDeleted = false;
	}

	public TagAndCards(String tag, String[] cardIds, Integer size) {
		super();
		this.tag = tag;
		this.cardIds = cardIds;
		this.size = size;
	}

	public String[] getCardIds() {
		return this.cardIds;
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

	public Integer getSize() {
		return this.size;
	}

	public String getTag() {
		return this.tag;
	}

	public void setCardIds(String[] cardIds) {
		this.cardIds = cardIds;
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

	public void setSize(Integer size) {
		this.size = size;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
