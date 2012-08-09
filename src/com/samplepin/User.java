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

	public User() {
		super();
	}

	public Long getBirthDay() {
		return this.birthDay;
	}

	public Long getCreateDate() {
		return this.createDate;
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

	public void setBirthDay(Long birthDay) {
		this.birthDay = birthDay;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
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

}
