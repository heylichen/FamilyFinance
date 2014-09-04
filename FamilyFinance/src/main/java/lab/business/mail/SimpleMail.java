package lab.business.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SimpleMail {

	public static void main(String[] args) {
		// SUBSTITUTE YOUR EMAIL ADDRESSES HERE!
		String to = "heylichen@qq.com";
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
			// Instantiate a message
			Message msg = new MimeMessage(session);

			// Set message attributes
			msg.setFrom(new InternetAddress(from));
			InternetAddress[] address = { new InternetAddress(to) };
			msg.setRecipients(Message.RecipientType.TO, address);
			msg.setSubject("Test E-Mail through Java");
			msg.setSentDate(new Date());

			// Set message content
			msg.setText("This is a test of sending a "
					+ "plain text e-mail through Java.\n" + "Here is line 2.");

			// Send the message
			Transport.send(msg);
		} catch (MessagingException mex) {
			// Prints all nested (chained) exceptions as well
			mex.printStackTrace();
		}

	}

}
