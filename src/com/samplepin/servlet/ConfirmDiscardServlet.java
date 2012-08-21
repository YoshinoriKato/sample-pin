package com.samplepin.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.query.Query;
import com.samplepin.ACMongo;
import com.samplepin.Card;
import com.samplepin.Comment;

@WebServlet(urlPatterns = "/confirm-discard.do")
public class ConfirmDiscardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6534228482284422460L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("userId");
		String cardId = req.getParameter("cardId");

		if ((userId != null) && (cardId != null)) {
			try (ACMongo mongo = new ACMongo()) {
				Query<Card> query = mongo.createQuery(Card.class)
						.filter("userId", userId).filter("cardId", cardId)
						.filter("isDeleted", false);
				Card card = query.get();
				if (card != null) {
					card.setIsDeleted(true);
					mongo.save(card);
					Query<Comment> query2 = mongo.createQuery(Comment.class)
							.filter("cardId", card.getCardId());
					List<Comment> comments = query2.asList();
					for (Comment comment : comments) {
						comment.setIsDeleted(true);
					}
					mongo.save(comments);
				}
			}
			log("discard end.");
			resp.sendRedirect("index.jsp");
			return;

		} else {
			log("make failed.");
			req.setAttribute("message", "Please, retry.");
			RequestDispatcher dispathcer = req
					.getRequestDispatcher("confirm-discard.jsp");
			dispathcer.forward(req, resp);
			return;
		}
	}
}
