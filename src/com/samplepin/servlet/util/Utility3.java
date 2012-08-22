package com.samplepin.servlet.util;

import static com.samplepin.Helper.valid;

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
import com.samplepin.KeyAndImage;

@WebServlet(urlPatterns = { "/util3.do" })
public class Utility3 extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6147766823673475622L;

	public static void main(String[] args) {
		try (ACMongo mongo = new ACMongo()) {
			Query<Card> query = mongo.createQuery(Card.class);
			List<Card> cards = query.asList();
			for (Card card : cards) {
				if (valid(card.getKeywords())) {
					for (String key : card.getKeywords().split(
							"( |	|　|\r\n|\n|\r)")) {
						Query<KeyAndImage> query2 = mongo
								.createQuery(KeyAndImage.class)
								.filter("key", key)
								.filter("imagePath", card.getImagePath());
						if (query2.countAll() == 0) {
							System.out.println(key);
							System.out.println(card.getImagePath());
							mongo.save(new KeyAndImage(key, card.getImagePath()));
						}
					}
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
