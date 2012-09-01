package com.samplepin.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.samplepin.Activity;

public class ActivityLogger {

	public static void log(HttpServletRequest req, Class<?> invoker, Object pojo)
			throws IOException {
		String note = (pojo != null) ? new Gson().toJson(pojo) : "null";
		log(req, invoker, note);
	}

	public static void log(HttpServletRequest req, Class<?> invoker, String note)
			throws IOException {
		String action = (invoker != null) ? invoker.getSimpleName() : "null";
		log(req, action, note);
	}

	public static void log(HttpServletRequest req, String action, String note)
			throws IOException {
		Object userId = Helper.getUserId(req);
		userId = (userId != null) ? userId : req.getSession().getId();
		log((String) userId, action, note);
	}

	public static void log(String userId, String action, String note)
			throws IOException {
		try (ACMongo mongo = new ACMongo()) {
			mongo.save(new Activity(userId, action, note, System
					.currentTimeMillis()));
		}
	}

}
