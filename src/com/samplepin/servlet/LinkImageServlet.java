package com.samplepin.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.samplepin.ShortCut;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/I" })
public class LinkImageServlet extends HttpServlet {

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
					.filter("category = ", "I").filter("hex = ", hex);
			ShortCut shortCut = query.get();
			String filePath = req.getServletContext().getRealPath(
					shortCut.getCardId());
			ActivityLogger.log(req, this.getClass(), filePath);
			write(resp, filePath);
			Helper.setFootprint(
					Helper.getCardByImagePath(shortCut.getCardId()), req
							.getSession().getId());
		}
	}

	public String toShortCut(String imagePath) throws IOException {
		Integer hashcode = imagePath.hashCode();
		String hex = Integer.toHexString(hashcode);
		try (ACMongo mongo = new ACMongo()) {
			Query<ShortCut> query = mongo.createQuery(ShortCut.class).filter(
					"hex", hex);
			ShortCut shortCut = query.get();
			if (shortCut == null) {
				shortCut = new ShortCut(hex, imagePath, "I");
				mongo.save(shortCut);
			}
		}
		return Helper.DOMAIN + "I?H=" + hex;
	}

	final void write(HttpServletResponse resp, String filePath)
			throws IOException {
		BufferedImage im = ImageIO.read(new File(filePath));

		for (String name : ImageIO.getWriterFormatNames()) {
			if (filePath.endsWith(name)) {
				resp.setContentType("image/" + name);
				ImageIO.write(im, name, resp.getOutputStream());
				break;
			}
		}
	}
}
