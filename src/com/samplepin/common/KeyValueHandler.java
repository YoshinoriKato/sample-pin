package com.samplepin.common;

import java.net.UnknownHostException;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.KeyAndValue;

public class KeyValueHandler {

	public static String getKeyByValue(String value)
			throws UnknownHostException, MongoException {
		try (ACMongo mongo = new ACMongo()) {
			Query<KeyAndValue> query = mongo.createQuery(KeyAndValue.class)
					.filter("value = ", value);
			return query.get().getKey();
		}
	}

	public static String getRegisteredKeyByValue(String value)
			throws UnknownHostException, MongoException {
		String key = Helper.generatedIdString("KEY_");
		while (!putKeyAndValue(key, value)) {
		}
		return key;
	}

	public static String getValueByKey(String key) throws UnknownHostException,
			MongoException {
		try (ACMongo mongo = new ACMongo()) {
			Query<KeyAndValue> query = mongo.createQuery(KeyAndValue.class)
					.filter("key = ", key);
			return query.get().getKey();
		}
	}

	public static boolean putKeyAndValue(String key, String value)
			throws UnknownHostException, MongoException {
		try (ACMongo mongo = new ACMongo()) {
			Query<KeyAndValue> query = mongo.createQuery(KeyAndValue.class)
					.filter("key = ", key);
			if (query.countAll() == 0) {
				mongo.save(new KeyAndValue(key, value));
				return true;
			}
		}
		return false;
	}

}
