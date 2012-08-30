package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.common.ActivityLogger;

@WebServlet(urlPatterns = { "/logout.do" })
public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7137737292592767679L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ActivityLogger.log(req, this.getClass(), "bye.");
		req.getSession().invalidate();
		resp.sendRedirect("login.jsp");
	}

}
