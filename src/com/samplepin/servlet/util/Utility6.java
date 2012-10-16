package com.samplepin.servlet.util;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.ShortCut;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/util6.do" })
public class Utility6 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5036134172073312539L;

	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		try (ACMongo mongo = new ACMongo()) {
			Query<ShortCut> query = mongo.createQuery(ShortCut.class);
			for (ShortCut shortCut : query.fetch()) {
				if (!Helper.valid(shortCut.getCategory())) {
					shortCut.setCategory("S");
				}
				mongo.save(shortCut);
			}
		}
	}

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		main(null);
		response.sendRedirect("home.jsp");
	}
}
