package com.samplepin.servlet.util;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.samplepin.nl.NaturalLanguageParser;

@WebServlet(urlPatterns = { "/util8.do" })
public class Utility8 extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1609644801230608871L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		NaturalLanguageParser.makeTags(request);
	}
}
