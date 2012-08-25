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

import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.KeywordAndCard;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;
import com.samplepin.nl.NaturalLanguageParser;
import com.samplepin.servlet.oauth.TwitterService;

@WebServlet(urlPatterns = "/confirm-make-card.do")
public class ConfirmMakeCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6534228482284422460L;

	public static void register(ACMongo mongo, HttpServletRequest req,
			String cardId, String text) throws Exception {
		// 辞書ディレクトリを引数で指定
		String dic = req.getServletContext().getRealPath("ipadic");
		Tagger tagger = new Tagger(dic);

		List<Morpheme> list = tagger.parse(text);
		for (Morpheme morph : list) {
			String keyword = morph.surface.toLowerCase();
			String part = morph.feature
					.substring(0, morph.feature.indexOf(","));
			// register
			Query<KeywordAndCard> query = mongo
					.createQuery(KeywordAndCard.class).filter("cardId", cardId)
					.filter("keyword = ", keyword.toLowerCase())
					.filter("part = ", part);
			if (query.countAll() == 0) {
				mongo.save(new KeywordAndCard(keyword, cardId, part));
			}
		}
	}

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

					NaturalLanguageParser.makeIndex(req, card.getCardId());

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
