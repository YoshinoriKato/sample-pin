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
import com.samplepin.Card;
import com.samplepin.Folder;
import com.samplepin.User;
import com.samplepin.View;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

public class CardAjax {

	void ajax(OutputStream os, String otherUserId, String sorted,
			String offset, String limit, String callback, String old,
			String young, String type, String userId, String cardId,
			String select, String folderId) throws IOException {

		List<Card> cards = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		type = valid(type) ? type : "card";
		data.put("type", type);
		data.put("userId", userId);
		data.put("select", select);

		boolean alreadyRead = false;
		int _offset = valid(offset) ? Integer.valueOf(offset) : 0;
		int _limit = valid(limit) ? Integer.valueOf(limit) : 0;

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();

			// filter
			Query<Card> query = datastore.createQuery(Card.class).filter(
					"isDeleted", false);

			if (valid(old)) {
				query.filter("updateDate < ", Long.valueOf(old));
			}

			if (valid(young)) {
				query.filter("updateDate > ", Long.valueOf(young));
			}


			// sort
			if ("view".equals(sorted)) {
				query.order("-view, -updateDate");

			} else if ("comment".equals(sorted)) {
				query.order("-likes, -updateDate");

			} else if ("footprints".equals(sorted)) {
				List<View> views = Helper.getViewsInfoByID(userId, _offset,
						_limit);
				if (valid(views)) {
					for (View view : views) {
						Card card = Helper.getCardByID(view.getCardId());
						if (card != null) {
							cards.add(card);
						}
					}
					alreadyRead = true;
				}
				alreadyRead = true;
			} else if ("recommend".equals(sorted)) {
				Set<String> recommends = new RecommendEngine()
						.getRecommendCards(userId);
				if (valid(recommends)) {
					query.filter("cardId in ", recommends);
					query.order("-updateDate");
				} else {
					alreadyRead = true;
				}
			} else if ("mine".equals(sorted)) {
				query.filter("userId = ", userId);
				query.order("-updateDate");

			} else if ("folder".equals(sorted)) {
				query.order("-updateDate");

				if (valid(folderId)) {
					Folder folder = mongo.createQuery(Folder.class)
							.filter("folderId = ", folderId)
							.filter("isDeleted", false).get();
					if (folder != null) {
						query.filter("cardId in ", folder.getCards().split(","));
					} else {
						query.filter("cardId = ", "XXX");
					}
				}

			} else if (valid(otherUserId)) {
				query.filter("userId = ", otherUserId);
				query.order("-updateDate");

			} else {
				query.order("-updateDate");
			}

			// option
			if (_limit != 0) {
				query.limit(_limit);
			} else {
				query.limit(1000);
			}

			if (_offset != 0) {
				query.offset(_offset);
			}

			if (!alreadyRead) {
				cards = query.asList();
			}

			for (Card card : cards) {
				User user = Helper.getUserById(card.getUserId());
				if (user != null) {
					Helper.setUserInfoToComment(card, user);
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
