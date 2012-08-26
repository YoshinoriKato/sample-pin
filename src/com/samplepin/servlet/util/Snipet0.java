package com.samplepin.servlet.util;

import java.util.List;

import com.google.code.morphia.query.Query;
import com.samplepin.KeywordsAndCard;
import com.samplepin.common.ACMongo;

public class Snipet0 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String key = "http";
		String[] values = { key };
		try (ACMongo mongo = new ACMongo()) {
			Query<KeywordsAndCard> query2 = mongo.createQuery(
					KeywordsAndCard.class).filter("keywords all ", values);
			List<KeywordsAndCard> kacs = query2.asList();
			if (kacs != null) {
				for (KeywordsAndCard kac : kacs) {
					System.out.println(kac.getCardId());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
