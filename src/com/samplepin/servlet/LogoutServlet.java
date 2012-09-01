package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.common.ActivityLogger;

@WebServlet(urlPatterns = { "/logout.do" })
public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7137737292592767679L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.getSession().invalidate();
		removeCookie(req, resp);
		ActivityLogger.log(req, this.getClass(), "bye.");
		resp.sendRedirect("login.jsp");
	}

	final void removeCookie(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Cookie[] cookies = req.getCookies();
		final int REMOVE = 0;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				cookie.setMaxAge(REMOVE);
				resp.addCookie(cookie);
			}
		}
	}

}
