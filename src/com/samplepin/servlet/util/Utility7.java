package com.samplepin.servlet.util;

import java.io.File;
import java.util.List;

import javax.servlet.ServletException;
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

public class Utility7 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1609644801230608871L;

	public static void main(String[] args) {
		try (ACMongo mongo = new ACMongo()) {
			File dir = new File("ipadic");
			dir = (dir.exists()) ? dir : new File("WebContent/ipadic");
			if (dir.exists()) {
				Tagger tagger = new Tagger(dir.getPath());
				Query<Card> query0 = mongo.createQuery(Card.class);
				List<Card> cards = query0.asList();
				for (Card card : cards) {
					register(mongo, tagger, card.getCardId(), card.getCaption()
							+ " " + card.getKeywords());
				}
				Query<Comment> query1 = mongo.createQuery(Comment.class);
				List<Comment> comments = query1.asList();
				for (Comment comment : comments) {
					register(mongo, tagger, comment.getCardId(),
							comment.getCaption());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// req.getSession().setAttribute("error", e);
			// throw new ServletException(e);
		}

		System.out.println("bye.");
	}

	public static void register(ACMongo mongo, Tagger tagger, String cardId,
			String text) throws Exception {
		// 辞書ディレクトリを引数で指定

		List<Morpheme> list = tagger.parse(text);
		for (Morpheme morph : list) {
			String keyword = morph.surface.toLowerCase();
			String part = morph.feature
					.substring(0, morph.feature.indexOf(","));
			System.out.println(keyword);
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
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
	}
}
