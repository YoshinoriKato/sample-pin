package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "onetimes", noClassnameStored = true)
public class OneTime {

	@Id
	ObjectId	id;

	String		mail;

	String		oneTimePassword;

	String		password;

	Long		loginDate;

	public OneTime() {
		super();
	}

	public OneTime(String mail, String oneTimePassword, String password) {
		this();
		this.mail = mail;
		this.oneTimePassword = oneTimePassword;
		this.password = password;
	}

	public ObjectId getId() {
		return this.id;
	}

	public Long getLoginDate() {
		return this.loginDate;
	}

	public String getMail() {
		return this.mail;
	}

	public String getOneTimePassword() {
		return this.oneTimePassword;
	}

	public String getPassword() {
		return this.password;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setLoginDate(Long loginDate) {
		this.loginDate = loginDate;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setOneTimePassword(String oneTimePassword) {
		this.oneTimePassword = oneTimePassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
