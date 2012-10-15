package com.samplepin.servlet;

import static com.samplepin.common.Helper.valid;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.samplepin.Card;
import com.samplepin.User;
import com.samplepin.common.ActivityLogger;
import com.samplepin.common.Helper;
import com.samplepin.servlet.util.MakeThumbnail;

@WebServlet(urlPatterns = "/make-card.do")
@MultipartConfig(location = "/Developer/uploaded")
public class MakeCardServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7182329627922034835L;

	public static void copyStream(File in, File out, int bufferSize)
			throws IOException {
		copyStream(new FileInputStream(in), new FileOutputStream(out),
				bufferSize);
	}

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
				in.close();
			}
			if (os != null) {
				os.close();
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

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log("upload start.");
		String userId = Helper.getUserId(req);
		List<Uploader> uploadQue = new ArrayList<Uploader>();
		Card card = readRequest(req.getParts(), userId, uploadQue);

		if ("img/no_image.png".equals(card.getImagePath())) {
			card.setWidth(400);
			card.setHeight(400);

		} else {
			writeFiles(req, uploadQue, card);

			String fullPath = req.getServletContext().getRealPath(
					"../icon-keeper");
			File realFolder = new File(fullPath);
			String fileName = card.getImagePath().substring(
					card.getImagePath().lastIndexOf("/") + 1);
			setImageSize(realFolder, fileName, card);
		}

		if ((card.getCaption() != null) && !card.getCaption().isEmpty()) {
			User user = Helper.getUserById(userId);
			if (user != null) {
				Helper.setUserInfoToComment(card, user);
			}

			ActivityLogger.log(req, this.getClass(), card);

			req.setAttribute("confirm", card);
			RequestDispatcher dispathcer = req
					.getRequestDispatcher("confirm-make-card.jsp");
			dispathcer.forward(req, resp);
			return;
		} else {
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
		return Long.toHexString(System.nanoTime()) + "_";
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
		String cardId = Helper.generatedIdString("C_");
		Card card = new Card();
		card.setImagePath("img/no_image.png");
		card.setUserId(userId);
		card.setCardId(cardId);
		card.setCreateDate(System.currentTimeMillis());
		card.setUpdateDate(card.getCreateDate());

		for (Part part : parts) {
			String title = getValueByKeyword(part, "title");
			String caption = getValueByKeyword(part, "caption");
			String url = getValueByKeyword(part, "url");
			String keywords = getValueByKeyword(part, "keywords");
			String site = getValueByKeyword(part, "site");
			String imagePath = getValueByKeyword(part, "imagePath");
			String anonymous = getValueByKeyword(part, "anonymous");
			String parentId = getValueByKeyword(part, "parentId");
			String accessLevel = getValueByKeyword(part, "accessLevel");

			if (title != null) {

			} else if (valid(caption)) {
				card.setCaption(caption);

			} else if (valid(imagePath)) {
				card.setImagePath(imagePath);

			} else if (valid(url)) {
				card.setUrl(url);

			} else if (valid(keywords)) {
				card.setKeywords(keywords);

			} else if (valid(site)) {
				card.setSite(site);

			} else if (valid(anonymous)) {
				card.setAnonymous(true);

			} else if (valid(parentId)) {
				card.setParentId(parentId);

			} else if (valid(accessLevel)) {
				card.setAccessLevel(Integer.valueOf(accessLevel));

			} else {
				String path = getFileName(part);
				if (valid(path)) {
					readFile(path, part, uploadQue);
					card.setImagePath(path);
				}
			}
		}
		return card;
	}

	final void saveCardInfo(File realFolder, File referenceFolder,
			String fileName, Card card) throws RuntimeException, IOException {
		File referenceFile = new File(referenceFolder, fileName);
		card.setImagePath(referenceFile.getPath());
		setImageSize(realFolder, fileName, card);
	}

	final void setImageSize(File realFolder, String fileName, Card card)
			throws IOException {
		File imageFile = new File(realFolder, fileName);
		if (imageFile.exists()) {
			BufferedImage image = ImageIO.read(imageFile);
			card.setWidth(image.getWidth());
			card.setHeight(image.getHeight());
		} else {
			// no_image.png
			card.setWidth(400);
			card.setHeight(400);
		}
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
		}
	}

	final void writeIconFile(File realFolder, String fileName, InputStream is)
			throws FileNotFoundException, IOException {
		File realPathFile = new File(realFolder, fileName);
		copyStream(is, new FileOutputStream(realPathFile), 1024);
		String formatName = ImageType.getFormat(realPathFile).toString();
		new MakeThumbnail().convert(new File(realFolder, "t"), realPathFile,
				formatName);
		log("upload user icon: " + realPathFile.getPath());
	}

	final void writeIconFiles(List<Uploader> uploadQue, Card card,
			File realFolder, File referenceFolder, List<String> acceptFields)
			throws FileNotFoundException, IOException {
		for (Uploader u : uploadQue) {
			try {
				String fileName = Helper.getImageFileName(card.getUserId())
						+ u.fileName.substring(u.fileName.lastIndexOf(".",
								u.fileName.length()));
				writeIconFile(realFolder, fileName, u.part.getInputStream());
				saveCardInfo(realFolder, referenceFolder, fileName, card);
				acceptFields.add(u.fileName);
			} catch (RuntimeException e) {
				throw e;
			}
		}
	}

}
