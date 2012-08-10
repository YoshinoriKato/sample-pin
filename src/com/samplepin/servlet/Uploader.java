package com.samplepin.servlet;

import java.io.IOException;

import javax.servlet.http.Part;

final class Uploader {

	final String fileName;

	final Part part;

	public Uploader(Part part, String fileName) {
		super();
		this.part = part;
		this.fileName = fileName;
	}

	void execute() throws IOException {
		this.part.write(this.fileName);
	}
}