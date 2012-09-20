package com.samplepin.servlet.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;
import com.samplepin.nl.NaturalLanguageParser;

public class SearchAjax extends CardAjax {

	void ajax(OutputStream os, String otherUserId, String sorted,
			String offset, String limit, String callback, String old,
			String young, String type, String userId, String cardId,
			String words, String dic) throws IOException {

		List<Card> cards = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		type = valid(type) ? type : "card";
		data.put("type", type);

		try (ACMongo mongo = new ACMongo()) {
			Set<String> searched = NaturalLanguageParser.cardIds(dic, words);
			if (valid(searched)) {
				cards = cards(mongo, otherUserId, sorted, offset, limit,
						callback, old, young, type, userId, cardId, searched);
				for (Card card : cards) {
					User user = Helper.getUserById(card.getUserId());
					if (user != null) {
						Helper.setUserInfoToComment(card, user);
					}
				}
			}
		}

		data.put("array", cards.toArray(new Card[0]));
		writeToJSON(os, data, callback);
	}

	List<Card> cards(ACMongo mongo, String otherUserId, String sorted,
			String offset, String limit, String callback, String old,
			String young, String type, String userId, String cardId,
			Set<String> searched) {
		List<Card> cards = new ArrayList<>();
		Query<Card> query = mongo.createQuery(Card.class)
				.filter("isDeleted", false).filter("cardId in ", searched);

		if (valid(otherUserId)) {
			query.filter("userId = ", otherUserId);
		}

		if (valid(old)) {
			query.filter("createDate < ", Long.valueOf(old));
		}

		if (valid(young)) {
			query.filter("createDate > ", Long.valueOf(young));
		}

		// sort
		query.order("-updateDate");

		// option
		if (valid(limit)) {
			query.limit(Integer.valueOf(limit));
		} else {
			query.limit(1000);
		}

		if (valid(offset)) {
			query.offset(Integer.valueOf(offset));
		}

		cards = query.asList();
		return cards;
	}

}
