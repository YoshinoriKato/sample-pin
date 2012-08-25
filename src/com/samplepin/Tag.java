package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "tags", noClassnameStored = true)
public class Tag implements Createable, Deleteable {

	@Id
	ObjectId id;

	String tag;

	String key;

	Long createDate;

	Boolean isDeleted;

	public Tag() {
		super();
		this.createDate = System.currentTimeMillis();
		this.isDeleted = false;
	}

	public Tag(String tag, String key) {
		this();
		this.tag = tag;
		this.key = key;
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

	public String getKey() {
		return this.key;
	}

	public String getTag() {
		return this.tag;
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

	public void setKey(String key) {
		this.key = key;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
