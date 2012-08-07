package com.samplepin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

@WebServlet(urlPatterns = { "/xxx.do" })
public class CardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818349586239857211L;

	int cap_min = 10;

	String caption = "雨ニモマケズ 風ニモマケズ 雪ニモ夏ノ暑サニモマケヌ 丈夫ナカラダヲモチ"
			+ " 慾ハナク 決シテ瞋ラズ イツモシヅカニワラッテヰル" + " 一日ニ玄米四合ト 味噌ト少シノ野菜ヲタベ"
			+ " アラユルコトヲ ジブンヲカンジョウニ入レズニ ヨクミキキシワカリ ソシテワスレズ"
			+ " 野原ノ松ノ林ノノ 小サナ萓ブキノ小屋ニヰテ" + " 東ニ病気ノコドモアレバ 行ッテ看病シテヤリ"
			+ " 西ニツカレタ母アレバ 行ッテソノ稲ノ朿ヲ負ヒ" + " 南ニ死ニサウナ人アレバ 行ッテコハガラナクテモイヽトイヒ"
			+ " 北ニケンクヮヤソショウガアレバ ツマラナイカラヤメロトイヒ"
			+ " ヒドリノトキハナミダヲナガシ サムサノナツハオロオロアルキ"
			+ " ミンナニデクノボートヨバレ ホメラレモセズ クニモサレズ" + " サウイフモノニ ワタシハナリタイ"
			+ " 南無無辺行菩薩 南無上行菩薩 南無多宝如来 南無妙法蓮華経 南無釈迦牟尼仏 南無浄行菩薩 南無安立行菩薩";

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		List<Card> cards = new ArrayList<>();
		Random dice = new Random(System.nanoTime());

		for (int i = 0; i < 300; i++) {
			String caption2 = this.caption.substring(0,
					dice.nextInt(this.caption.length() - this.cap_min)
							+ this.cap_min);
			cards.add(new Card("ID_" + System.nanoTime(),
					"29062360065790233_nVEunrt3_f.jpg", caption2, dice.nextInt(10),
					dice.nextInt(100)));
		}

		try (ServletOutputStream os = resp.getOutputStream()) {
			Gson gson = new Gson();
			String out = gson.toJson(cards);
			log(out);
			os.write(out.getBytes());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
