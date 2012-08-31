package com.samplepin.servlet.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.common.ACMongo;

@WebServlet(urlPatterns = { "/util1.do" })
public class Utility1 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5036134172073312539L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			Query<Card> query0 = mongo.createQuery(Card.class).filter(
					"isDeleted", false);
			for (Card card : query0.asList()) {
				if (card.getImagePath().startsWith("img/flag/")) {
					card.setIsDeleted(true);
					mongo.save(card);
				}
			}
			Query<Card> query1 = mongo.createQuery(Card.class).filter(
					"isDeleted", true);
			for (Card card : query1.asList()) {
				Query<Comment> query2 = mongo.createQuery(Comment.class)
						.filter("cardId", card.getCardId());
				List<Comment> comments = query2.asList();
				for (Comment comment : comments) {
					comment.setIsDeleted(true);
				}
				mongo.save(comments);
			}
		}
		response.sendRedirect("home.jsp");
	}
}
