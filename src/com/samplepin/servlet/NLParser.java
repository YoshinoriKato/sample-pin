package com.samplepin.servlet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

import org.atilika.kuromoji.Token;
import org.atilika.kuromoji.Tokenizer;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.KeywordAndCard;
import com.samplepin.common.ACMongo;

public class NLParser {

	class MakeIndexRunner extends Thread {

		MakeIndexRunner(final ACMongo mongo, final HttpServletRequest req,
				final Card card) {
			super(new Runnable() {

				@Override
				public void run() {
					try {
						makeIndex(mongo, req, card);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}
	}

	final static String keyword(Token token) {
		String keyword = token.getReading() != null ? token.getReading()
				: token.getSurfaceForm();
		return keyword.toLowerCase(); // ignore case
	}

	// kuromoji
	public static void makeIndex(ACMongo mongo, HttpServletRequest req)
			throws Exception {

		// clear
		Query<KeywordAndCard> query = mongo.createQuery(KeywordAndCard.class);
		Datastore datastore = mongo.createDatastore();
		datastore.delete(query);

		// cards
		Query<Card> query0 = mongo.createQuery(Card.class);
		List<Card> cards = query0.asList();
		for (Card card : cards) {
			makeIndex(mongo, req, card);
		}
	}

	public static void makeIndex(ACMongo mongo, HttpServletRequest req,
			Card card) throws Exception {
		Set<KeywordAndCard> parsed = new HashSet<>();
		String cardId = card.getCardId();
		makeIndex(mongo, req, cardId,
				card.getCaption() + " " + card.getKeywords(), parsed);

		// comments
		Query<Comment> query1 = mongo.createQuery(Comment.class).filter(
				"cardId = ", cardId);
		List<Comment> comments = query1.asList();
		for (Comment comment : comments) {
			makeIndex(mongo, req, comment.getCardId(), comment.getCaption(),
					parsed);
		}
		// cardId unit
		register(mongo, req, cardId, parsed);
	}

	// kuromoji
	public static void makeIndex(ACMongo mongo, HttpServletRequest req,
			String cardId, String text) throws Exception {
		Set<KeywordAndCard> parsed = new HashSet<>();
		makeIndex(mongo, req, cardId, text, parsed);
		register(mongo, req, cardId, parsed);
	}

	// kuromoji
	public static void makeIndex(ACMongo mongo, HttpServletRequest req,
			String cardId, String text, Set<KeywordAndCard> parsed)
			throws Exception {
		Tokenizer tokenizer = Tokenizer.builder().build();
		List<Token> tokens = tokenizer.tokenize(text);

		// 結果を出力してみる
		for (Token token : tokens) {
			// System.out
			// .println("==================================================");
			// System.out.println("allFeatures : " + token.getAllFeatures());
			// System.out.println("partOfSpeech : " + token.getPartOfSpeech());
			// System.out.println("position : " + token.getPosition());
			// System.out.println("reading : " + token.getReading());
			// System.out.println("surfaceFrom : " + token.getSurfaceForm());
			// System.out.println("allFeaturesArray : "
			// + Arrays.asList(token.getAllFeaturesArray()));
			// System.out.println("辞書にある言葉? : " + token.isKnown());
			// System.out.println("未知語? : " + token.isUnknown());
			// System.out.println("ユーザ定義? : " + token.isUser());

			String keyword = keyword(token);
			String part = token.getPartOfSpeech();
			part = part.substring(0, part.indexOf(","));
			parsed.add(new KeywordAndCard(keyword, cardId, part));
		}
	}

	public static List<String> makeKeys(String text) {
		Tokenizer tokenizer = Tokenizer.builder().build();
		List<Token> tokens = tokenizer.tokenize(text);
		List<String> keys = new ArrayList<>();
		for (Token token : tokens) {
			String keyword = keyword(token);
			keys.add(keyword);
		}
		return keys;
	}

	// kuromoji
	public static void register(ACMongo mongo, HttpServletRequest req,
			String cardId, Set<KeywordAndCard> parsed) throws Exception {

		Query<KeywordAndCard> query = mongo.createQuery(KeywordAndCard.class)
				.filter("cardId = ", cardId);
		List<KeywordAndCard> kacs = query.asList();
		for (KeywordAndCard keywordAndCard : kacs) {
			parsed.remove(keywordAndCard);
		}
		mongo.save(parsed);
	}

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
			register(mongo, req, cardId, keyword, part);
		}
	}

	public static void register(ACMongo mongo, HttpServletRequest req,
			String cardId, String keyword, String part) throws Exception {

		Query<KeywordAndCard> query = mongo.createQuery(KeywordAndCard.class)
				.filter("cardId", cardId)
				.filter("keyword = ", keyword.toLowerCase())
				.filter("part = ", part);
		if (query.countAll() == 0) {
			mongo.save(new KeywordAndCard(keyword, cardId, part));
		}
	}
}
