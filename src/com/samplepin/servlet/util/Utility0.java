package com.samplepin.servlet.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.samplepin.ACMongo;
import com.samplepin.Card;

@WebServlet(urlPatterns = { "/util0.do" })
public class Utility0 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5036134172073312539L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse response)
			throws IOException {
		String fullPath = req.getServletContext().getRealPath("../icon-keeper");
		try (ACMongo mongo = new ACMongo()) {
			Query<Card> query = mongo.createQuery(Card.class);
			for (Card card : query.asList()) {
				String path = card.getImagePath();
				String fileName = path.substring(path.lastIndexOf("/") + 1);
				File referenceFile = new File(fullPath, fileName);
				if (!referenceFile.exists()) {
					fullPath = req.getServletContext().getRealPath("img");
					referenceFile = new File(fullPath, fileName);
				}
				if (!referenceFile.exists()) {
					fullPath = req.getServletContext().getRealPath("img/flag");
					referenceFile = new File(fullPath, fileName);
				}
				if (referenceFile.exists()) {
					BufferedImage image = ImageIO.read(referenceFile);
					card.setWidth(image.getWidth());
					card.setHeight(image.getHeight());
					mongo.save(card);
				}
			}
		}
		response.sendRedirect("index.jsp");
	}
}
