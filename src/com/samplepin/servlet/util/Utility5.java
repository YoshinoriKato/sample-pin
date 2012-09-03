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
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.common.ACMongo;

@WebServlet(urlPatterns = { "/util5.do" })
public class Utility5 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5036134172073312539L;

	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		try (ACMongo mongo = new ACMongo()) {
			Query<Card> query = mongo.createQuery(Card.class);
			for (Card card : query.asList()) {
				Query<Comment> query2 = mongo.createQuery(Comment.class)
						.filter("isDeleted = ", false)
						.filter("cardId = ", card.getCardId());
				List<Comment> comments = query2.asList();
				for (Comment comment : comments) {
					if ((card.getUpdateDate() == null)
							|| (card.getUpdateDate() < comment.getCreateDate())) {
						card.setUpdateDate(comment.getCreateDate());
						System.out.println(comment.getCreateDate());
					}
				}
				card.setLikes(comments.size());
				mongo.save(card);
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		main(null);
		response.sendRedirect("home.jsp");
	}
}
