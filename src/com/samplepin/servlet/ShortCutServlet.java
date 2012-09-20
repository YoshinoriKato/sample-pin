package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.samplepin.ShortCut;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/S" })
public class ShortCutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4530246405324364355L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String hex = req.getParameter("H");
		try (ACMongo mongo = new ACMongo()) {
			Query<ShortCut> query = mongo.createQuery(ShortCut.class)
					.filter("category = ", "S").filter("hex = ", hex);
			ShortCut shortCut = query.get();
			resp.sendRedirect("card-comment.jsp?cardId=" + shortCut.getCardId()
					+ "&type=comment&image=open");
			ActivityLogger.log(req, this.getClass(), shortCut);
		}
	}

	public String toShortCut(String cardId) throws IOException {
		Integer hashcode = cardId.hashCode();
		String hex = Integer.toHexString(hashcode);
		try (ACMongo mongo = new ACMongo()) {
			Query<ShortCut> query = mongo.createQuery(ShortCut.class).filter(
					"hex", hex);
			ShortCut shortCut = query.get();
			if (shortCut == null) {
				shortCut = new ShortCut(hex, cardId, "S");
				mongo.save(shortCut);
			}
		}
		return Helper.DOMAIN + "S?H=" + hex;
	}
}
