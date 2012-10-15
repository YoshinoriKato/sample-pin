package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "shortcuts", noClassnameStored = true)
public class ShortCut {

	@Id
	ObjectId	id;

	String		hex;

	String		cardId;

	String		category;

	public ShortCut() {
		super();
		this.category = "S";
	}

	public ShortCut(String hex, String cardId, String category) {
		this();
		this.hex = hex;
		this.cardId = cardId;
		this.category = category;
	}

	public String getCardId() {
		return this.cardId;
	}

	public String getCategory() {
		return this.category;
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

	public void setCategory(String category) {
		this.category = category;
	}

	public void setHex(String hex) {
		this.hex = hex;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

}
