package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "users", noClassnameStored = true)
public class User {

	@Id
	ObjectId id;

	String userId;

	Integer password;

	Long birthDay;

	String mail;

	Integer loginFaileds;

	Long lastUpdate;

	Long createDate;

	String fontColor;

	String backgroundImage;

	String userName;

	String backgroundColor;

	public User() {
		super();
		this.userId = "";
		this.password = 0;
		this.birthDay = 0L;
		this.mail = "";
		this.loginFaileds = 0;
		this.lastUpdate = 0L;
		this.createDate = System.currentTimeMillis();
		this.fontColor = "#666666";
		this.backgroundImage = "img/wallpaper.png";
		this.userName = "nanashi";
		this.backgroundColor = "#cccccc";
	}

	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	public String getBackgroundImage() {
		return this.backgroundImage;
	}

	public Long getBirthDay() {
		return this.birthDay;
	}

	public Long getCreateDate() {
		return this.createDate;
	}

	public String getFontColor() {
		return this.fontColor;
	}

	public ObjectId getId() {
		return this.id;
	}

	public Long getLastUpdate() {
		return this.lastUpdate;
	}

	public Integer getLoginFaileds() {
		return this.loginFaileds;
	}

	public String getMail() {
		return this.mail;
	}

	public Integer getPassword() {
		return this.password;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public void setBackgroundImage(String backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public void setBirthDay(Long birthDay) {
		this.birthDay = birthDay;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setLastUpdate(Long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public void setLoginFaileds(Integer loginFaileds) {
		this.loginFaileds = loginFaileds;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPassword(Integer password) {
		this.password = password;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
