package com.samplepin.nl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.samplepin.Tag;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

public class NaturalLanguageParser {

	static final String SYMBOLS = "[ -/:-@\\[-\\`\\{-\\~]+";

	static final String ALPHA_NUM = "[A-Za-z0-9]";

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
			List<Card> cards = cards(mongo, null);

			Map<String, AtomicInteger> counts = new HashMap<>();

			for (Card card : cards) {

				ParserCallback callbacker = new TaggingCallbacker(tagger,
						counts);

				// cards
				parseCard(card, callbacker);

				// comments
				parseComment(mongo, card, callbacker);

			}
			// register
			mongo.save(newTag(mongo, counts));
		}
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
		List<Tag> tags = new ArrayList<>();
		for (String key : counts.keySet()) {

			if ((key.length() < 2) && key.matches("[\\w]")) {
				continue;
			}

			String[] keys = { key };
			Query<KeywordsAndCard> query0 = mongo.createQuery(
					KeywordsAndCard.class).filter("keywords all ", keys);
			Query<Tag> query1 = mongo.createQuery(Tag.class).filter("tag = ",
					key);
			KeywordsAndCard kac = query0.get();

			// image
			String imagePath = "img/no_image.png";
			if (kac != null) {
				Card card = Helper.getCardInfoByID(kac.getCardId());
				if (card != null) {
					imagePath = card.getImagePath();
				}
			}

			Tag tag = query1.get();
			if (tag != null) {
				tag.setSize(query0.countAll());
				tag.setImagePath(imagePath);
				tags.add(tag);
			} else {
				tags.add(new Tag(key, query0.countAll(), imagePath));
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
		Query<Comment> query1 = mongo.createQuery(Comment.class)
				.filter("cardId = ", card.getCardId()).order("-createDate");
		List<Comment> comments = query1.asList();
		for (Comment comment : comments) {
			callbacker.parse(comment.getCaption());
		}
	}

	public static void parseWebpage(String urlPath, ParserCallback callback) {
		try {
			URL url = new URL(urlPath);
			String line = null;
			StringBuilder builder = new StringBuilder();

			try (BufferedReader br = new BufferedReader(new InputStreamReader(
					url.openStream()))) {
				while ((line = br.readLine()) != null) {
					builder.append(line).append(Helper.LS);
				}
			}

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
