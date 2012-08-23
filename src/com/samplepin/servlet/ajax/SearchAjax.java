package com.samplepin.servlet.ajax;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.reduls.igo.Tagger;

import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.KeywordAndCard;
import com.samplepin.common.ACMongo;

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
			List<String> tokens = tokens(words, dic);
			if ((tokens != null) && !tokens.isEmpty()) {
				Set<String> searched = searched(mongo, tokens);

				if ((searched != null) && !searched.isEmpty()) {
					cards = cards(mongo, otherUserId, sorted, offset, limit,
							callback, old, young, type, userId, cardId,
							searched);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
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

	final Set<String> searched(ACMongo mongo, List<String> tokens) {
		Query<KeywordAndCard> query0 = mongo.createQuery(KeywordAndCard.class)
				.filter("keyword in ", tokens);

		Set<String> searched = new HashSet<>();
		for (KeywordAndCard keywordAndCard : query0.asList()) {
			searched.add(keywordAndCard.getCardId());
		}
		return searched;
	}

	final List<String> tokens(String words, String dic)
			throws FileNotFoundException, IOException {
		Tagger tagger = new Tagger(dic);
		List<String> tokens = tagger.wakati(words);
		return tokens;
	}

}
