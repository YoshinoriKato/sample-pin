package com.samplepin.servlet.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.KeywordAndCard;
import com.samplepin.common.ACMongo;

@WebServlet(urlPatterns = { "/util7.do" })
public class Utility7 extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1609644801230608871L;

	static Set<Long>			hashcodes			= new HashSet<>();

	public static void register(ACMongo mongo, Tagger tagger, String cardId,
			String text) throws Exception {
		// 辞書ディレクトリを引数で指定

		List<Morpheme> list = tagger.parse(text);
		for (Morpheme morph : list) {
			String keyword = morph.surface.toLowerCase();
			String part = morph.feature
					.substring(0, morph.feature.indexOf(","));

			long sum = (cardId.hashCode() * 1000) + (keyword.hashCode() * 10)
					+ part.hashCode();

			// register
			if (hashcodes.contains(sum)) {
				Query<KeywordAndCard> query = mongo
						.createQuery(KeywordAndCard.class)
						.filter("cardId", cardId).filter("keyword = ", keyword)
						.filter("part = ", part);
				if (query.countAll() == 0) {
					mongo.save(new KeywordAndCard(keyword, cardId, part));
				}
			} else {
				hashcodes.add(sum);
				mongo.save(new KeywordAndCard(keyword, cardId, part));
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		try (ACMongo mongo = new ACMongo()) {
			String dic = request.getServletContext().getRealPath("ipadic");
			Tagger tagger = new Tagger(dic);
			Query<Card> query0 = mongo.createQuery(Card.class);
			List<Card> cards = query0.asList();
			for (Card card : cards) {
				log(card.getCardId());
				register(mongo, tagger, card.getCardId(), card.getCaption()
						+ " " + card.getKeywords());
			}
			Query<Comment> query1 = mongo.createQuery(Comment.class);
			List<Comment> comments = query1.asList();
			for (Comment comment : comments) {
				log(comment.getCardId());
				register(mongo, tagger, comment.getCardId(),
						comment.getCaption());
			}

		} catch (Exception e) {
			e.printStackTrace();
			request.getSession().setAttribute("error", e);
			throw new ServletException(e);
		}

		System.out.println("bye.");
	}
}
