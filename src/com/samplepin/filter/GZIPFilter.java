package com.samplepin.filter;

import java.io.IOException;

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

@WebFilter(urlPatterns = { "/gzip.do" })
public class GZIPFilter implements Filter {

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
		if (isAcceptedGzipEncoding(req)) {
			GZIPServletResponseWrapper resp2 = new GZIPServletResponseWrapper(
					resp);
			this.context.log("Context-Encoding: gzip");
			chain.doFilter(req, resp2);
			resp2.finishResponse();
			return;
		}
		chain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig conf) throws ServletException {
		this.context = conf.getServletContext();
	}

	final boolean isAcceptedGzipEncoding(HttpServletRequest req) {
		String acceptEncoding = req.getHeader("Accept-Encoding");
		return (acceptEncoding.indexOf("gzip") != -1);
	}

}
