package com.samplepin.servlet.util;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.Datastore;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Card;
import com.samplepin.Comment;
import com.samplepin.User;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@WebServlet(urlPatterns = { "/init1.do" })
public class Initializer1 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9090615824997426253L;

	static int cap_min = 10;

	static String caption = "雨ニモマケズ 風ニモマケズ 雪ニモ夏ノ暑サニモマケヌ 丈夫ナカラダヲモチ"
			+ " 慾ハナク 決シテ瞋ラズ イツモシヅカニワラッテヰル" + " 一日ニ玄米四合ト 味噌ト少シノ野菜ヲタベ"
			+ " アラユルコトヲ ジブンヲカンジョウニ入レズニ ヨクミキキシワカリ ソシテワスレズ"
			+ " 野原ノ松ノ林ノノ 小サナ萓ブキノ小屋ニヰテ" + " 東ニ病気ノコドモアレバ 行ッテ看病シテヤリ"
			+ " 西ニツカレタ母アレバ 行ッテソノ稲ノ朿ヲ負ヒ" + " 南ニ死ニサウナ人アレバ 行ッテコハガラナクテモイヽトイヒ"
			+ " 北ニケンクヮヤソショウガアレバ ツマラナイカラヤメロトイヒ"
			+ " ヒドリノトキハナミダヲナガシ サムサノナツハオロオロアルキ"
			+ " ミンナニデクノボートヨバレ ホメラレモセズ クニモサレズ" + " サウイフモノニ ワタシハナリタイ"
			+ " 南無無辺行菩薩 南無上行菩薩 南無多宝如来 南無妙法蓮華経 南無釈迦牟尼仏 南無浄行菩薩 南無安立行菩薩";

	static String[] images = { "171136854560954777_w8G7ssLN_b.jpg",
			"211174953484576_dyuSomSz_b.jpg",
			"211317407485625618_ueI09UXp_b.jpg",
			"29062360065790233_nVEunrt3_f.jpg", "3450910519_0739fe4c95_q.jpg",
			"36521446948450850_3A8h2194_b.jpg", "6023677971_1a47ac6105_o.jpg",
			"87538786477858313_UHvBNBnH_b.jpg" };

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try (ACMongo mongo = new ACMongo()) {
			mongo.dropDatabase("sample-pin");

			Datastore datastore = mongo.createDatastore();
			User user = new User();
			user.setUserId("yoshinori");
			user.setPassword("hoge".hashCode());
			datastore.save(user);

			List<Card> cards = new ArrayList<>();
			List<Comment> comments = new ArrayList<>();
			Random dice = new Random(System.nanoTime());

			for (int i = 0; i < 30; i++) {
				String caption2 = caption.substring(0,
						dice.nextInt(caption.length() - cap_min) + cap_min);
				String cardId = Base64.encode(String.valueOf(System.nanoTime())
						.getBytes());
				int like = dice.nextInt(10);
				int view = dice.nextInt(100);
				view = view < like ? like : view;
				cards.add(new Card(cardId, "", "img/"
						+ images[dice.nextInt(images.length)], "", caption2,
						like, view, System.currentTimeMillis()));

				long mills = System.currentTimeMillis();
				for (int j = 0; j < like; j++) {
					String caption3 = caption.substring(0,
							dice.nextInt(caption.length() - cap_min) + cap_min);
					comments.add(new Comment("nanashi", cardId, caption3,
							++mills));
				}
			}
			datastore.save(cards);
			datastore.save(comments);

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
