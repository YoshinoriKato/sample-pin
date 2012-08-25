package com.samplepin.servlet.ajax;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/xxx.do" })
public class AjaxCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7818349586239857211L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		doPost(req, resp);
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/javascript+json; charset=UTF-8");
		// String name = req.getParameter("name");
		// String key = req.getParameter("key");
		String type = req.getParameter("type");
		String otherUserId = req.getParameter("userId");
		String sorted = req.getParameter("sorted");
		String offset = req.getParameter("offset");
		String limit = req.getParameter("limit");
		String callback = req.getParameter("callback");
		String old = req.getParameter("old");
		String young = req.getParameter("young");
		String userId = (String) req.getSession().getAttribute("userId");
		String cardId = req.getParameter("cardId");
		String words = req.getParameter("words");

		if ("search".equals(sorted)) {
			String dic = req.getServletContext().getRealPath("ipadic");
			new SearchAjax().ajax(resp.getOutputStream(), otherUserId, sorted,
					offset, limit, callback, old, young, type, userId, cardId,
					words, dic);

		} else if ("comment".equals(type)) {
			new CommentAjax().ajax(resp.getOutputStream(), otherUserId, sorted,
					offset, limit, callback, old, young, type, userId, cardId);

		} else {
			new CardAjax().ajax(resp.getOutputStream(), otherUserId, sorted,
					offset, limit, callback, old, young, type, userId, cardId);
		}
	}
}
