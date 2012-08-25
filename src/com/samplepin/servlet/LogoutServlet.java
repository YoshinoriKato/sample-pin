package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = { "/logout.do" })
public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 7137737292592767679L;

	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		req.getSession().invalidate();
		resp.sendRedirect("login.jsp");
	}

}
