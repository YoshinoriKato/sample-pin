package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;

public class ShortCut {

	@Id
	ObjectId id;

	String hex;

	String cardId;

	public ShortCut() {
		super();
	}

	public ShortCut(String hex, String cardId) {
		super();
		this.hex = hex;
		this.cardId = cardId;
	}

	public String getCardId() {
		return this.cardId;
	}

	public String getHex() {
		return this.hex;
	}

	public ObjectId getId() {
		return this.id;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}
