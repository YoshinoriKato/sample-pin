package com.samplepin.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/upload.do" })
public class ImageUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137737292592767679L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			String url = req.getParameter("url");
			URL u = new URL(url);
			MakeCardServlet make = new MakeCardServlet();
			InputStream is = u.openStream();
			String fullPath = req.getServletContext().getRealPath(
					"../icon-keeper");
			File referenceFolder = new File("../../icon-keeper");
			String fileName = make.makePrefix()
					+ url.substring(url.lastIndexOf("/") + 1);
			File realFolder = new File(fullPath);
			File realPathFile = new File(realFolder, fileName);
			File referenceFile = new File(referenceFolder, fileName);
			MakeCardServlet.copyStream(is, new FileOutputStream(realPathFile),
					1024);
			resp.sendRedirect("make-card.jsp?imagePath="
					+ referenceFile.getPath());
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
		resp.sendRedirect("image-search.jsp");
	}

}
