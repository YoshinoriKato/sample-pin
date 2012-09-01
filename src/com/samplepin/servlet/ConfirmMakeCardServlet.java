package com.samplepin.servlet;

import static com.samplepin.common.Helper.valid;

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
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;
import com.samplepin.nl.NaturalLanguageParser;
import com.samplepin.servlet.oauth.TwitterService;

@WebServlet(urlPatterns = "/confirm-make-card.do")
public class ConfirmMakeCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6534228482284422460L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String userId = Helper.getUserId(req);
		Card card = (Card) session.getAttribute("confirmed");
		String tweet = req.getParameter("tweet");

		if ((userId != null) && (card != null)) {
			try (ACMongo mongo = new ACMongo()) {
				Datastore datastore = mongo.createDatastore();
				datastore.save(card);

				try {
					if (valid(tweet) && "on".equals(tweet)) {
						String keywords = card.getKeywords();
						keywords = valid(keywords) ? "[" + keywords + "]" : "";
						String message = card.getCaption()
								+ Helper.LS
								+ keywords
								+ Helper.LS
								+ new ShortCutServlet().toShortCut(card
										.getCardId());
						TwitterService service = new TwitterService();
						service.tweet(userId, message);
						service.tweet("[最新]" + message);
					}

					NaturalLanguageParser.makeIndex(req, card.getCardId());

					ActivityLogger.log(req, this.getClass(), card);

				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("error", e);
					throw new ServletException(e);
				}
			}
			log("make end.");
			resp.sendRedirect("home.jsp");
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
