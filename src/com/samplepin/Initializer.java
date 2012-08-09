package com.samplepin;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.google.code.morphia.Datastore;
import com.mongodb.MongoException;
import com.sun.org.apache.xml.internal.security.utils.Base64;

public class Initializer {

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
			Random dice = new Random(System.nanoTime());

			for (int i = 0; i < 300; i++) {
				String caption2 = caption.substring(0,
						dice.nextInt(caption.length() - cap_min) + cap_min);
				String cardId = Base64.encode(String.valueOf(System.nanoTime())
						.getBytes());
				cards.add(new Card(cardId, "img/"
						+ images[dice.nextInt(images.length)], caption2, dice
						.nextInt(10), dice.nextInt(100)));
			}
			datastore.save(cards);

			System.out.println("bye.");
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
	}

}
