package com.samplepin.servlet;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
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
			Query<Card> query = datastore.createQuery(Card.class).limit(50);
			if (sorted == null) {
				query.order("-createDate");
			} else if ("view".equals(sorted)) {
				query.order("-view");
			} else if ("comment".equals(sorted)) {
				query.order("-likes");
			} else {
				query.order("-createDate");
			}

			cards = query.asList();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
			log(e.getMessage());
			throw e;
		}
		writeToJSON(resp, cards);
	}

	final void writeToJSON(HttpServletResponse resp, List<Card> cards)
			throws IOException {
		try (OutputStreamWriter osw = new OutputStreamWriter(
				resp.getOutputStream(), "UTF-8")) {
			Gson gson = new Gson();
			String out = gson.toJson(cards);
			osw.write(out);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			throw e;
		}
	}
}
