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

	static public void fillValues(List<Card> cards) {
		for (Card card : cards) {
			User user = Helper.getUserById(card.getUserId());
			if (user != null) {
				Helper.setUserInfoToComment(card, user);
			}
			if (!valid(card.getTitle())) {
				String caption = Helper.getOmitedString(card.getCaption(), 40);
				card.setTitle(caption);
			}
		}
	}

	static final <T> boolean valid(Collection<T> value) {
		return (value != null) && !value.isEmpty();
	}

	static final boolean valid(String value) {
		return (value != null) && !value.isEmpty();
	}

	static final void writeToJSON(OutputStream os, Map<String, Object> data,
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

	void ajax(OutputStream os, AjaxInfo info) throws IOException {

		List<Card> cards = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		info.type = valid(info.type) ? info.type : "card";
		data.put("type", info.type);
		data.put("userId", info.userId);
		data.put("select", info.select);

		boolean alreadyRead = false;
		int _offset = valid(info.offset) ? Integer.valueOf(info.offset) : 0;
		int _limit = valid(info.limit) ? Integer.valueOf(info.limit) : 0;

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();

			// filter
			Query<Card> query = datastore.createQuery(Card.class).filter(
					"isDeleted", false);
			query.or(query.criteria("accessLevel").equal(0),
					query.criteria("userId").equal(info.userId));

			if (valid(info.old)) {
				query.filter("updateDate < ", Long.valueOf(info.old));
			}

			if (valid(info.young)) {
				query.filter("updateDate > ", Long.valueOf(info.young));
			}

			// sort
			if ("view".equals(info.sorted)) {
				query.order("-view, -updateDate");

			} else if ("comment".equals(info.sorted)) {
				query.order("-likes, -updateDate");

			} else if ("footprints".equals(info.sorted)) {
				List<View> views = Helper.getViewsInfoByID(info.userId,
						_offset, _limit);
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
			} else if ("recommend".equals(info.sorted)) {
				Set<String> recommends = new RecommendEngine()
						.getRecommendCards(info.userId);
				if (valid(recommends)) {
					query.filter("cardId in ", recommends);
					query.order("-updateDate");
				} else {
					alreadyRead = true;
				}
			} else if ("mine".equals(info.sorted)) {
				query.filter("userId = ", info.userId);
				query.order("-updateDate");

			} else if ("folder".equals(info.sorted)) {
				query.order("-updateDate");

				if (valid(info.folderId)) {
					Folder folder = mongo.createQuery(Folder.class)
							.filter("folderId = ", info.folderId)
							.filter("isDeleted", false).get();
					if (folder != null) {
						query.filter("cardId in ", folder.getCards().split(","));
					} else {
						query.filter("cardId = ", "XXX");
					}
				}

			} else if (valid(info.otherUserId)) {
				query.filter("userId = ", info.otherUserId);
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

			fillValues(cards);

		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
			throw e;
		}

		data.put("array", cards.toArray(new Card[0]));
		writeToJSON(os, data, info.callback);

	}

}
