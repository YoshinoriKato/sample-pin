package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "recommends", noClassnameStored = true)
public class Recommend {

	@Id
	ObjectId id;

	String userId;

	Long createDate;

	String recommendJSON;

	public Recommend(String userId, String recommendJSON) {
		super();
		this.userId = userId;
		this.createDate = System.currentTimeMillis();
		this.recommendJSON = recommendJSON;
	}

	public Long getCreateDate() {
		return this.createDate;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getRecommendJSON() {
		return this.recommendJSON;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setRecommendJSON(String recommendJSON) {
		this.recommendJSON = recommendJSON;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
