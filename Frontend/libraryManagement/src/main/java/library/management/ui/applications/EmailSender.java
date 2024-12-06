package library.management.ui.applications;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

  public static void sendEmail(String to, String subject, String message) {
    String from = "kiritounderwood1234@gmail.com";
    String password = "pgth srwk xamm lvki";

    Properties properties = new Properties();
    properties.put("mail.smtp.host", "smtp.gmail.com");
    properties.put("mail.smtp.port", "587");
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", "true");

    Session session = Session.getInstance(properties, new Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(from, password);
      }
    });

    try {
      Message emailMessage = new MimeMessage(session);
      emailMessage.setFrom(new InternetAddress(from));
      emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
      emailMessage.setSubject(subject);
      emailMessage.setText(message);

      Transport.send(emailMessage);
      System.out.println("Email sent successfully.");
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }
}
