package com.samplepin.servlet.ajax;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

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
import com.samplepin.Helper;
import com.samplepin.View;

@WebServlet(urlPatterns = { "/xxx.do" })
public class AjaxCardServlet extends HttpServlet {

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
		// String name = req.getParameter("name");
		// String key = req.getParameter("key");
		String sorted = req.getParameter("sorted");
		String offset = req.getParameter("offset");
		String limit = req.getParameter("limit");
		String callback = req.getParameter("callback");
		String old = req.getParameter("old");
		String young = req.getParameter("young");
		String userId = (String) req.getSession().getAttribute("userId");

		List<Card> cards = new ArrayList<>();

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();

			// filter
			Query<Card> query = datastore.createQuery(Card.class).filter(
					"isDeleted", false);

			if (valid(old)) {
				query.filter("createDate < ", Long.valueOf(old));
			}

			if (valid(young)) {
				query.filter("createDate > ", Long.valueOf(young));
			}

			// sort
			if (!valid(sorted)) {
				query.order("-createDate");

			} else if ("view".equals(sorted)) {
				query.order("-view");
				query.order("-createDate");

			} else if ("comment".equals(sorted)) {
				query.order("-likes");
				query.order("-createDate");

			} else if ("footprints".equals(sorted)) {
				List<View> views = Helper.getViewsInfoByID(userId);
				List<String> cardIds = new ArrayList<>();
				if (valid(views)) {
					for (View view : views) {
						cardIds.add(view.getCardId());
					}
					query.filter("cardId in ", cardIds);
				} else {
					writeToJSON(resp, cards, callback);
					return;
				}
			} else if ("recommend".equals(sorted)) {
				Set<String> recommends = new RecommendEngine()
						.getRecommendCards(userId);
				if (valid(recommends)) {
					query.filter("cardId in ", recommends);
					query.order("-createDate");
				} else {
					writeToJSON(resp, cards, callback);
					return;
				}
			} else if ("mine".equals(sorted)) {
				query.filter("userId = ", userId);
				query.order("-createDate");

			} else {
				query.order("-createDate");
			}

			// option
			if (valid(limit)) {
				query.limit(Integer.valueOf(limit));
			} else {
				query.limit(1000);
			}

			if (valid(offset)) {
				query.offset(Integer.valueOf(offset));
			}

			cards = query.asList();
		} catch (UnknownHostException | MongoException e) {
			e.printStackTrace();
			log(e.getMessage());
			throw e;
		}
		writeToJSON(resp, cards, callback);
	}

	final <T> boolean valid(Collection<T> value) {
		return (value != null) && !value.isEmpty();
	}

	final boolean valid(String value) {
		return (value != null) && !value.isEmpty();
	}

	final void writeToJSON(HttpServletResponse resp, List<Card> cards)
			throws IOException {
		try (OutputStreamWriter osw = new OutputStreamWriter(
				resp.getOutputStream(), "UTF-8")) {
			Gson gson = new Gson();
			String out = gson.toJson(cards);
			osw.write(out);
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			throw e;
		}
	}

	final void writeToJSON(HttpServletResponse resp, List<Card> cards,
			String callback) throws IOException {
		try (OutputStreamWriter osw = new OutputStreamWriter(
				resp.getOutputStream(), "UTF-8")) {
			Gson gson = new Gson();
			String out = gson.toJson(cards);
			osw.write(callback + "(" + out + ")");
			osw.flush();
		} catch (Exception e) {
			e.printStackTrace();
			log(e.getMessage());
			throw e;
		}
	}

}
