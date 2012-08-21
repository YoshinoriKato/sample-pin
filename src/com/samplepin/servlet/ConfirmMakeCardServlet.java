package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.Datastore;
import com.samplepin.ACMongo;
import com.samplepin.Card;

@WebServlet(urlPatterns = "/confirm-make-card.do")
public class ConfirmMakeCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6534228482284422460L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("userId");
		Card card = (Card) session.getAttribute("confirmed");

		if ((userId != null) && (card != null)) {
			try (ACMongo mongo = new ACMongo()) {
				Datastore datastore = mongo.createDatastore();
				datastore.save(card);
			}
			log("make end.");
			resp.sendRedirect("index.jsp");
			return;

		} else {
			log("make failed.");
			req.setAttribute("message", "Please, retry.");
			RequestDispatcher dispathcer = req
					.getRequestDispatcher("make-card.jsp");
			dispathcer.forward(req, resp);
			return;
		}
	}
}
