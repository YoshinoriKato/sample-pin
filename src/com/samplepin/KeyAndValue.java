package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.annotations.Indexed;

@Entity(value = "keys_and_values", noClassnameStored = true)
public class KeyAndValue {

	@Id
	ObjectId id;

	@Indexed
	String key;

	String value;

	public KeyAndValue() {
		super();
	}

	public KeyAndValue(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getKey() {
		return this.key;
	}

	public String getValue() {
		return this.value;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
