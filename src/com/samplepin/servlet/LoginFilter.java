package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(urlPatterns = { "/make-card.jsp", "/my-card.jsp" }, dispatcherTypes = DispatcherType.REQUEST)
public class LoginFilter implements Filter {

	private ServletContext context;

	@Override
	public void destroy() {
		this.context = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String url = request.getRequestURL().toString();
		String userId = (String) request.getSession().getAttribute("userId");
		this.context.log("Request URL: " + url);
		if (userId == null) {
			HttpServletResponse response = (HttpServletResponse) resp;
			request.getSession().setAttribute("fromUrl", url);
			RequestDispatcher dispathcer = req
					.getRequestDispatcher("login.jsp");
			dispathcer.forward(req, response);
		} else {
			chain.doFilter(req, resp);
		}
	}

	@Override
	public void init(FilterConfig conf) throws ServletException {
		this.context = conf.getServletContext();
	}

}
