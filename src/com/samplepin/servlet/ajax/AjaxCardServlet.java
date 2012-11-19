package com.samplepin.servlet.ajax;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.nl.NaturalLanguageParser;

@WebServlet(urlPatterns = { "/xxx.do" })
public class AjaxCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7818349586239857211L;

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
		AjaxInfo info = new AjaxInfo(req);

		if ("search".equals(info.sorted)) {
			String dic = NaturalLanguageParser.getDictionaryPath(req);
			new SearchAjax().ajax(resp.getOutputStream(), info, dic);

		} else if ("comment".equals(info.type)) {
			new CommentAjax().ajax(resp.getOutputStream(), info);

		} else {
			new CardAjax().ajax(resp.getOutputStream(), info);
		}
	}
}
