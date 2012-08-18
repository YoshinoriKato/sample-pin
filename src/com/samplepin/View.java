package com.samplepin;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "views", noClassnameStored = true)
public class View implements Serializable, Deleteable, Createable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3644966756588336335L;

	@Id
	ObjectId					id;

	Long						visitedDate;

	Long						createDate;

	String						cardId;

	String						userId;

	Integer						times;

	Integer						comments;

	Boolean						isDeleted;

	public View() {
		this(System.currentTimeMillis(), "", "");
	}

	public View(Long visitedDate, String cardId, String userId) {
		super();
		this.createDate = System.currentTimeMillis();
		this.visitedDate = visitedDate;
		this.cardId = cardId;
		this.userId = userId;
		this.times = 0;
		this.comments = 0;
		this.isDeleted = false;
	}

	public String getCardId() {
		return this.cardId;
	}

	public Integer getComments() {
		return this.comments;
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
