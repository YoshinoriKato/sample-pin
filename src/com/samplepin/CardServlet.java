package com.samplepin;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

@WebServlet(urlPatterns = { "/xxx.do" })
public class CardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818349586239857211L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("application/json");
		resp.setCharacterEncoding("UTF-8");

		List<Card> cards = new ArrayList<>();

		try {
			Mongo mongo = new Mongo("127.0.0.1");
			Morphia morphia = new Morphia();
			Datastore datastore = morphia.createDatastore(mongo, "sample-pin");
			Query<Card> query = datastore.createQuery(Card.class);
			cards = query.asList();
		} catch (UnknownHostException | MongoException e1) {
			e1.printStackTrace();
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
