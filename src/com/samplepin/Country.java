package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "countries", noClassnameStored = true)
public class Country {

	@Id
	ObjectId id;

	String jpName;

	String enName;

	Integer code;

	String short3;

	String short2;

	String area;

	String isoCode;

	public Country() {
		super();
	}

	public Country(String jpName, String enName, Integer code, String short3,
			String short2, String area, String isoCode) {
		super();
		this.jpName = jpName;
		this.enName = enName;
		this.code = code;
		this.short3 = short3;
		this.short2 = short2;
		this.area = area;
		this.isoCode = isoCode;
	}

	public String getArea() {
		return this.area;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getEnName() {
		return this.enName;
	}

	public String getIsoCode() {
		return this.isoCode;
	}

	public String getJpName() {
		return this.jpName;
	}

	public String getShort2() {
		return this.short2;
	}

	public String getShort3() {
		return this.short3;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public void setIsoCode(String isoCode) {
		this.isoCode = isoCode;
	}

	public void setJpName(String jpName) {
		this.jpName = jpName;
	}

	public void setShort2(String short2) {
		this.short2 = short2;
	}

	public void setShort3(String short3) {
		this.short3 = short3;
	}

}
