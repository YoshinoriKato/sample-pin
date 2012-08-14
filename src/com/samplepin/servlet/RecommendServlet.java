package com.samplepin.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.samplepin.ACMongo;
import com.samplepin.Card;
import com.samplepin.View;

@WebServlet(urlPatterns = { "/recommend.do" })
public class RecommendServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6870644520561478166L;
	Comparator<View> SCORE = new Comparator<View>() {

		@Override
		public int compare(View o1, View o2) {
			return o1.score().compareTo(o2.score());
		}

	};

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("userId");

		List<View> views = getViewsByUserID(userId);
		log("User footprints: " + views.size());

		Set<String> others = getOthersByViewsAndUserID(views, userId);
		log("Nearly Users: " + others.size());

		List<View> othersViews = getOthersViewsByUserIDs(others);
		log("Others footprints: " + othersViews.size());

		Set<String> ids = new HashSet<>();
		for (View view : views) {
			ids.add(view.getCardId());
		}

		Set<String> recommends = new HashSet<>();
		for (View view : othersViews) {
			if (!ids.contains(view.getCardId())) {
				recommends.add(view.getCardId());
				if (recommends.size() >= 100) {
					break;
				}
			}
		}
		log("Recommends: " + recommends.size());

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Card> query = datastore.createQuery(Card.class).filter(
					"cardId in ", recommends);
			List<Card> cards = query.asList();
			new CardServlet().writeToJSON(resp, cards);
		}
	}

	final Set<String> getOthersByViewsAndUserID(List<View> views, String userId)
			throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Set<String> others = new HashSet<>();
			for (View view : views) {
				Query<View> query = datastore.createQuery(View.class)
						.filter("userId != ", userId)
						.filter("cardId = ", view.getCardId())
						.order("-comments").order("-times").limit(10);
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
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<View> query = datastore.createQuery(View.class)
					.filter("userId in ", userIds).order("-comments")
					.order("-times").limit(1000);
			List<View> result = query.asList();
			Collections.sort(result, this.SCORE);
			return result;
		}

	}

	final List<View> getViewsByUserID(String userId) throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();

			Query<View> query = datastore.createQuery(View.class)
					.filter("userId = ", userId).order("-visitedDate")
					.limit(10);
			List<View> views = query.asList();
			Collections.sort(views, this.SCORE);
			return views;
		}
	}
}
