package com.samplepin.nl;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import javax.swing.text.ChangedCharSetException;
import javax.swing.text.html.HTMLEditorKit;

import com.samplepin.WebPage;
import com.samplepin.common.ACMongo;
import com.samplepin.common.Helper;

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

	public static String charset(String contentType) {
		// Content-Typeから文字コード判定
		String charset;
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

	static boolean hasEncode(String charset) {
		return charset != null;
	}

	public static void main(String[] args) {
		try {
			System.out.println(parse(
					"http://ggsoku.com/2012/10/google-nexus-call-center-open/",
					null));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static StringBuilder parse(String urlPath, String charset)
			throws IOException {

		ParserGetter kit = new ParserGetter();
		HTMLEditorKit.Parser parser = kit.getParser();
		StringBuilder builder = new StringBuilder();

		URL u = new URL(urlPath);
		URLConnection urlconn = u.openConnection();
		Helper.setUserAgent(urlconn);

		try (InputStream in = urlconn.getInputStream()) {
			charset = hasEncode(charset) ? charset : charset(urlconn
					.getContentType());
			charset = hasEncode(charset) ? charset : urlconn
					.getContentEncoding();

			try (ACMongo mongo = new ACMongo()) {
				WebPage webPage = webPage(mongo, urlPath, u.getHost());
				HTMLEditorKit.ParserCallback callback = new PageSaver(builder,
						webPage);

				try (InputStreamReader isr = hasEncode(charset) ? new InputStreamReader(
						in, charset) : new InputStreamReader(in)) {
					parser.parse(isr, callback, hasEncode(charset));

				} catch (ChangedCharSetException e) {
					if (!hasEncode(charset)) {
						charset = charset(e.getCharSetSpec());
						return parse(urlPath, charset);
					}
				}
				mongo.save(webPage);
			}
		}

		return builder;
	}

	static WebPage webPage(ACMongo mongo, String urlPath, String host) {
		WebPage webPage = mongo.createQuery(WebPage.class)
				.filter("url", urlPath).get();
		if (webPage == null) {
			webPage = new WebPage(urlPath, "", "", host, "");
		}
		return webPage;
	}
}
