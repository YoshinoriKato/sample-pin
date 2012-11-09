package com.samplepin.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;
import com.samplepin.servlet.util.MakeThumbnail;

@WebServlet(urlPatterns = { "/upload.do" })
public class ImageUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137737292592767679L;

	final String copyToServer(HttpServletRequest req, HttpServletResponse resp,
			String urlPath) throws IOException {
		String userId = Helper.getUserId(req);

		String fullPath = req.getServletContext().getRealPath("../icon-keeper");
		File referenceFolder = new File("../../icon-keeper");
		File realFolder = new File(fullPath);
		String fileName = Helper.getImageFileName(userId);
		File realPathFile = new File(realFolder, fileName);

		InputStream is = getInputStreamFromURL(urlPath);

		MakeCardServlet
				.copyStream(is, new FileOutputStream(realPathFile), 1024);

		String formatName = "png";// ImageType.getFormat(realPathFile).toString();
		fileName = fileName + "." + formatName;
		File renamed = new File(realFolder, fileName);
		realPathFile.renameTo(renamed);
		File referenceFile = new File(referenceFolder, fileName);
		new MakeThumbnail().convert(new File(realFolder, "t"), renamed,
				formatName);

		ActivityLogger.log(req, this.getClass(), realPathFile.getName());

		return referenceFile.getPath();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		try {
			String urlPath = URLDecoder
					.decode(req.getParameter("url"), "UTF-8");
			String keywords = URLDecoder.decode(req.getParameter("keywords"),
					"UTF-8");
			String site = URLDecoder.decode(req.getParameter("site"), "UTF-8");
			String parentId = req.getParameter("parentId");
			parentId = Helper.valid(parentId) ? parentId : "";

			String copyed = copyToServer(req, resp, urlPath);
			req.setAttribute("keywords", keywords);
			req.setAttribute("site", site);
			req.setAttribute("parentId", parentId);
			req.setAttribute("imagePath", copyed);

		} catch (Exception e) {
			e.printStackTrace();
			req.setAttribute("error", e);
			req.setAttribute("imagePath", "img/no_image.png");
		}

		RequestDispatcher dispathcer = req
				.getRequestDispatcher("make-card.jsp");
		dispathcer.forward(req, resp);
		return;
	}

	final InputStream getInputStreamFromURL(String urlPath) throws IOException {
		InputStream is = null;
		urlPath = URLDecoder.decode(urlPath, "UTF-8");
		URL url = new URL(urlPath);
		URLConnection conn = url.openConnection();
		Helper.setUserAgent(conn);
		is = conn.getInputStream();
		return is;
	}
}
