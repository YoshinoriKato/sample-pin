package com.samplepin.servlet.ajax;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.google.gson.Gson;
import com.samplepin.Card;
import com.samplepin.common.ACMongo;

@WebServlet(urlPatterns = { "/request-card.do" })
public class RequestCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818349586239857211L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/javascript+json; charset=UTF-8");
		String lastUpdate = req.getParameter("lastUpdate");
		String token = req.getParameter("token");

		if (!"doya".equals(token)) {
			Map<String, String> map = new HashMap<>();
			map.put("message", "password error");
			writeToJSON(resp.getOutputStream(), map);
			return;
		}

		Long updateDate = Long.valueOf(lastUpdate);
		try (ACMongo mongo = new ACMongo()) {
			Query<Card> query = mongo.createQuery(Card.class)
					.filter("isDeleted", false)
					.filter("updateDate > ", updateDate).order("updateDate");
			Card card = query.get();
			if (card != null) {
				writeToJSON(resp.getOutputStream(), card);
			} else {
				Map<String, String> map = new HashMap<>();
				map.put("message", "no data");
				writeToJSON(resp.getOutputStream(), map);
			}
		}
	}

	final void writeToJSON(OutputStream os, Object data) throws IOException {
		try (OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8")) {
			Gson gson = new Gson();
			String out = gson.toJson(data);
			osw.write(out);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
