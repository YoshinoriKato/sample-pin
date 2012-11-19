package com.samplepin.servlet.ajax;

import javax.servlet.http.HttpServletRequest;

import com.samplepin.common.Helper;

public class AjaxInfo {

	String type;
	String otherUserId;
	String sorted;
	String offset;
	String limit;
	String callback;
	String old;
	String young;
	String userId;
	String cardId;
	String words;
	String select;
	String folderId;

	AjaxInfo(HttpServletRequest req) {
		this.type = req.getParameter("type");
		this.otherUserId = req.getParameter("userId");
		this.sorted = req.getParameter("sorted");
		this.offset = req.getParameter("offset");
		this.limit = req.getParameter("limit");
		this.callback = req.getParameter("callback");
		this.old = req.getParameter("old");
		this.young = req.getParameter("young");
		this.userId = Helper.getUserId(req);
		this.cardId = req.getParameter("cardId");
		this.words = req.getParameter("words");
		this.select = req.getParameter("select");
		this.folderId = req.getParameter("folderId");
	}

	public AjaxInfo(String otherUserId, String sorted, String offset,
			String limit, String callback, String old, String young,
			String type, String userId, String cardId) {
		this.otherUserId = otherUserId;
		this.sorted = sorted;
		this.offset = offset;
		this.limit = limit;
		this.callback = callback;
		this.old = old;
		this.young = young;
		this.type = type;
		this.userId = userId;
		this.cardId = cardId;
	}
}