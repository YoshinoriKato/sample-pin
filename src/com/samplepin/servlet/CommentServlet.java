package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;
import com.samplepin.nl.NaturalLanguageParser;
import com.samplepin.servlet.oauth.TwitterService;

@WebServlet(urlPatterns = { "/comment.do" })
public class CommentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5426777241563315344L;

	public static final Long COMMENTS_LIMIT = 1000L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String cardId = req.getParameter("cardId");
		String comment = req.getParameter("comment");

		try (ACMongo mongo = new ACMongo()) {
			HttpSession session = req.getSession();
			String userId = (String) session.getAttribute("userId");

			log(userId + " > " + comment);

			if ((userId != null) && (comment != null) && !comment.isEmpty()) {
				saveComment(new Comment(userId, cardId, comment,
						System.currentTimeMillis()));

				new TwitterService().tweet(userId, comment + Helper.LS
						+ Helper.LS + new ShortCutServlet().toShortCut(cardId));

				NaturalLanguageParser.makeIndex(req, cardId);

				resp.sendRedirect("card-comment.jsp?cardId=" + cardId
						+ "&type=comment");
				return;
			}

		} catch (Exception e) {
			log(e.getMessage());
			req.setAttribute("error", e);
		}

		req.setAttribute("message", "Please, write a comment.");
		RequestDispatcher dispathcer = req
				.getRequestDispatcher("card-comment.jsp?cardId=" + cardId
						+ "&type=comment");
		dispathcer.forward(req, resp);
	}

	final void saveComment(Comment comment) throws UnknownHostException,
			MongoException {

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Comment> query0 = datastore.createQuery(Comment.class)
					.filter("cardId = ", comment.getCardId())
					.filter("userId = ", comment.getUserId())
					.filter("caption = ", comment.getCaption())
					.filter("isDeleted", false);
			Query<Comment> query1 = datastore.createQuery(Comment.class)
					.filter("cardId = ", comment.getCardId())
					.filter("isDeleted", false);

			if ((query0.countAll() == 0)
					&& (query1.countAll() < COMMENTS_LIMIT)) {
				datastore.save(comment);

				Query<Card> query2 = datastore.createQuery(Card.class)
						.filter("cardId", comment.getCardId())
						.filter("isDeleted", false);
				Card card = query2.get();
				card.setLikes(card.getLikes() + 1);
				card.setUpdateDate(System.currentTimeMillis());
				datastore.save(card);
			}
		}
	}
}
