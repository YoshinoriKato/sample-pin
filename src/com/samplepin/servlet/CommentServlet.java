package com.samplepin.servlet;

import static com.samplepin.common.Helper.valid;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.TwitterException;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;
import com.samplepin.nl.NaturalLanguageParser;
import com.samplepin.servlet.oauth.twitter.TwitterService;

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
		String tweet = req.getParameter("tweet");
		String anonymous = req.getParameter("anonymous");

		try (ACMongo mongo = new ACMongo()) {
			String userId = Helper.getUserId(req);

			log(userId + " > " + comment);

			if ((userId != null) && (comment != null) && !comment.isEmpty()) {
				saveComment(new Comment(userId, cardId, comment,
						System.currentTimeMillis(), isAnonymous(anonymous)));

				tweet(cardId, userId, comment, tweet);

				NaturalLanguageParser.makeIndex(req, cardId);

				ActivityLogger.log(req, this.getClass(), comment);

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

	final boolean isAnonymous(String anonymous) {
		return valid(anonymous) && "on".equals(anonymous);
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

	final void tweet(String cardId, String userId, String comment, String tweet)
			throws IOException, MongoException, TwitterException {
		try {
			TwitterService service = new TwitterService();

			Card card = Helper.getCardByID(cardId);
			String keywords = card != null ? card.getKeywords() : "";
			keywords = valid(keywords) ? "[" + keywords + "]" : "";
			String message = comment + Helper.LS + keywords + Helper.LS
					+ new ShortCutServlet().toShortCut(cardId);

			if (valid(tweet) && "on".equals(tweet)) {
				service.tweet("MSG: " + message);
				service.tweet(userId, message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
