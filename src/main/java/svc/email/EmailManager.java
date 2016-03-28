package svc.email;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailManager extends JavaMailSenderImpl {
    
    public void SendEmail(Email mail)
    {
	    final String username = "...@....com";
		final String password = "somepassword";
	
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
	
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
	
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mail.getFrom()));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(mail.getTo()));
			message.setSubject(mail.getSubject());
			message.setText("This was sent from the lockerdome Unlockable app!");
	
			Transport.send(message);
		}
		catch (MessagingException e) 
		{
			throw new RuntimeException(e);
		}
    }
}
