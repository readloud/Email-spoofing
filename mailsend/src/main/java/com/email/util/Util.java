package com.email.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Util {
	final static String TO_EMAIL_LIST_FILE = "to.txt";
	final static String BODY_TO_SEND_FILE = "body";
	final static String SUBJECT_FILE = "subject.txt";
	final static String FOLDER_FILE_JOINED = "files";
	public static TYPE BODY_EXTENSION;

	public static List<String> readEmailToList() {
		List<String> result = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(TO_EMAIL_LIST_FILE));
			String line = br.readLine();

			while (line != null) {
				result.add(line);
				line = br.readLine();
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String readBodyContent() {
		String result = null;

		List<String> files = Util.getFilesNameInCurrentDirectory();
		TYPE type = Util.getBodyType(files);
		Util.BODY_EXTENSION = type;
		try {
			BufferedReader br = new BufferedReader(new FileReader(BODY_TO_SEND_FILE + "." + BODY_EXTENSION));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			result = sb.toString();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String readSubject() {
		String result = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(SUBJECT_FILE));
			String line = br.readLine();
			StringBuilder sb = new StringBuilder();

			while (line != null) {
				sb.append(line);
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			result = sb.toString();
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static List<String> readFileNamesToSend() {
		List<String> result = new ArrayList<String>();
		final File folder = new File(FOLDER_FILE_JOINED);
		String seperator = getSeparator();

		for (final File fileEntry : folder.listFiles()) {
			result.add(FOLDER_FILE_JOINED + seperator + fileEntry.getName());
		}
		return result;
	}

	public static String getSeparator() {
		String seperator;
		String your_os = System.getProperty("os.name").toLowerCase();

		if (your_os.indexOf("win") >= 0) {

			// if windows
			seperator = "\\";

		} else if (your_os.indexOf("nix") >= 0 || your_os.indexOf("nux") >= 0 || your_os.indexOf("mac") >= 0) {

			// if unix or mac
			seperator = "/";

		} else {

			// unknow os?
			seperator = "/";

		}
		return seperator;

	}

	public static boolean isStringNullOrEmpty(String text) {
		return text == null || text.isEmpty() || text.startsWith("null");
	}

	public static String extractName(String fullName) {
		return fullName.replace("files/", "");
	}

	public static List<String> getFilesNameInCurrentDirectory() {
		List<String> result = new ArrayList<String>();
		final File folder = new File(System.getProperty("user.dir"));
		for (final File fileEntry : folder.listFiles()) {
			result.add(fileEntry.getName());
		}
		return result;
	}

	public static TYPE getBodyType(List<String> files) {
		TYPE resutl = null;
		for (String file : files) {
			if (file.startsWith(BODY_TO_SEND_FILE)) {
				if (file.endsWith("txt"))
					resutl = TYPE.txt;
				else if (file.endsWith("html"))
					resutl = TYPE.html;
				break;
			}

		}
		return resutl;
	}

}
