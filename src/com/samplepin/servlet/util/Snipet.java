package com.samplepin.servlet.util;

import java.net.UnknownHostException;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.Card;
import com.samplepin.common.ACMongo;

public class Snipet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try (ACMongo mongo = new ACMongo()) {

			Query<Card> query = mongo.createQuery(Card.class).filter(
					"cardId = ", "C_gLlhmoWrnSacOUGo_FDyqGyCVFpR8i3s");

			mongo.createDatastore().delete(query);

		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		System.out.println("bye.");
	}

}
