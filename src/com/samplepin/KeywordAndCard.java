package com.samplepin;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "keywords_and_cards", noClassnameStored = true)
public class KeywordAndCard {

	@Id
	ObjectId	id;

	String		keyword;

	String		cardId;

	String		part;

	public KeywordAndCard() {
		super();
	}

	public KeywordAndCard(String keyword, String cardId, String part) {
		super();
		this.keyword = keyword;
		this.cardId = cardId;
		this.part = part;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof KeywordAndCard)) {
			return false;
		}
		KeywordAndCard other = (KeywordAndCard) obj;
		if (this.cardId == null) {
			if (other.cardId != null) {
				return false;
			}
		} else if (!this.cardId.equals(other.cardId)) {
			return false;
		}
		if (this.keyword == null) {
			if (other.keyword != null) {
				return false;
			}
		} else if (!this.keyword.equals(other.keyword)) {
			return false;
		}
		if (this.part == null) {
			if (other.part != null) {
				return false;
			}
		} else if (!this.part.equals(other.part)) {
			return false;
		}
		return true;
	}

	public String getCardId() {
		return this.cardId;
	}

	public ObjectId getId() {
		return this.id;
	}

	public String getKeyword() {
		return this.keyword;
	}

	public String getPart() {
		return this.part;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.cardId == null) ? 0 : this.cardId.hashCode());
		result = (prime * result)
				+ ((this.keyword == null) ? 0 : this.keyword.hashCode());
		result = (prime * result)
				+ ((this.part == null) ? 0 : this.part.hashCode());
		return result;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setPart(String part) {
		this.part = part;
	}

}
