package com.samplepin.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;

import com.samplepin.Card;
import com.samplepin.User;
import com.sun.org.apache.xml.internal.security.utils.Base64;

@WebServlet(name = "IconUploadServlet", urlPatterns = "/make-card.do")
@MultipartConfig(location = "/Developer/uploaded")
public class MakeCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7182329627922034835L;

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
		return true;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log("upload start.");
		HttpSession session = req.getSession();
		String userId = (String) session.getAttribute("userId");
		List<Uploader> uploadQue = new ArrayList<Uploader>();
		Card card = readRequest(req.getParts(), userId, uploadQue);
		writeFiles(req, uploadQue, card);

		if ((card.getCaption() != null) && !card.getCaption().isEmpty()) {
			// try (ACMongo mongo = new ACMongo()) {
			// Datastore datastore = mongo.createDatastore();
			// datastore.save(card);
			// }
			// log("upload end.");
			// Helper.setFootprint(card, userId);
			// resp.sendRedirect("index.jsp");
			// return;

			req.setAttribute("confirm", card);
			RequestDispatcher dispathcer = req
					.getRequestDispatcher("confirm-make-card.jsp");
			dispathcer.forward(req, resp);
			return;
		} else {
			log("upload failed.");
			req.setAttribute("message", "Please, write a caption.");
			RequestDispatcher dispathcer = req
					.getRequestDispatcher("make-card.jsp");
			dispathcer.forward(req, resp);
			return;
		}
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

	final Card readRequest(Collection<Part> parts, String userId,
			List<Uploader> uploadQue) throws IllegalStateException,
			IOException, ServletException {
		String cardId = Base64.encode(String
				.valueOf(System.currentTimeMillis()).getBytes());
		Card card = new Card();
		card.setImagePath("img/no_image.png");
		card.setUserId(userId);
		card.setCardId(cardId);
		card.setCreateDate(System.currentTimeMillis());

		for (Part part : parts) {
			String title = getValueByKeyword(part, "title");
			String caption = getValueByKeyword(part, "caption");
			String url = getValueByKeyword(part, "url");

			if (title != null) {

			} else if (caption != null) {
				card.setCaption(caption);

			} else if (url != null) {
				card.setUrl(url);

			} else {
				String path = getFileName(part);
				if (path != null) {
					readFile(path, part, uploadQue);
				}
			}
		}
		return card;
	}

	final void saveCardInfo(File referenceFolder, String fileName, Card card)
			throws RuntimeException, UnknownHostException {
		File referenceFile = new File(referenceFolder, fileName);
		card.setImagePath(referenceFile.getPath());
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
			Logger.getLogger(MakeCardServlet.class).error(
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
				saveCardInfo(referenceFolder, fileName, card);
				acceptFields.add(u.fileName);
			} catch (RuntimeException e) {
				Logger.getLogger(MakeCardServlet.class).error(
						"fail to upload icon file: " + u.fileName, e);
				throw e;
			}
		}
	}

}
