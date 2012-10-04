package com.samplepin;

import java.io.Serializable;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

@Entity(value = "users", noClassnameStored = true)
public class User implements Serializable, Deleteable, Createable {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2561029945069027198L;

	@Id
	ObjectId					id;

	@Indexed
	String						userId;

	Integer						password;

	String						imagePath;

	String						birthDay;

	String						mail;

	Integer						loginFaileds;

	Long						lastUpdate;

	Long						createDate;

	String						fontColor;

	String						backgroundImage;

	String						userName;

	String						backgroundColor;

	String						textShadowColor;

	Boolean						useBackgroundImage;

	Integer						code;

	String						comment;

	Integer						category;

	@Indexed
	Boolean						isDeleted;

	Boolean						isFirst;

	public User() {
		super();
		this.userId = "";
		this.password = 0;
		this.imagePath = "img/no_image.png";
		this.birthDay = "";
		this.mail = "";
		this.loginFaileds = 0;
		this.lastUpdate = 0L;
		this.createDate = System.currentTimeMillis();
		this.fontColor = "#666666";
		this.backgroundImage = "img/wallpaper.png";
		this.userName = "Please, set your name.";
		this.backgroundColor = "#cccccc";
		this.useBackgroundImage = true;
		this.code = 0;
		this.comment = "";
		this.category = 100;
		this.isFirst = false;
	}

	public User(String userId, String mail, String userName, Integer password) {
		this();
		this.userId = userId;
		this.mail = mail;
		this.userName = userName;
		this.password = password;
		this.isFirst = true;
	}

	public String getBackgroundColor() {
		return this.backgroundColor;
	}

	public String getBackgroundImage() {
		return this.backgroundImage;
	}

	public String getBirthDay() {
		return this.birthDay;
	}

	public Integer getCategory() {
		return this.category;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getComment() {
		return this.comment;
	}

	@Override
	public Long getCreateDate() {
		return this.createDate;
	}

	public String getFontColor() {
		return this.fontColor;
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

	public Boolean getIsFirst() {
		return this.isFirst;
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

	public String getTextShadowColor() {
		return this.textShadowColor;
	}

	public Boolean getUseBackgroundImage() {
		return this.useBackgroundImage;
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

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public void setCategory(Integer category) {
		this.category = category;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
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

	public void setIsFirst(Boolean isFirst) {
		this.isFirst = isFirst;
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

	public void setTextShadowColor(String textShadowColor) {
		this.textShadowColor = textShadowColor;
	}

	public void setUseBackgroundImage(Boolean useBackgroundImage) {
		this.useBackgroundImage = useBackgroundImage;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
