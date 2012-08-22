package com.samplepin.servlet.util;

import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Card;

public class Utility2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6147766823673475622L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) {

	}

	public static void main(String[] args) {
		try (ACMongo mongo = new ACMongo()) {
			Query<Card> query = mongo.createQuery(Card.class);
			List<Card> cards = query.asList();
			for (Card card : cards) {
				String caption = card.getCaption();

				if (caption.contains("Keywords:")) {
					System.out.println(caption);
					StringBuilder builder = new StringBuilder();
					String[] lines = caption.split("\r\n");
					for (String string : lines) {
						builder.append(string);
					}
				}
			}

		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
	}
}
