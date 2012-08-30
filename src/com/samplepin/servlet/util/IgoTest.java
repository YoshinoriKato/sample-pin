package com.samplepin.servlet.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.reduls.igo.Morpheme;
import net.reduls.igo.Tagger;

import com.google.code.morphia.query.Query;
import com.samplepin.Card;
import com.samplepin.common.ACMongo;

public class IgoTest extends HttpServlet {

	/**
	 * 
	 */
	private static final long			serialVersionUID	= -4233313439420920524L;

	static Map<String, AtomicInteger>	counts				= new HashMap<>();

	public static void main(String[] args) {
		try (ACMongo mongo = new ACMongo()) {
			File dir = new File("ipadic");
			dir = (dir.exists()) ? dir : new File("WebContent/ipadic");
			if (dir.exists()) {
				Tagger tagger = new Tagger(dir.getPath());
				Query<Card> query = mongo.createQuery(Card.class);
				List<Card> cards = query.asList();
				for (Card card : cards) {
					register(tagger, card.getCaption());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			// req.getSession().setAttribute("error", e);
			// throw new ServletException(e);
		}

		List<Integer> median = new ArrayList<>();
		int sum = 0;
		for (String key : counts.keySet()) {
			int count = counts.get(key).get();
			if (count < 54.5) {
				System.out.println(key + " : " + count);
			}
			median.add(count);
			sum += count;
		}
		Collections.sort(median);
		System.out.println("median: "
				+ median.get(Math.round(median.size() * .5f)));
		System.out.println("average: " + (((double) sum) / median.size()));
		System.out.println("bye.");
	}

	public static void register(Tagger tagger, String caption) throws Exception {
		// 辞書ディレクトリを引数で指定

		List<Morpheme> list = tagger.parse(caption);
		for (Morpheme morph : list) {
			if (morph.feature.startsWith("名詞,")) {
				// System.out.println(morph.surface + "," + morph.feature + ","
				// + morph.start);
				if (!counts.containsKey(morph.surface)) {
					counts.put(morph.surface, new AtomicInteger(1));
				}
				counts.get(morph.surface).incrementAndGet();
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException {
	}
}
