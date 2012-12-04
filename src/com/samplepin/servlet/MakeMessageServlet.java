package com.samplepin.servlet;

import java.io.IOException;
import java.net.UnknownHostException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoException;
import com.samplepin.Inquiry;
import com.samplepin.common.Helper;

@WebServlet(urlPatterns = { "/make-message.do" })
public class MakeMessageServlet extends HttpServlet {

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

		saveHeaderInfo(req);

		RequestDispatcher dispathcer = req
				.getRequestDispatcher("confirm-make-message.jsp");
		dispathcer.forward(req, resp);
		return;
	}

	final void saveHeaderInfo(HttpServletRequest req)
			throws UnknownHostException, MongoException {

		String mail = req.getParameter("mail");
		String message = req.getParameter("message");
		String userId = Helper.getUserId(req);
		String remoteAddress = req.getRemoteAddr();
		String uri = req.getRequestURI();
		String protocol = req.getProtocol();
		String host = req.getHeader("host");
		String connection = req.getHeader("connection");
		String cacheControl = req.getHeader("cache-control");
		String userAgent = req.getHeader("user-agent");
		String accept = req.getHeader("accept");
		String acceptEncoding = req.getHeader("accept-encoding");
		String acceptLanguage = req.getHeader("accept-language");
		String acceptCharset = req.getHeader("accept-charset");
		String cookie = req.getHeader("cookie");

		Inquiry inquiry = new Inquiry(host, connection, cacheControl,
				userAgent, accept, acceptEncoding, acceptLanguage,
				acceptCharset, cookie, remoteAddress, uri, protocol, mail,
				message, userId);

		if (Helper.valid(message)) {
			req.setAttribute("confirm", inquiry);
		}
	}

}
