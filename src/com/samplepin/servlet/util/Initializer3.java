package com.samplepin.servlet.util;

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
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.User;
import com.samplepin.View;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/init3.do" })
public class Initializer3 extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -9090615824997426253L;

	static String				caption				= "雨ニモマケズ 風ニモマケズ 雪ニモ夏ノ暑サニモマケヌ 丈夫ナカラダヲモチ"
															+ " 慾ハナク 決シテ瞋ラズ イツモシヅカニワラッテヰル"
															+ " 一日ニ玄米四合ト 味噌ト少シノ野菜ヲタベ"
															+ " アラユルコトヲ ジブンヲカンジョウニ入レズニ ヨクミキキシワカリ ソシテワスレズ"
															+ " 野原ノ松ノ林ノノ 小サナ萓ブキノ小屋ニヰテ"
															+ " 東ニ病気ノコドモアレバ 行ッテ看病シテヤリ"
															+ " 西ニツカレタ母アレバ 行ッテソノ稲ノ朿ヲ負ヒ"
															+ " 南ニ死ニサウナ人アレバ 行ッテコハガラナクテモイヽトイヒ"
															+ " 北ニケンクヮヤソショウガアレバ ツマラナイカラヤメロトイヒ"
															+ " ヒドリノトキハナミダヲナガシ サムサノナツハオロオロアルキ"
															+ " ミンナニデクノボートヨバレ ホメラレモセズ クニモサレズ"
															+ " サウイフモノニ ワタシハナリタイ"
															+ " 南無無辺行菩薩 南無上行菩薩 南無多宝如来 南無妙法蓮華経 南無釈迦牟尼仏 南無浄行菩薩 南無安立行菩薩";

	static String				song				= "London Bridge is broken down,"
															+ "Broken down, broken down."
															+ "London Bridge is broken down,"
															+ "My fair lady."
															+ ""
															+ "Build it up with wood and clay,"
															+ "Wood and clay, wood and clay,"
															+ "Build it up with wood and clay,"
															+ "My fair lady."
															+ ""
															+ "Wood and clay will wash away,"
															+ "Wash away, wash away,"
															+ "Wood and clay will wash away,"
															+ "My fair lady."
															+ ""
															+ "Build it up with bricks and mortar,"
															+ "Bricks and mortar, bricks and mortar,"
															+ "Build it up with bricks and mortar,"
															+ "My fair lady."
															+ ""
															+ "Bricks and mortar will not stay,"
															+ "Will not stay, will not stay,"
															+ "Bricks and mortar will not stay,"
															+ "My fair lady."
															+ ""
															+ "Build it up with iron and steel,"
															+ "Iron and steel, iron and steel,"
															+ "Build it up with iron and steel,"
															+ "My fair lady."
															+ ""
															+ "Iron and steel will bend and bow,"
															+ "Bend and bow, bend and bow,"
															+ "Iron and steel will bend and bow,"
															+ "My fair lady."
															+ ""
															+ "Build it up with silver and gold,"
															+ "Silver and gold, silver and gold,"
															+ "Build it up with silver and gold,"
															+ "My fair lady."
															+ ""
															+ "Silver and gold will be stolen away,"
															+ "Stolen away, stolen away,"
															+ "Silver and gold will be stolen away,"
															+ "My fair lady."
															+ ""
															+ "Set a man to watch all night,"
															+ "Watch all night, watch all night,"
															+ "Set a man to watch all night,"
															+ "My fair lady."
															+ ""
															+ "Suppose the man should fall asleep,"
															+ "Fall asleep, fall asleep,"
															+ "Suppose the man should fall asleep?"
															+ "My fair lady."
															+ ""
															+ "Give him a pipe to smoke all night,"
															+ "Smoke all night, smoke all night,"
															+ "Give him a pipe to smoke all night,"
															+ "My fair lady.";

	static String[]				FLAGS				= { "flag_001.png",
			"flag_004.png", "flag_101.png", "flag_104.png", "flag_105.png",
			"flag_106.png", "flag_107.png", "flag_108.png", "flag_109.png",
			"flag_110.png", "flag_111.png", "flag_114.png", "flag_115.png",
			"flag_116.png", "flag_117.png", "flag_118.png", "flag_119.png",
			"flag_120.png", "flag_121.png", "flag_122.png", "flag_123.png",
			"flag_124.png", "flag_125.png", "flag_126.png", "flag_127.png",
			"flag_128.png", "flag_131.png", "flag_132.png", "flag_133.png",
			"flag_134.png", "flag_135.png", "flag_136.png", "flag_137.png",
			"flag_138.png", "flag_139.png", "flag_140.png", "flag_141.png",
			"flag_142.png", "flag_143.png", "flag_144.png", "flag_145.png",
			"flag_146.png", "flag_147.png", "flag_148.png", "flag_149.png",
			"flag_150.png", "flag_151.png", "flag_202.png", "flag_205.png",
			"flag_206.png", "flag_207.png", "flag_208.png", "flag_209.png",
			"flag_210.png", "flag_211.png", "flag_212.png", "flag_213.png",
			"flag_218.png", "flag_219.png", "flag_222.png", "flag_227.png",
			"flag_228.png", "flag_229.png", "flag_230.png", "flag_231.png",
			"flag_232.png", "flag_233.png", "flag_234.png", "flag_235.png",
			"flag_236.png", "flag_237.png", "flag_238.png", "flag_239.png",
			"flag_240.png", "flag_241.png", "flag_242.png", "flag_243.png",
			"flag_244.png", "flag_245.png", "flag_247.png", "flag_248.png",
			"flag_249.png", "flag_250.png", "flag_251.png", "flag_252.png",
			"flag_253.png", "flag_302.png", "flag_303.png", "flag_306.png",
			"flag_307.png", "flag_308.png", "flag_309.png", "flag_310.png",
			"flag_311.png", "flag_314.png", "flag_315.png", "flag_318.png",
			"flag_319.png", "flag_320.png", "flag_321.png", "flag_322.png",
			"flag_323.png", "flag_326.png", "flag_327.png", "flag_328.png",
			"flag_339.png", "flag_340.png", "flag_341.png", "flag_342.png",
			"flag_343.png", "flag_402.png", "flag_405.png", "flag_410.png",
			"flag_411.png", "flag_412.png", "flag_415.png", "flag_418.png",
			"flag_419.png", "flag_420.png", "flag_505.png", "flag_506.png",
			"flag_507.png", "flag_608.png"			};

	static String[]				images				= {
			"171136854560954777_w8G7ssLN_b.jpg",
			"211174953484576_dyuSomSz_b.jpg",
			"211317407485625618_ueI09UXp_b.jpg",
			"29062360065790233_nVEunrt3_f.jpg", "3450910519_0739fe4c95_q.jpg",
			"36521446948450850_3A8h2194_b.jpg", "6023677971_1a47ac6105_o.jpg",
			"87538786477858313_UHvBNBnH_b.jpg"		};

	static int					cap_min				= 12;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Random dice = new Random(System.nanoTime());
		long mills = System.currentTimeMillis() - (1000 * 60 * 60 * 24 * 2000);
		final int MAX_USERS = 200;
		final int MAX_COMMENTS = 1000;
		final int MAX_VIEWS = 10000;

		try (ACMongo mongo = new ACMongo()) {
			mongo.dropDatabase(mongo.getDbName());
			Initializer0.main(null);

			Datastore datastore = mongo.createDatastore();
			List<Card> cards = new ArrayList<Card>();
			List<User> userIds = new ArrayList<>();
			Set<String> cardIds = new HashSet<>();

			for (int i = 0; i < MAX_USERS; i++) {
				User user = new User();
				String userId = Helper.generatedIdString("ID_");
				user.setImagePath("img/" + images[dice.nextInt(images.length)]);
				user.setUserId(userId);
				user.setPassword(Helper.generatedIdString().hashCode());
				user.setMail(Helper.generatedIdString("M_") + "@sample-pin.com");
				user.setBackgroundColor(Helper.generateColorString());
				user.setFontColor(Helper.generateColorString());
				user.setUseBackgroundImage(false);
				user.setUserName(Helper.generatedIdString().substring(0, 12));
				userIds.add(user);
			}
			datastore.save(userIds);

			long mills2 = mills;
			for (int i = 0; i < FLAGS.length; i++) {
				String cardId = Helper.generatedIdString("C_");
				cardIds.add(cardId);
				int likes = dice.nextInt(MAX_COMMENTS);
				int views = dice.nextInt(MAX_VIEWS);
				views = views < likes ? likes : views;
				User user = userIds.get(dice.nextInt(userIds.size()));
				String userId = user.getUserId();
				String caption2 = caption.substring(0,
						dice.nextInt(caption.length() - cap_min) + cap_min);
				String song2 = song.substring(0,
						dice.nextInt(song.length() - cap_min) + cap_min);
				cards.add(new Card("self", cardId, userId, "img/flag/"
						+ FLAGS[i], "", ((dice.nextInt() % 2) == 0) ? caption2
						: song2, likes, views, mills2));

				mills2 += 1000 * 60 * 30 * (dice.nextInt(7) + 1);
			}
			datastore.save(cards);

			Map<String, View> views = new HashMap<>();

			for (Card card : cards) {
				long mills3 = card.getCreateDate();
				for (int j = 0; j < card.getLikes(); j++) {
					User user = userIds.get(dice.nextInt(userIds.size()));
					String userId = user.getUserId();
					String caption2 = caption.substring(0,
							dice.nextInt(caption.length() - cap_min) + cap_min);
					String song2 = song.substring(0,
							dice.nextInt(song.length() - cap_min) + cap_min);

					Comment comment = new Comment(userId, card.getCardId(),
							((dice.nextInt() % 2) == 0) ? caption2 : song2,
							mills3);
					datastore.save(comment);
					card.setUpdateDate(comment.getCreateDate());
					mongo.save(card);

					View view = views.get(userId + "&" + card.getCardId());
					view = view != null ? view : new View(mills3,
							card.getCardId(), userId);
					views.put(userId + "&" + card.getCardId(), view);
					view.setComments(view.getComments() + 1);
					view.setTimes(view.getTimes() + 1);
					view.setVisitedDate(mills3);
					mills3 += 1000 * 60 * (dice.nextInt(10) + 1);
				}

				mills3 = card.getCreateDate();
				for (int j = 0; j < (card.getView() - card.getLikes()); j++) {
					User user = userIds.get(dice.nextInt(userIds.size()));
					String userId = user.getUserId();
					View view = views.get(userId + "&" + card.getCardId());
					view = view != null ? view : new View(mills3,
							card.getCardId(), userId);
					views.put(userId + "&" + card.getCardId(), view);
					view.setTimes(view.getTimes() + 1);
					if (view.getVisitedDate() < mills3) {
						view.setVisitedDate(mills3);
					}
					mills3 += 1000 * 60 * (dice.nextInt(10) + 1);
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
		resp.sendRedirect("home.jsp");
	}

}
