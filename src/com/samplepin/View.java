package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Id;


public class View {

	@Id
	ObjectId id;
	
	Long visitedDate;
	
	String cardId;
	
	String userId;
	
	
	
	public View(Long visitedDate, String cardId, String userId) {
		super();
		this.visitedDate = visitedDate;
		this.cardId = cardId;
		this.userId = userId;
	}

	public View(){
		
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public Long getVisitedDate() {
		return visitedDate;
	}

	public void setVisitedDate(Long visitedDate) {
		this.visitedDate = visitedDate;
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
}
