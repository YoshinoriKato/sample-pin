package com.samplepin.nl;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.KeywordsAndCard;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

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

	static List<Card> cards(ACMongo mongo, String cardId) {
		Query<Card> query0 = mongo.createQuery(Card.class);
		if ((cardId != null) && !cardId.isEmpty()) {
			query0.filter("cardId = ", cardId);
		}
		query0.order("-createDate");
		return query0.asList();
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
			List<Card> cards = cards(mongo, cardId);

			for (Card card : cards) {
				Set<String> parsed = new HashSet<>();
				IndexingCallbacker callbacker = new IndexingCallbacker(tagger,
						parsed);
				parseCard(card, callbacker);

				// comments
				parseComment(mongo, card, callbacker);

				// register
				KeywordsAndCard kac = newKeyword(mongo, card, parsed);
				mongo.save(kac);
			}
		}
	}

	public static void makeTags(HttpServletRequest req) throws IOException {
		String realPath = req.getServletContext().getRealPath("ipadic");
		Tagger tagger = new Tagger(realPath);

	}

	static KeywordsAndCard newKeyword(ACMongo mongo, Card card,
			Set<String> parsed) {
		Query<KeywordsAndCard> query2 = mongo
				.createQuery(KeywordsAndCard.class).filter("cardId = ",
						card.getCardId());
		KeywordsAndCard kac = query2.get();
		if (kac == null) {
			kac = new KeywordsAndCard(null, card.getCardId());
		}
		kac.setKeywords(parsed.toArray(new String[0]));
		return kac;
	}

	public static void parse(Tagger tagger, String text,
			Map<String, AtomicInteger> tags) {
		for (Morpheme morph : tagger.parse(text)) {
			String[] token = morph.feature.split(",");
			String keyword = morph.surface.toLowerCase();

			if (valid(keyword) && "名詞".equals(token[0])) {
				if (!tags.containsKey(keyword)) {
					tags.put(keyword, new AtomicInteger(0));
				}
				tags.get(keyword).incrementAndGet();
			}
		}
	}

	public static void parse(Tagger tagger, String text, Set<String> keywords) {
		List<Morpheme> list = tagger.parse(text);
		for (Morpheme morph : list) {
			String[] token = morph.feature.split(",");
			String keyword = null;
			int index = 9;
			if (token.length >= index) {
				keyword = token[index - 1];
			}
			if (!valid(keyword) && (token.length >= --index)) {
				keyword = token[index - 1];
			}
			if (!valid(keyword)) {
				keyword = morph.surface;
			}
			keyword = keyword.toLowerCase();

			if (valid(keyword) && !keywords.contains(keyword)) {
				keywords.add(keyword);
			}
		}
	}

	static void parseCard(Card card, ParserCallback callbacker) {
		String keywords = card.getKeywords();
		keywords = keywords != null ? keywords : "";
		String url = card.getSite();
		url = url != null ? url : "";
		callbacker.parse(card.getCaption() + Helper.LS + keywords + Helper.LS
				+ url);
	}

	static void parseComment(ACMongo mongo, Card card, ParserCallback callbacker) {
		Query<Comment> query1 = mongo.createQuery(Comment.class)
				.filter("cardId = ", card.getCardId()).order("-createDate");
		List<Comment> comments = query1.asList();
		for (Comment comment : comments) {
			callbacker.parse(comment.getCaption());
		}
	}

	static boolean valid(String value) {
		return (value != null) && !value.isEmpty() && !"*".equals(value)
				&& !".".equals(value);
	}
}
