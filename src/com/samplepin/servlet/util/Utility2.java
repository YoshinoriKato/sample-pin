package com.samplepin.servlet.util;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Card;
import com.samplepin.Helper;

@WebServlet(urlPatterns = { "/util2.do" })
public class Utility2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6147766823673475622L;

	public static void main(String[] args) {
		try (ACMongo mongo = new ACMongo()) {
			Query<Card> query = mongo.createQuery(Card.class);
			List<Card> cards = query.asList();
			for (Card card : cards) {
				String caption = card.getCaption();

				if (caption.contains("Keywords: ")) {
					StringBuilder builder = new StringBuilder();
					String[] lines = caption.split(Helper.LS);
					for (String string : lines) {
						System.out.println(string);
						if (string.startsWith("Keywords: ")) {
							String token = string.substring("Keywords: "
									.length());
							card.setKeywords(token);
						} else if (string.startsWith("URL: ")) {
							String token = string.substring("URL: ".length());
							card.setSite(token);
						} else {
							if (builder.length() > 0) {
								builder.append(Helper.LS);
							}
							builder.append(string);
						}
					}
					if (builder.length() == 0) {
						builder.append("画像検索。");
					}
					card.setCaption(builder.toString());
					System.out.println(">> " + card.getCaption());
					System.out.println(">> " + card.getKeywords());
					System.out.println(">> " + card.getSite());
					mongo.save(card);
				} else if (card.getCaption().isEmpty()) {
					card.setCaption("画像検索。");
					mongo.save(card);
				}
			}

		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		main(null);
		res.sendRedirect("index.jsp");
	}
}
