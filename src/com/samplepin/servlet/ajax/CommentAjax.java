package com.samplepin.servlet.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

public class CommentAjax extends CardAjax {

	@Override
	void ajax(OutputStream os, String otherUserId, String sorted,
			String offset, String limit, String callback, String old,
			String young, String type, String userId, String cardId, String select, String folderId)
			throws IOException {

		List<Comment> cards = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		type = valid(type) ? type : "card";
		data.put("type", type);

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();

			// filter
			Query<Comment> query = datastore.createQuery(Comment.class).filter(
					"isDeleted", false);

			if (valid(cardId)) {
				query.filter("cardId = ", cardId);
			}

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
			query.order("-createDate");

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
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
			throw e;
		}

		for (Comment comment : cards) {
			User user = Helper.getUserById(comment.getUserId());
			if (user != null) {
				Helper.setUserInfoToComment(comment, user);
				if (valid(otherUserId)) {
					// own comments
					Card card = Helper.getCardByID(comment.getCardId());
					if (card != null) {
						comment.setCardIcon(card.getImagePath());
						comment.setUserIcon(null); // judge by page
					}
				}
			}
		}

		data.put("array", cards.toArray(new Comment[0]));
		data.put("userId", userId);
		writeToJSON(os, data, callback);
	}

}
