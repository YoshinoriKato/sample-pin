package com.samplepin.servlet;

import static com.samplepin.common.Helper.valid;

import java.io.IOException;
import java.net.UnknownHostException;

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
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;
import com.samplepin.nl.NaturalLanguageParser;
import com.samplepin.servlet.oauth.twitter.TwitterService;

@WebServlet(urlPatterns = { "/remote-post.do" })
public class RemotePostServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5426777241563315344L;

	public static final Long COMMENTS_LIMIT = 1000L;

	public static String code = "cnjakhfavjabvibaribvavaroiwhfiowjgiawjgifjwefnanvlanvjkanwknncjkanjkvna;veruonbvajbiabajkbvkjakjvnaskjbvau;bnafbnafjkbkjasbvkladsbvjknavjabkjbvaksnvjkabjajkbvjkasdnviurhbaevebkdnvkb jbfheiutyiwauyrwuhvaiho";

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String cardId = Helper.generatedIdString("R_");
		String title = req.getParameter("title");
		String caption = req.getParameter("caption");
		String code = req.getParameter("code");
		resp.setHeader("Content-Type", "text/javascript");

		try (ACMongo mongo = new ACMongo()) {
			String userId = Helper.getUserId(req);

			if ((Helper.valid(title) & Helper.valid(caption))
					&& Helper.valid(code)
					&& RemotePostServlet.code.equals(code)) {

				Card card = new Card("self", cardId, userId,
						"img/doya_news.png", "", title, caption, 0, 0,
						System.currentTimeMillis());

				saveCard(card);

				tweet(cardId, userId, title);

				NaturalLanguageParser.makeIndex(req, cardId);

				ActivityLogger.log(req, this.getClass(), title);
			}

		} catch (Exception e) {
			log(e.getMessage());
			req.setAttribute("error", e);
		}

	}

	final void saveCard(Card card) throws UnknownHostException, MongoException {

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Card> query0 = datastore.createQuery(Card.class).filter(
					"cardId = ", card.getCardId());
			Query<Card> query1 = datastore.createQuery(Card.class).filter(
					"title = ", card.getTitle());

			if ((query0.countAll() == 0) && (query1.countAll() == 0)) {
				datastore.save(card);
			}
		}
	}

	final void tweet(String cardId, String userId, String comment)
			throws IOException, MongoException, TwitterException {
		try {
			TwitterService service = new TwitterService();

			Card card = Helper.getCardByID(cardId);
			String keywords = card != null ? card.getKeywords() : "";
			keywords = valid(keywords) ? "[" + keywords + "]" : "";
			String message = Helper.LS + keywords + Helper.LS
					+ new ShortCutServlet().toShortCut(cardId);
			message = Helper.getOmitedString(comment, (130 - message.length()))
					+ message;

			service.tweet("MSG: " + message);
			service.tweet(userId, message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
