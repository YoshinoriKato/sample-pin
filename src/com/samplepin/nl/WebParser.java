package com.samplepin.nl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.html.HTMLEditorKit;

import com.samplepin.WebPage;
import com.samplepin.common.ACMongo;

class ParserGetter extends HTMLEditorKit {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3311586319722170406L;

	@Override
	public HTMLEditorKit.Parser getParser() {
		return super.getParser();
	}

}

public class WebParser {

	public static void main(String[] args) {
		try {
			System.out.println(parse("http://shiba-bota.at.webry.info/"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String charset(URLConnection urlconn) {
		// Content-Typeを取得
		String contentType = urlconn.getContentType();
		contentType = urlconn.getHeaderField("Content-Type");

		// Content-Typeから文字コード判定
		String charset = urlconn.getContentEncoding();
		int charsetIndex = contentType.indexOf("=");
		if (charsetIndex == -1) {
			// 判定不可ならnullとする
			charset = null;
		} else {
			charset = contentType.substring(charsetIndex + 1,
					contentType.length());
		}
		return charset;
	}

	public static StringBuilder parse(String urlPath) throws IOException {

		ParserGetter kit = new ParserGetter();
		HTMLEditorKit.Parser parser = kit.getParser();

		URL u = new URL(urlPath);
		URLConnection urlconn = u.openConnection();
		String charset = urlconn.getContentEncoding();
		InputStream in = urlconn.getInputStream();
		InputStreamReader isr = charset != null ? new InputStreamReader(in,
				charset) : new InputStreamReader(in);
		StringBuilder builder = new StringBuilder();
		try (ACMongo mongo = new ACMongo()) {
			WebPage webPage = mongo.createQuery(WebPage.class)
					.filter("url", urlPath).get();
			if (webPage == null) {
				webPage = new WebPage(urlPath, "", "", u.getHost(), "");
			}
			HTMLEditorKit.ParserCallback callback = new PageSaver(builder,
					webPage);
			parser.parse(isr, callback, false);
			mongo.save(webPage);
		}

		return builder;
	}

}
