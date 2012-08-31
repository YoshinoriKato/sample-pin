package com.samplepin.servlet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.query.Query;
import com.samplepin.User;
import com.samplepin.common.ACMongo;
import com.samplepin.common.ActivityLogger;

@WebServlet(urlPatterns = "/account.do")
@MultipartConfig(location = "/Developer/uploaded")
public class AccountServlet extends HttpServlet {

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

	final boolean accept(String name) {
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
		String userId = (String) req.getSession().getAttribute("userId");
		List<Uploader> uploadQue = new ArrayList<>();
		User user = readRequest(req, uploadQue, userId);
		if (user != null) {
			writeFiles(req, uploadQue, user);
			try (ACMongo mongo = new ACMongo()) {
				Datastore datastore = mongo.createDatastore();
				datastore.save(user);
			}
			log("upload end.");
			ActivityLogger.log(req, this.getClass(), user);
			resp.sendRedirect("account.jsp");
		} else {
			log("upload failed.");
			req.setAttribute("message", "Please, write your password.");
			ActivityLogger.log(req, this.getClass(), "upload failed");
			RequestDispatcher dispathcer = req
					.getRequestDispatcher("account.jsp");
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

	final User readRequest(HttpServletRequest req, List<Uploader> uploadQue,
			String userId) throws IllegalStateException, IOException,
			ServletException {

		try (ACMongo mongo = new ACMongo()) {
			Datastore datastore = mongo.createDatastore();
			Query<User> query = datastore.createQuery(User.class).filter(
					"userId =", userId);
			User user = query.get();
			user.setLastUpdate(System.currentTimeMillis());

			String oldPassword = null;
			String newPassword0 = null;
			String newPassword1 = null;

			for (Part part : req.getParts()) {
				String userName = getValueByKeyword(part, "userName");
				String birthDay = getValueByKeyword(part, "birthDay");
				String mail = getValueByKeyword(part, "mail");
				String country = getValueByKeyword(part, "country");
				String comment = getValueByKeyword(part, "comment");

				String password0 = getValueByKeyword(part, "password0");
				String password1 = getValueByKeyword(part, "password1");
				String password2 = getValueByKeyword(part, "password2");

				if (valid(userName)) {
					user.setUserName(userName);

				} else if (valid(birthDay)) {
					user.setBirthDay(birthDay);

				} else if (valid(mail)) {
					user.setMail(mail);

				} else if (valid(country)) {
					user.setCode(Integer.valueOf(country));

				} else if (valid(comment)) {
					user.setComment(comment);

				} else if (valid(password0)) {
					oldPassword = password0;

				} else if (valid(password1)) {
					newPassword0 = password1;

				} else if (valid(password2)) {
					newPassword1 = password2;

				} else {
					String path = getFileName(part);
					if (path != null) {
						readFile(path, part, uploadQue);
					}
				}
			}

			if (valid(oldPassword)) {
				if (valid(newPassword0) && valid(newPassword1)) {
					if (newPassword0.hashCode() == newPassword1.hashCode()) {
						if (oldPassword.hashCode() == user.getPassword()) {
							user.setPassword(newPassword0.hashCode());
							return user;
						}
					}
				} else {
					if (oldPassword.hashCode() == user.getPassword()) {
						return user;
					}
				}
			} else {
				if (userId.startsWith("TW_")) {
					return user;
				}
			}

			return null;
		}
	}

	final void saveUserInfo(File referenceFolder, String fileName, User user)
			throws RuntimeException, UnknownHostException {
		File referenceFile = new File(referenceFolder, fileName);
		user.setImagePath(referenceFile.getPath());
		log("update user icon: " + referenceFile.getPath());
	}

	final boolean valid(String password) {
		return (password != null) && !password.isEmpty();
	}

	final void writeFiles(HttpServletRequest req, List<Uploader> uploadQue,
			User user) throws IOException {
		List<String> acceptFields = new ArrayList<String>();
		String fullPath = req.getServletContext().getRealPath("../icon-keeper");
		File realFolder = new File(fullPath);
		if (realFolder.exists() || realFolder.mkdirs()) {
			File referenceFolder = new File("../../icon-keeper");
			writeIconFiles(uploadQue, user, realFolder, referenceFolder,
					acceptFields);
			req.setAttribute("acceptFields", acceptFields);
		}
	}

	final void writeIconFile(File realFolder, String fileName, InputStream is)
			throws FileNotFoundException, IOException {
		File realPathFile = new File(realFolder, fileName);
		copyStream(is, new FileOutputStream(realPathFile), 1024);
		log("upload user icon: " + realPathFile.getPath());
	}

	final void writeIconFiles(List<Uploader> uploadQue, User user,
			File realFolder, File referenceFolder, List<String> acceptFields)
			throws FileNotFoundException, IOException {
		for (Uploader u : uploadQue) {
			try {
				String fileName = makePrefix() + u.fileName;
				writeIconFile(realFolder, fileName, u.part.getInputStream());
				saveUserInfo(referenceFolder, fileName, user);
				acceptFields.add(u.fileName);
			} catch (RuntimeException e) {
				throw e;
			}
		}
	}

}
