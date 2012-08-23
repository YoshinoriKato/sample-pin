package com.samplepin.servlet.util;

import java.net.UnknownHostException;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.Comment;
import com.samplepin.common.ACMongo;

public class Snipet {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try (ACMongo mongo = new ACMongo()) {

			Query<Comment> query = mongo.createQuery(Comment.class)
					.filter("isDeleted = ", false)
					.filter("userId = ", "ID_3Cb2TaAmy2tCLGqEQ5HAdSV3kKxEd3dJ");

			for (Comment comment : query.asList()) {
				System.out.println(comment.getCaption());
			}

		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
		}
		System.out.println("bye.");
	}

}
