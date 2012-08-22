package com.samplepin.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

class GZIPServletOutputStream extends ServletOutputStream implements
		AutoCloseable {

	protected ByteArrayOutputStream	baos		= null;
	protected GZIPOutputStream		gzipstream	= null;
	protected boolean				closed		= false;
	protected HttpServletResponse	response	= null;
	protected ServletOutputStream	output		= null;

	GZIPServletOutputStream(HttpServletResponse response) throws IOException {
		super();
		this.closed = false;
		this.response = response;
		this.output = response.getOutputStream();
		this.baos = new ByteArrayOutputStream();
		this.gzipstream = new GZIPOutputStream(this.baos);
	}

	public boolean closed() {
		return (this.closed);
	}

	@Override
	public void flush() throws IOException {
		if (this.closed) {
			throw new IOException("Cannot flush a closed output stream");
		}
		this.gzipstream.flush();
	}

	public void reset() {
	}

	@Override
	public void write(byte b[]) throws IOException {
		write(b, 0, b.length);
	}

	@Override
	public void write(byte b[], int off, int len) throws IOException {
		if (this.closed) {
			throw new IOException("Cannot write to a closed output stream");
		}
		this.gzipstream.write(b, off, len);
	}

	@Override
	public void write(int b) throws IOException {
		if (this.closed) {
			throw new IOException("Cannot write to a closed output stream");
		}
		this.gzipstream.write((byte) b);
	}
}