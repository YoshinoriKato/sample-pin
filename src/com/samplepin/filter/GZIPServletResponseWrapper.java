package com.samplepin.filter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

class GZIPServletResponseWrapper extends
		javax.servlet.http.HttpServletResponseWrapper {

	protected HttpServletResponse	origResponse	= null;

	protected ServletOutputStream	stream			= null;
	protected PrintWriter			writer			= null;

	GZIPServletResponseWrapper(HttpServletResponse response) {
		super(response);
		this.origResponse = response;
		setHeader("Context-Encoding", "gzip");
	}

	public ServletOutputStream createOutputStream() throws IOException {
		return (new GZIPServletOutputStream(this.origResponse));
	}

	public void finishResponse() {
		try {
			if (this.writer != null) {
				this.writer.close();
			} else {
				if (this.stream != null) {
					this.stream.close();
				}
			}
		} catch (IOException e) {
		}
	}

	@Override
	public void flushBuffer() throws IOException {
		this.stream.flush();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		if (this.writer != null) {
			throw new IllegalStateException(
					"getWriter() has already been called!");
		}

		if (this.stream == null) {
			this.stream = createOutputStream();
		}
		return (this.stream);
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (this.writer != null) {
			return (this.writer);
		}

		if (this.stream != null) {
			throw new IllegalStateException(
					"getOutputStream() has already been called!");
		}

		this.stream = createOutputStream();
		this.writer = new PrintWriter(new OutputStreamWriter(this.stream,
				"UTF-8"));
		return (this.writer);
	}

	@Override
	public void setContentLength(int length) {
	}

}