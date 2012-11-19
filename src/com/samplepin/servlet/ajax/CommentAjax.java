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
	void ajax(OutputStream os, AjaxInfo info) throws IOException {

		List<Comment> cards = new ArrayList<>();
		Map<String, Object> data = new HashMap<>();
		info.type = valid(info.type) ? info.type : "card";
		data.put("type", info.type);

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();

			// filter
			Query<Comment> query = datastore.createQuery(Comment.class).filter(
					"isDeleted", false);

			if (valid(info.cardId)) {
				query.filter("cardId = ", info.cardId);
			}

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
			query.order("-createDate");

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
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
			throw e;
		}

		for (Comment comment : cards) {
			User user = Helper.getUserById(comment.getUserId());
			if (user != null) {
				Helper.setUserInfoToComment(comment, user);
				if (valid(info.otherUserId)) {
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
		data.put("userId", info.userId);
		writeToJSON(os, data, info.callback);
	}

}
