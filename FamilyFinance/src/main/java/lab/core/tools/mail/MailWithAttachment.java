package lab.core.tools.mail;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.sun.xml.internal.messaging.saaj.packaging.mime.internet.MimeUtility;

public class MailWithAttachment {

	public static void main(String[] args) {
		// SUBSTITUTE YOUR EMAIL ADDRESSES HERE!
		String to = "heylichen@qq.com,heylichen@163.com";
		String from = "heylichen@qq.com";
		// SUBSTITUTE YOUR ISP'S MAIL SERVER HERE!
		String host = "smtp.qq.com";

		// Create properties, get Session
		Properties props = new Properties();

		// If using static Transport.send(),
		// need to specify which host to send it to
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.host", host);
		// To see what is going on behind the scene
		props.put("mail.debug", "true");
		Authenticator auth = new MailAuthenticator("heylichen@qq.com",
				"w670o521wl1il");
		Session session = Session.getInstance(props, auth);

		try {
			// Get a Transport object to send e-mail
			Transport bus = session.getTransport("smtp");

			// Connect only once here
			// Transport.send() disconnects after each send
			// Usually, no username and password is required for SMTP
			bus.connect();
			// bus.connect("smtpserver.yourisp.net", "username", "password");

			// Instantiate a message
			Message msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = InternetAddress.parse(to, false);

			msg.setRecipients(Message.RecipientType.TO, address);
			// Parse a comma-separated list of email addresses. Be strict.
			msg.setRecipients(Message.RecipientType.CC,
					InternetAddress.parse(to, true));
			// Parse comma/space-separated list. Cut some slack.
			msg.setRecipients(Message.RecipientType.BCC, address);

			msg.setSubject("Test E-Mail through Java");
			msg.setSentDate(new Date());

			// Set message content and send
			setTextContent(msg);
			msg.saveChanges();
			bus.sendMessage(msg, address);

			setMultipartContent(msg);
			msg.saveChanges();
			bus.sendMessage(msg, address);

			setFileAsAttachment(msg,
					"C:\\Users\\Public\\Pictures\\Sample Pictures\\Lighthouse.jpg");
			msg.saveChanges();
			bus.sendMessage(msg, address);

			setHTMLContent(msg);
			msg.saveChanges();
			bus.sendMessage(msg, address);

			bus.close();

		} catch ( Exception mex) {
			// Prints all nested (chained) exceptions as well
			mex.printStackTrace();
		}

	}

	// A simple, single-part text/plain e-mail.
	public static void setTextContent(Message msg) throws MessagingException {
		// Set message content
		String mytxt = "This is a test of sending a "
				+ "plain text e-mail through Java.\n" + "Here is line 2.";
		msg.setText(mytxt);

		// Alternate form
		msg.setContent(mytxt, "text/plain");

	}

	// A simple multipart/mixed e-mail. Both body parts are text/plain.
	public static void setMultipartContent(Message msg)
			throws MessagingException {
		// Create and fill first part
		MimeBodyPart p1 = new MimeBodyPart();
		p1.setText("This is part one of a test multipart e-mail.");

		// Create and fill second part
		MimeBodyPart p2 = new MimeBodyPart();
		// Here is how to set a charset on textual content
		p2.setText("This is the second part", "us-ascii");

		// Create the Multipart. Add BodyParts to it.
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(p1);
		mp.addBodyPart(p2);

		// Set Multipart as the message's content
		msg.setContent(mp);
	}

	// Set a file as an attachment. Uses JAF FileDataSource.
	public static void setFileAsAttachment(Message msg, String filename)
			throws  Exception {

		// Create and fill first part
		MimeBodyPart p1 = new MimeBodyPart();
		p1.setText("This is part one of a test multipart e-mail."
				+ "The second part is file as an attachment");

		// Create second part
		MimeBodyPart p2 = new MimeBodyPart();

		// Put a file in the second part
		FileDataSource fds = new FileDataSource(filename);

		p2.setDataHandler(new DataHandler(fds));
		String originalName  = fds.getName();
		//��ֹ��������
		originalName = MimeUtility.encodeText(originalName);
		p2.setFileName(originalName);

		// Create the Multipart. Add BodyParts to it.
		Multipart mp = new MimeMultipart();
		mp.addBodyPart(p1);
		mp.addBodyPart(p2);

		// Set Multipart as the message's content
		msg.setContent(mp);
	}

	// Set a single part html content.
	// Sending data of any type is similar.
	public static void setHTMLContent(Message msg) throws MessagingException {

		String html = "<html><head><title>" + msg.getSubject()
				+ "</title></head><body><h1>" + msg.getSubject()
				+ "</h1><p>This is a test of sending an HTML e-mail"
				+ " through Java.</body></html>";

		// HTMLDataSource is a static nested class
		msg.setDataHandler(new DataHandler(new HTMLDataSource(html)));
	}

	/*
	 * Static nested class to act as a JAF datasource to send HTML e-mail
	 * content
	 */
	static class HTMLDataSource implements DataSource {
		private String html;

		public HTMLDataSource(String htmlString) {
			html = htmlString;
		}

		// Return html string in an InputStream.
		// A new stream must be returned each time.
		public InputStream getInputStream() throws IOException {
			if (html == null)
				throw new IOException("Null HTML");
			return new ByteArrayInputStream(html.getBytes());
		}

		public OutputStream getOutputStream() throws IOException {
			throw new IOException("This DataHandler cannot write HTML");
		}

		public String getContentType() {
			return "text/html";
		}

		public String getName() {
			return "JAF text/html dataSource to send e-mail only";
		}
	}

}
