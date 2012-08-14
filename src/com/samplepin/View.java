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

	Integer times;

	Integer comments;

	public View() {
		this(System.currentTimeMillis(), "", "");
	}

	public View(Long visitedDate, String cardId, String userId) {
		super();
		this.visitedDate = visitedDate;
		this.cardId = cardId;
		this.userId = userId;
		this.times = 0;
		this.comments = 0;
	}

	public String getCardId() {
		return this.cardId;
	}

	public Integer getComments() {
		return this.comments;
	}

	public ObjectId getId() {
		return this.id;
	}

	public Integer getTimes() {
		return this.times;
	}

	public String getUserId() {
		return this.userId;
	}

	public Long getVisitedDate() {
		return this.visitedDate;
	}

	public Integer score() {
		return getTimes() + (getComments() * 5);
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setTimes(Integer times) {
		this.times = times;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setVisitedDate(Long visitedDate) {
		this.visitedDate = visitedDate;
	}

}
