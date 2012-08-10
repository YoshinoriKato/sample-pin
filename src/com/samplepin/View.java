package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "views", noClassnameStored = true)
public class View {

	@Id
	ObjectId id;

	Long visitedDate;

	String cardId;

	String userId;

	public View() {

	}

	public View(Long visitedDate, String cardId, String userId) {
		super();
		this.visitedDate = visitedDate;
		this.cardId = cardId;
		this.userId = userId;
	}

	public String getCardId() {
		return this.cardId;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getUserId() {
		return this.userId;
	}

	public Long getVisitedDate() {
		return this.visitedDate;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setVisitedDate(Long visitedDate) {
		this.visitedDate = visitedDate;
	}
}
