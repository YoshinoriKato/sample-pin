package com.samplepin.servlet.ajax;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.samplepin.Like;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/like.do" })
public class AjaxLikeServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3402207306957799959L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String cardId = req.getParameter("cardId");
		String userId = req.getParameter("userId");
		String action = req.getParameter("action");
		String callback = req.getParameter("callback");

		try (ACMongo mongo = new ACMongo()) {

			if (Helper.valid(cardId) && Helper.valid(userId)
					&& Helper.valid(action)) {

				Like like = mongo.createQuery(Like.class)
						.filter("userId", userId).filter("cardId", cardId)
						.get();

				if (like == null) {
					like = new Like(cardId, userId);
				} else {
					like.setUpdateDate(System.currentTimeMillis());
				}

				if ("up".equals(action)) {
					like.setIsDeleted(false);
					mongo.save(like);

				} else if ("down".equals(action)) {
					like.setIsDeleted(true);
					mongo.save(like);
				}

				ActivityLogger.log(req, this.getClass(), like);

				Query<Like> query = mongo.createQuery(Like.class)
						.filter("isDeleted", false).filter("cardId", cardId);
				long likes = query.countAll();
				Like self = query.filter("userId", userId).get();

				Map<String, Object> ret = new HashMap<>();
				ret.put("cardId", cardId);
				ret.put("updateDate", like.getUpdateDate());
				ret.put("likes", likes);
				ret.put("userId", self != null);

				resp.sendRedirect("card-comment.jsp?cardId=" + cardId
						+ "&type=comment");

				CardAjax.writeToJSON(resp.getOutputStream(), ret, callback);
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
}
