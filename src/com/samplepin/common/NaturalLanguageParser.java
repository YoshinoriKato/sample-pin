package com.samplepin.common;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.KeywordsAndCard;

public class NaturalLanguageParser {

	public static Set<String> cardIds(String dic, String text)
			throws IOException {
		Set<String> cardIds = new HashSet<>();
		try (ACMongo mongo = new ACMongo()) {
			Set<String> keywords = keywords(dic, text);
			if ((keywords != null) && !keywords.isEmpty()) {
				Query<KeywordsAndCard> query = mongo.createQuery(
						KeywordsAndCard.class)
						.filter("keywords all ", keywords);
				for (KeywordsAndCard kac : query.asList()) {
					if (!cardIds.contains(kac.getCardId())) {
						cardIds.add(kac.getCardId());
					}
				}
			}
		}
		return cardIds;
	}

	public static Set<String> keywords(String dic, String text)
			throws IOException {
		Set<String> keywords = new HashSet<>();
		Tagger tagger = new Tagger(dic);
		for (String string : text.split("( |　|\t|\r\n|\r|\n|\f|\b)")) {
			parse(tagger, string, keywords);
		}
		return keywords;
	}

	public static void makeIndex(HttpServletRequest req, String cardId)
			throws IOException {
		String realPath = req.getServletContext().getRealPath("ipadic");
		Tagger tagger = new Tagger(realPath);
		makeIndex(tagger, cardId);
	}

	public static void makeIndex(Tagger tagger, String cardId)
			throws IOException {
		try (ACMongo mongo = new ACMongo()) {

			// cards
			Query<Card> query0 = mongo.createQuery(Card.class);
			if ((cardId != null) && !cardId.isEmpty()) {
				query0.filter("cardId = ", cardId);
			}
			query0.order("-createDate");
			List<Card> cards = query0.asList();

			for (Card card : cards) {
				Set<String> keywords = new HashSet<>();
				parse(tagger, card.getCaption() + " " + card.getKeywords(),
						keywords);

				// comments
				Query<Comment> query1 = mongo.createQuery(Comment.class)
						.filter("cardId = ", card.getCardId())
						.order("-createDate");
				List<Comment> comments = query1.asList();
				for (Comment comment : comments) {
					parse(tagger, comment.getCaption(), keywords);
				}

				// unique
				Query<KeywordsAndCard> query2 = mongo.createQuery(
						KeywordsAndCard.class).filter("cardId = ",
						card.getCardId());
				KeywordsAndCard kac = query2.get();
				if (kac == null) {
					kac = new KeywordsAndCard(null, card.getCardId());
				}
				kac.setKeywords(keywords.toArray(new String[0]));
				mongo.save(kac);
			}
		}
	}

	public static void parse(Tagger tagger, String text, Set<String> keywords) {
		List<Morpheme> list = tagger.parse(text);
		for (Morpheme morph : list) {
			String[] token = morph.feature.split(",");
			if (token.length >= 9) {
				String keyword = token[8].toLowerCase();
				if (!keywords.contains(keyword)) {
					keywords.add(keyword);
				}
			}
		}
	}
}
