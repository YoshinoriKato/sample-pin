package com.samplepin.servlet.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.UnknownHostException;

import javax.imageio.ImageIO;
import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoException;
import com.samplepin.Card;
import com.samplepin.common.ACMongo;
import com.samplepin.servlet.ImageType;
import com.samplepin.servlet.MakeCardServlet;

@WebServlet(urlPatterns = { "/makethumbnail.do" }, asyncSupported = true)
public class MakeThumbnail extends HttpServlet {

	class AsyncService implements Runnable {

		String imageFolderPath;

		AsyncContext ctx;

		public AsyncService(AsyncContext ctx, String imageFolderPath) {
			this.ctx = ctx;
			this.imageFolderPath = imageFolderPath;
		}

		// <link rel="alternate" type="application/rss+xml"
		// href="http://news.google.co.jp/news?pz=1&amp;cf=all&amp;ned=jp&amp;hl=ja&amp;topic=h&amp;num=3&amp;output=rss">
		@Override
		public void run() {
			try {
				resize(imageFolderPath);
				PrintWriter writer = this.ctx.getResponse().getWriter();
				writer.write("Asynchronous processing complete");
				writer.close();
				this.ctx.complete();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	class AsyncServiceListener implements AsyncListener {

		@Override
		public void onComplete(AsyncEvent event) throws IOException {
			// do something.
		}

		@Override
		public void onError(AsyncEvent event) throws IOException {
			// do something.
		}

		@Override
		public void onStartAsync(AsyncEvent event) throws IOException {
			// do something.
		}

		@Override
		public void onTimeout(AsyncEvent event) throws IOException {
			// do something.
		}
	}

	static boolean valid(String value) {
		return (value != null) && !value.isEmpty();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String imageFolderPath = req.getServletContext().getRealPath(
				"../icon-keeper");
		// AsyncContext asyncC = req.startAsync();
		// asyncC.addListener(new AsyncServiceListener());
		// asyncC.start(new AsyncService(asyncC, imageFolderPath));
		resize(imageFolderPath);
		resp.sendRedirect("home.jsp");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5036134172073312539L;

	static FileFilter FILTER = new FileFilter() {
		@Override
		public boolean accept(File pathname) {
			String name = pathname.getName().toLowerCase();
			return name.endsWith(".png") || name.endsWith(".jpeg")
					|| name.endsWith(".jpg") || name.endsWith(".gif")
					|| name.endsWith(".bmp");
		}
	};

	static int WIDTH = 200;

	public static void main(String[] args) {
		try {
			URL url = new URL(
					"http://localhost:8080/icon-keeper/I_OZ8CehbL1349873393107608000.gif");

			File folder = new File("test");
			folder.mkdir();
			File copy = new File(folder, "I_OZ8CehbL1349873393107608000.gif");
			MakeCardServlet.copyStream(url.openStream(), new FileOutputStream(
					copy), 1024);
			File input = copy;

			BufferedImage origin = ImageIO.read(input);
			int width = origin.getWidth();
			double scale = ((double) WIDTH) / ((double) width);
			int height = (int) Math.round(scale * origin.getHeight());
			width = WIDTH;

			String name = input.getName().substring(0,
					input.getName().lastIndexOf("."));
			String formatName = ImageType.getFormat(input).toString();

			File output = new File(folder // input.getParentFile()
					, "t_" + name + "." + formatName);

			new MakeThumbnail().scaleImage(origin, output, formatName, width,
					height);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	void scaleImage(BufferedImage image, File output, String ext, int width,
			int height) throws IOException {
		BufferedImage shrinkImage = new BufferedImage(width, height,
				image.getType());

		Graphics2D g2d = shrinkImage.createGraphics();

		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
				RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);

		// g2d.setRenderingHint(RenderingHints.KEY_DITHERING,
		// RenderingHints.VALUE_DITHER_ENABLE);

		// g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
		// RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
				RenderingHints.VALUE_RENDER_QUALITY);

		// g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
		// RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		// g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
		// RenderingHints.VALUE_FRACTIONALMETRICS_ON);

		// g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
		// RenderingHints.VALUE_STROKE_NORMALIZE);

		g2d.drawImage(image, 0, 0, width, height, null);
		ImageIO.write(shrinkImage, ext, output);
	}

	void checkTable(File input, String formatName) throws UnknownHostException,
			MongoException {
		try (ACMongo mongo = new ACMongo()) {
			Card card = mongo
					.createQuery(Card.class)
					.filter("imagePath = ",
							"../../icon-keeper/" + input.getName()).get();

			if (card == null) {
				String name = input.getName().substring(0,
						input.getName().lastIndexOf("."));
				card = mongo.createQuery(Card.class)
						.filter("imagePath = ", "../../icon-keeper/" + name)
						.get();

				if (card != null) {
					card.setImagePath(card.getImagePath() + "." + formatName);
					mongo.save(card);
				}
			}
		}
	}

	public void convert(File folder, File input, String ext) throws IOException {

		if (!folder.exists()) {
			if (folder.mkdirs()) {
				throw new IOException("Not exist folder.");
			}
		}

		BufferedImage origin = ImageIO.read(input);
		int width = origin.getWidth();
		double scale = ((double) WIDTH) / ((double) width);
		int height = (int) Math.round(scale * origin.getHeight());
		width = WIDTH;
		String name = input.getName().substring(0,
				input.getName().lastIndexOf("."));
		File output = new File(folder // input.getParentFile()
				, "t_" + name + "." + ext);

		scaleImage(origin, output, ext, width, height);
	}

	public void resize(String imageFolderPath) throws IOException {
		File folder = new File(imageFolderPath);
		if (folder.exists()) {
			File child = new File(folder, "t");
			child.mkdir();
			for (File input : folder.listFiles()) {
				if (input.isFile()) {
					try {
						String formatName = ImageType.getFormat(input)
								.toString();
						if (!"UNKNOWN".equals(formatName)) {
							resizeImage(folder, input, child, formatName);
						}
					} catch (Exception e) {
						e.printStackTrace();
						log(e.getMessage());
					}
				}
			}
		}
	}

	void resizeImage(File folder, File input, File child, String formatName)
			throws IOException {

		if (FILTER.accept(input)) {
			convert(child, input, formatName);
			checkTable(input, formatName);

		} else {
			log(input.toString());
			String fileName = input.getName();
			if (fileName.lastIndexOf(".") < 0) {

				try (ACMongo mongo = new ACMongo()) {
					Card card = mongo
							.createQuery(Card.class)
							.filter("imagePath = ",
									"../../icon-keeper/" + input.getName())
							.get();
					if (card != null) {
						fileName = fileName + "." + formatName;
						File renamed = new File(folder, fileName);
						input.renameTo(renamed);
						card.setImagePath("../../icon-keeper/"
								+ renamed.getName());
						mongo.save(card);

						convert(child, renamed, formatName);

						checkTable(renamed, formatName);
					}
				}
			}
		}

	}
}
