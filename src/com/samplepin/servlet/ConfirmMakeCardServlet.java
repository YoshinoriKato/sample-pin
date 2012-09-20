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

import twitter4j.TwitterException;

import com.google.code.morphia.Datastore;
import com.mongodb.MongoException;
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
	private static final long serialVersionUID = 6534228482284422460L;

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
					tweet(card, userId, tweet);

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

	final String makeMessage(Card card) throws IOException {
		StringBuilder builder = new StringBuilder();

		builder.append(card.getCaption()).append(Helper.LS);

		String keywords = card.getKeywords();
		if (valid(keywords)) {
			keywords = valid(keywords) ? "[" + keywords + "]" : "";
			builder.append(keywords).append(Helper.LS);
		}

		builder.append(new ShortCutServlet().toShortCut(card.getCardId()))
				.append(Helper.LS);

		// if (Helper.valid(card.getImagePath())) {
		// builder.append(
		// new LinkImageServlet().toShortCut(card.getImagePath()))
		// .append(Helper.LS);
		// }

		return builder.toString();
	}

	final void tweet(Card card, String userId, String tweet)
			throws IOException, MongoException, TwitterException {
		try {
			TwitterService service = new TwitterService();

			String message = makeMessage(card);

			if (valid(tweet) && "on".equals(tweet)) {
				service.tweet(userId, message);
			}

			service.tweet("ADD: " + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
