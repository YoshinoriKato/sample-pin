package com.samplepin.servlet.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Card;
import com.samplepin.Helper;
import com.samplepin.User;
import com.samplepin.View;

public class CardAjax {

	void ajax(OutputStream os, String otherUserId, String sorted,
			String offset, String limit, String callback, String old,
			String young, String type, String userId, String cardId)
			throws IOException {

		List<Card> cards = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		type = valid(type) ? type : "card";
		data.put("type", type);

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();

			// filter
			Query<Card> query = datastore.createQuery(Card.class).filter(
					"isDeleted", false);

			if (valid(old)) {
				query.filter("createDate < ", Long.valueOf(old));
			}

			if (valid(young)) {
				query.filter("createDate > ", Long.valueOf(young));
			}

			// sort
			if ("view".equals(sorted)) {
				query.order("-view, -createDate");

			} else if ("comment".equals(sorted)) {
				query.order("-likes, -createDate");

			} else if ("footprints".equals(sorted)) {
				List<View> views = Helper.getViewsInfoByID(userId);
				List<String> cardIds = new ArrayList<>();
				if (valid(views)) {
					for (View view : views) {
						cardIds.add(view.getCardId());
					}
					query.filter("cardId in ", cardIds);
				} else {
					data.put("array", cards.toArray(new Card[0]));
					writeToJSON(os, data, callback);
					return;
				}
			} else if ("recommend".equals(sorted)) {
				Set<String> recommends = new RecommendEngine()
						.getRecommendCards(userId);
				if (valid(recommends)) {
					query.filter("cardId in ", recommends);
					query.order("-createDate");
				} else {
					data.put("array", cards.toArray(new Card[0]));
					writeToJSON(os, data, callback);
					return;
				}
			} else if ("mine".equals(sorted)) {
				query.filter("userId = ", userId);
				query.order("-createDate");

			} else if (valid(otherUserId)) {
				query.filter("userId = ", otherUserId);
				query.order("-createDate");

			} else {
				query.order("-createDate");
			}

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
			for (Card card : cards) {
				User user = Helper.getUserById(card.getUserId());
				if (user != null) {
					card.setUserIcon(user.getImagePath());
					card.setUserName(user.getUserName());
				}
			}

		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
			throw e;
		}

		data.put("array", cards.toArray(new Card[0]));
		writeToJSON(os, data, callback);

	}

	final <T> boolean valid(Collection<T> value) {
		return (value != null) && !value.isEmpty();
	}

	final boolean valid(String value) {
		return (value != null) && !value.isEmpty();
	}

	final void writeToJSON(OutputStream os, Map<String, Object> data,
			String callback) throws IOException {
		try (OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
			Gson gson = new Gson();
			String out = gson.toJson(data);
			osw.write(callback + "(" + out + ")");
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
