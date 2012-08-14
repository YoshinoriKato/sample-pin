package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.Datastore;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.Helper;
import com.samplepin.User;
import com.samplepin.View;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@WebServlet(urlPatterns = { "/init3.do" })
public class Initializer3 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9090615824997426253L;

	static String[] FLAGS = { "flag_001.png", "flag_004.png", "flag_101.png",
			"flag_104.png", "flag_105.png", "flag_106.png", "flag_107.png",
			"flag_108.png", "flag_109.png", "flag_110.png", "flag_111.png",
			"flag_114.png", "flag_115.png", "flag_116.png", "flag_117.png",
			"flag_118.png", "flag_119.png", "flag_120.png", "flag_121.png",
			"flag_122.png", "flag_123.png", "flag_124.png", "flag_125.png",
			"flag_126.png", "flag_127.png", "flag_128.png", "flag_131.png",
			"flag_132.png", "flag_133.png", "flag_134.png", "flag_135.png",
			"flag_136.png", "flag_137.png", "flag_138.png", "flag_139.png",
			"flag_140.png", "flag_141.png", "flag_142.png", "flag_143.png",
			"flag_144.png", "flag_145.png", "flag_146.png", "flag_147.png",
			"flag_148.png", "flag_149.png", "flag_150.png", "flag_151.png",
			"flag_202.png", "flag_205.png", "flag_206.png", "flag_207.png",
			"flag_208.png", "flag_209.png", "flag_210.png", "flag_211.png",
			"flag_212.png", "flag_213.png", "flag_218.png", "flag_219.png",
			"flag_222.png", "flag_227.png", "flag_228.png", "flag_229.png",
			"flag_230.png", "flag_231.png", "flag_232.png", "flag_233.png",
			"flag_234.png", "flag_235.png", "flag_236.png", "flag_237.png",
			"flag_238.png", "flag_239.png", "flag_240.png", "flag_241.png",
			"flag_242.png", "flag_243.png", "flag_244.png", "flag_245.png",
			"flag_247.png", "flag_248.png", "flag_249.png", "flag_250.png",
			"flag_251.png", "flag_252.png", "flag_253.png", "flag_302.png",
			"flag_303.png", "flag_306.png", "flag_307.png", "flag_308.png",
			"flag_309.png", "flag_310.png", "flag_311.png", "flag_314.png",
			"flag_315.png", "flag_318.png", "flag_319.png", "flag_320.png",
			"flag_321.png", "flag_322.png", "flag_323.png", "flag_326.png",
			"flag_327.png", "flag_328.png", "flag_339.png", "flag_340.png",
			"flag_341.png", "flag_342.png", "flag_343.png", "flag_402.png",
			"flag_405.png", "flag_410.png", "flag_411.png", "flag_412.png",
			"flag_415.png", "flag_418.png", "flag_419.png", "flag_420.png",
			"flag_505.png", "flag_506.png", "flag_507.png", "flag_608.png" };

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random dice = new Random(System.nanoTime());
		long mills = System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 1000);
		final int MAX_USERS = 10000;
		final int MAX_COMMENTS = 1000;
		final int MAX_VIEWS = 100000;

		try (ACMongo mongo = new ACMongo()) {
			mongo.dropDatabase(mongo.getDbName());

			Datastore datastore = mongo.createDatastore();
			List<Card> cards = new ArrayList<Card>();
			Set<String> userIds = new HashSet<>();
			Set<String> cardIds = new HashSet<>();

			for (int i = 0; i < MAX_USERS; i++) {
				User user = new User();
				String userId = Helper.generatedUserId("ID_");
				user.setUserId(userId);
				user.setPassword(Helper.generatedUserId().hashCode());
				user.setMail(Helper.generatedUserId("M_") + "@sample-pin.com");
				userIds.add(userId);
				user.setBackgroundColor(Helper.generateColorString());
				user.setFontColor(Helper.generateColorString());
				user.setUseBackgroundImage(false);
				user.setUserName(Helper.generatedUserId());
				datastore.save(user);
			}

			List<String> userIdList = new ArrayList<>(userIds);

			long mills2 = mills;
			for (int i = 0; i < FLAGS.length; i++) {
				String cardId = Base64.encode(String.valueOf(System.nanoTime())
						.getBytes());
				cardIds.add(cardId);
				int likes = dice.nextInt(MAX_COMMENTS);
				int views = dice.nextInt(MAX_VIEWS);
				views = views < likes ? likes : views;
				cards.add(new Card(cardId, "", "img/flag/" + FLAGS[i], "",
						"How about this country ?", likes, views, mills2));

				mills2 += 1000 * 60 * 60 * dice.nextInt(10);
			}
			datastore.save(cards);

			Map<String, View> views = new HashMap<>();

			for (Card card : cards) {
				long mills3 = card.getCreateDate();
				for (int j = 0; j < card.getLikes(); j++) {
					String userId = userIdList.get(dice.nextInt(userIdList
							.size()));
					Comment comment = new Comment(userId, card.getCardId(),
							"こめんと" + mills3, mills3);
					datastore.save(comment);

					View view = views.get(userId + "&" + card.getCardId());
					view = view != null ? view : new View(mills3,
							card.getCardId(), userId);
					views.put(userId + "&" + card.getCardId(), view);
					view.setComments(view.getComments() + 1);
					view.setTimes(view.getTimes() + 1);
					view.setVisitedDate(mills3);
					mills3 += 1000 * 60 * dice.nextInt(10);
				}

				mills3 = card.getCreateDate();
				for (int j = 0; j < (card.getView() - card.getLikes()); j++) {
					String userId = userIdList.get(dice.nextInt(userIdList
							.size()));
					View view = views.get(userId + "&" + card.getCardId());
					view = view != null ? view : new View(mills3,
							card.getCardId(), userId);
					views.put(userId + "&" + card.getCardId(), view);
					view.setTimes(view.getTimes() + 1);
					if (view.getVisitedDate() < mills3) {
						view.setVisitedDate(mills3);
					}
					mills3 += 1000 * 60 * dice.nextInt(10);
				}
			}
			datastore.save(views.values());

			System.out.println("bye.");
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		main(null);
		resp.sendRedirect("index.jsp");
	}

}
