package com.samplepin.filter;

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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.code.morphia.query.Query;
import com.samplepin.KeyAndValue;
import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.servlet.LoginServlet;

@WebFilter(urlPatterns = { "/index.jsp", "/card-comment.jsp", "/make-card.jsp",
		"/my-card.jsp", "/account.jsp", "/S" }, dispatcherTypes = DispatcherType.REQUEST)
public class LoginFilter implements Filter {

	private ServletContext	context;

	@Override
	public void destroy() {
		this.context = null;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String url = request.getRequestURL().toString();
		String userId = (String) request.getSession().getAttribute("userId");

		this.context.log("Request URL: " + url);

		if (userId == null) {
			readCookie(request, response);
			userId = (String) request.getSession().getAttribute("userId");
		}

		if (userId == null) {
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

	final void readCookie(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		Cookie[] cookies = req.getCookies();
		String key = null;
		String welcome = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (LoginServlet.KEY_FIRST.equals(cookie.getName())) {
					welcome = cookie.getValue();

				} else if (LoginServlet.KEY_SECOND.equals(cookie.getName())) {
					key = cookie.getValue();
				}
				if (((welcome != null) && (welcome.length() > 11))
						&& (key != null)) {
					try (ACMongo mongo = new ACMongo()) {

						Query<KeyAndValue> query0 = mongo.createQuery(
								KeyAndValue.class).filter("key", key);
						KeyAndValue keyAndValue = query0.get();

						if (keyAndValue != null) {
							Query<User> query1 = mongo
									.createQuery(User.class)
									.filter("userId = ", keyAndValue.getValue());
							User user = query1.get();
							if (user != null) {
								HttpSession session = req.getSession(true);
								session.setAttribute("userId", user.getUserId());
								req.setAttribute("login", "auto login");
							}
						}
					}
					return;
				}
			}
		}
	}

}
