package com.samplepin.nl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;

import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.KeywordsAndCard;
import com.samplepin.Tag;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;

public class NaturalLanguageParser {

	static final String	SYMBOLS		= "[ -/:-@\\[-\\`\\{-\\~]+";

	static final String	ALPHA_NUM	= "[A-Za-z0-9]";

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

	static Query<Card> cards(ACMongo mongo, String cardId) {
		Query<Card> query0 = mongo.createQuery(Card.class)
				.filter("isDeleted = ", false).order("createDate");
		if ((cardId != null) && !cardId.isEmpty()) {
			query0.filter("cardId = ", cardId);
		}
		query0.order("-createDate");
		return query0;
	}

	public static String getDictionaryPath(HttpServletRequest req) {
		return req.getServletContext().getRealPath("../dictionary/ipadic");
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
		String realPath = getDictionaryPath(req);
		Tagger tagger = new Tagger(realPath);
		makeIndex(tagger, cardId);
		ActivityLogger.log(req, NaturalLanguageParser.class, cardId);
	}

	public static void makeIndex(Tagger tagger, String cardId)
			throws IOException {
		try (ACMongo mongo = new ACMongo()) {

			// cards
			Query<Card> cards = cards(mongo, cardId);

			for (Card card : cards) {

				Set<String> parsed = new HashSet<>();
				IndexingCallbacker callbacker = new IndexingCallbacker(tagger,
						parsed);

				// cards
				parseCard(card, callbacker);

				// web
				if (valid(card.getSite())) {
					parseWebpage(card.getSite(), callbacker);
				}

				// comments
				parseComment(mongo, card, callbacker);

				// register
				mongo.save(newKeyword(mongo, card, parsed));
			}
		}
	}

	public static void makeTags(HttpServletRequest req) throws IOException {
		String realPath = getDictionaryPath(req);
		Tagger tagger = new Tagger(realPath);

		try (ACMongo mongo = new ACMongo()) {

			// cards
			Query<Card> cards = cards(mongo, null);

			Map<String, AtomicInteger> counts = new HashMap<>();

			for (Card card : cards) {

				ParserCallback callbacker = new TaggingCallbacker(tagger,
						counts);

				// cards
				parseCard(card, callbacker);

				// comments
				parseComment(mongo, card, callbacker);

			}
			// clear
			mongo.createDatastore().delete(mongo.createQuery(Tag.class));

			// register
			mongo.save(newTag(mongo, counts));
		}
		ActivityLogger.log(req, NaturalLanguageParser.class, "tag");
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

	static List<Tag> newTag(ACMongo mongo, Map<String, AtomicInteger> counts) {
		Random dice = new Random(System.nanoTime());
		List<Tag> tags = new ArrayList<>();
		for (String key : counts.keySet()) {
			try {
				if ((key.length() < 2) && key.matches("[\\w]")) {
					continue;
				}

				String[] keys = { key };
				Query<KeywordsAndCard> query0 = mongo.createQuery(
						KeywordsAndCard.class).filter("keywords all ", keys);

				long size = query0.countAll();
				int limit = (int) size;
				if (limit > 0) {
					int offset = dice.nextInt(limit) - 1;
					if (offset > 0) {
						query0.offset(offset);
					}
				}
				KeywordsAndCard kac = query0.get();

				Query<Tag> query1 = mongo.createQuery(Tag.class).filter(
						"tag = ", key);

				// image
				String imagePath = "img/no_image.png";
				if (kac != null) {
					Card card = Helper.getCardByID(kac.getCardId());
					if (card != null) {
						imagePath = card.getImagePath();
					}
				}

				Tag tag = query1.get();
				if (tag != null) {
					tag.setSize(size);
					tag.setImagePath(imagePath);
					tags.add(tag);
				} else {
					tags.add(new Tag(key, size, imagePath));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return tags;
	}

	public static void parse(Tagger tagger, CharSequence text,
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

	public static void parse(Tagger tagger, CharSequence text,
			Set<String> keywords) {
		List<Morpheme> list = tagger.parse(text);
		for (Morpheme morph : list) {
			String[] token = morph.feature.split(",");

			if (reject(morph, token[0], text)) {
				continue;
			}

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
		Query<Comment> query = mongo.createQuery(Comment.class)
				.filter("cardId = ", card.getCardId()).order("-createDate");
		for (Comment comment : query) {
			callbacker.parse(comment.getCaption());
		}
	}

	public static void parseWebpage(String urlPath, ParserCallback callback) {
		try {
			StringBuilder builder = WebParser.parse(urlPath);
			callback.parse(builder);
		} catch (IOException e) {
			// not throw
		}
	}

	static boolean reject(Morpheme morph, String part, CharSequence text) {
		return "記号".equals(part)
				|| morph.surface.matches(SYMBOLS)
				|| ((morph.surface.length() < 2) && morph.surface
						.matches(ALPHA_NUM));
	}

	static boolean valid(String value) {
		return (value != null) && !value.isEmpty() && !"*".equals(value)
				&& !".".equals(value);
	}
}
