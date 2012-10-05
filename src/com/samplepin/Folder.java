package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.samplepin.common.Helper;

@Entity(value = "folders", noClassnameStored = true)
public class Folder implements Updateable, Deleteable {

	@Id
	ObjectId id;

	String folderId;

	String userId;

	String folderName;

	String cards;

	@Indexed
	Long createDate;

	@Indexed
	Boolean isDeleted;

	@Indexed
	Long updateDate;

	public Folder() {
		super();
		this.folderId = Helper.generatedIdString("F_");
		this.folderName = "";
		this.cards = "";
		this.createDate = System.currentTimeMillis();
		this.isDeleted = false;
		this.updateDate = this.createDate;
	}

	public Folder(String userId, String folderName, String cards) {
		this();
		this.userId = userId;
		this.folderName = folderName;
		this.cards = cards;
	}

	public String getCards() {
		return this.cards;
	}

	public Long getCreateDate() {
		return this.createDate;
	}

	public String getFolderId() {
		return this.folderId;
	}

	public String getFolderName() {
		return this.folderName;
	}

	public ObjectId getId() {
		return this.id;
	}

	@Override
	public Boolean getIsDeleted() {
		return this.isDeleted;
	}

	@Override
	public Long getUpdateDate() {
		return this.updateDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setCards(String cards) {
		this.cards = cards;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setFolderId(String folderId) {
		this.folderId = folderId;
	}

	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	@Override
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	@Override
	public void setUpdateDate(Long updateDate) {
		this.updateDate = updateDate;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
