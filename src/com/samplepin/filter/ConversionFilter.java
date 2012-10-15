package com.samplepin.filter;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

@WebFilter(urlPatterns = { "/conversion.jsp" }, dispatcherTypes = DispatcherType.REQUEST)
public class ConversionFilter implements Filter {

	private ServletContext	context;

	@Override
	public void destroy() {
		this.context = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		User user = Helper.getUserById(req.getSession());

		if (user != null) {
			if (user.getIsFirst()) {
				user.setIsFirst(false);
				try (ACMongo mongo = new ACMongo()) {
					mongo.save(user);
				}

			} else {
				this.context.log("login.");
				resp.sendRedirect("home.jsp");
				return;
			}
		} else {
			resp.sendRedirect("error.jsp");
			return;
		}

		this.context.log("conversion.");
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig conf) throws ServletException {
		this.context = conf.getServletContext();
	}

}
