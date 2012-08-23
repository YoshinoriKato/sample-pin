package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.Datastore;
import com.samplepin.Card;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;
import com.samplepin.servlet.oauth.TwitterService;

@WebServlet(urlPatterns = "/confirm-make-card.do")
public class ConfirmMakeCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6534228482284422460L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("userId");
		Card card = (Card) session.getAttribute("confirmed");

		if ((userId != null) && (card != null)) {
			try (ACMongo mongo = new ACMongo()) {
				Datastore datastore = mongo.createDatastore();
				datastore.save(card);

				try {
					new TwitterService().tweet(
							userId,
							card.getCaption()
									+ Helper.LS
									+ Helper.LS
									+ new ShortCutServlet().toShortCut(card
											.getCardId()));
				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("error", e);
					throw new ServletException(e);
				}
			}
			log("make end.");
			resp.sendRedirect("index.jsp");
			return;

		} else {
			log("make failed.");
			req.setAttribute("message", "Please, retry.");
			RequestDispatcher dispathcer = req
					.getRequestDispatcher("make-card.jsp");
			dispathcer.forward(req, resp);
			return;
		}
	}
}
