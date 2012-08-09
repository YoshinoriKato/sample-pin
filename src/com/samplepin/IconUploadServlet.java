package com.samplepin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.google.code.morphia.Datastore;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@WebServlet(name = "IconUploadServlet", urlPatterns = "/uplaod.do")
@MultipartConfig(location = "/Developer/workspace/uploaded")
public class IconUploadServlet extends HttpServlet {

	final class Uploader {

		final String fileName;

		final Part part;

		public Uploader(Part part, String fileName) {
			super();
			this.part = part;
			this.fileName = fileName;
		}

		void execute(HttpServletRequest req) throws IOException {
			this.part.write(this.fileName);
		}
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7182329627922034835L;

	public static void copyStream(InputStream in, OutputStream os,
			int bufferSize) throws IOException {
		int len = -1;
		byte[] b = new byte[bufferSize * 1024];
		try {
			while ((len = in.read(b, 0, b.length)) != -1) {
				os.write(b, 0, len);
			}
			os.flush();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	boolean accept(String name) {
		name = name.toLowerCase();
		if (name.endsWith(".gif")) {
			return true;
		}
		if (name.endsWith(".png")) {
			return true;
		}
		if (name.endsWith(".jpeg")) {
			return true;
		}
		if (name.endsWith(".jpg")) {
			return true;
		}
		return false;
	}

	final boolean confirmed(HttpServletRequest req, String password, User user) {
		// String print = "";
		// for (int i = 0; i < password.length(); i++) {
		// print += "*";
		// }
		//
		// if (user.getPassword().equals(password.hashCode())) {
		// log("passoword : " + print);
		return true;
		// }
		// Logger.getLogger(IconUploadServlet.class).error("passoword : " +
		// print);
		// return false;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log("upload start.");
		List<Uploader> uploadQue = new ArrayList<IconUploadServlet.Uploader>();
		Card card = readRequest(req, uploadQue);
		writeFiles(req, uploadQue, card);
		log("upload end.");

		resp.sendRedirect("index.jsp");
	}

	final String getFileName(Part part) {
		String partHeader = part.getHeader("content-disposition");
		if (partHeader == null) {
			return null;
		} else {
			for (String cd : partHeader.split(";")) {
				if (cd.trim().startsWith("filename")) {
					return cd.substring(cd.indexOf('=') + 1).trim()
							.replace("\"", "");
				}
			}
		}
		return null;
	}

	final String getValueByKeyword(Part part, String keyword)
			throws IOException {
		String partHeader = part.getHeader("content-disposition");
		if (!partHeader.contains(keyword)) {
			return null;
		} else {
			InputStream is = part.getInputStream();
			byte[] b = new byte[is.available()];
			is.read(b);
			return new String(b, "UTF-8");
		}
	}

	final String makePrefix() {
		return Long.toHexString(System.currentTimeMillis()) + "_"
				+ Long.toHexString(System.nanoTime()) + "_";
	}

	final void readFile(String path, Part part, List<Uploader> uploadQue) {
		log("source file name: " + path);
		log("content type: " + part.getContentType());
		String[] dr = path.split("\\\\");
		String fileName = dr[dr.length - 1];
		if (accept(fileName)) {
			uploadQue.add(new Uploader(part, fileName));
		}
		log("destination file name: " + fileName);
	}

	final Card readRequest(HttpServletRequest req, List<Uploader> uploadQue)
			throws IllegalStateException, IOException, ServletException {
		String cardId = Base64.encode(String.valueOf(System.nanoTime())
				.getBytes());
		Card card = new Card();
		card.setCardId(cardId);
		for (Part part : req.getParts()) {
			String title = getValueByKeyword(part, "title");
			String comment = getValueByKeyword(part, "comment");

			if (title != null) {
				card.setCaption(title);

			} else if (comment != null) {
				card.setCaption(comment);

			} else {
				String path = getFileName(part);
				if (path != null) {
					readFile(path, part, uploadQue);
				}
			}
		}
		return card;
	}

	final void saveUserInfo(File referenceFolder, String fileName, Card card)
			throws RuntimeException, UnknownHostException {
		File referenceFile = new File(referenceFolder, fileName);
		card.setUrl(referenceFile.getPath());
		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			datastore.save(card);
		}
		log("update user icon: " + referenceFile.getPath());
	}

	final void writeFiles(HttpServletRequest req, List<Uploader> uploadQue,
			Card card) throws IOException {
		List<String> acceptFields = new ArrayList<String>();
		String fullPath = req.getServletContext().getRealPath("../icon-keeper");
		File realFolder = new File(fullPath);
		if (realFolder.exists() || realFolder.mkdirs()) {
			File referenceFolder = new File("../../icon-keeper");
			writeIconFiles(uploadQue, card, realFolder, referenceFolder,
					acceptFields);
			req.setAttribute("acceptFields", acceptFields);
		} else {
			Logger.getLogger(IconUploadServlet.class).error(
					"upload folder does not exist.");
		}
	}

	final void writeIconFile(File realFolder, String fileName, InputStream is)
			throws FileNotFoundException, IOException {
		File realPathFile = new File(realFolder, fileName);
		copyStream(is, new FileOutputStream(realPathFile), 1024);
		log("upload user icon: " + realPathFile.getPath());
	}

	final void writeIconFiles(List<Uploader> uploadQue, Card card,
			File realFolder, File referenceFolder, List<String> acceptFields)
			throws FileNotFoundException, IOException {
		for (Uploader u : uploadQue) {
			try {
				String fileName = makePrefix() + u.fileName;
				writeIconFile(realFolder, fileName, u.part.getInputStream());
				saveUserInfo(referenceFolder, fileName, card);
				acceptFields.add(u.fileName);
			} catch (RuntimeException e) {
				Logger.getLogger(IconUploadServlet.class).error(
						"fail to upload icon file: " + u.fileName, e);
				throw e;
			}
		}
	}

}
