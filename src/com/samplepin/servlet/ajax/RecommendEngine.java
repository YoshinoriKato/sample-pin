package com.samplepin.servlet.ajax;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Recommend;
import com.samplepin.View;

class RecommendEngine {
	Comparator<View> SCORE = new Comparator<View>() {

		@Override
		public int compare(View o1, View o2) {
			return o1.score().compareTo(o2.score());
		}

	};

	@SuppressWarnings("unchecked")
	final Set<String> getCache(String userId) throws UnknownHostException,
			MongoException {
		try (ACMongo mongo = new ACMongo()) {
			Query<Recommend> query = mongo
					.createQuery(Recommend.class)
					.filter("userId = ", userId)
					.filter("createDate < ",
							System.currentTimeMillis() - (1000 * 60 * 60));
			Recommend recommend = query.get();
			if (recommend == null) {
				return null;
			}
			Gson gson = new Gson();
			return gson.fromJson(recommend.getRecommendJSON(), Set.class);
		}
	}

	final Set<String> getOthersByViewsAndUserID(List<View> views, String userId)
			throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			Set<String> others = new HashSet<>();
			for (View view : views) {
				Query<View> query = mongo.createQuery(View.class)
						.filter("userId != ", userId)
						.filter("cardId = ", view.getCardId())
						.filter("isDeleted", false).order("-comments")
						.order("-times").limit(10);
				List<View> result = query.asList();
				for (View view2 : result) {
					others.add(view2.getUserId());
				}
			}
			return others;
		}
	}

	final List<View> getOthersViewsByUserIDs(Set<String> userIds)
			throws IOException {
		List<View> result = new ArrayList<View>();
		if (!userIds.isEmpty()) {
			try (ACMongo mongo = new ACMongo()) {
				Query<View> query = mongo.createQuery(View.class)
						.filter("userId in ", userIds)
						.filter("isDeleted", false).order("-comments")
						.order("-times").limit(1000);
				result = query.asList();
				result = result != null ? result : new ArrayList<View>();
				Collections.sort(result, this.SCORE);
			}
		}
		return result;

	}

	final Set<String> getRecommendCards(String userId) throws IOException {
		Set<String> recommends = getCache(userId);
		if (recommends != null) {
			return recommends;
		}
		List<View> views = getViewsByUserID(userId);
		Set<String> others = getOthersByViewsAndUserID(views, userId);
		List<View> othersViews = getOthersViewsByUserIDs(others);
		Set<String> ids = new HashSet<>();
		for (View view : views) {
			ids.add(view.getCardId());
		}
		recommends = getRecommends(othersViews, ids);
		try (ACMongo mongo = new ACMongo()) {
			Gson gson = new Gson();
			mongo.save(new Recommend(userId, gson.toJson(recommends)));
		}
		return recommends;
	}

	final Set<String> getRecommends(List<View> othersViews, Set<String> ids) {
		Set<String> recommends = new HashSet<>();
		for (View view : othersViews) {
			if (!ids.contains(view.getCardId())) {
				recommends.add(view.getCardId());
				if (recommends.size() >= 100) {
					break;
				}
			}
		}
		return recommends;
	}

	final List<View> getViewsByUserID(String userId) throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			Query<View> query = mongo.createQuery(View.class)
					.filter("userId = ", userId).filter("isDeleted", false)
					.order("-visitedDate").limit(10);
			List<View> views = query.asList();
			Collections.sort(views, this.SCORE);
			return views;
		}
	}
}