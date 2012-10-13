package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "web_pages", noClassnameStored = true)
public class WebPage implements Createable {

	@Id
	ObjectId	id;

	String		url;

	String		caption;

	String		title;

	String		domain;

	String		favicon;

	Long		createDate;

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

	public String getCaption() {
		return this.caption;
	}

	@Override
	public Long getCreateDate() {
		return this.createDate;
	}

	public String getDomain() {
		return this.domain;
	}

	public String getFavicon() {
		return this.favicon;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getTitle() {
		return this.title;
	}

	public String getUrl() {
		return this.url;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	@Override
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setFavicon(String favicon) {
		this.favicon = favicon;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
