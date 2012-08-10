package com.samplepin.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Card;

@WebServlet(urlPatterns = { "/xxx.do" })
public class CardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818349586239857211L;

	private static Comparator<Card> LATEST = new Comparator<Card>() {
		@Override
		public int compare(Card o1, Card o2) {
			return o2.getCreateDate().compareTo(o1.getCreateDate());
		}
	};

	private static Comparator<Card> MOST_VIEW = new Comparator<Card>() {
		@Override
		public int compare(Card o1, Card o2) {
			return o2.getView().compareTo(o1.getView());
		}
	};

	private static Comparator<Card> MOST_LIKE = new Comparator<Card>() {
		@Override
		public int compare(Card o1, Card o2) {
			return o2.getLikes().compareTo(o1.getLikes());
		}
	};

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/javascript+json; charset=UTF-8");
		String sorted = req.getParameter("sorted");

		List<Card> cards = new ArrayList<>();

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Card> query = datastore.createQuery(Card.class);
			cards = query.asList();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
			log(e.getMessage());
		}

		if (sorted == null) {
			Collections.sort(cards, LATEST);
		} else if ("view".equals(sorted)) {
			Collections.sort(cards, MOST_VIEW);
		} else if ("comment".equals(sorted)) {
			Collections.sort(cards, MOST_LIKE);
		} else {
			Collections.sort(cards, LATEST);
		}

		try (OutputStreamWriter osw = new OutputStreamWriter(
				resp.getOutputStream(), "UTF-8")) {
			Gson gson = new Gson();
			String out = gson.toJson(cards);
			log(out);
			osw.write(out);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
		}
	}
}
