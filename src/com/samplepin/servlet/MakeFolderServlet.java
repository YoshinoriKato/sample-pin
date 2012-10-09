package com.samplepin.servlet;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.Folder;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/make-folder.do" })
public class MakeFolderServlet extends HttpServlet {

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
		String folderName = req.getParameter("folderName");
		String cards = req.getParameter("cards");

		try (ACMongo mongo = new ACMongo()) {
			String userId = Helper.getUserId(req);

			log(userId + " make " + folderName);

			if (Helper.valid(userId) && Helper.valid(folderName)
					&& Helper.valid(cards)) {

				Folder folder = mongo.createQuery(Folder.class)
						.filter("userId = ", userId)
						.filter("folderName = ", folderName)
						.filter("isDeleted = ", false).get();

				if (folder != null) {
					merge(folder, cards);
				} else {
					folder = new Folder(userId, folderName, cards);
				}

				mongo.save(folder);

				ActivityLogger.log(req, this.getClass(), folder);

				resp.sendRedirect("home.jsp?sorted=folder&folderId="
						+ folder.getFolderId());
				return;
			}

		} catch (Exception e) {
			log(e.getMessage());
			req.setAttribute("error", e);
		}

		req.setAttribute("message", "Please, write a comment.");
		RequestDispatcher dispathcer = req
				.getRequestDispatcher("home.jsp?select=true");
		dispathcer.forward(req, resp);
	}

	private void merge(Folder folder, String cards) {
		Set<String> unique = new HashSet<>();
		String[] cards0 = folder.getCards().split(",");
		String[] cards1 = cards.split(",");
		for (String card : cards0) {
			unique.add(card);
		}
		for (String card : cards1) {
			unique.add(card);
		}
		StringBuilder builder = new StringBuilder();
		boolean isFirst = true;
		for (String card : unique) {
			if (isFirst) {
				isFirst = false;
			} else {
				builder.append(",");
			}
			builder.append(card);
		}
		folder.setCards(builder.toString());
	}
}
