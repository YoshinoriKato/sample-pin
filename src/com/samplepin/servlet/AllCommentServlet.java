package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.mongodb.MongoException;
import com.samplepin.ACMongo;
import com.samplepin.Card;
import com.samplepin.Comment;

@WebServlet(urlPatterns = { "/comments.do" })
public class AllCommentServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818349586239857211L;

	private static Comparator<Comment> LATEST = new Comparator<Comment>() {
		@Override
		public int compare(Comment o1, Comment o2) {
			return o2.getCreateDate().compareTo(o1.getCreateDate());
		}
	};

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/javascript+json; charset=UTF-8");

		List<Comment> cards = new ArrayList<>();

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<Comment> query = datastore.createQuery(Comment.class);
			cards = query.asList();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
			log(e.getMessage());
		}

		Collections.sort(cards, LATEST);

		try (ServletOutputStream os = resp.getOutputStream()) {
			Gson gson = new Gson();
			String out = gson.toJson(cards);
			log(out);
			os.write(out.getBytes());
			os.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
		}
	}
}
