package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "inquirys", noClassnameStored = true)
public class Inquiry implements Createable {

	@Id
	ObjectId id;

	String host;

	String connection;

	String cacheControl;

	String userAgent;

	String accept;

	String acceptEncoding;

	String acceptLanguage;

	String acceptCharset;

	String cookie;

	String remoteAddress;

	String uri;

	String protocol;

	Long createDate;

	String mail;

	String message;

	String userId;

	public Inquiry() {
		super();
		this.host = "";
		this.connection = "";
		this.cacheControl = "";
		this.userAgent = "";
		this.accept = "";
		this.acceptEncoding = "";
		this.acceptLanguage = "";
		this.acceptCharset = "";
		this.cookie = "";
		this.createDate = System.currentTimeMillis();
		this.mail = "";
		this.message = "";
		this.userId = "";
	}

	public Inquiry(String host, String connection, String cacheControl,
			String userAgent, String accept, String acceptEncoding,
			String acceptLanguage, String acceptCharset, String cookie,
			String remoteAddress, String uri, String protocol, String mail,
			String message, String userId) {
		this();
		this.host = host;
		this.connection = connection;
		this.cacheControl = cacheControl;
		this.userAgent = userAgent;
		this.accept = accept;
		this.acceptEncoding = acceptEncoding;
		this.acceptLanguage = acceptLanguage;
		this.acceptCharset = acceptCharset;
		this.cookie = cookie;
		this.remoteAddress = remoteAddress;
		this.uri = uri;
		this.protocol = protocol;
		this.mail = mail;
		this.message = message;
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAccept() {
		return this.accept;
	}

	public String getAcceptCharset() {
		return this.acceptCharset;
	}

	public String getAcceptEncoding() {
		return this.acceptEncoding;
	}

	public String getAcceptLanguage() {
		return this.acceptLanguage;
	}

	public String getCacheControl() {
		return this.cacheControl;
	}

	public String getConnection() {
		return this.connection;
	}

	public String getCookie() {
		return this.cookie;
	}

	@Override
	public Long getCreateDate() {
		return this.createDate;
	}

	public String getHost() {
		return this.host;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getProtocol() {
		return this.protocol;
	}

	public String getRemoteAddress() {
		return this.remoteAddress;
	}

	public String getUri() {
		return this.uri;
	}

	public String getUserAgent() {
		return this.userAgent;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	@Override
	public void setCreateDate(Long createDate) {
		this.createDate = createDate;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public void setRemoteAddress(String remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	@Override
	public String toString() {
		return " host = " + this.host + ", " + " connection = "
				+ this.connection + ", " + " cacheControl = "
				+ this.cacheControl + ", " + " userAgent = " + this.userAgent
				+ ", " + " accept = " + this.accept + ", "
				+ " acceptEncoding = " + this.acceptEncoding + ", "
				+ " acceptLanguage = " + this.acceptLanguage + ", "
				+ " acceptCharset = " + this.acceptCharset + ", "
				+ " cookie = " + this.cookie + ", " + " remoteAddress = "
				+ this.remoteAddress + ", " + " uri = " + this.uri + ", "
				+ " protocol = " + this.protocol;

	}
}
