package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "keys_and_images", noClassnameStored = true)
public class KeyAndImage implements Createable, Deleteable {

	@Id
	ObjectId	id;

	String		key;

	String		imagePath;

	Long		createDate;

	Boolean		isDeleted;

	public KeyAndImage() {
		super();
		this.createDate = System.currentTimeMillis();
		this.isDeleted = false;
	}

	public KeyAndImage(String key, String imagePath) {
		this();
		this.key = key;
		this.imagePath = imagePath;
	}

	@Override
	public Long getCreateDate() {
		return this.createDate;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	@Override
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	public String getKey() {
		return this.key;
	}

	@Override
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
