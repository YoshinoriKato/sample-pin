package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

@Entity(value = "tags", noClassnameStored = true)
public class Tag implements Createable, Deleteable {

	@Id
	ObjectId	id;

	@Indexed
	String		tag;

	String		imagePath;

	Long		size;

	Long		createDate;

	@Indexed
	Boolean		isDeleted;

	public Tag() {
		super();
		this.createDate = System.currentTimeMillis();
		this.isDeleted = false;
	}

	public Tag(String tag, Long size, String imagePath) {
		this();
		this.tag = tag;
		this.size = size;
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

	public Long getSize() {
		return this.size;
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

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	@Override
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setSize(Long size) {
		this.size = size;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
