package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoException;
import com.samplepin.Inquiry;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/confirm-make-message.do" })
public class ConfirmMakeMessageServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5426777241563315344L;

	public static final Long COMMENTS_LIMIT = 1000L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		saveHeaderInfo(req);

		resp.sendRedirect("index.jsp");
		return;
	}

	final void saveHeaderInfo(HttpServletRequest req)
			throws UnknownHostException, MongoException {
		try (ACMongo mongo = new ACMongo()) {

			Inquiry inquiry = (Inquiry)req.getSession().getAttribute("confirm");

			if (Helper.valid(inquiry)) {
				mongo.save(inquiry);
			}
		}
	}

}
