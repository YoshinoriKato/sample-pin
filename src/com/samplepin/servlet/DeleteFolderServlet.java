package com.samplepin.servlet;

import static com.samplepin.common.Helper.valid;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.code.morphia.query.Query;
import com.mongodb.MongoException;
import com.samplepin.Folder;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/delete-folder.do" })
public class DeleteFolderServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4585224248438234342L;

	final void deleteFolder(String userId, String folderId)
			throws UnknownHostException, MongoException {

		try (ACMongo mongo = new ACMongo()) {
			Query<Folder> query0 = mongo.createQuery(Folder.class)
					.filter("folderId = ", folderId)
					.filter("userId = ", userId).filter("isDeleted", false);

			if ((query0.countAll() != 0)) {
				Folder folder = query0.get();
				folder.setIsDeleted(true);
				mongo.save(folder);
			}
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		String folderId = req.getParameter("folderId");
		String fUserId = req.getParameter("userId");

		try (ACMongo mongo = new ACMongo()) {
			String userId = Helper.getUserId(req);

			if (valid(userId) && valid(fUserId) && valid(folderId)
					&& userId.equals(fUserId)) {
				deleteFolder(userId, folderId);

				ActivityLogger.log(req, this.getClass(), folderId);

				resp.sendRedirect("folder.jsp");
				return;
			}

		} catch (Exception e) {
			log(e.getMessage());
			req.setAttribute("error", e);
		}
		resp.sendRedirect("error.jsp");
	}
}
