package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Card;
import com.samplepin.Comment;

@WebServlet(urlPatterns = { "/comment.do" })
public class CommentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5426777241563315344L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		try {
			String cardId = req.getParameter("cardId");
			String comment = req.getParameter("comment");
			HttpSession session = req.getSession();
			String userId = (String) session.getAttribute("userId");

			log(userId + " > " + comment);

			if (userId != null) {
				saveComment(new Comment(userId, cardId, comment,
						System.currentTimeMillis()));
			}
			resp.sendRedirect("card.jsp?cardId=" + cardId);
		} catch (Exception e) {
			log(e.getMessage());
			req.setAttribute("error", e);
			resp.sendRedirect("login.jsp");
		}
	}

	final void saveComment(Comment comment) throws UnknownHostException,
			MongoException {

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Comment> query = datastore.createQuery(Comment.class)
					.filter("userId = ", comment.getUserId())
					.filter("comment = ", comment.getComment());
			if (query.countAll() == 0) {
				datastore.save(comment);
				Query<Card> query2 = datastore.createQuery(Card.class).filter(
						"cardId", comment.getCardId());
				Card card = query2.get();
				card.setLikes(card.getLikes() + 1);
				datastore.save(card);
			}
		}
	}
}
