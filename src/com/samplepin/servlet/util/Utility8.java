package com.samplepin.servlet.util;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.Folder;
import com.samplepin.common.ACMongo;

@WebServlet(urlPatterns = { "/util8.do" })
public class Utility8 extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5036134172073312539L;

	public static void main(String[] args) throws UnknownHostException,
			MongoException {
		try (ACMongo mongo = new ACMongo()) {
			Query<Folder> query = mongo.createQuery(Folder.class);
			for (Folder folder : query.fetch()) {
				mongo.save(folder);
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
