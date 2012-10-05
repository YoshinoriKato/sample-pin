package com.samplepin;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;
import com.google.code.morphia.annotations.Transient;

@Entity(value = "comments", noClassnameStored = true)
public class Comment implements Serializable, Deleteable, Createable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2819722174698713202L;

	@Id
	ObjectId id;

	String userId;

	@Indexed
	String cardId;

	String caption;

	@Indexed
	Long createDate;

	@Indexed
	Boolean isDeleted;

	String imagePath;

	@Transient
	String userIcon;

	@Transient
	String cardIcon;

	@Transient
	String userName;

	Boolean anonymous;

	Integer accessLevel;

	public Comment() {
		super();
		this.anonymous = false;
		this.isDeleted = false;
		this.imagePath = "";
		this.accessLevel = 0;
	}

	public Comment(String userId, String cardId, String caption,
			Long createDate, Boolean anonymous) {
		this();
		this.userId = userId;
		this.cardId = cardId;
		this.caption = caption;
		this.createDate = createDate;
		this.anonymous = anonymous;
	}

	public Integer getAccessLevel() {
		return this.accessLevel;
	}

	public Boolean getAnonymous() {
		return this.anonymous;
	}

	public String getCaption() {
		return this.caption;
	}

	public String getCardIcon() {
		return this.cardIcon;
	}

	public String getCardId() {
		return this.cardId;
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

	public String getUserIcon() {
		return this.userIcon;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setAccessLevel(Integer accessLevel) {
		this.accessLevel = accessLevel;
	}

	public void setAnonymous(Boolean anonymous) {
		this.anonymous = anonymous;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setCardIcon(String cardIcon) {
		this.cardIcon = cardIcon;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
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

	public void setUserIcon(String userIcon) {
		this.userIcon = userIcon;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
