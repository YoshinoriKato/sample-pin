package com.samplepin.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLDecoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/upload.do" })
public class ImageUploadServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137737292592767679L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		try {
			String url = URLDecoder.decode(req.getParameter("url"), "UTF-8");
			String keywords = URLDecoder.decode(req.getParameter("keywords"),
					"UTF-8");
			String site = URLDecoder.decode(req.getParameter("site"), "UTF-8");
			String parentId = req.getParameter("parentId");
			parentId = Helper.valid(parentId) ? parentId : "";
			String userId = Helper.getUserId(req);
			int len = userId.length();
			String prefix = userId.substring(len - 8, len);

			InputStream is = new URL(url).openStream();

			String fullPath = req.getServletContext().getRealPath(
					"../icon-keeper");
			File referenceFolder = new File("../../icon-keeper");
			File realFolder = new File(fullPath);
			String fileName = "I_" + prefix + System.nanoTime();
			File realPathFile = new File(realFolder, fileName);

			MakeCardServlet.copyStream(is, new FileOutputStream(realPathFile),
					1024);
			String formatName = ImageType.getFormat(realPathFile).toString();
			fileName = fileName + "." + formatName;
			realPathFile.renameTo(new File(realFolder, fileName));
			File referenceFile = new File(referenceFolder, fileName);

			ActivityLogger.log(req, this.getClass(), realPathFile.getName());

			req.setAttribute("keywords", keywords);
			req.setAttribute("site", site);
			req.setAttribute("imagePath", referenceFile.getPath());
			req.setAttribute("parentId", parentId);

			// resp.sendRedirect("make-card.jsp?imagePath="
			// + URLEncoder.encode(referenceFile.getPath(), "UTF-8")
			// + "&keywords=" + URLEncoder.encode(keywords, "UTF-8")
			// + "&site=" + URLEncoder.encode(site, "UTF-8")
			// + (Helper.valid(parentId) ? "&parentId=" + parentId : ""));

			RequestDispatcher dispathcer = req
					.getRequestDispatcher("make-card.jsp");
			dispathcer.forward(req, resp);

			return;
		} catch (IOException | ServletException e) {
			e.printStackTrace();
			req.setAttribute("error", e);
		}
		resp.sendRedirect("make-card.jsp");
	}

	final String fileName(MakeCardServlet make, String url) {
		int begin = url.lastIndexOf("/") + 1;
		begin = begin > 0 ? begin : 0;
		int end = url.lastIndexOf("?");
		end = end > 0 ? end : url.length();
		String fileName = make.makePrefix() + url.substring(begin, end);
		return fileName;
	}

}
