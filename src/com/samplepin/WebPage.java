package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "web_pages", noClassnameStored = true)
public class WebPage implements Createable {

	@Id
	ObjectId id;

	String url;

	String caption;

	String title;

	String domain;

	String favicon;

	Long createDate;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getFavicon() {
		return favicon;
	}

	public void setFavicon(String favicon) {
		this.favicon = favicon;
	}

	public Long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public WebPage() {
		super();
		this.url = "";
		this.caption = "";
		this.title = "";
		this.domain = "";
		this.favicon = "";
		this.createDate = System.currentTimeMillis();
	}


	public WebPage(String url, String caption, String title, String domain,
			String favicon) {
		this();
		this.url = url;
		this.caption = caption;
		this.title = title;
		this.domain = domain;
		this.favicon = favicon;
	}

	
}
