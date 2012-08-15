package com.samplepin.filter;

import javax.servlet.http.HttpServletRequest;

public interface FormHandler {

	public Object handle(HttpServletRequest req);

}
