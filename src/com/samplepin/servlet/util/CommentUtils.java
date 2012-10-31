package com.samplepin.servlet.util;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.samplepin.Comment;
import com.samplepin.common.ACMongo;

@WebServlet(urlPatterns = { "/comm_util.do" })
public class CommentUtils extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8455401168897843691L;

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
		System.out.println(System.nanoTime());
		long count = 0;
		try (ACMongo mongo = new ACMongo()) {
			int offer = 0;
			int limit = 1000;

			Query<Comment> query = mongo.createQuery(Comment.class);
			long all = query.countAll();
			System.out.println("records: " + all);

			while (all > 0) {
				query = mongo.createQuery(Comment.class).limit(limit);

				for (Comment c : query.offset(offer).asList()) {
					c.setCommentId((c.getCreateDate() * 1000000) + ++count);
					System.out.println(c.getCommentId());
					mongo.save(c);
				}

				offer += limit;
				all -= limit;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(count);
		System.out.println("bye.");
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		main(null);
	}

}
