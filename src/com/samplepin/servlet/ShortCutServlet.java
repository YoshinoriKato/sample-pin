package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.samplepin.ACMongo;
import com.samplepin.ShortCut;

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
			Query<ShortCut> query = mongo.createQuery(ShortCut.class).filter(
					"hex = ", hex);
			ShortCut shortCut = query.get();
			resp.sendRedirect("card-comment.jsp?cardId=" + shortCut.getCardId()
					+ "&type=comment");
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
				shortCut = new ShortCut(hex, cardId);
				mongo.save(shortCut);
			}
		}
		return "http://www45022u.sakura.ne.jp/sample-pin/" + "S?H=" + hex;
	}
}