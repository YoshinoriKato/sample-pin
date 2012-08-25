package com.samplepin;

import java.util.Arrays;

import org.bson.types.ObjectId;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;

@Entity(value = "keywordlists_and_cards", noClassnameStored = true)
public class KeywordsAndCard {

	@Id
	ObjectId	id;

	String[]	keywords;

	String		cardId;

	public KeywordsAndCard() {
		super();
	}

	public KeywordsAndCard(String[] keywords, String cardId) {
		super();
		this.keywords = keywords;
		this.cardId = cardId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		KeywordsAndCard other = (KeywordsAndCard) obj;
		if (this.cardId == null) {
			if (other.cardId != null) {
				return false;
			}
		} else if (!this.cardId.equals(other.cardId)) {
			return false;
		}
		if (!Arrays.equals(this.keywords, other.keywords)) {
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

	public String[] getKeywords() {
		return this.keywords;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result)
				+ ((this.cardId == null) ? 0 : this.cardId.hashCode());
		result = (prime * result) + Arrays.hashCode(this.keywords);
		return result;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

}
