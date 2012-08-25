package com.samplepin.servlet.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.code.morphia.query.Query;
import com.samplepin.KeywordsAndCard;
import com.samplepin.common.ACMongo;

public class Summary {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try (ACMongo mongo = new ACMongo()) {
			Map<String, AtomicInteger> map = new HashMap<>();

			Query<KeywordsAndCard> query = mongo
					.createQuery(KeywordsAndCard.class);

			for (KeywordsAndCard kac : query.asList()) {
				for (String key : kac.getKeywords()) {
					if (!map.containsKey(key)) {
						map.put(key, new AtomicInteger(0));
					}
					map.get(key).incrementAndGet();
				}
			}

			for (String key : map.keySet()) {
				if (map.get(key).get() > 20) {
					System.out.println(key + ":" + map.get(key).get());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
