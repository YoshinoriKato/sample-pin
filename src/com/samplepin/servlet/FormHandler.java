package com.samplepin.servlet;

import javax.servlet.http.HttpServletRequest;

public interface FormHandler {

	public Object handle(HttpServletRequest req);

}
