package com.samplepin.servlet.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.nl.NaturalLanguageParser;

@WebServlet(urlPatterns = { "/util7.do" })
public class Utility7 extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1609644801230608871L;

	// public static void parse(ACMongo mongo, Tagger tagger, String cardId,
	// String text, Set<KeywordAndCard> hashcodes) throws Exception {
	// // 辞書ディレクトリを引数で指定
	//
	// List<Morpheme> list = tagger.parse(text);
	// for (Morpheme morph : list) {
	// String keyword = morph.surface.toLowerCase();
	// String part = morph.feature
	// .substring(0, morph.feature.indexOf(","));
	// KeywordAndCard kac = new KeywordAndCard(keyword, cardId, part);
	//
	// // register
	// if (!hashcodes.contains(kac.hashCode())) {
	// hashcodes.add(kac);
	// }
	// }
	// }

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		NaturalLanguageParser.makeIndex(request, null);
		// try (ACMongo mongo = new ACMongo()) {
		// response.getWriter().println("<html><body>");
		// String dic = request.getServletContext().getRealPath("ipadic");
		// Tagger tagger = new Tagger(dic);
		//
		// // cards
		// Query<Card> query0 = mongo.createQuery(Card.class).order(
		// "-createDate");
		// List<Card> cards = query0.asList();
		// for (Card card : cards) {
		// response.getWriter().println(
		// "<p>start: " + card.getCardId() + "</p>");
		// Set<KeywordAndCard> hashcodes = new HashSet<>();
		// log("@@@ " + card.getCardId());
		// parse(mongo, tagger, card.getCardId(), card.getCaption() + " "
		// + card.getKeywords(), hashcodes);
		//
		// // comments
		// Query<Comment> query1 = mongo.createQuery(Comment.class)
		// .filter("cardId = ", card.getCardId())
		// .order("-createDate");
		// List<Comment> comments = query1.asList();
		// for (Comment comment : comments) {
		// parse(mongo, tagger, comment.getCardId(),
		// comment.getCaption(), hashcodes);
		// }
		//
		// // remove duplicate
		// Query<KeywordAndCard> query2 = mongo
		// .createQuery(KeywordAndCard.class)
		// .filter("cardId = ", card.getCardId())
		// .order("keyword, part");
		// List<KeywordAndCard> kacs = query2.asList();
		// for (KeywordAndCard keywordAndCard : kacs) {
		// if (hashcodes.contains(keywordAndCard)) {
		// hashcodes.remove(keywordAndCard);
		// }
		// }
		//
		// mongo.save(hashcodes);
		// response.getWriter().println(
		// "<p>end: " + card.getCardId() + "</p>");
		// }
		// response.getWriter().println("</body></html>");
		// } catch (Exception e) {
		// e.printStackTrace();
		// request.getSession().setAttribute("error", e);
		// throw new ServletException(e);
		// }
		//
		// System.out.println("bye.");
	}
}
