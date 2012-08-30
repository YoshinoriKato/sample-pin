package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

@Entity(value = "activities", noClassnameStored = true)
public class Activity implements Createable {

	@Id
	ObjectId	id;

	@Indexed
	String		userId;

	@Indexed
	String		action;

	String		note;

	@Indexed
	Long		createDate;

	public Activity() {
		super();
	}

	public Activity(String userId, String action, String note, Long createDate) {
		super();
		this.userId = userId;
		this.action = action;
		this.note = note;
		this.createDate = createDate;
	}

	public String getAction() {
		return this.action;
	}

	@Override
	public Long getCreateDate() {
		return this.createDate;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getNote() {
		return this.note;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
