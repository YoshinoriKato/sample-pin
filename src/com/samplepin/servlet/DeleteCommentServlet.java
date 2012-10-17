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

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;
import com.samplepin.nl.NaturalLanguageParser;

@WebServlet(urlPatterns = { "/delete-comment.do" })
public class DeleteCommentServlet extends HttpServlet {

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
		String fUserId = req.getParameter("userId");
		String createDate = req.getParameter("createDate");

		try (ACMongo mongo = new ACMongo()) {
			String userId = Helper.getUserId(req);

			if (valid(userId) && valid(fUserId) && valid(cardId)
					&& valid(createDate) && userId.equals(fUserId)) {
				saveComment(userId, cardId, createDate);

				NaturalLanguageParser.makeIndex(req, cardId);

				ActivityLogger.log(req, this.getClass(), userId);

				resp.sendRedirect("card-comment.jsp?cardId=" + cardId);
				return;
			}

		} catch (Exception e) {
			log(e.getMessage());
			req.setAttribute("error", e);
		}

		req.setAttribute("message", "Not delete comment.");
		RequestDispatcher dispathcer = req
				.getRequestDispatcher("card-comment.jsp?cardId=" + cardId);
		dispathcer.forward(req, resp);
	}

	final void saveComment(String userId, String cardId, String createDate)
			throws UnknownHostException, MongoException {

		try (ACMongo mongo = new ACMongo()) {
			Query<Comment> query0 = mongo.createQuery(Comment.class)
					.filter("cardId = ", cardId).filter("userId = ", userId)
					.filter("createDate = ", Long.valueOf(createDate))
					.filter("isDeleted", false);

			if ((query0.countAll() != 0)) {
				Card card = Helper.getCardByID(cardId);
				card.setLikes(card.getLikes() - 1);
				mongo.save(card);

				Comment comment = query0.get();
				comment.setIsDeleted(true);
				mongo.save(comment);
			}
		}
	}
}
