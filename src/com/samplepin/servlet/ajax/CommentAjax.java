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
import com.samplepin.ACMongo;
import com.samplepin.Comment;
import com.samplepin.Helper;
import com.samplepin.User;

public class CommentAjax extends CardAjax {

	@Override
	void ajax(OutputStream os, String otherUserId, String sorted,
			String offset, String limit, String callback, String old,
			String young, String type, String userId, String cardId)
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

			if (valid(userId)) {
				query.filter("userId = ", userId);
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
				comment.setImagePath(user.getImagePath());
				comment.setCardId(user.getUserName());
			}
		}

		data.put("array", cards.toArray(new Comment[0]));
		writeToJSON(os, data, callback);
	}

}
