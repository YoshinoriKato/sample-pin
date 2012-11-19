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
import com.samplepin.common.ACMongo;
import com.samplepin.nl.NaturalLanguageParser;

public class SearchAjax extends CardAjax {

	void ajax(OutputStream os, AjaxInfo info, String dic) throws IOException {

		List<Card> cards = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		info.type = valid(info.type) ? info.type : "card";
		data.put("type", info.type);
		data.put("userId", info.userId);
		data.put("select", info.select);

		try (ACMongo mongo = new ACMongo()) {
			Set<String> searched = NaturalLanguageParser.cardIds(dic,
					info.words);
			if (valid(searched)) {
				cards = cards(mongo, info, searched);
			}
		}

		data.put("array", cards.toArray(new Card[0]));
		writeToJSON(os, data, info.callback);
	}

	public List<Card> cards(ACMongo mongo, AjaxInfo info, Set<String> searched) {
		List<Card> cards = new ArrayList<>();
		Query<Card> query = mongo.createQuery(Card.class)
				.filter("isDeleted", false).filter("cardId in ", searched);
		query.or(query.criteria("accessLevel").equal(0),
				query.criteria("userId").equal(info.userId));

		if (valid(info.otherUserId)) {
			query.filter("userId = ", info.otherUserId);
		}

		if (valid(info.old)) {
			query.filter("createDate < ", Long.valueOf(info.old));
		}

		if (valid(info.young)) {
			query.filter("createDate > ", Long.valueOf(info.young));
		}

		// sort
		query.order("-updateDate");

		// option
		if (valid(info.limit)) {
			query.limit(Integer.valueOf(info.limit));
		} else {
			query.limit(1000);
		}

		if (valid(info.offset)) {
			query.offset(Integer.valueOf(info.offset));
		}

		cards = query.asList();
		fillValues(cards);
		return cards;
	}

}
