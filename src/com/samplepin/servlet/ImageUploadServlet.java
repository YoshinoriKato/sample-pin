package com.samplepin.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/upload.do" })
public class ImageUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7137737292592767679L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			String url = URLDecoder.decode(req.getParameter("url"), "UTF-8");
			String keywords = URLDecoder.decode(req.getParameter("keywords"),
					"UTF-8");
			String site = URLDecoder.decode(req.getParameter("site"), "UTF-8");
			URL u = new URL(url);
			MakeCardServlet make = new MakeCardServlet();
			InputStream is = u.openStream();
			String fullPath = req.getServletContext().getRealPath(
					"../icon-keeper");
			File referenceFolder = new File("../../icon-keeper");
			int begin = url.lastIndexOf("/") + 1;
			begin = begin > 0 ? begin : 0;
			int end = url.lastIndexOf("?");
			end = end > 0 ? end : url.length();
			String fileName = make.makePrefix() + url.substring(begin, end);
			File realFolder = new File(fullPath);
			File realPathFile = new File(realFolder, fileName);
			File referenceFile = new File(referenceFolder, fileName);
			MakeCardServlet.copyStream(is, new FileOutputStream(realPathFile),
					1024);
			resp.sendRedirect("make-card.jsp?imagePath="
					+ URLEncoder.encode(referenceFile.getPath(), "UTF-8")
					+ "&keywords=" + URLEncoder.encode(keywords, "UTF-8")
					+ "&site=" + URLEncoder.encode(site, "UTF-8"));
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("make-card.jsp");
	}

}
