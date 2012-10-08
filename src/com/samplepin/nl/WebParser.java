package com.samplepin.nl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import com.samplepin.common.Helper;

class PageSaver extends HTMLEditorKit.ParserCallback {

	private StringBuilder	builder;

	private boolean			canScan	= false;

	public PageSaver(StringBuilder builder) {
		this.builder = builder;
	}

	@Override
	public void handleComment(char[] text, int position) {
		// this.builder.append(new String(text)).append(Helper.LS);
	}

	@Override
	public void handleEndTag(HTML.Tag tag, int position) {
		this.canScan = false;
	}

	@Override
	public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes,
			int position) {
	}

	@Override
	public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes,
			int position) {
		if (HTML.Tag.TITLE.equals(tag) || HTML.Tag.BODY.equals(tag)
				|| HTML.Tag.A.equals(tag) || HTML.Tag.DIV.equals(tag)
				|| HTML.Tag.SPAN.equals(tag) || HTML.Tag.P.equals(tag)
				|| HTML.Tag.LI.equals(tag) || HTML.Tag.H1.equals(tag)
				|| HTML.Tag.H2.equals(tag) || HTML.Tag.H3.equals(tag)
				|| HTML.Tag.H4.equals(tag) || HTML.Tag.H5.equals(tag)) {
			this.canScan = true;
			writeAttributes(attributes);
		}
	}

	@Override
	public void handleText(char[] text, int position) {
		if (this.canScan) {
			this.builder.append(new String(text)).append(Helper.LS);
		}
	}

	private void writeAttributes(AttributeSet attributes) {
		Enumeration<?> e = attributes.getAttributeNames();
		while (e.hasMoreElements()) {
			Object name = e.nextElement();
			Object value = attributes.getAttribute(name);
			if (HTML.Attribute.ALT.equals(name)
					|| HTML.Attribute.CONTENT.equals(name)) {
				this.builder.append(value).append(Helper.LS);
			}
		}
	}

}

class ParserGetter extends HTMLEditorKit {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3311586319722170406L;

	@Override
	public HTMLEditorKit.Parser getParser() {
		return super.getParser();
	}

}

public class WebParser {

	public static void main(String[] args) {
		try {
			System.out
					.println(parse("http://matome.naver.jp/odai/2126620592429462101"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static StringBuilder parse(String urlPath) throws IOException {

		ParserGetter kit = new ParserGetter();
		HTMLEditorKit.Parser parser = kit.getParser();

		URL u = new URL(urlPath);
		InputStream in = u.openStream();
		InputStreamReader isr = new InputStreamReader(in);
		StringBuilder builder = new StringBuilder();
		HTMLEditorKit.ParserCallback callback = new PageSaver(builder);
		parser.parse(isr, callback, false);

		return builder;
	}

}
