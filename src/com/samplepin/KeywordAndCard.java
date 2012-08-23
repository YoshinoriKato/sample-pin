package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "keywords_and_cards", noClassnameStored = true)
public class KeywordAndCard {

	@Id
	ObjectId	id;

	String		keyword;

	String		cardId;

	String		part;

	public KeywordAndCard() {
		super();
	}

	public KeywordAndCard(String keyword, String cardId, String part) {
		super();
		this.keyword = keyword;
		this.cardId = cardId;
		this.part = part;
	}

	public String getCardId() {
		return this.cardId;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public String getPart() {
		return this.part;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setPart(String part) {
		this.part = part;
	}

}
