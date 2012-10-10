package com.samplepin.nl;

import java.util.Enumeration;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

import com.samplepin.WebPage;
import com.samplepin.common.Helper;

class PageSaver extends HTMLEditorKit.ParserCallback {

	private StringBuilder builder;

	private boolean canScan = false;

	private WebPage webPage;

	public PageSaver(StringBuilder builder, WebPage webPage) {
		this.builder = builder;
		this.webPage = webPage;
	}

	@Override
	public void handleComment(char[] text, int position) {
//		System.out.println(new String(text));
//		this.builder.append(new String(text)).append(Helper.LS);
	}

	@Override
	public void handleEndTag(HTML.Tag tag, int position) {
		this.canScan = false;
		isTitle = false;
	}

	@Override
	public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes,
			int position) {
		if (HTML.Tag.LINK.equals(tag)) {
			String rel = (String) attributes.getAttribute(HTML.Attribute.REL);
			String href = (String) attributes.getAttribute(HTML.Attribute.HREF);
			if (href != null && rel != null
					&& (rel.contains("short") || rel.contains("cut") || rel
							.contains("icon"))) {
				if (!Helper.valid(webPage.getFavicon())) {
					webPage.setFavicon(href);
				}
			}
		}
	}

	@Override
	public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes,
			int position) {
		if (HTML.Tag.HTML.equals(tag) || HTML.Tag.HEAD.equals(tag)
				|| HTML.Tag.BODY.equals(tag) || HTML.Tag.TITLE.equals(tag)
				|| HTML.Tag.A.equals(tag) || HTML.Tag.DIV.equals(tag)
				|| HTML.Tag.SPAN.equals(tag) || HTML.Tag.P.equals(tag)
				|| HTML.Tag.LI.equals(tag) || HTML.Tag.H1.equals(tag)
				|| HTML.Tag.H2.equals(tag) || HTML.Tag.H3.equals(tag)
				|| HTML.Tag.H4.equals(tag) || HTML.Tag.H5.equals(tag)) {
			this.canScan = true;
			writeAttributes(attributes);

			isTitle = HTML.Tag.TITLE.equals(tag);
		}
	}

	boolean isTitle = false;

	@Override
	public void handleText(char[] text, int position) {
		if (this.canScan) {
			System.out.println(new String(text));
			this.builder.append(new String(text)).append(Helper.LS);

			if (isTitle) {
				webPage.setTitle(new String(text));
			}
		}
	}

	private void writeAttributes(AttributeSet attributes) {
		Enumeration<?> e = attributes.getAttributeNames();
		while (e.hasMoreElements()) {
			Object name = e.nextElement();
			Object value = attributes.getAttribute(name);
			if (HTML.Attribute.ALT.equals(name)
					|| HTML.Attribute.CONTENT.equals(name)) {
//				System.out.println(value);
				this.builder.append(value).append(Helper.LS);
			}
		}
	}

}