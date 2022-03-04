package com.email.send;

import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.email.util.TYPE;
import com.email.util.Util;

public class EmailManager {

	private String login;
	private String password;
	Session session;
	Properties properties;

	public void inputLoginParamter() {

		@SuppressWarnings("resource")
		Scanner reader = new Scanner(System.in);
		System.out.println("Enter Login:");
		String input = reader.next();
		if (!Util.isStringNullOrEmpty(input)) {
			login = input;
			System.out.println("Enter Password:");
			input = reader.next();
			if (!Util.isStringNullOrEmpty(input))
				password = input;
		}
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean authenticate() {
		try {
			final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

			// Get system properties
			properties = System.getProperties();

			// Setup mail server
			properties.setProperty("mail.smtp.host", "smtp.gmail.com");
			properties.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
			properties.setProperty("mail.smtp.socketFactory.fallback", "false");
			properties.setProperty("mail.smtp.port", "465");
			properties.setProperty("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.debug", "true");
			properties.put("mail.store.protocol", "pop3");
			properties.put("mail.transport.protocol", "smtp");
			// Get the default Session object.

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean sendEmails() {
		try {
			// Create a default MimeMessage object.

			MimeMessage message = null;

			// Set From: header field of the header.
			// message.setFrom(new InternetAddress(from));
			List<String> toList = Util.readEmailToList();
			List<String> files = Util.readFileNamesToSend();
			String subject = Util.readSubject();
			String body = Util.readBodyContent();
			for (String to : toList) {
				session = Session.getInstance(properties, new Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(login, password);
					}
				});
				System.out.println("To: " + to);
				message = new MimeMessage(session);
				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

				// Set Subject: header field
				message.setSubject(subject);
				// Now set the actual message

				addJoinedFiles(message, body, files);

				try {
					// Send message
					Transport.send(message);
					System.out.println("Success");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("fail");

				}
			}

		} catch (MessagingException mex) {
			mex.printStackTrace();
			return false;
		}
		return true;
	}

	private void addJoinedFiles(MimeMessage message, String body, List<String> files) throws MessagingException {
		// Create the message part

		// Create a multipar message
		Multipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();

		if (Util.BODY_EXTENSION == TYPE.txt) {
			// Fill the message
			messageBodyPart.setText(body);
		} else if (Util.BODY_EXTENSION == TYPE.html) {
			// Send the actual HTML message, as big as you like
			messageBodyPart.setContent(body, "text/html");
		}
		// Set text message part
		multipart.addBodyPart(messageBodyPart);

		for (String filename : files) {

			// Part two is attachment
			messageBodyPart = new MimeBodyPart();
			String nameToSend = Util.extractName(filename);
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(nameToSend);
			multipart.addBodyPart(messageBodyPart);
		}
		// Send the complete message parts
		message.setContent(multipart);
	}

	public static void main(String[] args) {
		EmailManager eManager = new EmailManager();
		eManager.inputLoginParamter();
		eManager.authenticate();
		eManager.sendEmails();
	}

}
