package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.Card;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/update-card.do" })
public class UpdateCardServlet extends HttpServlet {

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
		String parentId = req.getParameter("parentId");

		try (ACMongo mongo = new ACMongo()) {
			if (Helper.valid(parentId)) {
				Card card = Helper.getCardByID(cardId);

				if (!"self".equals(parentId)) {
					Card parent = Helper.getCardByID(parentId);
					if (Helper.valid(card) && Helper.valid(parent)) {
						card.setParentId(parent.getCardId());
						mongo.save(card);
					}
				} else {
					card.setParentId("self");
					mongo.save(card);
				}
				ActivityLogger.log(req, this.getClass(), parentId);
			}
		} catch (Exception e) {
			log(e.getMessage());
			req.setAttribute("error", e);
		}

		resp.sendRedirect("card-comment.jsp?cardId=" + cardId + "&type=comment");
		return;
	}
}
