package com.samplepin.filter;

import java.io.IOException;
import java.net.UnknownHostException;

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

import com.mongodb.MongoException;
import com.samplepin.Header;
import com.samplepin.common.ACMongo;

@WebFilter(urlPatterns = { "/*" }, dispatcherTypes = DispatcherType.REQUEST)
public class LoggingFilter implements Filter {

	private ServletContext context;

	@Override
	public void destroy() {
		this.context = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;

		String remoteAddress = request.getRemoteAddr();
		String uri = req.getRequestURI();
		String protocol = request.getProtocol();
		this.context.log("User IP: " + remoteAddress + " | Resource File: "
				+ uri + " | Protocol: " + protocol);
		saveHeaderInfo(req);
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig conf) throws ServletException {
		this.context = conf.getServletContext();
	}

	final void saveHeaderInfo(HttpServletRequest req)
			throws UnknownHostException, MongoException {
		try (ACMongo mongo = new ACMongo()) {
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

			Header header = new Header(host, connection, cacheControl,
					userAgent, accept, acceptEncoding, acceptLanguage,
					acceptCharset, cookie, remoteAddress, uri, protocol);
			mongo.save(header);
		}
	}

}
